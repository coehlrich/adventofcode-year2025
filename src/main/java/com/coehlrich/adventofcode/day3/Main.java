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
        int part1 = 0;
        for (IntList bank : banks) {
            int max = bank.subList(0, bank.size() - 1).intStream().max().getAsInt();
            IntList single = bank.subList(bank.indexOf(max) + 1, bank.size());
            part1 += max * 10 + single.intStream().max().getAsInt();
        }
        return new Result(part1, 0);
    }

}
