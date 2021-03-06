package it.unibo.oop.cctan.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

import javax.swing.ImageIcon;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import it.unibo.oop.cctan.interpackage_comunication.data.LoadedFiles;
import it.unibo.oop.cctan.interpackage_comunication.data.LoadedFilesSingleton;
import it.unibo.oop.cctan.interpackage_comunication.data.LoadedFiles.ImageType;

/**
 * A class created to allow files access and modification. This class is package
 * protected.
 */
class FileLoader extends Thread {

    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final String PC_PATH = System.getProperty("user.home") + "/.cctan";
    private static final String PC_DIRECTORY_IMG = "/img";
    private static final String PC_DIRECTORY_SCORE = "/score";
    private static final String PC_FILE_SCORES_DIR_SCORE = "/Scores";
    private static final String PC_AND_JAR_IMG_JPG_LOGO = "/cctan.jpg";
    private static final String PC_AND_JAR_IMG_JPG_ICON = "/icona.jpg";
    private static final String JAR_DIRECTORY_FONT_SUBSPACE = "/subspace_font";
    private static final String JAR_FILE_FONT_SUBSPACE = "/SubspaceItalic.otf";
    private static final String JAR_IMG_SVG_ICON = "/icona.svg";
    private static final String JAR_IMG_SVG_LOGO = "/cctan.svg";
    private static final int[] PERCENTAGE_ADVANCE = { 10, 10, 10, 20, 50 };
    private static final IntSupplier ADVANCE_PERCENTAGE = new IntSupplier() {

        private int index;

        @Override
        public int getAsInt() {
            return index < PERCENTAGE_ADVANCE.length 
                   ? PERCENTAGE_ADVANCE[index++] 
                   : PERCENTAGE_ADVANCE[PERCENTAGE_ADVANCE.length - 1];
        }
    };
    private static final float QUALITY = 1.0f;
    private final LoadedFiles loadedFiles;

    /**
     * FileLoader constructor.
     * 
     * @param controller
     *            A class that implements Controller interface
     */
    FileLoader() {
        super();
        loadedFiles = LoadedFilesSingleton.getLoadedFiles();
        loadedFiles.addLoaderPercentage(100);
    }

    @Override
    /** {@inheritDoc} */
    public void run() {
        // check/create the game directory
        createDirectories(PC_PATH, new String[] { PC_DIRECTORY_IMG, PC_DIRECTORY_SCORE });
        loadedFiles.increaseAdvance(ADVANCE_PERCENTAGE.getAsInt());

        // convert svg to jpg. if jpg file already exists will do nothing
        if (Files.notExists(Paths.get(PC_PATH, PC_DIRECTORY_IMG + PC_AND_JAR_IMG_JPG_LOGO),
                            LinkOption.NOFOLLOW_LINKS)) {
            loadedFiles.setImage(new ImageIcon(FileLoader.class.getResource(PC_AND_JAR_IMG_JPG_LOGO)), 
                                 ImageType.LOGO);
            try {
                convertSvgToJpg(() -> FileLoader.class.getResourceAsStream(JAR_IMG_SVG_LOGO),
                                PC_PATH + PC_DIRECTORY_IMG + PC_AND_JAR_IMG_JPG_LOGO);
            } catch (Exception e) {
                System.err.println("Error during svg conversion!");
                e.printStackTrace();
            }
        }
        loadedFiles.setImage(new ImageIcon(PC_PATH + PC_DIRECTORY_IMG + PC_AND_JAR_IMG_JPG_LOGO), 
                                           ImageType.LOGO);
        loadedFiles.increaseAdvance(ADVANCE_PERCENTAGE.getAsInt());

        // convert svg to jpg. if jpg file already exists will do nothing
        if (Files.notExists(Paths.get(PC_PATH, PC_DIRECTORY_IMG + PC_AND_JAR_IMG_JPG_ICON),
                            LinkOption.NOFOLLOW_LINKS)) {
            try {
                convertSvgToJpg(() -> FileLoader.class.getResourceAsStream(JAR_IMG_SVG_ICON),
                                PC_PATH + PC_DIRECTORY_IMG + PC_AND_JAR_IMG_JPG_ICON);
            } catch (Exception e) {
                System.err.println("Error during svg conversion!");
                e.printStackTrace();
            }
        }
        loadedFiles.setImage(new ImageIcon(PC_PATH + PC_DIRECTORY_IMG + PC_AND_JAR_IMG_JPG_ICON), 
                             ImageType.ICON);
        loadedFiles.increaseAdvance(ADVANCE_PERCENTAGE.getAsInt());

        // Create score file into .cctan folder
        if (Files.notExists(Paths.get(PC_PATH, PC_DIRECTORY_SCORE + PC_FILE_SCORES_DIR_SCORE),
                            LinkOption.NOFOLLOW_LINKS)) {
            loadedFiles.setScoresFile(new File(PC_FILE_SCORES_DIR_SCORE));
            try {
                final File file = new File(PC_PATH + PC_DIRECTORY_SCORE + PC_FILE_SCORES_DIR_SCORE);
                if (!file.createNewFile()) {
                    System.out.println("File already present at the specified location");
                }
            } catch (IOException e) {
                System.err.println("Error during score file creation!");
                e.printStackTrace();
            }
        }
        loadedFiles.setScoresFile(new File(PC_PATH + PC_DIRECTORY_SCORE + PC_FILE_SCORES_DIR_SCORE));
        loadedFiles.increaseAdvance(ADVANCE_PERCENTAGE.getAsInt());

        // Load the font file
        try {
            final Font font = Font.createFont(Font.TRUETYPE_FONT,
                    this.getClass().getResourceAsStream(JAR_DIRECTORY_FONT_SUBSPACE + JAR_FILE_FONT_SUBSPACE));
            loadedFiles.setFont(font);
        } catch (Exception e) {
            System.err.println("Failed to load font");
        }
        loadedFiles.increaseAdvance(ADVANCE_PERCENTAGE.getAsInt());
    }

