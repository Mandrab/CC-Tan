package it.unibo.oop.cctan.model;

/**
 * This class makes use of the SINGLETON pattern, so that there is only one object 
 * in the whole application. This object is used to keep the user's score during 
 * the current game. This implementation is also thread-safe.
 */
public final class Score {

    private int points;

    private Score() {
        this.points = 0;
    }

    /**
     * Increase the current score by one.
     */
    public void increment() {
        this.points++;
    }

    /**
     * @return
     *          The current points.
     */
    public int getPoints() {
        return this.points;
    }

    /**
     * @return
     *          The only object (SINGLETON) present in the application.
     */
    public static Score getScore() {
        return ScoreGenerator.SINGLETON;
    }

    private static class ScoreGenerator {
        private static final Score SINGLETON = new Score();
    }
}
