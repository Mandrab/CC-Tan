package it.unibo.oop.cctan.controller;

import javax.swing.ImageIcon;

public class FileLoader extends Thread {

    private Controller controller;
    
    public FileLoader(Controller controller) {
        this.controller = controller;
    }
    
    @Override
    public void run() {
        //controlla/crea cartella del gioco
        
        controller.advanceLoading(10);
        
        controller.setLoadImage(new ImageIcon(FileLoader.class.getResource("/Background1.jpg")));
        
        controller.advanceLoading(20);
        
    }
}
