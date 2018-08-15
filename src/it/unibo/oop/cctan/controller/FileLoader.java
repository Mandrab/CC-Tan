package it.unibo.oop.cctan.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.function.IntSupplier;

import javax.swing.ImageIcon;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import it.unibo.oop.cctan.interPackageComunication.LoadedFiles;
import it.unibo.oop.cctan.interPackageComunication.LoadedFilesImpl;
import it.unibo.oop.cctan.view.View.Component;

/**
 * A class created to allow files access and modification. 
 * This class is package protected.
 */
class FileLoader extends Thread {

    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final String PATH = System.getProperty("user.home") + "/.cctan";
    private static final String DIRECTORY_IMG = "/img";
    private static final String DIRECTORY_SCORE = "/score";
    private static final String SCORE_FILE_SCORES = "/Scores";
    private static final String IMG_JPG_LOGO = "/cctan.jpg";
    private static final String IMG_SVG_LOGO = "/cctan.svg";
    private static final String FONT_SUBSPACE = FileLoader.class
                                                          .getResource("/subspace_font/SubspaceItalic.otf")
                                                          .getFile();
    private static final int[] PERCENTAGE_ADVANCE = { 10, 40, 50, 80, 100 };
    private static final IntSupplier ADVANCE_PERCENTAGE = () -> {
        int index = 0;
        return index < PERCENTAGE_ADVANCE.length ? PERCENTAGE_ADVANCE[index++] : 100;
    };
    private static final float QUALITY = 1.0f;
    private final Controller controller;
    private final LoadedFiles loadedFiles;
    private int percentage;

    /**
     * FileLoader constructor.
     * 
     * @param controller
     *            A class that implements Controller interface
     */
    FileLoader(final Controller controller) {
        this.controller = controller;
        loadedFiles = new LoadedFilesImpl(0);
    }

    @Override
    /** {@inheritDoc} */
    public void run() {
        // check/create the game directory
        createDirectories(PATH, new String[] { DIRECTORY_IMG, DIRECTORY_SCORE });
        percentage = ADVANCE_PERCENTAGE.getAsInt();
        controller.refreshGui(Component.LOADER);

        // convert svg to jpg. if jpg file already exists will do nothing
        if (Files.notExists(Paths.get(PATH, DIRECTORY_IMG + IMG_JPG_LOGO), LinkOption.NOFOLLOW_LINKS)) {
            loadedFiles.setLogo(new ImageIcon(FileLoader.class.getResource(IMG_JPG_LOGO)));
            controller.refreshGui(Component.LOADER);
            try {
                convertSvgToJpg(FileLoader.class.getResource(IMG_SVG_LOGO).toString(),
                                PATH + DIRECTORY_IMG + IMG_JPG_LOGO);
            } catch (Exception e) {
                System.err.println("Error during svg conversion!");
                e.printStackTrace();
            }
        }
        loadedFiles.setLogo(new ImageIcon(PATH + DIRECTORY_IMG + IMG_JPG_LOGO));
        percentage = ADVANCE_PERCENTAGE.getAsInt();
        controller.refreshGui(Component.LOADER);

        // if (Files.notExists(Paths.get(PATH, DIRECTORY_IMG + IMG_JPG_BACKGROUND),
        // LinkOption.NOFOLLOW_LINKS)) {
        // loadedFiles.setBackground(new
        // ImageIcon(FileLoader.class.getResource(IMG_JPG_BACKGROUND)));
        // controller.refreshGui(Component.LOADER);
        // }
        // loadedFiles.setBackground(new ImageIcon(PATH + DIRECTORY_IMG +
        // IMG_JPG_BACKGROUND));
        // //Files.copy(new InputStreamImageInputStreamSpi()., target, options)
        // percentage = 50;
        // controller.refreshGui(Component.LOADER);

        //Create score file into .cctan folder
        if (Files.notExists(Paths.get(PATH, DIRECTORY_SCORE + SCORE_FILE_SCORES), LinkOption.NOFOLLOW_LINKS)) {
            loadedFiles.setScores(new File(SCORE_FILE_SCORES));
            try {
                File file = new File(PATH + DIRECTORY_SCORE + SCORE_FILE_SCORES);
                if (!file.createNewFile()) {
                    System.out.println("File already present at the specified location");
                }
            } catch (IOException e) {
                System.err.println("Error during score file creation!");
                e.printStackTrace();
            }
        }
        loadedFiles.setScores(new File(PATH + DIRECTORY_SCORE + SCORE_FILE_SCORES));
        percentage = ADVANCE_PERCENTAGE.getAsInt();
        controller.refreshGui(Component.LOADER);

        //Load the font file
        loadedFiles.setFontFile(new File(FONT_SUBSPACE));
        percentage = ADVANCE_PERCENTAGE.getAsInt();
        controller.refreshGui(Component.LOADER);
    }

    /**
     * Returns a file containing all the loaded files.
     * This method is package protected.
     * 
     * @return The file containing all the loaded files
     */
    public LoadedFiles getLoadedFiles() {
        loadedFiles.setPercentage(percentage);
        return loadedFiles;
    }

    /**
     * Create directories in path with the names contained in the string array.
     * 
     * @param path
     *            The path in which will be created the directories
     * @param names
     *            The names of the directories to be created
     */
    private void createDirectories(final String path, final String[] names) {
        for (String name : names) {
            if (!new File(path + name).mkdirs() 
                && Files.notExists(Paths.get(path, name), LinkOption.NOFOLLOW_LINKS)) {
                    System.err.println("An error as occurred during " + name + " directory creation!");
            }
        }
    }

    /**
     * Convert an .svg to .jpg.
     * 
     * @param svgUri
     *            The path to the .svg
     * @param jpgUri
     *            The path in which create the .jpg file
     * @throws Exception
     *             Some method throws various exception
     */
    private void convertSvgToJpg(final String svgUri, final String jpgUri) throws Exception {
        // Create a JPEG transcoder
        JPEGTranscoder converter = new JPEGTranscoder();
        converter.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, QUALITY);
        converter.addTranscodingHint(JPEGTranscoder.KEY_WIDTH, getAdaptedWidth(svgUri));
        converter.addTranscodingHint(JPEGTranscoder.KEY_BACKGROUND_COLOR, Color.BLACK);

        // Create the transcoder input and output.
        TranscoderInput input = new TranscoderInput(svgUri);
        OutputStream ostream = new FileOutputStream(jpgUri);
        TranscoderOutput output = new TranscoderOutput(ostream);

        // Save the image.
        converter.transcode(input, output);

        // Flush and close the stream.
        ostream.flush();
        ostream.close();
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
    private float getAdaptedWidth(final String svgUri) throws JDOMException, IOException {
        final SAXBuilder builder = new SAXBuilder();
        Document document = builder.build(svgUri);

        Element root = document.getRootElement();
        float svgRateo = Float.valueOf(root.getAttributeValue("width"))
                                       / Float.valueOf(root.getAttributeValue("height"));
        double deltaX = SCREEN_SIZE.getWidth() - Double.valueOf(root.getAttributeValue("width"));
        double deltaY = SCREEN_SIZE.getHeight() - Double.valueOf(root.getAttributeValue("height"));
        return deltaX < deltaY ? svgRateo * SCREEN_SIZE.height : SCREEN_SIZE.width;
    }

}
