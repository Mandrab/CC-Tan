package it.unibo.oop.cctan.model;

import java.util.function.Supplier;

import javafx.geometry.Point2D;

/**
 * {@inheritDoc}.
 */
public class BulletGeneratorImpl extends ItemGeneratorImpl {

    private Supplier<BulletImpl.BulletBuilder> bullets;
    
    /**
     * Create a new thread that generates balls.
     * @param model
     *          it's the model of the application
     */
    public BulletGeneratorImpl(final Model model) {
        super(model, new BallRatio());
        bullets = () -> new BallAgent.BallBuilder();
    }

    public void setBulletType(Supplier<BulletImpl.BulletBuilder> bullets) {
        this.bullets = bullets;
    }
    
    @Override
    protected void createNewItem() {
        final Bullet bullet = (Bullet) this.bullets.get()
                .angle(this.getModel().getShuttle().getAngle())
                .speed(this.getRatio().getSpeed())
                .position(new Point2D(this.getModel().getShuttle().getTop().getX() /*-(BallAgent.WIDTH / 2) */,
                        this.getModel().getShuttle().getTop().getY() /*+ (BallAgent.HEIGHT / 2) */))
                .model(this.getModel())
                .build();
        this.addItemToList(bullet);
        new Thread(bullet).start();
    }
}
