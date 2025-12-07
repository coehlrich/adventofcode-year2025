package com.coehlrich.adventofcode.day7;

import com.coehlrich.adventofcode.Day;
import com.coehlrich.adventofcode.Result;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;

public class Main implements Day {

    @Override
    public Result execute(String input) {
        char[][] map = input.lines()
                .map(String::toCharArray)
                .toArray(char[][]::new);

        int startY = -1;
        int startX = -1;
        Int2ObjectMap<IntSet> splittersByRow = new Int2ObjectOpenHashMap<>();
        for (int y = 0; y < map.length; y++) {
            IntSet splitter = new IntOpenHashSet();
            for (int x = 0; x < map[y].length; x++) {
                switch (map[y][x]) {
                    case 'S' -> {
                        startY = y;
                        startX = x;
                    }
                    case '^' -> {
                        splitter.add(x);
                    }
                }
            }
            splittersByRow.put(y, splitter);
        }

        IntSet beams = new IntOpenHashSet();
        beams.add(startX);
        int part1 = 0;
        for (int y = startY; y < map.length; y++) {
            IntSet add = new IntOpenHashSet();
            IntSet remove = new IntOpenHashSet();
            IntSet splitters = splittersByRow.get(y);
            for (int beam : beams) {
                if (splitters.contains(beam)) {
                    remove.add(beam);
                    add.add(beam - 1);
                    add.add(beam + 1);
                    part1++;
                }
            }
            beams.addAll(add);
            beams.removeAll(remove);
        }
        return new Result(part1, 0);
    }

}
