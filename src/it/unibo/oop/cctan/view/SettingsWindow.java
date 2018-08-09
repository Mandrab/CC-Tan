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

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class SettingsWindow {
    private static String playerNick = "not set";
    private static JFrame settings;
    private static final String FILE_NAME = "./res//background2.jpg";
    private static Optional<Clip> clip = Optional.empty();
    private final View view;

    public SettingsWindow(View v) {
        this.view = v;
        settings = new JFrame("Settings");
        settings.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        settings.setLayout(new BorderLayout());
        String path = new File(FILE_NAME).getAbsolutePath();

        JLabel background = new JLabel(new ImageIcon(path));
        settings.add(background);
        background.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        Font font = new Font("Serif", 18, 60);
        JLabel title = new JLabel();
        title.setFont(font);
        title.setText("Settings");
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
        nickLabl.setText("Select NickName:");
        JTextField nickText = new JTextField(10);

        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 2;
        nickLabl.setHorizontalAlignment(JLabel.CENTER);
        background.add(nickLabl, c);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 3;
        background.add(nickText, c);

        JLabel dimensionLabl = new JLabel();
        dimensionLabl.setText("Select window size:");
        c.gridx = 0;
        c.gridy = 4;
        dimensionLabl.setHorizontalAlignment(JLabel.CENTER);
        background.add(dimensionLabl, c);

        JComboBox<String> dimension = new JComboBox<String>();
        dimension.addItem("-- SELECT --");
        dimension.addItem("320x240");
        dimension.addItem("640x480");
        dimension.addItem("1024x768");
        c.gridx = 0;
        c.gridy = 5;
        background.add(dimension, c);

        JLabel ratioLabl = new JLabel();
        ratioLabl.setText("Select screen ratio:");
        c.gridx = 0;
        c.gridy = 6;
        ratioLabl.setHorizontalAlignment(JLabel.CENTER);
        background.add(ratioLabl, c);
        JComboBox<String> ratio = new JComboBox<String>();
        ratio.addItem("-- SELECT --");
        ratio.addItem("1:1");
        ratio.addItem("4:3");
        ratio.addItem("16:9");
        c.gridx = 0;
        c.gridy = 7;
        background.add(ratio, c);

        JButton doneBtn = new JButton("done");
        c.gridx = 1;
        c.gridy = 8;
        c.weighty = 1.0;
        c.gridwidth = 2;
        c.insets = new Insets(10, 0, 0, 0);
        c.anchor = GridBagConstraints.PAGE_END;
        background.add(doneBtn, c);

        settings.setSize(font.getSize() * 4, font.getSize() * 5);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        settings.setLocation(dim.width / 2 - settings.getSize().width / 2,
                dim.height / 2 - settings.getSize().height / 2);
        settings.setVisible(true);

        doneBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!nickText.getText().equals("") && !dimension.getSelectedItem().equals("-- SELECT --")
                        && !ratio.getSelectedItem().equals("-- SELECT --")) {
                    playerNick = nickText.getText();
                    settings.setVisible(false);
                    // TODO
                    music();
                    showMenu();
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "Please, fill the Fields", "Notice",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void showMenu() {
        new MenuWindow(this);
    }

    public void show() {
        settings.setVisible(true);
    }

    public static String getPlayerName() {
        return playerNick;
    }

    /**
     * get method for the clip of the menu
     * 
     * @return the clip of the menu if it's present, if not it return null
     */
    public Clip getClipMenu() {
        if (clip.isPresent()) {
            return clip.get();
        }
        return null;
    }

    // TODO rendere la clip stoppabile e avviabile i
    public static void music() {
        try {
            if (!clip.isPresent()) {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("C:\\Users\\TUCCIO\\eclipse-workspace\\StartSettings\\res\\musicGame.wav")
                                .getAbsoluteFile());
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

}
