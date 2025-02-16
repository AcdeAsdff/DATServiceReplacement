package com.linearity.datservicereplacement.Connectivity;

import static android.system.OsConstants.AF_INET;
import static com.linearity.datservicereplacement.PackageManager.PackageManagerUtils.isSystemApp;
import static com.linearity.datservicereplacement.StartHook.publicSeed;
import static com.linearity.utils.LoggerUtils.LoggerLog;

import android.net.ConnectivityManager;
import android.net.LinkAddress;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Binder;
import android.telephony.TelephonyManager;

import com.linearity.datservicereplacement.ReturnIfNonSys;
import com.linearity.utils.ExtendedRandom;
import com.linearity.utils.SimpleExecutor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;

import de.robv.android.xposed.XposedHelpers;

public class NetworkConstructUtils {
    public static final Class<?> INET_ADDRESS_HOLDER_CLASS = XposedHelpers.findClass("java.net.InetAddress$InetAddressHolder",InetAddress.class.getClassLoader());
    public static final Class<?> INET_6_ADDRESS_HOLDER_CLASS = XposedHelpers.findClass("java.net.Inet6Address$Inet6AddressHolder",InetAddress.class.getClassLoader());
    public static final NetworkInfo FAKE_NETWORK_INFO_INSTANCE = new NetworkInfo(ConnectivityManager.TYPE_MOBILE, TelephonyManager.NETWORK_TYPE_LTE,"mobile","mobile_ims");
    public static final NetworkInfo[] FAKE_NETWORK_INFO_ARR = new NetworkInfo[]{FAKE_NETWORK_INFO_INSTANCE};
    public static final Constructor<Network> NETWORK_CONSTRUCTOR = (Constructor<Network>) XposedHelpers.findConstructorExact(Network.class,int.class,boolean.class);
    public static final Constructor<LinkAddress> LINK_ADDRESS_CONSTRUCTOR = (Constructor<LinkAddress>) XposedHelpers.findConstructorExact(LinkAddress.class,String.class);
    public static final Constructor<?> INET_ADDRESS_HOLDER_CONSTRUCTOR = XposedHelpers.findConstructorExact(INET_ADDRESS_HOLDER_CLASS);
    public static final Constructor<Inet4Address> INET_4_ADDRESS_CONSTRUCTOR = (Constructor<Inet4Address>) XposedHelpers.findConstructorExact(Inet4Address.class,String.class,int.class);
//    public static final Constructor<?> INET_6_ADDRESS_HOLDER_CONSTRUCTOR = XposedHelpers.findConstructorExact(INET_6_ADDRESS_HOLDER_CLASS,byte[].class, int.class, boolean.class, NetworkInterface.class, boolean.class);
    public static final Constructor<Inet6Address> INET_6_ADDRESS_CONSTRUCTOR = (Constructor<Inet6Address>) XposedHelpers.findConstructorExact(Inet6Address.class);
    public static Field LinkProperties_mIfaceName = XposedHelpers.findField(LinkProperties.class,"mIfaceName");//String
    public static Field LinkProperties_mLinkAddresses = XposedHelpers.findField(LinkProperties.class,"mLinkAddresses");//ArrayList<LinkAddress>
    public static Field LinkProperties_mDnses = XposedHelpers.findField(LinkProperties.class,"mDnses");//ArrayList<InetAddress>
    public static Field LinkProperties_mPcscfs = XposedHelpers.findField(LinkProperties.class,"mPcscfs");//ArrayList<InetAddress>
    public static Field LinkProperties_mValidatedPrivateDnses = XposedHelpers.findField(LinkProperties.class,"mValidatedPrivateDnses");//ArrayList<InetAddress>
    public static Field LinkProperties_mUsePrivateDns = XposedHelpers.findField(LinkProperties.class,"mUsePrivateDns");//boolean
    public static Field LinkProperties_mPrivateDnsServerName = XposedHelpers.findField(LinkProperties.class,"mPrivateDnsServerName");//String
    public static Field LinkProperties_mDomains = XposedHelpers.findField(LinkProperties.class,"mDomains");//String
    public static Field LinkProperties_mRoutes = XposedHelpers.findField(LinkProperties.class,"mRoutes");//ArrayList<RouteInfo>
    public static Field LinkProperties_mDhcpServerAddress = XposedHelpers.findField(LinkProperties.class,"mDhcpServerAddress");//Inet4Address
    public static Field LinkProperties_mHttpProxy = XposedHelpers.findField(LinkProperties.class,"mHttpProxy");//ProxyInfo
    public static Field LinkProperties_mMtu = XposedHelpers.findField(LinkProperties.class,"mMtu");//int
    public static Field LinkProperties_mTcpBufferSizes = XposedHelpers.findField(LinkProperties.class,"mTcpBufferSizes");//String
    public static Field LinkProperties_mNat64Prefix = XposedHelpers.findField(LinkProperties.class,"mNat64Prefix");//IpPrefix
    public static Field LinkProperties_mWakeOnLanSupported = XposedHelpers.findField(LinkProperties.class,"mWakeOnLanSupported");//boolean
    public static Field LinkProperties_mCaptivePortalApiUrl = XposedHelpers.findField(LinkProperties.class,"mCaptivePortalApiUrl");//Uri
    public static Field LinkProperties_mCaptivePortalData = XposedHelpers.findField(LinkProperties.class,"mCaptivePortalData");//CaptivePortalData
    public static Field LinkProperties_mParcelSensitiveFields = XposedHelpers.findField(LinkProperties.class,"mParcelSensitiveFields");//boolean
    public static Field LinkProperties_mStackedLinks = XposedHelpers.findField(LinkProperties.class,"mStackedLinks");//Hashtable<String,LinkProperties>

