import subprocess
import fieldLists
with open('dumpPropResult',mode='w+') as f:
    proc = subprocess.Popen("adb shell su", stdin=subprocess.PIPE,stdout=f)
    proc.stdin.write(b'getprop\n')
    proc.communicate()

with open('dumpPropResult',mode='r',encoding='utf-8') as f:
    for l in f.readlines():
        if 'lineage' in l:
            l = l.split(']: [')[0][1:]
            fieldLists.banned_field_list.add(l)
print(sorted(fieldLists.banned_field_list))