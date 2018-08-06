package it.unibo.oop.cctan.model;

import java.util.ArrayList;
import java.util.List;

/**
 * To do.
 */
public abstract class ItemGeneratorImpl extends Thread implements ItemGenerator {

    private final Model model;
    private final TimerRatio ratio;
    private final List<MovableItem> items;

    public ItemGeneratorImpl(final Model model, final TimerRatio time) {
        super();
        this.ratio = time;
        this.model = model;
        this.items = new ArrayList<>();
    }

    @Override
    public void run() {
        while (true) {
            createNewItem();
            try {
                Thread.sleep(ratio.getRatio());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected abstract void createNewItem();

    protected void addItemToList(final MovableItem item) {
        this.items.add(item);
    }

    @Override
    public void launch() {
        this.ratio.start();
        super.start();
    }

    @Override
    public void removeItem(final MovableItem item) {
        if (!this.items.isEmpty() && item != null) {
            item.terminate();
            this.items.remove(item);
        }
    }

    @Override
    public List<MovableItem> getItems() {
        return new ArrayList<>(this.items);
    }

    public Model getModel() {
        return this.model;
    }

    public TimerRatio getRatio() {
        return this.ratio;
    }

}