    public static Field LinkAddress_address = XposedHelpers.findField(LinkAddress.class,"address");//InetAddress
    public static Field LinkAddress_prefixLength = XposedHelpers.findField(LinkAddress.class,"prefixLength");//int
    public static Field LinkAddress_flags = XposedHelpers.findField(LinkAddress.class,"flags");//int
    public static Field LinkAddress_scope = XposedHelpers.findField(LinkAddress.class,"scope");//int
    public static Field LinkAddress_deprecationTime = XposedHelpers.findField(LinkAddress.class,"deprecationTime");//long
    public static Field LinkAddress_expirationTime = XposedHelpers.findField(LinkAddress.class,"expirationTime");//long

    public static Field InetAddress_holder = XposedHelpers.findField(InetAddress.class,"holder");//InetAddressHolder
    public static Field InetAddress_canonicalHostName = XposedHelpers.findField(InetAddress.class,"canonicalHostName");//String

    public static Field InetAddressHolder_originalHostName = XposedHelpers.findField(INET_ADDRESS_HOLDER_CLASS,"originalHostName");//String
    public static Field InetAddressHolder_hostName = XposedHelpers.findField(INET_ADDRESS_HOLDER_CLASS,"hostName");//String
    public static Field InetAddressHolder_address = XposedHelpers.findField(INET_ADDRESS_HOLDER_CLASS,"address");//int
    public static Field InetAddressHolder_family = XposedHelpers.findField(INET_ADDRESS_HOLDER_CLASS,"family");//int
    public static Field Inet6Address_holder6 = XposedHelpers.findField(Inet6Address.class,"holder6");
    public static Field Inet6AddressHolder_ipaddress = XposedHelpers.findField(INET_6_ADDRESS_HOLDER_CLASS,"ipaddress");//byte[]
    public static Field Inet6AddressHolder_scope_id = XposedHelpers.findField(INET_6_ADDRESS_HOLDER_CLASS,"scope_id");//int
    public static Field Inet6AddressHolder_scope_id_set = XposedHelpers.findField(INET_6_ADDRESS_HOLDER_CLASS,"scope_id_set");//boolean
    public static Field Inet6AddressHolder_scope_ifname = XposedHelpers.findField(INET_6_ADDRESS_HOLDER_CLASS,"scope_ifname");//NetworkInterface
    public static Field Inet6AddressHolder_scope_ifname_set = XposedHelpers.findField(INET_6_ADDRESS_HOLDER_CLASS,"scope_ifname_set");//boolean
    public static Object/*InetAddressHolder*/ constructInet4AddressHolderByUIDAndSalt(int callingUID,long salt){
        try {
            ExtendedRandom extendedRandom = new ExtendedRandom(
                    ((long)callingUID)
                            ^ salt
                    &((long)"4".hashCode()) + publicSeed);
            Object result = INET_ADDRESS_HOLDER_CONSTRUCTOR.newInstance();
            {
                InetAddressHolder_originalHostName.set(result,/*String*/extendedRandom.nextPackageName("."));
                InetAddressHolder_hostName.set(result,/*String*/extendedRandom.nextPackageName("."));
                //32bit ipv4 addr
                int addrPart1 = 0;
                int addrPart2 = 0;
                int addrPart3 = 0;
                int addrPart4 = 0;
                //while(isBogonIp)
                while (addrPart1 == 0
                        || addrPart1 == 10
                        || addrPart1 == 127
                        || (addrPart1 == 172
                        && addrPart2 >= 16
                        && addrPart2 <= 31)
                        || (addrPart1 == 192
                        && addrPart2 == 168)
                        || (addrPart1 == 169
                        && addrPart2 == 254)
                        || (addrPart1 >= 224)
                        || (addrPart1 == 198
                        && (addrPart2 == 18 || addrPart2 == 19))
                        || (addrPart1 == 198
                        && addrPart2 == 51
                        && addrPart3 == 100)
                        || (addrPart1 == 203
                        && addrPart2 == 0
                        && addrPart3 == 113)
                ){
                    addrPart1 = extendedRandom.nextInt(224);
                    addrPart2 = extendedRandom.nextInt(255);
                    addrPart3 = extendedRandom.nextInt(255);
                    addrPart4 = extendedRandom.nextInt(255);
                }
                int addrv4 = addrPart1 << 24 + addrPart2 << 16 + addrPart3 << 8 + addrPart4;
                InetAddressHolder_address.set(result,/*int*/addrv4);
                InetAddressHolder_family.set(result,/*int*/AF_INET);
                return result;
            }
        }catch (Exception e){
            LoggerLog(e);
            return null;
        }
    }

