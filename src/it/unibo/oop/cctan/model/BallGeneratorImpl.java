package it.unibo.oop.cctan.model;

import javafx.geometry.Point2D;

/**
 * {@inheritDoc}.
 */
public class BallGeneratorImpl extends ItemGeneratorImpl {

    /**
     * Create a new thread that generates balls.
     * @param model
     *          it's the model of the application
     */
    public BallGeneratorImpl(final Model model) {
        super(model, new BallRatio());
    }

    protected synchronized void createNewItem() {
        final BallAgent ball = (BallAgent) new BallAgent.BallBuilder()
                .angle(this.getModel().getShuttle().getAngle())
                .speed(this.getRatio().getSpeed())
                .position(new Point2D(this.getModel().getShuttle().getTop().getX() - (BallAgent.WIDTH / 2),
                        this.getModel().getShuttle().getTop().getY() + (BallAgent.HEIGHT / 2)))
                .model(this.getModel())
                .build();
        this.addItemToList(ball);
        new Thread(ball).start();
    }

}
