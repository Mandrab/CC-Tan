package it.unibo.oop.cctan.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.SwingConstants;

/**
 * A class that takes care to show the loading percentage of the application. 
 */
class Loader {

    private JWindow window;
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static final Dimension windowSize = new Dimension((int) (screenSize.getWidth() / 5),
                                                              (int) (screenSize.getHeight() / 5));
    private JLabel containerLabel;
    private JProgressBar progressBar;
    private JLabel pBarPercentage;

    public Loader() {
        window = new JWindow();
        window.setMinimumSize(windowSize);
        window.setMaximumSize(windowSize);
        centerWindow(window);

        containerLabel = new JLabel("Loading...", SwingConstants.CENTER);
        containerLabel.setBackground(Color.BLACK);
        containerLabel.setForeground(Color.WHITE);
        containerLabel.setOpaque(true);
        containerLabel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 10, 10, 0); // Borders
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Adds of progress bar
        progressBar = new JProgressBar(0, 100);
        gbc.weightx = 1.0; // Importance during the resize (100%)
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        containerLabel.add(progressBar, gbc);

        // Adds of the label of the progress bar
        pBarPercentage = new JLabel("0%");
        pBarPercentage.setForeground(Color.WHITE);
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        containerLabel.add(pBarPercentage, gbc);

        // Puts a filler component that will push the rest to the bottom
        gbc.weighty = 1.0;
        containerLabel.add(Box.createGlue(), gbc);

        window.add(containerLabel);
        window.pack();
        window.setVisible(true);
    }

    /**
     * Sets the percentage of advance. If the number is less than 0 or exceeds 100,
     * the change is ignored.
     * 
     * @param value
     *            the percentage (es. 1 -> 1%, 40 -> 40%)
     */
    public void advanceLoading(Integer value) {
        if (0 <= value && value <= 100) {
            progressBar.setValue(value);
            pBarPercentage.setText(value.toString() + "%");
        }
    }

    /**
     * Sets the background image
     * 
     * @param img
     *            is the imageIcon that will be load in the background
     */
    public void setLoadImage(ImageIcon img) {
        containerLabel.setText("");
        containerLabel.setIcon(new ImageIcon(img.getImage()
                                                .getScaledInstance((int)windowSize.getWidth(), 
                                                                   (int)windowSize.getHeight(), 
                                                                   Image.SCALE_SMOOTH)));
    }

    /**
     * Center the window on the screen
     * 
     * @param window
     *            the window to be centered
     */
    private void centerWindow(JWindow window) {
        window.setLocation((int) ((screenSize.getWidth() - windowSize.getWidth()) / 2),
                           (int) ((screenSize.getHeight() - windowSize.getHeight()) / 2));
    }
}
