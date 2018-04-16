package it.unibo.oop.cctan.model;

import java.awt.geom.Area;

import javafx.geometry.Point2D;

public interface Shuttle extends Item {

    Point2D getTop();

    double getAngle();

    void setAngle(final double angle);

    Area getImpactArea();
}
