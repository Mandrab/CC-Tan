package it.unibo.oop.cctan.view;

import static org.junit.Assert.fail;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Optional;
import java.util.function.IntSupplier;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import it.unibo.oop.cctan.interpackage_comunication.LoadedFiles;
import it.unibo.oop.cctan.interpackage_comunication.LoadedFilesSingleton;
import it.unibo.oop.cctan.interpackage_comunication.ModelData;

/**
 * Loader class test.
 */
public class LoaderJTest {

    private static final String EXCEPTION_STRING = "An exception has been caught";
    private static final int SLEEP_TIME = 1500;
    private static final int[] INCREASE = {5, 5, 10, 5, 15, 20};
    private static final int MAX_PERCENTAGE = 60;
    private static final IntSupplier ADVANCE_LOADING = new IntSupplier() {

        private int cicle;

        @Override
        public int getAsInt() {
            return INCREASE[cicle++];
        }
    };

    /**
     * Load-bar visual test.
     */
    @Test
    public void visualLoad() {
        final Loader loader = new Loader(new ViewJTest());
        final LoadedFiles loadedFiles = LoadedFilesSingleton.getLoadedFiles();
        loadedFiles.addLoaderPercentage(MAX_PERCENTAGE);

        try {
            loadedFiles.increaseAdvance(ADVANCE_LOADING.getAsInt());
            loader.update();
            Thread.sleep(SLEEP_TIME);
            loadedFiles.increaseAdvance(ADVANCE_LOADING.getAsInt());
            loader.update();
            Thread.sleep(SLEEP_TIME);
            loadedFiles.increaseAdvance(ADVANCE_LOADING.getAsInt());
            loader.update();
            Thread.sleep(SLEEP_TIME);
            loadedFiles.increaseAdvance(ADVANCE_LOADING.getAsInt());
            loader.update();
            Thread.sleep(SLEEP_TIME);
            loadedFiles.increaseAdvance(ADVANCE_LOADING.getAsInt());
            loader.update();
            Thread.sleep(SLEEP_TIME);
            loadedFiles.increaseAdvance(ADVANCE_LOADING.getAsInt());
            loader.update();
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail(EXCEPTION_STRING);
        }
    }

    /** 
     * Skeleton class.
     */
    public class ViewJTest extends SizeAndCommandsLinkImpl implements View {

        @Override
        /** {@inheritDoc} */
        public void showGameWindow(final Dimension resolution, final Pair<Integer, Integer> screenRatio) {
        }

        @Override
        /** {@inheritDoc} */
        public Optional<Point> getWindowLocation() {
            return Optional.empty();
        }

        @Override
        /** {@inheritDoc} */
        public double getMouseRelativePosition() {
            return 0;
        }

        @Override
        /** {@inheritDoc} */
        public Optional<Dimension> getGameWindowDimension() {
            return Optional.empty();
        }

        @Override
        /** {@inheritDoc} */
        public void showSettingsWindow() {

        }

        @Override
        /** {@inheritDoc} */
        public Optional<String> getPlayerName() {
            return Optional.empty();
        }

        @Override
        /** {@inheritDoc} */
        public KeyCommandsListener getKeyCommandsListener() {
            return null;
        }

        @Override
        /** {@inheritDoc} */
        public ModelData getModelData() {
            return null;
        }

        @Override
        /** {@inheritDoc} */
        public void refreshGui() {
        }

        @Override
        public void hideGameWindow() {
        }
    };

}
