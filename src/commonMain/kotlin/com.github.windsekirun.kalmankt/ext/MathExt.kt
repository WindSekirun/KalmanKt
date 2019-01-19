package com.github.windsekirun.kalmankt.ext

import kotlin.math.PI

/**
 * Degrees to Radians
 * @return converted radians
 */
fun Double.toRadians(): Double = this * PI / 180.0

/**
 * Radians to Degrees
 * @return converted degrees
 */
fun Double.toDegrees(): Double = this * 180.0 / PI