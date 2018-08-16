package it.unibo.oop.cctan.interPackageComunication;

/**
 * An interface that specifies the methods that a CommandsObserverSource class
 * must implement.
 */
public interface CommandsObserverSource {

    /**
     * Allow to add a CommandsObserver.
     * 
     * @param commandsObserver
     *            is a class that implements CommandsObserver interface
     */
    void addCommandsObserver(CommandsObserver commandsObserver);

    /**
     * Allow to remove a CommandsObserver.
     * 
     * @param commandsObserver
     *            is a class that implements CommandsObserver interface
     */
    void removeCommandsObserver(CommandsObserver commandsObserver);

    /**
     * Force the delivery of a new command from outside the CommandsObserverSource.
     * 
     * @param command
     *            The command to be delivered
     */
    void forceCommand(Commands command);

}
