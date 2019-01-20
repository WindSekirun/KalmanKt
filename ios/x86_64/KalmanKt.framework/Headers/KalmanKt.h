#import <Foundation/Foundation.h>

@class KalmanKtKalmanConstants, KalmanKtGeoHashFilter, KalmanKtLocationKt, KalmanKtGeoHashFilterCompanion, KalmanKtKalmanFilter, KalmanKtCoordinates, KalmanKtGeoPoint, KalmanKtKotlinArray, KalmanKtGeoHash, KalmanKtKotlinCharArray, KalmanKtBearingDistanceCache, KalmanKtLocationKtCompanion, KalmanKtKotlinFloatArray, KalmanKtVector, KalmanKtVectorCompanion, KalmanKtKotlinCharIterator, KalmanKtKotlinFloatIterator;

@protocol KalmanKtKotlinIterator;

NS_ASSUME_NONNULL_BEGIN

@interface KotlinBase : NSObject
- (instancetype)init __attribute__((unavailable));
+ (instancetype)new __attribute__((unavailable));
+ (void)initialize __attribute__((objc_requires_super));
@end;

@interface KotlinBase (KotlinBaseCopying) <NSCopying>
@end;

__attribute__((objc_runtime_name("KotlinMutableSet")))
__attribute__((swift_name("KotlinMutableSet")))
@interface KalmanKtMutableSet<ObjectType> : NSMutableSet<ObjectType>
@end;

__attribute__((objc_runtime_name("KotlinMutableDictionary")))
__attribute__((swift_name("KotlinMutableDictionary")))
@interface KalmanKtMutableDictionary<KeyType, ObjectType> : NSMutableDictionary<KeyType, ObjectType>
@end;

@interface NSError (NSErrorKotlinException)
@property (readonly) id _Nullable kotlinException;
@end;

__attribute__((objc_runtime_name("KotlinNumber")))
__attribute__((swift_name("KotlinNumber")))
@interface KalmanKtNumber : NSNumber
- (instancetype)initWithChar:(char)value __attribute__((unavailable));
- (instancetype)initWithUnsignedChar:(unsigned char)value __attribute__((unavailable));
- (instancetype)initWithShort:(short)value __attribute__((unavailable));
- (instancetype)initWithUnsignedShort:(unsigned short)value __attribute__((unavailable));
- (instancetype)initWithInt:(int)value __attribute__((unavailable));
- (instancetype)initWithUnsignedInt:(unsigned int)value __attribute__((unavailable));
- (instancetype)initWithLong:(long)value __attribute__((unavailable));
- (instancetype)initWithUnsignedLong:(unsigned long)value __attribute__((unavailable));
- (instancetype)initWithLongLong:(long long)value __attribute__((unavailable));
- (instancetype)initWithUnsignedLongLong:(unsigned long long)value __attribute__((unavailable));
- (instancetype)initWithFloat:(float)value __attribute__((unavailable));
- (instancetype)initWithDouble:(double)value __attribute__((unavailable));
- (instancetype)initWithBool:(BOOL)value __attribute__((unavailable));
- (instancetype)initWithInteger:(NSInteger)value __attribute__((unavailable));
- (instancetype)initWithUnsignedInteger:(NSUInteger)value __attribute__((unavailable));
+ (instancetype)numberWithChar:(char)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedChar:(unsigned char)value __attribute__((unavailable));
+ (instancetype)numberWithShort:(short)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedShort:(unsigned short)value __attribute__((unavailable));
+ (instancetype)numberWithInt:(int)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedInt:(unsigned int)value __attribute__((unavailable));
+ (instancetype)numberWithLong:(long)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedLong:(unsigned long)value __attribute__((unavailable));
+ (instancetype)numberWithLongLong:(long long)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedLongLong:(unsigned long long)value __attribute__((unavailable));
+ (instancetype)numberWithFloat:(float)value __attribute__((unavailable));
+ (instancetype)numberWithDouble:(double)value __attribute__((unavailable));
+ (instancetype)numberWithBool:(BOOL)value __attribute__((unavailable));
+ (instancetype)numberWithInteger:(NSInteger)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedInteger:(NSUInteger)value __attribute__((unavailable));
@end;

__attribute__((objc_runtime_name("KotlinByte")))
__attribute__((swift_name("KotlinByte")))
@interface KalmanKtByte : KalmanKtNumber
- (instancetype)initWithChar:(char)value;
+ (instancetype)numberWithChar:(char)value;
@end;

