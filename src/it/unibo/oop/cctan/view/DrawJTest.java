package it.unibo.oop.cctan.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;

import it.unibo.oop.cctan.controller.Controller;
import it.unibo.oop.cctan.controller.ControllerImpl;
import it.unibo.oop.cctan.controller.FileLoader;
import it.unibo.oop.cctan.interPackageComunication.Commands;
import it.unibo.oop.cctan.interPackageComunication.LoadedFiles;
import it.unibo.oop.cctan.interPackageComunication.LoadedFilesImpl;
import it.unibo.oop.cctan.interPackageComunication.MappableData;
import it.unibo.oop.cctan.interPackageComunication.ModelData;
import it.unibo.oop.cctan.interPackageComunication.ModelData.GameStatus;
import it.unibo.oop.cctan.interPackageComunication.ModelDataImpl;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class DrawJTest {

    private static final int REFRESH_TIME = 50; // Ms
    private static final double MOVING_TEST_MULTIPLIER = 0.001; // every call move the square by this amount
    private static final double SQUARE_EDGE_SIZE = 0.5;
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int SHORTER_EDGE = SCREEN_SIZE.width > SCREEN_SIZE.height ? SCREEN_SIZE.height
            : SCREEN_SIZE.width;
    private static final Pair<Integer, Integer> GAME_WINDOW_RATIO_TEST1 = new ImmutablePair<Integer, Integer>(1, 1); // ratio
    private static final Dimension GAME_WINDOW_DIMENSION_TEST1 = new Dimension(500, 500); // dimension of the window
                                                                                          // of
                                                                                          // window
    private static final Pair<Integer, Integer> GAME_WINDOW_RATIO_TEST2 = new ImmutablePair<Integer, Integer>(2, 1);
    private static final Dimension GAME_WINDOW_DIMENSION_TEST2 = new Dimension(SHORTER_EDGE, SHORTER_EDGE / 2);
    private static final Pair<Integer, Integer> GAME_WINDOW_RATIO_TEST3 = new ImmutablePair<Integer, Integer>(1, 2);
    private static final Dimension GAME_WINDOW_DIMENSION_TEST3 = new Dimension(SHORTER_EDGE / 2, SHORTER_EDGE);
    private static final String TEXT = "Testo linea 1" + System.lineSeparator() + "Testo linea 2"
            + System.lineSeparator() + "Testo linea 3";
    private View view;
    private int everyCallInitialValue;

    @Test
    void staticSquareTest() {
        everyCallInitialValue = 900;
        View view = new ViewImpl(new ControllerJTest(() -> everyCallInitialValue));
        view.showGameWindow(GAME_WINDOW_DIMENSION_TEST1, GAME_WINDOW_RATIO_TEST1);
        IntStream.range(0, 100).forEach(e -> {
            view.refreshGui();
            try {
                Thread.sleep(REFRESH_TIME);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        });
    }

    @Test
    void movingSquareTest() throws InterruptedException {
        everyCallInitialValue = 0;
        View view = new ViewImpl(new ControllerJTest(new Supplier<Integer>() {

            private int call = everyCallInitialValue;

            @Override
            public Integer get() {
                return call++;
            }
        }));
        view.showGameWindow(GAME_WINDOW_DIMENSION_TEST1, GAME_WINDOW_RATIO_TEST1);
        IntStream.range(0, 100).forEach(e -> {
            view.refreshGui();
            try {
                Thread.sleep(REFRESH_TIME);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        });
    }

    @Test
    void drawTextTest() throws InterruptedException {
        view = new ViewImpl(new ControllerJTest() {

            @Override
            public List<MappableData> getListOfMappableData() {
                List<MappableData> l = new LinkedList<>();
                l.add(new MappableDataImpl(TEXT, Color.RED, new Rectangle2D.Double(-1, -1, 2d, 2d)));
                return l;
            }
        });
        view.showGameWindow(GAME_WINDOW_DIMENSION_TEST1, GAME_WINDOW_RATIO_TEST1);
        Thread.sleep(TIME_BEFORE_JUNIT_TEST_END);
    }

    /*@Test
    public void xOverwhelm() {
        Controller ctrl = new ControllerImpl();
        View view = new ViewImpl(ctrl);
        view.showGameWindow(GAME_WINDOW_DIMENSION_TEST2, GAME_WINDOW_RATIO_TEST2);
        try {
            Thread.sleep(TIME_BEFORE_JUNIT_TEST_END);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void yOverwhelm() {
        Controller ctrl = new ControllerImpl();
        View v = new ViewImpl(ctrl);
        v.showGameWindow(GAME_WINDOW_DIMENSION_TEST3, GAME_WINDOW_RATIO_TEST3);
        try {
            Thread.sleep(TIME_BEFORE_JUNIT_TEST_END);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/

    private class ControllerJTest implements Controller {

        private Supplier<Integer> everyCall;
        private FileLoader fileLoader;

        ControllerJTest(final Supplier<Integer> everyCall) {
            this.everyCall = everyCall;
        }

        ControllerJTest() {
            fileLoader = new FileLoader(this);
        }

        private List<MappableData> getListOfMappableData() {
            return Arrays.asList(new MappableData() {

                @Override
                public String getText() {
                    return "test shape 1";
                }

                @Override
                public Shape getShape() {
                    return new Rectangle2D.Double(MOVING_TEST_MULTIPLIER * everyCall.get(),
                            MOVING_TEST_MULTIPLIER * everyCall.get(), SQUARE_EDGE_SIZE, SQUARE_EDGE_SIZE);
                }

                @Override
                public Color getColor() {
                    return Color.WHITE;
                }
            }, new MappableData() {

                @Override
                public String getText() {
                    return "Test with loooooooong text string ihihih";
                }

                @Override
                public Shape getShape() {
                    return new Rectangle2D.Double(-MOVING_TEST_MULTIPLIER * everyCall.get(),
                            -MOVING_TEST_MULTIPLIER * everyCall.get(), SQUARE_EDGE_SIZE, SQUARE_EDGE_SIZE);
                }

                @Override
                public Color getColor() {
                    return Color.RED;
                }
            });
        }

        private int getScore() {
            return 0;
        }
        @Override
        public void setView(final View v) {
        }

        @Override
        public void setMouseRelativePosition(final double angle) {
        }

        @Override
        public LoadedFiles getLoadedFiles() {
            return new LoadedFilesImpl(0);
        }

        @Override
        public void newCommand(Commands command) {
        }

        @Override
        public ModelData getModelData() {
            return new ModelDataImpl(getListOfMappableData(), getScore(), GameStatus.RUNNING);
        }

        @Override
        public void refreshGui() {
            view.refreshGui();
        }

    }

}
