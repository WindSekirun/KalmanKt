package com.github.windsekirun.kalmankt.ext

/**
 * Function to calculate GeoHash
 *
 * Since we already have UInt, ULong, but its still experimental api.
 *
 */
object GeoHash {

    val base32Table = charArrayOf(
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
        'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    )
    const val GEOHASH_MAX_PRECISION = 12

    private fun interleave(x: Long, y: Long): Long {
        var fx = x
        fx = fx or (fx shl 16) and 0x0000ffff0000ffffL
        fx = fx or (fx shl 8) and 0x00ff00ff00ff00ffL
        fx = fx or (fx shl 4) and 0x0f0f0f0f0f0f0f0fL
        fx = fx or (fx shl 2) and 0x3333333333333333L
        fx = fx or (fx shl 1) and 0x5555555555555555L

        var fy = y
        fy = fy or (fy shl 16) and 0x0000ffff0000ffffL
        fy = fy or (fy shl 8) and 0x00ff00ff00ff00ffL
        fy = fy or (fy shl 4) and 0x0f0f0f0f0f0f0f0fL
        fy = fy or (fy shl 2) and 0x3333333333333333L
        fy = fy or (fy shl 1) and 0x5555555555555555L

        return fx or (fy shl 1)
    }

    fun u64Encode(lat: Double, lon: Double, prec: Int): Long {
        var flat = (lat / 180.0 + 1.5).toBits()
        var flon = (lon / 360.0 + 1.5).toBits()
        flat = flat shr 20
        flon = flon shr 20
        flat = flat and 0x00000000ffffffffL
        flon = flon and 0x00000000ffffffffL
        return interleave(flat, flon) shr (GEOHASH_MAX_PRECISION - prec) * 5
    }

    fun geohash(geohash: Long, prec: Int): String {
        var fgeoHash = geohash
        var fprec = prec

        val buff = StringBuilder(GEOHASH_MAX_PRECISION)
        fgeoHash = fgeoHash shr 4
        fgeoHash = fgeoHash and 0x0fffffffffffffffL

        while (fprec-- > 0) {
            buff.append(base32Table[(fgeoHash and 0x1f).toInt()])
            fgeoHash = fgeoHash shr 5
        }
        return buff.reverse().toString()
    }
}