package it.unibo.oop.cctan.view;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.util.Optional;
import java.util.stream.IntStream;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import it.unibo.oop.cctan.interpackage_comunication.commands_observer.CommandsObserverSource;
import it.unibo.oop.cctan.interpackage_comunication.data.ModelData;
import it.unibo.oop.cctan.interpackage_comunication.size_observer.SizeObserverSource;

class MouseJTest {

    private static final String UNEXPECTED_EXCEPTION_E = "An unexpected exception has been trown during the test";
    private static final String WRONG_RETURN_E = "Expected another return value. NOTE: if mouse has been manually moved during the test, then that may have affected the calculate.";
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final Dimension GAME_WINDOW_DIMENSION_TEST = new Dimension(SCREEN_SIZE.height / 2,
            SCREEN_SIZE.height / 2); // dimension of the window
    private static final Point TOP_LEFT_WINDOW_POINT = new Point(SCREEN_SIZE.width / 4, SCREEN_SIZE.height / 4);
    private static final double[] EXPECTED_ANGLE = new double[] { 45.0, 90.0, 135.0, 180.0, 225.0, 270.0, 315.0 };
    private static final Point2D.Double[] CORRECT_POINTS_RELATIVE_TO_ANGLE = new Point2D.Double[] {
            new Point2D.Double(0.5, -0.5), new Point2D.Double(0, -0.5), new Point2D.Double(-0.5, -0.5),
            new Point2D.Double(-0.5, 0), new Point2D.Double(-0.5, 0.5), new Point2D.Double(0, 0.5),
            new Point2D.Double(0.5, 0.5) };
    private static final Point2D.Double[] INCORRECT_POINTS_RELATIVE_TO_ANGLE = new Point2D.Double[] {
            new Point2D.Double(-0.5, 0.5), new Point2D.Double(0, 0.5), new Point2D.Double(0.5, 0.5),
            new Point2D.Double(0.5, 0), new Point2D.Double(0.5, -0.5), new Point2D.Double(0, -0.5),
            new Point2D.Double(-0.5, -0.5) };
    private static final double DEGREE_DELTA = 1d;
    private static final double RADIANT_DELTA = 1d;
    private static final int POSITIONING_TIME = 10;

    @Test
    protected synchronized void degreeMouseCorrectValueTest() {
        try {
            mouseTest(EXPECTED_ANGLE, CORRECT_POINTS_RELATIVE_TO_ANGLE, DEGREE_DELTA, true);
        } catch (Exception e) {
            e.printStackTrace();
            fail(UNEXPECTED_EXCEPTION_E);
        }
    }

    @Test
    protected synchronized void degreeMouseIncorrectValueTest() {
        try {
            mouseTest(EXPECTED_ANGLE, INCORRECT_POINTS_RELATIVE_TO_ANGLE, DEGREE_DELTA, false);
        } catch (Exception e) {
            e.printStackTrace();
            fail(UNEXPECTED_EXCEPTION_E);
        }
    }

    @Test
    protected synchronized void rangeMouseCorrectValueTest() {
        try {
            mouseTest(EXPECTED_ANGLE, CORRECT_POINTS_RELATIVE_TO_ANGLE, RADIANT_DELTA, true);
        } catch (Exception e) {
            e.printStackTrace();
            fail(UNEXPECTED_EXCEPTION_E);
        }
    }

    @Test
    protected synchronized void rangeMouseIncorrectValueTest() {
        try {
            mouseTest(EXPECTED_ANGLE, INCORRECT_POINTS_RELATIVE_TO_ANGLE, RADIANT_DELTA, false);
        } catch (Exception e) {
            e.printStackTrace();
            fail(UNEXPECTED_EXCEPTION_E);
        }
    }

    private void mouseTest(final double[] expectedAngle, final Point2D.Double[] positions, final double delta,
            final boolean assertEquals) throws AWTException {
        if (expectedAngle.length != positions.length) {
            fail("Different length vector!");
        }

        final Robot r = new Robot();
        final MouseEvents me = new MouseEvents(new ViewJTest());

        final int xZero = TOP_LEFT_WINDOW_POINT.x + GAME_WINDOW_DIMENSION_TEST.width / 2;
        final int yZero = TOP_LEFT_WINDOW_POINT.y + GAME_WINDOW_DIMENSION_TEST.height / 2;

        try {
            r.mouseMove(xZero * 2, yZero); // x > 0, y = 0 -> 0°
            if (assertEquals) {
                assertEquals(WRONG_RETURN_E, 0.0, me.getMouseRelativePosition(), delta);
            } else {
                assertNotEquals(WRONG_RETURN_E, 0.0, me.getMouseRelativePosition(), delta);
            }
        } catch (AssertionError aer) {
            assertNotEquals(WRONG_RETURN_E, 360.0, me.getMouseRelativePosition(), delta);
        }

        IntStream.range(0, expectedAngle.length)
                .mapToObj(i -> new ImmutablePair<Double, Point2D.Double>(expectedAngle[i], positions[i]))
                .forEach(pair -> {
                    r.mouseMove((int) (xZero + GAME_WINDOW_DIMENSION_TEST.width * pair.getRight().x),
                            (int) (yZero + GAME_WINDOW_DIMENSION_TEST.height * pair.getRight().y));
                    if (assertEquals) {
                        assertEquals(WRONG_RETURN_E, pair.getLeft(), me.getMouseRelativePosition(), delta);
                    } else {
                        assertNotEquals(WRONG_RETURN_E, pair.getLeft(), me.getMouseRelativePosition(), delta);
                    }
                    try {
                        Thread.sleep(POSITIONING_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
    }

    private class ViewJTest extends SizeAndCommandsLinkImpl implements View {

        @Override
        public void showGameWindow(final Dimension resolution, final Pair<Integer, Integer> screenRatio) {
        }

        @Override
        public Optional<Point> getWindowLocation() {
            return Optional.of(TOP_LEFT_WINDOW_POINT);
        }

        @Override
        public double getMouseRelativePosition() {
            return 0;
        }

        @Override
        public Optional<Dimension> getGameWindowDimension() {
            return Optional.of(GAME_WINDOW_DIMENSION_TEST);
        }

        @Override
        public void showSettingsWindow() {
        }

        @Override
        public Optional<String> getPlayerName() {
            return Optional.empty();
        }

        @Override
        public KeyCommandsListener getKeyCommandsListener() {
            return null;
        }

        @Override
        public Optional<CommandsObserverSource> getCommandsObserverSource() {
            return Optional.empty();
        }

        @Override
        public Optional<SizeObserverSource> getSizeObserverSource() {
            return Optional.empty();
        }

        @Override
        public ModelData getModelData() {
            return null;
        }

        @Override
        public void refreshGui() {
        }

        @Override
        public void hideGameWindow() {
        }

    }

}
