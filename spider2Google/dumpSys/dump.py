import os
import subprocess


fileFolder = "dumped/"
def dump(name):
    filename = name.replace('/','_[divide]_')
    with open(fileFolder+filename, mode='wb') as f:
        proc = subprocess.Popen('adb shell su -c "dumpsys ' + name + '" root', stdin=subprocess.PIPE, stdout=f)
        proc.communicate(bytes('dumpsys ' + name+'\n',encoding='utf-8'))
        proc.kill()
        f.close()
    return fileFolder+filename


with open(fileFolder+'services_list.txt', mode='wb') as f:
    proc = subprocess.Popen('adb shell su', stdin=subprocess.PIPE, stdout=f)
    proc.communicate(b'service list\n')
    proc.kill()
    f.close()
with open(fileFolder+'services_list.txt', mode='r') as f:
    for l in f.read().split('\n')[1:]:
        l = l.split('\t')
        if l == ['']:
            continue
        name = l[1].split(':')[0]
        deleteFlag = False
        dumpedFile = dump(name)
        with open(dumpedFile,mode='rb') as fDumped:
            readlen = len(fDumped.read())
            # print(dumpedFile + ' ' + str(readlen))
            if readlen == 0:
                deleteFlag = True
            fDumped.close()
        if deleteFlag:
            os.remove(dumpedFile)

# dumps =