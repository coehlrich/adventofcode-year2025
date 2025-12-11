package com.coehlrich.adventofcode.day11;

import com.coehlrich.adventofcode.Day;
import com.coehlrich.adventofcode.Result;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;

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

        return new Result(getPaths("you", new Object2LongOpenHashMap<>(), connections, true, true), getPaths("svr", new Object2LongOpenHashMap<>(), connections, false, false));
    }

    public long getPaths(String value, Object2LongMap<State> cache, Map<String, Set<String>> connections, boolean dac, boolean fft) {
        State cacheKey = new State(value, dac, fft);
        if (value.equals("out")) {
            if (dac && fft) {
                return 1;
            } else {
                return 0;
            }
        } else if (cache.containsKey(cacheKey)) {
            return cache.getLong(cacheKey);
        }
        if (!dac && value.equals("dac")) {
            dac = true;
        } else if (!fft && value.equals("fft")) {
            fft = true;
        }
        long count = 0;
        if (connections.containsKey(value)) {
            for (String output : connections.get(value)) {
                count += getPaths(output, cache, connections, dac, fft);
            }
        }
        cache.put(cacheKey, count);
        return count;
    }

    public record State(String value, boolean dac, boolean fft) {
    }

}
