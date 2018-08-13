package it.unibo.oop.cctan.interPackageComunication;

import java.io.File;
import java.util.Optional;

import javax.swing.ImageIcon;

public class LoadedFilesImpl implements LoadedFiles {

    private int percentage = 0;
    private Optional<ImageIcon> background = Optional.empty();
    private Optional<ImageIcon> logo = Optional.empty();
    private Optional<File> fontFile = Optional.empty();
    private Optional<File> scoreFile = Optional.empty();

    public LoadedFilesImpl(final int percentage) {
        this.percentage = percentage;
    }

//    public void setBackground(ImageIcon background) {
//        this.background = Optional.of(background);
//    }

    public void setLogo(ImageIcon logo) {
        this.logo = Optional.of(logo);
    }

    public void setFontFile(File fontFile) {
        this.fontFile = Optional.of(fontFile);
    }
    
    @Override
    public void setScores(File file) {
        this.scoreFile = Optional.of(file);
        
    }

    @Override
    public Optional<File> getFontFile() {
        return fontFile;
    }

    public int getPercentage() {
        return percentage;
    }
    
    @Override
    public Optional<File> getScores() {
        return this.scoreFile;

    }

    @Override
    public void setPercentage(final int percentage) {
        if (percentage >= 0 && percentage <= 100) {
            this.percentage = percentage;
        }
    }

    @Override
    public Optional<ImageIcon> getImage(final ImageReturn type) {
        switch (type) {
        case LOGO:
            return logo;
        case BACKGROUND:
            return background;
        default:
            return Optional.empty();
        }
    }
}
