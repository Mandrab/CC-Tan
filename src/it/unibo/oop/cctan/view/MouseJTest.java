package it.unibo.oop.cctan.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import javax.swing.ImageIcon;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import it.unibo.oop.cctan.interPackageComunication.CommandsObserver;
import it.unibo.oop.cctan.interPackageComunication.MappableData;

class MouseJTest {

    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final Dimension GAME_WINDOW_DIMENSION_TEST = new Dimension(SCREEN_SIZE.height / 2, 
                                                                              SCREEN_SIZE.height / 2); // dimension of the window
    private static final Point TOP_LEFT_WINDOW_POINT = new Point(SCREEN_SIZE.width / 4,
                                                                 SCREEN_SIZE.height / 4);
    private static final int POSITIONING_TIME = 10;
    
    @Test
    synchronized void classicMouseTest() throws Exception {
        Robot r = new Robot();
        MouseEvents me = new MouseEvents(new ViewJTest());

        final int xZero = TOP_LEFT_WINDOW_POINT.x + GAME_WINDOW_DIMENSION_TEST.width / 2;
        final int yZero = TOP_LEFT_WINDOW_POINT.y + GAME_WINDOW_DIMENSION_TEST.height / 2;
        final double[] testPoint = { 45.0, 
                                     90.0, 
                                     135.0, 
                                     180.0, 
                                     225.0, 
                                     270.0, 
                                     315.0 };
        final Point2D.Double[] multiplier = { new Point2D.Double(0.5, -0.5), 
                                              new Point2D.Double(0, -0.5),
                                              new Point2D.Double(-0.5, -0.5), 
                                              new Point2D.Double(-0.5, 0), 
                                              new Point2D.Double(-0.5, 0.5),
                                              new Point2D.Double(0, 0.5), 
                                              new Point2D.Double(0.5, 0.5) };
        final double delta = 1.0;

        try {
            r.mouseMove(xZero * 2, yZero); // x > 0, y = 0 -> 0°
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
                        Thread.sleep(POSITIONING_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                 });
    }
    
    @Test
    synchronized void rangeMouseTest() throws AWTException {
        Robot r = new Robot();
        MouseEvents me = new MouseEvents(new ViewJTest());

        final int xZero = TOP_LEFT_WINDOW_POINT.x + GAME_WINDOW_DIMENSION_TEST.width / 2;
        final int yZero = TOP_LEFT_WINDOW_POINT.y + GAME_WINDOW_DIMENSION_TEST.height / 2;
        final double[] testPoint = { Math.PI / 8, 
                                     Math.PI / 4, 
                                     Math.PI * 3 / 8, 
                                     Math.PI, 
                                     Math.PI * 5 / 8, 
                                     Math.PI * 3 / 4, 
                                     Math.PI * 7 / 8 };
        final Point2D.Double[] multiplier = { new Point2D.Double(0.5, -0.5), 
                                              new Point2D.Double(0, -0.5),
                                              new Point2D.Double(-0.5, -0.5), 
                                              new Point2D.Double(-0.5, 0), 
                                              new Point2D.Double(-0.5, 0.5),
                                              new Point2D.Double(0, 0.5), 
                                              new Point2D.Double(0.5, 0.5) };
        final double delta = 5.0;

        try {
            r.mouseMove(xZero * 2, yZero); // x > 0, y = 0 -> 0°
            assertEquals(0.0, me.getMouseRelativePositionInRange(0, 2 * Math.PI), delta);
        } catch (AssertionError aer) {
            assertEquals(2 * Math.PI, me.getMouseRelativePositionInRange(0, 2 * Math.PI), delta);
        }

        IntStream.range(0, testPoint.length)
                 .mapToObj(i -> new ImmutablePair<Double, Point2D.Double>(testPoint[i], multiplier[i]))
                 .forEach(pair -> {
                     r.mouseMove((int) (xZero + (GAME_WINDOW_DIMENSION_TEST.width * pair.getRight().x)),
                                 (int) (yZero + (GAME_WINDOW_DIMENSION_TEST.height * pair.getRight().y)));
                     assertEquals(pair.getLeft(), me.getMouseRelativePositionInRange(0, 2 * Math.PI), delta);
                 });
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

        @Override
        public void showSettingsWindow() {
            // TODO Auto-generated method stub
            
        }

        @Override
        public List<CommandsObserver> getCommandsObserversList() {
            // TODO Auto-generated method stub
            return null;
        }

    }

}
