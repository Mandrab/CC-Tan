package it.unibo.oop.cctan.view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.List;

import it.unibo.oop.cctan.interPackageComunication.Commands;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserver;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserverSourceImpl;
import it.unibo.oop.cctan.interPackageComunication.GameStatus;

public class KeyCommandsListener extends CommandsObserverSourceImpl {

    private final View view;
    private KeyListener keyListener;
    private GameStatus actualState = GameStatus.ENDED;
    private boolean reset;

    private static final int P_KEY_VALUE = 80;
    private static final int SPACE_KEY_VALUE = 32;
    private static final int ESC_KEY_VALUE = 27;

    public KeyCommandsListener(final View view) {
        this.view = view;
        this.reset = false;

        keyListener = new KeyAdapter() {

            @Override
            public void keyPressed(final KeyEvent pressEvent) {
                int keyCode = pressEvent.getKeyCode();
                System.out.println("primo "+ actualState.name());
                switch (keyCode) {
                    case P_KEY_VALUE:
                    case SPACE_KEY_VALUE:
                        System.out.println("Valori prima della pressione: " 
                                           + actualState.toString() + " " 
                                           + (actualState == GameStatus.RUNNING 
                                             ? Commands.PAUSE 
                                             : Commands.RESUME)
                                             .toString());
                        getCommandsObservers().forEach(co -> co.newCommand(actualState == GameStatus.RUNNING ? Commands.PAUSE : Commands.RESUME));
                        actualState = actualState.denies();
                        System.out.println("Valori prima della pressione: " 
                                           + actualState.toString() + " " 
                                           + (actualState == GameStatus.RUNNING 
                                             ? Commands.PAUSE 
                                             : Commands.RESUME)
                                             .toString());
                        break;
                    case ESC_KEY_VALUE:
                        if (actualState.equals(GameStatus.RUNNING)) {
                            getCommandsObservers().forEach(co -> co.newCommand(Commands.PAUSE));
                            actualState = actualState.denies();

                            // avvia schermata ESC
                            new PauseWindow(view);
                            System.out.println("esc pressed and conditions are verificated");
                        }
                        System.out.println("esc pressed");
                        break;
                    default:
                }
            }
        };
    }

    public KeyListener getKeyListener() {
        return keyListener;
    }

    public synchronized boolean startCommand() {
        if (actualState.equals(GameStatus.ENDED)) {
            getCommandsObservers().forEach(co -> co.newCommand(Commands.START));
            actualState = GameStatus.RUNNING;
            return true;
        }
        return false;
    }

    public synchronized boolean pauseCommand() {
        if (actualState.equals(GameStatus.RUNNING)) {
            getCommandsObservers().forEach(co -> co.newCommand(Commands.PAUSE));
            actualState = GameStatus.PAUSED;
            return true;
        }
        return false;
    }

    public synchronized boolean resumeCommand() {
        if (actualState.equals(GameStatus.PAUSED)) {
            getCommandsObservers().forEach(co -> co.newCommand(Commands.RESUME));
            actualState = GameStatus.RUNNING;
            return true;
        }
        return false;
    }

    public synchronized boolean endCommand() {
        if (!actualState.equals(GameStatus.ENDED)) {
            getCommandsObservers().forEach(co -> co.newCommand(Commands.END));
            actualState = GameStatus.ENDED;
            if (!reset) {
                // fare partire la schermata finale con score e tutto
                new EndWindow(this.view);
            }
            reset = false;
            return true;
        }
        return false;
    }

    /**
     * set the reset variable that indicate if the end command is used to reset or to show the endWindow.
     * @param reset 
     *            boolean variable that indicate if the end command will act as reset or not.
     */

    public synchronized void setReset(final boolean reset) {
        this.reset = reset;
    }

    public synchronized void forceCommand(final Commands command) {
        switch (command) {
            case START:
                startCommand();
                break;
            case PAUSE:
                // todo
                break;
            case RESUME:
                resumeCommand();
                break;
            case END:
                endCommand();
                break;
            default:
        }
    }

}