    /**
     * Create directories in path with the names contained in the string array.
     * 
     * @param path
     *            The path in which will be created the directories
     * @param names
     *            The names of the directories to be created
     */
    private void createDirectories(final String path, final String... names) {
        Arrays.asList(names).forEach(name -> {
            if (!new File(path + name).mkdirs() && Files.notExists(Paths.get(path, name), LinkOption.NOFOLLOW_LINKS)) {
                System.err.println("An error as occurred during " + name + " directory creation!");
            }
        });
    }

    /**
     * Convert an .svg to .jpg.
     * 
     * @param inStreamSup
     *            The supplier for the resource stream
     * @param jpgUri
     *            The path in which create the .jpg file
     */
    private void convertSvgToJpg(final Supplier<InputStream> inStreamSup, final String jpgUri) {
        try {
            // Create a JPEG transcoder
            final JPEGTranscoder converter = new JPEGTranscoder();
            InputStream inStream = inStreamSup.get();
            converter.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, QUALITY);
            converter.addTranscodingHint(JPEGTranscoder.KEY_WIDTH, getAdaptedWidth(inStream));
            converter.addTranscodingHint(JPEGTranscoder.KEY_BACKGROUND_COLOR, Color.BLACK);
            inStream.close();

            // Create the transcoder input and output.
            inStream = inStreamSup.get();
            final TranscoderInput input = new TranscoderInput(inStream);
            final OutputStream ostream = new FileOutputStream(jpgUri);
            final TranscoderOutput output = new TranscoderOutput(ostream);

            // Save the image.
            converter.transcode(input, output);

            // Flush and close the stream.
            inStream.close();
            ostream.flush();
            ostream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Return an adapted width proportionally to the screen size.
     * 
     * @param svgUri
     *            The path to the .svg
     * @return A float representing the preferred width based on the screen size
     * @throws JDOMException
     *             An exception
     * @throws IOException
     *             An exception
     */
    private float getAdaptedWidth(final InputStream inStream) throws JDOMException, IOException {
        final SAXBuilder builder = new SAXBuilder();
        final Document document = builder.build(inStream);

        final Element root = document.getRootElement();
        final float svgRateo = Float.valueOf(root.getAttributeValue("width"))
                / Float.valueOf(root.getAttributeValue("height"));
        final double deltaX = SCREEN_SIZE.getWidth() - Double.valueOf(root.getAttributeValue("width"));
        final double deltaY = SCREEN_SIZE.getHeight() - Double.valueOf(root.getAttributeValue("height"));
        return deltaX < deltaY ? svgRateo * SCREEN_SIZE.height : SCREEN_SIZE.width;
    }

}
