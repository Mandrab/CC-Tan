package it.unibo.oop.cctan.model;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import it.unibo.oop.cctan.controller.Controller;
import it.unibo.oop.cctan.controller.ControllerImpl;
import it.unibo.oop.cctan.view.View;
import it.unibo.oop.cctan.view.ViewImpl;

public class GraphicJTest {

    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int SHORTER_EDGE = SCREEN_SIZE.width > SCREEN_SIZE.height 
                                            ? SCREEN_SIZE.height 
                                            : SCREEN_SIZE.width;
    private static final Pair<Integer, Integer> GAME_WINDOW_RATIO_TEST1 
        = new ImmutablePair<Integer, Integer> (1, 1); // ratio of window
    private static final Dimension GAME_WINDOW_DIMENSION_TEST1 
        = new Dimension(SHORTER_EDGE / 2, SHORTER_EDGE / 2); // dimension of the window
    private static final Pair<Integer, Integer> GAME_WINDOW_RATIO_TEST2 
        = new ImmutablePair<Integer, Integer>(2, 1);
    private static final Dimension GAME_WINDOW_DIMENSION_TEST2 
        = new Dimension(SHORTER_EDGE, SHORTER_EDGE / 2);
    private static final Pair<Integer, Integer> GAME_WINDOW_RATIO_TEST3 
        = new ImmutablePair<Integer, Integer>(1, 2);
    private static final Dimension GAME_WINDOW_DIMENSION_TEST3 
        = new Dimension(SHORTER_EDGE / 2, SHORTER_EDGE);
    
    @Test
    synchronized public void balancedRateo() {
        Controller ctrl = new ControllerImpl();
        View v = new ViewImpl(ctrl);
        v.showGameWindow(GAME_WINDOW_DIMENSION_TEST1, GAME_WINDOW_RATIO_TEST1);
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
   // @Test
    synchronized public void xOverwhelm() {
        Controller ctrl = new ControllerImpl();
        View v = new ViewImpl(ctrl);
        v.showGameWindow(GAME_WINDOW_DIMENSION_TEST2, GAME_WINDOW_RATIO_TEST2);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    //@Test
    synchronized public void yOverwhelm() {
        Controller ctrl = new ControllerImpl();
        View v = new ViewImpl(ctrl);
        v.showGameWindow(GAME_WINDOW_DIMENSION_TEST3, GAME_WINDOW_RATIO_TEST3);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
