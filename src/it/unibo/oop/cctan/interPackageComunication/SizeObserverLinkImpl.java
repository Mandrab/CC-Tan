package it.unibo.oop.cctan.interPackageComunication;

import java.util.Optional;

/**
 * An implementation of CommandsObserverTransitiveSource.
 */
public abstract class SizeObserverLinkImpl implements SizeObserverLink {

    private Optional<SizeObserverSource> source = Optional.empty();
    private Optional<SizeObserverLink> successor = Optional.empty();

    /**
     * Sets the SizeObserverLink to be called if this class implementation do not
     * directly contain the SizeObserverSource.
     * 
     * @param successor
     *            The SizeObserverTransitiveSource
     */
    public void setSizeSuccessor(final SizeObserverLink successor) {
        this.successor = successor != null ? Optional.of(successor) : Optional.empty();
    }

    /**
     * Sets the SizeObserverSource to be returned from the getSizeObserverSource
     * method.
     * 
     * @param source
     *            The SizeObserverSource
     */
    public void setSizeObserverSource(final SizeObserverSource source) {
        this.source = source != null ? Optional.of(source) : Optional.empty();
    }

    @Override
    /** {@inheritDoc} */
    public Optional<SizeObserverSource> getSizeObserverSource() {
        return source.isPresent() 
               ? source
               : successor.isPresent() 
                   ? successor.get().getSizeObserverSource() 
                   : Optional.empty();
    }

}
