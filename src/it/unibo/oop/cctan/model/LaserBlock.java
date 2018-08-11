package it.unibo.oop.cctan.model;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.function.Supplier;

import it.unibo.oop.cctan.model.generator.BulletGeneratorImpl;
import it.unibo.oop.cctan.model.generator.BulletGeneratorImpl.BulletGeneratorSettings;
import javafx.geometry.Point2D;

public class LaserBlock extends PowerUpBlock implements PowerUp {

    private static final String NAME = "Laser";
    private static final String SYMBOL = "X";
    private static final double WIDTH = 0.1;
    private static final double HEIGHT = 0.1;
    private static final int DURATION = 10000;

    protected LaserBlock(LaserBlockBuilder builder) {
        super(builder);
    }

    @Override
    public void use() {

        ((BulletGeneratorImpl) this.getModel().getBulletGenerator())
                .setBulletSettings(BulletGeneratorSettings.LASER);

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(DURATION);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ((BulletGeneratorImpl) getModel().getBulletGenerator())
                        .setBulletSettings(BulletGeneratorSettings.BALLS);
            }
        }).start();
    }

    @Override
    public Color getColor() {
        return Color.RED;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getSymbol() {
        return SYMBOL;
    }

    @Override
    public double getWidth() {
        return WIDTH;
    }

    @Override
    public double getHeight() {
        return HEIGHT;
    }

    /**
     * A basic builder for BallAgent class.
     */
    public static class LaserBlockBuilder extends PowerUpBlock.PowerUpBlockBuilder<LaserBlockBuilder> {

        /** 
         * {@inheritDoc}
         */
        @Override
        public LaserBlock build() {
            super.validate();
            return new LaserBlock(this);
        }
    }
}
