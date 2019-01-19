package com.github.windsekirun.kalmankt.filter

import com.github.windsekirun.kalmankt.model.Vector
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Class for Kalman filter to reduce noise of result
 * @param timeStep delta time between predictions.
 * @param processNoise Standard deviation to calculate noise covariance from.
 */
class KalmanFilter(private val timeStep: Double, private val processNoise: Double) {
    /**
     * Time step value
     */
    private val stepPow2: Double by lazy { timeStep.pow(2) }
    private val stepPow2Div2: Double by lazy { stepPow2 / 2.0 }
    private val stepPow3Div2: Double by lazy { stepPow2 * timeStep / 2.0 }
    private val stepPow4Div4: Double by lazy { stepPow2.pow(2) / 4.0 }

    /**
     * Noise covariance
     */
    private val q = Vector().apply {
        val n2 = processNoise.pow(2)
        a = n2 * stepPow4Div4
        b = n2 * stepPow3Div2
        c = b
        d = n2 * stepPow2
    }

    /**
     * Estimated state
     */
    private var stateA: Double = 0.0
    private var stateB: Double = 0.0

    /**
     * Estimated covariance
     */
    private val p = Vector()

    init {
        p.update(q)
    }

    /**
     * Reset filter to the given state.
     */
    fun reset(position: Double, velocity: Double, noise: Double) {
        stateA = position
        stateB = velocity

        val n2 = noise.pow(2)
        p.update(n2 * stepPow4Div4, n2 * stepPow3Div2, n2 * stepPow3Div2, n2 * stepPow2)
    }

    /**
     * Update with given measurement.
     */
    fun update(position: Double, noise: Double) {
        val r = noise.pow(2)
        val y = position - p.a // y = z - H.x
        // S = H.P.H' + R
        val s = p.a + r
        val si = 1.0 / s
        // K = P.H'.S^(-1)
        val ka = p.a * si
        val kb = p.c * si
        // x = x + K.y
        stateA += ka * y
        stateB += kb * y
        // P = P - K.(H.P)
        val pa = p.a - ka * p.a
        val pb = p.b - ka * p.b
        val pc = p.c - kb * p.c
        val pd = p.d - kb * p.d

        p.update(pa, pb, pc, pd)
    }

    /**
     * Predict state
     */
    fun predict(acceleration: Double) {
        // x = F.x + G.u
        stateA += stateB * stepPow2 + acceleration * stepPow2Div2
        stateB += acceleration * stepPow2

        // P = F.P.F' + Q
        val pdt = p.d * stepPow2
        val tb = p.b + pdt
        val ta = p.a + stepPow2 * (p.c + tb)
        val tc = p.c + pdt
        val td = p.d

        p.update(ta + q.a, tb + q.b, tc + q.c, td + q.d)
    }

    /**
     * Estimated position
     */
    fun getPosition() = stateA

    /**
     * Estimate velocity
     */
    fun getVelocity() = stateB

    /**
     * Estimate accuracy
     */
    fun getAccuracy() = sqrt(p.d / stepPow2)
}