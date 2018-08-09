package it.unibo.oop.cctan.view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.List;

import it.unibo.oop.cctan.interPackageComunication.Commands;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserver;

public class KeyCommandsListener {

    KeyListener ls;
    View view;
    Commands actualState = Commands.END;

    public KeyCommandsListener(View view) {
        this.view = view;
        ls = new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                List<CommandsObserver> observers = view.getCommandsObserversList();

                // int id = e.getID();
                int keyCode = e.getKeyCode();
                if (keyCode == 80 || keyCode == 32) {
                    if (actualState.equals(Commands.START) || actualState.equals(Commands.RESUME)) {
                        for (Iterator<CommandsObserver> i = observers.iterator(); i.hasNext();) {
                            CommandsObserver c = i.next();
                            c.newCommand(Commands.PAUSE);

                        }
                    } else if (actualState.equals(Commands.PAUSE)) {
                        for (Iterator<CommandsObserver> i = observers.iterator(); i.hasNext();) {
                            CommandsObserver c = i.next();
                            c.newCommand(Commands.RESUME);
                        }
                        // changedState(new CommandsObserverImpl(Commands.PAUSE));
                        // se lo stato attuale del gioco è in corso allora metti pausa e se è in pausa
                        // metti in corso
                        for (Iterator<CommandsObserver> i = observers.iterator(); i.hasNext();) {
                            CommandsObserver c = i.next();
                            c.newCommand(Commands.PAUSE);
                        }
                    }

                    System.out.println("è stato premuto il tasto p o SPACE per la pausa");
                    System.out.println(
                            "se lo stato attuale del gioco è in corso allora metti pausa e se è in pausa metti in corso");

                } else if (keyCode == 27) {
                    if (actualState.equals(Commands.START) || actualState.equals(Commands.RESUME)) {
                        for (Iterator<CommandsObserver> i = observers.iterator(); i.hasNext();) {
                            CommandsObserver c = i.next();
                            c.newCommand(Commands.PAUSE);
                            // TODO avvia schermata ESC
                        }

                        System.out.println("esc pressed");

                        // changedState(new CommandsObserverImpl(Commands.START));
                        // metti pausa
                        // mostra una finestra per la conferma della chiusura del gioco e magari anche
                        // bottone per tornare al main menu
                        // se preme ok chiudi system.exit
                        // se preme cancel riprendi il gioco
                    }
                }
            }
        };
    }
}
