package it.unibo.oop.cctan.interpackage_comunication;

/**
 * An interface that specifies the methods that a CommandsObserverSource class
 * must implement.
 */
public interface CommandsObserverSource extends ObserverSource<CommandsObserver> {

    /**
     * Force the delivery of a new command from outside the CommandsObserverSource.
     * 
     * @param command
     *            The command to be delivered
     */
    void forceCommand(Commands command);

}
