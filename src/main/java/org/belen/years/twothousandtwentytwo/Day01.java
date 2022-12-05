package org.belen.years.twothousandtwentytwo;

import org.belen.generics.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Day01 implements Day {
    List<Long> caloriesPerElf = new ArrayList<>();

    @Override
    public void initialize() {
        String text = getInputFile();

        String[] split = text.split("\n");
        long partial = 0;
        for (int i = 0; i < split.length; i++) {
            if (!split[i].isEmpty()) {
                partial += Long.parseLong(split[i]);
            }

            if (split[i].isEmpty() || i == split.length - 1) {
                caloriesPerElf.add(partial);
                partial = 0;
            }
        }
    }

    @Override
    public String getFilePath() {
        return "/Users/belen/study/advent/input/day1.txt";
    }

    @Override
    public void handlePart1() {
        long maxCalories = 0;

        for (long elf:caloriesPerElf) {
            maxCalories = Math.max(maxCalories, elf);
        }
        System.out.println("Part 1: " + maxCalories);

    }

    @Override
    public void handlePart2() {
        PriorityQueue<Long> maxCalories = new PriorityQueue<>();

        for (long elf:caloriesPerElf) {
            maxCalories.add(elf);
            if (maxCalories.size() > 3) {
                maxCalories.remove();
            }
        }

        long total = 0;
        while(!maxCalories.isEmpty()) {
            total += maxCalories.remove();
        }
        System.out.println("Part 2: "+ total);
    }

    public static void main(String[] args) {
        Day day = new Day01();
        day.initialize();
        day.handlePart1();
        day.handlePart2();
    }
}
