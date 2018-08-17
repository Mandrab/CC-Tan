package it.unibo.oop.cctan.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Dimension;
import java.awt.Point;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import it.unibo.oop.cctan.geometry.Boundary;
import it.unibo.oop.cctan.interPackageComunication.Commands;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserver;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserverLink;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserverSource;
import it.unibo.oop.cctan.interPackageComunication.GameStatus;
import it.unibo.oop.cctan.interPackageComunication.ModelData;
import it.unibo.oop.cctan.interPackageComunication.SizeObserverLink;
import it.unibo.oop.cctan.interPackageComunication.SizeObserverSource;
import it.unibo.oop.cctan.model.Bullet;
import it.unibo.oop.cctan.model.Model;
import it.unibo.oop.cctan.model.PowerUpBlock;
import it.unibo.oop.cctan.model.PowerUpBlockImpl.PowerUpBlockBuilder;
import it.unibo.oop.cctan.model.Score;
import it.unibo.oop.cctan.model.Shuttle;
import it.unibo.oop.cctan.model.SquareAgent;
import it.unibo.oop.cctan.model.generator.ItemGenerator;
import it.unibo.oop.cctan.view.KeyCommandsListener;
import it.unibo.oop.cctan.view.View;

class CommandsJTest {

    private static final String RUNNING_E = "Should not be running";
    private static final String NOT_RUNNING_E = "Should be running";
    private static final String TERMINATED_E = "Should not be terminated";
    private static final String NOT_TERMINATED_E = "Should be terminated";

    @Test
    public void modelUpdaterJTest() {
        final ModelUpdater mu = new ModelUpdater(new ViewJTest(), new ModelJTest(), new CommandsObserverSourceJTest());
        assertFalse(TERMINATED_E, mu.isTerminated());
        mu.start();
        mu.newCommand(Commands.START);
        assertTrue(NOT_RUNNING_E, mu.isRunning());
        assertFalse(TERMINATED_E, mu.isTerminated());
        mu.newCommand(Commands.PAUSE);
        assertFalse(RUNNING_E, mu.isRunning());
        assertFalse(TERMINATED_E, mu.isTerminated());
        mu.newCommand(Commands.RESUME);
        assertTrue(NOT_RUNNING_E, mu.isRunning());
        assertFalse(TERMINATED_E, mu.isTerminated());
        mu.newCommand(Commands.END);
        assertFalse(RUNNING_E, mu.isRunning());
        assertFalse(TERMINATED_E, mu.isTerminated());
        mu.newCommand(Commands.RESUME);
        assertTrue(NOT_RUNNING_E, mu.isRunning());
        assertFalse(TERMINATED_E, mu.isTerminated());
        mu.terminate();
        assertTrue(NOT_TERMINATED_E, mu.isTerminated());
    }

    @Test
    public void viewUpdaterJTest() throws InterruptedException {
        final ViewUpdater vu = new ViewUpdater(new ViewJTest(), new ModelJTest(), new CommandsObserverSourceJTest());
        assertFalse(TERMINATED_E, vu.isTerminated());
        vu.start();
        vu.newCommand(Commands.START);
        assertTrue(NOT_RUNNING_E, vu.isRunning());
        assertFalse(TERMINATED_E, vu.isTerminated());
        vu.newCommand(Commands.PAUSE);
        assertFalse(RUNNING_E, vu.isRunning());
        assertFalse(TERMINATED_E, vu.isTerminated());
        vu.newCommand(Commands.RESUME);
        assertTrue(NOT_RUNNING_E, vu.isRunning());
        assertFalse(TERMINATED_E, vu.isTerminated());
        vu.newCommand(Commands.END);
        assertFalse(RUNNING_E, vu.isRunning());
        assertFalse(TERMINATED_E, vu.isTerminated());
        vu.newCommand(Commands.RESUME);
        assertTrue(NOT_RUNNING_E, vu.isRunning());
        assertFalse(TERMINATED_E, vu.isTerminated());
        vu.terminate();
        assertTrue(NOT_TERMINATED_E, vu.isTerminated());
    }

    /**
     * Skeleton class.
     */
    private class ViewJTest implements View, SizeObserverLink, CommandsObserverLink {

        @Override
        public void showGameWindow(final Dimension resolution, final Pair<Integer, Integer> screenRatio) {
        }

        @Override
        public Optional<Point> getWindowLocation() {
            return Optional.of(new Point(0, 0));
        }

        @Override
        public double getMouseRelativePosition() {
            return 0;
        }

        @Override
        public Optional<Dimension> getGameWindowDimension() {
            return Optional.empty();
        }

        @Override
        public void showSettingsWindow() {
        }

        @Override
        public Optional<String> getPlayerName() {
            return Optional.empty();
        }

        @Override
        public KeyCommandsListener getKeyCommandsListener() {
            return null;
        }

        @Override
        public Optional<CommandsObserverSource> getCommandsObserverSource() {
            return Optional.empty();
        }

        @Override
        public Optional<SizeObserverSource> getSizeObserverSource() {
            return Optional.empty();
        }

        @Override
        public ModelData getModelData() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void hideGameWindow() {
        }

        @Override
        public void refreshGui(final Component component) {
        }

    }

    /**
     * Skeleton class.
     */
    private class ModelJTest implements Model {

        @Override
        public void pause() {
        }

        @Override
        public void terminate() {
        }

        @Override
        public void resumeGame() {
        }

        @Override
        public void launch() {
        }

        @Override
        public void setDisplayRatio(final double ratio) {
        }

        @Override
        public void setSpaceshipAngle(final double angle) {
        }

        @Override
        public void setGameStatus(final GameStatus status) {
        }

        @Override
        public void removeBullet(final Bullet bullet) {
        }

        @Override
        public void removeSquare(final SquareAgent square) {
        }

        @Override
        public void removePowerUp(final PowerUpBlock powerup) {
        }

        @Override
        public Score getScore() {
            return null;
        }

        @Override
        public Boundary getBounds() {
            return null;
        }

        @Override
        public Shuttle getShuttle() {
            return null;
        }

        @Override
        public GameStatus getGameStatus() {
            return null;
        }

        @Override
        public List<Bullet> getBulletAgents() {
            return null;
        }

        @Override
        public List<SquareAgent> getSquareAgents() {
            return null;
        }

        @Override
        public List<PowerUpBlock> getPowerUpBlocks() {
            return null;
        }

        @Override
        public ItemGenerator<Bullet> getBulletGenerator() {
            return null;
        }

        @Override
        public List<PowerUpBlockBuilder<?>> getPowerUpBlockTypes() {
            return null;
        }

    }

    /**
     * Skeleton class.
     */
    private class CommandsObserverSourceJTest implements CommandsObserverSource {

        @Override
        public void addCommandsObserver(final CommandsObserver commandsObserver) {
        }

        @Override
        public void removeCommandsObserver(final CommandsObserver commandsObserver) {
        }

        @Override
        public void forceCommand(final Commands command) {
        }

    }

}
