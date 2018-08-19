package it.unibo.oop.cctan.interpackage_comunication;

import java.awt.Font;
import java.io.File;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.swing.ImageIcon;

/**
 * A class that implements LoadedFiles. This is a Singleton class.
 */
public final class LoadedFilesSingleton implements LoadedFiles {

    private final Set<LoadObserver> loadObservers;
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
        loadObservers = new HashSet<>();
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
        notifyObservers();
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
        notifyObservers();
    }

    @Override
    public void setFont(final Font fontFile) {
        this.fontFile = Optional.of(fontFile);
        notifyObservers();
    }

    @Override
    public void setScoresFile(final File file) {
        this.scoreFile = Optional.of(file);
        notifyObservers();
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

    private synchronized void notifyObservers() {
        loadObservers.forEach(o -> o.update());
    }

    @Override
    public synchronized void addObserver(final LoadObserver loadObserver) {
        loadObservers.add(loadObserver);
    }

    @Override
    public synchronized void removeObserver(final LoadObserver loadObserver) {
        loadObservers.remove(loadObserver);
    }

}
