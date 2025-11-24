package org.example;

import java.awt.*;

import static java.lang.Math.abs;

public class Utils {

    public static void line(Graphics g, int x0, int y0, int x1, int y1) {
        int dx = abs(x1 - x0);
        int dy = abs(y1 - y0);

        int nx = sign(x1 - x0);
        int ny = sign(y1 - y0);

        int err = dx - dy;
        while (true) {
            g.fillRect(x0, y0, 1, 1);
            if (x0 == x1 && y0 == y1) return;

            int e = 2 * err;
            if (e > -dy) {
                err -= dy;
                x0 += nx;
            }
            if (e < dx) {
                err += dx;
                y0 += ny;
            }
        }
    }

    static int sign(double x) {
        if (x == 0) return 0;
        if (x > 0) return 1;
        return -1;
    }
}
