package it.unibo.oop.cctan.model;

import java.util.List;

public interface BallGenerator {

    void start(); 

    void removeBall(BallAgent ball);

    List<BallAgent> getBallAgents();
}
