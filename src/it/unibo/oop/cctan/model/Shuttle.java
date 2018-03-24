package it.unibo.oop.cctan.model;

import java.awt.geom.Area;

public interface Shuttle extends Item {

    double getAngle();

    void setAngle(final double angle);

    Area getImpactArea();
}
