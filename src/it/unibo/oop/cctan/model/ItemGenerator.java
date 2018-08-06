package it.unibo.oop.cctan.model;

import java.util.List;

public interface ItemGenerator {

    void launch();

    List<MovableItem> getItems();

    void removeItem(MovableItem item);

}
