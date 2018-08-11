package it.unibo.oop.cctan.view;

import java.util.Optional;

/**
 * An interface that specifies which method must have a class that has to act as
 * a link between the SizeObserverSource and the SizeObservers.
 */
interface SizeObserverChainOfResponsibility {

    /**
     * Sets the SizeObserverTransitiveSource to be called if this class
     * implementation do not directly contain the SizeObserverSource.
     * 
     * @param successor
     *            The SizeObserverTransitiveSource
     */
    void setSizeSuccessor(SizeObserverChainOfResponsibility successor);

    /**
     * Sets the SizeObserverSource to be returned from the
     * getSizeObserverSource method.
     * 
     * @param sizeObserverSource
     *            The SizeObserverSource
     */
    void setSizeObserverSource(SizeObserverSource sizeObserverSource);

    /**
     * Returns the actual source for size as optional. If the
     * SizeObserverSource has not yet been instantiated, then return an
     * Optional.empty().
     * 
     * @return The size source. Optional.empty() if not jet instantiated
     */
    Optional<SizeObserverSource> getSizeObserverSource();

}
