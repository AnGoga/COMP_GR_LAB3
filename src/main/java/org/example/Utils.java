package org.example;

import java.awt.*;
import java.util.Set;


public class Utils {
    public static void line(Graphics g, int x0, int y0, int x1, int y1) {
        int dx = x1 - x0;
        int dy = y1 - y0;
        int sz = Math.max(Math.abs(dx), Math.abs(dy));
        if (sz == 0) {
            g.fillRect(x0, y0, 1, 1);
            return;
        }

        for (int k = 0; k <= sz; k++) {
            int x = fix(x0 * sz + dx * k, sz);
            int y = fix(y0 * sz + dy * k, sz);

            g.fillRect(x, y, 1, 1);
        }
    }

    public static void line(Set<Point> g, int x0, int y0, int x1, int y1) {
        int dx = x1 - x0;
        int dy = y1 - y0;
        int sz = Math.max(Math.abs(dx), Math.abs(dy));
        if (sz == 0) {
            g.add(new Point(x0, y0));
            return;
        }

        for (int k = 0; k <= sz; k++) {
            int x = fix(x0 * sz + dx * k, sz);
            int y = fix(y0 * sz + dy * k, sz);

            g.add(new Point(x, y));
        }
    }

    private static int fix(int x, int n) {
        int r = (Math.abs(x) + n / 2) / n;
        return x < 0 ? -r : r;
    }


    static int sign(double x) {
        if (x == 0) return 0;
        if (x > 0) return 1;
        return -1;
    }
}
