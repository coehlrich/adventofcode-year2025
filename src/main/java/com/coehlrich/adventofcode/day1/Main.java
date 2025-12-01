package com.coehlrich.adventofcode.day1;

import com.coehlrich.adventofcode.Day;
import com.coehlrich.adventofcode.Result;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main implements Day {

    private static final Pattern PATTERN = Pattern.compile("(R|L)(\\d+)");

    @Override
    public Result execute(String input) {
        int pos = 50;
        int part1 = 0;
        int part2 = 0;
        boolean initial = false;
        for (String line : input.lines().toList()) {
            Matcher matcher = PATTERN.matcher(line);
            matcher.matches();
            char direction = matcher.group(1).charAt(0);
            int amount = Integer.parseInt(matcher.group(2));
            amount = direction == 'L' ? -amount : amount;
            pos += amount;
            if (pos >= 100) {
                part2 += pos / 100;
            } else if (pos <= 0) {
                part2 += -pos / 100 + 1;
                if (initial) {
                    part2--;
                }
            }
            initial = false;
            System.out.println(pos + " " + part2);
            pos = ((pos % 100) + 100) % 100;
            if (pos == 0) {
                initial = true;
                part1++;
            }
        }
        return new Result(part1, part2);
    }

}
