import android.location.Location
import android.os.Handler
import com.github.windsekirun.kalmankt.KalmanConstants
import com.github.windsekirun.kalmankt.ext.toRadians
import com.github.windsekirun.kalmankt.filter.GeoHashFilter
import com.github.windsekirun.kalmankt.filter.KalmanFilter
import com.github.windsekirun.kalmankt.model.LocationKt
import kotlin.math.cos

class KalmanProcessor {
    private var lastLocation: LocationKt? = null
    private var predicated: Boolean = false
    private var initialized: Boolean = false

    private var latitudeTracker: KalmanFilter? = null
    private var longitudeTracker: KalmanFilter? = null
    private var altitudeTracker: KalmanFilter? = null
    private val geoHashFilter = GeoHashFilter(8, 2)
    private var refreshTime: Long = 1000
    private var locationCallback: ((LocationKt) -> Unit)? = null
    private val handler = Handler()

    fun setLocationCallback(refreshTime: Long, callback: (LocationKt) -> Unit) {
        this.refreshTime = refreshTime
        this.locationCallback = callback
    }

    fun reset(precision: Int = 8, minPointCount: Int = 2) {
        latitudeTracker = null
        longitudeTracker = null
        altitudeTracker = null
        predicated = false
        lastLocation = null
        geoHashFilter.reset(precision, minPointCount)
    }
    
    fun process(location: Location) {
        process(toLocationKt(location))
    }

    fun process(locationKt: LocationKt) {
        val accuracy = locationKt.getAccuracy()
        var position: Double
        var noise: Double

        position = locationKt.getLatitude()
        noise = accuracy * KalmanConstants.METER_TO_DEG

        if (latitudeTracker == null) {
            latitudeTracker = KalmanFilter(KalmanConstants.TIME_STEP, KalmanConstants.COORDINATE_NOISE)
            latitudeTracker?.reset(position, 0.0, noise)
        }

        if (!predicated) latitudeTracker?.predict(0.0)
        latitudeTracker?.update(position, noise)

        position = locationKt.getLongitude()
        noise = accuracy * cos(locationKt.getLatitude().toRadians()) * KalmanConstants.METER_TO_DEG

        if (longitudeTracker == null) {
            longitudeTracker = KalmanFilter(KalmanConstants.TIME_STEP, KalmanConstants.COORDINATE_NOISE)
            longitudeTracker?.reset(position, 0.0, noise)
        }

        if (!predicated) longitudeTracker?.predict(0.0)
        longitudeTracker?.update(position, noise)

        if (locationKt.hasAccuracy()) {
            position = locationKt.getAltitude()
            noise = accuracy

            if (altitudeTracker == null) {
                altitudeTracker = KalmanFilter(KalmanConstants.TIME_STEP, KalmanConstants.ALTITUDE_NOISE)
                altitudeTracker?.reset(position, 0.0, noise)
            }

            if (!predicated) altitudeTracker?.predict(0.0)
            altitudeTracker?.update(position, noise)
        }

        predicated = false
        lastLocation = locationKt

        if (!initialized) {
            initialized = true
            handler.removeCallbacksAndMessages(null)
            handler.postDelayed({ pullingResult() }, refreshTime)
        }
    }

    private fun pullingResult() {
        latitudeTracker?.predict(0.0)
        longitudeTracker?.predict(0.0)

        if (lastLocation?.hasAltitude() == true) {
            altitudeTracker?.predict(0.0)
        }

        val locationKt = LocationKt().apply {
            setLatitude(latitudeTracker?.position ?: 0.0)
            setLongitude(longitudeTracker?.position ?: 0.0)

            if (lastLocation?.hasAltitude() == true) {
                setAltitude(altitudeTracker?.position ?: 0.0)
            }

            if (lastLocation?.hasSpeed() == true) {
                setSpeed(lastLocation?.getSpeed() ?: 0.0)
            }

            if (lastLocation?.hasBearing() == true) {
                setBearing(lastLocation?.getBearing() ?: 0.0)
            }

            setAccuracy(
                (latitudeTracker?.accuracy ?: 0.0 * KalmanConstants.DEG_TO_METER) +
                        (longitudeTracker?.accuracy ?: 0.0 * KalmanConstants.DEG_TO_METER)
            )
            setTimestamp(lastLocation?.getTimestamp() ?: System.currentTimeMillis())
        }

        geoHashFilter.filter(locationKt)

        locationCallback?.invoke(locationKt)

        handler.removeCallbacksAndMessages(null)
        handler.postDelayed({ pullingResult() }, refreshTime)
    }

    fun getFilteredList() = geoHashFilter.getFilteredTrack()

    companion object {
        fun toLocationKt(data: Location): LocationKt {
            return LocationKt().apply {
                setLatitude(data.latitude)
                setLongitude(data.longitude)
                setAccuracy(data.accuracy.toDouble())
                setAltitude(data.altitude)
                setBearing(datat.bearing.toDouble())
                setSpeed(data.speed.toDouble())
                setTimestamp(data.time)
            }
        }
    }
}
