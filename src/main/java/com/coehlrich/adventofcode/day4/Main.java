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

        int[][] counts = count(map);

        int part1 = remove(counts, false);

        int part2 = remove(counts, true);

        return new Result(part1, part2);
    }

    public int[][] count(boolean[][] map) {
        int[][] counts = new int[map.length][map[0].length];
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
                    counts[y][x] = count;
                } else {
                    counts[y][x] = -1;
                }
            }
//            System.out.println();
        }
        return counts;
    }

    public int remove(int[][] map, boolean modify) {
        int result = 0;
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] >= 0 && map[y][x] <= 3) {
                    if (modify) {
                        result += check(map, x, y);
                    } else {
                        result++;
                    }
                }
            }
        }
        return result;
    }

    public int check(int[][] map, int x, int y) {
        int result = 0;
        if (map[y][x] >= 0 && map[y][x] <= 3) {
            map[y][x] = -1;
            result = 1;
            for (int dy = -1; dy <= 1; dy++) {
                for (int dx = -1; dx <= 1; dx++) {
                    if ((dy != 0 || dx != 0)
                            && y + dy >= 0
                            && x + dx >= 0
                            && y + dy < map.length
                            && x + dx < map[0].length
                            && map[y + dy][x + dx] != -1) {
                        map[y + dy][x + dx]--;
                        result += check(map, x + dx, y + dy);
                    }
                }
            }
        }
        return result;
    }

}
