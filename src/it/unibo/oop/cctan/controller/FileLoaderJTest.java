package it.unibo.oop.cctan.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Class to test fileLoader work.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FileLoaderJTest {

    private static final String USER_HOME = System.getProperty("user.home");
    private static final String GAME_DIRECTORY = "/.cctan";
    private static final String IMG_DIRECTORY = GAME_DIRECTORY + "/img";
    private static final String SCORE_DIRECTORY = GAME_DIRECTORY + "/score";
    private static final String IMG_FILE = "/cctan.jpg";
    private static final String DIRECTORY_CREATION_E = "Directory should exist: ";
    private static final String FILE_CREATION_E = "File should exist";
    private static final String FILE_READ_E = "File should readable";

    /**
     * Test if fileLoader class properly create directories.
     * 
     * @throws InterruptedException
     *             join() method can throw an exception
     */
    @Test
    public void directoryCreation() throws InterruptedException {
        deleteDirectory(USER_HOME + GAME_DIRECTORY);
        final FileLoader fileLoader = createFileLoader();
        fileLoader.start();
        fileLoader.join();
        assertTrue(Files.exists(Paths.get(USER_HOME + GAME_DIRECTORY), LinkOption.NOFOLLOW_LINKS), DIRECTORY_CREATION_E + GAME_DIRECTORY);
        assertTrue(Files.exists(Paths.get(USER_HOME + IMG_DIRECTORY), LinkOption.NOFOLLOW_LINKS), DIRECTORY_CREATION_E + IMG_DIRECTORY);
        assertTrue(
                Files.exists(Paths.get(USER_HOME + SCORE_DIRECTORY), LinkOption.NOFOLLOW_LINKS), DIRECTORY_CREATION_E + SCORE_DIRECTORY);
        deleteDirectory(USER_HOME + GAME_DIRECTORY);
    }

    /**
     * Check if at fileLoader end exist a converted .jpg.
     * 
     * @throws InterruptedException
     *             join() method can throw an exception
     */
    @Test
    public void svgConversion() throws InterruptedException {
        deleteDirectory(USER_HOME + GAME_DIRECTORY);
        final FileLoader fileLoader = createFileLoader();
        fileLoader.start();
        fileLoader.join();
        assertTrue(Files.exists(Paths.get(USER_HOME + IMG_DIRECTORY + IMG_FILE),
                LinkOption.NOFOLLOW_LINKS), FILE_CREATION_E);
        assertTrue(Files.isReadable(Paths.get(USER_HOME + IMG_DIRECTORY + IMG_FILE)), FILE_READ_E);
        deleteDirectory(USER_HOME + GAME_DIRECTORY);
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
        return new FileLoader();
    }

}
