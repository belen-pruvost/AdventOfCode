package org.belen.years.twothousandtwentytwo;

import org.belen.generics.Day;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class Day11 implements Day {
    //--- Day 11: Monkey in the Middle ---
    @Override
    public String getFilePath() {
        return "/Users/belen/study/advent/input/day11.txt";
    }

    class Monkey {
        int name;
        long previousItems = 0;
        List<Long> items = new ArrayList<>();
        Function<Long, Long> operation;
        Predicate<Long> test;
        int throwToIfTrue;
        int throwToIfFalse;

        public Monkey(String[] lines) {
            String name = lines[0].replace("Monkey ", "").replace(":", "");
            this.name = Integer.parseInt(name);

            String[] items = lines[1].replace("  Starting items: ", "").replace(" ", "").split(",");
            for (String item : items) {
                this.items.add(Long.parseLong(item));
            }

            String operationString = lines[2].replace("  Operation: new = old ", "");

            if (operationString.charAt(0) == '+') {
                if (operationString.contains("old")) {
                    operation = i -> (i + i);
                } else {
                    operation = i -> (i + Integer.parseInt(operationString.substring(2)));
                }
            } else if (operationString.charAt(0) == '-') {
                if (operationString.contains("old")) {
                    operation = i -> (i - i);
                } else {
                    operation = i -> (i - Integer.parseInt(operationString.substring(2)));
                }
            } else if (operationString.charAt(0) == '*') {
                if (operationString.contains("old")) {
                    operation = i -> (i * i);
                } else {
                    operation = i -> (i * Integer.parseInt(operationString.substring(2)));
                }
            } else {
                if (operationString.contains("old")) {
                    operation = i -> (i / i);
                } else {
                    operation = i -> (i / Integer.parseInt(operationString.substring(2)));
                }
            }

            String divisible = lines[3].replace("  Test: divisible by ", "");
            test = i -> (i % Integer.parseInt(divisible) == 0);
            dividers.add(Long.parseLong(divisible));

            String ifTrue = lines[4].replace("    If true: throw to monkey ", "");
            String ifFalse = lines[5].replace("    If false: throw to monkey ", "");

            this.throwToIfTrue = Integer.parseInt(ifTrue);
            this.throwToIfFalse = Integer.parseInt(ifFalse);
        }

        public void inspect(boolean reduceWorry) {
            previousItems += items.size();
            Map<Integer, Monkey> map;

            for (int i = 0; i < items.size(); i++) {
                long item = items.get(i);
                item = this.operation.apply(item);
                if (reduceWorry) {
                    item /= 3;
                    map = mapOfMonkeysPart1;
                } else {
                    map = mapOfMonkeysPart2;
                }
                if (this.test.test(item)) {
                    map.get(throwToIfTrue).items.add(item % mod);
                } else {
                    map.get(throwToIfFalse).items.add(item % mod);
                }
            }
            items = new ArrayList<>();
        }

    }
    Set<Long> dividers = new HashSet<>();
    long mod;
    List<Monkey> monkeysPart1 = new ArrayList<>();
    Map<Integer, Monkey> mapOfMonkeysPart1 = new HashMap<>();

    List<Monkey> monkeysPart2 = new ArrayList<>();
    Map<Integer, Monkey> mapOfMonkeysPart2 = new HashMap<>();

    @Override
    public void initialize() {
        String[] split = getInputFile().split("\n");
        for (int i = 0; i < split.length - 5; i += 7) {
            Monkey monkey = new Monkey(Arrays.copyOfRange(split, i, i + 6));
            monkeysPart1.add(monkey);
            mapOfMonkeysPart1.put(monkey.name, monkey);

            monkey = new Monkey(Arrays.copyOfRange(split, i, i + 6));
            monkeysPart2.add(monkey);
            mapOfMonkeysPart2.put(monkey.name, monkey);
        }

        mod = dividers.stream().reduce(1L, (a, b) -> a * b);


    }

    @Override
    public void handlePart1() {
        int rounds = 20;

        goThroughRounds(rounds, monkeysPart1, true);

        System.out.println("Part 1: " + getProductOfMostActiveMonkeys(monkeysPart1));
    }

    @Override
    public void handlePart2() {
        int rounds = 10000;

        goThroughRounds(rounds, monkeysPart2, false);

        System.out.println("Part 2: " + getProductOfMostActiveMonkeys(monkeysPart2));
    }

    private void goThroughRounds(int rounds, List<Monkey> monkeysPart2, boolean reduceWorry) {
        for (int i = 0; i < rounds; i++) {
            for (Monkey monkey : monkeysPart2) {
                monkey.inspect(reduceWorry);
            }
        }
    }

    private long getProductOfMostActiveMonkeys(List<Monkey> monkeys) {
        PriorityQueue<Long> heap = new PriorityQueue<>();
        for (Monkey monkey : monkeys) {
            heap.add(monkey.previousItems);
            if (heap.size() > 2) {
                heap.remove();
            }
        }
        return heap.remove() * heap.remove();
    }

    public static void main(String[] args) {
        Day day = new Day11();
        day.initialize();
        day.handlePart1();
        day.handlePart2();
    }
}
