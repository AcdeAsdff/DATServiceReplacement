import os
import random
import subprocess

from fieldLists import *


def pickCharactersFromString(strToPick, times):
    sb = ''
    for i in range(times):
        sb += strToPick[random.randint(0, len(strToPick) - 1)]
    return sb


root = "D:\\Users\\MOKO\\AndroidStudioProjects\\dat_magisk\\"

for p in os.listdir(root + "resprop_presents"):
    os.remove(root + "resprop_presents\\" + p)
for p in os.listdir(root + "resprop_presents_after"):
    os.remove(root + "resprop_presents_after\\" + p)

f_ = open("preExecuted", mode="r", encoding='utf-8')

product_string_list = eval(f_.read())  # example

strs = "0123456789ABCDEF"
strsLower = "0123456789ABCDEF".lower()
chars = "zxcvbnmasdfghjklqwertyuiop" + ("zxcvbnmasdfghjklqwertyuiop".upper())
def write_to_file_A(file_):
    file_.write(
        "strs=(\"0\" \"1\" \"2\" \"3\" \"4\" \"5\" \"6\" \"7\" \"8\" \"9\" \"A\" \"B\" \"C\" \"D\" \"E\" \"F\")")
    file_.write("\n")
    file_.write(
        "strsLower=(\"0\" \"1\" \"2\" \"3\" \"4\" \"5\" \"6\" \"7\" \"8\" \"9\" \"a\" \"b\" \"c\" \"d\" \"e\" \"f\")")
    file_.write("\n")
    file_.write(
        "chars=(\"z\" \"x\" \"c\" \"v\" \"b\" \"n\" \"m\" \"a\" \"s\" \"d\" \"f\" \"g\" \"h\" \"j\" \"k\" \"l\" \"q\" "
        + "\"w\" \"e\" \"r\" \"t\" \"y\" \"u\" \"i\" \"o\" \"p\" \"Z\" \"X\" \"C\" \"V\" \"B\" \"N\" \"M\" \"A\" \"S\" "
        + "\"D\" \"F\" \"G\" \"H\" \"J\" \"K\" \"L\" \"Q\" \"W\" \"E\" \"R\" \"T\" \"Y\" \"U\" \"I\" \"O\" \"P\")")
    file_.write("\n")
    # if i == 0:
    #     file_.write("if (( ${rnd} == %d ))" % i)
    #     file_.write("\n")
    # else:
    #     file_.write("elif (( ${rnd} == %d ))" % i)
    #     file_.write("\n")
    # file_.write("then")
    # file_.write("\n")

    for f in product_string_field_list:
        file_.write("resetprop -n ")
        file_.write(f)
        file_.write(" ")
        file_.write("\"" + product_string_list[i][0] + "\"")
        file_.write("\n")

    for f in manufacturer_field_list:
        file_.write("resetprop -n ")
        file_.write(f)
        file_.write(" ")
        file_.write("\"" + product_string_list[i][3] + "\"")
        file_.write("\n")
    for f in brand_field_list:
        file_.write("resetprop -n ")
        file_.write(f)
        file_.write(" ")
        file_.write("\"" + product_string_list[i][4] + "\"")
        file_.write("\n")
    for f in fingerprint_field_list:
        file_.write("resetprop -n ")
        file_.write(f)
        file_.write(" ")
        file_.write("\"" + product_string_list[i][5] + "\"")
        file_.write("\n")
    for f in banned_field_list:
        file_.write("resetprop -n --delete ")
        file_.write(f)
        file_.write("\n")
    # file_.write("randomStr=" + "${strsLower[$(($RANDOM%16))]}" * 10)
    file_.write("randomStr=" + pickCharactersFromString(strsLower, 10))
    file_.write("\n")
    for f in incremental_list:
        if not "ro.build.version.incremental" in product_string_list[i][13].keys():
            file_.write("resetprop -n ")
            file_.write(f)
            file_.write(" ${randomStr}\n")
        else:
            file_.write("resetprop -n ")
            file_.write(f)
            file_.write(" " + product_string_list[i][13]["ro.build.version.incremental"] + "\n")
    for f in build_id_list:
        file_.write("resetprop -n " + f + " " + "\"" + product_string_list[i][6] + "\"")
        file_.write("\n")
    for f in build_date_list:
        file_.write("resetprop -n " + f + " " + "\"" + product_string_list[i][14] + "\"")
        file_.write("\n")
    for f in build_date_stamp_list:
        file_.write("resetprop -n " + f + " " + "\"" + product_string_list[i][15] + "\"")
        file_.write("\n")
    for f in build_tags_list:
        file_.write("resetprop -n " + f + " " + "\"" + product_string_list[i][17] + "\"")
        file_.write("\n")
    for f in build_type_list:
        file_.write("resetprop -n " + f + " " + "\"" + product_string_list[i][18] + "\"")
        file_.write("\n")
    for f in bootimage_name_list:
        file_.write("resetprop -n " + f + " " + product_string_list[i][19])
        file_.write("\n")
    file_.write("resetprop -n ro.build.flavor " + product_string_list[i][7])
    file_.write("\n")
    file_.write \
        ("randomStr=\"" + product_string_list[i][7] + " " + product_string_list[i][3] + " " + product_string_list[i][6]
         + " \"" + "${randomStr}")
    file_.write("\n")
    # print("echo ${randomStr}")
    file_.write("resetprop -n ro.build.display.id \"${randomStr}\"\n")

    file_.write("resetprop -n ro.soc.manufacturer " + product_string_list[i][9] + "\n")
    file_.write("resetprop -n ro.soc.model " + product_string_list[i][11] + "\n")
    file_.write("resetprop -n vendor.camera.aux.packagelist com.android.camera\n")
    file_.write(("randomStr=" + pickCharactersFromString(strsLower, 12)))
    file_.write("\n")
    file_.write("resetprop -n ro.build.host ${randomStr}\n")
    file_.write("randomStr=" + pickCharactersFromString(strsLower, 8))
    file_.write("\n")
    file_.write("resetprop -n ro.serialno ${randomStr}\n")
    file_.write("resetprop -n ro.boot.serialno ${randomStr}\n")
    file_.write("randomStr2=" + pickCharactersFromString(chars, 6))
    file_.write("\n")
    file_.write("resetprop -n persist.adb.wifi.guid \"adb-\"${randomStr}\"-\"${randomStr2}\n")
    file_.write("randomStr=" + pickCharactersFromString(strsLower, 13))
    file_.write("\n")
    file_.write("randomStr2=" + pickCharactersFromString(strsLower, 2))
    file_.write("\n")
    file_.write("resetprop -n persist.vendor.radio.imei ${randomStr}${randomStr2}\n")
    file_.write("resetprop -n persist.vendor.radio.imei1 ${randomStr}${randomStr2}\n")
    file_.write("randomStr2=" + pickCharactersFromString(strsLower, 2))
    file_.write("\n")
    file_.write("resetprop -n persist.vendor.radio.imei2 ${randomStr}${randomStr2}\n")
    # file_.write("resetprop -n ro.modversion " + product_string_list[i][12])
    file_.write("\n")
    file_.write("resetprop -n ro.product.locale zh-CN\n")
    file_.write("resetprop -n ro.product.locale.language zh\n")
    file_.write("resetprop -n ro.product.locale.region CN\n")
    file_.write("resetprop -n ro.hw.country cn\n")
    file_.write("resetprop -n ro.secure 0\n")
    file_.write("resetprop -n ro.debuggable 0\n")
    file_.write("resetprop -n ro.build.type user\n")
    for k in product_string_list[i][13].keys():
        if product_string_list[i][13][k] != "":
            file_.write("resetprop -n " + k + " " + "\"" + product_string_list[i][13][k] + "\"")
        else:
            file_.write("resetprop -n --delete " + k)
        file_.write("\n")
    file_.write("resetprop -n ro.vendor.build.security_patch " + product_string_list[i][16])
    file_.write("\n")

