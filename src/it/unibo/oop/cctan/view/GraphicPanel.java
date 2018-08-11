package it.unibo.oop.cctan.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.swing.JPanel;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import it.unibo.oop.cctan.interPackageComunication.CommandsObserverSource;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserverChainOfResponsibility;
import it.unibo.oop.cctan.interPackageComunication.MappableData;

class GraphicPanel extends JPanel implements CommandsObserverChainOfResponsibility {

    private static final long serialVersionUID = 7947210167853025169L;
    private GameWindow gameWindow;
    private GraphicPanelUpdater updater;
    private Drawer drawer;
    private Optional<Dimension> dimension;
    private List<MappableData> mappableDatas;
    private int score;
    private Optional<CommandsObserverSource> commandsObserverSource = Optional.empty();
    private Optional<CommandsObserverChainOfResponsibility> commandsObserverTransitiveSource = Optional.empty();

    GraphicPanel(final GameWindow gw, final File file) {
        gameWindow = gw;
        setCommandsSuccessor(gw);
        mappableDatas = new LinkedList<>();
        score = 0;

        drawer = new Drawer(file);

        updater = new GraphicPanelUpdater(this);
        updater.start();
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

    void redraw(final List<MappableData> mappableDatas) {
        synchronized (this) {
            this.mappableDatas = mappableDatas;
        }
        this.score = gameWindow.getScore();
        repaint();
    }

    public List<MappableData> getListOfMappableData() {
        return gameWindow.getListOfMappableData();
    }

    public int getScore() {
        return gameWindow.getScore();
    }

    @Override
    /** {@inheritDoc} */
    public void setCommandsSuccessor(final CommandsObserverChainOfResponsibility successor) {
        this.commandsObserverTransitiveSource = successor != null ? Optional.of(successor) : Optional.empty();
    }

    @Override
    /** {@inheritDoc} */
    public void setCommandsObserverSource(final CommandsObserverSource commandsObserverSource) {
        this.commandsObserverSource = commandsObserverSource != null ? Optional.of(commandsObserverSource)
                : Optional.empty();
    }

    @Override
    /** {@inheritDoc} */
    public Optional<CommandsObserverSource> getCommandsObserverSource() {
        return commandsObserverSource.isPresent() ? commandsObserverSource
                : commandsObserverTransitiveSource.isPresent()
                        ? commandsObserverTransitiveSource.get().getCommandsObserverSource()
                        : Optional.empty();
    }

}
