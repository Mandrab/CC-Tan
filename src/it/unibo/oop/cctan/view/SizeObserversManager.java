package it.unibo.oop.cctan.view;

import java.util.ArrayList;
import java.util.List;

public class SizeObserversManager {

    private List<SizeObserver> sizesObservers;

    public SizeObserversManager() {
        sizesObservers = new ArrayList<SizeObserver>();
    }
    /**
     * Allow to add a "size" observer.
     * 
     * @param sizeObserver
     *            is a class that implements SizeObserver interface
     */
    public void addSizeObserver(final SizeObserver sizeObserver) {
        sizesObservers.add(sizeObserver);
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
