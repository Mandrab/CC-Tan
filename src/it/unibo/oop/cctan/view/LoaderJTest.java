package it.unibo.oop.cctan.view;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Optional;
import java.util.function.IntSupplier;

import javax.swing.ImageIcon;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import it.unibo.oop.cctan.controller.FileLoader;
import it.unibo.oop.cctan.interPackageComunication.LoadedFiles;
import it.unibo.oop.cctan.interPackageComunication.LoadedFilesImpl;
import it.unibo.oop.cctan.interPackageComunication.ModelData;

/**
 * Loader class test.
 */
public class LoaderJTest {

    private static final int SLEEP_TIME = 1500;
    private int cicle = 0;
    private int setImgCicle = 3;
    private final IntSupplier getAdvance = () -> 10 * cicle++;
    private Loader l;

    @Test
    /**
     * Load-bar visual test.
     */
    public void visualLoad() {
        View v = new ViewJTest() {

            @Override
            public LoadedFiles getLoadedFiles() {
                int actual = getAdvance.getAsInt();
                LoadedFiles lf = new LoadedFilesImpl(actual);
                if (actual > 10 * setImgCicle) {
                    lf.setLogo(new ImageIcon(FileLoader.class.getResource("/cctan.jpg")));
                }
                return lf;
            }

        };
        l = new Loader(v);

        try {
            l.refresh();
            Thread.sleep(SLEEP_TIME);
            l.refresh();
            Thread.sleep(SLEEP_TIME);
            l.refresh();
            Thread.sleep(SLEEP_TIME);
            l.refresh();
            Thread.sleep(SLEEP_TIME);
            l.refresh();
            Thread.sleep(SLEEP_TIME);
            l.refresh();
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /** 
     * Scheleton class.
     */
    public class ViewJTest extends SizeAndControlChainOfResponsibilityImpl implements View {

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
        public Optional<Dimension> getDimension() {
            return Optional.empty();
        }

        @Override
        /** {@inheritDoc} */
        public void setMouseRelativePosition(final double mouseRelativePosition) {
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
            return null;
        }

        @Override
        /** {@inheritDoc} */
        public KeyCommandsListener getKeyCommandsListener() {
            return null;
        }

        @Override
        /** {@inheritDoc} */
        public LoadedFiles getLoadedFiles() {
            return null;
        }

        @Override
        /** {@inheritDoc} */
        public CommandsObserversSourceImpl getCommandsObserversManager() {
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
    };

}
