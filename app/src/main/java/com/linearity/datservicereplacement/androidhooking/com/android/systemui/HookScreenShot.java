package com.linearity.datservicereplacement.androidhooking.com.android.systemui;

import static android.os.Environment.DIRECTORY_SCREENSHOTS;
import static com.linearity.datservicereplacement.ReturnIfNonSys.SyntheticNameResolver.resolveMethodName;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.ReturnIfNonSys.noSystemChecker;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.utils.HookUtils.listenClass;
import static com.linearity.utils.LoggerUtils.LoggerLog;
import static com.linearity.utils.PublicSeed.publicSeed;
import static com.linearity.utils.SimpleExecutor.MODE_AFTER;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.provider.MediaStore;
import android.view.Display;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.linearity.utils.ExtendedRandom;
import com.linearity.utils.NotFinished;
import com.linearity.utils.SimpleExecutor;
import com.linearity.utils.SimpleExecutorWithMode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import kotlin.Triple;

@NotFinished
public class HookScreenShot {

    public static void doHook(){
        classesAndHooks.put("com.android.systemui.screenshot.ImageExporter",HookScreenShot::hookImageExporter);
//        classesAndHooks.put("com.android.systemui.screenshot.HeadlessScreenshotHandler",HookScreenShot::hookScreenshotHandler);
//        classesAndHooks.put("com.android.systemui.screenshot.TakeScreenshotService", HookScreenShot::listenClassWithHint);
//        classesAndHooks.put("com.android.systemui.screenshot.ScreenshotController", HookScreenShot::listenClassWithHint);
//        classesAndHooks.put("com.android.systemui.screenshot.SaveImageInBackgroundTask", HookScreenShot::listenClassWithHint);
//        classesAndHooks.put("com.android.systemui.screenshot.ImageExporter", HookScreenShot::listenClassWithHint);
//        classesAndHooks.put("com.android.systemui.screenshot.ImageLoader", HookScreenShot::listenClassWithHint);
//        classesAndHooks.put("com.android.systemui.screenshot.ScreenshotEvent", HookScreenShot::listenClassWithHint);
//        classesAndHooks.put(Bitmap.CompressFormat.class.getName(), HookScreenShot::listenToStringWithHint);

        classesAndHooks.put("com.android.systemui.screenshot.TakeScreenshotExecutorImpl",HookScreenShot::hookTakeScreenshotExecutorImpl);
        classesAndHooks.put("com.android.systemui.shade.display.AnyExternalShadeDisplayPolicy",HookScreenShot::hookAnyExternalShadeDisplayPolicy);
        classesAndHooks.put("com.android.systemui.shade.display.AnyExternalShadeDisplayPolicy$Companion",HookScreenShot::hookAnyExternalShadeDisplayPolicyCompanion);
    }

    private static Class<?> reqCallbackClass = null;
    private static final Set<Class<?>> hookReqCallbacks = new HashSet<>();
    private static final Multimap<Object,Uri> callbackResolveUris = Multimaps.newMultimap(new ConcurrentHashMap<>(), ConcurrentHashMap::newKeySet);
    private static final SimpleExecutor ConsumerAndCallbackHandler = param -> {
        int consumerIndex = Integer.MIN_VALUE;
        Object callback = null;
        for (int i=0;i<param.args.length;i+=1){
            if (param.args[i] instanceof Consumer){
                consumerIndex = i;
            }else if(param.args[i] != null){
                Class<?> argClass = param.args[i].getClass();
                callback = param.args[i];
                if (reqCallbackClass != null && argClass.isAssignableFrom(reqCallbackClass)){
                    if (hookReqCallbacks.contains(argClass)){
                        continue;
                    }
                    hookAllMethodsWithCache_Auto(argClass,"onFinish",(SimpleExecutor)paramInner -> {
                        Object thisObject = param.thisObject;
                        Uri[] uris = callbackResolveUris.get(thisObject).toArray(new Uri[0]);
                        callbackResolveUris.get(thisObject).clear();
                        for (Uri uri:uris){
                            LoggerLog(new Exception("image produced:" + uri));
                        }
                    },noSystemChecker);
                    hookReqCallbacks.add(argClass);
                }
            }
        }
        if (callback != null && consumerIndex != Integer.MIN_VALUE){
            Consumer<Uri> c = (Consumer<Uri>) param.args[consumerIndex];
            Object finalCallback = callback;
            Consumer<Uri> uriProxy = uri -> {
                callbackResolveUris.put(finalCallback,uri);
                c.accept(uri);
            };
            param.args[consumerIndex] = uriProxy;

        }
    };
    public static void listenClassWithHint(Class<?> hookClass){
        LoggerLog("listening class:" + hookClass);
        listenClass(hookClass);
    }
    public static void listenToStringWithHint(Class<?> hookClass){
        LoggerLog("listening class toString:" + hookClass);
        listenClass(hookClass);
        hookAllMethodsWithCache_Auto(hookClass,"toString",
                (SimpleExecutor)param -> LoggerLog(new Exception("listening toString()")),
                noSystemChecker);
    }
//    private static final AtomicReference<Field> FIELD_ImageExporterResult_fileName = new AtomicReference<>(null);

