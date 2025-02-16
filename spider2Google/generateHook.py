import javalang.parse

import aidlProcessor.parse
a = """public final class DisplayInfo implements Parcelable {
    /**
     * The surface flinger layer stack associated with this logical display.
     */
    public int layerStack;

    /**
     * Display flags.
     */
    public int flags;

    /**
     * Display type.
     */
    public int type;

    /**
     * Logical display identifier.
     */
    public int displayId;

    /**
     * Display Group identifier.
     */
    public int displayGroupId;

    /**
     * Display address, or null if none.
     * Interpretation varies by display type.
     */
    public DisplayAddress address;

    /**
     * Product-specific information about the display or the directly connected device on the
     * display chain. For example, if the display is transitively connected, this field may contain
     * product information about the intermediate device.
     */
    public DeviceProductInfo deviceProductInfo;

    /**
     * The human-readable name of the display.
     */
    public String name;

    /**
     * Unique identifier for the display. Shouldn't be displayed to the user.
     */
    public String uniqueId;

    /**
     * The width of the portion of the display that is available to applications, in pixels.
     * Represents the size of the display minus any system decorations.
     */
    public int appWidth;

    /**
     * The height of the portion of the display that is available to applications, in pixels.
     * Represents the size of the display minus any system decorations.
     */
    public int appHeight;

    /**
     * The smallest value of {@link #appWidth} that an application is likely to encounter,
     * in pixels, excepting cases where the width may be even smaller due to the presence
     * of a soft keyboard, for example.
     */
    public int smallestNominalAppWidth;

    /**
     * The smallest value of {@link #appHeight} that an application is likely to encounter,
     * in pixels, excepting cases where the height may be even smaller due to the presence
     * of a soft keyboard, for example.
     */
    public int smallestNominalAppHeight;

    /**
     * The largest value of {@link #appWidth} that an application is likely to encounter,
     * in pixels, excepting cases where the width may be even larger due to system decorations
     * such as the status bar being hidden, for example.
     */
    public int largestNominalAppWidth;

    /**
     * The largest value of {@link #appHeight} that an application is likely to encounter,
     * in pixels, excepting cases where the height may be even larger due to system decorations
     * such as the status bar being hidden, for example.
     */
    public int largestNominalAppHeight;

    /**
     * The logical width of the display, in pixels.
     * Represents the usable size of the display which may be smaller than the
     * physical size when the system is emulating a smaller display.
     */
    @UnsupportedAppUsage
    public int logicalWidth;

    /**
     * The logical height of the display, in pixels.
     * Represents the usable size of the display which may be smaller than the
     * physical size when the system is emulating a smaller display.
     */
    @UnsupportedAppUsage
    public int logicalHeight;

    /**
     * The {@link DisplayCutout} if present, otherwise {@code null}.
     *
     * @hide
     */
    // Remark on @UnsupportedAppUsage: Display.getCutout should be used instead
    @Nullable
    @UnsupportedAppUsage(maxTargetSdk = Build.VERSION_CODES.P)
    public DisplayCutout displayCutout;

    /**
     * The rotation of the display relative to its natural orientation.
     * May be one of {@link android.view.Surface#ROTATION_0},
     * {@link android.view.Surface#ROTATION_90}, {@link android.view.Surface#ROTATION_180},
     * {@link android.view.Surface#ROTATION_270}.
     * <p>
     * The value of this field is indeterminate if the logical display is presented on
     * more than one physical display.
     * </p>
     */
    @Surface.Rotation
    @UnsupportedAppUsage
    public int rotation;

    /**
     * The active display mode.
     */
    public int modeId;

    /**
     * The render frame rate this display is scheduled at, which is a divisor of the active mode
     * refresh rate. This is the rate SurfaceFlinger would consume frames and would be observable
     * by applications via the cadence of {@link android.view.Choreographer} callbacks and
     * by backpressure when submitting buffers as fast as possible.
     * Apps can call {@link android.view.Display#getRefreshRate} to query this value.
     *
     */
    public float renderFrameRate;

    /**
     * The default display mode.
     */
    public int defaultModeId;

    /**
     * The supported modes of this display.
     */
    public Display.Mode[] supportedModes = Display.Mode.EMPTY_ARRAY;

    /** The active color mode. */
    public int colorMode;

    /** The list of supported color modes */
    public int[] supportedColorModes = { Display.COLOR_MODE_DEFAULT };

    /** The display's HDR capabilities */
    public Display.HdrCapabilities hdrCapabilities;

    /** The formats disabled by user **/
    public int[] userDisabledHdrTypes = {};

    /**
     * Indicates whether the display can be switched into a mode with minimal post
     * processing.
     *
     * @see android.view.Display#isMinimalPostProcessingSupported
     */
    public boolean minimalPostProcessingSupported;

    /**
     * The logical display density which is the basis for density-independent
     * pixels.
     */
    public int logicalDensityDpi;

    /**
     * The exact physical pixels per inch of the screen in the X dimension.
     * <p>
     * The value of this field is indeterminate if the logical display is presented on
     * more than one physical display.
     * </p>
     */
    public float physicalXDpi;

    /**
     * The exact physical pixels per inch of the screen in the Y dimension.
     * <p>
     * The value of this field is indeterminate if the logical display is presented on
     * more than one physical display.
     * </p>
     */
    public float physicalYDpi;

    /**
     * This is a positive value indicating the phase offset of the VSYNC events provided by
     * Choreographer relative to the display refresh.  For example, if Choreographer reports
     * that the refresh occurred at time N, it actually occurred at (N - appVsyncOffsetNanos).
     */
    public long appVsyncOffsetNanos;

    /**
     * This is how far in advance a buffer must be queued for presentation at
     * a given time.  If you want a buffer to appear on the screen at
     * time N, you must submit the buffer before (N - bufferDeadlineNanos).
     */
    public long presentationDeadlineNanos;

    /**
     * The state of the display, such as {@link android.view.Display#STATE_ON}.
     */
    public int state;

    /**
     * The current committed state of the display. For example, this becomes
     * {@link android.view.Display#STATE_ON} only after the power state ON is fully committed.
     */
    public int committedState;

    /**
     * The UID of the application that owns this display, or zero if it is owned by the system.
     * <p>
     * If the display is private, then only the owner can use it.
     * </p>
     */
    public int ownerUid;

    /**
     * The package name of the application that owns this display, or null if it is
     * owned by the system.
     * <p>
     * If the display is private, then only the owner can use it.
     * </p>
     */
    public String ownerPackageName;

    /**
     * The refresh rate override for this app. 0 means no override.
     */
    public float refreshRateOverride;

    /**
     * @hide
     * Get current remove mode of the display - what actions should be performed with the display's
     * content when it is removed.
     *
     * @see Display#getRemoveMode()
     */
    // TODO (b/114338689): Remove the flag and use IWindowManager#getRemoveContentMode
    public int removeMode = Display.REMOVE_MODE_MOVE_CONTENT_TO_PRIMARY;

    /**
     * @hide
     * The current minimum brightness constraint of the display. Value between 0.0 and 1.0,
     * derived from the config constraints of the display device of this logical display.
     */
    public float brightnessMinimum;

    /**
     * @hide
     * The current maximum brightness constraint of the display. Value between 0.0 and 1.0,
     * derived from the config constraints of the display device of this logical display.
     */
    public float brightnessMaximum;

    /**
     * @hide
     * The current default brightness of the display. Value between 0.0 and 1.0,
     * derived from the configuration of the display device of this logical display.
     */
    public float brightnessDefault;

    /**
     * The {@link RoundedCorners} if present, otherwise {@code null}.
     */
    @Nullable
    public RoundedCorners roundedCorners;

    /**
     * Install orientation of the display relative to its natural orientation.
     */
    @Surface.Rotation
    public int installOrientation;

    @Nullable
    public DisplayShape displayShape;

    /**
     * Refresh rate range limitation based on the current device layout
     */
    @Nullable
    public SurfaceControl.RefreshRateRange layoutLimitedRefreshRate;

    /**
     * The current hdr/sdr ratio for the display. If the display doesn't support hdr/sdr ratio
     * queries then this is NaN
     */
    public float hdrSdrRatio = Float.NaN;

    /**
     * RefreshRateRange limitation for @Temperature.ThrottlingStatus
     */
    @NonNull
    public SparseArray<SurfaceControl.RefreshRateRange> thermalRefreshRateThrottling =
            new SparseArray<>();

    /**
     * The ID of the brightness throttling data that should be used. This can change e.g. in
     * concurrent displays mode in which a stricter brightness throttling policy might need to be
     * used.
     */
    @Nullable
    public String thermalBrightnessThrottlingDataId;


}"""
a = (a.replace("oneway ", '')
     .replace("in ", '')
     .replace("const ", "final ")
     .replace("parcelable ", 'class ')
     .replace("out ", ''))
toParse = javalang.parse.parse(a)
# print(toParse)
# print('=====================')
print("public static void hook" + str(toParse.types[0].name) + "(Class<?> hookClass){")
for bodyItem in toParse.types[0].body:
    if isinstance(bodyItem, javalang.tree.MethodDeclaration):
        aidlProcessor.parse.parsingMethod(bodyItem)
# print(f.read())
print("}")