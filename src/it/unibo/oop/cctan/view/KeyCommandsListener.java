package it.unibo.oop.cctan.view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.List;

import it.unibo.oop.cctan.interPackageComunication.Commands;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserver;

public class KeyCommandsListener {

    private KeyListener ls;
    private View view;
    private Commands actualState = Commands.END;
    private boolean reset;

    public KeyCommandsListener(View view) {
        this.view = view;
        this.reset = false;
        ls = new KeyListener() {

            @Override
            public void keyTyped(final KeyEvent e) {

            }

            @Override
            public void keyReleased(final KeyEvent e) {

            }

            @Override
            public void keyPressed(final KeyEvent e) {
                List<CommandsObserver> observers = view.getCommandsObserversList();

                // int id = e.getID();
                int keyCode = e.getKeyCode();
                if (keyCode == 80 || keyCode == 32) {
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

                } else if (keyCode == 27) {
                    if (actualState.equals(Commands.START) || actualState.equals(Commands.RESUME)) {
                        for (Iterator<CommandsObserver> i = observers.iterator(); i.hasNext();) {
                            CommandsObserver c = i.next();
                            c.newCommand(Commands.PAUSE);
                            actualState = Commands.PAUSE;
                            
                        }
                     //  avvia schermata ESC
                        new PauseWindow(view);
                        System.out.println("esc pressed");

                        // changedState(new CommandsObserverImpl(Commands.START));
                        // metti pausa
                        // mostra una finestra per la conferma della chiusura del gioco e magari anche
                        // bottone per tornare al main menu
                        // se preme ok chiudi system.exit
                    }
                }
            }
        };
    }
    
    public boolean endCommand() {
        List<CommandsObserver> observers = view.getCommandsObserversList();
        if (!actualState.equals(Commands.END)) {
            for (Iterator<CommandsObserver> i = observers.iterator(); i.hasNext();) {
                CommandsObserver c = i.next();
                c.newCommand(Commands.END);
                actualState = Commands.END;
                if(!reset) {
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
        List<CommandsObserver> observers = view.getCommandsObserversList();
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
    
    public void setReset(boolean reset) {
        this.reset = reset;
    }
    
    
}
