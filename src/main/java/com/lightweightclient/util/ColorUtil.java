package com.lightweightclient.util;
import java.awt.Color;
public class ColorUtil {
    public static int getChromaColor(int speed) {
        float hue = (System.currentTimeMillis() % speed) / (float) speed;
        int rgb = Color.HSBtoRGB(hue, 1.0f, 1.0f);
        return 0xFF000000 | rgb;
    }
}
