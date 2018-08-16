package it.unibo.oop.cctan.interPackageComunication;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

/**
 * An abstract class that implements CommandsObserverSource.
 */
public abstract class SizeObserverSourceImpl implements SizeObserverSource {

    private List<SizeObserver> sizesObservers;

    /**
     * Constructor.
     */
    public SizeObserverSourceImpl() {
        sizesObservers = new ArrayList<SizeObserver>();
    }

    @Override
    /** {@inheritDoc} */
    public synchronized void addSizeObserver(final SizeObserver sizeObserver) {
        sizesObservers.add(sizeObserver);
    }

    @Override
    /** {@inheritDoc} */
    public synchronized void removeSizeObserver(final SizeObserver sizeObserver) {
        sizesObservers.remove(sizeObserver);
    }

    @Override
    /** {@inheritDoc} */
    public abstract Optional<Dimension> getDimension();

    @Override
    /** {@inheritDoc} */
    public abstract Optional<Pair<Integer, Integer>> getRatio();

    /**
     * Get the copy of list of SizeObserver.
     * 
     * @return A defensive copy of list of SizeObserver.
     */
    public synchronized List<SizeObserver> getSizeObservers() {
        return new ArrayList<>(sizesObservers);
    }

}
