package it.unibo.oop.cctan.model.generator;

import it.unibo.oop.cctan.model.FixedItem;
import it.unibo.oop.cctan.model.Model;
import it.unibo.oop.cctan.model.PausableThread;

import java.util.ArrayList;
import java.util.List;

/**
 *  Represents a generic generator of objects of type <T>. This object is also a thread because 
 *  it must always remain running in order to continuously generate new objects.
 *  @param <T>
 *              It's the type of objects that will be created dynamically over time.
 */
public abstract class ItemGeneratorImpl<T extends FixedItem> extends PausableThread implements ItemGenerator<T> {

    //private boolean stop;
    //private boolean suspend;
    private final Model model;
    private final List<T> items;
    //private final Object pauseLock;
    private final TimerRatio ratio;

    /**
     * Create a new ItemGeneratorImpl thread respecting the value specified inside this fields.
     * @param model
     *          The model of the application.
     * @param time
     *          This object is of type TimerRatio and represents a thread that keeps track of 
     *          the speed that the object must have when it is generated and also of the 
     *          frequency with which objects of this type must be generated.
     * @param action
     *          the order on which execute operation, i.e. before or after timeout event
     * {@link TimerRatio#TimerRatio()}
     * @see TimerRatio#TimerRatio(double speed, int ratio)
     */
    public ItemGeneratorImpl(final Model model, final TimerRatio time, final ActionOrder action) {
        super(time.getRatio(), action);
        //this.stop = false;
        this.ratio = time;
        this.model = model;
        //this.suspend = false;
        //this.pauseLock = new Object();
        this.items = new ArrayList<>();
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    protected void operation() {
        this.setSleepTime(this.ratio.getRatio());
        this.createNewItem();
    }
    /**
     * This method creates a new object at each time interval. This time interval is set by 
     * the TimerRatio object because it takes care of varying the frequency with which the 
     * movable objects are generated. 
     */
//    public void run() {
//        while (!this.stop) {
//            synchronized (pauseLock) {
//                if (this.stop) {
//                    break;
//                }
//                if (this.suspend) {
//                    try {
//                        pauseLock.wait();
//                    } catch (InterruptedException ex) {
//                        break;
//                    }
//                    if (this.stop) {
//                        break;
//                    }
//                }
//            }
//            createNewItem();
//            try {
//                Thread.sleep(this.ratio.getRatio());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    //}

    /**
     * This method is used to create new object of type <T>. This operation varies according 
     * to the objects that must be generated.
     */
    protected abstract void createNewItem();

    /** 
     * {@inheritDoc}
     */
//    @Override
//    public synchronized void terminate() {
//        if (this.suspend) {
//            this.suspend = false;
//        }
//        this.stop = true;
//        this.ratio.terminate();
//    }
//
//    /** 
//     * {@inheritDoc}
//     */
//    @Override
//    public synchronized void pause() {
//        this.ratio.pause();
//        this.suspend = true;
//    }
//
//    /** 
//     * {@inheritDoc}
//     */
//    @Override
//    public synchronized void resumeGame() {
//        synchronized (this.pauseLock) {
//            this.ratio.resumeGame();
//            this.suspend = false;
//            this.pauseLock.notifyAll();
//        }
//    }

    /**
     * {@inheritDoc} 
     */
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
        //this.stop = false;
        //this.suspend = false;
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
