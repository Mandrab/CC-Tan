package it.unibo.oop.cctan.model.generator;

import java.util.Random;
import java.util.function.Supplier;

import it.unibo.oop.cctan.model.LaserBlock;
import it.unibo.oop.cctan.model.Model;
import it.unibo.oop.cctan.model.PowerUpBlock;
import it.unibo.oop.cctan.model.PowerUpBlockImpl;
import it.unibo.oop.cctan.model.geometry.RandomUtility;
import javafx.geometry.Point2D;

/**
 * It represents a random generator of {@link PowerUpBlock PowerUpBlock} over time.
 */
public class PowerUpGenerator extends AbstractItemGenerator<PowerUpBlock> {

    private Supplier<PowerUpBlockImpl.PowerUpBlockBuilder<?>> type;
    private boolean skip;

    /**
     * Create a new thread that generates {@link PowerUpBlock PowerUpBlock} over time.
     * 
     * @param model
     *            it's the model of the application
     */
    public PowerUpGenerator(final Model model) {
        super(model, new PowerUpRatio());
        this.type = () -> new LaserBlock.LaserBlockBuilder();
        this.skip = true;
    }

    /**
     * This method is used to create new object of {@link PowerUpBlock PowerUpBlock} type.
     */
    @Override
    protected void createNewItem() {
        if (!this.skip) {
            this.type = randomPowerUp();
            final PowerUpBlock powerUp = (PowerUpBlock) type.get()
                    .hitPoints(((PowerUpRatio) this.getRatio()).getHP())
                    .position(randomPoint())
                    .model(this.getModel())
                    .build();
            this.addItemToList(powerUp);
        } else {
            this.skip = false;
        }
    }

    private Point2D randomPoint() {
        final double percentageSpawn = 0.75;
        final double length = percentageSpawn * Math.abs(this.getModel().getBounds().getX1()
                - this.getModel().getBounds().getX0());
        final double height = percentageSpawn * Math.abs(this.getModel().getBounds().getY1()
                - this.getModel().getBounds().getY0());
        return new Point2D(RandomUtility.doubleInRange(-length / 2, length / 2),
                RandomUtility.doubleInRange(-height / 2, height / 2));
    }

    private Supplier<PowerUpBlockImpl.PowerUpBlockBuilder<?>> randomPowerUp() {
        return () -> this.getModel().getPowerUpBlockTypes().get(
                new Random().nextInt(this.getModel().getPowerUpBlockTypes().size()));
    }
}
