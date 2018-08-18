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
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import it.unibo.oop.cctan.interpackage_comunication.LoadedFilesSingleton;
import it.unibo.oop.cctan.interpackage_comunication.SizeObserver;
import it.unibo.oop.cctan.interpackage_comunication.SizeObserverSourceImpl;
import it.unibo.oop.cctan.interpackage_comunication.LoadedFiles.ImageType;

/**
 * Class that show settings to the user using a GUI.
 * 
 * @author Sutera Lorenzo
 *
 */
public class SettingsWindow extends SizeObserverSourceImpl {

    private static final Double THREE_QUARTER = 0.75;
    private static final Double FOUR_THREE_SCALE = 1.33;
    private static final Double SIXTEEN_NINE_SCALE = 0.5625;
    private static final int FONT_STYLE = 18;
    private static final int FONT_SIZE = 60;
    private static final String SELECT_CHOISE = "-- SELECT --";
    private String playerNick = "not set";
    private final JFrame settings;
    private static final String BACKGROUND_JPG = "/background2.jpg";
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final String SONG_NAME_WAV = "/musicGame.wav";
    private static Optional<Clip> clip = Optional.empty();
    private Optional<Dimension> gameWindowSize;
    private Optional<Pair<Integer, Integer>> gameWindowRatio;
    private final View view;

    /**
     * the constructor of the SettingsWindow class.
     * 
     * @param view
     *            A reference to the view (parents).
     */
    public SettingsWindow(final View view) {
        super();
        this.view = view;
        gameWindowSize = Optional.empty();
        gameWindowRatio = Optional.empty();
        settings = new JFrame("Settings");
        setUpWindow();
        settings.pack();
        settings.setLocationRelativeTo(null);
        settings.setResizable(false);
        settings.setVisible(true);
        settings.toFront();
    }