    public static void hookImageExporter(Class<?> hookClass){
        LoggerLog("hooking" + hookClass);
//        listenClass(hookClass);
//        Class<?> resultClass = XposedHelpers.findClassIfExists("com.android.systemui.screenshot.ImageExporter$Result",hookClass.getClassLoader());
//        if (resultClass != null){
//            LoggerLog("found result class");
//            XposedBridge.hookAllConstructors(resultClass, new XC_MethodHook() {
//                @Override
//                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                    super.afterHookedMethod(param);
//                    showAfter.simpleExecutor.execute(param);
//                }
//            });
//            hookAllMethodsWithCache_Auto(
//                    resultClass,
//                    "toString",
//                    (SimpleExecutor)param -> LoggerLog(new Exception("resulting screenshot")),
//                    noSystemChecker
//            );
//        }Class<?> taskClass = XposedHelpers.findClassIfExists("com.android.systemui.screenshot.ImageExporter$Task",hookClass.getClassLoader());
//        if (taskClass != null){
//            LoggerLog("found screenshot task class");
//            hookAllMethodsWithCache_Auto(
//                    taskClass,
//                    "execute",
//                    new SimpleExecutorWithMode(
//                            MODE_AFTER,
//                            param -> {
//                                Object resultObj = param.getResult();
//                                if (resultObj != null){
//                                    checkImageExporterResultFields(resultObj.getClass().getClassLoader());
//                                    try {
//                                        Uri uri = (Uri) FIELD_ImageExporterResult_uri.get().get(resultObj);
//
//                                    }catch (Exception e){
//                                        LoggerLog(e);
//                                    }
//                                }
//                            }
//                    ),
//                    noSystemChecker);
//        }
        hookAllMethodsWithCache_Auto(hookClass,"writeImage",(SimpleExecutor)param -> {
            try {
                Bitmap bitmap = (Bitmap) param.args[1];
                int bitmapIndex = 1;
                ContentResolver resolver = (ContentResolver) param.args[0];
                Bitmap.CompressFormat format = (Bitmap.CompressFormat) param.args[2];
                Uri contentUri = (Uri) param.args[4];
                int quality = (int) param.args[3];
                assert bitmap != null;
                assert contentUri != null;
                assert format != null;
                assert resolver != null;
                Bitmap copy = bitmap.copy(Bitmap.Config.ARGB_8888, true);
                Thread t = new Thread(() -> {
                    try {
                        Bitmap replaceWith = resolveBitmap(copy);
                        if (replaceWith == null){
                            throw new NullPointerException("resolved bitmap failed");
                        }
                        param.args[bitmapIndex] = replaceWith;
//                        LoggerLog(new Exception("resolved bitmap"));
//                        File file = new File("/sdcard/Pictures/Screenshots/"+UUID.randomUUID()+".png");
//                        if (!file.exists()){
//                            file.createNewFile();
//                        }
//                        try (OutputStream out = new FileOutputStream(file) ){
//                            replaceWith.compress(format, quality, out);
//                            LoggerLog(new Exception("resolved screenshot"));
//                        }catch (Exception e){
//                            LoggerLog(e);
//                        }
//                        param.args[1] = replaceWith;
                        bitmap.recycle();
                    }catch (Exception e){
                        LoggerLog(e);
                        param.args[1] = null;
                        param.args[4] = null;
                    }
                });
                t.start();
                t.join();
            }catch (Exception e){
                LoggerLog(e);
            }
        },noSystemChecker);
        hookAllMethodsWithCache_Auto(hookClass,"createMetadata",new SimpleExecutorWithMode(MODE_AFTER,param -> {
            ContentValues values = (ContentValues) param.getResult();
            values.put(MediaStore.MediaColumns.RELATIVE_PATH,
                    values.getAsString(MediaStore.MediaColumns.RELATIVE_PATH).replace(DIRECTORY_SCREENSHOTS,SCREENSHOT_PATH_REPLACE_STRING)
            );
        }),noSystemChecker);
//        for (Method m:hookClass.getDeclaredMethods()){
//            if (m.getName().endsWith("writeImage")){
//                XposedBridge.hookMethod(m, new XC_MethodHook() {
//                    @Override
//                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                        super.beforeHookedMethod(param);
//
//                        try {
//                            Bitmap bitmap = (Bitmap) param.args[1];
//                            int bitmapIndex = 1;
//                            ContentResolver resolver = (ContentResolver) param.args[0];
//                            Bitmap.CompressFormat format = (Bitmap.CompressFormat) param.args[2];
//                            Uri contentUri = (Uri) param.args[4];
//                            int quality = (int) param.args[3];
//                            assert bitmap != null;
//                            assert contentUri != null;
//                            assert format != null;
//                            assert resolver != null;
//                            Bitmap copy = bitmap.copy(Bitmap.Config.ARGB_8888, true);
//                            Thread t = new Thread(() -> {
//                                try {
//                                    Bitmap replaceWith = resolveBitmap(copy);
//                                    if (replaceWith == null){
//                                        throw new NullPointerException("resolved bitmap failed");
//                                    }
//                                    param.args[bitmapIndex] = replaceWith;
//                                    LoggerLog(new Exception("resolved bitmap"));
//                                    File file = new File("/sdcard/Pictures/Screenshots/"+UUID.randomUUID()+".png");
//                                    if (!file.exists()){
//                                        file.createNewFile();
//                                    }
//                                    try (OutputStream out = new FileOutputStream(file) ){
//                                        replaceWith.compress(format, quality, out);
//                                        LoggerLog(new Exception("resolved screenshot"));
//                                    }catch (Exception e){
//                                        LoggerLog(e);
//                                    }
//                                    param.args[1] = replaceWith;
//                                    bitmap.recycle();
//                                }catch (Exception e){
//                                    LoggerLog(e);
//                                    param.args[1] = null;
//                                    param.args[4] = null;
//                                }
//                            });
//                            t.start();
//                            t.join();
//                        }catch (Exception e){
//                            LoggerLog(e);
//                        }
//                    }
//                });
////                LoggerLog(new Exception("got method:" + m));
//            }else if (resolveMethodName(m.getName()).equals("createMetadata")){
//
//            }
//        }

    }
    private static final int ERASE_DIGITS = 2;
    private static final float DELTA_THRESHOLD = 2f;
    private static final int RESOLVE_SQUARE_SIZE = 300;
    private static final ForkJoinPool bitmapResolverForks = new ForkJoinPool(4);
    private static final Queue<SimplePair<AtomicReference<Bitmap>, AtomicInteger>> bitmapsToResolve = new ConcurrentLinkedQueue<>();
    private static class SimplePair <F,S>{
        public SimplePair(F first, S second) {
            this.first = first;
            this.second = second;
        }

