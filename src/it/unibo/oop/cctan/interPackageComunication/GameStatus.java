package it.unibo.oop.cctan.interPackageComunication;

/**
 * Specifies the states of the game.
 */
public enum GameStatus {

    /**
     * Specifies if the game is in running mode.
     */
    RUNNING {
        @Override
        public GameStatus denies() {
            return GameStatus.PAUSED;
        }
    },

    /**
     * Specifies if the game is in pause mode.
     */
    PAUSED {
        @Override
        public GameStatus denies() {
            return GameStatus.RUNNING;
        }
    },

    /**
     * Specifies if the game is ended or not yet started mode.
     */
    NOT_RUNNING {
        @Override
        public GameStatus denies() {
            return GameStatus.RUNNING;
        }
    };

    /**
     * Return the opposite of the status.
     * 
     * @return The opposite of the status
     */
    public abstract GameStatus denies();

}
