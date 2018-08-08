package it.unibo.oop.cctan.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import javax.swing.ImageIcon;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import it.unibo.oop.cctan.controller.Controller;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserver;
import it.unibo.oop.cctan.interPackageComunication.MappableData;

class MouseJTest {

    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final Dimension GAME_WINDOW_DIMENSION_TEST = new Dimension(SCREEN_SIZE.height / 2, 
                                                                              SCREEN_SIZE.height / 2); // dimension of the window
    private static final Pair<Integer, Integer> GAME_WINDOW_RATIO_TEST = new ImmutablePair<Integer, Integer>(1, 1); // ratio of window
    private static final Point TOP_LEFT_WINDOW_POINT = new Point(SCREEN_SIZE.width / 4,
                                                                 SCREEN_SIZE.height / 4);
    
    @Test
    void classicMouseTest() throws AWTException {
        Robot r = new Robot();
        
        MouseEvents me = new MouseEvents(new ViewJTest());

        final int xZero = TOP_LEFT_WINDOW_POINT.x + GAME_WINDOW_DIMENSION_TEST.width / 2;
        final int yZero = TOP_LEFT_WINDOW_POINT.y + GAME_WINDOW_DIMENSION_TEST.height / 2;
        final double[] testPoint = { 45.0, 90.0, 135.0, 180.0, 225.0, 270.0, 315.0 };
        final Point2D.Double[] multiplier = { new Point2D.Double(0.5, -0.5), new Point2D.Double(0, -0.5),
                new Point2D.Double(-0.5, -0.5), new Point2D.Double(-0.5, 0), new Point2D.Double(-0.5, 0.5),
                new Point2D.Double(0, 0.5), new Point2D.Double(0.5, 0.5) };
        final double delta = 1.0;

        try {
            r.mouseMove(TOP_LEFT_WINDOW_POINT.x + GAME_WINDOW_DIMENSION_TEST.width,
                        TOP_LEFT_WINDOW_POINT.y + GAME_WINDOW_DIMENSION_TEST.height / 2); // x > 0, y = 0 -> 0°
            assertEquals(0.0, me.getMouseRelativePosition(), delta);
        } catch (AssertionError aer) {
            assertEquals(360.0, me.getMouseRelativePosition(), delta);
        }

        IntStream.range(0, testPoint.length)
                .mapToObj(i -> new ImmutablePair<Double, Point2D.Double>(testPoint[i], multiplier[i]))
                .forEach(pair -> {
                    r.mouseMove((int) (xZero + GAME_WINDOW_DIMENSION_TEST.width * pair.getRight().x),
                                (int) (yZero + GAME_WINDOW_DIMENSION_TEST.height * pair.getRight().y));
                    assertEquals(pair.getLeft(), me.getMouseRelativePosition(), delta);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });

    }

    @Test
    void rangeMouseTest() throws AWTException {
        View view = new ViewImpl(new ControllerJTest());
        view.showGameWindow(GAME_WINDOW_DIMENSION_TEST, GAME_WINDOW_RATIO_TEST);
        Robot r = new Robot();

        final int xZero = view.getWindowLocation().get().x + view.getDimension().get().width / 2;
        final int yZero = view.getWindowLocation().get().y + view.getDimension().get().height / 2;
        final double[] testPoint = { Math.PI / 8, Math.PI / 4, Math.PI * 3 / 8, Math.PI, Math.PI * 5 / 8, Math.PI * 3 / 4, Math.PI * 7 / 8 };
        final Point2D.Double[] multiplier = { new Point2D.Double(0.5, -0.5), new Point2D.Double(0, -0.5),
                new Point2D.Double(-0.5, -0.5), new Point2D.Double(-0.5, 0), new Point2D.Double(-0.5, 0.5),
                new Point2D.Double(0, 0.5), new Point2D.Double(0.5, 0.5) };
        final double delta = 5.0;

        try {
            r.mouseMove((int) (view.getWindowLocation().get().x + view.getDimension().get().getWidth()),
                        (int) (view.getWindowLocation().get().y + (view.getDimension().get().getHeight() / 2))); // x > 0, y = 0 -> 0°
            assertEquals(0.0, view.getMouseRelativePositionInRange(0, 2 * Math.PI), delta);
        } catch (AssertionError aer) {
            assertEquals(2 * Math.PI, view.getMouseRelativePositionInRange(0, 2 * Math.PI), delta);
        }

        IntStream.range(0, testPoint.length)
                .mapToObj(i -> new ImmutablePair<Double, Point2D.Double>(testPoint[i], multiplier[i]))
                .forEach(pair -> {
                    r.mouseMove((int) (xZero + (view.getDimension().get().getWidth() * pair.getRight().x)),
                                (int) (yZero + (view.getDimension().get().getHeight() * pair.getRight().y)));
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

        @Override
        public File getFont() {
            return null;
        }

    }
    
    private class ViewJTest implements View {

        @Override
        public void showGameWindow(Dimension resolution, Pair<Integer, Integer> screenRatio) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public Optional<Point> getWindowLocation() {
            return Optional.of(TOP_LEFT_WINDOW_POINT);
        }

        @Override
        public double getMouseRelativePosition() {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public double getMouseRelativePositionInRange(double lowerBound, double upperBound) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public void addCommandsObserver(CommandsObserver commandsObserver) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void addSizeObserver(SizeObserver sizeObserver) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public Optional<Dimension> getDimension() {
            return Optional.of(GAME_WINDOW_DIMENSION_TEST);
        }

        @Override
        public List<MappableData> getListOfMappableData() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public int getScore() {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public void advanceLoading(int value) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void setLoadImage(ImageIcon img) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void setMouseRelativePosition(double mouseRelativePosition) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public Optional<Dimension> getGameWindowDimension() {
            return Optional.of(GAME_WINDOW_DIMENSION_TEST);
        }

        @Override
        public File getFont() {
            // TODO Auto-generated method stub
            return null;
        }

    }

}
