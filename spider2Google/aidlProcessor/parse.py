import os
import javalang
from javalang.tree import TypeArgument, ReferenceType, BasicType, MethodDeclaration

lazyMode = True


def getMethodHookFromTypeName(typeName):
    if typeName == 'int':
        return "0"
    if typeName == 'void' or typeName is None:
        return "null"
    if typeName == 'boolean':
        return "true"
    else:
        return typeName


def switchResultFromBasicType(name):
    if name == 'boolean':
        return 'true'
    if name == 'int':
        return '0'
    if name == 'long':
        return '0L'
    if name == 'short':
        return '((short) 0)'
    if name == 'float':
        return '0.f'
    if name == 'double':
        return '0.'
    if name == 'char':
        return "''"


def parsingMethod(method):
    # outStr = method.name + '('
    argsLength = len(method.parameters)
    methodHookName = "null"
    methodReturnTypeName = 'void'
    methodReturnType = method.return_type
    sysChecker = getSysChecker(method)
    # print(method)
    addDivide = False
    if methodReturnType is not None:
        methodReturnTypeName = methodReturnType.name
        if isinstance(methodReturnType, BasicType) and methodReturnType.dimensions == []:
            methodHookName = switchResultFromBasicType(methodReturnType.name)
        else:
            methodHookName = parseTypeString(methodReturnType)

        if not isinstance(methodReturnType, BasicType):
            addDivide = True

    if lazyMode:
        if methodHookName == 'byte[]':
            methodHookName = "EmptyArrays.EMPTY_BYTE_ARRAY"
            addDivide = False
        elif methodHookName == 'int[]':
            methodHookName = "EmptyArrays.EMPTY_INT_ARRAY"
            addDivide = False
        elif methodHookName == 'String[]':
            methodHookName = "EmptyArrays.EMPTY_STRING_ARRAY"
            addDivide = False
        elif methodHookName == 'NetworkInfo[]':
            methodHookName = "FAKE_NETWORK_INFO_ARR"
            addDivide = False
        elif methodHookName.startswith("List<") or methodHookName == "List":
            methodHookName = "EMPTY_ARRAYLIST"
            addDivide = False
        elif methodHookName.startswith("Map<") or methodHookName == "Map":
            methodHookName = "EMPTY_HASHMAP"
            addDivide = False
        elif methodHookName == "ParceledListSlice":
            methodHookName = "EMPTY_PARCELED_LIST_SLICE"
            addDivide = False
        elif methodHookName == "NetworkInfo":
            methodHookName = "FAKE_NETWORK_INFO_INSTANCE"
            addDivide = False
    if addDivide:
        print("//", end='')
    print('        hookAllMethodsWithCache_Auto(hookClass,"' + method.name + '",' + methodHookName + '%s);' % sysChecker)

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


def parseTypeString(type):
    if isinstance(type, ReferenceType):
        result = parseReferenceTypeString(type)
        if type.sub_type is not None:
            result = result + '.' + parseTypeString(type.sub_type)
        return result
    elif isinstance(type, TypeArgument):
        return parseTypeArgumentString(type)
    elif isinstance(type, BasicType):
        return parseBasicTypeString(type)
    else:
        print(type)


def parseReferenceTypeString(refType):
    assert isinstance(refType, ReferenceType)
    # print(refType)
    stringBuilder = refType.name
    refTypeArgs = refType.arguments
    if refTypeArgs is not None:
        stringBuilder += '<'
        counter = 0
        for arg in refTypeArgs:
            if isinstance(arg, TypeArgument):
                stringBuilder += parseTypeArgumentString(arg)
            else:
                print(arg)
            if counter != len(refTypeArgs) - 1:
                stringBuilder += ','
            counter += 1
        stringBuilder += '>'
    stringBuilder += parseDimStringForType(refType)
    return stringBuilder


def parseTypeArgumentString(typeArgument):
    assert isinstance(typeArgument, TypeArgument)
    stringBuilder = ''
    # print(typeArgument)
    type = typeArgument.type
    stringBuilder += parseTypeString(type)
    return stringBuilder


def parseBasicTypeString(type):
    assert isinstance(type, BasicType)
    stringBuilder = type.name + parseDimStringForType(type)
    return stringBuilder


def parseDimStringForType(type):
    stringBuilder = ''
    if type.dimensions is not None:
        methodReturnTypeDims = type.dimensions
        assert isinstance(methodReturnTypeDims, list)
        for dim in methodReturnTypeDims:
            if dim is None:
                stringBuilder += '[]'
                continue
            print(dim)
    return stringBuilder


varName_pkgName = [
    'callingPkg',
    "callingPackage",
    "pkgName",
    "packageName",
    "callingPackageName",
]
varName_uid = ['uid']


def getSysChecker(method):
    assert isinstance(method, MethodDeclaration)
    counter = 0
    for para in method.parameters:
        t = para.type
        if isinstance(t, ReferenceType):
            if t.name == "String" and para.name in varName_pkgName:
                return ",getSystemChecker_PackageNameAt(%d)" % counter
            if para.name.lower() in varName_uid:
                return ",getSystemChecker_UidAt(%d)" % counter
        counter += 1
    return ''


if __name__ == "__main__":
    executePath('')