    public static Inet4Address constructInet4AddressByUIDAndSalt(int callingUID,long salt){
        try {
            ExtendedRandom extendedRandom = new ExtendedRandom(callingUID * salt ^ (callingUID ^ salt ^ Inet4Address.class.getCanonicalName().hashCode()));
            Inet4Address result = INET_4_ADDRESS_CONSTRUCTOR.newInstance("127.0.0.1",39831);
            InetAddress_holder.set(result,/*InetAddressHolder*/constructInet4AddressHolderByUIDAndSalt(callingUID,salt));
            InetAddress_canonicalHostName.set(result,/*String*/extendedRandom.nextPackageName("."));
            return result;
        }catch (Exception e){
            LoggerLog(e);
            return null;
        }
    }
    public static Object/*Inet6AddressHolder*/constructInet6AddressHolderByUIDAndSalt(int callingUID,long salt,Object iNet6AddressHolder){
        try {

//            Inet6AddressHolder_ipaddress.set(iNet6AddressHolder,/*byte[]*/);
//            Inet6AddressHolder_scope_id.set(iNet6AddressHolder,/*int*/);
//            Inet6AddressHolder_scope_id_set.set(iNet6AddressHolder,/*boolean*/);
//            Inet6AddressHolder_scope_ifname.set(iNet6AddressHolder,/*NetworkInterface*/);
//            Inet6AddressHolder_scope_ifname_set.set(iNet6AddressHolder,/*boolean*/);
            
            return iNet6AddressHolder;
        }catch (Exception e){
            LoggerLog(e);
            return null;
        }
    }
    public static Inet6Address constructInet6AddressByUIDAndSalt(int callingUID,long salt){
        try {
            Inet6Address result = INET_6_ADDRESS_CONSTRUCTOR.newInstance();
            Inet6Address_holder6.set(result,/*Inet6AddressHolder*/constructInet6AddressHolderByUIDAndSalt(callingUID,salt,null));
            return result;
        }catch (Exception e){
            LoggerLog(e);
            return null;
        }
    }
    public static LinkAddress constructLinkAddressByUIDAndSalt(int callingUID,long salt){
        try {
            LinkAddress result = LINK_ADDRESS_CONSTRUCTOR.newInstance("127.0.0.1:39831");
//            LinkAddress_address.set(result,/*InetAddress*/);
//            LinkAddress_prefixLength.set(result,/*int*/);
//            LinkAddress_flags.set(result,/*int*/);
//            LinkAddress_scope.set(result,/*int*/);
//            LinkAddress_deprecationTime.set(result,/*long*/);
//            LinkAddress_expirationTime.set(result,/*long*/);
            return result;
        }catch (Exception e){
            LoggerLog(e);
            return null;
        }
    }
    public static LinkProperties constructLinkPropertiesByUID(int callingUID){
        LinkProperties result = new LinkProperties();
        try {
            ExtendedRandom extendedRandom = new ExtendedRandom(
                    ((publicSeed | callingUID) * publicSeed)
                            ^ (((long) callingUID) * LinkProperties.class.getCanonicalName().hashCode()));
            NetworkConstructUtils.LinkProperties_mIfaceName.set(result,/*String*/extendedRandom.pickFromArray(FAKE_NET_INTERFACE_NAME_ARRAY));
//            NetworkConstructUtils.LinkProperties_mLinkAddresses.set(result,/*ArrayList<LinkAddress>*/);
//            NetworkConstructUtils.LinkProperties_mDnses.set(result,/*ArrayList<InetAddress>*/);
//            NetworkConstructUtils.LinkProperties_mPcscfs.set(result,/*ArrayList<InetAddress>*/);
//            NetworkConstructUtils.LinkProperties_mValidatedPrivateDnses.set(result,/*ArrayList<InetAddress>*/);
//            NetworkConstructUtils.LinkProperties_mUsePrivateDns.set(result,/*boolean*/);
//            NetworkConstructUtils.LinkProperties_mPrivateDnsServerName.set(result,/*String*/);
//            NetworkConstructUtils.LinkProperties_mDomains.set(result,/*String*/);
//            NetworkConstructUtils.LinkProperties_mRoutes.set(result,/*ArrayList<RouteInfo>*/);
//            NetworkConstructUtils.LinkProperties_mDhcpServerAddress.set(result,/*Inet4Address*/);
//            NetworkConstructUtils.LinkProperties_mHttpProxy.set(result,/*ProxyInfo*/);
//            NetworkConstructUtils.LinkProperties_mMtu.set(result,/*int*/);
//            NetworkConstructUtils.LinkProperties_mTcpBufferSizes.set(result,/*String*/);
//            NetworkConstructUtils.LinkProperties_mNat64Prefix.set(result,/*IpPrefix*/);
//            NetworkConstructUtils.LinkProperties_mWakeOnLanSupported.set(result,/*boolean*/);
//            NetworkConstructUtils.LinkProperties_mCaptivePortalApiUrl.set(result,/*Uri*/);
//            NetworkConstructUtils.LinkProperties_mCaptivePortalData.set(result,/*CaptivePortalData*/);
//            NetworkConstructUtils.LinkProperties_mParcelSensitiveFields.set(result,/*boolean*/false);
//            NetworkConstructUtils.LinkProperties_mStackedLinks.set(result,/*Hashtable<String,LinkProperties>*/);
        }catch (Exception e){
            LoggerLog(e);
        }
        return result;
    }

