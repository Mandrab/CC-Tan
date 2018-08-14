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
import it.unibo.oop.cctan.interPackageComunication.CommandsObserverSource;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserverSourceImpl;
import it.unibo.oop.cctan.interPackageComunication.LoadedFiles;
import it.unibo.oop.cctan.interPackageComunication.MappableData;
import it.unibo.oop.cctan.interPackageComunication.MappableDataImpl;
import it.unibo.oop.cctan.interPackageComunication.ModelData;
import it.unibo.oop.cctan.interPackageComunication.SizeObserverSource;

class CommandsJTest {

    private static final int SLEEP_TIME = 2000;
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final Dimension GAME_WINDOW_SIZE = new Dimension(SCREEN_SIZE.height / 2,
                                                                    SCREEN_SIZE.height / 2);
    private static final Pair<Integer, Integer> SCREEN_RATEO = new ImmutablePair<Integer, Integer>(1, 1);
    private GameWindow gw;
    private CommandsObserver commandsObserver;

    @Test
    void mouseEventsJTest() {
        MouseEvents me = new MouseEvents(new ViewJTest());
        assertTrue(me.isRunning());
        commandsObserver.newCommand(Commands.PAUSE);
        assertFalse(me.isRunning());
        commandsObserver.newCommand(Commands.RESUME);
        assertTrue(me.isRunning());
        commandsObserver.newCommand(Commands.END);
        assertFalse(me.isRunning());
    }

    /*@Test
    void graphicPanelUpdaterJTest() throws InterruptedException {
        gw = new GameWindow(new ViewJTest());
        gw.update(GAME_WINDOW_SIZE, SCREEN_RATEO);
        gw.setVisible(true);
        commandsObserver.newCommand(Commands.START);
        Thread.sleep(SLEEP_TIME);
        commandsObserver.newCommand(Commands.PAUSE);
        Thread.sleep(SLEEP_TIME);
        commandsObserver.newCommand(Commands.RESUME);
        Thread.sleep(SLEEP_TIME);
        commandsObserver.newCommand(Commands.END);
        Thread.sleep(SLEEP_TIME);
    }*/

    private class ViewJTest extends SizeAndControlChainOfResponsibilityImpl implements View {

        @Override
        public void showGameWindow(final Dimension resolution, final Pair<Integer, Integer> screenRatio) {
        }

        @Override
        public Optional<Point> getWindowLocation() {
            return Optional.of(new Point(0, 0));
        }

        @Override
        public double getMouseRelativePosition() {
            return 0;
        }

        private List<MappableData> getListOfMappableData() {
            return IntStream.range(0, 10)
                            .mapToObj(e -> new MappableDataImpl("Ciao", 
                                                                Color.RED, 
                                                                new Rectangle2D.Double(Math.random()*2 -1, 
                                                                                       Math.random()*2 -1, 
                                                                                       0.7, 
                                                                                       0.7)))
                            .collect(Collectors.toList());
        }

        private int getScore() {
            return (int) (Math.random() * 100);
        }

        @Override
        public void setMouseRelativePosition(final double mouseRelativePosition) {
        }

        @Override
        public Optional<Dimension> getGameWindowDimension() {
            return Optional.of(new Dimension(500, 500));
        }

        @Override
        public Optional<Dimension> getDimension() {
            return Optional.of(new Dimension(500, 500));
        }

        @Override
        public void showSettingsWindow() {
        }

        @Override
        public Optional<String> getPlayerName() {
            return null;
        }

        @Override
        public KeyCommandsListener getKeyCommandsListener() {
            return null;
        }

        @Override
        public Optional<CommandsObserverSource> getCommandsObserverSource() {
            return Optional.of(new CommandsObserverSource() {

                @Override
                public void addCommandsObserver(final CommandsObserver co) {
                    commandsObserver = co;
                }

                @Override
                public void removeCommandsObserver(final CommandsObserver co) {
                    commandsObserver = null;
                }

            });
        }

        @Override
        public Optional<SizeObserverSource> getSizeObserverSource() {
            return Optional.empty();
        }

        @Override
        public LoadedFiles getLoadedFiles() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public CommandsObserverSourceImpl getCommandsObserversManager() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public ModelData getModelData() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void refreshGui() {
        }

    }

}
