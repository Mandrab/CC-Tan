package it.unibo.oop.cctan.view;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.SwingConstants;

class Loader {
    
    private View view;
    
    public Loader(View view) {
        
        this.view = view;
        
        JWindow window = new JWindow();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        window.setBounds((int)((screenSize.getWidth() - screenSize.getWidth() / 5) / 2), 
                         (int)((screenSize.getHeight() - screenSize.getHeight() / 5) / 2), 
                         (int)(screenSize.getWidth() / 5),
                         (int)(screenSize.getHeight() / 5));
        
        JLabel splash = new JLabel("Loading");
        window.add(splash);
        
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setSize(window.getWidth(), (int)(window.getHeight()*0.05));
        window.add(progressBar, BorderLayout.SOUTH);
        progressBar.setVisible(true);
        window.setVisible(true);
        
        //controlla/crea cartella del gioco
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        progressBar.setValue(10);
        
        //se esiste carica l'immagine di loading, altrimenti quella di default
        //se non è già stata convertita converte l'svg
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        progressBar.setValue(20);
        
        window.remove(splash);
        splash = new JLabel("", 
                            new ImageIcon(Loader.class.getResource("/Background1.jpg")), 
                            SwingConstants.CENTER);
        splash.setBounds(0, 0, window.getWidth(), (int)(window.getHeight()*0.95));
        window.add(splash);
        window.setVisible(true);
        
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        window.setVisible(false);
        window.dispose();
    }
}
