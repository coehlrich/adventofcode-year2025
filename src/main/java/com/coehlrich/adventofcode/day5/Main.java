package com.coehlrich.adventofcode.day5;

import com.coehlrich.adventofcode.Day;
import com.coehlrich.adventofcode.Result;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Main implements Day {

    @Override
    public Result execute(String input) {
        String[] parts = input.split("\n\n");
        List<Range> fresh = parts[0].lines()
                .map(line -> line.split("-"))
                .map(line -> new Range(Long.parseLong(line[0]), Long.parseLong(line[1])))
                .sorted()
                .collect(Collectors.toList());

        int part1 = 0;
        for (long value : parts[1].lines().mapToLong(Long::parseLong).toArray()) {
            for (Range range : fresh) {
                if (range.start <= value && value <= range.end) {
                    part1++;
                    break;
                } else if (value < range.start) {
                    break;
                }
            }
        }

        List<Range> newRanges = new ArrayList<>();
        long start = -1;
        long end = -1;
        for (Range range : fresh) {
            if (start == -1 || end < range.start) {
                if (start != -1) {
                    newRanges.add(new Range(start, end));
                }
                start = range.start;
            }

            end = Math.max(end, range.end);
        }
        newRanges.add(new Range(start, end));
//        System.out.println(newRanges.stream().map(Object::toString).collect(Collectors.joining("\n")));

        long part2 = 0;
        for (Range range : newRanges) {
            part2 += range.end - range.start + 1;
        }
        return new Result(part1, part2);
    }

    public record Range(long start, long end) implements Comparable<Range> {

        @Override
        public int compareTo(Range o) {
            int compare = Long.compare(start, o.start);
            if (compare == 0) {
                compare = Long.compare(end, o.end);
            }
            return compare;
        }

    }

}
