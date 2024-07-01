package com.linearity.utils.ListenerUtils;

import static com.linearity.utils.FakeClass.java.util.EmptyArrays.EMPTY_BYTE_ARRAY;
import static com.linearity.utils.LoggerUtils.LoggerLog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class ListenerByteArrayOutputStream extends ByteArrayOutputStream {
    public static final  ListenerByteArrayOutputStream INSTANCE = new ListenerByteArrayOutputStream();
    public static final boolean useLogger = false;
    public ListenerByteArrayOutputStream() {
    }

    public ListenerByteArrayOutputStream(int size) {
    }

    @Override
    public synchronized void write(int b) {
//        super.write(b);
        if (useLogger){
            LoggerLog("[linearity-ListenerByteArrayOutputStream]", String.valueOf(b));
        }
    }

    @Override
    public synchronized void write(@NonNull byte[] b, int off, int len) {
//        super.write(b, off, len);
        if (useLogger){
            LoggerLog("[linearity-ListenerByteArrayOutputStream]", new String(b));
        }
    }

    @Override
    public synchronized void writeTo(@NonNull OutputStream out) throws IOException {
//        super.writeTo(out);
        if (useLogger){
            LoggerLog("[linearity-ListenerByteArrayOutputStream]", "called writeTo");
        }
    }

    @Override
    public synchronized void reset() {
//        super.reset();
        if (useLogger){
            LoggerLog("[linearity-ListenerByteArrayOutputStream]", "called reset");
        }
    }

    @NonNull
    @Override
    public synchronized byte[] toByteArray() {
        if (useLogger){
            LoggerLog("[linearity-ListenerByteArrayOutputStream]", "called toByteArray");
        }
        return EMPTY_BYTE_ARRAY;
    }

    @Override
    public synchronized int size() {
        return 0;
    }

    @NonNull
    @Override
    public synchronized String toString() {
        return "";
    }

    @NonNull
    @Override
    public synchronized String toString(@NonNull String charsetName) throws UnsupportedEncodingException {
        return "";
    }

    @NonNull
    @Override
    public synchronized String toString(int hibyte) {
        return "";
    }

    @Override
    public void close() throws IOException {
    }

    @Override
    public void write(byte[] b) throws IOException {
        LoggerLog("[linearity-ListenerByteArrayOutputStream]",new String(b));
    }

    @Override
    public void flush() throws IOException {
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
        return INSTANCE;
    }

    @Override
    protected void finalize() throws Throwable {
    }
}
