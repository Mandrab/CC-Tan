package it.unibo.oop.cctan.model.generator;

import javafx.geometry.Point2D;
import java.util.function.Supplier;
import it.unibo.oop.cctan.model.Model;
import it.unibo.oop.cctan.model.Bullet;
import it.unibo.oop.cctan.model.BallAgent;
import it.unibo.oop.cctan.model.BulletImpl;
import it.unibo.oop.cctan.model.LaserAgent;

/**
 * It represents a generator of {@link Bullet Bullet} over time. The bullet that is generated 
 * is specified within the bulletSettings field. At the beginning of the game, balls are 
 * generated by default.
 */
public class BulletGenerator extends AbstractItemGenerator<Bullet> {

    private BulletGeneratorSettings bulletSettings;

    /**
     * Create a new thread that generates {@link Bullet Bullet} objects over time.
     * @param model
     *          it's the model of the application
     */
    public BulletGenerator(final Model model) {
        super(model, new BulletRatio());
        this.bulletSettings = BulletGeneratorSettings.BALLS;
    }

    /**
     * This method is good for all types of {@link Bullet Bullet} to be generated. 
     * In particular, the task of this method is to create a new bullet having a speed, 
     * an angle, the starting position and a reference to the model.
     */
    @Override
    protected void createNewItem() {
        final Bullet bullet = (Bullet) this.bulletSettings.getBulletBuilder()
                .angle(this.getModel().getShuttle().getAngle())
                .speed(this.bulletSettings.getSpeed(this.getRatio()))
                .position(this.bulletSettings.getStartingPoint(this.getModel()))
                .model(this.getModel())
                .build();
        this.addItemToList(bullet);
        new Thread(bullet).start();
    }

    /**
     * This method allows you to change the type of bullet that is generated.
     * @param bulletSettings
     *          It's the new bullet that must be generated.
     */
    public void setBulletSettings(final BulletGeneratorSettings bulletSettings) {
        this.bulletSettings = bulletSettings;
    }

    /**
     * This enumeration contains all the BulletBuilder that are present within the application. 
     * In this way, for each BulletBuilder we are able to get the initial position 
     * {@link BulletGeneratorSettings#getStartingPoint(Model model)} and 
     * the initial speed {@link BulletGeneratorSettings#getSpeed(AbstractTimerRatio timer)} 
     * that each bullet must have at its creation.
     */
    public enum BulletGeneratorSettings {

        /**
         * It's BallBuilder builder.
         */
        BALLS(() -> new BallAgent.BallBuilder()),

        /**
         * It's LaserBuilder builder.
         */
        LASER(() -> new LaserAgent.LaserBuilder());

        private Supplier<BulletImpl.BulletBuilder> bulletBuilder;

        /**
         * Set the bulletBuilder field of each object contained within the enumeration.
         * @param bulletBuilder
         *              The builder of the current bullet.
         */
        //The constructor BulletGeneratorSettings is already PRIVATE by default.
        BulletGeneratorSettings(final Supplier<BulletImpl.BulletBuilder> bulletBuilder) {
            this.bulletBuilder = bulletBuilder;
        }

        /**
         * @return
         *      The builder of the {@link BulletGeneratorSettings BulletGeneratorSettings} 
         *      that made the call to this method.
         */
        public BulletImpl.BulletBuilder getBulletBuilder() {
            return this.bulletBuilder.get();
        }

        /**
         * @param model
         *      The model of the application.
         * @return
         *      The initial position from which the bullet must start.
         */
        public Point2D getStartingPoint(final Model model) {
            switch (this) {
            case BALLS:
                return new Point2D(model.getShuttle().getTop().getX() - BallAgent.WIDTH / 2,
                        model.getShuttle().getTop().getY() - BallAgent.HEIGHT / 2);
            case LASER:
                return model.getShuttle().getTop();
            default:
                throw new IllegalStateException();
            }
        }

        /**
         * @param timer
         *      The {@link AbstractTimerRatio TimerRatio} object that increases the speed of bullet over time.
         * @return
         *      The speed that the bullet must have.
         */
        public double getSpeed(final AbstractTimerRatio timer) {
            final double defaultSpeed = 0.018;
            switch (this) {
            case BALLS:
                return timer.getSpeed();
            case LASER:
                return defaultSpeed;
            default:
                throw new IllegalStateException();
            }
        }
    }
}
