package com.github.windsekirun.kalmankt

object KalmanConstants {
    const val DEG_TO_METER = 111225.0
    const val METER_TO_DEG = 1.0 / DEG_TO_METER
    const val TIME_STEP = 1.0
    const val COORDINATE_NOISE = 4.0 * METER_TO_DEG
    const val ALTITUDE_NOISE = 10.0
}