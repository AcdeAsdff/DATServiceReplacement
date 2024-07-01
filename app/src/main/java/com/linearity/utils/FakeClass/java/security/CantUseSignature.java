package com.linearity.utils.FakeClass.java.security;

import static com.linearity.utils.FakeClass.java.util.EmptyArrays.EMPTY_BYTE_ARRAY;

import androidx.annotation.Nullable;

import com.linearity.utils.FakeClass.FakeReturnClasses.FakeReturnClassMap;

import java.nio.ByteBuffer;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.AlgorithmParameterSpec;

public class CantUseSignature extends Signature {
    public static final CantUseSignature INSTANCE = new CantUseSignature("");
    static {
        FakeReturnClassMap.registerInstance(CantUseSignature.class,INSTANCE);
    }
    protected CantUseSignature(String algorithm) {
        super("");
    }

    @Override
    public String toString() {
        return "Foolish!";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return this;
    }

    @Override
    protected void engineInitSign(PrivateKey privateKey, SecureRandom random) throws InvalidKeyException {
    }

    @Override
    protected void engineUpdate(ByteBuffer input) {
    }

    @Override
    protected int engineSign(byte[] outbuf, int offset, int len) throws SignatureException {
        return 0;
    }

    @Override
    protected boolean engineVerify(byte[] sigBytes, int offset, int length) throws SignatureException {
        return true;
    }

    @Override
    protected void engineSetParameter(AlgorithmParameterSpec params) throws InvalidAlgorithmParameterException {
    }

    @Override
    protected AlgorithmParameters engineGetParameters() {
        return CantUseAlgorithmParameters.INSTANCE;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return false;
    }

    @Override
    protected void finalize() throws Throwable {
    }

    @Override
    protected void engineInitVerify(PublicKey publicKey) throws InvalidKeyException {

    }

    @Override
    protected void engineInitSign(PrivateKey privateKey) throws InvalidKeyException {

    }

    @Override
    protected void engineUpdate(byte b) throws SignatureException {

    }

    @Override
    protected void engineUpdate(byte[] b, int off, int len) throws SignatureException {

    }

    @Override
    protected byte[] engineSign() throws SignatureException {
        return EMPTY_BYTE_ARRAY;
    }

    @Override
    protected boolean engineVerify(byte[] sigBytes) throws SignatureException {
        return false;
    }

    @Override
    protected void engineSetParameter(String param, Object value) throws InvalidParameterException {

    }

    @Override
    protected Object engineGetParameter(String param) throws InvalidParameterException {
        return null;
    }
}
