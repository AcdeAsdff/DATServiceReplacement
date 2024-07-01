package com.linearity.utils;

public class SimpleExecutorWithMode {
    public final int mode;
    public final SimpleExecutor simpleExecutor;

    public SimpleExecutorWithMode(int mode,SimpleExecutor simpleExecutor){
        this.mode = mode;
        this.simpleExecutor = simpleExecutor;
    }
}
