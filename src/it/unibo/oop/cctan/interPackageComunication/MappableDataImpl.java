package it.unibo.oop.cctan.interPackageComunication;

import java.awt.Color;
import java.awt.Shape;

public class MappableDataImpl implements MappableData {

    private String text;
    private Color color;
    private Shape shape;
    
    public MappableDataImpl(String text, Color color, Shape shape) {
        this.text = text;
        this.color = color;
        this.shape = shape;
    }
    
    @Override
    public String getText() {
        return text;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public Shape getShape() {
        return shape;
    }

}
