package com.github.windsekirun.kalmankt.filter

import com.github.windsekirun.kalmankt.ext.Coordinates
import com.github.windsekirun.kalmankt.model.GeoPoint
import com.github.windsekirun.kalmankt.model.LocationKt

/**
 * GeoHashFilter to filter almost same value of routes
 */
class GeoHashFilter(private var geohashPrecision: Int, private var geohashMinPointCount: Int) {
    private var geoFiltered: Double = 0.0
    private var geoFilteredHP: Double = 0.0
    private var distanceAsIs: Double = 0.0
    private var distanceAsIsHP: Double = 0.0
    private var ppCompGeoHash: Int = 0
    private var ppReadGeoHash: Int = 1

    private lateinit var hashBuffers: LongArray
    private var pointsInCurrentGeoHashCount: Int = 0

    private var currentGeoPoint: GeoPoint = GeoPoint(COORD_NOT_INITIALIZED, COORD_NOT_INITIALIZED)
    private var lastApprovedGeoPoint: GeoPoint = GeoPoint(COORD_NOT_INITIALIZED, COORD_NOT_INITIALIZED)
    private var lastGeoPoint: GeoPoint = GeoPoint(COORD_NOT_INITIALIZED, COORD_NOT_INITIALIZED)
    private var isFirstCoordinate = true
    private val filteredTrack = mutableListOf<LocationKt>()

    private var resBuffAsIs = FloatArray(3)
    private var resBuffGeo = FloatArray(3)

    init {
        reset()
    }

    fun getFilteredTrack() = filteredTrack

    fun reset(precision: Int = 8, count: Int = 2) {
        filteredTrack.clear()
        hashBuffers = LongArray(2)
        pointsInCurrentGeoHashCount = 0
        lastApprovedGeoPoint = GeoPoint(COORD_NOT_INITIALIZED, COORD_NOT_INITIALIZED)
        currentGeoPoint = GeoPoint(COORD_NOT_INITIALIZED, COORD_NOT_INITIALIZED)
        lastGeoPoint = GeoPoint(COORD_NOT_INITIALIZED, COORD_NOT_INITIALIZED)
        geoFilteredHP = 0.0
        geoFiltered = 0.0
        distanceAsIsHP = 0.0
        distanceAsIs = 0.0
        isFirstCoordinate = true

        this.geohashPrecision = precision
        this.geohashMinPointCount = count
    }

    fun filter(locationKt: LocationKt) {
        val point = GeoPoint(locationKt.getLatitude(), locationKt.getLongitude())
        if (isFirstCoordinate) {
            hashBuffers[ppCompGeoHash] = GeoHash.u64Encode(point.latitude, point.longitude, geohashPrecision)

            isFirstCoordinate = false
            pointsInCurrentGeoHashCount = 1
            currentGeoPoint.copy(point)
            lastGeoPoint.copy(point)
            return
        }

        distanceAsIs += Coordinates.distanceBetween(
            lastGeoPoint.longitude,
            lastGeoPoint.latitude, point.longitude, point.latitude
        )

        LocationKt.distanceBetween(lastGeoPoint, point, resBuffAsIs)
        distanceAsIsHP += resBuffAsIs[0]
        lastGeoPoint.latitude = locationKt.getLatitude()
        lastGeoPoint.longitude = locationKt.getLongitude()

        hashBuffers[ppReadGeoHash] = GeoHash.u64Encode(point.latitude, point.longitude, geohashPrecision)
        if (hashBuffers[ppCompGeoHash] != hashBuffers[ppReadGeoHash]) {
            if (pointsInCurrentGeoHashCount >= geohashMinPointCount) {
                currentGeoPoint.latitude /= pointsInCurrentGeoHashCount
                currentGeoPoint.longitude /= pointsInCurrentGeoHashCount

                if (lastApprovedGeoPoint.latitude != COORD_NOT_INITIALIZED) {
                    val dd1 = Coordinates.distanceBetween(
                        lastApprovedGeoPoint.longitude,
                        lastApprovedGeoPoint.latitude,
                        currentGeoPoint.longitude,
                        currentGeoPoint.latitude
                    )

                    geoFiltered += dd1
                    LocationKt.distanceBetween(lastApprovedGeoPoint, currentGeoPoint, resBuffGeo)
                    val dd2 = resBuffGeo[0]
                    geoFilteredHP += dd2
                }

                lastApprovedGeoPoint.copy(currentGeoPoint)

                val filtered = LocationKt().apply {
                    setLatitude(lastApprovedGeoPoint.latitude)
                    setLongitude(lastApprovedGeoPoint.longitude)
                    setAltitude(locationKt.getAltitude())
                    setTimestamp(locationKt.getTimestamp())
                }
                filteredTrack.add(filtered)

                currentGeoPoint.latitude = 0.0
                currentGeoPoint.longitude = 0.0
            }

            pointsInCurrentGeoHashCount = 1
            currentGeoPoint.copy(point)
            val swp = ppCompGeoHash
            ppCompGeoHash = ppReadGeoHash
            ppReadGeoHash = swp;
            return
        }

        currentGeoPoint.latitude += point.latitude
        currentGeoPoint.longitude += point.longitude
        ++pointsInCurrentGeoHashCount
    }

    fun stop() {
        if (pointsInCurrentGeoHashCount >= geohashMinPointCount) {
            currentGeoPoint.latitude /= pointsInCurrentGeoHashCount
            currentGeoPoint.longitude /= pointsInCurrentGeoHashCount

            if (lastApprovedGeoPoint.latitude != COORD_NOT_INITIALIZED) {
                val dd1 = Coordinates.distanceBetween(
                    lastApprovedGeoPoint.longitude,
                    lastApprovedGeoPoint.latitude,
                    currentGeoPoint.longitude,
                    currentGeoPoint.latitude
                )
                geoFiltered += dd1
                LocationKt.distanceBetween(lastApprovedGeoPoint, currentGeoPoint, resBuffGeo)
                val dd2 = resBuffGeo[0]
                geoFilteredHP += dd2
            }
            lastApprovedGeoPoint.copy(currentGeoPoint)

            val filtered = LocationKt().apply {
                setLatitude(lastApprovedGeoPoint.latitude)
                setLongitude(lastApprovedGeoPoint.longitude)
            }
            filteredTrack.add(filtered)

            currentGeoPoint.longitude = 0.0
            currentGeoPoint.latitude = 0.0
        }
    }

    companion object {
        const val COORD_NOT_INITIALIZED = 361.0
    }
}