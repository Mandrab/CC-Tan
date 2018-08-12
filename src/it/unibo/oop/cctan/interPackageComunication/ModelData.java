package it.unibo.oop.cctan.interPackageComunication;

import java.util.List;

public interface ModelData {

    enum GameStatus {

        RUNNING,

        PAUSED,

        ENDED;

    }

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

    GameStatus getGameStatus();

}
