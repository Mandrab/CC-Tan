package it.unibo.oop.cctan.controller;

import java.io.File;
import java.util.List;

import javax.swing.ImageIcon;

import it.unibo.oop.cctan.interPackageComunication.CommandsObserver;
import it.unibo.oop.cctan.interPackageComunication.LoadedFiles;
import it.unibo.oop.cctan.interPackageComunication.MappableData;
import it.unibo.oop.cctan.interPackageComunication.ModelData;
import it.unibo.oop.cctan.interPackageComunication.SizeObserver;
import it.unibo.oop.cctan.view.View;
import it.unibo.oop.cctan.view.View.Component;

/**
 * Interface that allow the communication between controller and view.
 */
public interface Controller extends CommandsObserver, SizeObserver {

    /**
     * Pass the degrees of the mouse relatively at the center of the game window to the model.
     * 
     * @param A double representing the position of the mouse relatively to the
     *         center of the window [center-right = 0, top-center = 90, ...]
     */
    void setMouseRelativePosition(double angle);

    void setView(View v);

    LoadedFiles getLoadedFiles();

    ModelData getModelData();

    void refreshGui(Component component);

}
