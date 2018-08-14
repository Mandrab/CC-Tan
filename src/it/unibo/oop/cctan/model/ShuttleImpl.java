package it.unibo.oop.cctan.model;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import javafx.geometry.Point2D;

/**
 * Represent the shuttle in the space, where balls get out from. When a square collide with it the game will ends.
 * The shuttle will rotate with mouse position.
 */
public class ShuttleImpl extends FixedItemImpl implements Shuttle {

    private static final double HEIGHT = 1/15.0; //height of the rectangle containing the triangle, in terms of interval-unit
    private static final double WIDTH = 1/15.0; //width of the rectangle, in terms of interval-unit

    private final double width;
    private final double height;
    private final Point2D startingPos;
    private final List<Pair<PowerUpBlock, PowerUpExecution>> activePowerUps;

    /**
     * Create a new shuttle in the center of the game area.
     * @param model
     *                  the model of MVC pattern
     */
    public ShuttleImpl(final Model model) {
        super(model, new Point2D(0, 0));
        this.width = WIDTH * this.getModel().getBounds().width();
        this.height = HEIGHT * this.getModel().getBounds().height();
        this.activePowerUps = new ArrayList<>();
        this.startingPos = new Point2D(this.width / 2, this.height / 2);
        this.setPos(this.startingPos);
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public Area getImpactArea() {
        return new Area(this.getShape());
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public Point2D getTop() {
        final double angle = Math.toRadians(90 - this.getAngle());
        return new Point2D(this.getPos().getX() + (this.width / 2) * Math.cos(angle),
                this.getPos().getY() - (this.width / 2) * Math.sin(angle));
    }

    /** 
     * {@inheritDoc}
     * In more, rotate the shuttle.
     */
    @Override
    public synchronized void setAngle(final double angle) {
        super.setAngle(angle);
        final double centerX = this.startingPos.getX() - this.width / 2;
        final double centerY = this.startingPos.getY() - this.height / 2;
        final double angleRad = Math.toRadians(angle);
        final double[] pt = { this.startingPos.getX(), this.startingPos.getY() };
        AffineTransform.getRotateInstance(angleRad, centerX, centerY).transform(pt, 0, pt, 0, 1);
        this.setPos(new Point2D(pt[0], pt[1]));
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public List<Point2D> getShapePoints() {
        final double angle = Math.toRadians(this.getAngle());
        final Point2D leftBase = new Point2D(this.getPos().getX() - this.height * Math.cos(angle),
                this.getPos().getY() - this.height * Math.sin(angle));
        final Point2D rightBase = new Point2D(leftBase.getX() + this.width * Math.sin(angle),
                leftBase.getY() - this.width * Math.cos(angle));
        return Arrays.asList(this.getTop(), leftBase, rightBase);
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public Shape getShape() {
        final Path2D.Double path = new Path2D.Double();
        final List<Point2D> points = this.getShapePoints();
        points.forEach(p -> {
            if (points.indexOf(p) == 0) {
                path.moveTo(p.getX(), p.getY());
            } else {
                path.lineTo(p.getX(), p.getY());
            }
        });
        path.closePath();
        return path;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public double getWidth() {
        return this.getShape().getBounds2D().getWidth();
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public double getHeight() {
        return this.getShape().getBounds2D().getHeight();
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public Color getColor() {
        return Color.WHITE;
    }

    @Override
    public synchronized List<PowerUpExecution> getActivePowerUps() {
        return Collections.unmodifiableList(this.activePowerUps.stream()
                                                                .map(p -> p.getRight())
                                                                .collect(Collectors.toList()));
    }

    @Override
    public synchronized void activePowerUp(final Pair<PowerUpBlock, PowerUpExecution> powerUpExecution) {
        final List<Pair<PowerUpBlock, PowerUpExecution>> duplicate = this.activePowerUps.stream()
                                                 .filter(p -> p.getLeft().getClass()
                                                         .equals(powerUpExecution.getLeft().getClass()))
                                                 .collect(Collectors.toList());
        if (!duplicate.isEmpty()) {
            duplicate.forEach(p -> p.getRight().increaseTimer(powerUpExecution.getRight().getTimer()));
        } else {
            this.activePowerUps.add(powerUpExecution);
        }
    }

    @Override
    public synchronized void removePowerUp(final Pair<PowerUpBlock, PowerUpExecution> powerUpExecution) {
        this.activePowerUps.remove(powerUpExecution);
    }
}
