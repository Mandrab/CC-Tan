package it.unibo.oop.cctan.view;

import java.awt.Dimension;

import org.apache.commons.lang3.tuple.Pair;

interface SizeObserver {

    void update(Dimension gameWindowSize, Pair<Integer, Integer> screenRatio);

}
