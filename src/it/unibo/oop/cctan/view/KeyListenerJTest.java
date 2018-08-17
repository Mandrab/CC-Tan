package it.unibo.oop.cctan.view;

import static org.junit.Assert.assertNotEquals;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Optional;

import javax.swing.JFrame;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import it.unibo.oop.cctan.interPackageComunication.Commands;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserver;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserverSource;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserverSourceImpl;
import it.unibo.oop.cctan.interPackageComunication.GameStatus;
import it.unibo.oop.cctan.interPackageComunication.ModelData;
import it.unibo.oop.cctan.interPackageComunication.SizeObserverSource;

public class KeyListenerJTest {
    private static final Dimension GAME_WINDOW_DIMENSION_TEST = new Dimension(500, 500); // dimension of the window
    // private static final Pair<Integer, Integer> GAME_WINDOW_RATIO_TEST = new
    // ImmutablePair<Integer, Integer>(1, 1);// ratio /// window

    private static final int P_KEY_VALUE = KeyEvent.VK_P;
    private static final int SPACE_KEY_VALUE = KeyEvent.VK_SPACE;
    private static final int ESC_KEY_VALUE = KeyEvent.VK_ESCAPE;

    private Optional<GameWindow> gameWindow = Optional.empty();
    private static View view;
    private KeyCommandsListener keyCommandsListener;
    private static CommandsObserverSourceImpl commandsObserversManager;
    private static boolean setuped = false;

    @Test
    synchronized void pausePPressed() throws Exception {
        KeyListenerTest(P_KEY_VALUE, GameStatus.PAUSED, true);
        KeyListenerTest(P_KEY_VALUE, GameStatus.RUNNING, true);
        KeyListenerTest(P_KEY_VALUE, GameStatus.PAUSED, true);
        KeyListenerTest(P_KEY_VALUE, GameStatus.RUNNING, true);
        KeyListenerTest(P_KEY_VALUE, GameStatus.RUNNING, false);
    }

    @Test
    synchronized void escPauseAndPpressed() throws Exception {
        KeyListenerTest(ESC_KEY_VALUE, GameStatus.PAUSED, true);
        KeyListenerTest(P_KEY_VALUE, GameStatus.RUNNING, false);
        KeyListenerTest(P_KEY_VALUE, GameStatus.RUNNING, false);
        KeyListenerTest(ESC_KEY_VALUE, GameStatus.RUNNING, false);

    }

    @Test
    synchronized void pauseSpacePressed() throws Exception {
        KeyListenerTest(SPACE_KEY_VALUE, GameStatus.PAUSED, true);
        KeyListenerTest(SPACE_KEY_VALUE, GameStatus.RUNNING, true);
        KeyListenerTest(SPACE_KEY_VALUE, GameStatus.PAUSED, true);
        KeyListenerTest(SPACE_KEY_VALUE, GameStatus.RUNNING, true);

    }

    private void KeyListenerTest(final int kcInput, final GameStatus gSExpected, final boolean assertEquals)
            throws Exception {

        if (!setuped) {
            setuped = true;
            setUp();
        }

        final Robot r = new Robot();
        r.keyPress(kcInput);
        // necessaria per fare funzionare il test
        Thread.sleep(100);

        if (assertEquals) {
            assertEquals(gSExpected, keyCommandsListener.getActualState());
        } else {
            assertNotEquals(gSExpected, keyCommandsListener.getActualState());
        }

    }

    public final void setUp() {
        System.out.println("imposto la view");
        view = new ViewJTest();

        System.out.println("imposto la keycommandlistener");
        this.keyCommandsListener = new KeyCommandsListener(view);

        System.out.println("imposto la commandibservermanager");
        commandsObserversManager = new CommandsObserverSourceImpl() {

            @Override
            public void forceCommand(final Commands command) {
                switch (command) {
                case START:
                    keyCommandsListener.startCommand();
                    break;
                case PAUSE:
                    // todo
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

        System.out.println("imposto un observer nella lista");
        commandsObserversManager.addCommandsObserver(new CommandsObserver() {
            @Override
            public void newCommand(final Commands command) {
                System.out.println("comando lanciato : " + command);
            }
        });

        System.out.println("imposto la gamewindow");
        // view.showGameWindow(GAME_WINDOW_DIMENSION_TEST, GAME_WINDOW_RATIO_TEST);

        JFrame jf = new JFrame();
        jf.addKeyListener(keyCommandsListener.getKeyListener());
        jf.setSize(GAME_WINDOW_DIMENSION_TEST);
        jf.setVisible(true);
        jf.requestFocus();

        System.out.println("imposto la startCommand");
        keyCommandsListener.startCommand();

        System.out.println(keyCommandsListener.getActualState() + " state");
    }

    private class ViewJTest extends SizeAndCommandsLinkImpl implements View {

        @Override
        public void showGameWindow(final Dimension gameWindowSize, final Pair<Integer, Integer> screenRatio) {
            if (!gameWindow.isPresent()) {
                gameWindow = Optional.of(new GameWindow(this));
            }
            gameWindow.get().update(gameWindowSize, screenRatio);
            gameWindow.get().setVisible(true);
        }

        @Override
        public Optional<Point> getWindowLocation() {
            return gameWindow.isPresent() ? Optional.ofNullable(gameWindow.get().getLocation()) : Optional.empty();
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
        public void refreshGui(final Component component) {
        }

        @Override
        public void hideGameWindow() {
        }

    }

}
