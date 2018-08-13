package it.unibo.oop.cctan.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.SwingUtilities;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;

import it.unibo.oop.cctan.interPackageComunication.CommandsObserverChainOfResponsibility;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserverSource;
import it.unibo.oop.cctan.interPackageComunication.LoadedFiles;
import it.unibo.oop.cctan.interPackageComunication.LoadedFilesImpl;
import it.unibo.oop.cctan.interPackageComunication.MappableDataImpl;
import it.unibo.oop.cctan.interPackageComunication.ModelData;
import it.unibo.oop.cctan.interPackageComunication.GameStatus;
import it.unibo.oop.cctan.interPackageComunication.ModelDataImpl;
import it.unibo.oop.cctan.view.View.Component;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class DrawJTest {

    private static final int REFRESH_TIME = 50; // Ms
    private static final int TIME_BEFORE_JUNIT_TEST_END = 5000; // Ms
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

   /* @Test
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

    private GameWindow gw;

    @Test
    public void staticSquare() throws InterruptedException, InvocationTargetException {
        View view = new ViewJTest();
                /*return new ModelDataImpl(IntStream.range(0, 20)
                                                  .mapToObj(e -> new MappableDataImpl("" + Math.random() * 10, 
                                                                                      Color.RED,
                                                                                      new Rectangle2D.Double(Math.random() * 2 - 1, 
                                                                                                             Math.random() * 2 - 1, 
                                                                                                             0.2, 
                                                                                                             0.2)))
                                                  .collect(Collectors.toList()), 
                                         (int) (Math.random()*10), 
                                         GameStatus.RUNNING);*/
        gw = new GameWindow(view);
        gw.update(GAME_WINDOW_DIMENSION_TEST1, GAME_WINDOW_RATIO_TEST1);
        gw.setVisible(true);
        int cicle = 0;
        while (cicle * REFRESH_TIME < TIME_BEFORE_JUNIT_TEST_END) {
            gw.refresh(new ModelDataImpl(IntStream.range(1, 2)
                                                  .mapToObj(i -> new MappableDataImpl("" + Math.random() * 10, 
                                                                                      Color.RED,
                                                                                      new Rectangle2D.Double(i == 1 
                                                                                                             ? -0.15 - cicle/10 
                                                                                                             : 0.15 + cicle/10, 
                                                                                                             i == 1 
                                                                                                             ? -0.15 - cicle/10 
                                                                                                             : 0.15 + cicle/10, 
                                                                                                             0.4, 
                                                                                                             0.4)))
                                                  .collect(Collectors.toList()), 
                                                  (int) (Math.random() * 10),
                                                  GameStatus.RUNNING));
            view.refreshGui(Component.GAME_WINDOW);
            try {
                Thread.sleep(REFRESH_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cicle++;
        }
    }

    private class ViewJTest implements View {

        @Override
        public void setCommandsSuccessor(final CommandsObserverChainOfResponsibility successor) {
        }

        @Override
        public void setCommandsObserverSource(final CommandsObserverSource commandsObserverSource) {
        }

        @Override
        public Optional<CommandsObserverSource> getCommandsObserverSource() {
            return Optional.empty();
        }

        @Override
        public void setSizeSuccessor(final SizeObserverChainOfResponsibility successor) {
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
        public Optional<Dimension> getDimension() {
            return Optional.empty();
        }

        @Override
        public void setMouseRelativePosition(final double mouseRelativePosition) {
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

    }

}