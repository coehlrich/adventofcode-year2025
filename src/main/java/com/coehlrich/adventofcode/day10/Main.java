package com.coehlrich.adventofcode.day10;

import com.coehlrich.adventofcode.Day;
import com.coehlrich.adventofcode.Result;
import it.unimi.dsi.fastutil.ints.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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
        int part2 = 0;
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
            List<IntSet> buttons = machine.buttons.stream().sorted(Comparator.comparingInt(IntSet::size)).toList().reversed();
            Int2ObjectMap<IntSet> finalButtons = new Int2ObjectOpenHashMap<>();
            IntSet found = new IntOpenHashSet();
            for (int i = buttons.size() - 1; i >= 0; i--) {
                IntSet current = new IntOpenHashSet();
                IntSet button = buttons.get(i);
                for (int joltage : button) {
                    if (found.add(joltage)) {
                        current.add(joltage);
                    }
                }
                finalButtons.put(i, current);
            }
            part2 += part2(machine, buttons, new int[machine.joltage.length], 0, 0, Integer.MAX_VALUE, finalButtons);
//            System.out.println(part2);
            System.out.println(machine);
        }
        return new Result(part1, part2);
    }

    public int part2(Machine machine, List<IntSet> buttons, int[] current, int presses, int i, int min, Int2ObjectMap<IntSet> finalButtons) {
        if (presses > min) {
            return min;
        }
        IntSet button = buttons.get(i);
        int maxPresses = Integer.MAX_VALUE;
        boolean next = buttons.size() - 1 > i;
        IntSet required = finalButtons.get(i);
        int expected = -1;
        current = Arrays.copyOf(current, current.length);
        for (int joltage : button) {
            if (required.contains(joltage)) {
                if (expected == -1) {
                    expected = machine.joltage[joltage] - current[joltage];
                } else if (expected != machine.joltage[joltage] - current[joltage]) {
                    return Integer.MAX_VALUE;
                }
            } else {
                maxPresses = Math.min(maxPresses, machine.joltage[joltage] - current[joltage]);
            }
        }

        if (expected != -1) {
            for (int joltage : button) {
                current[joltage] += expected;
            }
            if (expected > maxPresses) {
                return Integer.MAX_VALUE;
            } else if (next) {
                return part2(machine, buttons, current, presses + expected, i + 1, min, finalButtons);
            } else {
                return presses + expected;
            }
        }

        for (int j = 0; j <= maxPresses; j++) {
            if (j > 0) {
                presses++;
                if (presses > min) {
                    return min;
                }
                for (int joltage : button) {
                    current[joltage]++;
                }
            }
            if (next) {
                min = Math.min(min, part2(machine, buttons, current, presses, i + 1, min, finalButtons));
            }
        }
        return min;
    }

    public record Machine(boolean[] target, List<IntSet> buttons, int[] joltage) {
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

            int[] joltage = Stream.of(matcher.group(3).split(","))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            return new Machine(target, buttons, joltage);
        }
    }

}
