package it.unibo.oop.cctan.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.oop.cctan.interpackage_comunication.LoadObserver;
import it.unibo.oop.cctan.interpackage_comunication.data.LoadedFilesSingleton;
import it.unibo.oop.cctan.interpackage_comunication.data.MappableData;

/**
 * Class used to draw shapes on a specific Graphics.
 */
class Drawer implements LoadObserver {

    private static final int DEFAULT_MULTIPLIER = 20;
    private static final int DEFAULT_FONT_SIZE = Toolkit.getDefaultToolkit()
                                                            .getScreenSize().height / DEFAULT_MULTIPLIER;
    private static final float PERCENTAGE_OF_SHAPE_OCCUPIED_BY_TEXT = 0.55f;
    private Graphics2D graphics;
    private Font font;
    private int defaultFontSize;
    private Optional<Dimension> gameWindowSize;
    private AffineTransform aTransformation;

    /**
     * The constructor of Drawer class.
     */
    Drawer() {
        defaultFontSize = DEFAULT_FONT_SIZE;
        aTransformation = new AffineTransform();
        font = LoadedFilesSingleton.getLoadedFiles().getFont()
                .orElseGet(() -> new Font("Sans-Serif", Font.BOLD, DEFAULT_FONT_SIZE));
        GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
        font.deriveFont(Font.BOLD, DEFAULT_FONT_SIZE);
    }

    /**
     * Informs of the change of the dimension/ratio of the screen.
     * 
     * @param gameWindowSize
     *            Dimension of the game window (e.g.: 320x240, 640x480,
     *            1024x768,...).
     * @param screenRatio
     *            Ratio of the game window (e.g.: 1:1, 4:3, 16:9,...).
     */
    public synchronized void update(final Dimension gameWindowSize, final Pair<Integer, Integer> screenRatio) {
        if (gameWindowSize == null || screenRatio == null) {
            throw new IllegalArgumentException();
        }
        this.gameWindowSize = Optional.of(gameWindowSize);
        defaultFontSize = gameWindowSize.height / DEFAULT_MULTIPLIER;
        aTransformation = new AffineTransform();
        aTransformation.translate(gameWindowSize.width / 2, gameWindowSize.height / 2);
        aTransformation.scale(gameWindowSize.width * screenRatio.getValue().doubleValue()
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
        drawShapeText(mappableData.getText(), shape);
    }

    /**
     * Draw the text at the center of the surrounding rectangle of the shape.
     * 
     * @param text
     *            The text to be drawn
     * @param shape
     *            The shape in which center the text
     */
    private synchronized void drawShapeText(final String text, final Shape shape) {
        final String[] strings = text.split("\n", -1);
        final int fontSize = graphics.getFont().getSize();
        if (textProtrudes(shape, strings)) {
            graphics.setFont(getAdaptedFont(shape.getBounds().getSize(), strings));
        }
        final int lineHeight = graphics.getFontMetrics().getAscent() + graphics.getFontMetrics().getDescent();
        final int yStartingPoint = (int) Math.round(shape.getBounds().getCenterY() - lineHeight * strings.length / 2);
        IntStream.range(0, strings.length)
                 .forEach(i -> graphics.drawString(strings[i],
                                                   Math.round(shape.getBounds().getCenterX() 
                                                              - graphics.getFontMetrics()
                                                                        .stringWidth(strings[i]) / 2),
                                                   yStartingPoint + (1 + i) * lineHeight));
        graphics.setFont(graphics.getFont().deriveFont((float) fontSize));
    }

    /**
     * Checks if with the actual font the text is inside outsider rectangle of the
     * shape.
     * 
     * @param shape
     *            The shape to test
     * @param strings
     *            The text
     * @return true if the text is greater than the shape, false otherwise
     */
    private boolean textProtrudes(final Shape shape, final String... strings) {
        final int x = Arrays.asList(strings)
                            .stream()
                            .mapToInt(str -> graphics.getFontMetrics().stringWidth(str))
                            .sorted()
                            .max()
                            .orElseGet(() -> 0);
        final int y = (graphics.getFontMetrics().getAscent() 
                      + graphics.getFontMetrics().getDescent()) 
                      * strings.length;
        return !shape.contains(shape.getBounds2D().getCenterX() - x / 2, 
                               shape.getBounds2D().getCenterY() - y / 2, 
                               x, y);
    }

    /**
     * Return a font with adapted size according to the shape size.
     * 
     * @param border
     *            The maximum size of the text
     * @param strings
     *            The text
     * @return The modified font
     */
    private Font getAdaptedFont(final Dimension border, final String... strings) {
        final int longestStringSize = IntStream.range(0, strings.length)
                                               .mapToObj(i -> graphics.getFontMetrics()
                                                                      .stringWidth(strings[i]))
                                               .max((x, y) -> x - y)
                                               .orElseGet(() -> 0);
        final float xPreferredSize = border.width * PERCENTAGE_OF_SHAPE_OCCUPIED_BY_TEXT / longestStringSize;
        final float yPreferredSize = border.width * PERCENTAGE_OF_SHAPE_OCCUPIED_BY_TEXT
                / ((graphics.getFontMetrics().getAscent() + graphics.getFontMetrics().getDescent()) * strings.length);
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
        this.graphics.setFont(font.deriveFont((float) defaultFontSize));
    }

    @Override
    /** {@inheritDoc} */
    public void update() {
        LoadedFilesSingleton.getLoadedFiles().getFont().ifPresent(font -> {
            if (!this.font.getFontName().equals(font.getFontName())) {
                this.font = font;
                GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
                font.deriveFont(Font.BOLD, DEFAULT_FONT_SIZE);
                if (graphics != null) {
                    graphics.setFont(font);
                }
            }
        });
    }

}
