package org.belen.years.twothousandtwentytwo;

import org.belen.generics.Day;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Day05 implements Day {
    //--- Day 5: Supply Stacks ---
    List<LinkedList> firstPartStacks = new ArrayList<>();
    List<LinkedList> secondPartStacks = new ArrayList<>();
    List<Instruction> instructions = new ArrayList<>();

    class Instruction {
        int quantity;
        int origin;
        int destiny;

        public Instruction(int quantity, int origin, int destiny) {
            this.quantity = quantity;
            this.origin = origin;
            this.destiny = destiny;
        }
    }

    @Override
    public String getFilePath() {
        return "/Users/belen/study/advent/input/day5.txt";
    }

    @Override
    public void initialize() {
        String[] split = getInputFile().split("\n");
        int numberOfStacks = (split[0].length() + 1) / 4;
        for (int i = 0; i < numberOfStacks; i++) {
            LinkedList<Character> stack = new LinkedList();
            firstPartStacks.add(stack);
            stack = new LinkedList();
            secondPartStacks.add(stack);
        }
        boolean moves = false;
        for (int j = 0; j < split.length; j++) {
            String s = split[j];
            if (s.startsWith(" 1  ")) {
                j++;
                moves = true;
                continue;
            }
            if (moves) {
                String[] move = s.split(" ");
                Instruction instruction = new Instruction(Integer.parseInt(move[1]), Integer.parseInt(move[3]), Integer.parseInt(move[5]));
                instructions.add(instruction);
            } else {
                int pointer = 1;
                for (int i = 0; i < firstPartStacks.size(); i++) {
                    if (s.charAt(i + pointer) != ' ') {
                        firstPartStacks.get(i).addFirst(s.charAt(i + pointer));
                        secondPartStacks.get(i).addFirst(s.charAt(i + pointer));
                    }
                    pointer += 3;
                }
            }
        }
    }

    @Override
    public void handlePart1() {
        for (Instruction instruction : instructions) {
            for (int i = 0; i < instruction.quantity; i++) {
                Object toMove = firstPartStacks.get(instruction.origin - 1).removeLast();
                firstPartStacks.get(instruction.destiny - 1).addLast(toMove);
            }
        }

        StringBuilder result = new StringBuilder(firstPartStacks.size());
        for (LinkedList stack : firstPartStacks) {
            result.append(stack.getLast());
        }
        System.out.println("Part 1: " + result);
    }

    @Override
    public void handlePart2() {
        LinkedList stack = new LinkedList();
        for (Instruction instruction : instructions) {
            for (int i = 0; i < instruction.quantity; i++) {
                Object toMove = secondPartStacks.get(instruction.origin - 1).removeLast();
                stack.addFirst(toMove);
            }
            while (!stack.isEmpty()) {
                secondPartStacks.get(instruction.destiny - 1).addLast(stack.removeFirst());
            }
        }

        StringBuilder result = new StringBuilder(secondPartStacks.size());
        for (LinkedList s : secondPartStacks) {
            result.append(s.getLast());
        }
        System.out.println("Part 2: " + result);
    }

    public static void main(String[] args) {
        Day day = new Day05();
        day.initialize();
        day.handlePart1();
        day.handlePart2();
    }
}