        F first;
        S second;
    }
    private static final Runnable RESOLVE_BITMAP_RUNNABLE = () -> {
        SimplePair<AtomicReference<Bitmap>,AtomicInteger> toResolve;
        while ((toResolve = bitmapsToResolve.poll()) != null){
            AtomicReference<Bitmap> bitmapRef = toResolve.first;
            Bitmap before = bitmapRef.get();
            bitmapRef.set(resolveBitmapPart2(before));
            before.recycle();
            int remaining = toResolve.second.addAndGet(-1);
            LoggerLog("remaining:" + remaining);
        }
    };
    public static Bitmap resolveBitmap(Bitmap bitmap){

        int cols = bitmap.getWidth() / RESOLVE_SQUARE_SIZE + (bitmap.getWidth() % RESOLVE_SQUARE_SIZE == 0?0:1);
        int rows = bitmap.getHeight() / RESOLVE_SQUARE_SIZE + (bitmap.getHeight() % RESOLVE_SQUARE_SIZE == 0?0:1);
        Triple<List<AtomicReference<Bitmap>>,Integer,Integer> split = splitBitmap(bitmap,rows,cols);
        AtomicInteger totalPartsCounter = new AtomicInteger(split.component1().size());
        split.component1().forEach(bmapRef -> bitmapsToResolve.add(new SimplePair<>(bmapRef,totalPartsCounter)));

        try {

            bitmapResolverForks.submit(RESOLVE_BITMAP_RUNNABLE);
            bitmapResolverForks.submit(RESOLVE_BITMAP_RUNNABLE).join();
            LoggerLog("execution finished");
            Bitmap result = combineBitmaps(split);
            LoggerLog("combined");
            for (AtomicReference<Bitmap> toFree:split.component1()){
                toFree.get().recycle();
            }
            return result;
        }catch (Exception e){
            LoggerLog(e);
            return null;
        }
    }
    public static class GPUBitmapProcessor {
        private static final String TAG = "OffscreenGPUProc";

