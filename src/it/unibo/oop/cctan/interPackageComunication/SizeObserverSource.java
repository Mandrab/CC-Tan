package it.unibo.oop.cctan.interPackageComunication;

import java.awt.Dimension;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

/**
 * An interface that specifies the method that a SizeObserverSource class must
 * implement.
 */
public interface SizeObserverSource {

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

    Optional<Dimension> getDimension();

    Optional<Pair<Integer, Integer>> getRatio();

}
