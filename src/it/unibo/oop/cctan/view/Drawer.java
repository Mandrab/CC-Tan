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
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.oop.cctan.interPackageComunication.MappableData;;

/**
 * Class used to draw shapes on a specific Graphics.
 */
class Drawer {

    private Graphics2D graphics;
    private Font font;
    private Optional<Dimension> gameWindowSize;
    private Optional<Pair<Integer, Integer>> screenRatio;
    private AffineTransform aTransformation;

    /**
     * The constructor of Drawer class.
     */
    Drawer(final File fontFile) {
        aTransformation = new AffineTransform();
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            font = font.deriveFont(Font.BOLD, 30);
        } catch (Exception e) {
            font = new Font("Sans-Serif", Font.BOLD, 30);
        }
    }

    synchronized public void update(final Dimension gameWindowSize, final Pair<Integer, Integer> screenRatio) {
        if (gameWindowSize == null || screenRatio == null)
            throw new IllegalArgumentException();
        this.gameWindowSize = Optional.of(gameWindowSize);
        aTransformation = new AffineTransform();
        aTransformation.scale(gameWindowSize.width / (2 * screenRatio.getKey()),//(ratio.getValue().doubleValue()) / (2 * ratio.getKey().doubleValue()),
                              -gameWindowSize.height / (2 * screenRatio.getValue()));//* (ratio.getKey().doubleValue()) / (2 * ratio.getValue().doubleValue()));
        aTransformation.translate(screenRatio.getKey().doubleValue(),// / ratio.getValue().doubleValue(),
                                  -screenRatio.getValue().doubleValue());//-ratio.getValue().doubleValue() / ratio.getKey().doubleValue());
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
                             (int) (shape.getBounds2D().getCenterY())),
                   new Dimension(shape.getBounds().width, shape.getBounds().height));
    }

    synchronized void drawString(final String text, final Point textCenter, final Dimension border) {
        graphics.setFont(getAdaptedFont(border));
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

    private Font getAdaptedFont(final Dimension border) {
        return font.deriveFont(border.width > border.height ? border.height/2.5f : border.width/2.5f);
    }

    void drawText(final Pair<Double, Double> screenPositionOnPercentage, final Color color, final String text) {
        if (gameWindowSize.isPresent()) {
            graphics.setColor(color);
            graphics.drawString(text, 
                                (float) (screenPositionOnPercentage.getKey() 
                                         * gameWindowSize.get().getWidth() 
                                         - graphics.getFontMetrics().stringWidth(text) / 2), 
                                (float) (screenPositionOnPercentage.getValue() 
                                         * gameWindowSize.get().getHeight() / 10));
        }
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
