package it.unibo.oop.cctan.interPackageComunication;

import java.util.List;

public class ModelDataImpl implements ModelData {

    private final List<MappableData> mappableDatas;
    private final int score;
    private final GameStatus gameStatus;

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
