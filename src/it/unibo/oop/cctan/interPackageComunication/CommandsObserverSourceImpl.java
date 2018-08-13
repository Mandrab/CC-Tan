package it.unibo.oop.cctan.interPackageComunication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class CommandsObserverSourceImpl implements CommandsObserverSource{
    
    private List<CommandsObserver> commandsObservers;

    public CommandsObserverSourceImpl() {
        commandsObservers = new ArrayList<CommandsObserver>();
    }

    @Override
    /** {@inheritDoc} */
    public synchronized void addCommandsObserver(final CommandsObserver commandsObserver) {
        commandsObservers.add(commandsObserver);
    }

    @Override
    /** {@inheritDoc} */
    public synchronized void removeCommandsObserver(final CommandsObserver commandsObserver) {
        commandsObservers.remove(commandsObserver);
    }

    /**
     * Get the copy of list of CommandsObservers.
     * 
     * @return A defencive copy of list of CommandsObservers.
     */
    public synchronized List<CommandsObserver> getCommandsObservers() {
        return new ArrayList<>(commandsObservers);
    }

    public abstract void forceCommand(Commands command);
}
