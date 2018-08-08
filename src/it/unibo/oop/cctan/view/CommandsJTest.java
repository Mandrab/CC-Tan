package it.unibo.oop.cctan.view;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.List;
import java.util.Optional;
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
    
    private static final int SLEEP_TIME = 2000;
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final Dimension GAME_WINDOW_SIZE = new Dimension(SCREEN_SIZE.height,
                                                                    SCREEN_SIZE.height);
    private static final Pair<Integer, Integer> SCREEN_RATEO = new ImmutablePair<Integer, Integer>(1, 1);
    CommandsObserver co;
    
    @Test
    void MouseEventsJTest() {
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
    void GraphicPanelUpdaterJTest() throws InterruptedException {
        GameWindow gw = new GameWindow(new ViewJTest());
        gw.update(GAME_WINDOW_SIZE, SCREEN_RATEO);
        gw.setVisible(true);
        co.newCommand(Commands.START);
        Thread.sleep(SLEEP_TIME);
        co.newCommand(Commands.PAUSE);
        Thread.sleep(SLEEP_TIME);
        co.newCommand(Commands.RESUME);
        Thread.sleep(SLEEP_TIME);
        co.newCommand(Commands.END);
        Thread.sleep(SLEEP_TIME);
    }
    
    private class ViewJTest implements View {

        @Override
        public void showGameWindow(Dimension resolution, Pair<Integer, Integer> screenRatio) {
        }

        @Override
        public Optional<Point> getWindowLocation() {
            return Optional.of(new Point(0, 0));
        }

        @Override
        public double getMouseRelativePosition() {
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

        @Override
        public Optional<Dimension> getGameWindowDimension() {
            return Optional.of(new Dimension(500, 500));
        }

        @Override
        public File getFont() {
            return null;
        }

        @Override
        public Optional<Dimension> getDimension() {
            return Optional.of(new Dimension(500, 500));
        }

    }

}
