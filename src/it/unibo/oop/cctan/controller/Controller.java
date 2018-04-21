package it.unibo.oop.cctan.controller;

import java.util.List;

import it.unibo.oop.cctan.interPackageComunication.MappableData;

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

}
