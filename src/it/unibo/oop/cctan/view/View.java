package it.unibo.oop.cctan.view;

import java.awt.Dimension;

import org.apache.commons.lang3.tuple.Pair;

public interface View {

	void show(Pair<Integer, Integer> displayRatio, Dimension resolution);
	
	Dimension getDimension();

    //TO DO
}
