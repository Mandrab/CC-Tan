package it.unibo.oop.cctan.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import it.unibo.oop.cctan.controller.Controller;
import it.unibo.oop.cctan.interPackageComunication.MappableData;

class DrawJTest {

    private static final int TIME_BEFORE_JUNIT_TEST_END = 5000; // Ms
    private static final double MOVING_TEST_MULTIPLIER = 0.001; // every call move the square by this amount
    private static final double SQUARE_EDGE_SIZE = 0.5;
    private static final Dimension GAME_WINDOW_DIMENSION_TEST = new Dimension(500, 500); // dimension of the window
    private static final Pair<Integer, Integer> GAME_WINDOW_RATIO_TEST = new ImmutablePair<Integer, Integer>(1, 1); // ratio of window
    private int everyCallInitialValue;

    @Test
    synchronized void staticSquareTest() throws InterruptedException {
        everyCallInitialValue = 900;
        View view = new ViewImpl(new ControllerJTest(() -> everyCallInitialValue));
        view.showGameWindow(GAME_WINDOW_DIMENSION_TEST, GAME_WINDOW_RATIO_TEST);
        Thread.sleep(TIME_BEFORE_JUNIT_TEST_END);
    }

    @Test
    synchronized void movingSquareTest() throws InterruptedException {
        everyCallInitialValue = 0;
        View view = new ViewImpl(new ControllerJTest(new Supplier<Integer>() {

            private int call = everyCallInitialValue;

            @Override
            public Integer get() {
                return call++;
            }
        }));
        view.showGameWindow(GAME_WINDOW_DIMENSION_TEST, GAME_WINDOW_RATIO_TEST);
        Thread.sleep(TIME_BEFORE_JUNIT_TEST_END);
    }

    @Test
    synchronized void drawTextTest() throws InterruptedException {
        View view = new ViewImpl(new Controller() {

            @Override
            public List<MappableData> getListOfMappableData() {
                return new LinkedList<>();
            }

            @Override
            public int getScore() {
                return 0;
            }
        });
        view.showGameWindow(GAME_WINDOW_DIMENSION_TEST, GAME_WINDOW_RATIO_TEST);
        Thread.sleep(TIME_BEFORE_JUNIT_TEST_END);
    }

    private class ControllerJTest implements Controller {

        private Supplier<Integer> everyCall;

        ControllerJTest(final Supplier<Integer> everyCall) {
            this.everyCall = everyCall;
        }

        @Override
        public List<MappableData> getListOfMappableData() {
            return Arrays.asList(new MappableData() {

                @Override
                public String getText() {
                    return "test shape 1";
                }

                @Override
                public Shape getShape() {
                    return new Rectangle2D.Double(MOVING_TEST_MULTIPLIER * everyCall.get(),
                            MOVING_TEST_MULTIPLIER * everyCall.get(), SQUARE_EDGE_SIZE, SQUARE_EDGE_SIZE);
                }

                @Override
                public Color getColor() {
                    return Color.WHITE;
                }
            }, new MappableData() {

                @Override
                public String getText() {
                    return "Test with loooooooong text string ihihih";
                }

                @Override
                public Shape getShape() {
                    return new Rectangle2D.Double(-MOVING_TEST_MULTIPLIER * everyCall.get(),
                            -MOVING_TEST_MULTIPLIER * everyCall.get(), SQUARE_EDGE_SIZE, SQUARE_EDGE_SIZE);
                }

                @Override
                public Color getColor() {
                    return Color.RED;
                }
            });
        }

        @Override
        public int getScore() {
            return 0;
        }

    }

}
