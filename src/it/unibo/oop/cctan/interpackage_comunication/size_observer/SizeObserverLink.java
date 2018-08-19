package it.unibo.oop.cctan.interpackage_comunication.size_observer;

import java.util.Optional;

/**
 * An interface that specifies which method must have a class that has to act as
 * a link between the SizeObserverSource and the SizeObservers. This is
 * a particular implementation of the "Chain of Responsibility" pattern in which
 * only the classes that stores the SizeObserverSource have the "authority"
 * for return it.
 */
public interface SizeObserverLink {

    /**
     * Returns the actual source for size as optional. If the
     * SizeObserverSource has not yet been instantiated, then return an
     * Optional.empty().
     * 
     * @return The size source. Optional.empty() if not jet instantiated
     */
    Optional<SizeObserverSource> getSizeObserverSource();

}
