package it.unibo.oop.cctan.model;

import javafx.geometry.Point2D;

public interface Item {

	Model getModel();
    
    Point2D getPos();

    double getWidth();

    double getHeight();
}
