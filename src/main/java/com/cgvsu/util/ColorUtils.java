package com.cgvsu.util;

import javafx.scene.paint.Color;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ColorUtils {
    private static final Random rand = ThreadLocalRandom.current();

    public static Color getRandomColor() {
        return new Color(
                rand.nextDouble(),
                rand.nextDouble(),
                rand.nextDouble(),
                1.0f
        );
    }

    public static String colorToHexLabel(Color c) {
        return String.format("#%02x%02x%02x",
                (int) c.getRed() * 255,
                (int) c.getGreen() * 255,
                (int) c.getBlue() * 255);
    }
}
