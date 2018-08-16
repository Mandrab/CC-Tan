package it.unibo.oop.cctan.controller;

import it.unibo.oop.cctan.interPackageComunication.CommandsObserver;
import it.unibo.oop.cctan.interPackageComunication.ModelData;
import it.unibo.oop.cctan.interPackageComunication.SizeObserver;
import it.unibo.oop.cctan.view.View;
import it.unibo.oop.cctan.view.View.Component;

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
     * Make a specific component of the view to refresh. The possible component are
     * specified in the Component enumeration.
     * 
     * @param component
     *            The component to refresh
     */
    void refreshGui(Component component);

}
