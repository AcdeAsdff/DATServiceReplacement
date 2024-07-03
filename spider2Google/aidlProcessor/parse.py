import os
import javalang


def getMethodHookFromTypeName(typeName):
    if typeName == 'int':
        return "0"
    if typeName == 'void' or typeName is None:
        return "null"
    if typeName == 'boolean':
        return "true"
    else:
        return typeName



def parsingMethod(method):
    # outStr = method.name + '('
    argsLength = len(method.parameters)
    methodHookName = "null"
    methodReturnTypeName = 'void'
    if not method.return_type is None:
        methodReturnTypeName = method.return_type.name
        methodHookName = getMethodHookFromTypeName(methodReturnTypeName)
        if method.return_type.name == methodHookName:
            print("//",end='')

    print('    hookAllMethodsWithCache_Auto(hookClass,"' + method.name + '",' + methodHookName + ');')

    # for parament in method.parameters:
    #     outStr += parament.type.name + " " + parament.name + ","
    #
    # if len(method.parameters) != 0:
    #     outStr = outStr[:-1] + ')'
    # else:
    #     outStr += ')'
    # if (method.return_type == None):
    #     print("void " + outStr)
    # else:
    #     print(str(method.return_type.name) + " " + outStr)


def executePath(path):
    for p in os.listdir(path):
        p = path + "/" + p
        if os.path.isdir(p):
            executePath(p)
        elif os.path.isfile(p):
            if p.endswith('.py'):
                continue
            f = open(p, mode='r')
            s = f.read()
            s = s.replace("oneway ", '').replace("in ", '').replace("const ", "final ").replace("parcelable ", 'class ')
            # print(s)
            parsed = javalang.parse.parse(s)
            print("public static void hook" + str(parsed.types[0].name) + "(Class<?> hookClass){")
            for bodyItem in parsed.types[0].body:
                if isinstance(bodyItem, javalang.tree.MethodDeclaration):
                    parsingMethod(bodyItem)
            # print(f.read())
            print("}")
            f.close()

if __name__ == "__main__":
    executePath('')
