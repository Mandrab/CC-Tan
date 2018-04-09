package it.unibo.oop.cctan.model;

import it.unibo.oop.cctan.geometry.Boundary;

public interface Model extends BallGenerator, SquareGenerator {

    Boundary getBounds();

    void start();
   
    Shuttle getShuttle();
}
