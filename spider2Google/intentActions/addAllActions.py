import os
nameToAdd = 'BLACKLIST_INTENT_ACTION_SET'
allSet = set()
toWrite = open('result.txt', mode='w+')
for fName in os.listdir('allActions'):
    f = open('allActions/' + fName, mode='r')
    for action in f.read().split('\n'):
        if action == '':
            continue
        toWriteStr = nameToAdd + '.add("%s");\n' % action
        if not toWriteStr in allSet:
            allSet.add(toWriteStr)
            toWrite.write(toWriteStr)
        if not action.startswith("android.intent.action."):
            action = action.split('.')[-1]
            action = "android.intent.action." + action
            toWriteStr = nameToAdd + '.add("%s");\n' % action
            if not toWriteStr in allSet:
                allSet.add(toWriteStr)
                toWrite.write(toWriteStr)
    f.close()

toWrite.close()
