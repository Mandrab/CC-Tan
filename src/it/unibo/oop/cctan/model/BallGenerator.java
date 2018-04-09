package it.unibo.oop.cctan.model;

import java.util.List;
import javafx.geometry.Point2D;

public interface BallGenerator {

	void start();
	
	void removeBall(BallAgent ball);
	
	List<BallAgent> getBallAgents();
}
