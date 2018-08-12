package it.unibo.oop.cctan.view;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

public abstract class SizeObserversSourceImpl implements SizeObserverSource {

    private List<SizeObserver> sizesObservers;

    public SizeObserversSourceImpl() {
        sizesObservers = new ArrayList<SizeObserver>();
    }

    @Override
    /** {@inheritDoc} */
    public void addSizeObserver(final SizeObserver sizeObserver) {
        sizesObservers.add(sizeObserver);
    }

    @Override
    /** {@inheritDoc} */
    public void removeSizeObserver(final SizeObserver sizeObserver) {
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
     * @return A defencive copy of list of SizeObserver.
     */
    public List<SizeObserver> getSizeObservers() {
        return Collections.unmodifiableList(this.sizesObservers);
    }

}
