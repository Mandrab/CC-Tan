package it.unibo.oop.cctan.interpackage_comunication;

/**
 * An interface that specifies which method must have a class that needs to be
 * informed when a commands is delivered. This is an interface of the "Observer"
 * pattern.
 */
public interface CommandsObserver {

    /**
     * Notify that a new command has been called.
     * 
     * @param command
     *            is the type of the command, chosen from the Commands enumeration.
     */
    void newCommand(Commands command);

}
