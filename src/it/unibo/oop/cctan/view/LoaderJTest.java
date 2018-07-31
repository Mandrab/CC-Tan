package it.unibo.oop.cctan.view;

import java.awt.Dimension;
import java.awt.Point;
import java.util.List;

import javax.swing.ImageIcon;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import it.unibo.oop.cctan.controller.FileLoader;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserver;
import it.unibo.oop.cctan.interPackageComunication.MappableData;

public class LoaderJTest {

    Loader l = new Loader();
    
    @Test
    public void test() {
        View v = new View() {
            
            @Override
            public void showGameWindow(Dimension resolution, Pair<Integer, Integer> screenRatio) {}
            
            @Override
            public void setLoadImage(ImageIcon img) {
                l.setLoadImage(img);
            }
            
            @Override
            public Point getWindowLocation() {
                return null;}
            
            @Override
            public int getScore() {
                return 0;}
            
            @Override
            public double getMouseRelativePositionInRange(double lowerBound, double upperBound) {
                return 0;}
            
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
            public void advanceLoading(int value) {
                l.advanceLoading(value);
            }
            
            @Override
            public void addSizeObserver(SizeObserver sizeObserver) {}
            
            @Override
            public void addCommandsObserver(CommandsObserver commandsObserver) {}
        };
        
        
        try {
            v.advanceLoading(0);
            Thread.sleep(1500);
            v.setLoadImage(new ImageIcon(FileLoader.class.getResource("/Background1.jpg")));
            v.advanceLoading(10);
            Thread.sleep(1500);
            v.advanceLoading(20);
            Thread.sleep(1500);
            v.advanceLoading(30);
            Thread.sleep(1500);
            v.advanceLoading(40);
            Thread.sleep(1500);
            v.advanceLoading(100);
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
