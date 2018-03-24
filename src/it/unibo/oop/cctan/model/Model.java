package it.unibo.oop.cctan.model;


import it.unibo.oop.cctan.geometry.Boundary;

public interface Model {

    Boundary getBounds();

    Shuttle getShuttle();
}
