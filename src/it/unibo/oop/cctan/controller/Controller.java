package it.unibo.oop.cctan.controller;

import java.util.List;

import javax.swing.ImageIcon;

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

    int getScore();

    void advanceLoading(int value);

    void setView(View v);
    
    public void setLoadImage(ImageIcon img);

}
