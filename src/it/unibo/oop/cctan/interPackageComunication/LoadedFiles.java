package it.unibo.oop.cctan.interPackageComunication;

import java.io.File;
import java.util.Optional;

import javax.swing.ImageIcon;

/**
 * An interface that specifies which method must have a class of tipe LoadedFiles.
 */
public interface LoadedFiles {

    /**
     * Enumeration to specify which image attribute set/get.
     */
    enum ImageType {

        /**
         * The game logo image.
         */
        LOGO,

        /**
         * The game icon.
         */
        ICON,

        /**
         * The game background.
         */
        BACKGROUND;

    }

    /**
     * Add the value to set the percentage to reach while advancing.
     * 
     * @param maxPercentage
     *            The max percentage of the loader. Eg: 100%
     */
    void addLoaderPercentage(int maxPercentage);

    /**
     * Increase percentage of advance of loading.
     * 
     * @param percentage
     *            the percentage (eg. actual:10, percentage: 20 -> 30%)
     */
    void increaseAdvance(int percentage);

    /**
     * Get the percentage ratio of loading.
     * 
     * @return The percentage ratio
     */
    int getPercentage();

    /**
     * Inform if all the files has been loaded.
     * 
     * @return true if all is loaded, false otherwise
     */
    boolean isLoaded();

    /**
     * Specifies which image should be saved in which attribute.
     * 
     * @param img
     *            The image to be saved
     * @param type
     *            Specifies the attribute in which save the image (specified in the
     *            enumeration) loaded
     */
    void setImage(ImageIcon img, ImageType type);

    /**
     * Get the image from the attribute specified in the enumeration. Optional
     * return.
     * 
     * @param type
     *            Which image chose
     * @return If yet loaded return an optional filled with the image
     */
    Optional<ImageIcon> getImage(ImageType type);

    /**
     * Set the font file.
     * 
     * @param fontFile
     *            The loaded font file
     */
    void setFontFile(File fontFile);

    /**
     * Get the loaded font file. Optional return.
     * 
     * @return If yet loaded, then return the font file
     */
    Optional<File> getFontFile();

    /**
     * Set the score loaded file.
     * 
     * @param file
     *            The score loaded file
     */
    void setScoresFile(File file);

    /**
     * Get the loaded scores file. Optional operation.
     * 
     * @return The loaded score file
     */
    Optional<File> getScoresFile();

}
