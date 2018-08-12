package it.unibo.oop.cctan.view;

import static org.junit.Assert.assertNotEquals;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Optional;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import it.unibo.oop.cctan.interPackageComunication.Commands;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserver;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserverSource;
import it.unibo.oop.cctan.interPackageComunication.LoadedFiles;
import it.unibo.oop.cctan.interPackageComunication.MappableData;
import it.unibo.oop.cctan.interPackageComunication.ModelData;

public class KeyListenerJTest {
    
    private static final Dimension GAME_WINDOW_DIMENSION_TEST = new Dimension(500, 500); // dimension of the window
    //private static final Pair<Integer, Integer> GAME_WINDOW_RATIO_TEST = new ImmutablePair<Integer, Integer>(1, 1);// ratio                                                                                                     /// window
    
    private static final int P_KEY_VALUE = KeyEvent.VK_P;
    private static final int SPACE_KEY_VALUE = KeyEvent.VK_SPACE;
    private static final int ESC_KEY_VALUE = KeyEvent.VK_ESCAPE;

    private Optional<GameWindow> gameWindow = Optional.empty();
    private View view;
    private KeyCommandsListener keyCommandsListener;
    private CommandsObserversManager commandsObserversManager;
    private boolean setuped = false;
    
    @Test
    synchronized void pausePPressed() throws Exception {
        KeyListenerTest(P_KEY_VALUE, Commands.PAUSE, true);
        KeyListenerTest(P_KEY_VALUE, Commands.RESUME, true);
        KeyListenerTest(P_KEY_VALUE, Commands.PAUSE, true);
        KeyListenerTest(P_KEY_VALUE, Commands.RESUME, true);
        KeyListenerTest(P_KEY_VALUE, Commands.RESUME, false);
    }
    
    @Test
    synchronized void escPauseAndPpressed() throws Exception {
        KeyListenerTest(ESC_KEY_VALUE, Commands.PAUSE, true);
        KeyListenerTest(P_KEY_VALUE, Commands.RESUME, false);
        KeyListenerTest(P_KEY_VALUE, Commands.RESUME, false);
        KeyListenerTest(ESC_KEY_VALUE, Commands.RESUME, false);

    }
    
     @Test
     synchronized void pauseSpacePressed() throws Exception {
        KeyListenerTest(SPACE_KEY_VALUE, Commands.PAUSE, true);
        KeyListenerTest(SPACE_KEY_VALUE, Commands.RESUME, true);
        KeyListenerTest(SPACE_KEY_VALUE, Commands.PAUSE, true);
        KeyListenerTest(SPACE_KEY_VALUE, Commands.RESUME, true);

    }

    private void KeyListenerTest(final int kcInput, final Commands cExpected, final boolean assertEquals) throws Exception {

        if(!setuped) {
            this.setuped=true;
            setUp();
        }
        
        Robot r = new Robot();
        r.keyPress(kcInput);
        //necessaria per fare funzionare il test
        Thread.sleep(100);
        
        if (assertEquals) {
            assertEquals(cExpected, keyCommandsListener.getActualState());
        } else {
            assertNotEquals(cExpected, keyCommandsListener.getActualState());
        }


    }
    
    public void setUp() {
        System.out.println("imposto la view");
        this.view = new ViewJTest();
        System.out.println("imposto la commandibservermanager");
        this.commandsObserversManager = new CommandsObserversManager();
        System.out.println("imposto la keycommandlistener");
        this.keyCommandsListener = new KeyCommandsListener(view);
        
        System.out.println("imposto un observer nella lista");
        this.commandsObserversManager.addCommandsObserver(new CommandsObserver() {
            @Override
            public void newCommand(final Commands command) {
                System.out.println("comando lanciato : " + command);
            }
        });

        System.out.println("imposto la gamewindow");
//        view.showGameWindow(GAME_WINDOW_DIMENSION_TEST, GAME_WINDOW_RATIO_TEST);
        
        JFrame jf = new JFrame();
        jf.addKeyListener(keyCommandsListener.getKeyListener());
        jf.setSize(GAME_WINDOW_DIMENSION_TEST);
        jf.setVisible(true);
        jf.requestFocus();
        
        System.out.println("imposto la startCommand");
        keyCommandsListener.startCommand();
        
        System.out.println(keyCommandsListener.getActualState()+" state");
    }

    private class ViewJTest extends SizeAndControlChainOfResponsibilityImpl implements View {

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
            return gameWindow.isPresent() 
                    ? Optional.ofNullable(gameWindow.get().getLocation()) 
                    : Optional.empty();
        }

        @Override
        public double getMouseRelativePosition() {
            return 0;
        }

        @Override
        public Optional<Dimension> getDimension() {
            return Optional.of(GAME_WINDOW_DIMENSION_TEST);
        }

        @Override
        public void setMouseRelativePosition(final double mouseRelativePosition) {
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
        public LoadedFiles getLoadedFiles() {
            return null;
        }

        @Override
        public CommandsObserversManager getCommandsObserversManager() {
            return commandsObserversManager;
        }

        @Override
        public ModelData getModelData() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void refreshGui() {
            // TODO Auto-generated method stub
            
        }

    }

}
