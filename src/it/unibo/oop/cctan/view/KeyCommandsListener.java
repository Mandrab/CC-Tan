package it.unibo.oop.cctan.view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyCommandsListener {

    KeyListener ls;
    View view;

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
                int id = e.getID();
                int keyCode = e.getKeyCode();
                if (keyCode == 80 || keyCode == 32) {
                    // changedState(new CommandsObserverImpl(Commands.PAUSE));
                    // se lo stato attuale del gioco è in corso allora metti pausa e se è in pausa
                    // metti in corso
                    System.out.println("è stato premuto il tasto p o SPACE per la pausa");
                    System.out.println(
                            "se lo stato attuale del gioco è in corso allora metti pausa e se è in pausa metti in corso");

                } else if (keyCode == 27) {
                    System.out.println("esc pressed");
                    
                    

                    // changedState(new CommandsObserverImpl(Commands.START));
                    // metti pausa
                    // mostra una finestra per la conferma della chiusura del gioco e magari anche
                    // bottone per tornare al main menu
                    // se preme ok chiudi system.exit
                    // se preme cancel riprendi il gioco
                }
            }
        };
    }

}
