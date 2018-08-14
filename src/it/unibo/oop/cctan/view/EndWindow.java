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
import javax.swing.JPanel;

import org.apache.commons.lang3.tuple.ImmutableTriple;

public class EndWindow {
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final String BACKGROUND_JPG = "/background2.jpg";
    private final int score;
    private final View view;

    public EndWindow(final View view) {
        this.view = view;
        this.score = this.view.getModelData().getScore();

        JFrame mainFrame = new JFrame("oop17-cctan Conclusion Menù");
        // TODO impostare sicuro di volr uscire dal gioco?
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainFrame.setLayout(new BorderLayout());
        
        Dimension windowDimension = tryDimensionOfWindow();

        ImageIcon imgIco = new ImageIcon(SettingsWindow.class.getResource(BACKGROUND_JPG));
        JLabel background = new JLabel(new ImageIcon(imgIco.getImage().getScaledInstance((int)windowDimension.getWidth(), (int)windowDimension.getHeight(), 0)));
//        JLabel background = new JLabel( new ImageIcon(imgIco.getImage().getScaledInstance(SCREEN_SIZE.width / 3, SCREEN_SIZE.height / 2, 0)));
        mainFrame.add(background);
        mainFrame.setResizable(false);
        background.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        Font font = new Font("Serif", 18, 60);
        JLabel title = new JLabel();
        title.setFont(font);
        title.setText("GAME-OVER");
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

        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

        restartBtn.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                if (view.getPlayerName().isPresent()) {
                    final String nick = view.getPlayerName().get();
                    // SALVARE IL PUNTEGGIO SCORE ATTUALE
                    Records rec = new Records(view);
                    rec.addWithNoDuplicate(new ImmutableTriple<String, Integer, Date>(nick, score, new Date()));

                    // MANDARE IL VOMANDO reset end e start
                    KeyCommandsListener c = view.getKeyCommandsListener();
                    c.setReset(true);
                    c.endCommand();
                    // try {
                    // Thread.sleep(200);
                    // } catch (InterruptedException e1) {
                    // e1.printStackTrace();
                    // }
                    c.startCommand();

                    mainFrame.dispose();
                }
            }
        });

        settingsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                if (view.getPlayerName().isPresent()) {
                    final String nick = view.getPlayerName().get();
                    // SALVARE IL PUNTEGGIO SCORE ATTUALE
                    Records rec = new Records(view);
                    rec.addWithNoDuplicate(new ImmutableTriple<String, Integer, Date>(nick, score, new Date()));

                    // non mostrare la endwindow
                    KeyCommandsListener c = view.getKeyCommandsListener();
                    c.setReset(true);
                    c.endCommand();

                    // view.setting.Show(view) o qualcosa del genere
                    System.out.println("colpa dell' end window");
                    view.showSettingsWindow();
                    mainFrame.dispose();
                }
            }
        });
        exitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                final String nick = view.getPlayerName().get();
                // SALVARE IL PUNTEGGIO SCORE ATTUALE
                Records rec = new Records(view);
                rec.addWithNoDuplicate(new ImmutableTriple<String, Integer, Date>(nick, score, new Date()));
                System.exit(0);
            }
        });
    }
    private Dimension tryDimensionOfWindow() {
        JFrame tmpSet = new JFrame("tryDimension");
        tmpSet.setLayout(new BorderLayout());
        JPanel tmpBackground = new JPanel();
        tmpSet.add(tmpBackground);
        tmpBackground.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        Font font = new Font("Serif", 18, 60);
        JLabel title = new JLabel();
        title.setFont(font);
        title.setText("GAME-OVER");
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        title.setHorizontalAlignment(JLabel.CENTER);
        tmpBackground.add(title, c);
        c.gridx = 0;
        c.gridy = 1;
        title.setHorizontalAlignment(JLabel.CENTER);
        tmpBackground.add(new JLabel("           "), c);

        JLabel nickLabl = new JLabel();
        nickLabl.setText("Your score : " + this.score);
        c.gridx = 0;
        c.gridy = 2;
        nickLabl.setHorizontalAlignment(JLabel.CENTER);
        tmpBackground.add(nickLabl, c);

        c.insets = new Insets(10, 5, 0, 5);
        c.gridwidth = 1;

        JButton restartBtn = new JButton("Restart");
        c.gridx = 0;
        c.gridy = 6;
        tmpBackground.add(restartBtn, c);

        JButton settingsBtn = new JButton("Settings");
        c.gridx = 0;
        c.gridy = 4;
        tmpBackground.add(settingsBtn, c);

        JButton exitBtn = new JButton("Exit");
        c.gridx = 0;
        c.gridy = 5;
        tmpBackground.add(exitBtn, c);


        tmpSet.pack();
        return tmpSet.getSize();
    }
}
