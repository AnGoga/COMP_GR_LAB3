package org.example;

import java.awt.*;
import java.util.*;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static org.example.Utils.sign;

public class Polygone {
    public List<Point> pts = new ArrayList<>();

    public boolean isSimple() {
        for (int i = 0; i < pts.size(); i++) {
            for (int j = i + 2; j < pts.size(); j++) {
                Point a = pts.get(i);
                Point b = pts.get((i + 1) % pts.size());
                Point c = pts.get(j);
                Point d = pts.get((j + 1) % pts.size());

                if (multi(a, b, c) * multi(a, b, d) < 0 && multi(c, d, a) * multi(c, d, b) < 0)
                    return false;
            }
        }
        return true;
    }

    public boolean isConvex() {
        int f = sign(multi(pts.get(0), pts.get(1), pts.get(2)));

        for (int i = 1; i < pts.size(); i++) {
            double m = multi(pts.get(i), pts.get((i + 1) % pts.size()), pts.get((i + 2) % pts.size()));
            if (sign(m) != f) return false;
        }
        return true;
    }

    private double multi(Point a, Point b, Point c) {
        return (c.x - a.x) * (b.y - a.y) - (b.x - a.x) * (c.y - a.y);
    }

    public Set<Point> evenOdd() {
        Set<Point> res = new HashSet<>();
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (Point p : pts) {
            minY = min(minY, p.y);
            maxY = max(maxY, p.y);
        }

        for (int y = minY; y <= maxY; y++) {
            List<Integer> ps = new ArrayList<>();

            for (int i = 0; i < pts.size(); i++) {
                Point p1 = pts.get(i);
                Point p2 = pts.get((i + 1) % pts.size());
                Utils.line(res, p1.x, p1.y, p2.x, p2.y);

                if (y >= min(p1.y, p2.y) && y < max(p1.y, p2.y)) {
                    double t = (double)(y - p1.y) / (p2.y - p1.y);
                    double dx = t * (p2.x - p1.x);
                    ps.add(p1.x + (int)Math.round(dx));
                }
            }

            Collections.sort(ps);
            for (int i = 0; i < ps.size() - 1; i += 2) {
                for (int x = ps.get(i); x < ps.get(i + 1); x++) {
                    res.add(new Point(x, y));
                }
            }
        }
        return res;
    }

    public Set<Point> nonZero() {
        Set<Point> res = new HashSet<>();
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (Point p : pts) {
            minY = min(minY, p.y);
            maxY = max(maxY, p.y);
        }

        for (int y = minY; y <= maxY; y++) {
            List<Point> ps = new ArrayList<>();

            for (int i = 0; i < pts.size(); i++) {
                Point p1 = pts.get(i);
                Point p2 = pts.get((i + 1) % pts.size());
                Utils.line(res, p1.x, p1.y, p2.x, p2.y);

                if (y >= min(p1.y, p2.y) && y < max(p1.y, p2.y)) {
                    double t = (double)(y - p1.y) / (p2.y - p1.y);
                    double x = p1.x + t * (p2.x - p1.x);
                    int sign = Utils.sign(p2.y - p1.y);

                    ps.add(new Point((int)x, sign));
                }
            }

            ps.sort((a, b) -> a.x - b.x);;
            int cnt = 0;
            int lastX = 0;
            for (Point p : ps) {
                if (cnt != 0) {
                    for (int x = lastX; x < p.x; x++) {
                        res.add(new Point(x, y));
                    }
                }
                cnt += p.y;
                lastX = p.x;
            }
        }
        return res;
    }
}
