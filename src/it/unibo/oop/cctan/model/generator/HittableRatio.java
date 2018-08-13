package it.unibo.oop.cctan.model.generator;

import it.unibo.oop.cctan.geometry.RandomUtility;

public abstract class HittableRatio extends TimerRatio {

    private static final int DELTA = 10;
    private final int defaultPoints;
    private int points;

    public HittableRatio(final double speed, final int ratio, final int defaultPoints) {
        super(speed, ratio);
        this.points = defaultPoints + DELTA;
        this.defaultPoints = defaultPoints;
    }

    public void setPoints(final int points) {
        this.points = points;
    }

    public int getPoints() {
        return this.points;
    }

    public int getHP() {
        return RandomUtility.intInRange(this.defaultPoints, this.points);
    }
}
