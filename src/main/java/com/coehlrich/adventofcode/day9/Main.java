package com.coehlrich.adventofcode.day9;

import com.coehlrich.adventofcode.Day;
import com.coehlrich.adventofcode.Result;
import com.coehlrich.adventofcode.util.Point2;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

public class Main implements Day {

    private static final Pattern PATTERN = Pattern.compile("(\\d+),(\\d+)");

    @Override
    public Result execute(String input) {
        List<Point2> tiles = input.lines()
                .map(PATTERN::matcher)
                .map(matcher -> {
                    matcher.matches();
                    return new Point2(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
                })
                .toList();

        boolean horizontal = true;
        List<Area> areas = new ArrayList<>();
        IntSet horizontalLines = new IntOpenHashSet();
        IntSet verticalLines = new IntOpenHashSet();
        for (int i = 0; i < tiles.size(); i++) {
            Point2 a = tiles.get(i);
            Point2 b = tiles.get((i + 1) % tiles.size());
            if (a.x() != b.x()) {
                horizontalLines.add(a.y());
            } else {
                verticalLines.add(a.x());
            }
        }

        long part2 = 0;

        long part1 = 0;
        for (int i = 0; i < tiles.size(); i++) {
            for (int j = i + 1; j < tiles.size(); j++) {
                Point2 a = tiles.get(i);
                Point2 b = tiles.get(j);
                long ax = Math.min(a.x(), b.x());
                long bx = Math.max(a.x(), b.x());
                long ay = Math.min(a.y(), b.y());
                long by = Math.max(a.y(), b.y());
                part1 = Math.max((bx - ax + 1) * (by - ay + 1), part1);
                areas.add(new Area(
                        new Point2(
                                Math.min(a.x(), b.x()),
                                Math.min(a.y(), b.y())),
                        new Point2(
                                Math.max(a.x(), b.x()),
                                Math.max(a.y(), b.y()))));
            }
        }

        areas.sort(Comparator.comparingLong(Area::getSize));

        long time = System.currentTimeMillis();
        areas:
        for (int i = areas.size() - 1; i >= 0; i--) {
            Area area = areas.get(i);
//                System.out.println(area);
            Point2 a = area.a;
            Point2 b = area.b;
            int tx = a.x() + 1;
            int ty = a.y() + 1;
            while (verticalLines.contains(tx)) {
                tx++;
            }

            while (horizontalLines.contains(ty)) {
                ty++;
            }

            if (tx >= b.x() || ty >= b.y()) {
                throw new IllegalStateException();
            }

            int t = 0;
            int l = 0;

            for (int j = 0; j < tiles.size(); j++) {
//                    if (area.toString().equals("Area[a=Point2[x=2, y=3], b=Point2[x=9, y=5]]") && j == 7) {
//                        System.out.println();
//                    }
                Point2 c = tiles.get(j);
                Point2 d = tiles.get((j + 1) % tiles.size());

                if (c.x() != d.x()) {
                    int ax = Math.min(c.x(), d.x());
                    int bx = Math.max(c.x(), d.x());
                    if (c.y() < ty && ax < tx && bx > tx) {
                        t++;
                    }

                    if (c.y() > a.y() && c.y() < b.y() && (ax <= a.x() && bx > a.x()
                            || bx >= b.x() && ax < b.x())) {
                        continue areas;
                    }
                } else if (c.y() != d.y()) {
                    int ay = Math.min(c.y(), d.y());
                    int by = Math.max(c.y(), d.y());
                    if (c.x() < tx && ay < ty && by > ty) {
                        l++;
                    }

                    if (c.x() > a.x() && c.x() < b.x() && (ay <= a.y() && by > a.y()
                            || by >= b.y() && ay < b.y())) {
                        continue areas;
                    }
                }
            }
//                if (area.toString().equals("Area[a=Point2[x=2, y=3], b=Point2[x=9, y=5]]")) {
//                    System.out.println();
//                }
            if (t % 2 != 1) {
                continue areas;
            }
            if (l % 2 != 1) {
                continue areas;
            }
            long ax = Math.min(a.x(), b.x());
            long bx = Math.max(a.x(), b.x());
            long ay = Math.min(a.y(), b.y());
            long by = Math.max(a.y(), b.y());
            part2 = (bx - ax + 1) * (by - ay + 1);
            break;
        }
        return new Result(part1, part2);
    }

    private record Area(Point2 a, Point2 b) {

        public long getSize() {
            long ax = Math.min(a.x(), b.x());
            long bx = Math.max(a.x(), b.x());
            long ay = Math.min(a.y(), b.y());
            long by = Math.max(a.y(), b.y());
            return (bx - ax + 1) * (by - ay + 1);
        }

    }

}
