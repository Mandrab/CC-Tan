package it.unibo.oop.cctan.interPackageComunication;

public enum GameStatus {

    RUNNING {
        @Override
        public GameStatus denies() {
            return GameStatus.PAUSED;
        }
    },

    PAUSED {
        @Override
        public GameStatus denies() {
            return GameStatus.RUNNING;
        }
    },

    ENDED {
        @Override
        public GameStatus denies() {
            return GameStatus.RUNNING;
        }
    };

    public abstract GameStatus denies();

}
