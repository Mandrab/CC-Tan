package it.unibo.oop.cctan.view;

import java.awt.Dimension;
import java.awt.Point;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserver;
import it.unibo.oop.cctan.interPackageComunication.MappableData;

public class LoaderJTest {

    @Test
    public void test() {
        View v = new View() {
            
            @Override
            public void showGameWindow(Dimension resolution, Pair<Integer, Integer> screenRatio) {}
            
            @Override
            public Point getWindowLocation() {
                return null;}
            
            @Override
            public int getScore() {
                return 0;}
            
            @Override
            public double getMouseRelativePositionInRange(double lowerBound, double upperBound) {
                return upperBound;}
            
            @Override
            public double getMouseRelativePosition() {
                return 0;}
            
            @Override
            public List<MappableData> getListOfMappableData() {
                return null;}
            
            @Override
            public Dimension getDimension() {
                return null;}
            
            @Override
            public void addSizeObserver(SizeObserver sizeObserver) {}
            
            @Override
            public void addCommandsObserver(CommandsObserver commandsObserver) {}
        };
        Loader l = new Loader(v);
    }

}
