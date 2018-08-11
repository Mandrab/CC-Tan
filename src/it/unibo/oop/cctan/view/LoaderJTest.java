package it.unibo.oop.cctan.view;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.ImageIcon;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import it.unibo.oop.cctan.controller.FileLoader;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserver;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserverChainOfResponsibility;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserverSource;
import it.unibo.oop.cctan.interPackageComunication.LoadedFiles;
import it.unibo.oop.cctan.interPackageComunication.MappableData;

/**
 * Loader class test.
 */
public class LoaderJTest {

    private static final int SLEEP_TIME = 1500;
    private static final AtomicInteger AI = new AtomicInteger();
    private static final int ADVANCE_VALUE = AI.addAndGet(10);
    private static final int LAST_VALUE = 100;
    private Loader l = new Loader();

    @Test
    /**
     * Load-bar visual test.
     */
    public void visualLoad() {
        View v = new ViewJTest();

        try {
            v.advanceLoading(0);
            Thread.sleep(SLEEP_TIME);
            v.setLoadImage(new ImageIcon(FileLoader.class.getResource("/cctan.jpg")));
            v.advanceLoading(ADVANCE_VALUE);
            Thread.sleep(SLEEP_TIME);
            v.advanceLoading(ADVANCE_VALUE);
            Thread.sleep(SLEEP_TIME);
            v.advanceLoading(ADVANCE_VALUE);
            Thread.sleep(SLEEP_TIME);
            v.advanceLoading(ADVANCE_VALUE);
            Thread.sleep(SLEEP_TIME);
            v.advanceLoading(LAST_VALUE);
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public class ViewJTest extends SizeAndControlChainOfResponsibilityImpl implements View {

        @Override
        public void showGameWindow(Dimension resolution, Pair<Integer, Integer> screenRatio) {
        }

        @Override
        public void setLoadImage(ImageIcon img) {
            l.setLoadImage(img);
        }

        @Override
        public Optional<Point> getWindowLocation() {
            return Optional.empty();
        }

        @Override
        public int getScore() {
            return 0;
        }

        @Override
        public double getMouseRelativePosition() {
            return 0;
        }

        @Override
        public List<MappableData> getListOfMappableData() {
            return null;
        }

        @Override
        public Optional<Dimension> getDimension() {
            return Optional.empty();
        }

        @Override
        public void advanceLoading(int value) {
            l.advanceLoading(value);
        }

        @Override
        public void setMouseRelativePosition(double mouseRelativePosition) {
        }

        @Override
        public Optional<Dimension> getGameWindowDimension() {
            return Optional.empty();
        }

        @Override
        public void showSettingsWindow() {
            // TODO Auto-generated method stub

        }

        @Override
        public List<CommandsObserver> getCommandsObserversList() {
            return null;
        }

        @Override
        public List<SizeObserver> getSizeObserversList() {
            return null;
        }

        @Override
        public Optional<String> getPlayerName() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public KeyCommandsListener getKeyCommandsListener() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public LoadedFiles getLoadedFiles() {
            // TODO Auto-generated method stub
            return null;
        }
    };

}
