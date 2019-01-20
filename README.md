# KalmanKt

Kotlin Shared Library for Android, IOS that provides **implementations for Kalman filters and geohash filters** for increase accuracy of Geolocations 

It use the Kalman filter algorithm to predict the correction. (Not Extended Kalman Filter, because it's too hard to me >.<)

This library use [Kotlin Multiplatform](https://kotlinlang.org/docs/reference/multiplatform.html) for Kotlin/JVM(Android) and Kotlin/Native(IOS). 

## Usages

### Android

#### Install

1. Add 'KalmanKt-Jvm-1.0.0.jar' into libs folder. [Download here](https://github.com/WindSekirun/KalmanKt/tree/master/android)
2. Sync Project with Gradle.

#### Usage with KalmanProcessor.kt

Because of lack support of Kotlin Multiplatform, This library provide pure implementations for Kalman filters and geohash filters. 

So, This library provide 'KalmanProcessor' to handle predict using KalmanFilter.

1. [Download this code](https://github.com/WindSekirun/KalmanKt/blob/master/android/KalmanProcessor.kt) and put into your project.
2. Generate instance of KalmanProcessor by `private val kalmanProcessor = KalmanProcessor()`. 
3. Reset processor before start `LocationManager.requestLocationUpdates`. by `kalmanProcessor.reset(8, 2)`
4. Receive predicted result by `kalmanProcessor.setLocationCallback(1000) {}`
5. When `onLocationChanged` is called, call `kalmanProcessor.process(location)`

### IOS (Swift)

#### Install

1. Add KalmanKt.framework into XCode project. Download arm64 for real device, x86_64 for simulator. [Download here](https://github.com/WindSekirun/KalmanKt/tree/master/ios)
2. Add KalmanKt.framework to Embeed Binaries, Linked Frameworks and Libraries.
3. Disable bitcode by Build Settings > 'Enable Bitcode' to false. Kotlin/Native produces the fully native binaries, not the LLVM bitcode.

#### Usage with KalmanProcessor.swift

TBD. 

(I had no experience developing IOS myself, so I asked a friend for help. It will be updated soon.)

## Update History

- Ver 1.0.0 (2019-01-20) Initial Release

## License

MIT License. 
