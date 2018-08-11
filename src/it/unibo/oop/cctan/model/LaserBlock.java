package it.unibo.oop.cctan.model;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.function.Supplier;

import it.unibo.oop.cctan.model.generator.BulletGeneratorImpl;
import javafx.geometry.Point2D;

public class LaserBlock extends PowerUpBlock implements PowerUp {

    private static final String NAME = "Laser";
    private static final String SYMBOL = "X";
    private static final double WIDTH = 0.1;
    private static final double HEIGHT = 0.1;

    protected LaserBlock(LaserBlockBuilder builder) {
        super(builder);
    }

    @Override
    public void use() {

        this.setBulletGenerator(() -> new LaserAgent.LaserBuilder(), () -> this.getModel().getShuttle().getTop());
        /*
         * IDEA: a questo punto, avviare un thread-timer, che raggiunti X secondi fa
         * terminare il power-up, richiamando
         */
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LaserBlock.this.setBulletGenerator(() -> new BallAgent.BallBuilder(),
                        () -> new Point2D(getModel().getShuttle().getTop().getX() - BallAgent.WIDTH / 2,
                                getModel().getShuttle().getTop().getY() - BallAgent.HEIGHT / 2));
            }
        }).start();
    }

    private void setBulletGenerator(final Supplier<BulletImpl.BulletBuilder> bulletType, Supplier<Point2D> startingPos) {
        ((BulletGeneratorImpl) this.getModel().getBulletGenerator())
                .setBulletType(bulletType);
        ((BulletGeneratorImpl) this.getModel().getBulletGenerator())
                .setRetrievingPos(startingPos);
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
