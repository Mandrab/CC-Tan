package it.unibo.oop.cctan.model.generator;

import java.util.List;

import it.unibo.oop.cctan.model.FixedItem;

public interface ItemGenerator<T extends FixedItem> {

    void launch();

    List<T> getItems();

    void removeItem(T item);
    
    void terminate();

}
