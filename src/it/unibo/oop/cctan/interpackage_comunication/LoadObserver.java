package it.unibo.oop.cctan.interpackage_comunication;

/**
 * An interface that specifies which method must have a class that needs to be
 * informed when a file is loaded. This is an interface of the "Observer"
 * pattern.
 */
public interface LoadObserver {

    /**
     * A method that call when a new file is loaded.
     */
    void update();

}
