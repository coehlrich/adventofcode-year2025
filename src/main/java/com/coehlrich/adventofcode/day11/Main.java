package com.coehlrich.adventofcode.day11;

import com.coehlrich.adventofcode.Day;
import com.coehlrich.adventofcode.Result;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main implements Day {

    @Override
    public Result execute(String input) {
        Map<String, Set<String>> connections = new HashMap<>();
        for (String line : input.lines().toList()) {
            String[] split = line.split(": ");
            String device = split[0];
            Set<String> outputs = Stream.of(split[1].split(" ")).collect(Collectors.toSet());
            connections.put(device, outputs);
        }

        Object2IntMap<String> cache = new Object2IntOpenHashMap<>();

        return new Result(getPaths("you", cache, connections), 0);
    }

    public int getPaths(String value, Object2IntMap<String> cache, Map<String, Set<String>> connections) {
        if (value.equals("out")) {
            return 1;
        } else if (cache.containsKey(value)) {
            return cache.getInt(value);
        }
        int count = 0;
        if (connections.containsKey(value)) {
            for (String output : connections.get(value)) {
                count += getPaths(output, cache, connections);
            }
        }
        cache.put(value, count);
        return count;
    }

}
