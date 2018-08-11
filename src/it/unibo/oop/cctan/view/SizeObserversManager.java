package it.unibo.oop.cctan.view;

import java.util.ArrayList;
import java.util.List;

public class SizeObserversManager implements SizeObserverSource {

    private List<SizeObserver> sizesObservers;

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
     * @return A copy of list of SizeObserver.
     */
    public List<SizeObserver> getSizeObservers() {
        List<SizeObserver> copia = new ArrayList<>();
        copia.addAll(sizesObservers);
        return copia;
    }

}
