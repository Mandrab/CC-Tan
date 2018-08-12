package it.unibo.oop.cctan.view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.List;

import it.unibo.oop.cctan.interPackageComunication.Commands;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserver;

public class KeyCommandsListener {

    private KeyListener keyListener;
    private final View view;
    private Commands actualState = Commands.END;
    private boolean reset;

    private static final int P_KEY_VALUE = 80;
    private static final int SPACE_KEY_VALUE = 32;
    private static final int ESC_KEY_VALUE = 27;

    public KeyCommandsListener(final View view) {
        this.view = view;
        this.reset = false;
        keyListener = new KeyListener() {

            @Override
            public void keyTyped(final KeyEvent e) {

            }

            @Override
            public void keyReleased(final KeyEvent e) {

            }

            @Override
            public void keyPressed(final KeyEvent e) {
                //TODO VEDERE SE EUSARE MANAGER O SOURCE
                List<CommandsObserver> observers = view.getCommandsObserversManager().getCommandsObservers();
                // int id = e.getID();
                int keyCode = e.getKeyCode();
                if (keyCode == KeyCommandsListener.P_KEY_VALUE || keyCode == KeyCommandsListener.SPACE_KEY_VALUE) {
                    if (actualState.equals(Commands.START) || actualState.equals(Commands.RESUME)) {
                        for (Iterator<CommandsObserver> i = observers.iterator(); i.hasNext();) {
                            CommandsObserver c = i.next();
                            actualState = Commands.PAUSE;
                            c.newCommand(Commands.PAUSE);

                        }
                    } else if (actualState.equals(Commands.PAUSE)) {
                        for (Iterator<CommandsObserver> i = observers.iterator(); i.hasNext();) {
                            CommandsObserver c = i.next();
                            actualState = Commands.RESUME;
                            c.newCommand(Commands.RESUME);
                        }
                    }

                    System.out.println("è stato premuto il tasto p o SPACE per la pausa/resume");

                } else if (keyCode == KeyCommandsListener.ESC_KEY_VALUE) {
                    if (actualState.equals(Commands.START) || actualState.equals(Commands.RESUME) || actualState.equals(Commands.END)) {
                        for (Iterator<CommandsObserver> i = observers.iterator(); i.hasNext();) {
                            CommandsObserver c = i.next();
                            c.newCommand(Commands.PAUSE);
                            actualState = Commands.PAUSE;

                        }
                     //  avvia schermata ESC
                        new PauseWindow(view);
                        System.out.println("esc pressed and conditions are verificated");
                    }
                    System.out.println("esc pressed");
                }
            }
        };
    }

    public boolean endCommand() {
        //TODO
        List<CommandsObserver> observers = view.getCommandsObserversManager().getCommandsObservers();
        if (!actualState.equals(Commands.END)) {
            for (Iterator<CommandsObserver> i = observers.iterator(); i.hasNext();) {
                CommandsObserver c = i.next();
                c.newCommand(Commands.END);
                actualState = Commands.END;
                if (!reset) {
                // fare partire la schermata finale con score e tutto
                    new EndWindow(this.view);
                }
                reset = false;
                return true;
            }
        }
        return false;
    }

    public boolean startCommand() {
        //TODO
        List<CommandsObserver> observers = view.getCommandsObserversManager().getCommandsObservers();
        if (actualState.equals(Commands.END)) {
            for (Iterator<CommandsObserver> i = observers.iterator(); i.hasNext();) {
                CommandsObserver c = i.next();
                c.newCommand(Commands.START);
                actualState = Commands.START;
                return true;
            }
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

    public Commands getActualState() {
        return this.actualState;
    }
    
    public KeyListener getKeyListener() {
        return keyListener;
    }
}
