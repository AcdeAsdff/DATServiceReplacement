a = """1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue state UNKNOWN group default qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
    inet 127.0.0.1/8 scope host lo
       valid_lft forever preferred_lft forever
    inet6 ::1/128 scope host
       valid_lft forever preferred_lft forever
2: bond0: <BROADCAST,MULTICAST,MASTER> mtu 1500 qdisc noop state DOWN group default qlen 1000
    link/ether c6:16:c7:12:3a:d9 brd ff:ff:ff:ff:ff:ff
3: dummy0: <BROADCAST,NOARP,UP,LOWER_UP> mtu 1500 qdisc noqueue state UNKNOWN group default qlen 1000
    link/ether de:a6:3f:d0:a8:b5 brd ff:ff:ff:ff:ff:ff
    inet6 fe80::dca6:3fff:fed0:a8b5/64 scope link
       valid_lft forever preferred_lft forever
4: ip_vti0@NONE: <NOARP> mtu 1480 qdisc noop state DOWN group default qlen 1000
    link/ipip 0.0.0.0 brd 0.0.0.0
5: ip6_vti0@NONE: <NOARP> mtu 1364 qdisc noop state DOWN group default qlen 1000
    link/tunnel6 :: brd ::
6: sit0@NONE: <NOARP> mtu 1480 qdisc noop state DOWN group default qlen 1000
    link/sit 0.0.0.0 brd 0.0.0.0
7: ip6tnl0@NONE: <NOARP> mtu 1452 qdisc noop state DOWN group default qlen 1000
    link/tunnel6 :: brd ::
8: rmnet_ipa0: <> mtu 9216 qdisc noop state DOWN group default qlen 1000
    link/[519]
10: rmnet_mhi0: <UP,LOWER_UP> mtu 65535 qdisc pfifo_fast state UNKNOWN group default qlen 1000
    link/[519]
11: rmnet_data0@rmnet_mhi0: <UP,LOWER_UP> mtu 1500 qdisc mq state UNKNOWN group default qlen 1000
    link/[519]
    inet6 fe80::767c:132d:c0f1:2866/64 scope link
       valid_lft forever preferred_lft forever
12: rmnet_data1@rmnet_mhi0: <> mtu 1432 qdisc mq state DOWN group default qlen 1000
    link/[519]
13: rmnet_data2@rmnet_mhi0: <UP,LOWER_UP> mtu 1432 qdisc mq state UNKNOWN group default qlen 1000
    link/[519]
    inet6 2408:854f:1550:a306:a96a:f22a:a0e8:1285/64 scope global dynamic mngtmpaddr
       valid_lft forever preferred_lft forever
    inet6 fe80::a96a:f22a:a0e8:1285/64 scope link
       valid_lft forever preferred_lft forever
14: rmnet_data3@rmnet_mhi0: <UP,LOWER_UP> mtu 1432 qdisc mq state UNKNOWN group default qlen 1000
    link/[519]
    inet 10.105.94.227/29 scope global rmnet_data3
       valid_lft forever preferred_lft forever
    inet6 2408:844f:1521:f6ef:3d5b:39cc:3c95:f54b/64 scope global dynamic mngtmpaddr
       valid_lft forever preferred_lft forever
    inet6 fe80::3d5b:39cc:3c95:f54b/64 scope link
       valid_lft forever preferred_lft forever
15: rmnet_data4@rmnet_mhi0: <> mtu 1500 qdisc noop state DOWN group default qlen 1000
    link/[519]
16: rmnet_data5@rmnet_mhi0: <> mtu 1500 qdisc noop state DOWN group default qlen 1000
    link/[519]
17: r_rmnet_data0@rmnet_mhi0: <> mtu 1500 qdisc noop state DOWN group default qlen 1000
    link/[519]
18: r_rmnet_data1@rmnet_mhi0: <> mtu 1500 qdisc noop state DOWN group default qlen 1000
    link/[519]
19: r_rmnet_data2@rmnet_mhi0: <> mtu 1500 qdisc noop state DOWN group default qlen 1000
    link/[519]
20: r_rmnet_data3@rmnet_mhi0: <> mtu 1500 qdisc noop state DOWN group default qlen 1000
    link/[519]
31: rndis0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP group default qlen 1000
    link/ether 72:a8:2a:27:b9:55 brd ff:ff:ff:ff:ff:ff
    inet 192.168.73.152/24 brd 192.168.73.255 scope global rndis0
       valid_lft forever preferred_lft forever
    inet6 2408:844f:1521:f6ef::cb/64 scope global
       valid_lft forever preferred_lft forever
    inet6 fe80::70a8:2aff:fe27:b955/64 scope link
       valid_lft forever preferred_lft forever"""
a = a.split('\n')

netnames = []
counter = 0
for l in a:
    if not l.startswith(' '):
        lParts = l.split(': ')
        counter = int(lParts[0])
        netnames.append(lParts[1])
print(netnames)