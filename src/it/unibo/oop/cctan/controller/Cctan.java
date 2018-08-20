package it.unibo.oop.cctan.controller;

import it.unibo.oop.cctan.view.View;
import it.unibo.oop.cctan.view.ViewImpl;

/**
 * The class that starts the base components of the game.
 */
public final class Cctan {

    private Cctan() {
    }

    /**
     * The static method that starts all.
     * 
     * @param args
     *            Parameters passed by command line. Unused.
     */
    public static void main(final String[] args) {
        final Controller ctx = new ControllerImpl();
        final View v = new ViewImpl(ctx);
        ctx.setView(v);
    }

}
