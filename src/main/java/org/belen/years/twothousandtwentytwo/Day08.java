package org.belen.years.twothousandtwentytwo;

import org.belen.generics.Day;

import java.util.*;

public class Day08 implements Day {
    // --- Day 8: Treetop Tree House ---
    @Override
    public String getFilePath() {
        return "/Users/belen/study/advent/input/day8.txt";
    }

    List<List<Integer>> grid = new ArrayList<>();
    int m = 0;
    int n = 0;

    @Override
    public void initialize() {
        String[] split = getInputFile().split("\n");
        m = split.length;
        n = split[0].length();

        for (String s : split) {
            List<Integer> list = new ArrayList<>();
            for (Character c : s.toCharArray()) {
                list.add(c - '0');
            }
            grid.add(list);
        }
    }

    static class Pair {
        int first;
        int second;

        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return first == pair.first && second == pair.second;
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, second);
        }
    }

    @Override
    public void handlePart1() {
        int visible = 2 * n + (m - 2) * 2;
        Set<Pair> visibles = new HashSet<>();

        int[][] newGridFromTheTop = new int[m][n];
        int[][] newGridFromTheBottom = new int[m][n];
        int[][] newGridFromTheLeft = new int[m][n];
        int[][] newGridFromTheRight = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                newGridFromTheTop[i][j] = grid.get(i).get(j);
                newGridFromTheBottom[i][j] = grid.get(i).get(j);
                newGridFromTheLeft[i][j] = grid.get(i).get(j);
                newGridFromTheRight[i][j] = grid.get(i).get(j);
            }
        }

        // comparing from the top
        for (int i = 1; i < m - 1; i++) {
            for (int j = 1; j < n - 1; j++) {
                if (newGridFromTheTop[i][j] > newGridFromTheTop[i - 1][j]) {
                    visibles.add(new Pair(i, j));
                }
                newGridFromTheTop[i][j] = Math.max(newGridFromTheTop[i][j], newGridFromTheTop[i - 1][j]);
            }
        }

        // comparing from the left
        for (int i = 1; i < m - 1; i++) {
            for (int j = 1; j < n - 1; j++) {
                if (newGridFromTheLeft[i][j] > newGridFromTheLeft[i][j - 1]) {
                    visibles.add(new Pair(i, j));
                }
                newGridFromTheLeft[i][j] = Math.max(newGridFromTheLeft[i][j], newGridFromTheLeft[i][j - 1]);
            }
        }


        // comparing from the bottom
        for (int i = m - 2; i > 0; i--) {
            for (int j = 1; j < n - 1; j++) {
                if (newGridFromTheBottom[i][j] > newGridFromTheBottom[i + 1][j]) {
                    visibles.add(new Pair(i, j));
                }
                newGridFromTheBottom[i][j] = Math.max(newGridFromTheBottom[i][j], newGridFromTheBottom[i + 1][j]);
            }
        }

        // comparing from the right
        for (int i = m - 2; i > 0; i--) {
            for (int j = n - 2; j > 0; j--) {
                if (newGridFromTheRight[i][j] > newGridFromTheRight[i][j + 1]) {
                    visibles.add(new Pair(i, j));
                }
                newGridFromTheRight[i][j] = Math.max(newGridFromTheRight[i][j], newGridFromTheRight[i][j + 1]);
            }
        }


        System.out.println("Part 2: " + (visible + visibles.size()));

    }

    @Override
    public void handlePart2() {
        int[][] newGridFromTheTop = new int[m][n];
        int[][] newGridFromTheBottom = new int[m][n];
        int[][] newGridFromTheLeft = new int[m][n];
        int[][] newGridFromTheRight = new int[m][n];


        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0) {
                    newGridFromTheTop[i][j] = 0;
                } else if (i == 1) {
                    newGridFromTheTop[i][j] = 1;
                }

                if (i == m - 1) {
                    newGridFromTheBottom[i][j] = 0;
                } else if (i == m - 2){
                    newGridFromTheBottom[i][j] = 1;
                }

                if (j == 0) {
                    newGridFromTheLeft[i][j] = 0;
                } else if (j == 1) {
                    newGridFromTheLeft[i][j] = 1;
                }
                if (j == n - 1) {
                    newGridFromTheRight[i][j] = 0;
                } else if (j == n - 2) {
                    newGridFromTheRight[i][j] = 1;
                }
            }
        }

        Map<Integer, Integer> lastSeen;

        // comparing from the top
        for (int j = 0; j <= n - 1; j++) {
            int maxSeen = grid.get(1).get(j);
            int maxIndex = 1;
            lastSeen = new HashMap<>();
            for (int i = 2; i <= m - 1; i++) {
                if (grid.get(i).get(j) > grid.get(i - 1).get(j)) {
                    if (grid.get(i).get(j) > maxSeen) {
                        newGridFromTheTop[i][j] = newGridFromTheTop[maxIndex][j] + (i - maxIndex);
                        maxSeen = grid.get(i).get(j);
                        maxIndex = i;
                    } else {
                        // look for another max
                        int relativeMax = maxSeen;
                        int previousIndex = -1;
                        while (relativeMax >= grid.get(i).get(j)) {
                            if (lastSeen.containsKey(relativeMax)) {
                                previousIndex = Math.max(lastSeen.get(relativeMax), previousIndex);
                            }
                            relativeMax--;
                        }

                        if (previousIndex == -1) {
                            newGridFromTheTop[i][j] = newGridFromTheTop[i - 1][j] + 1;
                        } else {
                            newGridFromTheTop[i][j] = i - previousIndex;
                        }
                    }
                } else {
                    newGridFromTheTop[i][j] = 1;
                }

                lastSeen.put(grid.get(i).get(j), i);
            }
        }

        // comparing from the left
        for (int i = 0; i <= m - 1; i++) {
            int maxSeen = grid.get(i).get(1);
            int maxIndex = 1;
            lastSeen = new HashMap<>();
            for (int j = 2; j <= n - 1; j++) {
                if (grid.get(i).get(j) > grid.get(i).get(j - 1)) {
                    if (grid.get(i).get(j) > maxSeen) {
                        newGridFromTheLeft[i][j] = newGridFromTheLeft[i][maxIndex] + (j - maxIndex);
                        maxSeen = grid.get(i).get(j);
                        maxIndex = j;
                    } else {
                        // look for another max
                        int relativeMax = maxSeen;
                        int previousIndex = -1;
                        while (relativeMax >= grid.get(i).get(j)) {
                            if (lastSeen.containsKey(relativeMax)) {
                                previousIndex = Math.max(lastSeen.get(relativeMax), previousIndex);
                            }
                            relativeMax--;
                        }

                        if (previousIndex == -1) {
                            newGridFromTheLeft[i][j] = newGridFromTheLeft[i][j - 1] + 1;
                        } else {
                            newGridFromTheLeft[i][j] = j - previousIndex;
                        }
                    }
                } else {
                    newGridFromTheLeft[i][j] = 1;
                }

                lastSeen.put(grid.get(i).get(j), j);
            }
        }

        // comparing from the bottom
        for (int j = 0; j <= n - 1; j++) {
            int maxSeen = grid.get(m - 2).get(j);
            int maxIndex = m - 2;
            lastSeen = new HashMap<>();
            for (int i = m - 2; i >= 0; i--) {
                if (grid.get(i).get(j) > grid.get(i + 1).get(j)) {
                    if (grid.get(i).get(j) > maxSeen) {
                        newGridFromTheBottom[i][j] = newGridFromTheBottom[maxIndex][j] + (maxIndex - i);
                        maxSeen = grid.get(i).get(j);
                        maxIndex = i;
                    } else {
                        // look for another max
                        int relativeMax = maxSeen;
                        int previousIndex = 1000;
                        while (relativeMax >= grid.get(i).get(j)) {
                            if (lastSeen.containsKey(relativeMax)) {
                                previousIndex = Math.min(lastSeen.get(relativeMax), previousIndex);
                            }
                            relativeMax--;
                        }

                        if (previousIndex == 1000) {
                            newGridFromTheBottom[i][j] = newGridFromTheBottom[i+1][j] + 1;
                        } else {
                            newGridFromTheBottom[i][j] = previousIndex - i;
                        }
                    }
                } else {
                    newGridFromTheBottom[i][j] = 1;
                }
                lastSeen.put(grid.get(i).get(j), i);

            }
        }

        // comparing from the right
        for (int i = 0; i <= m - 1; i++) {
            int maxSeen = grid.get(i).get(n - 2);
            int maxIndex = n - 2;
            lastSeen = new HashMap<>();
            for (int j = n - 2; j >= 0; j--) {
                if (grid.get(i).get(j) > grid.get(i).get(j + 1)) {
                    if (grid.get(i).get(j) > maxSeen) {
                        newGridFromTheRight[i][j] = newGridFromTheRight[i][maxIndex] + (maxIndex - j);
                        maxSeen = grid.get(i).get(j);
                        maxIndex = j;
                    } else {
                        // look for another max
                        int relativeMax = maxSeen;
                        int previousIndex = 1000;
                        while (relativeMax >= grid.get(i).get(j)) {
                            if (lastSeen.containsKey(relativeMax)) {
                                previousIndex = Math.min(lastSeen.get(relativeMax), previousIndex);
                            }
                            relativeMax--;
                        }

                        if (previousIndex == 1000) {
                            newGridFromTheRight[i][j] = newGridFromTheRight[i][j+1] + 1;
                        } else {
                            newGridFromTheRight[i][j] = previousIndex - i;
                        }
                    }
                } else {
                    newGridFromTheRight[i][j] = 1;
                }
                lastSeen.put(grid.get(i).get(j), j);
            }
        }

        long max = 0;
        int maxI = 0;
        int maxJ = 0;
        for (int i = 0; i <= m - 1; i++) {
            for (int j = 0; j <= n - 1; j++) {
                if (newGridFromTheBottom[i][j] <= 0 || newGridFromTheLeft[i][j] <= 0 || newGridFromTheTop[i][j] <= 0 || newGridFromTheRight[i][j] <= 0) {
                    continue;
                }
                long current = newGridFromTheBottom[i][j] * newGridFromTheLeft[i][j] * newGridFromTheTop[i][j] * newGridFromTheRight[i][j];
                if (max < current) {
                    max = current;
                    maxI = i;
                    maxJ = j;
                }
            }
        }

        System.out.println("Part 2: " + max );
    }

    public static void main(String[] args) {
        Day day = new Day08();
        day.initialize();
        day.handlePart1();
        day.handlePart2();
    }
}
