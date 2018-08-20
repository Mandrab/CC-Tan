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

import it.unibo.oop.cctan.interpackage_comunication.LoadObserver;
import it.unibo.oop.cctan.interpackage_comunication.data.LoadedFilesSingleton;
import it.unibo.oop.cctan.interpackage_comunication.data.LoadedFiles.ImageType;

/**
 * A class that takes care to show the loading percentage of the application. Package protected.
 */
class Loader extends JWindow implements LoadObserver {

    private static final long serialVersionUID = -5568669894413165308L;
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final Dimension WINDOW_SIZE = new Dimension((int) (SCREEN_SIZE.getWidth() / 5),
                                                               (int) (SCREEN_SIZE.getHeight() / 5));
    private static final int WINDOW_PERMANENCY_TIME = 1200;
    private final View view;
    private final JLabel containerLabel;
    private final JProgressBar progressBar;
    private final JLabel pBarPercentage;

    Loader(final View view) {
        super();
        this.view = view;
        LoadedFilesSingleton.getLoadedFiles().addObserver(this);
        setMinimumSize(WINDOW_SIZE);
        setMaximumSize(WINDOW_SIZE);
        centerWindow();

        containerLabel = new JLabel("Loading...", SwingConstants.CENTER);
        containerLabel.setBackground(Color.BLACK);
        containerLabel.setForeground(Color.WHITE);
        containerLabel.setOpaque(true);
        containerLabel.setLayout(new GridBagLayout());
        final GridBagConstraints gbc = new GridBagConstraints();
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

        add(containerLabel);
        pack();
        setVisible(true);
        this.update();
    }

    /**
     * Sets the percentage of advance. If the number is less than 0 or exceeds 100,
     * the change is ignored.
     * 
     * @param value
     *            the percentage (es. 1 -> 1%, 40 -> 40%)
     */
    private void advanceLoading(final Integer value) {
        if (0 <= value && value <= 100) {
            progressBar.setValue(value);
            pBarPercentage.setText(value.toString() + "%");
            if (LoadedFilesSingleton.getLoadedFiles().isLoaded()) {
                pBarPercentage.setText(100 + "%");
                try {
                    Thread.sleep(WINDOW_PERMANENCY_TIME);
                    setVisible(false);
                    view.showSettingsWindow();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Sets the background image.
     * 
     * @param img
     *            is the imageIcon that will be load in the background
     */
    private void setLoadImage(final ImageIcon img) {
        containerLabel.setText("");
        containerLabel.setIcon(new ImageIcon(img.getImage()
                                                .getScaledInstance(WINDOW_SIZE.width, 
                                                                   WINDOW_SIZE.height, 
                                                                   Image.SCALE_SMOOTH)));
    }

    /**
     * Center the window on the screen.
     * 
     * @param window
     *            the window to be centered
     */
    private void centerWindow() {
        setLocation((int) ((SCREEN_SIZE.getWidth() - WINDOW_SIZE.getWidth()) / 2),
                    (int) ((SCREEN_SIZE.getHeight() - WINDOW_SIZE.getHeight()) / 2));
    }

    @Override
    /** {@inheritDoc} */
    public final void update() {
        LoadedFilesSingleton.getLoadedFiles().getImage(ImageType.LOGO).ifPresent(img -> setLoadImage(img));
        advanceLoading(LoadedFilesSingleton.getLoadedFiles().getPercentage());
    }
}
