package it.unibo.oop.cctan.model.generator;

import it.unibo.oop.cctan.model.FixedItem;
import it.unibo.oop.cctan.model.Model;
import java.util.ArrayList;
import java.util.List;

/**
 *  Represents a generic generator of MovableItem. This object is also a thread because 
 *  it must always remain running in order to continuously generate new MovableItem.
 */
public abstract class ItemGeneratorImpl<T extends FixedItem> extends Thread implements ItemGenerator<T> {

    private boolean stop;
    private final Model model;
    private final List<T> items;
    private final TimerRatio ratio;

    /**
     * Create a new movable item respecting the value specified inside this fields.
     * @param model
     *          The model of the application.
     * @param time
     *          This object is of type TimerRatio and represents a thread that keeps track of 
     *          the speed that the object must have when it is generated and also of the 
     *          frequency with which objects of this type must be generated.
     * {@link TimerRatio#TimerRatio()}
     * @see TimerRatio#TimerRatio(double speed, int ratio)
     */
    public ItemGeneratorImpl(final Model model, final TimerRatio time) {
        super();
        this.stop = false;
        this.ratio = time;
        this.model = model;
        this.items = new ArrayList<>();
    }

    /**
     * This method creates a new object at each time interval. This time interval is set by 
     * the TimerRatio object because it takes care of varying the frequency with which the 
     * movable objects are generated. 
     */
    @Override
    public void run() {
        while (!stop) {
            createNewItem();
            try {
                Thread.sleep(ratio.getRatio());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method is used to create new MovableItem. This operation varies according 
     * to the MovableItem that must be generated.
     */
    protected abstract void createNewItem();

    /**
     * {@inheritDoc} 
     */
    public void addItemToList(final T item) {

        this.items.add(item);
    }

    /**
     * This method is used to launch the TimerRatio thread first. Finally, this thread 
     * that generates movable objects is also launched.
     */
    @Override
    public void launch() {
        this.ratio.start();
        super.start();
    }

    /**
     * This method is used to stop this thread.
     */
    @Override
    public void terminate() {
        this.stop = true;
    }

    /**
     * {@inheritDoc} 
     */
    @Override
    public void removeItem(final T item) {
        if (!this.items.isEmpty() && item != null) {
            this.items.remove(item);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> getItems() {
        return new ArrayList<>(this.items);
    }

    /**
     * @return
     *          TimerRatio object.
     */
    public TimerRatio getRatio() {
        return this.ratio;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Model getModel() {
        return this.model;
    }

}
