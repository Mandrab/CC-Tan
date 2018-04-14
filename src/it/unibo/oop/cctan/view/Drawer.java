package it.unibo.oop.cctan.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import org.apache.commons.lang3.tuple.Pair;

class Drawer {

    private Graphics2D graphics;
    private final AffineTransform transformation;

    public Drawer(Dimension dim, Pair<Integer, Integer> ratio) {
        transformation = new AffineTransform();
        transformation.scale(dim.width * (ratio.getValue().doubleValue())
                             / (2 * ratio.getKey().doubleValue()), 
                             -dim.height * (ratio.getKey().doubleValue())
                             / (2 * ratio.getValue().doubleValue()));
        transformation.translate(ratio.getKey().doubleValue() / ratio.getValue().doubleValue(), 
                                 -ratio.getValue().doubleValue() / ratio.getKey().doubleValue());
    }

    public synchronized void draw(/*cosa disegnare (SHAPE)*/) {
    }

    void setGraphics(final Graphics graphics) {
        this.graphics = (Graphics2D) graphics;
    }

}
