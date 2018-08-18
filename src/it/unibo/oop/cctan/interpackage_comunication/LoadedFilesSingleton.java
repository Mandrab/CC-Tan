package it.unibo.oop.cctan.interpackage_comunication;

import java.awt.Font;
import java.io.File;
import java.util.Optional;

import javax.swing.ImageIcon;

/**
 * A class that implements LoadedFiles. This is a Singleton class.
 */
public final class LoadedFilesSingleton implements LoadedFiles {

    private int maxPercentage;
    private int percentage;
    private Optional<ImageIcon> background;
    private Optional<ImageIcon> logo;
    private Optional<ImageIcon> icon;
    private Optional<Font> fontFile;
    private Optional<File> scoreFile;

    private static class LazyHolder {
        private static final LoadedFiles SINGLETON = new LoadedFilesSingleton();
    }

    private LoadedFilesSingleton() {
        maxPercentage = 0;
        percentage = 0;
        background = Optional.empty();
        logo = Optional.empty();
        icon = Optional.empty();
        fontFile = Optional.empty();
        scoreFile = Optional.empty();
    }

    /**
     * Return the singleton LoadedFiles.
     * @return
     * The singleton
     */
    public static LoadedFiles getLoadedFiles() {
        return LazyHolder.SINGLETON;
    }

    @Override
    public void addLoaderPercentage(final int maxPercentage) {
        this.maxPercentage += maxPercentage;
    }

    @Override
    public void increaseAdvance(final int percentage) {
        this.percentage += percentage;
    }

    @Override
    public int getPercentage() {
        return Math.round(percentage * 100f / maxPercentage);
    }

    @Override
    public boolean isLoaded() {
        return maxPercentage == percentage;
    }

    @Override
    public void setImage(final ImageIcon img, final ImageType type) {
        switch (type) {
            case LOGO:
                this.logo = Optional.of(img);
                break;
            case BACKGROUND:
                this.background = Optional.empty();
                break;
            case ICON:
                this.icon = Optional.of(img);
                break;
            default:
        }
    }

    @Override
    public void setFont(final Font fontFile) {
        this.fontFile = Optional.of(fontFile);
    }

    @Override
    public void setScoresFile(final File file) {
        this.scoreFile = Optional.of(file);

    }

    @Override
    public Optional<Font> getFont() {
        return fontFile;
    }

    @Override
    public Optional<File> getScoresFile() {
        return this.scoreFile;

    }

    @Override
    public Optional<ImageIcon> getImage(final ImageType type) {
        switch (type) {
        case LOGO:
            return logo;
        case ICON:
            return icon;
        case BACKGROUND:
            return background;
        default:
            return Optional.empty();
        }
    }

}
