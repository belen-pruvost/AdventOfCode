package org.belen.years.twothousandtwentytwo;

import org.belen.generics.Day;

import java.util.*;

public class Day07 implements Day {
//--- Day 7: No Space Left On Device ---

    class File {
        String name;
        long size;
        String directory;

        public File(String directory, String line) {
            this.directory = directory;
            String[] split = line.split(" ");
            this.name = split[1];
            this.size = Long.parseLong(split[0]);
        }
    }

    class Directory {
        String name;
        String parent;
        List<File> files = new ArrayList<>();
        List<Directory> directories = new ArrayList<>();

        public Directory(String parent, String name) {
            this.parent = parent;
            this.name = name;
        }

        public long getSize() {
            long total = 0;
            total += files.stream().map(f -> f.size).reduce(0L, (a, b) -> a + b);
            total += directories.stream().map(d -> d.getSize()).reduce(0L, (a, b) -> a + b);
            return total;
        }

    }

    Map<String, Directory> directories = new HashMap<>();
    String currentDirectory = "/";
    String[] output;

    @Override
    public String getFilePath() {
        return "/Users/belen/study/advent/input/day7.txt";
    }

    @Override
    public void initialize() {
        output = getInputFile().split("\n");
        directories.put(currentDirectory, new Directory(null, currentDirectory));
        for (int i = 1; i < output.length; i++) {
            i = handleCommand(i) - 1;
        }
    }

    private int handleCommand(int i) {
        if (output[i].equals("$ ls")) {
            // read content
            i++;
            while (i < output.length && !output[i].startsWith("$")) {
                if (output[i].startsWith("dir")) {
                    // directory
                    String newDirectoryName = currentDirectory + output[i].split(" ")[1];
                    Directory newDirectory = new Directory(currentDirectory, newDirectoryName);
                    if (directories.containsKey(newDirectoryName)) {
                        break;
                    }
                    directories.put(newDirectoryName, newDirectory);
                    directories.get(currentDirectory).directories.add(newDirectory);
                } else {
                    // file
                    File file = new File(currentDirectory, output[i]);
                    directories.get(currentDirectory).files.add(file);
                }
                i++;
            }
        } else if (output[i].equals("$ cd ..")) {
            // move back
            currentDirectory = directories.get(currentDirectory).parent;
            i++;
        } else {
            // move into directory
            String[] split = output[i].split(" ");
            currentDirectory = currentDirectory + split[2];
            i++;
        }

        return i;
    }

    @Override
    public void handlePart1() {
        long result = directories.entrySet().stream()
                .map(d -> d.getValue().getSize())
                .filter(r -> r <= 100000)
                .reduce(0L, (a, b) -> a + b);
        System.out.println("Part 1: " + result);
    }

    @Override
    public void handlePart2() {
        long used = directories.get("/").getSize();
        long unused = 70000000 - used;
        long needed = 30000000 - unused;
        PriorityQueue<Long> maxHeap = new PriorityQueue<>();
        directories.entrySet()
                .stream()
                .filter(d -> d.getValue().getSize() >= needed)
                .forEach(d -> maxHeap.add(d.getValue().getSize()));
        System.out.println("Part 2: " + maxHeap.remove());
    }

    public static void main(String[] args) {
        Day day = new Day07();
        day.initialize();
        day.handlePart1();
        day.handlePart2();
    }
}
