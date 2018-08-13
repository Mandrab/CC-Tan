package it.unibo.oop.cctan.view;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

<<<<<<< HEAD:src/it/unibo/oop/cctan/view/SizeObserversManager.java
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
=======
import org.apache.commons.lang3.tuple.Pair;

public abstract class SizeObserversSourceImpl implements SizeObserverSource {

    private List<SizeObserver> sizesObservers;

    public SizeObserversSourceImpl() {
>>>>>>> 6dd02bf9f29d4edf3b1379ff23c5b0eb0680cb17:src/it/unibo/oop/cctan/view/SizeObserversSourceImpl.java
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
