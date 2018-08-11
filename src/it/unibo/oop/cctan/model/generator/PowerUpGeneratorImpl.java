package it.unibo.oop.cctan.model.generator;

import java.util.Random;
import java.util.function.Supplier;

import it.unibo.oop.cctan.model.LaserBlock;
import it.unibo.oop.cctan.model.Model;
import it.unibo.oop.cctan.model.PowerUp;
import it.unibo.oop.cctan.model.PowerUpBlock;
import javafx.geometry.Point2D;

public class PowerUpGeneratorImpl extends ItemGeneratorImpl<PowerUp> {
    
    private static final int DEFAULT_RATIO = 40000;
    private static Supplier<PowerUpBlock.PowerUpBlockBuilder<?>> type = () -> new LaserBlock.LaserBlockBuilder();

    public PowerUpGeneratorImpl(Model model) {
        super(model, new TimerRatio(0, DEFAULT_RATIO) {

            private static final int DECREASE_RATIO = 3000;
            private static final int MAX_RATIO = 25000;

            @Override
            public void operationRatio() {
                final int newRatio = this.getRatio() - DECREASE_RATIO;
                if (newRatio >= MAX_RATIO) {
                    this.setRatio(newRatio);
                    //type = () -> new LaserBlock.LaserBlockBuilder();
                    //to change with random powerup choice
                }
            }
        });
    }

    @Override
    protected void createNewItem() {
        final PowerUp powerUp = (PowerUp) type.get()
                .position(randomPoint())
                .model(this.getModel())
                .hitPoints(5 + new Random().nextInt(26))
                .build();
        this.addItemToList(powerUp);
    }
    
    private Point2D randomPoint() {
        Random r = new Random();
        final double percentageSpawn = 0.75;
        double length = percentageSpawn * Math.abs(this.getModel().getBounds().getX1()
                - this.getModel().getBounds().getX0());
        double height = percentageSpawn * Math.abs(this.getModel().getBounds().getY1()
                - this.getModel().getBounds().getY0());
        return new Point2D(length * r.nextDouble() - length / 2, height * r.nextDouble() - height / 2);
    }

}