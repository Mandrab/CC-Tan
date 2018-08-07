package it.unibo.oop.cctan.model;

public final class Score {

    private int points;

    private Score() {
        this.points = 0;
    }

    public void increment() {
        this.points++;
    }

    public int getPoints() {
        return this.points;
    }

    public static Score getScore() {
        return ScoreGenerator.SINGLETON;
    }

    private static class ScoreGenerator {
        private static final Score SINGLETON = new Score();
    }
}
