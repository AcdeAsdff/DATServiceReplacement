Remake DeviceAddressTweaker in a (android) Service way so that apps (in China) won't be able to detect xposed.


[dat_selinux](https://github.com/AcdeAsdff/dat_selinux_and_more) is also needed


-------------------


需要[dat_selinux](https://github.com/AcdeAsdff/dat_selinux_and_more)(magisk模块)
在system_server层重置DeviceAddressTweaker(已废弃项目，因为要注入客户端，会被不同厂商的许多检测方法针对，避免这些检测方法需要极大的工作量。)以躲避Xposed检测
随机内容主要根据UID生成随机种子进行随机

- 非系统应用得到的蓝牙返回值(随机)
- 非系统应用得到的剪贴板返回值(随机)
- 非系统应用得到的app列表(只看得到微信支付宝)
- 非系统应用得到的AppOps信息(MODE_ALLOWED和空ArrayList)
- 非系统应用得到的应用权限（检查权限时总是允许，但对于事实上拒绝的权限用起来照样被拒绝）
- 非系统应用得到的闹钟信息(null)
- 非系统应用读取的剪贴板信息(随机)
- [待测试]非系统应用得到的WIFI信息(随机)
- [待测试]非系统应用得到的电池状态(随机)
- [待测试]非系统应用得到的输入法信息(搜狗输入法)
- 还有一大堆我忘记写了

(我不知道怎么写kpm改掉mediadrm的mac地址，写出来kpm根本没作用)
(↑后续：dat_zygisk用ptrace解决了这一问题,我也放在git了，以及这其实是zygisk-next的bug)

(不常更新，学校课多而且没用，还有点难逃)



-------------------


对于搞逆向的人来说，这在原理上是很容易的事情，因为Xposed是现成的。
就是非常累，因为AOSP实在是太多了。
我到现在也没拦住system_server给app所有的Configuration。
不过隔壁dat_zygisk去改mediadrm应该鲜有人去做，可能搞机的人不喜欢藏设备指纹吧。


-------------------
一般来说，对system_server中方法的改造(这里特别指hook)分成两部分： 判断调用者(我们只关心是不是系统或者应用白名单在调用)、修改返回值(或者别的操作)

判断调用者大同小异，修改返回值很多时候也一样。
在经过大量重复劳动之后，我们能够写出下面的玩意。

```java
if (systemAppChecker.checkSystemApp(param)){return;}
simpleExecutorWithMode.simpleExecutor.execute(param);
```

其中systemAppChecker检查调用者，simpleExecutor进行后续操作。
systemAppChecker不一样再另外塞，simpleExecutorWithMode同理。
只要实现systemAppChecker和simpleExecutorWithMode即可，
不用把二者合在一起导致大量代码重复(这二者任何一个都可以重复，比如许多相同的返回null和相同的根据uid检查应用)。


不妨考察如下代码，我们对于特定classloader的某一class( com.android.server.clipboard.ClipboardService$ClipboardImpl )执行如下操作:
```java
hookAllMethodsWithCache_Auto(hookClass,"clearPrimaryClip",null);
```
于是当非系统app（根据uid判断）试图清除剪贴板（调用clearPrimaryClip方法）时，会在方法执行前返回null，不执行原方法。于是剪贴板其实没用被清除。
这么封装有个好处--当我要返回多个null时，我只要创建一个返回null的匿名类
(在这样封装Xposed api之前，需要对每个需要返回null的方法都写一个XC_MethodHook,代码非常冗余,其它常量同理)。


作用于android.os.ServiceManagerProxy的代码
```java
hookAllMethodsWithCache_Auto(hookClass,
                             "addService",
                             onServicePublish,
                             com.linearity.datservicereplacement.ReturnIfNonSys.noSystemChecker);
```
会在任意Service被加入时执行onServicePublish(一个com.linearity.utils.SimpleExecutor的实现)的execute方法，
noSystemChecker把一切app当作非系统应用处理--它总返回false。