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

import javax.swing.ImageIcon;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class FileLoader extends Thread {

    private Controller controller;
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static final String PATH = System.getProperty("user.home") + "/.cctan";
    private static final String DIRECTORY_IMG = "/img";
    private static final String DIRECTORY_SCORE = "/score";
    private static final String IMG_JPG_LOGO = "/cctan.jpg";
    private static final String IMG_SVG_LOGO = "/cctan.svg";
    private static final String FONT_SUBSPACE = FileLoader.class.getResource("/subspace_font/SubspaceItalic.otf").getFile();
    private static final float QUALITY = 1.0f;
    private File fontFile;
    
    public FileLoader(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        //check/create the game directory
        createDirectories(PATH, new String[] {DIRECTORY_IMG, DIRECTORY_SCORE});
        controller.advanceLoading(10);
        
        //convert svg to jpg. if jpg file already exists will do nothing
        if (Files.notExists(Paths.get(PATH, DIRECTORY_IMG + IMG_JPG_LOGO), 
                                    LinkOption.NOFOLLOW_LINKS)) {
            controller.setLoadImage(new ImageIcon(FileLoader.class.getResource(IMG_JPG_LOGO)));
            try {
                convertSvgToJpg(FileLoader.class.getResource(IMG_SVG_LOGO).toString(), 
                                PATH + DIRECTORY_IMG + IMG_JPG_LOGO);
            } catch (Exception e) {
                System.err.println("Error during svg conversion!");
                e.printStackTrace();
            }
        }
        controller.setLoadImage(new ImageIcon(PATH + DIRECTORY_IMG + IMG_JPG_LOGO));
        controller.advanceLoading(40);
        
        fontFile = new File(FONT_SUBSPACE);
        
        controller.advanceLoading(100);
    }
    
    public File getFontFile() {
        return fontFile;
    }
    
    private void createDirectories(String path, String[] names) {
        for (String name : names) {
            if(!new File(path + name).mkdirs() 
                    && Files.notExists(Paths.get(path, name), LinkOption.NOFOLLOW_LINKS))
                System.err.println("An error as occurred during " + name + " directory creation!");
        }
    }

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

    private float getAdaptedWidth(String svgUri) throws JDOMException, IOException {
        final SAXBuilder builder = new SAXBuilder();
        Document document = builder.build(svgUri);

        Element root = document.getRootElement();
        float svgRateo = Float.valueOf(root.getAttributeValue("width")) / 
                         Float.valueOf(root.getAttributeValue("height"));
        double deltaX = screenSize.getWidth() - Double.valueOf(root.getAttributeValue("width"));
        double deltaY = screenSize.getHeight() - Double.valueOf(root.getAttributeValue("height"));
        return deltaX < deltaY ? svgRateo * screenSize.height : screenSize.width;
    }

}
