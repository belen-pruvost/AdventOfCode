package org.belen.years.twothousandtwentytwo;

import org.belen.generics.Day;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day03 implements Day {
//--- Day 3: Rucksack Reorganization ---
    List<RuckSack> ruckSacks = new ArrayList<>();
    List<ElvesGroup> groups = new ArrayList<>();
    class RuckSack {
        Compartment first;
        Compartment second;

        Character repeated;


        public RuckSack(String items) {
            this.first = new Compartment(1, items.substring(0, items.length() / 2));
            this.second = new Compartment(2, items.substring(items.length() / 2), this);
        }


        public int getPriority() {
            if (Character.isLowerCase(repeated)) {
                return repeated - 'a' + 1;
            }
            return repeated - 'A' + 27;
        }

        public Set<Character> getAllItems() {
            Set<Character> allItems = new HashSet<>(first.items);
            allItems.addAll(second.items);
            return allItems;
        }

    }

    class Compartment {
        int order;
        Set<Character> items;

        public Compartment(int order, String items) {
            this.order = order;
            this.items = new HashSet<>();
            for (Character c:items.toCharArray()) {
                this.items.add(c);
            }
        }

        public Compartment(int order, String items, RuckSack ruckSack) {
            this.order = order;
            this.items = new HashSet<>();
            for (Character c:items.toCharArray()) {
                this.items.add(c);
                if (ruckSack.first.items.contains(c)) {
                    ruckSack.repeated = c;
                }
            }
        }
    }

    class ElvesGroup {
        RuckSack elf1;
        RuckSack elf2;
        RuckSack elf3;

        Character badge;

        public ElvesGroup(List<String> rucksacks) {
            this.elf1 = new RuckSack(rucksacks.get(0));
            this.elf2 = new RuckSack(rucksacks.get(1));
            this.elf3 = new RuckSack(rucksacks.get(2));
        }

        public int getPriority() {
            setBadge();
            if (Character.isLowerCase(badge)) {
                return badge - 'a' + 1;
            }
            return badge - 'A' + 27;
        }

        private void setBadge() {
            Set<Character> firstItems = elf1.getAllItems();
            Set<Character> secondItems = elf2.getAllItems();
            Set<Character> thirdItems = elf3.getAllItems();

            for (Character item:firstItems) {
                if (secondItems.contains(item) && thirdItems.contains(item)) {
                    badge = item;
                    break;
                }
            }
        }
    }


    @Override
    public String getFilePath() {
        return "/Users/belen/study/advent/input/day3.txt";
    }

    @Override
    public void initialize() {
        String[] split = getInputFile().split("\n");
        List<String> group = new ArrayList<>();
        for (String s:split) {
            ruckSacks.add(new RuckSack(s));
            group.add(s);
            if (group.size() == 3) {
                groups.add(new ElvesGroup(group));
                group = new ArrayList<>();
            }
        }
    }

    @Override
    public void handlePart1() {
        int total = 0;
        for (RuckSack rucksack:ruckSacks) {
            total += rucksack.getPriority();
        }
        System.out.println("Part 1: " + total);
    }

    @Override
    public void handlePart2() {
        int total = 0;
        for (ElvesGroup group:groups) {
            total += group.getPriority();
        }
        System.out.println("Part 2: " + total);
    }

    public static void main(String[] args) {
        Day day = new Day03();
        day.initialize();
        day.handlePart1();
        day.handlePart2();
    }
}
