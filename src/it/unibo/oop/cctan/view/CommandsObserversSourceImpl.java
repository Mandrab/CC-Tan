package it.unibo.oop.cctan.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.unibo.oop.cctan.interPackageComunication.CommandsObserver;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserverSource;

public abstract class CommandsObserversSourceImpl implements CommandsObserverSource{
    
    private List<CommandsObserver> commandsObservers;

    public CommandsObserversSourceImpl() {
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
        return Collections.unmodifiableList(this.commandsObservers);
    }
}