    public static final SimpleExecutor returnNetworkByCallingUID = param -> {
        param.setResult(ReturnIfNonSys.constructNetworkByUID(Binder.getCallingUid()));
    };
    public static final SimpleExecutor returnNetworkArrayByCallingUID = param -> {
        param.setResult(new Network[]{ReturnIfNonSys.constructNetworkByUID(Binder.getCallingUid())});
    };



    public static final String[] FAKE_NET_INTERFACE_NAME_ARRAY = new String[]{"lo","bond0","rndis0","ip_vti0@NONE","ip6_vti0@NONE",
            "sit0@NONE","ip6tnl0@NONE","rmnet_ipa0","rmnet_mhi0","rmnet_data0@rmnet_mhi0",
            "r_rmnet_data0@rmnet_mhi0","rmnet_data0@rmnet_mhi1","r_rmnet_data0@rmnet_mhi1","rmnet_data0@rmnet_mhi2","r_rmnet_data0@rmnet_mhi2",
            "rmnet_data0@rmnet_mhi3","r_rmnet_data0@rmnet_mhi3","rmnet_data0@rmnet_mhi4","r_rmnet_data0@rmnet_mhi4","rmnet_data0@rmnet_mhi5",
            "r_rmnet_data0@rmnet_mhi5","rmnet_data0@rmnet_mhi6","r_rmnet_data0@rmnet_mhi6","rmnet_data0@rmnet_mhi7","r_rmnet_data0@rmnet_mhi7",
            "rmnet_data0@rmnet_mhi8","r_rmnet_data0@rmnet_mhi8","rmnet_data0@rmnet_mhi9","r_rmnet_data0@rmnet_mhi9","bond1",
            "rndis1","ip_vti1@NONE","ip6_vti1@NONE","sit1@NONE","ip6tnl1@NONE",
            "rmnet_ipa1","rmnet_mhi1","rmnet_data1@rmnet_mhi0","r_rmnet_data1@rmnet_mhi0","rmnet_data1@rmnet_mhi1",
            "r_rmnet_data1@rmnet_mhi1","rmnet_data1@rmnet_mhi2","r_rmnet_data1@rmnet_mhi2","rmnet_data1@rmnet_mhi3","r_rmnet_data1@rmnet_mhi3",
            "rmnet_data1@rmnet_mhi4","r_rmnet_data1@rmnet_mhi4","rmnet_data1@rmnet_mhi5","r_rmnet_data1@rmnet_mhi5","rmnet_data1@rmnet_mhi6",
            "r_rmnet_data1@rmnet_mhi6","rmnet_data1@rmnet_mhi7","r_rmnet_data1@rmnet_mhi7","rmnet_data1@rmnet_mhi8","r_rmnet_data1@rmnet_mhi8",
            "rmnet_data1@rmnet_mhi9","r_rmnet_data1@rmnet_mhi9","bond2","rndis2","ip_vti2@NONE",
            "ip6_vti2@NONE","sit2@NONE","ip6tnl2@NONE","rmnet_ipa2","rmnet_mhi2",
            "rmnet_data2@rmnet_mhi0","r_rmnet_data2@rmnet_mhi0","rmnet_data2@rmnet_mhi1","r_rmnet_data2@rmnet_mhi1","rmnet_data2@rmnet_mhi2",
            "r_rmnet_data2@rmnet_mhi2","rmnet_data2@rmnet_mhi3","r_rmnet_data2@rmnet_mhi3","rmnet_data2@rmnet_mhi4","r_rmnet_data2@rmnet_mhi4",
            "rmnet_data2@rmnet_mhi5","r_rmnet_data2@rmnet_mhi5","rmnet_data2@rmnet_mhi6","r_rmnet_data2@rmnet_mhi6","rmnet_data2@rmnet_mhi7",
            "r_rmnet_data2@rmnet_mhi7","rmnet_data2@rmnet_mhi8","r_rmnet_data2@rmnet_mhi8","rmnet_data2@rmnet_mhi9","r_rmnet_data2@rmnet_mhi9",
            "bond3","rndis3","ip_vti3@NONE","ip6_vti3@NONE","sit3@NONE",
            "ip6tnl3@NONE","rmnet_ipa3","rmnet_mhi3","rmnet_data3@rmnet_mhi0","r_rmnet_data3@rmnet_mhi0",
            "rmnet_data3@rmnet_mhi1","r_rmnet_data3@rmnet_mhi1","rmnet_data3@rmnet_mhi2","r_rmnet_data3@rmnet_mhi2","rmnet_data3@rmnet_mhi3",
            "r_rmnet_data3@rmnet_mhi3","rmnet_data3@rmnet_mhi4","r_rmnet_data3@rmnet_mhi4","rmnet_data3@rmnet_mhi5","r_rmnet_data3@rmnet_mhi5",
            "rmnet_data3@rmnet_mhi6","r_rmnet_data3@rmnet_mhi6","rmnet_data3@rmnet_mhi7","r_rmnet_data3@rmnet_mhi7","rmnet_data3@rmnet_mhi8",
            "r_rmnet_data3@rmnet_mhi8","rmnet_data3@rmnet_mhi9","r_rmnet_data3@rmnet_mhi9","bond4","rndis4",
            "ip_vti4@NONE","ip6_vti4@NONE","sit4@NONE","ip6tnl4@NONE","rmnet_ipa4",
            "rmnet_mhi4","rmnet_data4@rmnet_mhi0","r_rmnet_data4@rmnet_mhi0","rmnet_data4@rmnet_mhi1","r_rmnet_data4@rmnet_mhi1",
            "rmnet_data4@rmnet_mhi2","r_rmnet_data4@rmnet_mhi2","rmnet_data4@rmnet_mhi3","r_rmnet_data4@rmnet_mhi3","rmnet_data4@rmnet_mhi4",
            "r_rmnet_data4@rmnet_mhi4","rmnet_data4@rmnet_mhi5","r_rmnet_data4@rmnet_mhi5","rmnet_data4@rmnet_mhi6","r_rmnet_data4@rmnet_mhi6",
            "rmnet_data4@rmnet_mhi7","r_rmnet_data4@rmnet_mhi7","rmnet_data4@rmnet_mhi8","r_rmnet_data4@rmnet_mhi8","rmnet_data4@rmnet_mhi9",
            "r_rmnet_data4@rmnet_mhi9","bond5","rndis5","ip_vti5@NONE","ip6_vti5@NONE",
            "sit5@NONE","ip6tnl5@NONE","rmnet_ipa5","rmnet_mhi5","rmnet_data5@rmnet_mhi0",
            "r_rmnet_data5@rmnet_mhi0","rmnet_data5@rmnet_mhi1","r_rmnet_data5@rmnet_mhi1","rmnet_data5@rmnet_mhi2","r_rmnet_data5@rmnet_mhi2",
            "rmnet_data5@rmnet_mhi3","r_rmnet_data5@rmnet_mhi3","rmnet_data5@rmnet_mhi4","r_rmnet_data5@rmnet_mhi4","rmnet_data5@rmnet_mhi5",
            "r_rmnet_data5@rmnet_mhi5","rmnet_data5@rmnet_mhi6","r_rmnet_data5@rmnet_mhi6","rmnet_data5@rmnet_mhi7","r_rmnet_data5@rmnet_mhi7",
            "rmnet_data5@rmnet_mhi8","r_rmnet_data5@rmnet_mhi8","rmnet_data5@rmnet_mhi9","r_rmnet_data5@rmnet_mhi9","bond6",
            "rndis6","ip_vti6@NONE","ip6_vti6@NONE","sit6@NONE","ip6tnl6@NONE",
            "rmnet_ipa6","rmnet_mhi6","rmnet_data6@rmnet_mhi0","r_rmnet_data6@rmnet_mhi0","rmnet_data6@rmnet_mhi1",
            "r_rmnet_data6@rmnet_mhi1","rmnet_data6@rmnet_mhi2","r_rmnet_data6@rmnet_mhi2","rmnet_data6@rmnet_mhi3","r_rmnet_data6@rmnet_mhi3",
            "rmnet_data6@rmnet_mhi4","r_rmnet_data6@rmnet_mhi4","rmnet_data6@rmnet_mhi5","r_rmnet_data6@rmnet_mhi5","rmnet_data6@rmnet_mhi6",
            "r_rmnet_data6@rmnet_mhi6","rmnet_data6@rmnet_mhi7","r_rmnet_data6@rmnet_mhi7","rmnet_data6@rmnet_mhi8","r_rmnet_data6@rmnet_mhi8",
            "rmnet_data6@rmnet_mhi9","r_rmnet_data6@rmnet_mhi9","bond7","rndis7","ip_vti7@NONE",
            "ip6_vti7@NONE","sit7@NONE","ip6tnl7@NONE","rmnet_ipa7","rmnet_mhi7",
            "rmnet_data7@rmnet_mhi0","r_rmnet_data7@rmnet_mhi0","rmnet_data7@rmnet_mhi1","r_rmnet_data7@rmnet_mhi1","rmnet_data7@rmnet_mhi2",
            "r_rmnet_data7@rmnet_mhi2","rmnet_data7@rmnet_mhi3","r_rmnet_data7@rmnet_mhi3","rmnet_data7@rmnet_mhi4","r_rmnet_data7@rmnet_mhi4",
            "rmnet_data7@rmnet_mhi5","r_rmnet_data7@rmnet_mhi5","rmnet_data7@rmnet_mhi6","r_rmnet_data7@rmnet_mhi6","rmnet_data7@rmnet_mhi7",
            "r_rmnet_data7@rmnet_mhi7","rmnet_data7@rmnet_mhi8","r_rmnet_data7@rmnet_mhi8","rmnet_data7@rmnet_mhi9","r_rmnet_data7@rmnet_mhi9",
            "bond8","rndis8","ip_vti8@NONE","ip6_vti8@NONE","sit8@NONE",
            "ip6tnl8@NONE","rmnet_ipa8","rmnet_mhi8","rmnet_data8@rmnet_mhi0","r_rmnet_data8@rmnet_mhi0",
            "rmnet_data8@rmnet_mhi1","r_rmnet_data8@rmnet_mhi1","rmnet_data8@rmnet_mhi2","r_rmnet_data8@rmnet_mhi2","rmnet_data8@rmnet_mhi3",
            "r_rmnet_data8@rmnet_mhi3","rmnet_data8@rmnet_mhi4","r_rmnet_data8@rmnet_mhi4","rmnet_data8@rmnet_mhi5","r_rmnet_data8@rmnet_mhi5",
            "rmnet_data8@rmnet_mhi6","r_rmnet_data8@rmnet_mhi6","rmnet_data8@rmnet_mhi7","r_rmnet_data8@rmnet_mhi7","rmnet_data8@rmnet_mhi8",
            "r_rmnet_data8@rmnet_mhi8","rmnet_data8@rmnet_mhi9","r_rmnet_data8@rmnet_mhi9","bond9","rndis9",
            "ip_vti9@NONE","ip6_vti9@NONE","sit9@NONE","ip6tnl9@NONE","rmnet_ipa9",
            "rmnet_mhi9","rmnet_data9@rmnet_mhi0","r_rmnet_data9@rmnet_mhi0","rmnet_data9@rmnet_mhi1","r_rmnet_data9@rmnet_mhi1",
            "rmnet_data9@rmnet_mhi2","r_rmnet_data9@rmnet_mhi2","rmnet_data9@rmnet_mhi3","r_rmnet_data9@rmnet_mhi3","rmnet_data9@rmnet_mhi4",
            "r_rmnet_data9@rmnet_mhi4","rmnet_data9@rmnet_mhi5","r_rmnet_data9@rmnet_mhi5","rmnet_data9@rmnet_mhi6","r_rmnet_data9@rmnet_mhi6",
            "rmnet_data9@rmnet_mhi7","r_rmnet_data9@rmnet_mhi7","rmnet_data9@rmnet_mhi8","r_rmnet_data9@rmnet_mhi8","rmnet_data9@rmnet_mhi9",
            "r_rmnet_data9@rmnet_mhi9",};
}
