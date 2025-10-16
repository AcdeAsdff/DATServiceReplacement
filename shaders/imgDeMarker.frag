
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
    c = eraseLSB(c, eraseDigits);

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
}//ERROR: 0:94: 'assign' :  cannot convert from '4-component vector of float' to 'float'
//ERROR: 0:96: 'assign' :  cannot convert from 'float' to 'FragColor 4-component vector of float'
//ERROR: 3 compilation errors.  No code generated.