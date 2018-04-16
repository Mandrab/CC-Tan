package it.unibo.oop.cctan.view;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Optional;
import org.apache.commons.lang3.tuple.Pair;

public class ViewImpl implements View {

    private Optional<GameWindow> gameWindow = Optional.empty();
    private Pair<Integer, Integer> screenRatio;

    public ViewImpl() {
        show(new Dimension(500, 500));
    }

    public void show(Dimension gameWindowSize) {
        if (!gameWindow.isPresent()) {
            gameWindow = Optional.of(new GameWindow(this, gameWindowSize));
        }
        gameWindow.get().show();
    }

    @Override
    public Dimension getDimension() {
        // return gameWindowSize;
        return null;
    }

    @Override
    public Point getWindowLocation() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setMouseRelativePosition(Point point) {
        // TODO Auto-generated method stub

    }

    @Override
    public void addCommandsObserver(CommandsObserver commandsObserver) {
        // TODO Auto-generated method stub

    }

}
