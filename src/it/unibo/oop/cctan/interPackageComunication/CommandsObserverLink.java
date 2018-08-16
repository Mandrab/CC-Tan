package it.unibo.oop.cctan.interPackageComunication;

import java.util.Optional;

/**
 * An interface that specifies which method must have a class that has to act as
 * a link between the CommandsObserverSource and the CommandsObservers. This is
 * a particular implementation of the "Chain of Responsibility" pattern in which
 * only the classes that stores the CommandObserverSource have the "authority"
 * for return it.
 */
public interface CommandsObserverLink {

    /**
     * Returns the actual source for commands as optional. If the
     * CommandsObserverSource has not yet been instantiated, then return an
     * Optional.empty().
     * 
     * @return The commands source. Optional.empty() if not jet instantiated
     */
    Optional<CommandsObserverSource> getCommandsObserverSource();

}
