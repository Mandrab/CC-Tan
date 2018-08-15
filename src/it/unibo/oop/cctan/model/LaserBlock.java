package it.unibo.oop.cctan.model;

import java.awt.Color;

import org.apache.commons.lang3.tuple.ImmutablePair;

import it.unibo.oop.cctan.model.Shuttle.PowerUpExecution;
import it.unibo.oop.cctan.model.generator.BulletGeneratorImpl;
import it.unibo.oop.cctan.model.generator.BulletGeneratorImpl.BulletGeneratorSettings;

/**
 * The laser block power-up implementation. It represent the block to be
 * destroyed by bullets to then activate power-up (i.e. laser bullets).
 */
public class LaserBlock extends PowerUpBlockImpl {

    private static final String NAME = "Laser";
    private static final String SYMBOL = "X";
    private static final double WIDTH = 1 / 8.0;
    private static final double HEIGHT = 1 / 8.0;
    private static final int DURATION = 10000;

    /**
     * Create a new LaserBlock using values contained in the specified builder.
     * 
     * @param builder
     *            the builder to construct the laser block
     */
    protected LaserBlock(final LaserBlockBuilder builder) {
        super(builder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void use() {

        ((BulletGeneratorImpl) this.getModel().getBulletGenerator()).setBulletSettings(BulletGeneratorSettings.LASER);

        final PowerUpExecution execution = new PowerUpExecution(DURATION) {

            @Override
            protected void operation() {
                getModel().getShuttle().removePowerUp(new ImmutablePair<>(LaserBlock.this, this));
                ((BulletGeneratorImpl) getModel().getBulletGenerator())
                        .setBulletSettings(BulletGeneratorSettings.BALLS);
            }
        };
        this.getModel().getShuttle().activePowerUp(new ImmutablePair<>(this, execution));
        execution.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color getColor() {
        return Color.RED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSymbol() {
        return SYMBOL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getWidth() {
        return WIDTH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getHeight() {
        return HEIGHT;
    }

    /**
     * A basic builder for BallAgent class.
     */
    public static class LaserBlockBuilder extends PowerUpBlockImpl.PowerUpBlockBuilder<LaserBlockBuilder> {

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
