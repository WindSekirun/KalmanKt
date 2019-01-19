package com.github.windsekirun.kalmankt.model

class GeoPoint(var latitude: Double, var longitude: Double) {

    fun copy(target: GeoPoint) {
        this.latitude = target.latitude
        this.longitude = target.longitude
    }
}