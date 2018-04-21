package it.unibo.oop.cctan.view;

import java.awt.Dimension;
import java.awt.Point;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import it.unibo.oop.cctan.interPackageComunication.MappableData;
import it.unibo.oop.cctan.controller.Controller;

public class ViewImpl implements View {

    private Controller controller;
    private Optional<GameWindow> gameWindow = Optional.empty();
    private Pair<Integer, Integer> screenRatio;
    //private List<CommandsObserver> commandsObservers;
    //private List<SizeObserver> sizeObervers;

    public ViewImpl(Controller controller) {
        this.controller = controller;
        //commandsObservers = new ArrayList<>();
        //sizeObervers = new ArrayList<>();
        new Loader();
        showGameWindow(new Dimension(500, 500), new ImmutablePair<Integer, Integer>(1, 1));
    }

    public void showGameWindow(Dimension gameWindowSize, Pair<Integer, Integer> screenRatio) {
        if (!gameWindow.isPresent()) {
            gameWindow = Optional.of(new GameWindow(this, gameWindowSize, screenRatio));
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
        //commandsObservers.add(commandsObserver);
    }

    @Override
    public void addSizeObserver(SizeObserver sizeObserver) {
        //sizeObervers.add(sizeObserver);
    }

    @Override
    public List<MappableData> getListOfMappableData() {
        return controller.getListOfMappableData();
    }

}
