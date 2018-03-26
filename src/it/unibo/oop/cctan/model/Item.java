package it.unibo.oop.cctan.model;

import it.unibo.oop.cctan.geometry.Point2D;

public interface Item {

    Point2D getPos();

    double getWidth();

    double getHeight();

    Model getModel();
}
