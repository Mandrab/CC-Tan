package it.unibo.oop.cctan.controller;

import it.unibo.oop.cctan.view.ViewImpl;

public class Cctan {

    public static void main(String[] args) {
        Controller ctx = new ControllerImpl();
        new ViewImpl(ctx);
    }

}
