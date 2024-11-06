Remake DeviceAddressTweaker in a (android) Service way so that apps (in China) won't be able to detect xposed.


-------------------


在system_server层重置DeviceAddressTweaker以躲避Xposed检测

- 非系统应用得到的蓝牙返回值(随机，对相同包名的结果相同)
- 非系统应用得到的剪贴板返回值(随机，对相同包名的结果相同)
- 非系统应用得到的app列表(只有微信支付宝)
- [待测试]非系统应用得到的电池状态(随机，对相同包名的结果相同)
- [待测试]非系统应用得到的输入法信息(搜狗输入法)

(我不知道怎么写kpm改掉mediadrm的mac地址，写出来kpm根本没作用)

(我太懒了，基本上没怎么更新)