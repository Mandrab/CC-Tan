package it.unibo.oop.cctan.interPackageComunication;

import java.util.Optional;

/**
 * An implementation of CommandsObserverTransitiveSource.
 */
public abstract class CommandsObserverChainOfResponsibilityImpl implements CommandsObserverChainOfResponsibility {

    private Optional<CommandsObserverSource> commandsObserverSource = Optional.empty();
    private Optional<CommandsObserverChainOfResponsibility> successor = Optional.empty();

    @Override
    /** {@inheritDoc} */
    public void setCommandsSuccessor(final CommandsObserverChainOfResponsibility successor) {
        this.successor = successor != null ? Optional.of(successor) : Optional.empty();
    }

    @Override
    /** {@inheritDoc} */
    public void setCommandsObserverSource(final CommandsObserverSource commandsObserverSource) {
        this.commandsObserverSource = commandsObserverSource != null ? Optional.of(commandsObserverSource)
                : Optional.empty();
    }

    @Override
    /** {@inheritDoc} */
    public Optional<CommandsObserverSource> getCommandsObserverSource() {
        return commandsObserverSource.isPresent() ? commandsObserverSource
                : successor.isPresent() ? successor.get().getCommandsObserverSource() : Optional.empty();
    }

}
