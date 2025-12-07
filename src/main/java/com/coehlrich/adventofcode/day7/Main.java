package com.coehlrich.adventofcode.day7;

import com.coehlrich.adventofcode.Day;
import com.coehlrich.adventofcode.Result;
import it.unimi.dsi.fastutil.ints.*;

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

        Int2LongMap beams = new Int2LongOpenHashMap();
        beams.put(startX, 1);
        int part1 = 0;
        for (int y = startY; y < map.length; y++) {
            Int2LongMap add = new Int2LongOpenHashMap();
            Int2LongMap remove = new Int2LongOpenHashMap();
            IntSet splitters = splittersByRow.get(y);
            for (Int2LongMap.Entry beam : beams.int2LongEntrySet()) {
                if (splitters.contains(beam.getIntKey())) {
                    int x = beam.getIntKey();
                    long amount = beam.getLongValue();
                    remove.put(beam.getIntKey(), beam.getLongValue());
                    add.put(x - 1, add.get(x - 1) + amount);
                    add.put(x + 1, add.get(x + 1) + amount);
                    part1++;
                }
            }
            for (Int2LongMap.Entry toAdd : add.int2LongEntrySet()) {
                beams.put(toAdd.getIntKey(), beams.get(toAdd.getIntKey()) + toAdd.getLongValue());
            }
            for (Int2LongMap.Entry toRemove : remove.int2LongEntrySet()) {
                beams.put(toRemove.getIntKey(), beams.get(toRemove.getIntKey()) - toRemove.getLongValue());
                if (beams.get(toRemove.getIntKey()) <= 0) {
                    beams.remove(toRemove.getIntKey());
                }
            }
        }
        return new Result(part1, beams.values().longStream().sum());
    }

}
