package it.unibo.oop.cctan.interpackage_comunication.commands_observer;

import java.util.Optional;

/**
 * An implementation of CommandsObserverLink.
 */
public abstract class CommandsObserverLinkImpl implements CommandsObserverLink {

    private Optional<CommandsObserverSource> source = Optional.empty();
    private Optional<CommandsObserverLink> successor = Optional.empty();

    /**
     * Sets the successor to be called if this class implementation do not directly
     * contain the CommandsObserverSource.
     * 
     * @param successor
     *            The successor to ask
     */
    public void setCommandsSuccessor(final CommandsObserverLink successor) {
        this.successor = successor != null ? Optional.of(successor) : Optional.empty();
    }

    /**
     * Sets the CommandsObserverSource to be returned from the
     * getCommandsObserverSource method.
     * 
     * @param source
     *            The CommandsObserverSource
     */
    public void setCommandsObserverSource(final CommandsObserverSource source) {
        this.source = source != null ? Optional.of(source) : Optional.empty();
    }

    @Override
    /** {@inheritDoc} */
    public Optional<CommandsObserverSource> getCommandsObserverSource() {
        return source.isPresent() 
               ? source
               : successor.isPresent() 
                   ? successor.get().getCommandsObserverSource() 
                   : Optional.empty();
    }

}
