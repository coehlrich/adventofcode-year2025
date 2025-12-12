package com.coehlrich.adventofcode.day12;

import com.coehlrich.adventofcode.Day;
import com.coehlrich.adventofcode.Result;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main implements Day {

    @Override
    public Result execute(String input) {
        String[] parts = input.split("\n\n");

        Int2ObjectMap<boolean[][]> shapes = new Int2ObjectOpenHashMap<>();
        int[] filledSpace = new int[parts.length - 1];
        for (int i = 0; i < parts.length - 1; i++) {
            int count = 0;
            String[] lines = parts[i].lines().toArray(String[]::new);
            int index = Integer.parseInt(lines[0].replace(":", ""));
            boolean[][] map = new boolean[lines.length - 1][lines.length - 1];
            for (int y = 1; y < lines.length; y++) {
                String line = lines[y];
                for (int x = 0; x < line.length(); x++) {
                    boolean filled = line.charAt(x) == '#';
                    map[y - 1][x] = filled;
                    if (filled) {
                        count++;
                    }
                }
            }
            filledSpace[index] = count;
            shapes.put(index, map);
        }

        int part1 = 0;
        for (String line : parts[parts.length - 1].lines().toArray(String[]::new)) {
            String[] lineParts = line.split(": ");
            String[] size = lineParts[0].split("x");
            int width = Integer.parseInt(size[0]);
            int height = Integer.parseInt(size[1]);

            int[] fit = Stream.of(lineParts[1].split(" ")).mapToInt(Integer::parseInt).toArray();

            int filled = 0;
            for (int i = 0; i < fit.length; i++) {
                filled += fit[i] * filledSpace[i];
            }
            int empty = (width * height) - filled;
            if (empty < 0) {
                continue;
            }
            part1++;
        }
        return new Result(part1, "N/A");
    }

}
