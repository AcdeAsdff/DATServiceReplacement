package com.linearity.utils;

import static com.linearity.utils.FakeClass.java.util.EmptyArrays.EMPTY_STACKTRACE_ELEMENT_ARRAY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModifyThrowable {

    public static void cleanStackTrace(Throwable throwable){
        for (Throwable others : throwable.getSuppressed()) {
            cleanStackTrace(others);
        }
        List<StackTraceElement> elementList = new ArrayList<>(throwable.getStackTrace().length);

        Arrays.stream(throwable.getStackTrace()).forEach(
                element -> {
                    String elementString = element.toString().toLowerCase();
                    if (elementString.contains("lineage")
                            || elementString.contains("xposed")
                            || elementString.contains("lsphooker_")
                            || elementString.contains("j.callback")
                            || elementString.contains("lsposed")
                            || elementString.contains("linearity")){
                        return;
                    }
                    elementList.add(element);
                }
        );
        throwable.setStackTrace(elementList.toArray(EMPTY_STACKTRACE_ELEMENT_ARRAY));
    }
}
