package it.unibo.oop.cctan.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.File;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.commons.lang3.tuple.ImmutableTriple;

import it.unibo.oop.cctan.interPackageComunication.Commands;

public class PauseWindow {
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final String BACKGROUND_JPG = "/background2.jpg";
    private final int score;
    private final View view;

    public PauseWindow(final View view) {

        // TODO usare metodo per ottenere lo score tramite view o controller?

        this.view = view;
        this.score = this.view.getModelData().getScore();

        JFrame mainFrame = new JFrame("oop17-cctan Pause Menù");

        // TODO impostare sicuro di volr uscire dal gioco?
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainFrame.setLayout(new BorderLayout());
        
        ImageIcon imgIco = new ImageIcon(SettingsWindow.class.getResource(BACKGROUND_JPG));
        JLabel background = new JLabel(new ImageIcon(imgIco.getImage().getScaledInstance(SCREEN_SIZE.width / 5, SCREEN_SIZE.height / 3, 0)));
        mainFrame.add(background);
        mainFrame.setResizable(false);
        background.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        Font font = new Font("Serif", 18, 60);
        JLabel title = new JLabel();
        title.setFont(font);
        title.setText("PAUSE");
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

        JButton resumeBtn = new JButton("Resume");
        c.gridx = 0;
        c.gridy = 3;
        background.add(resumeBtn, c);


        mainFrame.pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setLocation(dim.width / 2 - mainFrame.getSize().width / 2,
                dim.height / 2 - mainFrame.getSize().height / 2);
        mainFrame.setVisible(true);

        restartBtn.addActionListener(e -> {
            if (view.getPlayerName().isPresent()) {
                String nick = view.getPlayerName().get();
                // SALVARE IL PUNTEGGIO SCORE ATTUALE
                Records rec = new Records(view);
                rec.addWithNoDuplicate(new ImmutableTriple<String, Integer, Date>(nick, score, new Date()));

                // MANDARE IL VOMANDO reset end e start
                view.getKeyCommandsListener().setReset(true);
                view.getKeyCommandsListener().endCommand();
//                try {
//                    Thread.sleep(200);
//                } catch (InterruptedException e1) {
//                    e1.printStackTrace();
//                }
                view.getKeyCommandsListener().startCommand();

                view.getKeyCommandsListener().setLockResumeKey(false);
                mainFrame.dispose();
            }
        });

        settingsBtn.addActionListener(e -> {
            if (view.getPlayerName().isPresent()) {
                String nick = view.getPlayerName().get();
                // SALVARE IL PUNTEGGIO SCORE ATTUALE
                Records rec = new Records(view);
                rec.addWithNoDuplicate(new ImmutableTriple<String, Integer, Date>(nick, score, new Date()));

                // MANDARE IL VOMANDO END
                view.getKeyCommandsListener().setReset(true);
                view.getKeyCommandsListener().endCommand();
                System.out.println("colpa del pause window");
                view.showSettingsWindow();
                view.getKeyCommandsListener().setLockResumeKey(false);
                mainFrame.dispose();
            }
        });

        exitBtn.addActionListener(e -> {
            System.exit(0);
        });

        resumeBtn.addActionListener(e -> {
            view.getKeyCommandsListener().setLockResumeKey(false);
            view.getKeyCommandsListener().forceCommand(Commands.RESUME);
            mainFrame.dispose();
        });
    }
}
