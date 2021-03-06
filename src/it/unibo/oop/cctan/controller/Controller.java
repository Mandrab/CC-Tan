package it.unibo.oop.cctan.controller;

import it.unibo.oop.cctan.interpackage_comunication.commands_observer.CommandsObserver;
import it.unibo.oop.cctan.interpackage_comunication.data.ModelData;
import it.unibo.oop.cctan.interpackage_comunication.size_observer.SizeObserver;
import it.unibo.oop.cctan.view.View;

/**
 * Interface that allow the communication between controller and view.
 */
public interface Controller extends CommandsObserver, SizeObserver {

    /**
     * Sets the view class with which the controller will work.
     * 
     * @param v
     *            The class that implements view
     */
    void setView(View v);

    /**
     * Return a flat object containing all the useful data from the file model.
     * 
     * @return A file containing all the useful data from the model
     */
    ModelData getModelData();

    /**
     * Make the view to refresh.
     */
    void refreshGui();

}
