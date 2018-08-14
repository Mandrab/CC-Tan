package it.unibo.oop.cctan.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import it.unibo.oop.cctan.interPackageComunication.Commands;
import it.unibo.oop.cctan.interPackageComunication.LoadedFiles;
import it.unibo.oop.cctan.interPackageComunication.ModelData;
import it.unibo.oop.cctan.view.View;
import it.unibo.oop.cctan.view.View.Component;

/**
 * Class to test fileLoader work.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FileLoaderJTest {

    /**
     * Test if fileLoader class properly create directories.
     * 
     * @throws InterruptedException
     *             join() method can throw an exception
     */
    @Test
    public void directoryCreation() throws InterruptedException {
        deleteDirectory(System.getProperty("user.home") + "/.cctan");
        FileLoader fl = createFileLoader();
        fl.start();
        fl.join();
        assertTrue(Files.exists(Paths.get(System.getProperty("user.home") + "/.cctan"), LinkOption.NOFOLLOW_LINKS));
        assertTrue(Files.exists(Paths.get(System.getProperty("user.home") + "/.cctan/img"), LinkOption.NOFOLLOW_LINKS));
        assertTrue(
                Files.exists(Paths.get(System.getProperty("user.home") + "/.cctan/score"), LinkOption.NOFOLLOW_LINKS));
        deleteDirectory(System.getProperty("user.home") + "/.cctan");
    }

    /**
     * Check if at fileLoader end exist a converted .jpg.
     * 
     * @throws InterruptedException
     *             join() method can throw an exception
     */
    @Test
    public void svgConversion() throws InterruptedException {
        deleteDirectory(System.getProperty("user.home") + "/.cctan");
        FileLoader fl = createFileLoader();
        fl.start();
        fl.join();
        assertTrue(Files.exists(Paths.get(System.getProperty("user.home") + "/.cctan/img/cctan.jpg"),
                LinkOption.NOFOLLOW_LINKS));
        assertTrue(Files.isReadable(Paths.get(System.getProperty("user.home") + "/.cctan/img/cctan.jpg")));
        deleteDirectory(System.getProperty("user.home") + "/.cctan");
    }

    private void deleteDirectory(final String path) {
        try {
            if (Files.exists(Paths.get(path))) {
                FileUtils.deleteDirectory(new File(path));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private FileLoader createFileLoader() {
        return new FileLoader(new Controller() {

            @Override
            public void setView(final View v) {
            }

            @Override
            public void setMouseRelativePosition(final double angle) {
            }

            @Override
            public LoadedFiles getLoadedFiles() {
                return null;
            }

            @Override
            public void newCommand(final Commands command) {
            }

            @Override
            public void update(final Dimension gameWindowSize, final Pair<Integer, Integer> screenRatio) {
            }

            @Override
            public ModelData getModelData() {
                return null;
            }

            @Override
            public void refreshGui(final Component component) {
            }
        });
    }

}