__attribute__((objc_runtime_name("KotlinUByte")))
__attribute__((swift_name("KotlinUByte")))
@interface KalmanKtUByte : KalmanKtNumber
- (instancetype)initWithUnsignedChar:(unsigned char)value;
+ (instancetype)numberWithUnsignedChar:(unsigned char)value;
@end;

__attribute__((objc_runtime_name("KotlinShort")))
__attribute__((swift_name("KotlinShort")))
@interface KalmanKtShort : KalmanKtNumber
- (instancetype)initWithShort:(short)value;
+ (instancetype)numberWithShort:(short)value;
@end;

__attribute__((objc_runtime_name("KotlinUShort")))
__attribute__((swift_name("KotlinUShort")))
@interface KalmanKtUShort : KalmanKtNumber
- (instancetype)initWithUnsignedShort:(unsigned short)value;
+ (instancetype)numberWithUnsignedShort:(unsigned short)value;
@end;

__attribute__((objc_runtime_name("KotlinInt")))
__attribute__((swift_name("KotlinInt")))
@interface KalmanKtInt : KalmanKtNumber
- (instancetype)initWithInt:(int)value;
+ (instancetype)numberWithInt:(int)value;
@end;

__attribute__((objc_runtime_name("KotlinUInt")))
__attribute__((swift_name("KotlinUInt")))
@interface KalmanKtUInt : KalmanKtNumber
- (instancetype)initWithUnsignedInt:(unsigned int)value;
+ (instancetype)numberWithUnsignedInt:(unsigned int)value;
@end;

__attribute__((objc_runtime_name("KotlinLong")))
__attribute__((swift_name("KotlinLong")))
@interface KalmanKtLong : KalmanKtNumber
- (instancetype)initWithLongLong:(long long)value;
+ (instancetype)numberWithLongLong:(long long)value;
@end;

__attribute__((objc_runtime_name("KotlinULong")))
__attribute__((swift_name("KotlinULong")))
@interface KalmanKtULong : KalmanKtNumber
- (instancetype)initWithUnsignedLongLong:(unsigned long long)value;
+ (instancetype)numberWithUnsignedLongLong:(unsigned long long)value;
@end;

__attribute__((objc_runtime_name("KotlinFloat")))
__attribute__((swift_name("KotlinFloat")))
@interface KalmanKtFloat : KalmanKtNumber
- (instancetype)initWithFloat:(float)value;
+ (instancetype)numberWithFloat:(float)value;
@end;

__attribute__((objc_runtime_name("KotlinDouble")))
__attribute__((swift_name("KotlinDouble")))
@interface KalmanKtDouble : KalmanKtNumber
- (instancetype)initWithDouble:(double)value;
+ (instancetype)numberWithDouble:(double)value;
@end;

