package it.unibo.oop.cctan.model;

import java.util.List;

public interface SquareGenerator {

    void start();

    void removeSquare(SquareAgent square);

    List<SquareAgent> getSquareAgents();
}
