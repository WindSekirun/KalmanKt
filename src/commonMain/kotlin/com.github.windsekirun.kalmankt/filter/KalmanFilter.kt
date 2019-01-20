package com.github.windsekirun.kalmankt.filter

import com.github.windsekirun.kalmankt.model.Vector
import kotlin.math.sqrt

/**
 * Class for Kalman filter to reduce noise of result
 * @param timeStep delta time between predictions.
 * @param processNoise Standard deviation to calculate noise covariance from.
 */
class KalmanFilter(private val mt: Double, processNoise: Double) {
    private val mt2: Double = mt * mt
    private val mt2d2: Double
    private val mt3d2: Double
    private val mt4d4: Double

    private val q = Vector()

    var position: Double = 0.toDouble()
        private set
    var velocity: Double = 0.toDouble()
        private set

    private val p = Vector()

    val accuracy: Double
        get() = sqrt(p.d / mt2)

    init {
        mt2d2 = mt2 / 2.0
        mt3d2 = mt2 * mt / 2.0
        mt4d4 = mt2 * mt2 / 4.0

        val n2 = processNoise * processNoise
        q.a = n2 * mt4d4
        q.b = n2 * mt3d2
        q.c = q.b
        q.d = n2 * mt2

        p.update(q)
    }

    fun reset(position: Double, velocity: Double, noise: Double) {

        // State vector
        this.position = position
        this.velocity = velocity

        // Covariance
        val n2 = noise * noise
        p.update(n2 * mt4d4, n2 * mt3d2, n2 * mt3d2, n2 * mt2)
    }

    fun update(position: Double, noise: Double) {

        val r = noise * noise

        //  y   =  z   -   H  . x
        val y = position - this.position

        // S = H.P.H' + R
        val s = p.a + r
        val si = 1.0 / s

        // K = P.H'.S^(-1)
        val ka = p.a * si
        val kb = p.c * si

        // x = x + K.y
        this.position = this.position + ka * y
        velocity += kb * y

        // P = P - K.(H.P)
        val pa = p.a - ka * p.a
        val pb = p.b - ka * p.b
        val pc = p.c - kb * p.a
        val pd = p.d - kb * p.b

        p.update(pa, pb, pc, pd)
    }

    fun predict(acceleration: Double) {

        // x = F.x + G.u
        position += velocity * mt + acceleration * mt2d2
        velocity += acceleration * mt

        // P = F.P.F' + Q
        val pdt = p.d * mt
        val tb = p.b + pdt
        val ta = p.a + mt * (p.c + tb)
        val tc = p.c + pdt
        val td = p.d

        p.update(ta + q.a, tb + q.b, tc + q.c, td + q.d)
    }
}
