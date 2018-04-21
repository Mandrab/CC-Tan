package it.unibo.oop.cctan.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.oop.cctan.interPackageComunication.MappableData;;

/**
 * Class used to draw shapes on a specific Graphics.
 */
class Drawer {

    private Graphics2D graphics;
    private final AffineTransform transformation;

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
        transformation = new AffineTransform();
        transformation.scale(
                gameWindowDim.width * (ratio.getValue().doubleValue()) / (2 * ratio.getKey().doubleValue()),
                -gameWindowDim.height * (ratio.getKey().doubleValue()) / (2 * ratio.getValue().doubleValue()));
        transformation.translate(ratio.getKey().doubleValue() / ratio.getValue().doubleValue(),
                -ratio.getValue().doubleValue() / ratio.getKey().doubleValue());
    }

    /**
     * Draw a specific "MappableData" on the GraphicPanel.
     * 
     * @param mappableData
     *            The MappableData to draw
     */
    synchronized void draw(final MappableData mappableData) {
        graphics.setColor(mappableData.getColor());
        Shape shape = transformation.createTransformedShape(mappableData.getShape());
        graphics.draw(shape);
        double diff = shape.getBounds2D().getWidth() - graphics.getFontMetrics().stringWidth(mappableData.getText());
        graphics.drawString(mappableData.getText(), (int) (shape.getBounds2D().getX() + diff / 2),
                (int) shape.getBounds2D().getCenterY());
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
    }

}