        private static final String VERTEX_SHADER = """
                attribute vec4 aPosition;
                attribute vec2 aTexCoord;
                varying vec2 vTexCoord;
                void main() {
                    gl_Position = aPosition;
                    vTexCoord = aTexCoord;
                }""";
        private static final String FRAGMENT_SHADER = """

precision mediump float;

uniform sampler2D uTexture;
uniform float deltaThreshold; // perceptual threshold
uniform float eraseDigits;    // number of LSBs to erase
uniform vec2 texSize;         // texture dimensions

varying vec2 vTexCoord;

// --- erase LSBs ---
vec4 eraseLSB(vec4 color, float digits) {
    digits = clamp(digits, 0.0, 8.0);
    float levels = pow(2.0, 8.0 - digits);
    vec4 c255 = color * 255.0;
    c255 = floor(c255 / levels) * levels;
    return c255 / 255.0;
}

// --- linearize sRGB ---
vec3 linearize(vec3 c) {
    return pow(c, vec3(2.2));
}

// --- RGB -> XYZ ---
vec3 rgb2xyz(vec3 c) {
    c = linearize(c);
    return vec3(
    c.r * 0.4124 + c.g * 0.3576 + c.b * 0.1805,
    c.r * 0.2126 + c.g * 0.7152 + c.b * 0.0722,
    c.r * 0.0193 + c.g * 0.1192 + c.b * 0.9505
    );
}

// --- XYZ -> Lab ---
vec3 xyz2lab(vec3 xyz) {
    vec3 n = xyz / vec3(0.95047, 1.0, 1.08883); // D65 reference
    vec3 f = mix(pow(n, vec3(1.0/3.0)), (7.787 * n + vec3(16.0/116.0)), lessThan(n, vec3(0.008856)));
    float L = 116.0 * f.y - 16.0;
    float a = 500.0 * (f.x - f.y);
    float b = 200.0 * (f.y - f.z);
    return vec3(L, a, b);
}

// --- ΔE76 ---
float deltaE(vec3 c1, vec3 c2) {
    vec3 lab1 = xyz2lab(rgb2xyz(c1));
    vec3 lab2 = xyz2lab(rgb2xyz(c2));
    vec3 diff = lab1 - lab2;
    return sqrt(dot(diff, diff));
}

float rand(vec2 co) {
    // Generates repeatable pseudo-random noise per pixel
    return fract(sin(dot(co.xy ,vec2(12.9898, 78.233))) * 43758.5453);
}

void main() {
    vec2 onePixel = 1.0 / texSize;
    vec4 c = texture2D(uTexture, vTexCoord);
//    c = eraseLSB(c, eraseDigits);

    vec4 sum = c;
    float count = 1.0;

    // neighbors: top, left, top-left
    vec4 top = texture2D(uTexture, vTexCoord + vec2(0.0, -onePixel.y));
    vec4 left = texture2D(uTexture, vTexCoord + vec2(-onePixel.x, 0.0));
    //    vec4 tl = texture2D(uTexture, vTexCoord + vec2(-onePixel.x, -onePixel.y));

    if(deltaE(c.rgb, top.rgb) < deltaThreshold) { sum = top;}
    if(deltaE(c.rgb, left.rgb) < deltaThreshold) { sum = left;}
    //    if(deltaE(c.rgb, tl.rgb) < deltaThreshold) { sum += tl;}

    // Quantization step (0–255 domain)
    float stepSize = 16.0;
    float noiseStrength = pow(2.0, eraseDigits); // replaces (1 << eraseDigits)

    // Convert to 0–255
    vec4 rgba255 = sum * 255.0;
    rgba255 = floor(rgba255 / stepSize + 0.5) * stepSize;

    // --- Add noise ---
    vec4 noise = vec4(
    fract(sin(dot(vTexCoord * 13.1, vec2(12.9898,78.233))) * 43758.5453),
    fract(sin(dot(vTexCoord * 17.7, vec2(12.9898,78.233))) * 43758.5453),
    fract(sin(dot(vTexCoord * 19.3, vec2(12.9898,78.233))) * 43758.5453),
    fract(sin(dot(vTexCoord * 23.9, vec2(12.9898,78.233))) * 43758.5453)
    ) * noiseStrength - (noiseStrength / 2.0);

    rgba255 += noise;
    rgba255 = clamp(rgba255, 0.0, 255.0);

    // Back to 0–1 range
    vec4 quantized = rgba255 / 255.0;  // no "vec4" redeclaration

    gl_FragColor = quantized;
}
""";

