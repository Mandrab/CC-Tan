package it.unibo.oop.cctan.view;

/**
 * An interface that specifies the method that a SizeObserverSource class must
 * implement.
 */
interface SizeObserverSource {

    /**
     * Allow to add a "size" observer.
     * 
     * @param sizeObserver
     *            is a class that implements SizeObserver interface
     */
    void addSizeObserver(SizeObserver sizeObserver);

    /**
     * Allow to remove a "size" observer.
     * 
     * @param sizeObserver
     *            is a class that implements SizeObserver interface
     */
    void removeSizeObserver(SizeObserver sizeObserver);

}
