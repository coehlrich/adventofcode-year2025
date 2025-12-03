package com.coehlrich.adventofcode.day3;

import java.util.List;
import java.util.stream.Stream;

import com.coehlrich.adventofcode.Day;
import com.coehlrich.adventofcode.Result;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;

public class Main implements Day {

    @Override
    public Result execute(String input) {
        List<IntList> banks = input.lines().map(line -> (IntList) new IntArrayList(Stream.of(line.split(""))
                .map(Integer::parseInt)
                .toList()))
                .toList();
        long part1 = 0;
        long part2 = 0;
        for (IntList bank : banks) {
            part1 += calculate(bank, 2);
            part2 += calculate(bank, 12);
        }
        return new Result(part1, part2);
    }

    private long calculate(IntList bank, int count) {
        long value = 0;
        int index = 0;
        for (int i = 0; i < count; i++) {
            value *= 10;
            IntList subList = bank.subList(index, bank.size() - (count - i - 1));
            int max = subList.intStream().max().getAsInt();
            index = subList.indexOf(max) + index + 1;
            value += max;
        }
        System.out.println(value);
        return value;
    }

}
