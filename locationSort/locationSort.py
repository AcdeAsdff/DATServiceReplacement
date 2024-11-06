#see HookLocationManager
from math import sin,cos,pi

#location:[lat,lit,altitude,phoneBearing]
ls = [
]
#start :[114.340964, 30.378593,120.844535,0.0]
#start :[114.340964, 30.379515,120.844535,0.0]
offset = (30.379515 - 30.378593)/10
for i in range(11):
    ls.append([114.340964,30.378593+offset*i,120.844535,1.])

#start :[114.340964, 30.379515, 120.844535, 0.0]
#end :[114.34016,30.379515,120.844535,181.]
radi = 114.340964 - 114.34016
for i in range(11):
    ls.append([114.340964 + radi * cos(i*pi/(10)),30.379515 + radi * sin(i*pi/(10)),120.844535,1.+i*180./(10)])

#start :[114.340964, 30.378593,120.844535,0.0]
offset = -(30.379515 - 30.378593)/10
for i in range(11):
    ls.append([114.34016,30.379515+offset*i,120.844535,181.])

for i in range(11):
    ls.append([114.34016 + radi * cos((i * pi / 10 )+ pi),
               30.378593 + radi * sin((i * pi / 10) + pi),
               120.844535, 181. + i * 180. / 10])

for i in ls:
    if i[3] > 360:
        i[3] -= 360
    print("addLocation(%f,%f,%f,%ff);"% tuple(i))