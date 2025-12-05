package com.coehlrich.adventofcode.day5;

import com.coehlrich.adventofcode.Day;
import com.coehlrich.adventofcode.Result;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;

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
        return new Result(part1, 0);
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
