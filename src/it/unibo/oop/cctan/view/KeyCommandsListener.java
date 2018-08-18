package it.unibo.oop.cctan.view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import it.unibo.oop.cctan.interpackage_comunication.Commands;
import it.unibo.oop.cctan.interpackage_comunication.CommandsObserverSourceImpl;
import it.unibo.oop.cctan.interpackage_comunication.GameStatus;

/**
 * A class which manages keystrokes of some keys via keyListener, and act
 * notifying all the CommandObserversListener.
 * 
 * @author Sutera Lorenzo
 *
 */
public class KeyCommandsListener extends CommandsObserverSourceImpl {

    private final View view;
    private final KeyListener keyListener;
    private GameStatus actualState = GameStatus.ENDED;
    private boolean reset;
    private boolean lockResumeKey;

    private static final int P_KEY_VALUE = 80;
    private static final int SPACE_KEY_VALUE = 32;
    private static final int ESC_KEY_VALUE = 27;

    /**
     * the constructor of the KeyCommandsListener class.
     * 
     * @param view
     *            A reference to the view (parents).
     */
    public KeyCommandsListener(final View view) {
        // TODO verificare vhe vada
        super();
        this.view = view;
        this.reset = false;

        keyListener = new KeyAdapter() {

            @Override
            public void keyPressed(final KeyEvent pressEvent) {
                final int keyCode = pressEvent.getKeyCode();
                switch (keyCode) {
                case P_KEY_VALUE:
                case SPACE_KEY_VALUE:
                    if (actualState != GameStatus.ENDED && !lockResumeKey) {
                        getCommandsObservers().forEach(co -> co
                                .newCommand(actualState == GameStatus.RUNNING ? Commands.PAUSE : Commands.RESUME));
                        actualState = actualState.denies();
                    }
                    break;
                case ESC_KEY_VALUE:
                    if (actualState.equals(GameStatus.RUNNING)) {
                        getCommandsObservers().forEach(co -> co.newCommand(Commands.PAUSE));
                        actualState = actualState.denies();
                        setLockResumeKey(true);

                        // avvia schermata ESC
                        new PauseWindow(view);
                    }
                    break;
                default:
                }
            }
        };
    }

    /**
     * Set the lockResumeKey variable.
     * 
     * @param lockResumeKey
     *            If true the p and SPACE keys do not act in any ways.
     */
    public void setLockResumeKey(final boolean lockResumeKey) {
        this.lockResumeKey = lockResumeKey;
    }

    /**
     * Getter for the variable lockResumeKey.
     * 
     * @return the boolean variable lockResumeKey.
     */
    public boolean isLockResumeKey() {
        return lockResumeKey;
    }

    /**
     * Return the key listener which have to be applied to the components of the
     * gameWindow.
     * 
     * @return A KeyListener which act if P, SPACE or ESCAPE are pressed.
     */
    public KeyListener getKeyListener() {
        return keyListener;
    }

    /**
     * If called notify all the Command Observers whit the START Command.
     * 
     * @return True if the command is sent, false if it is not sent because the game
     *         status is in running or paused.
     */
    public synchronized boolean startCommand() {
        if (actualState.equals(GameStatus.ENDED)) {
            getCommandsObservers().forEach(co -> co.newCommand(Commands.START));
            actualState = GameStatus.RUNNING;
            return true;
        }
        return false;
    }

    /**
     * If called notify all the Command Observers whit the PAUSE Command.
     * 
     * @return True if the command is sent, false if it is not sent because the game
     *         status is in ended or paused.
     */
    public synchronized boolean pauseCommand() {
        if (actualState.equals(GameStatus.RUNNING)) {
            getCommandsObservers().forEach(co -> co.newCommand(Commands.PAUSE));
            actualState = GameStatus.PAUSED;
            return true;
        }
        return false;
    }

    /**
     * If called notify all the Command Observers whit the RESUME Command.
     * 
     * @return True if the command is sent, false if it is not sent because the game
     *         status is in Running or Ended.
     */
    public synchronized boolean resumeCommand() {
        if (actualState.equals(GameStatus.PAUSED)) {
            getCommandsObservers().forEach(co -> co.newCommand(Commands.RESUME));
            actualState = GameStatus.RUNNING;
            return true;
        }
        return false;
    }

    /**
     * If called notify all the Command Observers whit the END Command.
     * 
     * @return True if the command is sent, false if it is not sent because the game
     *         status is: ended.
     */
    public synchronized boolean endCommand() {
        System.out.println("è stato richiesto l'end-command e il reset è " + reset);
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
        reset = false;
        return false;
    }

    /**
     * set the reset variable that indicate if the end command is used to reset or
     * to show the endWindow.
     * 
     * @param reset
     *            boolean variable that indicate if the end command will act as
     *            reset or not.
     */

    public synchronized void setReset(final boolean reset) {
        this.reset = reset;
    }

    /**
     * Return the actual state of the game.
     * 
     * @return A GameStatus value indicates the actual state of the game.
     */
    public GameStatus getActualState() {
        return this.actualState;
    }

    /**
     * Force to send the command indicate in the argument.
     * 
     * @param command
     *            the Command that we want to force.
     */
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
