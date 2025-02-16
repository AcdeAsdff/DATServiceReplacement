with open("PNP_ID_Registry.csv",mode='r',encoding='utf-8') as f:
    counter = 0
    for l in f.read().split('\n')[1:]:
        print('"%s"'%l.split(',')[-2],end=',')
        counter += 1
        if counter % 10 == 0:
            counter = 0
            print()
    f.close()