package it.unibo.oop.cctan.model.generator;

import java.util.ArrayList;
import java.util.List;

import it.unibo.oop.cctan.model.FixedItem;
import it.unibo.oop.cctan.model.Model;
import it.unibo.oop.cctan.model.MovableItem;

/**
 * To do.
 */
public abstract class ItemGeneratorImpl<T extends FixedItem> extends Thread implements ItemGenerator<T> {

    private final Model model;
    private final TimerRatio ratio;
    private final List<T> items;
    private boolean stop;

    public ItemGeneratorImpl(final Model model, final TimerRatio time) {
        super();
        this.ratio = time;
        this.model = model;
        this.items = new ArrayList<>();
        this.stop = false;
    }

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

    protected abstract void createNewItem();

    protected void addItemToList(final T item) {
        this.items.add(item);
    }

    @Override
    public void launch() {
        this.ratio.start();
        super.start();
    }
    
    @Override
    public void terminate() {
        this.stop = true;
    }

    @Override
    public void removeItem(final T item) {
        if (!this.items.isEmpty() && item != null) {
            this.items.remove(item);
        }
    }

    @Override
    public List<T> getItems() {
        return new ArrayList<>(this.items);
    }

    public Model getModel() {
        return this.model;
    }

    public TimerRatio getRatio() {
        return this.ratio;
    }

}
