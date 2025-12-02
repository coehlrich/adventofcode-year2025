package com.coehlrich.adventofcode.day2;

import com.coehlrich.adventofcode.Day;
import com.coehlrich.adventofcode.Result;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main implements Day {
    private static final long[] POWER_10 = IntStream.rangeClosed(0, 18).mapToLong(value -> (long) Math.pow(10, value)).toArray();

    @Override
    public Result execute(String input) {
        System.out.println(Math.log10(Long.MAX_VALUE));
        long[][] ranges = Stream.of(input.replace("\n", "").split(","))
                .map(range -> Stream.of(range.split("-"))
                        .mapToLong(Long::parseLong)
                        .toArray())
                .toArray(long[][]::new);
        long part1 = 0;
        long part2 = 0;
        for (long[] range : ranges) {
            double log10 = Math.log10(range[0]);
            int digits = (int) Math.floor(log10) + 1;
            long power10 = POWER_10[digits];
//            if (Math.floor(Math.log10(range[0])) != Math.floor(Math.log10(range[1]))) {
//                System.out.println(range[0]);
//                System.out.println(range[1]);
//            }
            for (long value = range[0]; value <= range[1]; value++) {
                if (power10 <= value) {
                    log10 = Math.log10(value);
                    digits = (int) Math.floor(log10) + 1;
                    power10 = POWER_10[digits];
//                    System.out.println("test");
                }
                if (invalid(value, false, digits)) {
                    part1 += value;
//                    System.out.println(value);
                }
                if (invalid(value, true, digits)) {
                    part2 += value;
                }
            }
        }
        return new Result(part1, part2);
    }

    private boolean invalid(long value, boolean part2, int digits) {

        if (!part2 && digits % 2 != 0) {
            return false;
        }
        loop:
        for (int i = part2 ? 1 : digits / 2; i <= digits / 2; i++) {
            if (i > 0 && digits % i == 0) {
                long tmp = value;
                long power10 = POWER_10[i];
                long currentValue = -1;
                while (tmp != 0) {
                    if (currentValue == -1) {
                        currentValue = tmp % power10;
                    } else if (currentValue != tmp % power10) {
                        continue loop;
                    }
                    tmp /= power10;
                }
                return true;
            }
        }
        return false;

    }

}
