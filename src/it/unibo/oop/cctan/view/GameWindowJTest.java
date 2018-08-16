package it.unibo.oop.cctan.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.InvocationTargetException;
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

import it.unibo.oop.cctan.interPackageComunication.CommandsObserverLink;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserverSource;
import it.unibo.oop.cctan.interPackageComunication.LoadedFiles;
import it.unibo.oop.cctan.interPackageComunication.LoadedFilesImpl;
import it.unibo.oop.cctan.interPackageComunication.MappableData;
import it.unibo.oop.cctan.interPackageComunication.MappableDataImpl;
import it.unibo.oop.cctan.interPackageComunication.ModelData;
import it.unibo.oop.cctan.interPackageComunication.GameStatus;
import it.unibo.oop.cctan.interPackageComunication.ModelDataImpl;
import it.unibo.oop.cctan.interPackageComunication.SizeObserverLink;
import it.unibo.oop.cctan.interPackageComunication.SizeObserverSource;
import it.unibo.oop.cctan.view.View.Component;

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

    private GameWindow gw;

    @Test
    public void staticSquare() throws InterruptedException, InvocationTargetException {
        Supplier<Double> s = new Supplier<Double>() {

            private final double upper = 0.6;
            private final double lower = -0.6;
            private int call = 0;

            @Override
            public Double get() {
                double d = call % 4 == 0 || call % 4 == 1 ? upper : lower;
                call++;
                return d;
            }
        };
        squareTest(getModelDataSupplier(Optional.of(s), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));
    }

    @Test
    public void movingSquare() throws InterruptedException, InvocationTargetException {
        Supplier<Double> s = new Supplier<Double>() {

            private final double toUpInitialValue = -0.6;
            private final double toBottomInitialValue = 0.6;
            private final double deltaMove = 0.001;
            private int call = 0;

            @Override
            public Double get() {
                double d = call % 4 == 0 || call % 4 == 1 ? toUpInitialValue + call * deltaMove : toBottomInitialValue - call * deltaMove;
                call++;
                return d;
            }
        };
        squareTest(getModelDataSupplier(Optional.of(s), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));
    }

    @Test
    public void commandsTextDrawTest() {
        Supplier<GameStatus> s = new Supplier<GameStatus>() {

            private final double lowerBound = 0.3;
            private final double upperBound = 0.6;
            private int cicle = -1;

            @Override
            public GameStatus get() {
                return (++cicle < (TIME_BEFORE_JUNIT_TEST_END / REFRESH_TIME) * lowerBound
                        ? GameStatus.RUNNING 
                        : cicle < (TIME_BEFORE_JUNIT_TEST_END / REFRESH_TIME) * upperBound
                            ? GameStatus.PAUSED 
                            : GameStatus.NOT_RUNNING);
            }
        };
        squareTest(getModelDataSupplier(Optional.empty(), Optional.empty(), Optional.of(s), Optional.empty(), Optional.empty()));
    }

    @Test
    public void shapeTextDrawTest() {
        Supplier<Double> positionSupplier = new Supplier<Double>() {

            private final double upperPosition = 0d;
            private final double lowerPosition = -0.6;
            private int call = 0;

            @Override
            public Double get() {
                double d = call % 4 == 0 || call % 4 == 1 ? upperPosition : lowerPosition;
                call++;
                return d;
            }
        };
        Supplier<Double> sizeSupplier = new Supplier<Double>() {

            private final double upperSize = 0.3d;
            private final double lowerSize = 1d;
            private int call = 0;

            @Override
            public Double get() {
                double d = call % 4 == 0 || call % 4 == 1 ? upperSize : lowerSize;
                call++;
                return d;
            }
        };
        Supplier<String> stringSupplier = new Supplier<String>() {

            private int call = 0;

            @Override
            public String get() {
                return call++ % 2 == 0 ? TEXT1 : TEXT2;
            }
        };
        squareTest(getModelDataSupplier(Optional.of(positionSupplier), Optional.of(sizeSupplier), Optional.empty(), Optional.empty(), Optional.of(stringSupplier)));
    }

    @Test
    public void unbalancedRatioXOverwhelmingTest() {
        View view = new EmptyJTestView();
        gw = new GameWindow(view);
        gw.update(GAME_WINDOW_DIMENSION_TEST2, GAME_WINDOW_RATIO_TEST2);
        gw.setVisible(true);
        List<MappableData> list = new LinkedList<>();
        list.add(new MappableDataImpl("" + (int) (Math.random() * 10), 
                Color.RED,
                new Rectangle2D.Double(-(GAME_WINDOW_RATIO_TEST2.getKey().doubleValue() / GAME_WINDOW_RATIO_TEST2.getValue().doubleValue() * DIMENSION_REDUCER_MULTIPLIER),
                                       1 * DIMENSION_REDUCER_MULTIPLIER,
                                       SQUARE_EDGE_SIZE, 
                                       SQUARE_EDGE_SIZE)));             //Top-Left
        list.add(new MappableDataImpl("" + (int) (Math.random() * 10), 
                Color.RED,
                new Rectangle2D.Double((GAME_WINDOW_RATIO_TEST2.getKey().doubleValue() / GAME_WINDOW_RATIO_TEST2.getValue().doubleValue() * DIMENSION_REDUCER_MULTIPLIER),
                                       -1 * DIMENSION_REDUCER_MULTIPLIER,
                                       SQUARE_EDGE_SIZE, 
                                       SQUARE_EDGE_SIZE)));             //Bottom-Right
        int cicle = 0;
        while (cicle++ * REFRESH_TIME < TIME_BEFORE_JUNIT_TEST_END) {
            gw.refresh(new ModelDataImpl(list, 
                            (int) (Math.random() * 10),
                            GameStatus.RUNNING));
            view.refreshGui(Component.GAME_WINDOW);
            try {
                Thread.sleep(REFRESH_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        gw.setVisible(false);
    }

    @Test
    public void unbalancedRatioYOverwhelmingTest() {
        View view = new EmptyJTestView();
        gw = new GameWindow(view);
        gw.update(GAME_WINDOW_DIMENSION_TEST3, GAME_WINDOW_RATIO_TEST3);
        gw.setVisible(true);
        List<MappableData> list = new LinkedList<>();
        list.add(new MappableDataImpl("" + (int) (Math.random() * 10), 
                Color.RED,
                new Rectangle2D.Double(-GAME_WINDOW_RATIO_TEST3.getKey().doubleValue() / GAME_WINDOW_RATIO_TEST3.getValue().doubleValue() * DIMENSION_REDUCER_MULTIPLIER,
                                       1 * DIMENSION_REDUCER_MULTIPLIER,
                                       SQUARE_EDGE_SIZE, 
                                       SQUARE_EDGE_SIZE)));             //Top-Left
        list.add(new MappableDataImpl("" + (int) (Math.random() * 10), 
                Color.RED,
                new Rectangle2D.Double((GAME_WINDOW_RATIO_TEST3.getKey().doubleValue() / GAME_WINDOW_RATIO_TEST3.getValue().doubleValue() * DIMENSION_REDUCER_MULTIPLIER),
                                       -1 * DIMENSION_REDUCER_MULTIPLIER,
                                       SQUARE_EDGE_SIZE, 
                                       SQUARE_EDGE_SIZE)));             //Bottom-Right
        int cicle = 0;
        while (cicle++ * REFRESH_TIME < TIME_BEFORE_JUNIT_TEST_END) {
            gw.refresh(new ModelDataImpl(list, 
                            (int) (Math.random() * 10),
                            GameStatus.RUNNING));
            view.refreshGui(Component.GAME_WINDOW);
            try {
                Thread.sleep(REFRESH_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        gw.setVisible(false);
    }

    private void squareTest(final Supplier<ModelData> modelDataSupplier) {
        View view = new EmptyJTestView();
        gw = new GameWindow(view);
        gw.update(GAME_WINDOW_DIMENSION_TEST1, GAME_WINDOW_RATIO_TEST1);
        gw.setVisible(true);
        int cicle = 0;
        while (cicle++ * REFRESH_TIME < TIME_BEFORE_JUNIT_TEST_END) {
            gw.refresh(modelDataSupplier.get());
            view.refreshGui(Component.GAME_WINDOW);
            try {
                Thread.sleep(REFRESH_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        gw.setVisible(false);
    }

    private Supplier<ModelData> getModelDataSupplier(final Optional<Supplier<Double>> positionSupplier, final Optional<Supplier<Double>> squareDimension, final Optional<Supplier<GameStatus>> statusSupplier, final Optional<Integer> score, final Optional<Supplier<String>> text) {
        return () -> new ModelDataImpl(IntStream.range(0, 2)
                        .mapToObj(i -> new MappableDataImpl(text.isPresent() ? text.get().get() : "" + (int) (Math.random() * 10), 
                        Color.RED,
                        new Rectangle2D.Double(positionSupplier.isPresent() ? positionSupplier.get().get() : Math.random() * 2 - 1,
                                               positionSupplier.isPresent() ? positionSupplier.get().get() : Math.random() * 2 - 1,
                                               squareDimension.isPresent() ? squareDimension.get().get() : SQUARE_EDGE_SIZE, 
                                               squareDimension.isPresent() ? squareDimension.get().get() : SQUARE_EDGE_SIZE)))
                        .collect(Collectors.toList()), 
                        score.isPresent() ? score.get() : (int) (Math.random() * 10),
                        statusSupplier.isPresent() ? statusSupplier.get().get() : GameStatus.RUNNING);
    }

    private class EmptyJTestView implements View {

        @Override
        public void setCommandsSuccessor(final CommandsObserverLink successor) {
        }

        @Override
        public void setCommandsObserverSource(final CommandsObserverSource commandsObserverSource) {
        }

        @Override
        public Optional<CommandsObserverSource> getCommandsObserverSource() {
            return Optional.empty();
        }

        @Override
        public void setSizeSuccessor(final SizeObserverLink successor) {
        }

        @Override
        public void setSizeObserverSource(final SizeObserverSource sizeObserverSource) {
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
        public LoadedFiles getLoadedFiles() {
            return new LoadedFilesImpl(0);
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
        public void refreshGui(final Component component) {
        }

        @Override
        public void hideGameWindow() {
        }

    }

}
