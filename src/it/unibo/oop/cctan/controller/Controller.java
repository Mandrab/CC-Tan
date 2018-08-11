package it.unibo.oop.cctan.controller;

import java.io.File;
import java.util.List;

import javax.swing.ImageIcon;

import it.unibo.oop.cctan.interPackageComunication.LoadedFiles;
import it.unibo.oop.cctan.interPackageComunication.MappableData;
import it.unibo.oop.cctan.view.View;

/**
 * Interface that allow the communication between controller and view.
 */
public interface Controller {

    /**
     * Return a list of data that as to be mapped.
     * 
     * @return The list of the MappableData
     */
    List<MappableData> getListOfMappableData();

    /**
     * Pass the degrees of the mouse relatively at the center of the game window to the model.
     * 
     * @param A double representing the position of the mouse relatively to the
     *         center of the window [center-right = 0, top-center = 90, ...]
     */
    void setMouseRelativePosition(double angle);

    int getScore();

    void advanceLoading(int value);

    void setView(View v);

    public void setLoadImage(ImageIcon img);

    LoadedFiles getLoadedFiles();

}
