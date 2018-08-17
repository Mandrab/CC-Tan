package it.unibo.oop.cctan.model;

import java.awt.Dimension;
import java.awt.Toolkit;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import it.unibo.oop.cctan.controller.Controller;
import it.unibo.oop.cctan.interPackageComunication.Commands;
import it.unibo.oop.cctan.interPackageComunication.ModelData;
import it.unibo.oop.cctan.view.View;
import it.unibo.oop.cctan.view.ViewImpl;
import it.unibo.oop.cctan.view.View.Component;

/**
 * First application test.
 */
public class GraphicJTest {

    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int SHORTER_EDGE = SCREEN_SIZE.width > SCREEN_SIZE.height 
                                            ? SCREEN_SIZE.height 
                                            : SCREEN_SIZE.width;
    private static final Pair<Integer, Integer> GAME_WINDOW_RATIO_TEST1 
        = new ImmutablePair<Integer, Integer>(1, 1); // ratio of window
    private static final Dimension GAME_WINDOW_DIMENSION_TEST1 
        = new Dimension((int) (SHORTER_EDGE / 1.2), (int) (SHORTER_EDGE / 1.2)); // dimension of the window
    private static final int SLEEP_BALANCED_RATEO_MILLISEC = 1000000;

    /**
     * First application test.
     */
    @Test
    public void balancedRateo() {
        final Controller ctrl = new Controller() {

            @Override
            public void update(final Dimension gameWindowSize, final Pair<Integer, Integer> screenRatio) {
                // TODO Auto-generated method stub

            }

            @Override
            public void newCommand(final Commands command) {
                // TODO Auto-generated method stub

            }

            @Override
            public void setView(final View v) {
                // TODO Auto-generated method stub

            }

            @Override
            public void refreshGui(final Component component) {
                // TODO Auto-generated method stub

            }

            @Override
            public ModelData getModelData() {
                // TODO Auto-generated method stub
                return null;
            }
        };
        final View v = new ViewImpl(ctrl);
        ctrl.newCommand(Commands.START);
        v.showGameWindow(GAME_WINDOW_DIMENSION_TEST1, GAME_WINDOW_RATIO_TEST1);
        try {
            Thread.sleep(SLEEP_BALANCED_RATEO_MILLISEC);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
