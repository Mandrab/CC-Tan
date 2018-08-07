package it.unibo.oop.cctan.view;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.ImageIcon;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import it.unibo.oop.cctan.interPackageComunication.Commands;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserver;
import it.unibo.oop.cctan.interPackageComunication.MappableData;
import it.unibo.oop.cctan.interPackageComunication.MappableDataImpl;

class CommandsJTest {
    
    CommandsObserver co;
    
    @Test
    synchronized void MouseEventsJTest() {
        MouseEvents me = new MouseEvents(new ViewJTest());
        assertTrue(me.isRunning());
        co.newCommand(Commands.PAUSE);
        assertFalse(me.isRunning());
        co.newCommand(Commands.RESUME);
        assertTrue(me.isRunning());
        co.newCommand(Commands.END);
        assertFalse(me.isRunning());
    }
    
    @Test
    synchronized void GraphicPanelUpdaterJTest() {
        GameWindow gw = new GameWindow(new ViewJTest(), 
                                       new Dimension(500, 500), 
                                       new ImmutablePair<Integer, Integer>(1, 1));
        gw.setVisible(true);
        co.newCommand(Commands.START);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        co.newCommand(Commands.PAUSE);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        co.newCommand(Commands.RESUME);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        co.newCommand(Commands.END);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private class ViewJTest implements View {

        @Override
        public void showGameWindow(Dimension resolution, Pair<Integer, Integer> screenRatio) {
        }

        @Override
        public Point getWindowLocation() {
            return new Point(0, 0);
        }

        @Override
        public double getMouseRelativePosition() {
            return 0;
        }

        @Override
        public double getMouseRelativePositionInRange(double lowerBound, double upperBound) {
            return 0;
        }

        @Override
        public void addCommandsObserver(CommandsObserver commandsObserver) {
            co = commandsObserver;
        }

        @Override
        public void addSizeObserver(SizeObserver sizeObserver) {
        }

        @Override
        public Dimension getDimension() {
            return new Dimension(500, 500);
        }

        @Override
        public List<MappableData> getListOfMappableData() {
            return IntStream.range(0, 10)
                            .mapToObj(e -> new MappableDataImpl("Ciao", 
                                                                Color.RED, 
                                                                new Rectangle2D.Double(Math.random()*2 -1, 
                                                                                       Math.random()*2 -1, 
                                                                                       0.7, 
                                                                                       0.7)))
                            .collect(Collectors.toList());
        }

        @Override
        public int getScore() {
            return (int) (Math.random() * 100);
        }

        @Override
        public void advanceLoading(int value) {
        }

        @Override
        public void setLoadImage(ImageIcon img) {
        }

        @Override
        public void setMouseRelativePosition(double mouseRelativePosition) {
        }

    }

}
