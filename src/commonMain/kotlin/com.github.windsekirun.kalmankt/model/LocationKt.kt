package com.github.windsekirun.kalmankt.model

import kotlin.math.*

/**
 * Back-port of combined version of Location, CLLocation
 */
class LocationKt {
    private var _latitude: Double = 0.0
    private var _longitude: Double = 0.0
    private var _altitude: Double = 0.0
    private var _speed: Double = 0.0
    private var _bearing: Double = 0.0
    private var _horizontalAccuracyMeter: Double = 0.0
    private var _timestamp: Long = 0
    private var fieldMask: Byte = 0

    fun getLatitude() = _latitude

    fun setLatitude(latitude: Double) {
        this._latitude = latitude
    }

    fun getLongitude() = _longitude

    fun setLongitude(longitude: Double) {
        this._longitude = longitude
    }

    fun getAltitude() = _altitude

    fun hasAltitude(): Boolean {
        return fieldMask.toInt() and HAS_ALTITUDE_MASK != 0
    }

    fun setAltitude(altitude: Double) {
        this._altitude = altitude
        fieldMask = (fieldMask.toInt() or HAS_ALTITUDE_MASK).toByte()
    }

    fun getSpeed() = _speed

    fun hasSpeed(): Boolean {
        return fieldMask.toInt() and HAS_SPEED_MASK != 0
    }

    fun setSpeed(speed: Double) {
        this._speed = speed
        fieldMask = (fieldMask.toInt() or HAS_SPEED_MASK).toByte()
    }

    fun getBearing() = _bearing

    fun hasBearing(): Boolean {
        return fieldMask.toInt() and HAS_BEARING_MASK != 0
    }

    fun setBearing(bearing: Double) {
        var newBearing = bearing
        while (newBearing < 0.0f) {
            newBearing += 360.0f
        }
        while (newBearing >= 360.0f) {
            newBearing -= 360.0f
        }

        this._bearing = newBearing
        fieldMask = (fieldMask.toInt() or HAS_BEARING_MASK).toByte()
    }

    fun getAccuracy() = _horizontalAccuracyMeter

    fun hasAccuracy(): Boolean {
        return fieldMask.toInt() and HAS_HORIZONTAL_ACCURACY_MASK != 0
    }

    fun setAccuracy(accuracy: Double) {
        this._horizontalAccuracyMeter = accuracy
        fieldMask = (fieldMask.toInt() or HAS_HORIZONTAL_ACCURACY_MASK).toByte()
    }

    fun getTimestamp() = _timestamp

    fun setTimestamp(timestamp: Long) {
        this._timestamp = timestamp
    }

