package it.unibo.oop.cctan.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A class created to handle the sizeObservers list.
 * @author Sutera Lorenzo
 */
public class SizeObserversManager implements SizeObserverSource {

    private List<SizeObserver> sizesObservers;

    /**
     * The constructor of SizeObserverManager class.
     */
    public SizeObserversManager() {
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

    /**
     * Get the copy of list of SizeObserver.
     * 
     * @return A defencive copy of list of SizeObserver.
     */
    public List<SizeObserver> getSizeObservers() {
        return Collections.unmodifiableList(this.sizesObservers);
    }

}
