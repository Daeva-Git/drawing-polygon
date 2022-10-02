package library;
import java.awt.image.BufferedImage;

public class ScanConversion {
    public static void bresenhamLine(int x1, int y1, int x2, int y2, BufferedImage img, int[] pixel) {
        // calculate slope
        float m = (y2 - y1) * 1f / (x2 - x1) * 1f;
        if (Float.isNaN(m))
            m = 0;

        if (y1 < y2) {
            // draw in bottom half
            if (1 >= m && m >= 0) {
                // draw in octant 1
                drawLineInOctant(x1, y1, x2, y2, false, false, false, img, pixel);
            } else if (Float.POSITIVE_INFINITY >= m && m >= 1) {
                // draw in octant 2
                drawLineInOctant(y1, x1, y2, x2, true, false, false, img, pixel);
            } else if (-1 >= m && m >= Float.NEGATIVE_INFINITY) {
                // draw in octant 3
                drawLineInOctant(y1, x1, y2, x2, true, false, true, img, pixel);
            } else if (0 >= m && m >= -1) {
                // draw in octant 4
                drawLineInOctant(x1, y1, x2, y2, false, true, true, img, pixel);
            }
        } else {
            // draw in upper half
            if (1 >= m && m >= 0 && x1 > x2) {
                // draw in octant 5
                drawLineInOctant(x1, y1, x2, y2, false, true, false, img, pixel);
            } else if (Float.POSITIVE_INFINITY >= m && m >= 1) {
                // draw in octane 6
                drawLineInOctant(y1, x1, y2, x2, true, true, false, img, pixel);
            } else if (-1 >= m && m >= Float.NEGATIVE_INFINITY) {
                // draw in octant 7
                drawLineInOctant(y1, x1, y2, x2, true, true, true, img, pixel);
            } else if (0 >= m && m >= -1) {
                // draw in octant 8
                drawLineInOctant(x1, y1, x2, y2, false, false, true, img, pixel);
            }
        }
    }

    private static void drawLineInOctant(int x1, int y1, int x2, int y2, boolean swaped, boolean reverse,
            boolean decrement, BufferedImage img, int[] pixel) {
        final int dx = Math.abs(x2 - x1);
        final int dy = Math.abs(y2 - y1);
        final int endX = reverse ? x1 : x2;

        int x = reverse ? x2 : x1;
        int y = reverse ? y2 : y1;
        int d = 2 * dy - dx;

        while (x < endX) {
            if (d <= 0) {
                // E
                d = d + 2 * dy;
            } else {
                // NE
                d = d + 2 * (dy - dx);
                y = y + (decrement ? -1 : 1);
            }
            x = x + 1;

            // draw pixel
            img.getRaster().setPixel(swaped ? y : x, swaped ? x : y, pixel);
        }
    }
}
