package it.unibo.oop.cctan.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.io.File;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.oop.cctan.interPackageComunication.MappableData;;

/**
 * Class used to draw shapes on a specific Graphics.
 */
class Drawer {

    private Graphics2D graphics;
    private Font font;
    private Dimension gameWindowDim;
    private final AffineTransform aTransformation;

    /**
     * The constructor of Drawer class.
     * 
     * @param gameWindowDim
     *            The dimension of the window of the game (eg: 320x240, 640x480,
     *            1024x768,...);
     * @param ratio
     *            The ratio of the window of the game (eg: 1:1, 4:3, 16:9,...)
     */
    Drawer(final Dimension gameWindowDim, final Pair<Integer, Integer> ratio) {
        this.gameWindowDim = gameWindowDim;
        aTransformation = new AffineTransform();
        aTransformation.scale(
                        gameWindowDim.width * (ratio.getValue().doubleValue()) / (2 * ratio.getKey().doubleValue()),
                        -gameWindowDim.height * (ratio.getKey().doubleValue()) / (2 * ratio.getValue().doubleValue()));
        aTransformation.translate(ratio.getKey().doubleValue() / ratio.getValue().doubleValue(),
                                       -ratio.getValue().doubleValue() / ratio.getKey().doubleValue());
        try {//SPOSTA IN CONTROLLER????
            font = Font.createFont(Font.TRUETYPE_FONT, new File(Drawer.class.getResource("/subspace_font/SubspaceItalic.otf").getFile()));
            font = font.deriveFont(Font.BOLD, 30);
        } catch (Exception e) {
            font = new Font("Sans-Serif", Font.BOLD, 60);
        }
    }

    /**
     * Draw a specific "MappableData" on the GraphicPanel.
     * 
     * @param mappableData
     *            The MappableData to draw
     */
    synchronized void draw(final MappableData mappableData) {
        graphics.setColor(mappableData.getColor());
        Shape shape = aTransformation.createTransformedShape(mappableData.getShape());
        graphics.draw(shape);
        drawString(mappableData.getText(),
                   new Point((int) (shape.getBounds2D().getCenterX()), 
                             (int) (shape.getBounds2D().getCenterY())));
        //double diff = shape.getBounds2D().getWidth() - graphics.getFontMetrics().stringWidth(mappableData.getText());
        //graphics.drawString(mappableData.getText(), 
          //                  (int) (shape.getBounds2D().getX() + diff / 2),
            //                (int) shape.getBounds2D().getCenterY());
    }
    
    private void drawString(String text, Point textCenter) {
        String[] strings = text.split("\n", -1);
        int lineHeight = graphics.getFontMetrics().getAscent() + graphics.getFontMetrics().getDescent();
        int textHeight = lineHeight * strings.length;
        int yStartingPoint = (int) (textCenter.getY() - (textHeight / 2));
        for (int index = 0; index < strings.length; index++) {
            String string = strings[index];
            graphics.drawString(string, 
                                (int) (textCenter.getX() - graphics.getFontMetrics().stringWidth(string) / 2),
                                yStartingPoint + (1+index) * lineHeight);
        }
    }

    void drawText(final Pair<Double, Double> screenPositionOnPercentage, final Color color, final String text) {
        graphics.setColor(color);
        graphics.drawString(text, 
                            (float) (screenPositionOnPercentage.getKey() * gameWindowDim.getWidth() - graphics.getFontMetrics().stringWidth(text) / 2), 
                            (float) (screenPositionOnPercentage.getValue() * gameWindowDim.getHeight() / 10));
    }

    /**
     * Set the graphic class used by the GraphicPanel to let the drawer to draw on
     * it.
     * 
     * @param graphics
     *            The graphic class used by GraphicPanel
     */
    void setGraphics(final Graphics graphics) {
        this.graphics = (Graphics2D) graphics;
        this.graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        this.graphics.setFont(font);
    }

}
