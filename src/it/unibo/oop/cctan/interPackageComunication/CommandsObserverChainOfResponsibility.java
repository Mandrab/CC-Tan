package it.unibo.oop.cctan.interPackageComunication;

import java.util.Optional;

/**
 * An interface that specifies which method must have a class that has to act as
 * a link between the CommandsObserverSource and the CommandsObservers.
 */
public interface CommandsObserverChainOfResponsibility {

    /**
     * Sets the CommandsObserverTransitiveSource to be called if this class
     * implementation do not directly contain the CommandsObserverSource.
     * 
     * @param commandsObserverTransitiveSource
     *            The CommandsObserverTransitiveSource
     */
    void setCommandsSuccessor(CommandsObserverChainOfResponsibility successor);

    /**
     * Sets the CommandsObserverSource to be returned from the
     * getCommandsObserverSource method.
     * 
     * @param commandsObserverSource
     *            The CommandsObserverSource
     */
    void setCommandsObserverSource(CommandsObserverSource commandsObserverSource);

    /**
     * Returns the actual source for commands as optional. If the
     * CommandsObserverSource has not yet been instantiated, then return an
     * Optional.empty().
     * 
     * @return The commands source. Optional.empty() if not jet instantiated
     */
    Optional<CommandsObserverSource> getCommandsObserverSource();

}
