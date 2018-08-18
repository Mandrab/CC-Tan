package it.unibo.oop.cctan.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.JPanel;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import it.unibo.oop.cctan.interpackage_comunication.GameStatus;
import it.unibo.oop.cctan.interpackage_comunication.MappableData;
import it.unibo.oop.cctan.interpackage_comunication.MappableDataImpl;
import it.unibo.oop.cctan.interpackage_comunication.ModelData;

class GraphicPanel extends JPanel {

    private static final long serialVersionUID = 7947210167853025169L;
    private static final float DEFAULT_FONT_SIZE = Toolkit.getDefaultToolkit().getScreenSize().width / 35;
    private static final int OPACITY_VALUE = 127; // [0 - 127]; 0 = transparent, 255 = normal
    private static final Pair<Double, Double> SCORE_POSITION_ON_SCREEN = new ImmutablePair<Double, Double>(0d, 0.9);
    private static final Pair<Double, Double> COMMANDS_TEXT_POSITION_ON_SCREEN = new ImmutablePair<Double, Double>(0d, 0d);
    private static final double EDGE_MULTIPLIER = 1.05;
    private final Drawer drawer;
    private Optional<Dimension> dimension;
    private Optional<ModelData> modelData;

    GraphicPanel(final File file) {
        super();
        drawer = new Drawer(file);
        modelData = Optional.empty();
    }

    public void update(final Dimension gameWindowSize, final Pair<Integer, Integer> screenRatio) {
        if (gameWindowSize == null || screenRatio == null) {
            throw new IllegalArgumentException();
        }
        dimension = Optional.of(gameWindowSize);
        setPreferredSize(gameWindowSize);
        setSize(gameWindowSize);
        drawer.update(gameWindowSize, screenRatio);
    }

    public void refresh(final ModelData modelData) {
        if (modelData == null) {
            throw new IllegalArgumentException();
        }
        synchronized (this) {
            this.modelData = Optional.of(modelData);
        }
        repaint();
    }

    @Override
    /** {@inheritDoc} */
    public void paint(final Graphics graphics) {
        if (dimension.isPresent()) {
            graphics.setColor(Color.BLACK);
            graphics.fillRect(0, 0, (int) (dimension.get().width * EDGE_MULTIPLIER), (int) (dimension.get().height * EDGE_MULTIPLIER));
            drawer.setGraphics(graphics);
            modelData.ifPresent(modelData -> {
                synchronized (this) {
                    if (modelData.getGameStatus() == GameStatus.RUNNING) {
                        modelData.getMappableDatas()
                                 .forEach(drawer::drawMappableData);
                        drawer.drawText(Integer.toString(modelData.getScore()), 
                                        SCORE_POSITION_ON_SCREEN, 
                                        DEFAULT_FONT_SIZE, 
                                        Color.WHITE);
                    } else {
                        opacifies(modelData.getMappableDatas())
                                           .forEach(drawer::drawMappableData);
                        drawer.drawText(Integer.toString(modelData.getScore()), 
                                        SCORE_POSITION_ON_SCREEN, 
                                        DEFAULT_FONT_SIZE, 
                                        new Color(Color.WHITE.getRed(), Color.WHITE.getGreen(), Color.WHITE.getBlue(), OPACITY_VALUE));
                        drawer.drawText(modelData.getGameStatus().name(), 
                                        COMMANDS_TEXT_POSITION_ON_SCREEN, 
                                        DEFAULT_FONT_SIZE * 2, 
                                        Color.RED);
                    }
                }
            });
        }
    }

    private List<MappableData> opacifies(final List<MappableData> mappableDatas) {
        return mappableDatas.stream().map(e -> new MappableDataImpl(e.getText(),
                new Color(e.getColor().getRed(), e.getColor().getGreen(), e.getColor().getBlue(), OPACITY_VALUE),
                e.getShape())).collect(Collectors.toList());
    }

}
