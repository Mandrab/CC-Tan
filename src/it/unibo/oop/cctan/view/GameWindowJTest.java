package it.unibo.oop.cctan.view;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;

import it.unibo.oop.cctan.interpackage_comunication.CommandsObserverSource;
import it.unibo.oop.cctan.interpackage_comunication.GameStatus;
import it.unibo.oop.cctan.interpackage_comunication.MappableData;
import it.unibo.oop.cctan.interpackage_comunication.MappableDataImpl;
import it.unibo.oop.cctan.interpackage_comunication.ModelData;
import it.unibo.oop.cctan.interpackage_comunication.ModelDataImpl;
import it.unibo.oop.cctan.interpackage_comunication.SizeObserverSource;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class GameWindowJTest {

    private static final int REFRESH_TIME = 50; // Ms
    private static final int TIME_BEFORE_JUNIT_TEST_END = 5000; // Ms
    private static final double SQUARE_EDGE_SIZE = 0.5;
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final double DIMENSION_REDUCER_MULTIPLIER = 0.95;
    private static final int SHORTER_EDGE = SCREEN_SIZE.width > SCREEN_SIZE.height ? SCREEN_SIZE.height
            : SCREEN_SIZE.width;
    private static final Pair<Integer, Integer> GAME_WINDOW_RATIO_TEST1 = new ImmutablePair<Integer, Integer>(1, 1); // ratio
    private static final Dimension GAME_WINDOW_DIMENSION_TEST1 = new Dimension(500, 500); // dimension of the window
                                                                                          // of
                                                                                          // window
    private static final Pair<Integer, Integer> GAME_WINDOW_RATIO_TEST2 = new ImmutablePair<Integer, Integer>(4, 3);
    private static final Dimension GAME_WINDOW_DIMENSION_TEST2 = new Dimension(SHORTER_EDGE, SHORTER_EDGE * 3 / 4);
    private static final Pair<Integer, Integer> GAME_WINDOW_RATIO_TEST3 = new ImmutablePair<Integer, Integer>(9, 16);
    private static final Dimension GAME_WINDOW_DIMENSION_TEST3 = new Dimension((int) (SHORTER_EDGE * DIMENSION_REDUCER_MULTIPLIER * 9d / 16d), (int) (SHORTER_EDGE * DIMENSION_REDUCER_MULTIPLIER));
    private static final String TEXT1 = "TESTO PER test testo molto LUNGO.";
    private static final String TEXT2 = "Testo linea 1" + System.lineSeparator() + "Testo linea 2"
            + System.lineSeparator() + "Testo linea 3";
    private static final String GAME_WINDOW_VISIBLE_E = "GameWindow should not be visible";
    private static final String GAME_WINDOW_NOT_VISIBLE_E = "GameWindow should be visible";

    private GameWindow gw;

    @Test
    public void staticSquare() {
        final Supplier<Double> s = new Supplier<Double>() {

            private static final double UPPER = 0.6;
            private static final double LOWER = -0.6;
            private int call;

            @Override
            public Double get() {
                double d = call % 4 == 0 || call % 4 == 1 ? UPPER : LOWER;
                call++;
                return d;
            }
        };
        squareTest(getModelDataSupplier(Optional.of(s), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));
        assertFalse(GAME_WINDOW_VISIBLE_E, gw.isVisible());
    }

    @Test
    public void movingSquare() {
        final Supplier<Double> s = new Supplier<Double>() {

            private static final double TO_UP_INITIAL_VALUE = -0.6;
            private static final double TO_BOTTOM_INITIAL_VALUE = 0.6;
            private static final double DELTA_MOVE = 0.001;
            private int call;

            @Override
            public Double get() {
                double d = call % 4 == 0 || call % 4 == 1 ? TO_UP_INITIAL_VALUE + call * DELTA_MOVE : TO_BOTTOM_INITIAL_VALUE - call * DELTA_MOVE;
                call++;
                return d;
            }
        };
        squareTest(getModelDataSupplier(Optional.of(s), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));
        assertFalse(GAME_WINDOW_VISIBLE_E, gw.isVisible());
    }

    @Test
    public void commandsTextDrawTest() {
        final Supplier<GameStatus> s = new Supplier<GameStatus>() {

            private static final double LOWER_BOUND = 0.3;
            private static final double UPPER_BOUND = 0.6;
            private int cicle = -1;

            @Override
            public GameStatus get() {
                return (++cicle < (TIME_BEFORE_JUNIT_TEST_END / REFRESH_TIME) * LOWER_BOUND
                        ? GameStatus.RUNNING 
                        : cicle < (TIME_BEFORE_JUNIT_TEST_END / REFRESH_TIME) * UPPER_BOUND
                            ? GameStatus.PAUSED 
                            : GameStatus.ENDED);
            }
        };
        squareTest(getModelDataSupplier(Optional.empty(), Optional.empty(), Optional.of(s), Optional.empty(), Optional.empty()));
        assertFalse(GAME_WINDOW_VISIBLE_E, gw.isVisible());
    }

    @Test
    public void shapeTextDrawTest() {
        final Supplier<Double> positionSupplier = new Supplier<Double>() {

            private static final double UPPER_POSITION = 0d;
            private static final double LOWER_POSITION = -0.6;
            private int call;

            @Override
            public Double get() {
                double d = call % 4 == 0 || call % 4 == 1 ? UPPER_POSITION : LOWER_POSITION;
                call++;
                return d;
            }
        };
        final Supplier<Double> sizeSupplier = new Supplier<Double>() {

            private static final double UPPER_SIZE = 0.3d;
            private static final double LOWER_SIZE = 1d;
            private int call;

            @Override
            public Double get() {
                double d = call % 4 == 0 || call % 4 == 1 ? UPPER_SIZE : LOWER_SIZE;
                call++;
                return d;
            }
        };
        final Supplier<String> stringSupplier = new Supplier<String>() {

            private int call;

            @Override
            public String get() {
                return call++ % 2 == 0 ? TEXT1 : TEXT2;
            }
        };
        squareTest(getModelDataSupplier(Optional.of(positionSupplier), Optional.of(sizeSupplier), Optional.empty(), Optional.empty(), Optional.of(stringSupplier)));
        assertFalse(GAME_WINDOW_VISIBLE_E, gw.isVisible());
    }

    @Test
    public void unbalancedRatioXOverwhelmingTest() {
        final View view = new EmptyJTestView();
        gw = new GameWindow(view);
        gw.update(GAME_WINDOW_DIMENSION_TEST2, GAME_WINDOW_RATIO_TEST2);
        assertFalse(GAME_WINDOW_VISIBLE_E, gw.isVisible());
        gw.setVisible(true);
        assertTrue(GAME_WINDOW_NOT_VISIBLE_E, gw.isVisible());
        final List<MappableData> list = new LinkedList<>();
        list.add(new MappableDataImpl(Integer.toString((int) (Math.random() * 10)), 
                Color.RED,
                new Rectangle2D.Double(-(GAME_WINDOW_RATIO_TEST2.getKey().doubleValue() / GAME_WINDOW_RATIO_TEST2.getValue().doubleValue() * DIMENSION_REDUCER_MULTIPLIER),
                                       1 * DIMENSION_REDUCER_MULTIPLIER,
                                       SQUARE_EDGE_SIZE, 
                                       SQUARE_EDGE_SIZE)));             //Top-Left
        list.add(new MappableDataImpl(Integer.toString((int) (Math.random() * 10)), 
                Color.RED,
                new Rectangle2D.Double((GAME_WINDOW_RATIO_TEST2.getKey().doubleValue() / GAME_WINDOW_RATIO_TEST2.getValue().doubleValue() * DIMENSION_REDUCER_MULTIPLIER),
                                       -1 * DIMENSION_REDUCER_MULTIPLIER,
                                       SQUARE_EDGE_SIZE, 
                                       SQUARE_EDGE_SIZE)));             //Bottom-Right
        for (int cicle = 0; cicle * REFRESH_TIME < TIME_BEFORE_JUNIT_TEST_END; cicle++) {
            gw.refresh(new ModelDataImpl(list, 
                            (int) (Math.random() * 10),
                            GameStatus.RUNNING));
            view.refreshGui();
            try {
                Thread.sleep(REFRESH_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertTrue(GAME_WINDOW_NOT_VISIBLE_E, gw.isVisible());
        gw.setVisible(false);
        assertFalse(GAME_WINDOW_VISIBLE_E, gw.isVisible());
    }

    @Test
    public void unbalancedRatioYOverwhelmingTest() {
        final View view = new EmptyJTestView();
        gw = new GameWindow(view);
        gw.update(GAME_WINDOW_DIMENSION_TEST3, GAME_WINDOW_RATIO_TEST3);
        assertFalse(GAME_WINDOW_VISIBLE_E, gw.isVisible());
        gw.setVisible(true);
        assertTrue(GAME_WINDOW_NOT_VISIBLE_E, gw.isVisible());
        final List<MappableData> list = new LinkedList<>();
        list.add(new MappableDataImpl(Integer.toString((int) (Math.random() * 10)), 
                Color.RED,
                new Rectangle2D.Double(-GAME_WINDOW_RATIO_TEST3.getKey().doubleValue() / GAME_WINDOW_RATIO_TEST3.getValue().doubleValue() * DIMENSION_REDUCER_MULTIPLIER,
                                       1 * DIMENSION_REDUCER_MULTIPLIER,
                                       SQUARE_EDGE_SIZE, 
                                       SQUARE_EDGE_SIZE)));             //Top-Left
        list.add(new MappableDataImpl(Integer.toString((int) (Math.random() * 10)), 
                Color.RED,
                new Rectangle2D.Double((GAME_WINDOW_RATIO_TEST3.getKey().doubleValue() / GAME_WINDOW_RATIO_TEST3.getValue().doubleValue() * DIMENSION_REDUCER_MULTIPLIER),
                                       -1 * DIMENSION_REDUCER_MULTIPLIER,
                                       SQUARE_EDGE_SIZE, 
                                       SQUARE_EDGE_SIZE)));             //Bottom-Right
        for (int cicle = 0; cicle * REFRESH_TIME < TIME_BEFORE_JUNIT_TEST_END; cicle++) {
            gw.refresh(new ModelDataImpl(list, 
                            (int) (Math.random() * 10),
                            GameStatus.RUNNING));
            view.refreshGui();
            try {
                Thread.sleep(REFRESH_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertTrue(GAME_WINDOW_NOT_VISIBLE_E, gw.isVisible());
        gw.setVisible(false);
        assertFalse(GAME_WINDOW_VISIBLE_E, gw.isVisible());
    }

    private void squareTest(final Supplier<ModelData> modelDataSupplier) {
        final View view = new EmptyJTestView();
        gw = new GameWindow(view);
        gw.update(GAME_WINDOW_DIMENSION_TEST1, GAME_WINDOW_RATIO_TEST1);
        assertFalse(GAME_WINDOW_VISIBLE_E, gw.isVisible());
        gw.setVisible(true);
        assertTrue(GAME_WINDOW_NOT_VISIBLE_E, gw.isVisible());
        for (int cicle = 0; cicle * REFRESH_TIME < TIME_BEFORE_JUNIT_TEST_END; cicle++) {
            gw.refresh(modelDataSupplier.get());
            view.refreshGui();
            try {
                Thread.sleep(REFRESH_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertTrue(GAME_WINDOW_NOT_VISIBLE_E, gw.isVisible());
        gw.setVisible(false);
    }

    private Supplier<ModelData> getModelDataSupplier(final Optional<Supplier<Double>> positionSupplier, final Optional<Supplier<Double>> squareDimension, final Optional<Supplier<GameStatus>> statusSupplier, final Optional<Integer> score, final Optional<Supplier<String>> text) {
        return () -> new ModelDataImpl(IntStream.range(0, 2)
                        .mapToObj(i -> new MappableDataImpl(text.isPresent() ? text.get().get() : Integer.toString((int) (Math.random() * 10)), 
                        Color.RED,
                        new Rectangle2D.Double(positionSupplier.isPresent() ? positionSupplier.get().get() : Math.random() * 2 - 1,
                                               positionSupplier.isPresent() ? positionSupplier.get().get() : Math.random() * 2 - 1,
                                               squareDimension.isPresent() ? squareDimension.get().get() : SQUARE_EDGE_SIZE, 
                                               squareDimension.isPresent() ? squareDimension.get().get() : SQUARE_EDGE_SIZE)))
                        .collect(Collectors.toList()), 
                        score.isPresent() ? score.get() : (int) (Math.random() * 10),
                        statusSupplier.isPresent() ? statusSupplier.get().get() : GameStatus.RUNNING);
    }

    /**
     * Skeleton class.
     */
    private class EmptyJTestView implements View {

        @Override
        public Optional<CommandsObserverSource> getCommandsObserverSource() {
            return Optional.empty();
        }

        @Override
        public Optional<SizeObserverSource> getSizeObserverSource() {
            return Optional.empty();
        }

        @Override
        public void showGameWindow(final Dimension resolution, final Pair<Integer, Integer> screenRatio) {
        }

        @Override
        public void showSettingsWindow() {
        }

        @Override
        public Optional<Point> getWindowLocation() {
            return Optional.empty();
        }

        @Override
        public double getMouseRelativePosition() {
            return 0;
        }

        @Override
        public Optional<Dimension> getGameWindowDimension() {
            return Optional.empty();
        }

        @Override
        public Optional<String> getPlayerName() {
            return Optional.empty();
        }

        @Override
        public KeyCommandsListener getKeyCommandsListener() {
            return null;
        }

        @Override
        public ModelData getModelData() {
            return null;
        }

        @Override
        public void refreshGui() {
        }

        @Override
        public void hideGameWindow() {
        }

    }

}
