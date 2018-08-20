package it.unibo.oop.cctan.interpackage_comunication.commands_observer;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract class that implements CommandsObserverSource.
 */
public abstract class CommandsObserverSourceImpl implements CommandsObserverSource {

    private final List<CommandsObserver> commandsObservers;

    /**
     * Constructor.
     */
    public CommandsObserverSourceImpl() {
        commandsObservers = new ArrayList<CommandsObserver>();
    }

    @Override
    /** {@inheritDoc} */
    public synchronized void addObserver(final CommandsObserver commandsObserver) {
        commandsObservers.add(commandsObserver);
    }

    @Override
    /** {@inheritDoc} */
    public synchronized void removeObserver(final CommandsObserver commandsObserver) {
        commandsObservers.remove(commandsObserver);
    }

    @Override
    /** {@inheritDoc} */
    public abstract void forceCommand(Commands command);

    /**
     * Get a copy of the list of CommandsObservers.
     * 
     * @return A defensive copy of list of CommandsObservers.
     */
    public synchronized List<CommandsObserver> getCommandsObservers() {
        return new ArrayList<>(commandsObservers);
    }

}
