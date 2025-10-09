package com.linearity.utils;

import java.util.Objects;

public class SimpleExecutorWithMode {
    public final int mode;
    public final SimpleExecutor simpleExecutor;

    public SimpleExecutorWithMode(int mode,SimpleExecutor simpleExecutor){
        this.mode = mode;
        this.simpleExecutor = simpleExecutor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleExecutorWithMode that = (SimpleExecutorWithMode) o;
        return mode == that.mode && Objects.equals(simpleExecutor, that.simpleExecutor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mode, simpleExecutor);
    }
}
