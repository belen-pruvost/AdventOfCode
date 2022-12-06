package org.belen.years.twothousandtwentytwo;

import org.belen.generics.Day;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Day06 implements Day {
//--- Day 6: Tuning Trouble ---
    String signal = "";
    @Override
    public String getFilePath() {
        return "/Users/belen/study/advent/input/day6.txt";
    }

    @Override
    public void initialize() {
        signal = getInputFile();
    }

    @Override
    public void handlePart1() {
        Map<Character, Integer> distinct = new HashMap<>();
        for(int i = 0; i < signal.length(); i++) {
            if (distinct.containsKey(signal.charAt(i))) {
                int index = distinct.get(signal.charAt(i));
                distinct.remove(signal.charAt(i));
                List<Entry> entries = distinct.entrySet().stream().filter(e -> e.getValue() < index).collect(Collectors.toList());
                entries.forEach(p -> distinct.remove(p.getKey()));
            }
            distinct.put(signal.charAt(i), i);
            if (distinct.size() == 4) {
                System.out.println("Part 1: " + (i + 1));
                break;
            }
        }

    }

    @Override
    public void handlePart2() {
        Map<Character, Integer> distinct = new HashMap<>();
        for(int i = 0; i < signal.length(); i++) {
            if (distinct.containsKey(signal.charAt(i))) {
                int index = distinct.get(signal.charAt(i));
                distinct.remove(signal.charAt(i));
                List<Entry> entries = distinct.entrySet().stream().filter(e -> e.getValue() < index).collect(Collectors.toList());
                entries.forEach(p -> distinct.remove(p.getKey()));
            }
            distinct.put(signal.charAt(i), i);
            if (distinct.size() == 14) {
                System.out.println("Part 2: " + (i + 1));
                break;
            }
        }
    }

    public static void main(String[] args) {
        Day day = new Day06();
        day.initialize();
        day.handlePart1();
        day.handlePart2();
    }
}
