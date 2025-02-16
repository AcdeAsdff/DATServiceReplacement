package com.linearity.utils.FakeClass.java.security;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.linearity.utils.FakeClass.FakeReturnClasses.FakeReturnClassMap;

import java.security.AlgorithmParameters;
import java.security.AlgorithmParametersSpi;
import java.security.Provider;

public class CantUseAlgorithmParameters extends AlgorithmParameters {
    public static final CantUseAlgorithmParameters INSTANCE = new CantUseAlgorithmParameters(null,null,null);
    static {
        FakeReturnClassMap.registerInstance(CantUseAlgorithmParameters.class,INSTANCE);
    }
    protected CantUseAlgorithmParameters(AlgorithmParametersSpi paramSpi, Provider provider, String algorithm) {
        super(null,null,null);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return false;
    }

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return this;
    }

    @Override
    protected void finalize() throws Throwable {
    }
}
