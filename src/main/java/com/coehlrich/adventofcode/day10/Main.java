package com.coehlrich.adventofcode.day10;

import com.coehlrich.adventofcode.Day;
import com.coehlrich.adventofcode.Result;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Main implements Day {

    private static final Pattern PATTERN = Pattern.compile("\\[([.#]+)] ((?:\\([\\d,]+\\) )+)\\{([\\d,]+)\\}");

    @Override
    public Result execute(String input) {
        List<Machine> machines = input.lines().map(Machine::parse).toList();
        int part1 = 0;
        for (Machine machine : machines) {
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < Math.pow(2, machine.buttons.size()); i++) {
                List<IntSet> buttons = machine.buttons;
                boolean[] current = new boolean[machine.target.length];
                int count = 0;
                for (int j = 0; j < buttons.size(); j++) {
                    if ((i & (1 << j)) != 0) {
                        count++;
                        for (int light : buttons.get(j)) {
                            current[light] = !current[light];
                        }
                    }
                }
                if (Arrays.equals(current, machine.target)) {
                    min = Math.min(count, min);
                }
            }
            part1 += min;
        }
        return new Result(part1, 0);
    }

    public record Machine(boolean[] target, List<IntSet> buttons, IntSet joltage) {
        public static Machine parse(String line) {
            Matcher matcher = PATTERN.matcher(line);
            matcher.matches();
            String targetString = matcher.group(1);
            boolean[] target = new boolean[targetString.length()];
            for (int i = 0; i < target.length; i++) {
                target[i] = targetString.charAt(i) == '#';
            }

            List<IntSet> buttons = new ArrayList<>();
            for (String part : matcher.group(2).split(" ")) {
                part = part.replace("(", "").replace(")", "");
                buttons.add(new IntOpenHashSet(
                        Stream.of(part.split(","))
                                .mapToInt(Integer::parseInt)
                                .toArray()));
            }

            IntSet joltage = new IntOpenHashSet(
                    Stream.of(matcher.group(3).split(","))
                            .mapToInt(Integer::parseInt)
                            .toArray());
            return new Machine(target, buttons, joltage);
        }
    }

}
