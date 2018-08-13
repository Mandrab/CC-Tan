package it.unibo.oop.cctan.view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import it.unibo.oop.cctan.interPackageComunication.Commands;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserverSourceImpl;
import it.unibo.oop.cctan.interPackageComunication.GameStatus;

<<<<<<< HEAD
/**
 * A class created to handle the received keyEvents and send a Command by the newCommand method to the CommandsObservers.
 * @author Sutera Lorenzo
 *
 */
public class KeyCommandsListener {
=======
public class KeyCommandsListener extends CommandsObserverSourceImpl {
>>>>>>> e364e05ebd100dd29b959931c16ad2b6f05f3585

    private final View view;
    private KeyListener keyListener;
    private GameStatus actualState = GameStatus.ENDED;
    private boolean reset;

    private static final int P_KEY_VALUE = 80;
    private static final int SPACE_KEY_VALUE = 32;
    private static final int ESC_KEY_VALUE = 27;

    /**
     * The constructor of KeyCommandsListener class.
     * 
     *  @param view
     *            A reference to the view (parents).
     */
    public KeyCommandsListener(final View view) {
        this.view = view;
        this.reset = false;

        keyListener = new KeyAdapter() {

            @Override
<<<<<<< HEAD
            public void keyPressed(final KeyEvent e) {
                List<CommandsObserver> observers = view.getCommandsObserversManager().getCommandsObservers();
                // int id = e.getID();
                int keyCode = e.getKeyCode();
                if (keyCode == KeyCommandsListener.P_KEY_VALUE || keyCode == KeyCommandsListener.SPACE_KEY_VALUE) {
                    if (actualState.equals(Commands.START) || actualState.equals(Commands.RESUME)) {
                        for (Iterator<CommandsObserver> i = observers.iterator(); i.hasNext();) {
                            CommandsObserver c = i.next();
                            c.newCommand(Commands.PAUSE);
                        }
                        actualState = Commands.PAUSE;
                    } else if (actualState.equals(Commands.PAUSE) && !escPaused) {
                        for (Iterator<CommandsObserver> i = observers.iterator(); i.hasNext();) {
                            CommandsObserver c = i.next();
                            c.newCommand(Commands.RESUME);
                        }
                        actualState = Commands.RESUME;
                    }

                    System.out.println("è stato premuto il tasto p o SPACE per la pausa/resume");

                } else if (keyCode == KeyCommandsListener.ESC_KEY_VALUE) {
                    if (actualState.equals(Commands.START) || actualState.equals(Commands.RESUME)) {
                        keyEscPaused(true);
                        for (Iterator<CommandsObserver> i = observers.iterator(); i.hasNext();) {
                            CommandsObserver c = i.next();
                            c.newCommand(Commands.PAUSE);
                        }
                        actualState = Commands.PAUSE;
                     //  avvia schermata ESC
                        new PauseWindow(view);
                        System.out.println("esc pressed and conditions are verificated");
                    }
                    System.out.println("esc pressed");
=======
            public void keyPressed(final KeyEvent pressEvent) {
                int keyCode = pressEvent.getKeyCode();
                switch (keyCode) {
                    case P_KEY_VALUE:
                    case SPACE_KEY_VALUE:
                        getCommandsObservers().forEach(co -> co.newCommand(actualState == GameStatus.RUNNING ? Commands.PAUSE : Commands.RESUME));
                        actualState = actualState.denies();
                        break;
                    case ESC_KEY_VALUE:
                        if (actualState.equals(GameStatus.RUNNING)) {
                            getCommandsObservers().forEach(co -> co.newCommand(Commands.PAUSE));
                            actualState = actualState.denies();

                            // avvia schermata ESC
                            new PauseWindow(view);
                        }
                        break;
                    default:
>>>>>>> e364e05ebd100dd29b959931c16ad2b6f05f3585
                }
            }
        };
    }

<<<<<<< HEAD
    /**
     * Send the END command to all the CommandsObservers if internal conditions are respected.
     * @return
     *          A boolean value that indicate if the command is sent or not.
     */
    public boolean endCommand() {
        List<CommandsObserver> observers = view.getCommandsObserversManager().getCommandsObservers();
        if (!actualState.equals(Commands.END)) {
            for (Iterator<CommandsObserver> i = observers.iterator(); i.hasNext();) {
                CommandsObserver c = i.next();
                c.newCommand(Commands.END);
            }
            actualState = Commands.END;
            if (!reset) {
                // fare partire la schermata finale con score e tutto
                    new EndWindow(this.view);
                }
            reset = false;
=======
    public KeyListener getKeyListener() {
        return keyListener;
    }

    public synchronized boolean startCommand() {
        if (actualState.equals(GameStatus.ENDED)) {
            getCommandsObservers().forEach(co -> co.newCommand(Commands.START));
            actualState = GameStatus.RUNNING;
>>>>>>> e364e05ebd100dd29b959931c16ad2b6f05f3585
            return true;
        }
        return false;
    }

<<<<<<< HEAD
    /**
     * Send the START command to all the CommandsObservers if internal conditions are respected.
     * @return
     *          A boolean value that indicate if the command is sent or not.
     */
    public boolean startCommand() {
        List<CommandsObserver> observers = view.getCommandsObserversManager().getCommandsObservers();
        if (actualState.equals(Commands.END)) {
            for (Iterator<CommandsObserver> i = observers.iterator(); i.hasNext();) {
                CommandsObserver c = i.next();
                c.newCommand(Commands.START);
=======
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
>>>>>>> e364e05ebd100dd29b959931c16ad2b6f05f3585
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

<<<<<<< HEAD
    /**
     * Return the actual state of the class, so the last command sent to the commands Observers.
     * @return
     *          a Command that rappresent the actual state of the class.
     */
    public Commands getActualState() {
        return this.actualState;
    }

    /**
     * Return the keyListener that have to be applied to the jComponents of the gameWindow.
     * @return
     *          a KeyListener that recive the P, SPACE and ESC key events and send commands.
     */
    public KeyListener getKeyListener() {
        return keyListener;
    }

    /**
     * Set a variable that will be used as a part of a condition that if it is true the P keyboard pressed event can not send the resume command.
     * @param escPaused
     *                  a boolean variable that set the possibility or not for the P button to resume in pause state.
     */
    public void keyEscPaused(final boolean escPaused) {
        this.escPaused = escPaused;
    }

    /**
     * Send the RESUME command to all the CommandsObservers if internal conditions are respected.
     * @return
     *          A boolean value that indicate if the command is sent or not.
     */
    public boolean resumeCommand() {
        List<CommandsObserver> observers = view.getCommandsObserversManager().getCommandsObservers();
        if (actualState.equals(Commands.PAUSE)) {
            for (Iterator<CommandsObserver> i = observers.iterator(); i.hasNext();) {
                CommandsObserver c = i.next();
                c.newCommand(Commands.RESUME);
            }
            actualState = Commands.RESUME;
            System.out.println(getActualState());
            return true;
=======
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
>>>>>>> e364e05ebd100dd29b959931c16ad2b6f05f3585
        }
    }

}
