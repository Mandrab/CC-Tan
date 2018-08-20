package it.unibo.oop.cctan.interpackage_comunication.data;

import java.util.List;

import it.unibo.oop.cctan.interpackage_comunication.GameStatus;

/**
 * A flat class containing all the useful data from the model.
 */
public interface ModelData {

    /**
     * Return a list of data that as to be mapped.
     * 
     * @return The list of the MappableData
     */
    List<MappableData> getMappableDatas();

    /**
     * Obtain the actual score in the game.
     * 
     * @return the actual score
     */
    int getScore();

    /**
     * Return the actual status of the game as described in the GameStatus
     * enumeration.
     * 
     * @return The status of the game
     */
    GameStatus getGameStatus();

}