for i in range(len(product_string_list)):
    if product_string_list[i][8] == '8unset':
        product_string_list[i][8] = product_string_list[i][5].split('/')[2].split(':')[0]
    file_ = open(root + "resprop_presents\\resprop_" + str(i) + ".sh",
                 mode="w+", encoding='utf-8', newline='\n')
    write_to_file_A(file_)
    file_.close()
for i in range(len(product_string_list)):
    file_ = open(root + "resprop_presents_after\\resprop_"
                 + str(i) + ".sh", mode="w+", encoding='utf-8', newline='\n')
    file_.write("randomStr=" + pickCharactersFromString(strs, 60))  # * for str works well in python XD
    file_.write("\n")
    file_.write("resetprop -n vendor.camera.sensor.frontMain.fuseID sunn${randomStr}\n")
    file_.write("randomStr=" + pickCharactersFromString(strs, 60))
    file_.write("\n")
    file_.write("resetprop -n vendor.camera.sensor.rearMacro.fuseID sunn${randomStr}\n")
    file_.write("randomStr=" + pickCharactersFromString(strs, 60))
    file_.write("\n")
    file_.write("resetprop -n vendor.camera.sensor.rearMain.fuseID sunn${randomStr}\n")
    file_.write("randomStr=" + pickCharactersFromString(strs, 60))
    file_.write("\n")
    file_.write("resetprop -n vendor.camera.sensor.rearUltra.fuseID sunn${randomStr}\n")
    file_.write("randomStr=" + pickCharactersFromString(strs, 60))
    file_.write("\n")
    file_.write("resetprop -n vendor.camera.sensor.rearDepth.fuseID ofil${randomStr}\n")

    # 👇causes restart when post-fs-data(lineageos 21;android 14;MindTheGapp)
    file_.write(
        "resetprop -n ro.build.description \"" + product_string_list[i][7] + " " + product_string_list[i][2] + " "
        + product_string_list[i][6] + " " + product_string_list[i][17] + "\"\n")
    file_.write("resetprop -n gsm.version.ril-impl \"" + product_string_list[i][10] + "\"\n")
    file_.write("randomBaseBand=" + pickCharactersFromString(strs, 1) + "\".\""
                + pickCharactersFromString(strs, 1) + "\".\""
                + pickCharactersFromString(strs, 1)
                + pickCharactersFromString(strs, 1) +
                "\"-\"" + pickCharactersFromString(strs, 1) + "\".\""
                + pickCharactersFromString(strs, 1) + "\"-\""
                + pickCharactersFromString(strs, 4) + "\".\""
                + pickCharactersFromString(strs, 2) + "\"-\""
                + pickCharactersFromString(strs, 4) + "\"_\""
                + pickCharactersFromString(strs, 4) + "\"_\"" + pickCharactersFromString(strs, 10))
    file_.write("\n")
    file_.write("randomBaseBand=${randomBaseBand}\",\"${randomBaseBand}\n")
    file_.write("resetprop -n gsm.version.baseband ${randomBaseBand}\n")
    write_to_file_A(file_)
    # file_.write("resetprop -n sys.usb.config rndis,none\n")

    # # 👇causes crash and endless restart if set to wrong version
    # for f in sdkver_field_list:
    #     file_.write("resetprop -n ")
    #     file_.write(f)
    #     file_.write(" \"" + product_string_list[i][1] + "\"\n")
    #
    # for f in codename_field_list:
    #     file_.write("resetprop -n ")
    #     file_.write(f)
    #     file_.write(" \"" + product_string_list[i][2] + "\"\n")

    # 👇I have to do this after telecom service registered,see DATServiceReplacement(search:"telecom"(within \"))
    for f in device_field_list:
        file_.write("resetprop -p ")
        file_.write(f)
        file_.write(" \"" + product_string_list[i][8] + "\"\n")
