package it.unibo.oop.cctan.interPackageComunication;

import java.io.File;

import javax.swing.ImageIcon;

public class LoadedFilesImpl implements LoadedFiles {

    private ImageIcon background;
    private ImageIcon logo;
    private File fontFile;

    public LoadedFilesImpl(final File fontFile, final ImageIcon logo, final ImageIcon background) {
        this.fontFile = fontFile;
        this.logo = logo;
        this.background = background;
    }

    @Override
    public File getFontFile() {
        return fontFile;
    }

    @Override
    public ImageIcon getImage(final ImageReturn type) {
        switch (type) {
            case LOGO:
                return logo;
            case BACKGROUND:
                return background;
            default:
                return null;
        }

    }

}
