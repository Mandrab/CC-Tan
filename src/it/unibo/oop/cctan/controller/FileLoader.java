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
    private final String PATH = System.getProperty("user.home") + "/.cctan/";
    private final static float QUALITY = 1.0f;
    
    public FileLoader(Controller controller) {
        this.controller = controller;
    }
    
    @Override
    public void run() {
        //check/create the game directory
        if (!new File(PATH + "img").mkdirs())
            System.err.println("Error during img folder creation!");
        if (!new File(PATH + "score").mkdirs())
            System.err.println("Error during score folder creation!");
        controller.advanceLoading(10);
        
        //convert svg to jpg. if jpg file already exists will do nothing
        if (Files.notExists(Paths.get(PATH, "img/cctan.jpg"), LinkOption.NOFOLLOW_LINKS)) {
            controller.setLoadImage(new ImageIcon(FileLoader.class.getResource("/cctan.jpg")));
            try {
                convertSvgToJpg(FileLoader.class.getResource("/cctan.svg").toString(), 
                                PATH + "img/cctan.jpg");
            } catch (Exception e) {
                System.err.println("Error during svg conversion!");
                e.printStackTrace();
            }
        }
        controller.setLoadImage(new ImageIcon(PATH + "img/cctan.jpg"));
        controller.advanceLoading(40);        
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
