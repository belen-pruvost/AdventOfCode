package org.belen.years.twothousandtwentytwo;

import org.belen.generics.Day;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day10 implements Day {

    //    --- Day 10: Cathode-Ray Tube ---

    enum Command {
        NOOP("noop", 1),
        ADD_X("addx", 2);

        private String name;
        private int cycles;

        Command(String name, int cycles) {
            this.name = name;
            this.cycles = cycles;
        }

        public static Command retrieveByName(String name) {
            switch (name) {
                case "noop":
                    return Command.NOOP;
                case "addx":
                    return Command.ADD_X;
                default:
                    return null;
            }
        }

    }

    class Instruction {
        Command command;
        int value;

        public Instruction(String line) {
            String[] split = line.split(" ");
            command = Command.retrieveByName(split[0]);

            if (!command.equals(Command.NOOP)) {
                value = Integer.parseInt(split[1]);
            }
        }
    }


    List<Instruction> instructions = new ArrayList<>();

    @Override
    public String getFilePath() {
        return "/Users/belen/study/advent/input/day10.txt";
    }

    @Override
    public void initialize() {
        String[] lines = getInputFile().split("\n");
        for (String line : lines) {
            instructions.add(new Instruction(line));
        }
    }

    Map<Integer, Integer> cyclesAndValues = new HashMap<>();

    @Override
    public void handlePart1() {
        int x = 1;
        int original = 1;
        int cycles = 0;


        for (Instruction instruction : instructions) {
            cycles += instruction.command.cycles;
            for (int i = original; i < cycles; i++) {
                cyclesAndValues.put(i, x);
            }

            x += instruction.value;
            cyclesAndValues.put(cycles, x);
            original = cycles;
        }

        long total = 0;


        for(int i = 19; i < cyclesAndValues.size(); i += 40) {
            total += cyclesAndValues.get(i) * (i + 1);
        }


        System.out.println("Part 1: " + total);

    }

    @Override
    public void handlePart2() {
        System.out.println("Part 2: ");

        int maxCol = 40;
        int maxRow = 6;
        char[][] grid = new char[maxRow][maxCol];

        for(int i = 0; i < maxRow; i++) {
            for (int j = 0; j < maxCol; j++) {
                int cycle = i * maxCol + j;
                int x = cyclesAndValues.getOrDefault(cycle,1);

                if (Math.abs(j - x) <= 1) {
                    grid[i][j] = '#';
                } else {
                    grid[i][j] = '.';
                }
            }
        }


        for (int i = 0; i < maxRow; i++) {
            for (int j = 0; j < maxCol; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.print("\n");
        }
    }

    public static void main(String[] args) {
        Day day = new Day10();
        day.initialize();
        day.handlePart1();
        day.handlePart2();
    }
}
