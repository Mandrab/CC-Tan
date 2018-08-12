package it.unibo.oop.cctan.controller;

import it.unibo.oop.cctan.interPackageComunication.Commands;
import it.unibo.oop.cctan.view.View;
import it.unibo.oop.cctan.view.ViewImpl;

public class Cctan {

    public static void main(String[] args) {
        Controller ctx = new ControllerImpl();
        View v = new ViewImpl(ctx);
        ctx.setView(v);
        ctx.newCommand(Commands.START);
    }

}
