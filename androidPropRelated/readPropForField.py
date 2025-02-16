with open('dumpPropResult', mode='r', encoding='utf-8') as f:
    for l in f.readlines():
        if 'lmi' in l:
            print(l.replace('\n', '').split(':')[0][1:-1])
