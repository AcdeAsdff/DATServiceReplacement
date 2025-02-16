#see HookLocationManager
from math import sin,cos,pi
#不要用百度或者别的国内坐标
#location:[lat,lit,altitude,phoneBearing]
ls = [
]
alti = 120.844535
start =[114.334403, 30.372249,alti,0.0]
end =[114.334403, 30.373188,alti,0.0]
offset = (end[1] - start[1])/10
for i in range(11):
    ls.append([start[0],start[1]+offset*i,alti,1.])

start =[114.334403, 30.373188,alti,0.0]
end = [114.333493,30.373188,alti,181.]
radi = start[0] - end[0]
for i in range(11):
    ls.append([start[0] + radi * cos(i*pi/(10)),start[1] + radi * sin(i*pi/(10)),alti,1.+i*180./(10)])

start =[114.334493, 30.373188,alti,181.]
end = [114.333493,30.372249,alti,181.]
offset = -(end[1] - start[1])/10

for i in range(11):
    ls.append([end[0],end[1]+offset*i,alti,181.])

start = [114.333493, 30.372249,alti,181.]
end = [114.334403, 30.372249,alti,0.0]
for i in range(11):
    ls.append([end[0] + radi * cos((i * pi / 10 )+ pi),
               start[1] + radi * sin((i * pi / 10) + pi),
               alti, 181. + i * 180. / 10])

for i in ls:
    if i[3] > 360:
        i[3] -= 360
    # print("addLocation(%f,%f,%f,%ff);"% tuple(i))
    print("""{sizeof(struct LocationStruct) ,
        0xffff,
        0,// uint64_t timestamp;      // UTC timestamp for location fix, milliseconds since January 1, 1970
        %f,//double latitude,         // in degrees
        %f,//double longitude,        // in degrees
        %f,//double altitude,         // in meters above the WGS 84 reference ellipsoid
        0,//float speed,             // in meters per second
        %f,//float bearing,           // in degrees; range [0, 360)
        1.,//float accuracy,          // in meters
        1.,//float verticalAccuracy,  // in meters
        0.2,//float speedAccuracy,     // in meters/second
        0.2,//float bearingAccuracy,   // in degrees (0 to 359.999)
        0,//float conformityIndex,   // in range [0, 1]
        0xffff,
        0xffff,
        0,//uint64_t elapsedRealTime,    // in ns
        0,//uint64_t elapsedRealTimeUnc // in ns
        },"""%(i[0],i[1],i[2],i[3])
          )
print(len(ls))