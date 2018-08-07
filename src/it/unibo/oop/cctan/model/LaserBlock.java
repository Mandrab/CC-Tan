package it.unibo.oop.cctan.model;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javafx.geometry.Point2D;

public class LaserBlock extends PowerUpBlock implements PowerUp {

    private static final String NAME = "Laser";
    private static final String SYMBOL = "X";
    private static final double WIDTH = 0.075;
    private static final double HEIGHT = 0.075;

    protected LaserBlock(LaserBlockBuilder builder) {
        super(builder);
    }

    @Override
    public void use() {
         ((BulletGeneratorImpl) this.getModel().getBulletGenerator()).setBulletType(() -> new LaserAgent.LaserBuilder());
         /*IDEA: a questo punto, avviare un thread-timer, che raggiunti X secondi
         fa terminare il power-up, richiamando*/
         ((BulletGeneratorImpl) this.getModel().getBulletGenerator()).setBulletType(() -> new BallAgent.BallBuilder());
    }

    @Override
    public Color getColor() {
        return Color.LIGHT_GRAY;
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
