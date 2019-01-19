package com.github.windsekirun.kalmankt.processor

import com.github.windsekirun.kalmankt.ext.toRadians
import com.github.windsekirun.kalmankt.filter.GeoHashFilter
import com.github.windsekirun.kalmankt.filter.KalmanFilter
import com.github.windsekirun.kalmankt.model.LocationKt
import kotlin.math.cos

class KalmanProcessor {
    private var lastLocation: LocationKt? = null
    private var predicated: Boolean = false

    private var latitudeTracker: KalmanFilter? = null
    private var longitudeTracker: KalmanFilter? = null
    private var altitudeTracker: KalmanFilter? = null
    private val geoHashFilter = GeoHashFilter(8, 2)

    fun reset(precision: Int = 8, minPointCount: Int = 2) {
        latitudeTracker = null
        longitudeTracker = null
        altitudeTracker = null
        predicated = false
        lastLocation = null
        geoHashFilter.reset(precision, minPointCount)
    }

    fun process(locationKt: LocationKt) {
        val accuracy = locationKt.getAccuracy()
        var position: Double
        var noise: Double

        position = locationKt.getLatitude()
        noise = accuracy * METER_TO_DEG

        if (latitudeTracker == null) {
            latitudeTracker = KalmanFilter(TIME_STEP, COORDINATE_NOISE)
            latitudeTracker?.reset(position, 0.0, noise)
        }

        if (!predicated) latitudeTracker?.predict(0.0)
        latitudeTracker?.update(position, noise)

        position = locationKt.getLongitude()
        noise = accuracy * cos(locationKt.getLatitude().toRadians()) * METER_TO_DEG

        if (longitudeTracker == null) {
            longitudeTracker = KalmanFilter(TIME_STEP, COORDINATE_NOISE)
            longitudeTracker?.reset(position, 0.0, noise)
        }

        if (!predicated) longitudeTracker?.predict(0.0)
        longitudeTracker?.update(position, noise)

        if (locationKt.hasAccuracy()) {
            position = locationKt.getAltitude()
            noise = accuracy

            if (altitudeTracker == null) {
                altitudeTracker = KalmanFilter(TIME_STEP, ALTITUDE_NOISE)
                altitudeTracker?.reset(position, 0.0, noise)
            }

            if (!predicated) altitudeTracker?.predict(0.0)
            altitudeTracker?.update(position, noise)
        }

        predicated = false
        lastLocation = locationKt
    }

    fun pullingResult(): LocationKt {
        latitudeTracker?.predict(0.0)
        longitudeTracker?.predict(0.0)

        if (lastLocation?.hasAltitude() == true) {
            altitudeTracker?.predict(0.0)
        }

        val locationKt = LocationKt().apply {
            setLatitude(latitudeTracker?.getPosition() ?: 0.0)
            setLongitude(longitudeTracker?.getPosition() ?: 0.0)

            if (lastLocation?.hasAltitude() == true) {
                setAltitude(altitudeTracker?.getPosition() ?: 0.0)
            }

            if (lastLocation?.hasSpeed() == true) {
                setSpeed(lastLocation?.getSpeed() ?: 0.0)
            }

            if (lastLocation?.hasBearing() == true) {
                setBearing(lastLocation?.getBearing() ?: 0.0)
            }

            setAccuracy(
                (latitudeTracker?.getAccuracy() ?: 0.0 * DEG_TO_METER) +
                        (longitudeTracker?.getAccuracy() ?: 0.0 * DEG_TO_METER)
            )
            setTimestamp(lastLocation?.getTimestamp() ?: 0)
        }

        geoHashFilter.filter(locationKt)

        return locationKt
    }

    fun getFilteredList() = geoHashFilter.getFilteredTrack()

    companion object {
        const val DEG_TO_METER = 111225.0
        const val METER_TO_DEG = 1.0 / DEG_TO_METER
        const val TIME_STEP = 1.0
        const val COORDINATE_NOISE = 4.0 * METER_TO_DEG
        const val ALTITUDE_NOISE = 10.0
    }
}