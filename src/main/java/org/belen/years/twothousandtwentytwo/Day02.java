package org.belen.years.twothousandtwentytwo;

import org.belen.generics.Day;

import java.util.ArrayList;
import java.util.List;

public class Day02 implements Day {
//--- Day 2: Rock Paper Scissors ---

    List<Play> plays = new ArrayList<>();
    class Play {
        OpponentMove opponentMove;
        MyMove myMove;
        Result result;

        public Play(String opponent, String mine) {
            this.opponentMove = OpponentMove.valueOf(opponent);
            this.myMove = MyMove.valueOf(mine);
            this.result = Result.valueOf(mine);
        }

        public int outcome() {
            if (opponentMove.equals(OpponentMove.A)) {
                if (myMove.equals(MyMove.Y)) {
                    return 6;
                }
                if (myMove.equals(MyMove.X)) {
                    return 3;
                }
                return 0;
            }
            if (opponentMove.equals(OpponentMove.B)) {
                if (myMove.equals(MyMove.Z)) {
                    return 6;
                }
                if (myMove.equals(MyMove.Y)) {
                    return 3;
                }
                return 0;
            }

            if (myMove.equals(MyMove.X)) {
                return 6;
            }
            if (myMove.equals(MyMove.Z)) {
                return 3;
            }
            return 0;
        }

    }
    enum OpponentMove {
        A("Rock",1),
        B("Paper",2),
        C("Scissors", 3);
        private String name;
        private int value;

        OpponentMove(String name, int value) {
            this.name = name;
            this.value = value;
        }
    }

    enum MyMove {
        X("Rock",1),
        Y("Paper",2),
        Z("Scissors", 3);
        private String name;
        private int value;

        MyMove(String name, int value) {
            this.name = name;
            this.value = value;
        }
    }

    enum Result {
        X("Lose",0),
        Y("Draw",3),
        Z("Win", 6);
        private String name;
        private int value;

        Result(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public MyMove move (OpponentMove opponentMove) {
            if (this.equals(Y)) {
                if (opponentMove.equals(OpponentMove.A)) {
                    return MyMove.X;
                }
                if (opponentMove.equals(OpponentMove.B)) {
                    return MyMove.Y;
                }
                return MyMove.Z;
            }
            if (this.equals(X)) {
                if (opponentMove.equals(OpponentMove.A)) {
                    return MyMove.Z;
                }
                if (opponentMove.equals(OpponentMove.B)) {
                    return MyMove.X;
                }
                return MyMove.Y;
            }
            if (opponentMove.equals(OpponentMove.A)) {
                return MyMove.Y;
            }
            if (opponentMove.equals(OpponentMove.B)) {
                return MyMove.Z;
            }
            return MyMove.X;
        }
    }

    @Override
    public String getFilePath() {
        return "/Users/belen/study/advent/input/day2.txt";
    }

    @Override
    public void initialize() {
        String[] split = getInputFile().split("\n");
        for (String s:split) {
            plays.add(new Play(s.substring(0,1), s.substring(2)));
        }
    }

    @Override
    public void handlePart1() {
        long total = 0;
        for (Play play:plays) {
            total += play.outcome() + play.myMove.value;
        }
        System.out.println("Part 1: " + total);
    }

    @Override
    public void handlePart2() {
        long total = 0;
        for (Play play:plays) {
            MyMove mine = play.result.move(play.opponentMove);
            total += play.result.value + mine.value;
        }
        System.out.println("Part 2: " + total);
    }

    public static void main(String[] args) {
        Day day = new Day02();
        day.initialize();
        day.handlePart1();
        day.handlePart2();
    }
}
