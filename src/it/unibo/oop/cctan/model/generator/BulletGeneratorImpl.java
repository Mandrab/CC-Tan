package it.unibo.oop.cctan.model.generator;

import javafx.geometry.Point2D;
import java.util.function.Supplier;
import it.unibo.oop.cctan.model.Model;
import it.unibo.oop.cctan.model.Bullet;
import it.unibo.oop.cctan.model.BallAgent;
import it.unibo.oop.cctan.model.BulletImpl;
import it.unibo.oop.cctan.model.LaserAgent;

/**
 * {@inheritDoc}.
 */
public class BulletGeneratorImpl extends ItemGeneratorImpl<Bullet> {

    private BulletGeneratorSettings bulletSettings;

    /**
     * Create a new thread that generates balls.
     * @param model
     *          it's the model of the application
     */
    public BulletGeneratorImpl(final Model model) {
        super(model, new BulletRatio());
        this.bulletSettings = BulletGeneratorSettings.BALLS;
    }

    public void setBulletSettings(final BulletGeneratorSettings bulletSettings) {
        this.bulletSettings = bulletSettings;
    }

    /*
     * Questo metodo va bene per tutti i proiettili perchè sono tutti dei movableItem e io nella 
     * lista devo aggiungere dei movableItem 
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

    public static enum BulletGeneratorSettings {

        BALLS(() -> new BallAgent.BallBuilder()), LASER(() -> new LaserAgent.LaserBuilder());

        private Supplier<BulletImpl.BulletBuilder> bulletBuilder;

        BulletGeneratorSettings(final Supplier<BulletImpl.BulletBuilder> bulletBuilder) {
            this.bulletBuilder = bulletBuilder;
        }

        public BulletImpl.BulletBuilder getBulletBuilder() {
            return this.bulletBuilder.get();
        }

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

        public double getSpeed(final TimerRatio timer) {
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
