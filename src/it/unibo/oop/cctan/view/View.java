package it.unibo.oop.cctan.view;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.oop.cctan.interPackageComunication.ModelData;

/**
 * State what a View implementation must implements.
 */
public interface View extends SizeAndControlChainOfResponsibility {

    /**
     * Enumeration used to select which component update.
     */
    enum Component {
        /**
         * Loader graphic component.
         */
        LOADER,
        /**
         * GameWindow graphic component.
         */
        GAME_WINDOW;
    }

    /**
     * Setup and show the game window.
     * 
     * @param resolution
     *            represent the pixel of width and height
     * @param screenRatio
     *            represent the ratio between x and y edge (eg:: 16:9, 4:3)
     */
    void showGameWindow(Dimension resolution, Pair<Integer, Integer> screenRatio);

    /**
     * If present hide the game window.
     */
    void hideGameWindow();

    /**
     * Setup and show the setting window if not present yes, show it if is present.
     */
    void showSettingsWindow();

    /**
     * Get the top-left point of the window.
     * 
     * @return the x,y origin of the window.
     */
    Optional<Point> getWindowLocation();

    /**
     * Get the degrees of the mouse relatively at the center of the game window.
     * 
     * @return A double representing the position of the mouse relatively to the
     *         center of the window [center-right = 0, top-center = 90, ...]
     */
    double getMouseRelativePosition();

    /**
     * Return the effective dimension of the game window.
     * 
     * @return A dimension that stores effective width and height of the game window
     */
    Optional<Dimension> getGameWindowDimension();

    /**
     * Allow to get the actual Player name if present.
     * 
     * @return a optional of string if present, otherwise return an optional empty
     */
    Optional<String> getPlayerName();

    /**
     * allow to get the keyCommandListener.
     * 
     * @return the keyCommandListener
     */
    KeyCommandsListener getKeyCommandsListener();

    /**
     * Return a flat object containing all the useful data from the file model.
     * 
     * @return A file containing all the useful data from the model
     */
    ModelData getModelData();

    /**
     * Make a specific component of the view to refresh. The possible component are
     * specified in the Component enumeration.
     * 
     * @param component
     *            The component to refresh
     */
    void refreshGui(Component component);

}
