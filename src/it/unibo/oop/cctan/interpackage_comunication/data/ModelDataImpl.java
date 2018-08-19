package it.unibo.oop.cctan.interpackage_comunication.data;

import java.util.List;

import it.unibo.oop.cctan.interpackage_comunication.GameStatus;

/**
 * A class that implements ModelData interface.
 */
public class ModelDataImpl implements ModelData {

    private final List<MappableData> mappableDatas;
    private final int score;
    private final GameStatus gameStatus;

    /**
     * Constructor of the class.
     * 
     * @param mappableDatas
     *            The list of the data to be painted
     * @param score
     *            The actual score in the game
     * @param gameStatus
     *            The actual game status
     */
    public ModelDataImpl(final List<MappableData> mappableDatas, final int score, final GameStatus gameStatus) {
        this.mappableDatas = mappableDatas;
        this.score = score;
        this.gameStatus = gameStatus;
    }

    @Override
    /** {@inheritDoc} */
    public List<MappableData> getMappableDatas() {
        return mappableDatas;
    }

    @Override
    /** {@inheritDoc} */
    public int getScore() {
        return score;
    }

    @Override
    /** {@inheritDoc} */
    public GameStatus getGameStatus() {
        return gameStatus;
    }

}
