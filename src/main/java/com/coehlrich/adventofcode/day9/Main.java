package com.coehlrich.adventofcode.day9;

import com.coehlrich.adventofcode.Day;
import com.coehlrich.adventofcode.Result;
import com.coehlrich.adventofcode.util.Point2;

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
            }
        }
        return new Result(part1, 0);
    }

}
