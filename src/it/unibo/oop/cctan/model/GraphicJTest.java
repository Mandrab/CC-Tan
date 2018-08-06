package it.unibo.oop.cctan.model;

import static org.junit.Assert.*;

import java.awt.Dimension;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import it.unibo.oop.cctan.controller.ControllerImpl;
import it.unibo.oop.cctan.view.View;
import it.unibo.oop.cctan.view.ViewImpl;

public class GraphicJTest {

    private static final Dimension GAME_WINDOW_DIMENSION_TEST = new Dimension(500, 500); // dimension of the window
    private static final Pair<Integer, Integer> GAME_WINDOW_RATIO_TEST = new ImmutablePair<Integer, Integer>(1, 1); // ratio of window
    
    @Test
    public void test() {
        View v = new ViewImpl(new ControllerImpl());
        v.showGameWindow(GAME_WINDOW_DIMENSION_TEST, GAME_WINDOW_RATIO_TEST);
        try {
            Thread.sleep(200000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
