package it.unibo.oop.cctan.view;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.ImageIcon;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import it.unibo.oop.cctan.controller.FileLoader;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserver;
import it.unibo.oop.cctan.interPackageComunication.MappableData;

public class LoaderJTest {

    Loader l = new Loader();
    
    @Test
    public void visualLoad() {
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

            @Override
            public void setMouseRelativePosition(double mouseRelativePosition) {}

            @Override
            public Dimension getGameWindowDimension() {
                return null;
            }
        };
        
        
        try {
            v.advanceLoading(0);
            Thread.sleep(1500);
            v.setLoadImage(new ImageIcon(FileLoader.class.getResource("/cc-tan.jpg")));
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
    
    @Test
    public void directoryCreation() {
        String p = System.getProperty("user.home");
        System.out.println(p);
        new File(p + "/.dir_prog_oop").mkdirs();
        assertTrue(Files.exists(Paths.get(p + "/.dir_prog_oop")));
        new File(p + "/.dir_prog_oop/inner_dir").mkdirs();
        assertTrue(Files.exists(Paths.get(p + "/.dir_prog_oop", "/inner_dir")));
        new File(p + "/.dir_prog_oop").mkdirs();
        assertTrue(Files.exists(Paths.get(p + "/.dir_prog_oop", "/inner_dir")));
        new File(p + "/.dir_prog_oop/dsfs/sdfs/sds").mkdirs();
        assertTrue(Files.exists(Paths.get(p + "/.dir_prog_oop/dsfs/sdfs/sds")));
        try {
            FileUtils.deleteDirectory(new File(p + "/.dir_prog_oop"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
