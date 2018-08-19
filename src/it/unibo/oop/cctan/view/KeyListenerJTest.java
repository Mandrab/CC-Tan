package it.unibo.oop.cctan.view;

import static org.junit.Assert.assertNotEquals;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Optional;

import javax.swing.JFrame;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import it.unibo.oop.cctan.interpackage_comunication.Commands;
import it.unibo.oop.cctan.interpackage_comunication.CommandsObserver;
import it.unibo.oop.cctan.interpackage_comunication.CommandsObserverSource;
import it.unibo.oop.cctan.interpackage_comunication.CommandsObserverSourceImpl;
import it.unibo.oop.cctan.interpackage_comunication.GameStatus;
import it.unibo.oop.cctan.interpackage_comunication.MappableData;
import it.unibo.oop.cctan.interpackage_comunication.ModelData;
import it.unibo.oop.cctan.interpackage_comunication.SizeObserverSource;

/**
 * KeyListener class test.
 * 
 * @author Sutera Lorenzo
 *
 */
public class KeyListenerJTest {
    private static final Dimension GAME_WINDOW_DIMENSION_TEST = new Dimension(500, 500); // dimension of the window

    private static final int P_KEY_VALUE = KeyEvent.VK_P;
    private static final int SPACE_KEY_VALUE = KeyEvent.VK_SPACE;
    private static final int ESC_KEY_VALUE = KeyEvent.VK_ESCAPE;

    private static View view;
    private KeyCommandsListener keyCommandsListener;
    private static CommandsObserverSourceImpl commandsObserversManager;
    private static boolean setuped;

    /**
     * this test the "p" key multiple times.
     */
    @Test
    public synchronized void pausePPressed() {
        setuped = false;
        keyListenerTest(P_KEY_VALUE, GameStatus.PAUSED, true);
        keyListenerTest(P_KEY_VALUE, GameStatus.RUNNING, true);
        keyListenerTest(P_KEY_VALUE, GameStatus.PAUSED, true);
        keyListenerTest(P_KEY_VALUE, GameStatus.RUNNING, true);
        keyListenerTest(P_KEY_VALUE, GameStatus.RUNNING, false);
        keyListenerTest(P_KEY_VALUE, GameStatus.RUNNING, true);
    }

    /**
     * this test the "escape" key one times and verify if the other key worcks. 
     */
    @Test
    public synchronized void tryEscPauseAndPpressed() {
        setuped = false;
        keyListenerTest(ESC_KEY_VALUE, GameStatus.PAUSED, true);
        keyListenerTest(P_KEY_VALUE, GameStatus.RUNNING, false);
        keyListenerTest(P_KEY_VALUE, GameStatus.RUNNING, false);
        keyListenerTest(ESC_KEY_VALUE, GameStatus.RUNNING, false);
        keyCommandsListener.setLockResumeKey(false);
    }

    /**
     * this test the "SPACE" key multiple times.
     */
    @Test
    public synchronized void pauseSpacePressed() {
        setuped = false;
        keyListenerTest(SPACE_KEY_VALUE, GameStatus.PAUSED, true);
        keyListenerTest(SPACE_KEY_VALUE, GameStatus.RUNNING, true);
        keyListenerTest(SPACE_KEY_VALUE, GameStatus.PAUSED, true);
        keyListenerTest(SPACE_KEY_VALUE, GameStatus.RUNNING, true);
    }

    /**
     * to use those tests it is necessary to not use any kind of input until the test ends.
     * @param kcInput the code of the key specified.
     * @param gSExpected the status we are verifying.
     * @param assertEquals if the state will be the same or not.
     */
    private void keyListenerTest(final int kcInput, final GameStatus gSExpected, final boolean assertEquals) {

        if (!setuped) {
            setuped = true;
            setUp();
        }

        Robot r;
        try {
            r = new Robot();
            r.keyPress(kcInput);
            r.keyRelease(kcInput);
         // necessaria per fare funzionare il test
            Thread.sleep(100);
        } catch (AWTException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (assertEquals) {
            assertEquals(gSExpected, keyCommandsListener.getActualState());
        } else {
            assertNotEquals(gSExpected, keyCommandsListener.getActualState());
        }

    }

    @Before
    private void setUp() {
        view = new ViewJTest();
        this.keyCommandsListener = new KeyCommandsListener(view);
        commandsObserversManager = new CommandsObserverSourceImpl() {
            @Override
            public void forceCommand(final Commands command) {
                switch (command) {
                case START:
                    keyCommandsListener.startCommand();
                    break;
                case PAUSE:
                    break;
                case RESUME:
                    keyCommandsListener.resumeCommand();
                    break;
                case END:
                    keyCommandsListener.endCommand();
                    break;
                default:
                }
            }
        };
        commandsObserversManager.addCommandsObserver(new CommandsObserver() {
            @Override
            public void newCommand(final Commands command) {
                //System.out.println("comando lanciato : " + command);
            }
        });
        final JFrame jf = new JFrame();
        jf.addKeyListener(keyCommandsListener.getKeyListener());
        jf.setSize(GAME_WINDOW_DIMENSION_TEST);
        jf.setVisible(true);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        jf.requestFocus();

        keyCommandsListener.startCommand();
    }

    private class ViewJTest extends SizeAndCommandsLinkImpl implements View {

        @Override
        public void showGameWindow(final Dimension gameWindowSize, final Pair<Integer, Integer> screenRatio) {
        }

        @Override
        public Optional<Point> getWindowLocation() {
            return Optional.empty();
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
            return keyCommandsListener;
        }

        @Override
        public Optional<CommandsObserverSource> getCommandsObserverSource() {
            return Optional.of(commandsObserversManager);
        }

        @Override
        public Optional<SizeObserverSource> getSizeObserverSource() {
            return Optional.empty();
        }

        @Override
        public ModelData getModelData() {
            return new ModelData() {

                @Override
                public int getScore() {
                    return 0;
                }

                @Override
                public List<MappableData> getMappableDatas() {
                    return null;
                }

                @Override
                public GameStatus getGameStatus() {
                    return null;
                }
            };
        }

        @Override
        public void refreshGui() {
        }

        @Override
        public void hideGameWindow() {
        }

    }

}
