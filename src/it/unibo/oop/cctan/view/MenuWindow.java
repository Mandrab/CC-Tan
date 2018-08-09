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
import java.util.Optional;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MenuWindow {

    static final private String FILE_NAME = "./res//background2.jpg";
    private Optional<LeaderBoardTable> leaderboard = Optional.empty();
    private SettingsWindow pBGD;

    public MenuWindow(SettingsWindow pBG) {

        this.pBGD = pBG;

        JFrame mainFrame = new JFrame("oop17-cctan Main Menù");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setMaximumSize(new Dimension(400, 400));

        mainFrame.setLayout(new BorderLayout());

        // TODO settare l'indirizzo giusto
        String path = new File(FILE_NAME).getAbsolutePath();

        JLabel background = new JLabel(new ImageIcon(path));
        mainFrame.add(background);
        background.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        Font font = new Font("Serif", 18, 60);
        JLabel title = new JLabel();
        title.setFont(font);
        title.setText("CC-TAN");
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
        nickLabl.setText("Player name : " + pBG.getPlayerName());
        c.gridx = 0;
        c.gridy = 2;
        nickLabl.setHorizontalAlignment(JLabel.CENTER);
        background.add(nickLabl, c);

        c.insets = new Insets(10, 5, 0, 5);
        c.gridwidth = 1;

        JButton startBtn = new JButton("START");
        c.gridx = 0;
        c.gridy = 3;
        background.add(startBtn, c);

        JButton settingsBtn = new JButton("Settings");
        c.gridx = 0;
        c.gridy = 4;
        background.add(settingsBtn, c);

        JButton scoresBtn = new JButton("View Leaderboard");
        c.gridx = 0;
        c.gridy = 5;
        background.add(scoresBtn, c);

        JButton exitBtn = new JButton("Exit");
        c.gridx = 0;
        c.gridy = 6;
        background.add(exitBtn, c);
        JButton soundsBtn = new JButton("Mute");
        c.gridx = 1;
        c.gridy = 6;
        background.add(soundsBtn, c);

        mainFrame.setSize(font.getSize() * 4, font.getSize() * 5);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setLocation(dim.width / 2 - mainFrame.getSize().width / 2,
                dim.height / 2 - mainFrame.getSize().height / 2);
        mainFrame.setVisible(true);

        scoresBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showLeaderBoard();
                // new LeaderBoardTable();
                // leaderboard.get().addObserver(this);
            }
        });

        settingsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pBG.show();
                mainFrame.dispose();
            }
        });
        exitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        startBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                /*
                 * creare key listenercommand nella view e da quella fare un metodo che richiami
                 * lo start command e rihiamarlo qui per avvisare tutti
                 */

            }
        });
        soundsBtn.addActionListener(new ActionListener() {

            // utilizzare una classe come commands che avvisa chi di dovere per avviare o
            // bloccare il sounds
            public void actionPerformed(ActionEvent e) {
                if (pBGD.getClipMenu().isRunning()) {
                    pBGD.getClipMenu().stop();
                } else {
                    pBGD.getClipMenu().start();
                }
            }
        });

    }

    public void removeLeaderBoard() {
        if (leaderboard.isPresent()) {
            leaderboard = Optional.empty();
        }
    }

    public void showLeaderBoard() {
        if (!leaderboard.isPresent()) {
            leaderboard = Optional.of(new LeaderBoardTable(this));
        }
    }
}
