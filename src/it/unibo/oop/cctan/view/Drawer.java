package it.unibo.oop.cctan.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.oop.cctan.interpackage_comunication.MappableData;

/**
 * Class used to draw shapes on a specific Graphics.
 */
class Drawer {

    private static final int DEFAULT_FONT_SIZE = Toolkit.getDefaultToolkit().getScreenSize().width / 35;
    private static final double PERCENTAGE_OF_SHAPE_OCCUPIED_BY_TEXT = 0.55;
    private Graphics2D graphics;
    private Font font;
    private Optional<Dimension> gameWindowSize;
    private AffineTransform aTransformation;

    /**
     * The constructor of Drawer class.
     */
    Drawer(final File fontFile) {
        aTransformation = new AffineTransform();
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            font = font.deriveFont(Font.BOLD, DEFAULT_FONT_SIZE);
        } catch (Exception e) {
            font = new Font("Sans-Serif", Font.BOLD, DEFAULT_FONT_SIZE);
        }
    }

    public synchronized void update(final Dimension gameWindowSize, final Pair<Integer, Integer> screenRatio) {
        if (gameWindowSize == null || screenRatio == null) {
            throw new IllegalArgumentException();
        }
        this.gameWindowSize = Optional.of(gameWindowSize);
        aTransformation = new AffineTransform();
        aTransformation.translate(gameWindowSize.width / 2, gameWindowSize.height / 2);
        aTransformation.scale((gameWindowSize.width * screenRatio.getValue().doubleValue())
                / (2 * screenRatio.getKey().doubleValue()), -gameWindowSize.height / 2);
    }

    /**
     * Draw a specific "MappableData" on the GraphicPanel.
     * 
     * @param mappableData
     *            The MappableData to draw
     */
    protected synchronized void drawMappableData(final MappableData mappableData) {
        graphics.setColor(mappableData.getColor());
        final Shape shape = aTransformation.createTransformedShape(mappableData.getShape());
        graphics.draw(shape);
        drawString(mappableData.getText(),
                new Point((int) (shape.getBounds2D().getCenterX()), (int) (shape.getBounds2D().getCenterY())),
                new Dimension(shape.getBounds().width, shape.getBounds().height));
    }

    private synchronized void drawString(final String text, final Point textCenter, final Dimension border) {
        final String[] strings = text.split("\n", -1);
        graphics.setFont(getAdaptedFont(border, strings));
        final int lineHeight = graphics.getFontMetrics().getAscent() + graphics.getFontMetrics().getDescent();
        final int textHeight = lineHeight * strings.length;
        final int yStartingPoint = (int) (textCenter.getY() - (textHeight / 2));
        for (int index = 0; index < strings.length; index++) {
            final String string = strings[index];
            graphics.drawString(string, (int) (textCenter.getX() - graphics.getFontMetrics().stringWidth(string) / 2),
                    yStartingPoint + (1 + index) * lineHeight);
        }
    }

    private Font getAdaptedFont(final Dimension border, final String... strings) {
        int indexLonger = 0;
        for (int index = 0; index < strings.length; index++) {
            indexLonger = graphics.getFontMetrics().stringWidth(strings[index]) > graphics.getFontMetrics()
                    .stringWidth(strings[indexLonger]) ? index : indexLonger;
        }
        final float xPreferredSize = (float) (border.width * PERCENTAGE_OF_SHAPE_OCCUPIED_BY_TEXT
                / graphics.getFontMetrics().stringWidth(strings[indexLonger]));
        final float yPreferredSize = (float) (border.width * PERCENTAGE_OF_SHAPE_OCCUPIED_BY_TEXT
                / ((graphics.getFontMetrics().getAscent() + graphics.getFontMetrics().getDescent()) * strings.length));
        return font.deriveFont(
                xPreferredSize < yPreferredSize ? (float) Math.ceil(xPreferredSize * graphics.getFont().getSize())
                        : (float) Math.ceil(yPreferredSize * graphics.getFont().getSize()));
    }

    /**
     * Draw a string in a position of the game window. You have to specify which
     * size and color use.
     * 
     * @param text
     *            The string to print
     * @param screenPositionOnPercentage
     *            The center of the string position in a percentage. Eg: 0.5, 1 -> x
     *            = 50% of the game window x and 100% of the game window y
     * @param size
     *            The size of the font to use
     * @param color
     *            The color of the text
     */
    protected synchronized void drawText(final String text, final Pair<Double, Double> screenPositionOnPercentage,
            final float size, final Color color) {
        if (gameWindowSize.isPresent()) {
            graphics.setFont(graphics.getFont().deriveFont(size));
            graphics.setColor(color);
            graphics.drawString(text,
                    (float) ((gameWindowSize.get().width / 2) * (screenPositionOnPercentage.getKey() + 1)
                            - graphics.getFontMetrics().stringWidth(text) / 2),
                    (float) ((gameWindowSize.get().height / 2) * (-screenPositionOnPercentage.getValue() + 1)
                            + graphics.getFontMetrics().getHeight() / 2));
        }
    }

    /**
     * Set the graphic class to draw on.
     * 
     * @param graphics
     *            The graphic class used by GraphicPanel
     */
    protected synchronized void setGraphics(final Graphics graphics) {
        this.graphics = (Graphics2D) graphics;
        this.graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        this.graphics.setFont(font);
    }

}
