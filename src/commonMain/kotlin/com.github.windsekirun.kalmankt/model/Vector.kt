package com.github.windsekirun.kalmankt.model

class Vector {
    var a: Double = 0.0
    var b: Double = 0.0
    var c: Double = 0.0
    var d: Double = 0.0

    fun update(vector: Vector) {
        this.a = vector.a
        this.b = vector.b
        this.c = vector.c
        this.d = vector.d
    }

    fun update(a: Double, b: Double, c: Double, d: Double) {
        this.a = a
        this.b = b
        this.c = c
        this.d = d
    }

    companion object {
        fun Vector.copy(target: Vector) {
            this.update(target)
        }
    }
}