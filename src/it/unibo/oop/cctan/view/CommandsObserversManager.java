package it.unibo.oop.cctan.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.unibo.oop.cctan.interPackageComunication.CommandsObserver;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserverSource;

/**
 * A class created to handle the commandsObservers list.
 * @author Sutera Lorenzo
 *
 */
public class CommandsObserversManager implements CommandsObserverSource {
    private List<CommandsObserver> commandsObservers;

    /**
     * The constructor of CommandsObserverManager class.
     */
    public CommandsObserversManager() {
        commandsObservers = new ArrayList<CommandsObserver>();
    }

    @Override
    /** {@inheritDoc} */
    public void addCommandsObserver(final CommandsObserver commandsObserver) {
        commandsObservers.add(commandsObserver);
    }

    @Override
    /** {@inheritDoc} */
    public void removeCommandsObserver(final CommandsObserver commandsObserver) {
        commandsObservers.remove(commandsObserver);
    }

    /**
     * Get the copy of list of CommandsObservers.
     * 
     * @return A defencive copy of list of CommandsObservers.
     */
    public List<CommandsObserver> getCommandsObservers() {
        return Collections.unmodifiableList(this.commandsObservers);
    }
}
