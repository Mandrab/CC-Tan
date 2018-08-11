package it.unibo.oop.cctan.interPackageComunication;

import java.io.File;

import javax.swing.ImageIcon;

public interface LoadedFiles {

    enum ImageReturn {

        LOGO,

        BACKGROUND;

    }

    File getFontFile();

    ImageIcon getImage(ImageReturn type);
    
}
