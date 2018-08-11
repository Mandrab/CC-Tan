package it.unibo.oop.cctan.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import java.util.Optional;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.oop.cctan.interPackageComunication.CommandsObserver;
import it.unibo.oop.cctan.interPackageComunication.MappableData;
/**
 * Class that instance the component used to show the game to the user.
 */
class GameWindow extends JFrame implements SizeObserver {

    private static final long serialVersionUID = -4110471158542881589L;
    private final View view;
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
        this.view = view;
        view.addSizeObserver(this);

        gpanel = new GraphicPanel(this, view.getFont());
        getContentPane().add(gpanel, BorderLayout.CENTER);

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

    /**
     * A method that return a list of data that as to be mapped.
     * 
     * @return The list of the MappableData
     */
    public List<MappableData> getListOfMappableData() {
        return view.getListOfMappableData();
    }

    public int getScore() {
        return view.getScore();
    }

    public void addCommandsObserver(CommandsObserver commandsObserver) {
        view.addCommandsObserver(commandsObserver);
    }
    
    @Override
    public void setVisible(boolean b) {
        if(!gameWindowSize.isPresent() || !screenRatio.isPresent())
            throw new IllegalArgumentException();
        else 
            super.setVisible(b);
    }

}
