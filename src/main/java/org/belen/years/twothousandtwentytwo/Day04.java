package org.belen.years.twothousandtwentytwo;

import org.belen.generics.Day;

import java.util.ArrayList;
import java.util.List;

public class Day04 implements Day {
    //--- Day 4: Camp Cleanup ---
    class Assignment {
        long start;
        long end;

        public Assignment(String line) {
            String[] parts = line.split("-");
            this.start = Long.parseLong(parts[0]);
            this.end = Long.parseLong(parts[1]);
        }

        public Assignment(long start, long end) {
            this.start = start;
            this.end = end;
        }
    }

    class Pair {
        Assignment first;
        Assignment second;

        public Pair(Assignment first, Assignment second) {
            this.first = first;
            this.second = second;
        }

        public boolean fullyContains() {
            if (first.start <= second.start && first.end >= second.end) {
                return true;
            }
            if (second.start <= first.start && second.end >= first.end) {
                return true;
            }
            return false;
        }

        public boolean overlaps() {
            if (first.start <= second.start && first.end >= second.end) {
                return true;
            }
            if (second.start <= first.start && second.end >= first.end) {
                return true;
            }
            if (first.start <= second.start && first.end >= second.start) {
                return true;
            }
            if (second.start <= first.start && second.end >= first.start) {
                return true;
            }
            return false;
        }
    }

    List<Pair> pairs = new ArrayList<>();

    @Override
    public String getFilePath() {
        return "/Users/belen/study/advent/input/day4.txt";
    }

    @Override
    public void initialize() {
        String[] split = getInputFile().split("\n");
        for (String s : split) {
            String[] parts = s.split(",");
            Assignment first = new Assignment(parts[0]);
            Assignment second = new Assignment(parts[1]);
            pairs.add(new Pair(first, second));
        }
    }

    @Override
    public void handlePart1() {
        int total = 0;
        for (Pair pair : pairs) {
            if (pair.fullyContains()) {
                total++;
            }
        }
        System.out.println("Part 1: " + total);
    }

    @Override
    public void handlePart2() {
        int total = 0;
        for (Pair pair : pairs) {
            if (pair.overlaps()) {
                total++;
            }
        }
        System.out.println("Part 2: " + total);
    }

    public static void main(String[] args) {
        Day day = new Day04();
        day.initialize();
        day.handlePart1();
        day.handlePart2();
    }
}
