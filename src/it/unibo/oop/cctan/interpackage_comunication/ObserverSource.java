package it.unibo.oop.cctan.interpackage_comunication;

/**
 * Generic ObserverSource.
 * 
 * @param <T>
 *            The Observer class
 */
public interface ObserverSource<T> {

    /**
     * Allow to add an observer.
     * 
     * @param observer
     *            is a class that implements T interface
     */
    void addObserver(T observer);

    /**
     * Allow to remove an observer.
     * 
     * @param observer
     *            is a class that implements T interface
     */
    void removeObserver(T observer);

}