__attribute__((objc_runtime_name("KotlinBoolean")))
__attribute__((swift_name("KotlinBoolean")))
@interface KalmanKtBoolean : KalmanKtNumber
- (instancetype)initWithBool:(BOOL)value;
+ (instancetype)numberWithBool:(BOOL)value;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KalmanConstants")))
@interface KalmanKtKalmanConstants : KotlinBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)kalmanConstants __attribute__((swift_name("init()")));
@property (readonly) double DEG_TO_METER;
@property (readonly) double METER_TO_DEG;
@property (readonly) double TIME_STEP;
@property (readonly) double COORDINATE_NOISE;
@property (readonly) double ALTITUDE_NOISE;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("GeoHashFilter")))
@interface KalmanKtGeoHashFilter : KotlinBase
- (instancetype)initWithGeohashPrecision:(int32_t)geohashPrecision geohashMinPointCount:(int32_t)geohashMinPointCount __attribute__((swift_name("init(geohashPrecision:geohashMinPointCount:)"))) __attribute__((objc_designated_initializer));
- (NSMutableArray<KalmanKtLocationKt *> *)getFilteredTrack __attribute__((swift_name("getFilteredTrack()")));
- (void)resetPrecision:(int32_t)precision count:(int32_t)count __attribute__((swift_name("reset(precision:count:)")));
- (void)filterLocationKt:(KalmanKtLocationKt *)locationKt __attribute__((swift_name("filter(locationKt:)")));
- (void)stop __attribute__((swift_name("stop()")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("GeoHashFilter.Companion")))
@interface KalmanKtGeoHashFilterCompanion : KotlinBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (readonly) double COORD_NOT_INITIALIZED;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KalmanFilter")))
@interface KalmanKtKalmanFilter : KotlinBase
- (instancetype)initWithMt:(double)mt processNoise:(double)processNoise __attribute__((swift_name("init(mt:processNoise:)"))) __attribute__((objc_designated_initializer));
- (void)resetPosition:(double)position velocity:(double)velocity noise:(double)noise __attribute__((swift_name("reset(position:velocity:noise:)")));
- (void)updatePosition:(double)position noise:(double)noise __attribute__((swift_name("update(position:noise:)")));
- (void)predictAcceleration:(double)acceleration __attribute__((swift_name("predict(acceleration:)")));
@property (readonly) double position;
@property (readonly) double velocity;
@property (readonly) double accuracy;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Coordinates")))
@interface KalmanKtCoordinates : KotlinBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)coordinates __attribute__((swift_name("init()")));
- (double)distanceBetweenLon1:(double)lon1 lat1:(double)lat1 lon2:(double)lon2 lat2:(double)lat2 __attribute__((swift_name("distanceBetween(lon1:lat1:lon2:lat2:)")));
- (double)longitudeToMetersLon:(double)lon __attribute__((swift_name("longitudeToMeters(lon:)")));
- (KalmanKtGeoPoint *)metersToGeoPointLonMeters:(double)lonMeters latMeters:(double)latMeters __attribute__((swift_name("metersToGeoPoint(lonMeters:latMeters:)")));
- (double)latitudeToMetersLat:(double)lat __attribute__((swift_name("latitudeToMeters(lat:)")));
- (double)calculateDistanceTrack:(KalmanKtKotlinArray * _Nullable)track __attribute__((swift_name("calculateDistance(track:)")));
@property (readonly) double EARTH_RADIUS;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("GeoHash")))
@interface KalmanKtGeoHash : KotlinBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)geoHash __attribute__((swift_name("init()")));
- (int64_t)u64EncodeLat:(double)lat lon:(double)lon prec:(int32_t)prec __attribute__((swift_name("u64Encode(lat:lon:prec:)")));
- (NSString *)geohashGeohash:(int64_t)geohash prec:(int32_t)prec __attribute__((swift_name("geohash(geohash:prec:)")));
@property (readonly) KalmanKtKotlinCharArray *base32Table;
@property (readonly) int32_t GEOHASH_MAX_PRECISION;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("BearingDistanceCache")))
@interface KalmanKtBearingDistanceCache : KotlinBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property double lat1;
@property double lon1;
@property double lat2;
@property double lon2;
@property float distance;
@property float initialBearing;
@property float finalBearing;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("GeoPoint")))
@interface KalmanKtGeoPoint : KotlinBase
- (instancetype)initWithLatitude:(double)latitude longitude:(double)longitude __attribute__((swift_name("init(latitude:longitude:)"))) __attribute__((objc_designated_initializer));
- (void)doCopyTarget:(KalmanKtGeoPoint *)target __attribute__((swift_name("doCopy(target:)")));
@property double latitude;
@property double longitude;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("LocationKt")))
@interface KalmanKtLocationKt : KotlinBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (double)getLatitude __attribute__((swift_name("getLatitude()")));
- (void)setLatitudeLatitude:(double)latitude __attribute__((swift_name("setLatitude(latitude:)")));
- (double)getLongitude __attribute__((swift_name("getLongitude()")));
- (void)setLongitudeLongitude:(double)longitude __attribute__((swift_name("setLongitude(longitude:)")));
- (double)getAltitude __attribute__((swift_name("getAltitude()")));
- (BOOL)hasAltitude __attribute__((swift_name("hasAltitude()")));
- (void)setAltitudeAltitude:(double)altitude __attribute__((swift_name("setAltitude(altitude:)")));
- (double)getSpeed __attribute__((swift_name("getSpeed()")));
- (BOOL)hasSpeed __attribute__((swift_name("hasSpeed()")));
- (void)setSpeedSpeed:(double)speed __attribute__((swift_name("setSpeed(speed:)")));
- (double)getBearing __attribute__((swift_name("getBearing()")));
- (BOOL)hasBearing __attribute__((swift_name("hasBearing()")));
- (void)setBearingBearing:(double)bearing __attribute__((swift_name("setBearing(bearing:)")));
- (double)getAccuracy __attribute__((swift_name("getAccuracy()")));
- (BOOL)hasAccuracy __attribute__((swift_name("hasAccuracy()")));
- (void)setAccuracyAccuracy:(double)accuracy __attribute__((swift_name("setAccuracy(accuracy:)")));
- (int64_t)getTimestamp __attribute__((swift_name("getTimestamp()")));
- (void)setTimestampTimestamp:(int64_t)timestamp __attribute__((swift_name("setTimestamp(timestamp:)")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("LocationKt.Companion")))
@interface KalmanKtLocationKtCompanion : KotlinBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
- (void)distanceBetweenStart:(KalmanKtGeoPoint *)start end:(KalmanKtGeoPoint *)end results:(KalmanKtKotlinFloatArray * _Nullable)results __attribute__((swift_name("distanceBetween(start:end:results:)")));
@property (readonly) int32_t HAS_ALTITUDE_MASK;
@property (readonly) int32_t HAS_SPEED_MASK;
@property (readonly) int32_t HAS_BEARING_MASK;
@property (readonly) int32_t HAS_HORIZONTAL_ACCURACY_MASK;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Vector")))
@interface KalmanKtVector : KotlinBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (void)updateVector:(KalmanKtVector *)vector __attribute__((swift_name("update(vector:)")));
- (void)updateA:(double)a b:(double)b c:(double)c d:(double)d __attribute__((swift_name("update(a:b:c:d:)")));
@property double a;
@property double b;
@property double c;
@property double d;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Vector.Companion")))
@interface KalmanKtVectorCompanion : KotlinBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
- (void)doCopy:(KalmanKtVector *)receiver target:(KalmanKtVector *)target __attribute__((swift_name("doCopy(_:target:)")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("MathExtKt")))
@interface KalmanKtMathExtKt : KotlinBase
+ (double)toRadians:(double)receiver __attribute__((swift_name("toRadians(_:)")));
+ (double)toDegrees:(double)receiver __attribute__((swift_name("toDegrees(_:)")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinArray")))
@interface KalmanKtKotlinArray : KotlinBase
+ (instancetype)arrayWithSize:(int32_t)size init:(id _Nullable (^)(KalmanKtInt *))init __attribute__((swift_name("init(size:init:)")));
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (id _Nullable)getIndex:(int32_t)index __attribute__((swift_name("get(index:)")));
- (id<KalmanKtKotlinIterator>)iterator __attribute__((swift_name("iterator()")));
- (void)setIndex:(int32_t)index value:(id _Nullable)value __attribute__((swift_name("set(index:value:)")));
@property (readonly) int32_t size;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinCharArray")))
@interface KalmanKtKotlinCharArray : KotlinBase
+ (instancetype)arrayWithSize:(int32_t)size __attribute__((swift_name("init(size:)")));
+ (instancetype)arrayWithSize:(int32_t)size init:(id (^)(KalmanKtInt *))init __attribute__((swift_name("init(size:init:)")));
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (unichar)getIndex:(int32_t)index __attribute__((swift_name("get(index:)")));
- (KalmanKtKotlinCharIterator *)iterator __attribute__((swift_name("iterator()")));
- (void)setIndex:(int32_t)index value:(unichar)value __attribute__((swift_name("set(index:value:)")));
@property (readonly) int32_t size;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinFloatArray")))
@interface KalmanKtKotlinFloatArray : KotlinBase
+ (instancetype)arrayWithSize:(int32_t)size __attribute__((swift_name("init(size:)")));
+ (instancetype)arrayWithSize:(int32_t)size init:(KalmanKtFloat *(^)(KalmanKtInt *))init __attribute__((swift_name("init(size:init:)")));
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (float)getIndex:(int32_t)index __attribute__((swift_name("get(index:)")));
- (KalmanKtKotlinFloatIterator *)iterator __attribute__((swift_name("iterator()")));
- (void)setIndex:(int32_t)index value:(float)value __attribute__((swift_name("set(index:value:)")));
@property (readonly) int32_t size;
@end;

__attribute__((swift_name("KotlinIterator")))
@protocol KalmanKtKotlinIterator
@required
- (BOOL)hasNext __attribute__((swift_name("hasNext()")));
- (id _Nullable)next __attribute__((swift_name("next()")));
@end;

__attribute__((swift_name("KotlinCharIterator")))
@interface KalmanKtKotlinCharIterator : KotlinBase <KalmanKtKotlinIterator>
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (id)next __attribute__((swift_name("next()")));
- (unichar)nextChar __attribute__((swift_name("nextChar()")));
@end;

__attribute__((swift_name("KotlinFloatIterator")))
@interface KalmanKtKotlinFloatIterator : KotlinBase <KalmanKtKotlinIterator>
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (KalmanKtFloat *)next __attribute__((swift_name("next()")));
- (float)nextFloat __attribute__((swift_name("nextFloat()")));
@end;

NS_ASSUME_NONNULL_END