        public static Bitmap process(Bitmap input, int eraseDigits, float deltaThreshold) {
            int width = input.getWidth();
            int height = input.getHeight();

            // --- EGL setup ---
            EGLDisplay eglDisplay = EGL14.eglGetDisplay(EGL14.EGL_DEFAULT_DISPLAY);
            if (eglDisplay == EGL14.EGL_NO_DISPLAY) throw new RuntimeException("Unable to get EGL display");

            int[] ver = new int[2];
            if (!EGL14.eglInitialize(eglDisplay, ver, 0, ver, 1))
                throw new RuntimeException("Unable to initialize EGL");

            int[] attribList = {
                    EGL14.EGL_RED_SIZE, 8,
                    EGL14.EGL_GREEN_SIZE, 8,
                    EGL14.EGL_BLUE_SIZE, 8,
                    EGL14.EGL_ALPHA_SIZE, 8,
                    EGL14.EGL_RENDERABLE_TYPE, EGL14.EGL_OPENGL_ES2_BIT,
                    EGL14.EGL_NONE
            };
            EGLConfig[] configs = new EGLConfig[1];
            int[] numConfig = new int[1];
            EGL14.eglChooseConfig(eglDisplay, attribList, 0, configs, 0, configs.length, numConfig, 0);
            EGLConfig eglConfig = configs[0];

            int[] attrib_context = {EGL14.EGL_CONTEXT_CLIENT_VERSION, 2, EGL14.EGL_NONE};
            EGLContext eglContext = EGL14.eglCreateContext(eglDisplay, eglConfig, EGL14.EGL_NO_CONTEXT, attrib_context, 0);

            int[] surfaceAttribs = {EGL14.EGL_WIDTH, width, EGL14.EGL_HEIGHT, height, EGL14.EGL_NONE};
            EGLSurface eglSurface = EGL14.eglCreatePbufferSurface(eglDisplay, eglConfig, surfaceAttribs, 0);

            if (!EGL14.eglMakeCurrent(eglDisplay, eglSurface, eglSurface, eglContext))
                throw new RuntimeException("eglMakeCurrent failed");

            // --- GL program ---
            int program = createProgram(VERTEX_SHADER, FRAGMENT_SHADER);
            GLES20.glUseProgram(program);

            // --- Texture setup ---
            int[] textures = new int[2];
            GLES20.glGenTextures(2, textures, 0);

            // Input texture
            int srcTex = textures[0];
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, srcTex);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, input, 0);