MODDIR = '${0%/*}'
file_ = open(root + "post-fs-data.sh",
             mode="w+", encoding='utf-8', newline='\n')

randIntStr = "352"
# randIntStr = str(random.randint(0,len(product_string_list)))
# file_.write("rnd=$(($RANDOM" + "%" + "%d))"%len(product_string_list))
file_.write("rnd=$((" + randIntStr + "))")
file_.write("\n")
file_.write("MODDIR=%s\n" % MODDIR)
file_.write("touch ${PWD}/${rnd}\n")
file_.write("su -c sh ${PWD}\"/resprop_presents/resprop_\"${rnd}\".sh\" root\n")
file_.write("su -c sh ${PWD}\"/move_version.sh\" root\n")
file_.close()

file_ = open(root + "boot-completed.sh",
             mode="w+", encoding='utf-8', newline='\n')
file_.write(("MODDIR=%s\n" % MODDIR) + "rnd=" + randIntStr + '\n')
for i in range(len(product_string_list)):
    file_.write("if [ -e ${MODDIR}/%d ]" % i)
    file_.write("\nthen\n    rnd=%d\n    rm ${MODDIR}/%d" % (i, i))
    file_.write("\nfi\n")
file_.write("\n")
file_.write("su -c sh ${MODDIR}\"/resprop_presents/resprop_\"${rnd}\".sh\" root\n")
file_.write("su -c sh ${MODDIR}\"/resprop_presents_after/resprop_\"${rnd}\".sh\" root\n")
file_.close()

if os.path.exists('dat_magisk.zip'):
    os.remove('dat_magisk.zip')
proc = subprocess.Popen("cmd", stdin=subprocess.PIPE)
proc.stdin.write(b'7z a -tzip dat_magisk.zip D:/Users/MOKO/AndroidStudioProjects/dat_magisk/*\n')
proc.communicate()
subprocess.run('adb push dat_magisk.zip /sdcard/dat_magisk.zip')
subprocess.run('adb push D:/Users/MOKO/AndroidStudioProjects/dat_magisk /data/adb/modules/')