package it.unibo.oop.cctan.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.oop.cctan.interPackageComunication.CommandsObserver;
import it.unibo.oop.cctan.interPackageComunication.MappableData;;

/**
 * Class that instance the component used to show the game to the user.
 */
class GameWindow extends JFrame implements SizeObserver {

    private static final long serialVersionUID = -4110471158542881589L;
    private Dimension gameWindowSize;
    private Pair<Integer, Integer> screenRatio;
    private View view;
    private GraphicPanel gpanel;

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
    GameWindow(final View view, final Dimension gameWindowSize, final Pair<Integer, Integer> screenRatio) {
        setTitle("CC-Tan!");
        this.view = view;
        this.gameWindowSize = gameWindowSize;
        this.screenRatio = screenRatio;
        view.addSizeObserver(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        gpanel = new GraphicPanel(this);
        getContentPane().add(gpanel, BorderLayout.CENTER);
        
        setUndecorated(true);
        pack();
        setResizable(false);
    }

    /**
     * Get the dimension of the game window (e.g.: 320x240, 640x480, 1024x768,...).
     * 
     * @return Dimension class that contains the dimension of the window
     */
    Dimension getDimension() {
        return gameWindowSize;
    }

    /**
     * Get the screen ratio of the game window (e.g.: 1:1, 4:3, 16:9,...).
     * 
     * @return Pair class that contains the screen ratio of the window
     */
    Pair<Integer, Integer> getScreenRatio() {
        return screenRatio;
    }

    @Override
    public void update(final Dimension gameWindowSize, final Pair<Integer, Integer> screenRatio) {
        this.gameWindowSize = gameWindowSize;
        this.screenRatio = screenRatio;
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

}
