package com.coehlrich.adventofcode.day6;

import com.coehlrich.adventofcode.Day;
import com.coehlrich.adventofcode.Result;

import java.util.stream.Stream;

public class Main implements Day {

    @Override
    public Result execute(String input) {
        String[][] grid = input.lines()
                .map(line -> Stream.of(line.split(" "))
                        .filter(part -> !"".equals(part))
                        .toArray(String[]::new))
                .toArray(String[][]::new);

        long total = 0;
        for (int x = 0; x < grid[0].length; x++) {
            char operation = grid[grid.length - 1][x].charAt(0);
            long result = operation == '*' ? 1 : 0;
            for (int y = 0; y < grid.length - 1; y++) {
                long value = Long.parseLong(grid[y][x]);
                result = switch (operation) {
                    case '*' -> result * value;
                    case '+' -> result + value;
                    default -> throw new IllegalArgumentException("Unexpected value: " + operation);
                };
            }
            total += result;
//            System.out.println(operation);
        }
        return new Result(total, 0);
    }

}
