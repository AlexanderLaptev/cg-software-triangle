package com.cgvsu.rasterization;

import com.cgvsu.util.Triangle;
import com.cgvsu.util.Vector2f;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.Comparator;

public class Rasterization {
    private static final Comparator<Vector2f> COMPARATOR = (a, b) -> {
        int cmp = Float.compare(a.y, b.y);
        if (cmp != 0) { return cmp; } else return Float.compare(a.x, b.x);
    };

    private static final Vector2f p = new Vector2f();

    public static void drawTriangle(PixelWriter pw, Triangle trig) {
        drawTriangle(pw, trig.v1, trig.v2, trig.v3, trig.c1, trig.c2, trig.c3);
    }

    public static void drawTriangle(
            PixelWriter pw,
            Vector2f v1,
            Vector2f v2,
            Vector2f v3,
            Color c1,
            Color c2,
            Color c3
    ) {
        // Sort vertices by y.
        var verts = new Vector2f[]{v1, v2, v3};
        Arrays.sort(verts, COMPARATOR);
        int x1 = (int) verts[0].x;
        int x2 = (int) verts[1].x;
        int x3 = (int) verts[2].x;
        int y1 = (int) verts[0].y;
        int y2 = (int) verts[1].y;
        int y3 = (int) verts[2].y;

        // Precalculate common terms.
        int x2x1 = x2 - x1;
        int x3x2 = x3 - x2;
        int x3x1 = x3 - x1;
        int y2y1 = y2 - y1;
        int y3y2 = y3 - y2;
        int y3y1 = y3 - y1;

        // Double the area of the triangle. Used to calculate the barycentric coordinates later.
        float area = Math.abs(v1.to(v2).crossMagnitude(v1.to(v3)));

        // Draw the top triangle. The separating row is not drawn yet.
        for (int y = y1; y < y2; y++) {
            // No need to check if the divisors are null, because the loop will not execute if y1 == y2.
            int l = x2x1 * (y - y1) / y2y1 + x1; // Edge 1-2.
            int r = x3x1 * (y - y1) / y3y1 + x1; // Edge 1-3.
            if (l > r) { // Swap.
                int tmp = l;
                l = r;
                r = tmp;
            }
            for (int x = l; x <= r; x++) {
                // Interpolate the color using barycentric coordinates.
                p.set(x, y);
                float w1 = Math.abs(v2.to(p).crossMagnitude(v2.to(v3))) / area;
                float w2 = Math.abs(v1.to(p).crossMagnitude(v1.to(v3))) / area;
                float w3 = Math.abs(v1.to(p).crossMagnitude(v1.to(v2))) / area;
                double red = w1 * c1.getRed() + w2 * c2.getRed() + w3 * c3.getRed();
                double green = w1 * c1.getGreen() + w2 * c2.getGreen() + w3 * c3.getGreen();
                double blue = w1 * c1.getBlue() + w2 * c2.getBlue() + w3 * c3.getBlue();
                pw.setColor(x, y, new Color(red, green, blue, 1.0));
            }
        }

        // Draw the separating line and bottom triangle.
        // The loop is almost the same as the above one, the only difference is in the line equations.
        if (y3y2 == 0 || y3y1 == 0) return; // Stop now if the bottom triangle is degenerate (avoids div by zero).
        for (int y = y2; y <= y3; y++) {
            int l = x3x2 * (y - y2) / y3y2 + x2; // Edge 2-3.
            int r = x3x1 * (y - y1) / y3y1 + x1; // Edge 1-3.
            if (l > r) {
                int tmp = l;
                l = r;
                r = tmp;
            }
            for (int x = l; x <= r; x++) {
                p.set(x, y);
                float w1 = Math.abs(v2.to(p).crossMagnitude(v2.to(v3))) / area;
                float w2 = Math.abs(v1.to(p).crossMagnitude(v1.to(v3))) / area;
                float w3 = Math.abs(v1.to(p).crossMagnitude(v1.to(v2))) / area;
                double red = w1 * c1.getRed() + w2 * c2.getRed() + w3 * c3.getRed();
                double green = w1 * c1.getGreen() + w2 * c2.getGreen() + w3 * c3.getGreen();
                double blue = w1 * c1.getBlue() + w2 * c2.getBlue() + w3 * c3.getBlue();
                pw.setColor(x, y, new Color(red, green, blue, 1.0));
            }
        }
    }
}
