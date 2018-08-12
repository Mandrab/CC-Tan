package it.unibo.oop.cctan.view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.List;

import it.unibo.oop.cctan.interPackageComunication.Commands;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserver;

/**
 * A class created to handle the received keyEvents and send a Command by the newCommand method to the CommandsObservers.
 * @author Sutera Lorenzo
 *
 */
public class KeyCommandsListener {

    private KeyListener keyListener;
    private final View view;
    private Commands actualState = Commands.END;
    private boolean reset;
    private boolean escPaused;

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
        this.escPaused = false;
        keyListener = new KeyListener() {

            @Override
            public void keyTyped(final KeyEvent e) {

            }

            @Override
            public void keyReleased(final KeyEvent e) {

            }

            @Override
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
                }
                System.out.println(getActualState());
            }
        };
    }

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
            return true;
        }
        return false;
    }

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
            }
            actualState = Commands.START;
            System.out.println(getActualState());
            return true;
        }
        return false;
    }

    /**
     * set the reset variable that indicate if the end command is used to reset or to show the endWindow.
     * @param reset 
     *            boolean variable that indicate if the end command will act as reset or not.
     */

    public void setReset(final boolean reset) {
        this.reset = reset;
    }

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
        }
        return false;
    }
}
