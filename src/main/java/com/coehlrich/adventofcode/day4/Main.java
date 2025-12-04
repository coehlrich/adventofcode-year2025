package com.coehlrich.adventofcode.day4;

import com.coehlrich.adventofcode.Day;
import com.coehlrich.adventofcode.Result;
import it.unimi.dsi.fastutil.ints.IntObjectPair;

public class Main implements Day {

    @Override
    public Result execute(String input) {
        String[] lines = input.split("\n");
        boolean[][] map = new boolean[lines.length][lines[0].length()];
        for (int y = 0; y < lines.length; y++) {
            for (int x = 0; x < lines[y].length(); x++) {
                map[y][x] = lines[y].charAt(x) == '@';
            }
        }

        int part1 = remove(map, false);

        int runs = 1;
        int removed = remove(map, true);
        int part2 = 0;
        while (removed != 0) {
            runs++;
            part2 += removed;
            removed = remove(map, true);
        }

        System.out.println(runs);
        return new Result(part1, part2);
    }

    public int remove(boolean[][] map, boolean modify) {
        int result = 0;
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x]) {
                    int count = 0;
                    for (int dy = -1; dy <= 1; dy++) {
                        for (int dx = -1; dx <= 1; dx++) {
                            if ((dy != 0 || dx != 0)
                                    && y + dy >= 0
                                    && x + dx >= 0
                                    && y + dy < map.length
                                    && x + dx < map[0].length
                                    && map[y + dy][x + dx]) {
                                count++;
                            }
                        }
                    }
                    if (count < 4) {
                        result++;
                        if (modify) {
                            map[y][x] = false;
                        }
//                        System.out.print('x');
                    } else {
//                        System.out.print('@');
                    }
                } else {
//                    System.out.print('.');
                }
            }
//            System.out.println();
        }
        return result;
    }

}
