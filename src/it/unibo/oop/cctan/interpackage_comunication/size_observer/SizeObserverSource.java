package it.unibo.oop.cctan.interpackage_comunication.size_observer;

import java.awt.Dimension;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.oop.cctan.interpackage_comunication.ObserverSource;

/**
 * An interface that specifies the method that a SizeObserverSource class must
 * implement.
 */
public interface SizeObserverSource extends ObserverSource<SizeObserver> {

    /**
     * Return, if present, the set dimension.
     * 
     * @return The set dimension
     */
    Optional<Dimension> getDimension();

    /**
     * Return, if present, the set ratio.
     * 
     * @return The set ratio
     */
    Optional<Pair<Integer, Integer>> getRatio();

}
