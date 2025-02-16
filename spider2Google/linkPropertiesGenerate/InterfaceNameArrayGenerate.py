result = ['lo']


for i in range(10):
    result.append('bond%d'%i)
    result.append('rndis%d'%i)
    result.append('ip_vti%d@NONE'%i)
    result.append('ip6_vti%d@NONE'%i)
    result.append('sit%d@NONE'%i)
    result.append('ip6tnl%d@NONE'%i)
    result.append('rmnet_ipa%d'%i)
    result.append('rmnet_mhi%d'%i)
    for j in range(10):
        result.append('rmnet_data%d@rmnet_mhi%d'%(i,j))
        result.append('r_rmnet_data%d@rmnet_mhi%d'%(i,j))

print('{',end='')
counter = 0
for i in result:
    print('"%s"'%i,end='')
    print(',',end='')
    counter += 1
    if counter % 5 == 0:
        print()
print('}')