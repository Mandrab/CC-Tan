package it.unibo.oop.cctan.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import javax.swing.ImageIcon;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import it.unibo.oop.cctan.controller.Controller;
import it.unibo.oop.cctan.interPackageComunication.MappableData;

class MouseJTest {

    private static final Dimension GAME_WINDOW_DIMENSION_TEST = new Dimension(500, 500); // dimension of the window
    private static final Pair<Integer, Integer> GAME_WINDOW_RATIO_TEST = new ImmutablePair<Integer, Integer>(1, 1); // ratio of window
    private double mousePositionAngle;
    
    @Test
    void classicMouseTest() throws AWTException {
        View view = new ViewImpl(new ControllerJTest());
        view.showGameWindow(GAME_WINDOW_DIMENSION_TEST, GAME_WINDOW_RATIO_TEST);
        Robot r = new Robot();

        final int xZero = view.getWindowLocation().x + view.getDimension().width / 2;
        final int yZero = view.getWindowLocation().y + view.getDimension().height / 2;
        final double[] testPoint = { 45.0, 90.0, 135.0, 180.0, 225.0, 270.0, 315.0 };
        final Point2D.Double[] multiplier = { new Point2D.Double(0.5, -0.5), new Point2D.Double(0, -0.5),
                new Point2D.Double(-0.5, -0.5), new Point2D.Double(-0.5, 0), new Point2D.Double(-0.5, 0.5),
                new Point2D.Double(0, 0.5), new Point2D.Double(0.5, 0.5) };
        final double delta = 5.0;

        try {
            r.mouseMove((int) (view.getWindowLocation().x + view.getDimension().getWidth()),
                        (int) (view.getWindowLocation().y + (view.getDimension().getHeight() / 2))); // x > 0, y = 0 -> 0°
            assertEquals(0.0, view.getMouseRelativePosition(), delta);
        } catch (AssertionError aer) {
            assertEquals(360.0, view.getMouseRelativePosition(), delta);
        }

        IntStream.range(0, testPoint.length)
                .mapToObj(i -> new ImmutablePair<Double, Point2D.Double>(testPoint[i], multiplier[i]))
                .forEach(pair -> {
                    r.mouseMove((int) (xZero + (view.getDimension().getWidth() * pair.getRight().x)),
                                (int) (yZero + (view.getDimension().getHeight() * pair.getRight().y)));
                    assertEquals(pair.getLeft(), view.getMouseRelativePosition(), delta);
                });

    }

    @Test
    void rangeMouseTest() throws AWTException {
        View view = new ViewImpl(new ControllerJTest());
        view.showGameWindow(GAME_WINDOW_DIMENSION_TEST, GAME_WINDOW_RATIO_TEST);
        Robot r = new Robot();

        final int xZero = view.getWindowLocation().x + view.getDimension().width / 2;
        final int yZero = view.getWindowLocation().y + view.getDimension().height / 2;
        final double[] testPoint = { Math.PI / 8, Math.PI / 4, Math.PI * 3 / 8, Math.PI, Math.PI * 5 / 8, Math.PI * 3 / 4, Math.PI * 7 / 8 };
        final Point2D.Double[] multiplier = { new Point2D.Double(0.5, -0.5), new Point2D.Double(0, -0.5),
                new Point2D.Double(-0.5, -0.5), new Point2D.Double(-0.5, 0), new Point2D.Double(-0.5, 0.5),
                new Point2D.Double(0, 0.5), new Point2D.Double(0.5, 0.5) };
        final double delta = 5.0;

        try {
            r.mouseMove((int) (view.getWindowLocation().x + view.getDimension().getWidth()),
                        (int) (view.getWindowLocation().y + (view.getDimension().getHeight() / 2))); // x > 0, y = 0 -> 0°
            assertEquals(0.0, view.getMouseRelativePositionInRange(0, 2 * Math.PI), delta);
        } catch (AssertionError aer) {
            assertEquals(2 * Math.PI, view.getMouseRelativePositionInRange(0, 2 * Math.PI), delta);
        }

        IntStream.range(0, testPoint.length)
                .mapToObj(i -> new ImmutablePair<Double, Point2D.Double>(testPoint[i], multiplier[i]))
                .forEach(pair -> {
                    r.mouseMove((int) (xZero + (view.getDimension().getWidth() * pair.getRight().x)),
                                (int) (yZero + (view.getDimension().getHeight() * pair.getRight().y)));
                    assertEquals(pair.getLeft(), view.getMouseRelativePositionInRange(0, 2 * Math.PI), delta);
                });

    }

    private class ControllerJTest implements Controller {

        @Override
        public List<MappableData> getListOfMappableData() {
            return Arrays.asList();
        }

        @Override
        public int getScore() {
            return 0;
        }

        @Override
        public void advanceLoading(int i) {
        }

        @Override
        public void setView(View v) {
        }

        @Override
        public void setLoadImage(ImageIcon img) {
        }

        @Override
        public void setMouseRelativePosition(double angle) {
        }

    }

}
