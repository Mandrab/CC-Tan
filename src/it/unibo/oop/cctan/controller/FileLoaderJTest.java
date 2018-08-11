package it.unibo.oop.cctan.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.ImageIcon;

import org.apache.commons.io.FileUtils;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import it.unibo.oop.cctan.interPackageComunication.MappableData;
import it.unibo.oop.cctan.view.View;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FileLoaderJTest {

    private FileLoader f;

    @Test
    public void directoryCreation() throws InterruptedException {
        deleteDirectory(System.getProperty("user.home") + "/.cctan");
        createFileLoader();
        assertTrue(Files.exists(Paths.get(System.getProperty("user.home") + "/.cctan"), LinkOption.NOFOLLOW_LINKS));
        assertTrue(Files.exists(Paths.get(System.getProperty("user.home") + "/.cctan/img"), LinkOption.NOFOLLOW_LINKS));
        assertTrue(Files.exists(Paths.get(System.getProperty("user.home") + "/.cctan/score"), LinkOption.NOFOLLOW_LINKS));
        deleteDirectory(System.getProperty("user.home") + "/.cctan");
    }

    @Test
    public void svgConversion() throws InterruptedException {
        deleteDirectory(System.getProperty("user.home") + "/.cctan");
        createFileLoader();
        assertTrue(Files.exists(Paths.get(System.getProperty("user.home") + "/.cctan/img/cctan.jpg"), LinkOption.NOFOLLOW_LINKS));
        assertTrue(Files.isReadable(Paths.get(System.getProperty("user.home") + "/.cctan/img/cctan.jpg")));
        deleteDirectory(System.getProperty("user.home") + "/.cctan");
    }

    private void createFileLoader() {
        f = new FileLoader(new Controller() {

            @Override
            public void setView(final View v) {
            }

            @Override
            public void setLoadImage(final ImageIcon img) {
            }

            @Override
            public int getScore() {
                return 0;
            }

            @Override
            public List<MappableData> getListOfMappableData() {
                return null;
            }

            @Override
            public void advanceLoading(final int value) {
            }

            @Override
            public void setMouseRelativePosition(final double angle) {
            }

            @Override
            public File getFont() {
                return null;
            }
        });
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

}
