package it.unibo.oop.cctan.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.lang3.tuple.ImmutableTriple;

import it.unibo.oop.cctan.interpackage_comunication.LoadedFilesSingleton;
import it.unibo.oop.cctan.interpackage_comunication.LoadedFiles.ImageType;

/**
 * Class that instance the component used to show the end menu to the user.
 */
public class EndWindow {
    private static final int INSETS = 5;
    private static final int FONT_STYLE = 18;
    private static final int FONT_SIZE = 60;
    private static final String BACKGROUND_JPG = "/background2.jpg";
    private final int score;
    private final View view;
    private final JFrame mainFrame;

    /**
     * the constructor of the PauseWindow class.
     * 
     * @param view
     *            A reference to the view (parents).
     */
    public EndWindow(final View view) {
        this.view = view;
        this.score = this.view.getModelData().getScore();
        this.mainFrame = new JFrame("oop17-cctan Conclusion Menù");
        setUpWindow();
        mainFrame.pack();
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private void setUpWindow() {
        LoadedFilesSingleton.getLoadedFiles().getImage(ImageType.ICON)
                .ifPresent(img -> mainFrame.setIconImage(img.getImage()));
        // TODO impostare sicuro di volr uscire dal gioco?
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainFrame.setLayout(new BorderLayout());

        final Dimension windowDimension = tryDimensionOfWindow();

        final ImageIcon imgIco = new ImageIcon(SettingsWindow.class.getResource(BACKGROUND_JPG));
        final JLabel background = new JLabel(new ImageIcon(imgIco.getImage()
                .getScaledInstance((int) windowDimension.getWidth(), (int) windowDimension.getHeight(), 0)));
        mainFrame.add(background);
        mainFrame.setResizable(false);
        background.setLayout(new GridBagLayout());

        final GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        int pos = 0;
        final Font font = new Font("Serif", FONT_STYLE, FONT_SIZE);
        final JLabel title = new JLabel();
        title.setFont(font);
        title.setText("GAME-OVER");
        c.gridx = 0;
        c.gridy = pos++;
        c.gridwidth = 2;
        title.setHorizontalAlignment(JLabel.CENTER);
        background.add(title, c);
        c.gridx = 0;
        c.gridy = pos++;
        title.setHorizontalAlignment(JLabel.CENTER);
        background.add(new JLabel("           "), c);

        final JLabel nickLabl = new JLabel();
        nickLabl.setText("Your score : " + this.score);
        c.gridx = 0;
        c.gridy = pos++;
        nickLabl.setHorizontalAlignment(JLabel.CENTER);
        background.add(nickLabl, c);

        c.insets = new Insets(10, INSETS, 0, INSETS);
        c.gridwidth = 1;

        final JButton settingsBtn = new JButton("Settings");
        c.gridx = 0;
        c.gridy = pos++;
        background.add(settingsBtn, c);

        final JButton exitBtn = new JButton("Exit");
        c.gridx = 0;
        c.gridy = pos++;
        background.add(exitBtn, c);

        final JButton restartBtn = new JButton("Restart");
        c.gridx = 0;
        c.gridy = pos++;
        background.add(restartBtn, c);

        restartBtn.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                if (view.getPlayerName().isPresent()) {
                    final String nick = view.getPlayerName().get();
                    // SALVARE IL PUNTEGGIO SCORE ATTUALE
                    final Records rec = Records.getInstance();
                    rec.addWithNoDuplicate(new ImmutableTriple<String, Integer, Date>(nick, score, new Date()));

                    // MANDARE IL VOMANDO reset end e start
                    view.getKeyCommandsListener().setReset(true);
                    view.getKeyCommandsListener().endCommand();

                    view.getKeyCommandsListener().setLockResumeKey(false);
                    view.getKeyCommandsListener().startCommand();

                    mainFrame.dispose();
                }
            }
        });

        settingsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                if (view.getPlayerName().isPresent()) {
                    final String nick = view.getPlayerName().get();
                    // SALVARE IL PUNTEGGIO SCORE ATTUALE
                    final Records rec = Records.getInstance();
                    rec.addWithNoDuplicate(new ImmutableTriple<String, Integer, Date>(nick, score, new Date()));

                    // non mostrare la endwindow
                    final KeyCommandsListener c = view.getKeyCommandsListener();
                    c.setReset(true);
                    c.endCommand();

                    // view.setting.Show(view) o qualcosa del genere
                    view.hideGameWindow();
                    view.showSettingsWindow();
                    mainFrame.dispose();
                }
            }
        });
        exitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                final String nick = view.getPlayerName().get();
                // SALVARE IL PUNTEGGIO SCORE ATTUALE
                final Records rec = Records.getInstance();
                rec.addWithNoDuplicate(new ImmutableTriple<String, Integer, Date>(nick, score, new Date()));
                Runtime.getRuntime().exit(0);
                // System.exit(0);
            }
        });

    }

    private Dimension tryDimensionOfWindow() {
        final JFrame tmpSet = new JFrame("tryDimension");
        tmpSet.setLayout(new BorderLayout());
        final JPanel tmpBackground = new JPanel();
        tmpSet.add(tmpBackground);
        tmpBackground.setLayout(new GridBagLayout());
        final GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        int pos = 0;
        final Font font = new Font("Serif", FONT_STYLE, FONT_SIZE);
        final JLabel title = new JLabel();
        title.setFont(font);
        title.setText("GAME-OVER");
        c.gridx = 0;
        c.gridy = pos++;
        c.gridwidth = 2;
        title.setHorizontalAlignment(JLabel.CENTER);
        tmpBackground.add(title, c);
        c.gridx = 0;
        c.gridy = pos++;
        title.setHorizontalAlignment(JLabel.CENTER);
        tmpBackground.add(new JLabel("           "), c);

        final JLabel nickLabl = new JLabel();
        nickLabl.setText("Your score : " + this.score);
        c.gridx = 0;
        c.gridy = pos++;
        nickLabl.setHorizontalAlignment(JLabel.CENTER);
        tmpBackground.add(nickLabl, c);

        c.insets = new Insets(10, INSETS, 0, INSETS);
        c.gridwidth = 1;

        final JButton settingsBtn = new JButton("Settings");
        c.gridx = 0;
        c.gridy = pos++;
        tmpBackground.add(settingsBtn, c);

        final JButton exitBtn = new JButton("Exit");
        c.gridx = 0;
        c.gridy = pos++;
        tmpBackground.add(exitBtn, c);

        final JButton restartBtn = new JButton("Restart");
        c.gridx = 0;
        c.gridy = pos++;
        tmpBackground.add(restartBtn, c);

        tmpSet.pack();
        return tmpSet.getSize();
    }
}
