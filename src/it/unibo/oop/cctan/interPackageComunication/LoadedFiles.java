package it.unibo.oop.cctan.interPackageComunication;

import java.io.File;
import java.util.Optional;

import javax.swing.ImageIcon;

public interface LoadedFiles {

    public enum ImageType {

        LOGO,

        ICON,

        BACKGROUND;

    }

    Optional<File> getFontFile();

    Optional<ImageIcon> getImage(ImageType type);

//    public void setBackground(ImageIcon background);

    public void setImage(ImageIcon img, ImageType type);

    public void setFontFile(File fontFile);

    public int getPercentage();

    /**
     * Sets the percentage of advance of loading. If the number is less than 0 or
     * exceeds 100, the change is ignored.
     * 
     * @param percentage
     *            the percentage (es. 1 -> 1%, 40 -> 40%)
     */
    void setPercentage(int percentage);

    void setScores(File file);

    Optional<File> getScores();


}
