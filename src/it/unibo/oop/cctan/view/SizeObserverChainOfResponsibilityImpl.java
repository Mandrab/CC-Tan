package it.unibo.oop.cctan.view;

import java.util.Optional;

/**
 * An implementation of CommandsObserverTransitiveSource.
 */
abstract class SizeObserverChainOfResponsibilityImpl implements SizeObserverChainOfResponsibility {

    private Optional<SizeObserverSource> sizeObserverSource = Optional.empty();
    private Optional<SizeObserverChainOfResponsibility> successor = Optional.empty();

    @Override
    /** {@inheritDoc} */
    public void setSizeSuccessor(final SizeObserverChainOfResponsibility successor) {
        this.successor = successor != null ? Optional.of(successor) : Optional.empty();
    }

    @Override
    /** {@inheritDoc} */
    public void setSizeObserverSource(final SizeObserverSource sizeObserverSource) {
        this.sizeObserverSource = sizeObserverSource != null ? Optional.of(sizeObserverSource) : Optional.empty();
    }

    @Override
    /** {@inheritDoc} */
    public Optional<SizeObserverSource> getSizeObserverSource() {
        return sizeObserverSource.isPresent() ? sizeObserverSource
                : successor.isPresent() ? successor.get().getSizeObserverSource() : Optional.empty();
    }

}
