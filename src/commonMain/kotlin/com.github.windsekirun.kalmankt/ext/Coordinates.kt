package com.github.windsekirun.kalmankt.ext

import com.github.windsekirun.kalmankt.model.GeoPoint
import kotlin.math.*


object Coordinates {
    const val EARTH_RADIUS = 6371.0 * 1000.0

    fun distanceBetween(lon1: Double, lat1: Double, lon2: Double, lat2: Double): Double {
        val lati1 = lat1.toRadians()
        val lati2 = lat2.toRadians()
        val deltaLatitude = (lat2 - lat1).toRadians()
        val deltaLongitude = (lon2 - lon1).toRadians()


        val a = sin(deltaLatitude / 2) * sin(deltaLatitude / 2) +
                cos(lati1) * cos(lati2) *
                sin(deltaLongitude / 2) * sin(deltaLongitude / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        val d = EARTH_RADIUS * c
        return d
    }

    fun longitudeToMeters(lon: Double): Double {
        val distance = distanceBetween(lon, 0.0, 0.0, 0.0)
        return distance * if (lon < 0.0) -1.0 else 1.0
    }

    fun metersToGeoPoint(
        lonMeters: Double,
        latMeters: Double
    ): GeoPoint {
        val point = GeoPoint(0.0, 0.0)
        val pointEast = pointPlusDistanceEast(point, lonMeters)
        return pointPlusDistanceNorth(pointEast, latMeters)
    }

    fun latitudeToMeters(lat: Double): Double {
        val distance = distanceBetween(0.0, lat, 0.0, 0.0)
        return distance * if (lat < 0.0) -1.0 else 1.0
    }

    fun calculateDistance(track: Array<GeoPoint>?): Double {
        var distance = 0.0
        var lastLon: Double
        var lastLat: Double
        if (track == null || track.size - 1 <= 0) return 0.0

        lastLon = track[0].longitude
        lastLat = track[0].latitude

        for (i in 1 until track.size) {
            distance += Coordinates.distanceBetween(
                lastLat, lastLon,
                track[i].latitude, track[i].longitude
            )
            lastLat = track[i].latitude
            lastLon = track[i].longitude
        }
        return distance
    }

    private fun getPointAhead(
        point: GeoPoint,
        distance: Double,
        azimuthDegrees: Double
    ): GeoPoint {
        val radiusFraction = distance / EARTH_RADIUS
        val bearing = azimuthDegrees.toRadians()
        val lat1 = point.latitude.toRadians()
        val lng1 = point.longitude.toRadians()

        val lat2 = asin(sin(lat1) * cos(radiusFraction) + cos(lat1) * sin(radiusFraction) * cos(bearing))
        var lng2 = lng1 + atan2(
            sin(bearing) * sin(radiusFraction) * cos(lat1),
            cos(radiusFraction) - sin(lat1) * sin(lat2)
        )
        lng2 = (lng2 + 3.0 * PI) % (2.0 * PI) - PI

        return GeoPoint(lat2.toDegrees(), lng2.toDegrees())
    }

    private fun pointPlusDistanceEast(point: GeoPoint, distance: Double): GeoPoint {
        return getPointAhead(point, distance, 90.0)
    }

    private fun pointPlusDistanceNorth(point: GeoPoint, distance: Double): GeoPoint {
        return getPointAhead(point, distance, 0.0)
    }
}