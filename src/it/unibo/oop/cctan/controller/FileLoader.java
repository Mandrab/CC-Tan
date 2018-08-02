package it.unibo.oop.cctan.controller;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.text.AttributedCharacterIterator;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;

public class FileLoader extends Thread {

    private Controller controller;
    
    public FileLoader(Controller controller) {
        this.controller = controller;
    }
    
    @Override
    public void run() {
        
        //check/create the game directory
        new File(System.getProperty("user.home") + "/.cctan/img").mkdirs();
        new File(System.getProperty("user.home") + "/.cctan/score").mkdirs();
        controller.advanceLoading(10);
        
        String path = System.getProperty("user.home") + "/.cctan/";
        
        //convert svg to jpg. if jpg file already exists will do nothing
        if (Files.notExists(Paths.get(path, "img/cctan.jpg"), LinkOption.NOFOLLOW_LINKS)) {
            controller.setLoadImage(new ImageIcon(FileLoader.class.getResource("/cctan.jpg")));
            try {
                convertSvgToJpg(FileLoader.class.getResource("/cctan.svg").toString(), 
                                path + "img/cctan.jpg");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        controller.setLoadImage(new ImageIcon(path + "img/cctan.jpg"));
        controller.advanceLoading(40);        
    }
    
    private void convertSvgToJpg(String svgUri, String jpgUri) throws FileNotFoundException, 
                                                                        TranscoderException, 
                                                                        IOException {
        /*//Step -1: We read the input SVG document into Transcoder Input
        TranscoderInput input_svg_image = new TranscoderInput(svg_URI);
        //Step-2: Define OutputStream to JPG file and attach to TranscoderOutput
        OutputStream jpgOutputStream = new FileOutputStream(jpg_URI);
        TranscoderOutput jpgOutputImage = new TranscoderOutput(jpgOutputStream);
        // Step-3: Create JPEGTranscoder and define hints
        JPEGTranscoder jpgConverter = new JPEGTranscoder();
        //jpgConverter.addTranscodingHint(JPEGTranscoder.KEY_HEIGHT, 1000/*calculateAdjustedSize()*///);
        //jpgConverter.addTranscodingHint(JPEGTranscoder.KEY_WIDTH, );
        // Step-4: Write output
        /*jpgConverter.transcode(input_svg_image, jpgOutputImage);
        jpgOutputStream.flush();
        jpgOutputStream.close(); */
        
        // Create a JPEG transcoder
        JPEGTranscoder t = new JPEGTranscoder();

        // Set the transcoding hints.
        t.addTranscodingHint(JPEGTranscoder.KEY_QUALITY,
                   new Float(.8));

        // Create the transcoder input.
        TranscoderInput input = new TranscoderInput(svgUri);

        // Create the transcoder output.
        //OutputStream ostream = new FileOutputStream(jpgUri);
        //TranscoderOutput output = new TranscoderOutput(ostream);

        BufferedImage bufferedImage = null;
        System.out.println(bufferedImage);
        bufferedImage = t.createImage(400, 400);
        //System.out.println(bufferedImage);
        //System.out.println(jp.getGraphics());
        //jp.getGraphics().drawImage(bufferedImage, 0, 0, null);
        
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ImageIO.write(bufferedImage, "jpg", new File(jpgUri));
        // Save the image.
        //TranscoderOutput output = new TranscoderOutput(jpgUri);
        //t.transcode(input, output);

        // Flush and close the stream.
        //ostream.flush();
        //ostream.close();
        //System.exit(0);
    }
    
    

    /*private Dimension calculateAdjustedSize() {
        Dimension d = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        double ratio = d.getWidth() / d.getHeight();
        if (ratio > 0) {        //x bigger than y
            
        }
    }*/
    
    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(200, 200);
        frame.setVisible(true);

        //final BufferedImage image = pan.getimage();
        System.err.println(FileLoader.class.getResource("/cctan.jpg").toString());
        System.err.println(new File(FileLoader.class.getResource("/cctan.jpg").toString()));
        File file = new File(FileLoader.class.getResource("/cctan.jpg").toString());
        if(!file.exists()) {
            System.err.println("wtf esiste");
        }
        if(!file.canRead()) {
            System.err.println("wtf");
        }
        final BufferedImage image = ImageIO.read(file);
        
        JPanel pane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, null);
            }
        };


        frame.add(pane);
    }
}

class pan extends JPanel {

    public static BufferedImage getimage() {
        String svgUri = FileLoader.class.getResource("/cctan.svg").toString();
        String jpgUri = System.getProperty("user.home") + "/.cctan/" + "img/cctan.jpg";
     // Create a JPEG transcoder
        JPEGTranscoder t = new JPEGTranscoder();

        // Set the transcoding hints.
        t.addTranscodingHint(JPEGTranscoder.KEY_QUALITY,
                   new Float(.8));

        // Create the transcoder input.
        TranscoderInput input = new TranscoderInput(svgUri);

        // Create the transcoder output.
        //OutputStream ostream = new FileOutputStream(jpgUri);
        //TranscoderOutput output = new TranscoderOutput(ostream);
        return t.createImage(400, 400);
        /*BufferedImage bufferedImage = t.createImage(400, 400);
        g.drawImage(bufferedImage, 0, 0, null);
        //g.drawImage(new Buffe, 0, 0, null);
        
        /*try {
            ImageIO.write(bufferedImage, "jpg", new File(jpgUri));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        //super.paint(g);
    }
    
    
}
