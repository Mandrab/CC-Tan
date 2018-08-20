package it.unibo.oop.cctan.controller;

import it.unibo.oop.cctan.interpackage_comunication.data.ModelData;

/**
 * An interface that a class that has to update view must implements. Package protected.
 */
interface ViewUpdater extends Updater {

    /**
     * Return a flat class containing all the useful data to map.
     * 
     * @return A flat class which contain all the useful data
     */
    ModelData getModelData();

}
