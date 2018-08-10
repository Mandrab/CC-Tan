package it.unibo.oop.cctan.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.commons.lang3.tuple.ImmutableTriple;

public class EndWindow {
    static final private String FILE_NAME = "./res//background2.jpg";
    private int score;
    private View view;

    public EndWindow(View view) {

        // TODO usare metodo per ottenere lo score tramite view o controller?
        this.score = 123;
        this.view = view;

        JFrame mainFrame = new JFrame("oop17-cctan Conclusion Menù");
      //TODO impostare sicuro di volr uscire dal gioco?
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainFrame.setLayout(new BorderLayout());

        String path = new File(FILE_NAME).getAbsolutePath();

        JLabel background = new JLabel(new ImageIcon(path));
        mainFrame.add(background);
        background.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        Font font = new Font("Serif", 18, 60);
        JLabel title = new JLabel();
        title.setFont(font);
        title.setText("GAME OVER");
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        title.setHorizontalAlignment(JLabel.CENTER);
        background.add(title, c);
        c.gridx = 0;
        c.gridy = 1;
        title.setHorizontalAlignment(JLabel.CENTER);
        background.add(new JLabel("           "), c);

        JLabel nickLabl = new JLabel();
        nickLabl.setText("Your score : " + this.score);
        c.gridx = 0;
        c.gridy = 2;
        nickLabl.setHorizontalAlignment(JLabel.CENTER);
        background.add(nickLabl, c);

        c.insets = new Insets(10, 5, 0, 5);
        c.gridwidth = 1;

        JButton restartBtn = new JButton("Restart");
        c.gridx = 0;
        c.gridy = 6;
        background.add(restartBtn, c);

        JButton settingsBtn = new JButton("Settings");
        c.gridx = 0;
        c.gridy = 4;
        background.add(settingsBtn, c);

        JButton exitBtn = new JButton("Exit");
        c.gridx = 0;
        c.gridy = 5;
        background.add(exitBtn, c);

        mainFrame.setSize(font.getSize() * 4, font.getSize() * 4);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setLocation(dim.width / 2 - mainFrame.getSize().width / 2,
                dim.height / 2 - mainFrame.getSize().height / 2);
        mainFrame.setVisible(true);

        restartBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (view.getPlayerName().isPresent()) {
                    String nick = view.getPlayerName().get();
                    // SALVARE IL PUNTEGGIO SCORE ATTUALE
                    Records rec = new Records();
                    rec.addWithNoDuplicate(new ImmutableTriple<String, Integer, Date>(nick, score, new Date()));
                    
                  //MANDARE IL VOMANDO reset end e start
                    KeyCommandsListener c = view.getKeyCommandsListener();
                    c.setReset(true);
                    c.endCommand();
                    //TODO mettere una sleep?
                    c.startCommand();

                    mainFrame.dispose();
                }
            }
        });

        settingsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (view.getPlayerName().isPresent()) {
                    String nick = view.getPlayerName().get();
                    // SALVARE IL PUNTEGGIO SCORE ATTUALE
                    Records rec = new Records();
                    rec.addWithNoDuplicate(new ImmutableTriple<String, Integer, Date>(nick, score, new Date()));
                    
                    
                    //non mostrare la endwindow
                    KeyCommandsListener c = view.getKeyCommandsListener();
                    c.setReset(true);
                    c.endCommand();

                    // view.setting.Show(view) o qualcosa del genere
                    view.showSettingsWindow();
                    mainFrame.dispose();
                }
            }
        });
        exitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}

