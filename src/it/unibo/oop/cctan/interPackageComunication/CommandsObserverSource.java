package it.unibo.oop.cctan.interPackageComunication;

/**
 * An interface that specifies the method that a CommandsObserverSource class
 * must implement.
 */
public interface CommandsObserverSource {

    /**
     * Allow to add a "commands" observer.
     * 
     * @param commandsObserver
     *            is a class that implements CommandsObserver interface
     */
    void addCommandsObserver(CommandsObserver commandsObserver);

    /**
     * Allow to remove a "commands" observer.
     * 
     * @param commandsObserver
     *            is a class that implements CommandsObserver interface
     */
    void removeCommandsObserver(CommandsObserver commandsObserver);

    void forceCommand(Commands command);

}
