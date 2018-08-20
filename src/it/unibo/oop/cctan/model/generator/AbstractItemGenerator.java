package it.unibo.oop.cctan.model.generator;

import it.unibo.oop.cctan.model.FixedItem;
import it.unibo.oop.cctan.model.Model;

import java.util.ArrayList;
import java.util.List;

/**
 *  Represents a generic generator of objects of type <T>. This object is also a thread because 
 *  it must always remain running in order to continuously generate new objects.
 *  @param <T>
 *              It's the type of objects that will be created dynamically over time.
 */
public abstract class AbstractItemGenerator<T extends FixedItem> extends Thread implements ItemGenerator<T> {

    private boolean stop;
    private boolean suspend;
    private final Model model;
    private final List<T> items;
    private final Object pauseLock;
    private final AbstractTimerRatio ratio;

    /**
     * Create a new ItemGeneratorImpl thread respecting the value specified inside this fields.
     * @param model
     *          The model of the application.
     * @param time
     *          This object is of type TimerRatio and represents a thread that keeps track of 
     *          the speed that the object must have when it is generated and also of the 
     *          frequency with which objects of this type must be generated.
     * {@link AbstractTimerRatio#TimerRatio()}
     * @see AbstractTimerRatio#TimerRatio(double speed, int ratio)
     */
    public AbstractItemGenerator(final Model model, final AbstractTimerRatio time) {
        super();
        this.stop = false;
        this.ratio = time;
        this.model = model;
        this.suspend = false;
        this.pauseLock = new Object();
        this.items = new ArrayList<>();
    }

    /**
     * This method creates a new object at each time interval. This time interval is set by 
     * the TimerRatio object because it takes care of varying the frequency with which the 
     * movable objects are generated. 
     */
    @Override
    public void run() {
        while (!this.stop) {
            synchronized (pauseLock) {
                if (this.stop) {
                    break;
                }
                if (this.suspend) {
                    try {
                        pauseLock.wait();
                    } catch (InterruptedException ex) {
                        break;
                    }
                    if (this.stop) {
                        break;
                    }
                }
            }
            createNewItem();
            try {
                Thread.sleep(this.ratio.getFrequency());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method is used to create new object of generic type T. This operation varies according 
     * to the objects that must be generated.
     */
    protected abstract void createNewItem();

    /** 
     * {@inheritDoc}
     */
    @Override
    public synchronized void terminate() {
        if (this.suspend) {
            this.suspend = false;
        }
        this.stop = true;
        this.ratio.terminate();
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public synchronized void pause() {
        this.ratio.pause();
        this.suspend = true;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public synchronized void resumeGame() {
        synchronized (this.pauseLock) {
            this.ratio.resumeGame();
            this.suspend = false;
            this.pauseLock.notifyAll();
        }
    }

    /**
     * {@inheritDoc} 
     */
    @Override
    public synchronized void addItemToList(final T item) {
        this.items.add(item);
    }

    /**
     * This method is used to launch the TimerRatio thread first. Finally, this thread 
     * that generates objects is also launched.
     */
    @Override
    public void launch() {
        this.ratio.start();
        this.stop = false;
        this.suspend = false;
        start();
    }

    /**
     * {@inheritDoc} 
     */
    @Override
    public synchronized void removeItem(final T item) {
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
     * {@inheritDoc}
     */
    @Override
    public Model getModel() {
        return this.model;
    }

    /**
     * @return
     *          TimerRatio object.
     */
    protected AbstractTimerRatio getRatio() {
        return this.ratio;
    }

}
