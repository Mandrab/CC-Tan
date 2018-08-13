package it.unibo.oop.cctan.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.File;
import java.util.Optional;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import it.unibo.oop.cctan.interPackageComunication.Commands;

public class MenuWindow extends JFrame {

    private static final long serialVersionUID = 2339975308093481172L;
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final String BACKGROUND_JPG = "/background2.jpg";
    private Optional<LeaderBoardTable> leaderboard = Optional.empty();
    private View view;

    public MenuWindow(final View view, final SettingsWindow settingsWindow) {

        this.view = view;

        setTitle("oop17-cctan Main Menù");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //COMPONENTS
        ImageIcon imgIco = new ImageIcon(SettingsWindow.class.getResource(BACKGROUND_JPG));
        JLabel background = new JLabel(new ImageIcon(imgIco.getImage().getScaledInstance(SCREEN_SIZE.width / 5, SCREEN_SIZE.height / 3, 0)));
        background.setLayout(new GridBagLayout());
        add(background);
        this.setResizable(false);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;

        Font font = new Font("Serif", 18, 60);
        JLabel title = new JLabel();
        title.setFont(font);
        title.setText("CC-TAN");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        title.setHorizontalAlignment(JLabel.CENTER);
        background.add(title, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        title.setHorizontalAlignment(JLabel.CENTER);
        background.add(new JLabel("           "), constraints);

        JLabel nickLabl = new JLabel();
        nickLabl.setText("Player name : " + view.getPlayerName().get());
        constraints.gridx = 0;
        constraints.gridy = 2;
        nickLabl.setHorizontalAlignment(JLabel.CENTER);
        background.add(nickLabl, constraints);

        constraints.insets = new Insets(10, 5, 0, 5);
        constraints.gridwidth = 1;

        JButton startBtn = new JButton("START");
        constraints.gridx = 0;
        constraints.gridy = 3;
        background.add(startBtn, constraints);

        JButton settingsBtn = new JButton("Settings");
        constraints.gridx = 0;
        constraints.gridy = 4;
        background.add(settingsBtn, constraints);

        JButton scoresBtn = new JButton("View Leaderboard");
        constraints.gridx = 0;
        constraints.gridy = 5;
        background.add(scoresBtn, constraints);

        JButton exitBtn = new JButton("Exit");
        constraints.gridx = 0;
        constraints.gridy = 6;
        background.add(exitBtn, constraints);
        
        JButton soundsBtn = new JButton("Mute");
        constraints.gridx = 1;
        constraints.gridy = 6;
        background.add(soundsBtn, constraints);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        scoresBtn.addActionListener(e -> {
            showLeaderBoard();
        });

        settingsBtn.addActionListener(e -> {
            System.out.println("colpa del menu window");
            view.showSettingsWindow();
            dispose();
        });

        exitBtn.addActionListener(e -> {
            System.exit(0);
        });

        startBtn.addActionListener(e -> {
            dispose();
            view.getKeyCommandsListener().forceCommand(Commands.START);
            //Questi sotto da spostare nella view
            view.showGameWindow(settingsWindow.getDimension().get(), settingsWindow.getRatio().get()); // da togliere
            view.getCommandsObserverSource().ifPresent(s -> s.forceCommand(Commands.START));
        });

        soundsBtn.addActionListener(e -> {
            // utilizzare una classe come commands che avvisa chi di dovere per avviare o
            // bloccare il sounds
            if (settingsWindow.getClipMenu().isRunning()) {
                settingsWindow.getClipMenu().stop();
                soundsBtn.setText("Unmute");
            } else {
                settingsWindow.getClipMenu().start();
                soundsBtn.setText("Mute");
            }
        });

    }
    
    public View getView() {
        return this.view;
    }

    public String getPlayerName() {
        return view.getPlayerName().get();
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
