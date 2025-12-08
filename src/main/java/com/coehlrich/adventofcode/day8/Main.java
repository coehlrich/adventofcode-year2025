package com.coehlrich.adventofcode.day8;

import com.coehlrich.adventofcode.Day;
import com.coehlrich.adventofcode.Result;
import com.coehlrich.adventofcode.util.Point3;
import it.unimi.dsi.fastutil.Pair;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main implements Day {

    private static final Pattern PATTERN = Pattern.compile("(\\d+),(\\d+),(\\d+)");

    @Override
    public Result execute(String input) {
        Map<Point3, Set<Point3>> circuits = new HashMap<>();
        Set<Point3> junctions = input.lines()
                .map(PATTERN::matcher)
                .map(matcher -> {
                    matcher.matches();
                    return new Point3(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)));
                })
                .collect(Collectors.toSet());

        for (Point3 junction : junctions) {
            circuits.put(junction, new HashSet<>(Set.of(junction)));
        }

        List<Pair<Point3, Point3>> pairs = junctions.stream()
                .flatMap(junction -> junctions.stream()
                        .filter(o -> junction != o)
                        .map(o -> Set.of(junction, o)))
                .distinct()
                .map(set -> {
                    Iterator<Point3> it = set.iterator();
                    return Pair.of(it.next(), it.next());
                })
                .sorted(Comparator.comparingLong(pair -> distance(pair.left(), pair.right())))
                .toList();
        int j = 0;
        int part1 = 0;
        int part2 = 0;
        for (int i = 0; i < junctions.size() - 1; j++) {
            Pair<Point3, Point3> pair = pairs.get(j);
//            System.out.println(pair);
            Set<Point3> leftCircuit = circuits.get(pair.left());
            Set<Point3> rightCircuit = circuits.get(pair.right());
            if (leftCircuit != rightCircuit) {
                i++;
                leftCircuit.addAll(rightCircuit);
                for (Point3 right : rightCircuit) {
                    circuits.put(right, leftCircuit);
                }
                part2 = pair.left().x() * pair.right().x();
            }
            if (j == 999) {
                List<Set<Point3>> sorted = circuits.values().stream().distinct().sorted(Comparator.comparingInt(Set::size)).toList();
                part1 = sorted.get(sorted.size() - 1).size() * sorted.get(sorted.size() - 2).size() * sorted.get(sorted.size() - 3).size();
            }
        }
//        System.out.println(pairs.get(0));
//        for (Set<Point3> circuit : sorted) {
//            System.out.println(circuit.size());
//        }

        return new Result(part1, part2);
    }

    private long distance(Point3 pos1, Point3 pos2) {
        long x = pos1.x() - pos2.x();
        long y = pos1.y() - pos2.y();
        long z = pos1.z() - pos2.z();
        return x * x + y * y + z * z;
    }

}
