package it.unibo.oop.cctan.view;

import java.util.Optional;

import it.unibo.oop.cctan.interPackageComunication.CommandsObserverLink;
import it.unibo.oop.cctan.interPackageComunication.CommandsObserverSource;
import it.unibo.oop.cctan.interPackageComunication.SizeObserverLink;
import it.unibo.oop.cctan.interPackageComunication.SizeObserverSource;

abstract class SizeAndControlChainOfResponsibilityImpl implements SizeAndControlChainOfResponsibility {

    private Optional<CommandsObserverSource> commandsObserverSource = Optional.empty();
    private Optional<CommandsObserverLink> commandsSuccessor = Optional.empty();
    private Optional<SizeObserverSource> sizeObserverSource = Optional.empty();
    private Optional<SizeObserverLink> sizeSuccessor = Optional.empty();

    /** {@inheritDoc} */
    public void setCommandsSuccessor(final CommandsObserverLink commandsSuccessor) {
        this.commandsSuccessor = commandsSuccessor != null ? Optional.of(commandsSuccessor) : Optional.empty();
    }

    /** {@inheritDoc} */
    public void setCommandsObserverSource(final CommandsObserverSource commandsObserverSource) {
        this.commandsObserverSource = commandsObserverSource != null ? Optional.of(commandsObserverSource)
                : Optional.empty();
    }

    @Override
    /** {@inheritDoc} */
    public Optional<CommandsObserverSource> getCommandsObserverSource() {
        return commandsObserverSource.isPresent() ? commandsObserverSource
                : commandsSuccessor.isPresent() ? commandsSuccessor.get().getCommandsObserverSource() : Optional.empty();
    }

    public void setSizeSuccessor(final SizeObserverLink sizeSuccessor) {
        this.sizeSuccessor = sizeSuccessor != null ? Optional.of(sizeSuccessor) : Optional.empty();
    }

    public void setSizeObserverSource(final SizeObserverSource sizeObserverSource) {
        this.sizeObserverSource = sizeObserverSource != null ? Optional.of(sizeObserverSource) : Optional.empty();
    }

    @Override
    /** {@inheritDoc} */
    public Optional<SizeObserverSource> getSizeObserverSource() {
        return sizeObserverSource.isPresent() ? sizeObserverSource
                : sizeSuccessor.isPresent() ? sizeSuccessor.get().getSizeObserverSource() : Optional.empty();
    }

}
