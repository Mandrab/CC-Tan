package it.unibo.oop.cctan.interpackage_comunication;

/**
 * An interface that specifies the methods that a LoadObserverSource class
 * must implement.
 */
public interface LoadObserverSource {

    /**
     * Allow to add a class to inform at load.
     * 
     * @param loadObserver
     *            is a class that implements LoadObserver interface
     */
    void addLoadObserver(LoadObserver loadObserver);

    /**
     * Allow to remove a LoadObserver.
     * 
     * @param loadObserver
     *            is a class that implements LoadObserver interface
     */
    void removeCommandsObserver(LoadObserver loadObserver);

}
