package it.unibo.oop.cctan.view;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.util.List;
import java.util.Optional;

import javax.swing.ImageIcon;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import it.unibo.oop.cctan.controller.FileLoader;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserver;
import it.unibo.oop.cctan.interPackageComunication.MappableData;

public class LoaderJTest {

    Loader l = new Loader();
    
    @Test
    public void visualLoad() {
        View v = new ViewJTest();
        
        try {
            v.advanceLoading(0);
            Thread.sleep(1500);
            v.setLoadImage(new ImageIcon(FileLoader.class.getResource("/cctan.jpg")));
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
    
    public class ViewJTest implements View {
        
        @Override
        public void showGameWindow(Dimension resolution, Pair<Integer, Integer> screenRatio) {
        }
        
        @Override
        public void setLoadImage(ImageIcon img) {
            l.setLoadImage(img);
        }
        
        @Override
        public Optional<Point> getWindowLocation() {
            return Optional.empty();
        }
        
        @Override
        public int getScore() {
            return 0;
        }
        
        @Override
        public double getMouseRelativePosition() {
            return 0;
        }
        
        @Override
        public List<MappableData> getListOfMappableData() {
            return null;
        }
        
        @Override
        public Optional<Dimension> getDimension() {
            return Optional.empty();
        }
        
        @Override
        public void advanceLoading(int value) {
            l.advanceLoading(value);
        }
        
        @Override
        public void addSizeObserver(SizeObserver sizeObserver) {
        }
        
        @Override
        public void addCommandsObserver(CommandsObserver commandsObserver) {
        }

        @Override
        public void setMouseRelativePosition(double mouseRelativePosition) {
        }

        @Override
        public Optional<Dimension> getGameWindowDimension() {
            return Optional.empty();
        }

        @Override
        public File getFont() {
            return null;
        }

        @Override
        public void showSettingsWindow() {
            // TODO Auto-generated method stub
            
        }

        @Override
        public List<CommandsObserver> getCommandsObserversList() {
            return null;
        }

        @Override
        public List<SizeObserver> getSizeObserversList() {
            return null;
        }
    };

}
