package it.unibo.oop.cctan.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.util.Optional;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.oop.cctan.interPackageComunication.LoadedFiles.ImageType;
import it.unibo.oop.cctan.interPackageComunication.ModelData;
import it.unibo.oop.cctan.interPackageComunication.SizeObserver;

/**
 * Class that instance the component used to show the game to the user.
 */
class GameWindow extends JFrame implements SizeObserver {

    private static final long serialVersionUID = 3126913839407712312L;
    private GraphicPanel gpanel;
    private Optional<Dimension> gameWindowSize;
    private Optional<Pair<Integer, Integer>> screenRatio;

    /**
     * The constructor of GameWindow class.
     * 
     * @param view
     *            A reference to the view (parents)
     * @param gameWindowSize
     *            The dimension of the window of the game (e.g.: 320x240, 640x480,
     *            1024x768,...);
     * @param screenRatio
     *            The ratio of the window of the game (e.g.: 1:1, 4:3, 16:9,...)
     */
    GameWindow(final View view) {
        setTitle("CC-Tan!");
        view.getLoadedFiles().getImage(ImageType.ICON).ifPresent(img -> setIconImage(img.getImage()));
        view.getSizeObserverSource().ifPresent(s -> s.addSizeObserver(this));

        gpanel = new GraphicPanel(view.getLoadedFiles().getFontFile().orElseGet(() -> new File("")));
        getContentPane().add(gpanel, BorderLayout.CENTER);
        //gpanel.addKeyListener(view.getKeyCommandsListener().getKeyListener());  //SPOSTO?
        //gpanel.requestFocus();                                                  //SPOSTO?

        gameWindowSize = Optional.empty();
        screenRatio = Optional.empty();

        pack();
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * Get the dimension of the game window (e.g.: 320x240, 640x480, 1024x768,...).
     * 
     * @return Dimension class that contains the dimension of the window
     */
    Optional<Dimension> getDimension() {
        return gameWindowSize.isPresent() ? gameWindowSize : Optional.empty();
    }

    /**
     * Get the screen ratio of the game window (e.g.: 1:1, 4:3, 16:9,...).
     * 
     * @return Pair class that contains the screen ratio of the window
     */
    Optional<Pair<Integer, Integer>> getScreenRatio() {
        return screenRatio.isPresent() ? screenRatio : Optional.empty();
    }

    public void update(final Dimension gameWindowSize, final Pair<Integer, Integer> screenRatio) {
        this.gameWindowSize = Optional.of(gameWindowSize);
        this.screenRatio = Optional.of(screenRatio);
        gpanel.update(gameWindowSize, screenRatio);
        pack();
    }

    @Override
    public void setVisible(final boolean cond) {
        if (!gameWindowSize.isPresent() || !screenRatio.isPresent()) {
            throw new IllegalArgumentException();
        }
        super.setVisible(cond);
    }

    public void refresh(final ModelData modelData) {
        gpanel.refresh(modelData);
    }

}
