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
import javax.swing.JTextField;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import it.unibo.oop.cctan.interPackageComunication.Commands;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserver;

public class SettingsWindow {
    private static String playerNick = "not set";
    private static JFrame settings;
    private static final String FILE_NAME = "./res//background2.jpg";
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final String SONG_NAME = "./res//musicGame.wav";
    private static Optional<Clip> clip = Optional.empty();
    private final View view;

    public SettingsWindow(final View v) {
        this.view = v;
        settings = new JFrame("Settings");
        settings.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        settings.setLayout(new BorderLayout());

        // TODO settare l'indirizzo giusto
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

        JLabel ratioLabl = new JLabel();
        ratioLabl.setText("Select screen ratio:");
        c.gridx = 0;
        c.gridy = 4;
        ratioLabl.setHorizontalAlignment(JLabel.CENTER);
        background.add(ratioLabl, c);
        JComboBox<String> ratio = new JComboBox<String>();
        ratio.addItem("-- SELECT --");
        ratio.addItem("1:1");
        ratio.addItem("4:3");
        ratio.addItem("16:9");
        c.gridx = 0;
        c.gridy = 5;
        background.add(ratio, c);

        JLabel dimensionLabl = new JLabel();
        dimensionLabl.setText("Select window size:");
        c.gridx = 0;
        c.gridy = 6;
        dimensionLabl.setHorizontalAlignment(JLabel.CENTER);
        background.add(dimensionLabl, c);

        JComboBox<String> dimension = new JComboBox<String>();
        dimension.addItem("-- SELECT --");
        c.gridx = 0;
        c.gridy = 7;
        background.add(dimension, c);

        dimension.setEnabled(false);

        ratio.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (!ratio.getSelectedItem().equals("-- SELECT --")) {
                    dimension.setEnabled(true);
                    dimension.removeAllItems();
                    dimension.addItem("-- SELECT --");
                    double height = SCREEN_SIZE.getHeight();
                    double width = SCREEN_SIZE.getWidth();
                    if (ratio.getSelectedItem().equals("1:1")) {
                        String stringRatio = String.valueOf((int) height) + ":" + String.valueOf((int) height);
                        dimension.addItem(stringRatio);
                        stringRatio = String.valueOf((int) (height * 0.75)) + ":"
                                + String.valueOf((int) (height * 0.75));
                        dimension.addItem(stringRatio);
                        stringRatio = String.valueOf((int) (height * 0.5)) + ":" + String.valueOf((int) (height * 0.5));
                        dimension.addItem(stringRatio);

                    } else if (ratio.getSelectedItem().equals("4:3")) {
                        String stringRatio = String.valueOf((int) (height * 1.33)) + ":" + String.valueOf((int) height);
                        dimension.addItem(stringRatio);
                        stringRatio = String.valueOf((int) (height * 0.75 * 1.33)) + ":"
                                + String.valueOf((int) (height * 0.75));
                        dimension.addItem(stringRatio);
                        stringRatio = String.valueOf((int) (height * 0.5 * 1.33)) + ":"
                                + String.valueOf((int) (height * 0.5));
                        dimension.addItem(stringRatio);
                    } else {
                        String stringRatio = String.valueOf((int) width) + ":" + String.valueOf((int) (width * 0.5625));
                        dimension.addItem(stringRatio);
                        stringRatio = String.valueOf((int) (width * 0.75)) + ":"
                                + String.valueOf((int) (width * 0.75 * 0.5625));
                        dimension.addItem(stringRatio);
                        stringRatio = String.valueOf((int) (width * 0.5)) + ":"
                                + String.valueOf((int) (width * 0.5 * 0.5625));
                        dimension.addItem(stringRatio);
                    }
                } else {
                    dimension.setEnabled(false);
                    dimension.removeAllItems();
                    dimension.addItem("-- SELECT --");
                }

            }
        });

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
        settings.setLocationRelativeTo(null);
        settings.setResizable(false);
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
                    
                    //usare stringToDim((String)dimension.getSelectedItem()); per ottenere la Dimension da passare all'observer
                    //usare stringToPair((String)ratio.getSelectedItem()) per ottenere il Pair da passare all'observer
                    Dimension dim = stringToDim((String)dimension.getSelectedItem());
                    Pair<Integer,Integer> rat =stringToPair((String)ratio.getSelectedItem());
                    List<SizeObserver> observers = view.getSizeObserversManager().getSizeObservers();
                    //view.getSizeObserverSource().get().
                    System.out.println(rat+"      "+dim);
                    System.out.println(ratio.getSelectedItem()+"           "+dimension.getSelectedItem());
                    
                    for (Iterator<SizeObserver> i = observers.iterator(); i.hasNext();) {
                        SizeObserver sO = i.next();
                        sO.update(dim, rat);

                    }
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "Please, fill the Fields", "Notice",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void showMenu() {
        new MenuWindow(view,this);
    }

    public void show() {
        settings.setVisible(true);
    }

    public String getPlayerName() {
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
                String path = new File(SONG_NAME).getAbsolutePath();
                System.out.println(path);
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile());
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
    
    public Pair<Integer, Integer> stringToPair(String s) {
        Pair<Integer, Integer> p;
        if (s.length() == 3) {
            int el0 = Integer.parseInt(s.substring(0, 1));
            int el1 = Integer.parseInt(s.substring(2, 3));
            System.out.println(el0 + "  " + el1);
            p = new ImmutablePair<>(el0, el1);
        } else {
            int el0 = Integer.parseInt(s.substring(0, 2));
            int el1 = Integer.parseInt(s.substring(3, 4));
            System.out.println(el0 + "  " + el1);
            p = new ImmutablePair<>(el0, el1);
        }
        return p;
    }

    public Dimension stringToDim(String s) {
        if (s.length() == 7) {
            int width = Integer.parseInt(s.substring(0, 3));
            int height = Integer.parseInt(s.substring(4, 7));
            System.out.println(width + "  " + height);
            Dimension dim = new Dimension(width, height);

            return dim;
        } else if(s.length() == 8){
            int width = Integer.parseInt(s.substring(0, 4));
            int height = Integer.parseInt(s.substring(5, 8));
            System.out.println(width + "  " + height);
            Dimension dim = new Dimension(width, height);
            
            return dim;
        }
        return null;
    }

}
