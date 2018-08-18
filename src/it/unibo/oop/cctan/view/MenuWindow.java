package it.unibo.oop.cctan.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Optional;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import it.unibo.oop.cctan.interpackage_comunication.Commands;
import it.unibo.oop.cctan.interpackage_comunication.LoadedFilesSingleton;
import it.unibo.oop.cctan.interpackage_comunication.LoadedFiles.ImageType;

/**
 * Class that instance the component used to show the main menu to the user.
 */
public class MenuWindow extends JFrame {

    private static final int INSETS = 5;
    private static final int FONT_STYLE = 18;
    private static final int FONT_SIZE = 60;
    private static final long serialVersionUID = 2339975308093481172L;
    private static final String BACKGROUND_JPG = "/background2.jpg";
    private Optional<LeaderBoardTable> leaderboard = Optional.empty();
    private final View view;

    /**
     * the constructor of the SettingsWindow class.
     * 
     * @param view
     *            A reference to the view (parents).
     * @param settingsWindow
     *            A reference to the settingsWindow.
     */
    public MenuWindow(final View view, final SettingsWindow settingsWindow) {
        super();
        this.view = view;
        setUpWindow(settingsWindow);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setUpWindow(final SettingsWindow settingsWindow) {
        final Dimension settingsDimansion = tryDimensionOfWindow();
        LoadedFilesSingleton.getLoadedFiles().getImage(ImageType.ICON).ifPresent(img -> setIconImage(img.getImage()));
        setTitle("oop17-cctan Main Menù");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // COMPONENTS
        final ImageIcon imgIco = new ImageIcon(SettingsWindow.class.getResource(BACKGROUND_JPG));
        final JLabel background = new JLabel(new ImageIcon(imgIco.getImage()
                .getScaledInstance((int) settingsDimansion.getWidth(), (int) settingsDimansion.getHeight(), 0)));
        background.setLayout(new GridBagLayout());
        add(background);
        this.setResizable(false);

        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;

        int pos = 0;
        final Font font = new Font("Serif", FONT_STYLE, FONT_SIZE);
        final JLabel title = new JLabel();
        title.setFont(font);
        title.setText("CC-TAN");
        constraints.gridx = 0;
        constraints.gridy = pos++;
        constraints.gridwidth = 2;
        title.setHorizontalAlignment(JLabel.CENTER);
        background.add(title, constraints);

        constraints.gridx = 0;
        constraints.gridy = pos++;
        title.setHorizontalAlignment(JLabel.CENTER);
        background.add(new JLabel("           "), constraints);

        final JLabel nickLabl = new JLabel();
        nickLabl.setText("Player name : " + view.getPlayerName().get());
        constraints.gridx = 0;
        constraints.gridy = pos++;
        nickLabl.setHorizontalAlignment(JLabel.CENTER);
        background.add(nickLabl, constraints);

        constraints.insets = new Insets(10, INSETS, 0, INSETS);
        constraints.gridwidth = 1;

        final JButton startBtn = new JButton("START");
        constraints.gridx = 0;
        constraints.gridy = pos++;
        background.add(startBtn, constraints);

        final JButton settingsBtn = new JButton("Settings");
        constraints.gridx = 0;
        constraints.gridy = pos++;
        background.add(settingsBtn, constraints);

        final JButton scoresBtn = new JButton("View Leaderboard");
        constraints.gridx = 0;
        constraints.gridy = pos++;
        background.add(scoresBtn, constraints);

        final JButton exitBtn = new JButton("Exit");
        constraints.gridx = 0;
        constraints.gridy = pos;
        background.add(exitBtn, constraints);

        final JButton soundsBtn;
        if (settingsWindow.getClipMenu().isRunning()) {
            soundsBtn = new JButton("Mute");
        } else {
            soundsBtn = new JButton("Unmute");
        }
        constraints.gridx = 1;
        constraints.gridy = pos;
        background.add(soundsBtn, constraints);

        scoresBtn.addActionListener(e -> {
            showLeaderBoard();
        });

        settingsBtn.addActionListener(e -> {
            view.showSettingsWindow();
            dispose();
        });

        exitBtn.addActionListener(e -> {
            Runtime.getRuntime().exit(0);
            // System.exit(0);
        });

        startBtn.addActionListener(e -> {

            view.getKeyCommandsListener().forceCommand(Commands.START);
            view.showGameWindow(settingsWindow.getDimension().get(), settingsWindow.getRatio().get());
            // view.getCommandsObserverSource().ifPresent(s ->
            // s.forceCommand(Commands.START));
            dispose();
        });

        soundsBtn.addActionListener(e -> {
            // utilizzare una classe come commands che avvisa chi di dovere per avviare o
            // bloccare il sounds
            SwingUtilities.invokeLater(() -> {
                if (settingsWindow.getClipMenu().isRunning()) {
                    settingsWindow.getClipMenu().stop();
                    soundsBtn.setText("Unmute");
                } else {
                    settingsWindow.getClipMenu().start();
                    soundsBtn.setText("Mute");
                }
            });
        });
    }

    private Dimension tryDimensionOfWindow() {
        final JFrame tmpSet = new JFrame("tryDimension");
        tmpSet.setLayout(new BorderLayout());
        final JPanel tmpBackground = new JPanel();
        tmpSet.add(tmpBackground);
        tmpBackground.setLayout(new GridBagLayout());
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;

        int pos = 0;
        final Font font = new Font("Serif", FONT_STYLE, FONT_SIZE);
        final JLabel title = new JLabel();
        title.setFont(font);
        title.setText("CC-TAN");
        constraints.gridx = 0;
        constraints.gridy = pos++;
        constraints.gridwidth = 2;
        title.setHorizontalAlignment(JLabel.CENTER);
        tmpBackground.add(title, constraints);
        title.setHorizontalAlignment(JLabel.CENTER);

        final JLabel nickLabl = new JLabel();
        nickLabl.setText("Player name : ten chars !");
        constraints.gridx = 0;
        constraints.gridy = pos++;
        nickLabl.setHorizontalAlignment(JLabel.CENTER);
        tmpBackground.add(nickLabl, constraints);

        constraints.insets = new Insets(10, INSETS, 0, INSETS);
        constraints.gridwidth = 1;

        final JButton startBtn = new JButton("START");
        constraints.gridx = 0;
        constraints.gridy = pos++;
        tmpBackground.add(startBtn, constraints);

        final JButton settingsBtn = new JButton("Settings");
        constraints.gridx = 0;
        constraints.gridy = pos++;
        tmpBackground.add(settingsBtn, constraints);

        final JButton scoresBtn = new JButton("View Leaderboard");
        constraints.gridx = 0;
        constraints.gridy = pos++;
        tmpBackground.add(scoresBtn, constraints);

        final JButton exitBtn = new JButton("Exit");
        constraints.gridx = 0;
        constraints.gridy = pos;
        tmpBackground.add(exitBtn, constraints);

        final JButton soundsBtn = new JButton("Mute");
        constraints.gridx = 1;
        constraints.gridy = pos;
        tmpBackground.add(soundsBtn, constraints);

        tmpSet.pack();
        return tmpSet.getSize();
    }

    /**
     * return the view.
     * 
     * @return the View insance.
     */
    public View getView() {
        return this.view;
    }

    /**
     * return the Player nickname.
     * 
     * @return a String containing the player nickname.
     */
    public String getPlayerName() {
        return view.getPlayerName().get();
    }

    /**
     * A method that remove all the saved records.
     */
    public void removeLeaderBoard() {
        if (leaderboard.isPresent()) {
            leaderboard = Optional.empty();
        }
    }

    /**
     * A method that show the leaderboard table which contain a ordered table of the
     * best scores.
     */
    public void showLeaderBoard() {
        if (!leaderboard.isPresent()) {
            leaderboard = Optional.of(new LeaderBoardTable(this));
        }
    }
}
