package it.unibo.oop.cctan.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import org.apache.commons.lang3.tuple.Pair;

import InterPackageComunication.MappableData;

class Drawer {

    private Graphics2D graphics;
    private final AffineTransform transformation;

    Drawer(final Dimension gameWindowDim, final Pair<Integer, Integer> ratio) {
        transformation = new AffineTransform();
        transformation.scale(gameWindowDim.width * (ratio.getValue().doubleValue())
                             / (2 * ratio.getKey().doubleValue()), 
                             -gameWindowDim.height * (ratio.getKey().doubleValue())
                             / (2 * ratio.getValue().doubleValue()));
        transformation.translate(ratio.getKey().doubleValue() / ratio.getValue().doubleValue(), 
                                 -ratio.getValue().doubleValue() / ratio.getKey().doubleValue());
    }

    synchronized void draw(final MappableData mappableData) {
        graphics.setColor(mappableData.getColor());
        Shape shape = transformation.createTransformedShape(mappableData.getShape());
        graphics.draw(shape);
        graphics.drawString(mappableData.getText(), 
                            (int) shape.getBounds2D().getCenterX(),
                            (int) shape.getBounds2D().getCenterY());
    }

    void setGraphics(final Graphics graphics) {
        this.graphics = (Graphics2D) graphics;
        this.graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                                       RenderingHints.VALUE_ANTIALIAS_ON);
    }

}