            // Output texture
            int outTex = textures[1];
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, outTex);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, width, height, 0,
                    GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null);

            // --- FBO setup ---
            int[] fboIds = new int[1];
            GLES20.glGenFramebuffers(1, fboIds, 0);
            int fbo = fboIds[0];
            GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fbo);
            GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0,
                    GLES20.GL_TEXTURE_2D, outTex, 0);
            int status = GLES20.glCheckFramebufferStatus(GLES20.GL_FRAMEBUFFER);
            if (status != GLES20.GL_FRAMEBUFFER_COMPLETE) {
                throw new RuntimeException("FBO not complete: " + status);
            }

            GLES20.glViewport(0, 0, width, height);

            // --- Uniforms ---
            int texSizeLoc = GLES20.glGetUniformLocation(program, "texSize");
            GLES20.glUniform2f(texSizeLoc, width, height);

            int deltaLoc = GLES20.glGetUniformLocation(program, "deltaThreshold");
            GLES20.glUniform1f(deltaLoc, deltaThreshold);

            int eraseLoc = GLES20.glGetUniformLocation(program, "eraseDigits");
            GLES20.glUniform1f(eraseLoc, eraseDigits);

            int uTexLoc = GLES20.glGetUniformLocation(program, "uTexture");
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, srcTex);
            GLES20.glUniform1i(uTexLoc, 0);

            // --- Vertex data (interleaved) ---
            float[] vertices = {
                    -1f, -1f, 0f, 0f, 1f,
                    1f, -1f, 0f, 1f, 1f,
                    -1f,  1f, 0f, 0f, 0f,
                    1f,  1f, 0f, 1f, 0f
            };
            FloatBuffer vb = ByteBuffer.allocateDirect(vertices.length * 4)
                    .order(ByteOrder.nativeOrder()).asFloatBuffer();
            vb.put(vertices).position(0);

            int posLoc = GLES20.glGetAttribLocation(program, "aPosition");
            int texLoc = GLES20.glGetAttribLocation(program, "aTexCoord");

            vb.position(0);
            GLES20.glEnableVertexAttribArray(posLoc);
            GLES20.glVertexAttribPointer(posLoc, 3, GLES20.GL_FLOAT, false, 5 * 4, vb);

            FloatBuffer texBuf = vb.duplicate();
            texBuf.position(3);
            GLES20.glEnableVertexAttribArray(texLoc);
            GLES20.glVertexAttribPointer(texLoc, 2, GLES20.GL_FLOAT, false, 5 * 4, texBuf);

            // --- Draw ---
            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);

            // --- Read pixels ---
            IntBuffer ib = IntBuffer.allocate(width * height);
            GLES20.glReadPixels(0, 0, width, height, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, ib);

            int[] rgba = ib.array();
            int[] argb = new int[width * height];
            for (int y = 0; y < height; y++) {
                int srcRow = y * width;
                int dstRow = (height - 1 - y) * width;
                for (int x = 0; x < width; x++) {
                    int i = rgba[srcRow + x];
                    int r = (i) & 0xFF;
//                    r += ThreadLocalRandom.current().nextInt(1<<eraseDigits);
//                    r = Math.min(255,r);
                    int g = (i >> 8) & 0xFF;
//                    g += ThreadLocalRandom.current().nextInt(1<<eraseDigits);
//                    g = Math.min(255,g);
                    int b = (i >> 16) & 0xFF;
//                    b += ThreadLocalRandom.current().nextInt(1<<eraseDigits);
//                    b = Math.min(255,b);
                    int a = (i >> 24) & 0xFF;
                    argb[dstRow + x] = (a << 24) | (r << 16) | (g << 8) | b;
                }
            }

            Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            output.setPixels(argb, 0, width, 0, 0, width, height);

            // --- Cleanup ---
            GLES20.glDisableVertexAttribArray(posLoc);
            GLES20.glDisableVertexAttribArray(texLoc);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
            GLES20.glDeleteTextures(2, textures, 0);
            GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
            GLES20.glDeleteFramebuffers(1, fboIds, 0);
            GLES20.glDeleteProgram(program);

            EGL14.eglMakeCurrent(eglDisplay, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_CONTEXT);
            EGL14.eglDestroySurface(eglDisplay, eglSurface);
            EGL14.eglDestroyContext(eglDisplay, eglContext);
            EGL14.eglTerminate(eglDisplay);

            return output;
        }

        // Helpers
        private static int loadShader(int type, String source) {
            int shader = GLES20.glCreateShader(type);
            GLES20.glShaderSource(shader, source);
            GLES20.glCompileShader(shader);

            int[] compiled = new int[1];
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
            if (compiled[0] == 0) {
                String err = GLES20.glGetShaderInfoLog(shader);
                GLES20.glDeleteShader(shader);
                throw new RuntimeException("Could not compile shader " + type + ":" + err);
            }
            return shader;
        }

        private static int createProgram(String vSrc, String fSrc) {
            int vs = loadShader(GLES20.GL_VERTEX_SHADER, vSrc);
            int fs = loadShader(GLES20.GL_FRAGMENT_SHADER, fSrc);
            int program = GLES20.glCreateProgram();
            GLES20.glAttachShader(program, vs);
            GLES20.glAttachShader(program, fs);
            GLES20.glLinkProgram(program);
            int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
            if (linkStatus[0] != GLES20.GL_TRUE) {
                String err = GLES20.glGetProgramInfoLog(program);
                GLES20.glDeleteProgram(program);
                throw new RuntimeException("Could not link program: " + err);
            }
            return program;
        }

        private static void checkEglError(String msg) {
            int err;
            while ((err = EGL14.eglGetError()) != EGL14.EGL_SUCCESS) {
                LoggerLog(msg + ": EGL error: 0x" + Integer.toHexString(err));
            }
        }

        private static void checkGlError(String op) {
            int error;
            while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
                LoggerLog(op + ": glError " + error);
                throw new RuntimeException(op + ": glError " + error);
            }
        }
    }
    private static Bitmap resolveBitmapPart2(Bitmap part){
        return GPUBitmapProcessor.process(part, ERASE_DIGITS, DELTA_THRESHOLD);
    }

    /**
     * Splits a bitmap into a grid (rows x cols) with no pixel loss.
     * Handles uneven divisions by stretching the last tile in each row/column.
     * alert:list not thread-safe
     */
    private static Triple<List<AtomicReference<Bitmap>>,Integer,Integer> splitBitmap(Bitmap source, int rows, int cols) {
        List<AtomicReference<Bitmap>> pieces = new ArrayList<>(rows * cols);

        int totalWidth = source.getWidth();
        int totalHeight = source.getHeight();

        int basePieceWidth = totalWidth / cols;
        int basePieceHeight = totalHeight / rows;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int left = col * basePieceWidth;
                int top = row * basePieceHeight;

                // Adjust width/height for last row/column to include any remainder pixels
                int width = (col == cols - 1)
                        ? totalWidth - left
                        : basePieceWidth;
                int height = (row == rows - 1)
                        ? totalHeight - top
                        : basePieceHeight;

                Bitmap piece = Bitmap.createBitmap(source, left, top, width, height);
                pieces.add(new AtomicReference<>(piece));
            }
        }
        return new Triple<>(pieces,rows,cols);
    }
    private static Bitmap combineBitmaps(Triple<List<AtomicReference<Bitmap>>,Integer,Integer> splitParts) {
        List<AtomicReference<Bitmap>> parts = splitParts.component1();
        int rows = splitParts.component2();
        int cols = splitParts.component3();
        // Compute total width and height dynamically (handles uneven splits)
        int totalWidth = 0, totalHeight = 0;

        // Compute width from first row, height from first column
        for (int c = 0; c < cols; c++)
            totalWidth += parts.get(c).get().getWidth();
        for (int r = 0; r < rows; r++)
            totalHeight += parts.get(r * cols).get().getHeight();

        Bitmap result = Bitmap.createBitmap(totalWidth, totalHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);

        int index = 0;
        int y = 0;

        for (int r = 0; r < rows; r++) {
            int x = 0;
            int rowHeight = parts.get(r * cols).get().getHeight();
            for (int c = 0; c < cols; c++) {
                Bitmap piece = parts.get(index++).get();
                canvas.drawBitmap(piece, x, y, paint);
                x += piece.getWidth();
            }
            y += rowHeight;
        }

        return result;
    }
    public static void hookScreenshotHandler(Class<?> hookClass){
        listenClass(hookClass);
        if (reqCallbackClass == null){
            reqCallbackClass = XposedHelpers.findClassIfExists("com.android.systemui.screenshot.TakeScreenshotService$RequestCallback",hookClass.getClassLoader());
        }
        hookAllMethodsWithCache_Auto(hookClass,"handleScreenshot",ConsumerAndCallbackHandler,noSystemChecker);

    }
    public static void hookTakeScreenshotService(Class<?> hookClass){
        if (reqCallbackClass == null){
            reqCallbackClass = XposedHelpers.findClassIfExists("com.android.systemui.screenshot.TakeScreenshotService$RequestCallback",hookClass.getClassLoader());
        }
        hookAllMethodsWithCache_Auto(hookClass,"handleRequest",ConsumerAndCallbackHandler,noSystemChecker);
    }
    public static void hookTakeScreenshotExecutorImpl(Class<?> hookClass){
        listenClass(hookClass);

    }
    public static void hookAnyExternalShadeDisplayPolicy(Class<?> hookClass){

    }
    private static final Pattern displayTypeFieldNamePattern = Pattern.compile("TYPE_[A-Z_]+[A-Z_]");
    public static void hookAnyExternalShadeDisplayPolicyCompanion(Class<?> hookClass){
        try {
            Set<Integer> defaultAllowed = (Set<Integer>) XposedHelpers.getStaticObjectField(hookClass, "ALLOWED_DISPLAY_TYPES");
            Set<Integer> allowedScreenshotDisplayTypes = new HashSet<>(defaultAllowed);
            for (Field f:Display.class.getDeclaredFields()){
                if (Modifier.isStatic(f.getModifiers())){
                    if (f.getType() == int.class || f.getType() == Integer.class){
                        if (displayTypeFieldNamePattern.matcher(f.getName()).matches()){
                            try {
                                allowedScreenshotDisplayTypes.add(f.getInt(null));
                            }catch (Exception e){
                                LoggerLog(e);
                            }
                        }
                    }
                }
            }

            // Replace static field
            XposedHelpers.setStaticObjectField(hookClass, "ALLOWED_DISPLAY_TYPES", allowedScreenshotDisplayTypes);
        }catch (Exception e){
            LoggerLog(e);
        }
    }

    private static final String SCREENSHOT_PATH_REPLACE_STRING;
    static {
        ExtendedRandom random = new ExtendedRandom(publicSeed).nextExtendedRandom();
        random.nextString(20);
        random = random.nextExtendedRandom();
        SCREENSHOT_PATH_REPLACE_STRING = random.nextRandomHexUpper(15);
    }
}
