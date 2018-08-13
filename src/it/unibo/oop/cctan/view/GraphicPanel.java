package it.unibo.oop.cctan.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.JPanel;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import it.unibo.oop.cctan.interPackageComunication.MappableData;
import it.unibo.oop.cctan.interPackageComunication.MappableDataImpl;
import it.unibo.oop.cctan.interPackageComunication.ModelData;
import it.unibo.oop.cctan.interPackageComunication.GameStatus;

class GraphicPanel extends JPanel {

    private static final long serialVersionUID = 7947210167853025169L;
    private static final int OPACITY_VALUE = 127; // [0 - 127]; 0 = transparent, 255 = normal
    private Drawer drawer;
    private Optional<Dimension> dimension;
    private List<MappableData> mappableDatas;
    private int score;

    GraphicPanel(final File file) {
        mappableDatas = new LinkedList<>();
        score = 0;

        drawer = new Drawer(file);
    }

    public void update(final Dimension gameWindowSize, final Pair<Integer, Integer> screenRatio) {
        if (gameWindowSize == null || screenRatio == null) {
            throw new IllegalArgumentException();
        }
        dimension = Optional.of(gameWindowSize);
        setSize(gameWindowSize);
        setPreferredSize(gameWindowSize);
        drawer.update(gameWindowSize, screenRatio);
    }

    public void paint(final Graphics graphics) {
        if (dimension.isPresent()) {
            graphics.setColor(Color.BLACK);
            graphics.fillRect(0, 0, (int) (dimension.get().width * 1.1), (int) (dimension.get().height * 1.1));
            drawer.setGraphics(graphics);
            synchronized (this) {
                mappableDatas.forEach(drawer::draw);
            }
            drawer.drawText(new ImmutablePair<Double, Double>(0.5, 0.9), Color.WHITE, score + "");
        }
    }

    void redraw(final List<MappableData> mappableDatas, final int score) {
        synchronized (this) {
            this.mappableDatas = mappableDatas;
        }
        this.score = score;
        repaint();
    }

    public void refresh(ModelData modelData) {
        if (modelData.getGameStatus() == GameStatus.RUNNING) {
            redraw(modelData.getMappableDatas(), modelData.getScore());
        } else {
            redraw(addPrintableText(modelData, modelData.getGameStatus().name() + "!" + System.lineSeparator() + modelData.getScore()), modelData.getScore());
        }
    }

    /**
     * Modifies the mappable data list to show a centered message. The other object
     * will become opacities.
     * 
     * @param text
     *            The message to be printed.
     * @return The new list that now include the message
     */
    private List<MappableData> addPrintableText(final ModelData modelData, final String text) {
        List<MappableData> l = modelData.getMappableDatas().stream().map(e -> new MappableDataImpl(e.getText(),
                new Color(e.getColor().getRed(), e.getColor().getGreen(), e.getColor().getBlue(), OPACITY_VALUE),
                e.getShape())).collect(Collectors.toList());
        l.add(new MappableDataImpl(text, Color.RED, new Rectangle2D.Double(-1, -1, 2d, 2d)));
        return l;
    }

}
