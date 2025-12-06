package com.coehlrich.adventofcode.day6;

import com.coehlrich.adventofcode.Day;
import com.coehlrich.adventofcode.Result;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Main implements Day {

    @Override
    public Result execute(String input) {
        String[][] grid = input.lines()
                .map(line -> Stream.of(line.split(" "))
                        .filter(part -> !"".equals(part))
                        .toArray(String[]::new))
                .toArray(String[][]::new);

        List<Problem> part1 = new ArrayList<>();
        for (int x = 0; x < grid[0].length; x++) {
            char operation = grid[grid.length - 1][x].charAt(0);
            LongList values = new LongArrayList();
            for (int y = 0; y < grid.length - 1; y++) {
                values.add(Long.parseLong(grid[y][x]));
            }
            part1.add(new Problem(values, operation));
//            System.out.println(operation);
        }

        List<Problem> part2 = new ArrayList<>();
        String[] lines = input.lines().toArray(String[]::new);
        LongList currentValues = new LongArrayList();
        char operation = '\0';
        String operations = lines[lines.length - 1];
        for (int i = 0; i < operations.length(); i++) {
            if (operations.charAt(i) != ' ') {
                if (operation != '\0') {
                    part2.add(new Problem(currentValues, operation));
                    currentValues = new LongArrayList();
                }
                operation = operations.charAt(i);
            }
            long number = 0;
            for (int y = 0; y < lines.length - 1; y++) {
                char character = lines[y].charAt(i);
                if (character != ' ') {
                    number *= 10;
                    number += Character.digit(character, 10);
                }
            }
            if (number != 0) {
                currentValues.add(number);
            }
        }
        part2.add(new Problem(currentValues, operation));
        return new Result(calculate(part1), calculate(part2));
    }

    private long calculate(List<Problem> problems) {
        long total = 0;
        for (Problem problem : problems) {
            char operation = problem.operation;
            long result = operation == '*' ? 1 : 0;
            for (long value : problem.values) {
                result = switch (operation) {
                    case '*' -> result * value;
                    case '+' -> result + value;
                    default -> throw new IllegalArgumentException("Unexpected value: " + operation);
                };
            }
            total += result;
//            System.out.println(operation);
        }
        return total;
    }

    private record Problem(LongList values, char operation) {

    }

}
