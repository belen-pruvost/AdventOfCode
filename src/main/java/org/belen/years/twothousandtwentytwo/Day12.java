package org.belen.years.twothousandtwentytwo;

import org.belen.generics.Day;

import java.util.*;

import static org.belen.years.twothousandtwentytwo.Day08.Pair;

public class Day12 implements Day {
    //--- Day 12: Hill Climbing Algorithm ---
    @Override
    public String getFilePath() {
        return "/Users/belen/study/advent/input/day12.txt";
    }

    int[][] grid;
    int[][] memo;
    int m, n;
    Pair origin, target;

    Set<Pair> origins = new HashSet<>();

    @Override
    public void initialize() {
        String[] split = getInputFile().split("\n");
        m = split.length;
        n = split[0].length();
        grid = new int[m][n];
        memo = new int[m][n];


        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char c = split[i].charAt(j);
                if (c == 'S') {
                    grid[i][j] = 'a' - 'a';
                    origin = new Pair(i, j);
                } else if (c == 'E') {
                    grid[i][j] = 'z' - 'a';
                    target = new Pair(i, j);
                } else {
                    grid[i][j] = split[i].charAt(j) - 'a';
                }

                if (grid[i][j] == 0) {
                    origins.add(new Pair(i, j));
                }
            }
        }
    }

    @Override
    public void handlePart1() {
        Set<Pair> destinations = new HashSet<>();
        destinations.add(target);
        long steps = bfs(origin, destinations, false);
        System.out.println("Part 1: " + steps);
    }

    @Override
    public void handlePart2() {
        long min = Integer.MAX_VALUE;
        long steps = bfs(target, origins, true);
        min = Math.min(min, steps);

        System.out.println("Part 2: " + min);
    }

    private long bfs(Pair origin, Set<Pair> targets, boolean reversed) {
        Set<Pair> visited = new HashSet<>();
        Queue<Pair> queue = new LinkedList<>();
        visited.add(origin);
        queue.add(origin);

        long steps = 0;
        while (true) {
            Queue<Pair> nextLevel = new LinkedList<>();

            while (!queue.isEmpty()) {
                Pair current = queue.remove();

                if (targets.contains(current)) {
                    return steps;
                }
                getNeighbours(current)
                        .stream()
                        .filter(i -> isCorrect(i))
                        .filter(i -> isReachable(current, i, reversed))
                        .filter(i -> !visited.contains(i))
                        .forEach(i -> {
                            nextLevel.add(i);
                            visited.add(i);
                        });
            }
            steps++;
            queue.addAll(nextLevel);
        }
    }

    private List<Pair> getNeighbours(Pair current) {
        List<Pair> neighbours = new ArrayList<>();
        neighbours.add(new Pair(current.first - 1, current.second));
        neighbours.add(new Pair(current.first + 1, current.second));
        neighbours.add(new Pair(current.first, current.second + 1));
        neighbours.add(new Pair(current.first, current.second - 1));
        return neighbours;
    }

    private boolean isCorrect(Pair current) {
        return current.first >= 0 && current.first < m && current.second >= 0 && current.second < n;
    }

    private boolean isReachable(Pair current, Pair next, boolean reversed) {
        if (reversed) {
            return grid[current.first][current.second] - grid[next.first][next.second] <= 1;
        } else {
            return grid[next.first][next.second] - grid[current.first][current.second] <= 1;
        }
    }

    public static void main(String[] args) {
        Day day = new Day12();
        day.initialize();
        day.handlePart1();
        day.handlePart2();
    }
}