    private void setUpWindow() {
        final Dimension settingsDimansion = tryDimensionOfWindow();

        LoadedFilesSingleton.getLoadedFiles().getImage(ImageType.ICON)
                .ifPresent(img -> settings.setIconImage(img.getImage()));
        settings.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        settings.setLayout(new BorderLayout());

        final ImageIcon imgIco = new ImageIcon(SettingsWindow.class.getResource(BACKGROUND_JPG));

        final JLabel background = new JLabel(new ImageIcon(imgIco.getImage()
                .getScaledInstance((int) settingsDimansion.getWidth(), (int) settingsDimansion.getHeight(), 0)));
        settings.add(background);

        background.setLayout(new GridBagLayout());

        final GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        int pos = 0;
        final Font font = new Font("Serif", FONT_STYLE, FONT_SIZE);
        final JLabel title = new JLabel();
        title.setFont(font);
        title.setText("Settings");
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
        nickLabl.setText("Select NickName:");
        final JTextField nickText = new JTextField(10);

        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = pos++;
        nickLabl.setHorizontalAlignment(JLabel.CENTER);
        background.add(nickLabl, c);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = pos++;
        background.add(nickText, c);

        final JLabel ratioLabl = new JLabel();
        ratioLabl.setText("Select screen ratio:");
        c.gridx = 0;
        c.gridy = pos++;
        ratioLabl.setHorizontalAlignment(JLabel.CENTER);
        background.add(ratioLabl, c);
        final JComboBox<String> ratio = new JComboBox<String>();
        ratio.addItem(SELECT_CHOISE);
        ratio.addItem("1:1");
        ratio.addItem("4:3");
        ratio.addItem("16:9");
        c.gridx = 0;
        c.gridy = pos++;
        background.add(ratio, c);

        final JLabel dimensionLabl = new JLabel();
        dimensionLabl.setText("Select window size:");
        c.gridx = 0;
        c.gridy = pos++;
        dimensionLabl.setHorizontalAlignment(JLabel.CENTER);
        background.add(dimensionLabl, c);

        final JComboBox<String> dimension = new JComboBox<String>();
        dimension.addItem(SELECT_CHOISE);
        c.gridx = 0;
        c.gridy = pos++;
        background.add(dimension, c);

        dimension.setEnabled(false);

        ratio.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent arg0) {
                if (!ratio.getSelectedItem().equals(SELECT_CHOISE)) {
                    dimension.setEnabled(true);
                    dimension.removeAllItems();
                    dimension.addItem(SELECT_CHOISE);
                    final double height = SCREEN_SIZE.getHeight();
                    final double width = SCREEN_SIZE.getWidth();
                    if (ratio.getSelectedItem().equals("1:1")) {
                        String stringRatio = (int) height + ":" + (int) height;
                        dimension.addItem(stringRatio);
                        stringRatio = (int) (height * THREE_QUARTER) + ":" + (int) (height * THREE_QUARTER);
                        dimension.addItem(stringRatio);
                        stringRatio = (int) (height * 0.5) + ":" + (int) (height * 0.5);
                        dimension.addItem(stringRatio);

                    } else if (ratio.getSelectedItem().equals("4:3")) {
                        String stringRatio = (int) (height * FOUR_THREE_SCALE) + ":" + (int) height;
                        dimension.addItem(stringRatio);
                        stringRatio = (int) (height * THREE_QUARTER * FOUR_THREE_SCALE) + ":"
                                + (int) (height * THREE_QUARTER);
                        dimension.addItem(stringRatio);
                        stringRatio = (int) (height * 0.5 * FOUR_THREE_SCALE) + ":" + (int) (height * 0.5);
                        dimension.addItem(stringRatio);
                    } else {
                        String stringRatio = (int) width + ":" + (int) (width * SIXTEEN_NINE_SCALE);
                        dimension.addItem(stringRatio);
                        stringRatio = (int) (width * THREE_QUARTER) + ":"
                                + (int) (width * THREE_QUARTER * SIXTEEN_NINE_SCALE);
                        dimension.addItem(stringRatio);
                        stringRatio = (int) (width * 0.5) + ":" + (int) (width * 0.5 * SIXTEEN_NINE_SCALE);
                        dimension.addItem(stringRatio);
                    }
                } else {
                    dimension.setEnabled(false);
                    dimension.removeAllItems();
                    dimension.addItem(SELECT_CHOISE);
                }

            }
        });

        final JButton doneBtn = new JButton("done");
        c.gridx = 1;
        c.gridy = 8;
        c.weighty = 1.0;
        c.gridwidth = 2;
        c.insets = new Insets(10, 0, 0, 0);
        c.anchor = GridBagConstraints.PAGE_END;
        background.add(doneBtn, c);

        doneBtn.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                if (!nickText.getText().equals("") && !dimension.getSelectedItem().equals(SELECT_CHOISE)
                        && !ratio.getSelectedItem().equals(SELECT_CHOISE)) {
                    playerNick = nickText.getText();
                    settings.setVisible(false);
                    // TODO
                    music();
                    showMenu();

                    // usare stringToDim((String)dimension.getSelectedItem()); per ottenere la
                    // Dimension da passare all'observer
                    // usare stringToPair((String)ratio.getSelectedItem()) per ottenere il Pair da
                    // passare all'observer
                    Dimension dim;
                    Pair<Integer, Integer> rat;
                    synchronized (this) {
                        dim = stringToDim((String) dimension.getSelectedItem());
                        rat = stringToPair((String) ratio.getSelectedItem());
                        gameWindowSize = Optional.of(dim);
                        gameWindowRatio = Optional.of(rat);
                    }
                    final List<SizeObserver> observers = getSizeObservers();
                    // view.getSizeObserverSource().get();

                    for (final Iterator<SizeObserver> i = observers.iterator(); i.hasNext();) {
                        final SizeObserver sO = i.next();
                        sO.update(dim, rat);
                    }
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "Please, fill the Fields", "Notice",
                            JOptionPane.ERROR_MESSAGE);
                }
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
        title.setText("Settings");
        c.gridx = 0;
        c.gridy = pos++;
        c.gridwidth = 2;
        title.setHorizontalAlignment(JLabel.CENTER);
        tmpBackground.add(title, c);
        title.setHorizontalAlignment(JLabel.CENTER);

        final JLabel nickLabl = new JLabel();
        nickLabl.setText("Select NickName:");
        final JTextField nickText = new JTextField(10);

        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = pos++;
        nickLabl.setHorizontalAlignment(JLabel.CENTER);
        tmpBackground.add(nickLabl, c);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = pos++;
        tmpBackground.add(nickText, c);

        final JLabel ratioLabl = new JLabel();
        ratioLabl.setText("Select screen ratio:");
        c.gridx = 0;
        c.gridy = pos++;
        ratioLabl.setHorizontalAlignment(JLabel.CENTER);
        tmpBackground.add(ratioLabl, c);
        final JComboBox<String> ratio = new JComboBox<String>();
        ratio.addItem(SELECT_CHOISE);
        c.gridx = 0;
        c.gridy = pos++;
        tmpBackground.add(ratio, c);

        final JLabel dimensionLabl = new JLabel();
        dimensionLabl.setText("Select window size:");
        c.gridx = 0;
        c.gridy = pos++;
        dimensionLabl.setHorizontalAlignment(JLabel.CENTER);
        tmpBackground.add(dimensionLabl, c);

        final JComboBox<String> dimension = new JComboBox<String>();
        dimension.addItem(SELECT_CHOISE);
        c.gridx = 0;
        c.gridy = pos++;
        tmpBackground.add(dimension, c);

        final JButton doneBtn = new JButton("done");
        c.gridx = 1;
        c.gridy = pos++;
        c.weighty = 1.0;
        c.gridwidth = 2;
        c.insets = new Insets(10, 0, 0, 0);
        c.anchor = GridBagConstraints.PAGE_END;
        tmpBackground.add(doneBtn, c);

        tmpSet.pack();
        return tmpSet.getSize();
    }

    /**
     * a method which call the menu window.
     */
    public void showMenu() {
        new MenuWindow(view, this);
    }

    /**
     * a method that allow the window to be visible.
     */
    public void show() {
        settings.setVisible(true);
    }

    /**
     * a method that return the player name that the usere must insert before
     * playng.
     * 
     * @return the nick name of the player.
     */
    public String getPlayerName() {
        return playerNick;
    }

    @Override
    public final synchronized Optional<Dimension> getDimension() {
        return gameWindowSize;
    }

    @Override
    public final synchronized Optional<Pair<Integer, Integer>> getRatio() {
        return gameWindowRatio;
    }

    /**
     * get method for the clip of the menu.
     * 
     * @return the clip of the menu if it's present, if not it return null.
     */
    public Clip getClipMenu() {
        if (clip.isPresent()) {
            return clip.get();
        }
        return null;
    }

    private static void music() {
        try {
            if (!clip.isPresent()) {
                final AudioInputStream audioInputStream = AudioSystem
                        .getAudioInputStream(SettingsWindow.class.getResource(SONG_NAME_WAV));
                clip = Optional.of(AudioSystem.getClip());
                clip.get().open(audioInputStream);
                clip.get().loop(Clip.LOOP_CONTINUOUSLY);
                clip.get().start();
            }
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }

    }

    private Pair<Integer, Integer> stringToPair(final String string) {
        final String[] strings = string.split(":");
        final int x = Integer.parseInt(strings[0]);
        final int y = Integer.parseInt(strings[1]);
        return new ImmutablePair<>(x, y);
    }

    private Dimension stringToDim(final String string) {
        final String[] strings = string.split(":");
        final int width = Integer.parseInt(strings[0]);
        final int height = Integer.parseInt(strings[1]);
        return new Dimension(width, height);
    }

}
