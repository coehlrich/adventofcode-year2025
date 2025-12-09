package com.coehlrich.adventofcode.day9;

import com.coehlrich.adventofcode.Day;
import com.coehlrich.adventofcode.Result;
import com.coehlrich.adventofcode.util.Direction;
import com.coehlrich.adventofcode.util.Point2;

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
        for (int i = 0; i < tiles.size(); i++) {

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

        areas:
        for (int i = areas.size() - 1; i >= 0; i--) {
            Area area = areas.get(i);
//                System.out.println(area);
            Point2 a = area.a;
            Point2 b = area.b;
            int[] el = new int[b.y() - a.y() + 1];
            int[] er = new int[b.y() - a.y() + 1];
            int[] eu = new int[b.x() - a.x() + 1];
            int[] ed = new int[b.x() - a.x() + 1];

            for (int j = 0; j < tiles.size(); j++) {
//                    if (area.toString().equals("Area[a=Point2[x=2, y=3], b=Point2[x=9, y=5]]") && j == 7) {
//                        System.out.println();
//                    }
                Point2 c = tiles.get(j);
                Point2 d = tiles.get((j + 1) % tiles.size());

                if (c.x() != d.x()) {
                    int ax = Math.min(c.x(), d.x());
                    int bx = Math.max(c.x(), d.x());
                    int start = Math.max(ax + 1, a.x()) - a.x();
                    int end = Math.min(bx - 1, b.x()) - a.x();
                    if (end >= 0 && start < eu.length) {
                        if (c.y() <= a.y()) {
                            for (int k = start; k <= end; k++) {
                                eu[k]++;
                            }
                        }
                        if (c.y() >= b.y()) {
                            for (int k = start; k <= end; k++) {
                                ed[k]++;
                            }
                        }
                    }
                    if (c.y() >= a.y() && c.y() <= b.y()) {
                        if (ax <= a.x()) {
                            el[c.y() - a.y()]++;
                        }
                        if (bx >= b.x()) {
                            er[c.y() - a.y()]++;
                        }
                    }
                } else if (c.y() != d.y()) {
                    int ay = Math.min(c.y(), d.y());
                    int by = Math.max(c.y(), d.y());
                    int start = Math.max(ay + 1, a.y()) - a.y();
                    int end = Math.min(by - 1, b.y()) - a.y();
                    if (end >= 0 && start < eu.length) {
                        if (c.x() <= a.x()) {
                            for (int k = start; k <= end; k++) {
                                el[k]++;
                            }
                        }
                        if (c.x() >= b.x()) {
                            for (int k = start; k <= end; k++) {
                                er[k]++;
                            }
                        }
                    }
                    if (c.x() >= a.x() && c.x() <= b.x()) {
                        if (ay <= a.y()) {
                            eu[c.x() - a.x()]++;
                        }
                        if (by >= b.y()) {
                            ed[c.x() - a.x()]++;
                        }
                    }
                }
            }
//                if (area.toString().equals("Area[a=Point2[x=2, y=3], b=Point2[x=9, y=5]]")) {
//                    System.out.println();
//                }
            for (int j = 1; j < el.length - 1; j++) {
                if (el[j] % 2 != 1 || er[j] % 2 != 1) {
                    continue areas;
                }
            }
            for (int j = 1; j < eu.length - 1; j++) {
                if (eu[j] % 2 != 1 && ed[j] % 2 != 1) {
                    continue areas;
                }
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