    companion object {
        const val HAS_ALTITUDE_MASK = 1
        const val HAS_SPEED_MASK = 2
        const val HAS_BEARING_MASK = 4
        const val HAS_HORIZONTAL_ACCURACY_MASK = 8

        fun distanceBetween(start: GeoPoint, end: GeoPoint, results: FloatArray?) = distanceBetween(
            start.latitude,
            start.longitude, end.latitude, end.longitude, results
        )

        private fun distanceBetween(
            startLatitude: Double,
            startLongitude: Double,
            endLatitude: Double,
            endLongitude: Double,
            results: FloatArray?
        ) {
            if (results == null || results.isEmpty()) return

            val data = BearingDistanceCache()
            computeDistanceAndBearing(startLatitude, startLongitude, endLatitude, endLongitude, data)
            results[0] = data.distance
            if (results.size > 1) {
                results[1] = data.initialBearing
                if (results.size > 2) {
                    results[2] = data.finalBearing
                }
            }
        }

        private fun computeDistanceAndBearing(
            lat1: Double, lon1: Double,
            lat2: Double, lon2: Double, results: BearingDistanceCache
        ) {
            var newLat1 = lat1
            var newLon1 = lon1
            var newLat2 = lat2
            var newLon2 = lon2
            // Based on http://www.ngs.noaa.gov/PUBS_LIB/inverse.pdf
            // using the "Inverse Formula" (section 4)
            val maxiters = 20
            // Convert lat/long to radians
            newLat1 *= PI / 180.0
            newLat2 *= PI / 180.0
            newLon1 *= PI / 180.0
            newLon2 *= PI / 180.0
            val a = 6378137.0 // WGS84 major axis
            val b = 6356752.3142 // WGS84 semi-major axis
            val f = (a - b) / a
            val aSqMinusBSqOverBSq = (a * a - b * b) / (b * b)
            val l = newLon2 - newLon1
            var a2 = 0.0
            val u1 = atan((1.0 - f) * tan(newLat1))
            val u2 = atan((1.0 - f) * tan(newLat2))
            val cosU1 = cos(u1)
            val cosU2 = cos(u2)
            val sinU1 = sin(u1)
            val sinU2 = sin(u2)
            val cosU1cosU2 = cosU1 * cosU2
            val sinU1sinU2 = sinU1 * sinU2
            var sigma = 0.0
            var deltaSigma = 0.0
            var cosSqAlpha: Double
            var cos2SM: Double
            var cosSigma: Double
            var sinSigma: Double
            var cosLambda = 0.0
            var sinLambda = 0.0
            var lambda = l // initial guess
            for (iter in 0 until maxiters) {
                val lambdaOrig = lambda
                cosLambda = cos(lambda)
                sinLambda = sin(lambda)
                val t1 = cosU2 * sinLambda
                val t2 = cosU1 * sinU2 - sinU1 * cosU2 * cosLambda
                val sinSqSigma = t1 * t1 + t2 * t2 // (14)
                sinSigma = sqrt(sinSqSigma)
                cosSigma = sinU1sinU2 + cosU1cosU2 * cosLambda // (15)
                sigma = atan2(sinSigma, cosSigma) // (16)
                val sinAlpha = if (sinSigma == 0.0) 0.0 else cosU1cosU2 * sinLambda / sinSigma // (17)
                cosSqAlpha = 1.0 - sinAlpha * sinAlpha
                cos2SM = if (cosSqAlpha == 0.0) 0.0 else cosSigma - 2.0 * sinU1sinU2 / cosSqAlpha // (18)
                val uSquared = cosSqAlpha * aSqMinusBSqOverBSq // defn
                a2 = 1 + uSquared / 16384.0 * // (3)
                        (4096.0 + uSquared * (-768 + uSquared * (320.0 - 175.0 * uSquared)))
                val b2 = uSquared / 1024.0 * // (4)
                        (256.0 + uSquared * (-128.0 + uSquared * (74.0 - 47.0 * uSquared)))
                val c2 = f / 16.0 *
                        cosSqAlpha *
                        (4.0 + f * (4.0 - 3.0 * cosSqAlpha)) // (10)
                val cos2SMSq = cos2SM * cos2SM
                deltaSigma = b2 * sinSigma * // (6)
                        (cos2SM + b2 / 4.0 * (cosSigma * (-1.0 + 2.0 * cos2SMSq) - b2 / 6.0 * cos2SM *
                                (-3.0 + 4.0 * sinSigma * sinSigma) *
                                (-3.0 + 4.0 * cos2SMSq)))
                lambda = l + (1.0 - c2) * f * sinAlpha *
                        (sigma + c2 * sinSigma *
                                (cos2SM + c2 * cosSigma *
                                        (-1.0 + 2.0 * cos2SM * cos2SM))) // (11)
                val delta = (lambda - lambdaOrig) / lambda
                if (abs(delta) < 1.0e-12) {
                    break
                }
            }

            var initialBearing = atan2(cosU2 * sinLambda, cosU1 * sinU2 - sinU1 * cosU2 * cosLambda).toFloat()
            initialBearing *= (180.0 / PI).toFloat()
            var finalBearing = atan2(cosU1 * sinLambda, -sinU1 * cosU2 + cosU1 * sinU2 * cosLambda).toFloat()
            finalBearing *= (180.0 / PI).toFloat()

            results.apply {
                this.distance = (b * a2 * (sigma - deltaSigma)).toFloat()
                this.initialBearing = initialBearing
                this.finalBearing = finalBearing
                this.lat1 = newLat1
                this.lat2 = newLat2
                this.lon1 = newLon1
                this.lon2 = newLon2
            }
        }

    }
}