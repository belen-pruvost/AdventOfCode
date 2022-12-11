package org.belen.years.twothousandtwentytwo;

import org.belen.generics.Day;
import org.belen.years.twothousandtwentytwo.Day08.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day09 implements Day {
    //--- Day 9: Rope Bridge ---
    Set<Pair> visited = new HashSet<>();
    Set<Pair> visitedBy9 = new HashSet<>();
    List<Instruction> instructions = new ArrayList<>();

    class Instruction {
        char direction;
        int amount;

        public Instruction(String line) {
            this.direction = line.charAt(0);
            this.amount = Integer.parseInt(line.substring(2));
        }

        public Pair move() {
            if (direction == 'R') {
                return new Pair(0, 1);
            }

            if (direction == 'L') {
                return new Pair(0, -1);
            }

            if (direction == 'D') {
                return new Pair(1, 0);
            }

            if (direction == 'U') {
                return new Pair(-1, 0);
            }
            return null;
        }
    }

    @Override
    public String getFilePath() {
        return "/Users/belen/study/advent/input/day9.txt";
    }

    @Override
    public void initialize() {
        String[] split = getInputFile().split("\n");
        for (String s : split) {
            instructions.add(new Instruction(s));
        }

    }

    @Override
    public void handlePart1() {
        int column = 0;
        int row = 99;

        Pair headCurrentPlace = new Pair(row, column);
        Pair tailCurrentPlace = new Pair(row, column);
        visited.add(tailCurrentPlace);

        for (Instruction i : instructions) {
            int moves = i.amount;
            while (moves > 0) {
                Pair move = i.move();
                headCurrentPlace = new Pair(headCurrentPlace.first + move.first, headCurrentPlace.second + move.second);
                moves--;

                if (headCurrentPlace.equals(tailCurrentPlace)) {
                    continue;
                }

                if (headCurrentPlace.first == tailCurrentPlace.first &&
                        Math.abs(headCurrentPlace.second - tailCurrentPlace.second) == 2) {
                    if (i.direction == 'U') {
                        tailCurrentPlace = new Pair(tailCurrentPlace.first - 1, tailCurrentPlace.second);
                    } else if (i.direction == 'D') {
                        tailCurrentPlace = new Pair(tailCurrentPlace.first + 1, tailCurrentPlace.second + 1);
                    } else if (i.direction == 'L') {
                        tailCurrentPlace = new Pair(tailCurrentPlace.first, tailCurrentPlace.second - 1);
                    } else {
                        tailCurrentPlace = new Pair(tailCurrentPlace.first, tailCurrentPlace.second + 1);
                    }

                } else if (headCurrentPlace.second == tailCurrentPlace.second &&
                        Math.abs(headCurrentPlace.first - tailCurrentPlace.first) == 2) {
                    if (i.direction == 'U') {
                        tailCurrentPlace = new Pair(tailCurrentPlace.first - 1, tailCurrentPlace.second);
                    } else if (i.direction == 'D') {
                        tailCurrentPlace = new Pair(tailCurrentPlace.first + 1, tailCurrentPlace.second);
                    } else if (i.direction == 'L') {
                        tailCurrentPlace = new Pair(tailCurrentPlace.first, tailCurrentPlace.second - 1);
                    } else {
                        tailCurrentPlace = new Pair(tailCurrentPlace.first, tailCurrentPlace.second + 1);
                    }
                } else if (Math.abs(headCurrentPlace.first - tailCurrentPlace.first) <= 1 &&
                        Math.abs(headCurrentPlace.second - tailCurrentPlace.second) <= 1) {
                    continue;
                } else {
                    // move diagonally
                    if (headCurrentPlace.first < tailCurrentPlace.first && headCurrentPlace.second > tailCurrentPlace.second) {
                        tailCurrentPlace = new Pair(tailCurrentPlace.first - 1, tailCurrentPlace.second + 1);
                    } else if (headCurrentPlace.first < tailCurrentPlace.first && headCurrentPlace.second < tailCurrentPlace.second) {
                        tailCurrentPlace = new Pair(tailCurrentPlace.first - 1, tailCurrentPlace.second - 1);
                    } else if (headCurrentPlace.first > tailCurrentPlace.first && headCurrentPlace.second > tailCurrentPlace.second) {
                        tailCurrentPlace = new Pair(tailCurrentPlace.first + 1, tailCurrentPlace.second + 1);
                    } else {
                        tailCurrentPlace = new Pair(tailCurrentPlace.first + 1, tailCurrentPlace.second - 1);
                    }
                }

                visited.add(tailCurrentPlace);
            }
        }

        System.out.println("Part 1: " + visited.size());

    }

    @Override
    public void handlePart2() {
        int rope = 10;
        List<Pair> snake;

        snake = IntStream.range(0, rope).mapToObj(i ->  new Pair(0, 0)).collect(Collectors.toList());

        visitedBy9.add(snake.get(0));

        Pair headCurrentPlace =  new Pair(0, 0);

        for (Instruction instruction : instructions) {
            Pair move = instruction.move();

            headCurrentPlace = new Pair(headCurrentPlace.first + (move.first * instruction.amount),
                    headCurrentPlace.second + (move.second * instruction.amount));

            snake.set(rope - 1, headCurrentPlace);

            boolean moved;
            do {
                moved = false;

                for (int i = rope - 1; i > 0; i--) {
                    Pair head = snake.get(i);
                    Pair tail = snake.get(i - 1);

                    if (Math.sqrt(Math.pow(head.first - tail.first, 2) + Math.pow(head.second - tail.second, 2)) >= 2.0) {
                        int up = head.first < tail.first ? -1 : head.first == tail.first? 0 : 1;
                        int left = head.second < tail.second ? -1 : head.second == tail.second? 0 : 1;

                        snake.set(i - 1, new Pair(tail.first + up, tail.second + left));
                        moved = true;
                    }
                }

                visitedBy9.add(snake.get(0));
            } while (moved);
        }
        System.out.println("Part 2: " + visitedBy9.size());

    }

    public static void main(String[] args) {
        Day day = new Day09();
        day.initialize();
        day.handlePart1();
        day.handlePart2();
    }
}
