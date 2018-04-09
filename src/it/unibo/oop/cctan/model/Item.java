package it.unibo.oop.cctan.model;

import javafx.geometry.Point2D;

public interface Item {

    Point2D getPos();

    double getWidth();

    double getHeight();

    Model getModel();
}
