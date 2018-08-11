package it.unibo.oop.cctan.model.generator;

import java.util.function.Supplier;

import it.unibo.oop.cctan.model.BallAgent;
import it.unibo.oop.cctan.model.Bullet;
import it.unibo.oop.cctan.model.BulletImpl;
import it.unibo.oop.cctan.model.Model;
import it.unibo.oop.cctan.model.BallAgent.BallBuilder;
import it.unibo.oop.cctan.model.BulletImpl.BulletBuilder;
import it.unibo.oop.cctan.model.LaserAgent;
import javafx.geometry.Point2D;

/**
 * {@inheritDoc}.
 */
public class BulletGeneratorImpl extends ItemGeneratorImpl<Bullet> {

    private Supplier<BulletImpl.BulletBuilder> bullets;
    private Supplier<Point2D> startingPos;

    /**
     * Create a new thread that generates balls.
     * @param model
     *          it's the model of the application
     */
    public BulletGeneratorImpl(final Model model) {
        super(model, new BulletRatio());
        bullets = () -> new BallAgent.BallBuilder();
        startingPos = () -> new Point2D(this.getModel().getShuttle().getTop().getX() - BallAgent.WIDTH / 2,
                this.getModel().getShuttle().getTop().getY() - BallAgent.HEIGHT / 2);
    }

    /*
     * Utilizzo il pattern strategy per farmi dire il tipo di proiettile che devo generare 
     */
    public void setBulletType(final Supplier<BulletImpl.BulletBuilder> bullets) {
        this.bullets = bullets;
    }
    
    public void setRetrievingPos(final Supplier<Point2D> initialPos) {
        this.startingPos = initialPos;
    }

    /*
     * Questo metodo va bene per tutti i proiettili perchè sono tutti dei movableItem e io nella 
     * lista devo aggiungere dei movableItem 
     */
    @Override
    protected void createNewItem() {
        final Bullet bullet = (Bullet) this.bullets.get()
                .angle(this.getModel().getShuttle().getAngle())
                .speed(((BulletRatio) this.getRatio()).getSpeed())
                .position(this.startingPos.get())
                .model(this.getModel())
                .build();
        this.addItemToList(bullet);
        new Thread(bullet).start();
    }
}
