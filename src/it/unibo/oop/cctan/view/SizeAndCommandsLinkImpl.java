package it.unibo.oop.cctan.view;

import java.util.Optional;

import it.unibo.oop.cctan.interpackage_comunication.commands_observer.CommandsObserverLink;
import it.unibo.oop.cctan.interpackage_comunication.commands_observer.CommandsObserverSource;
import it.unibo.oop.cctan.interpackage_comunication.size_observer.SizeObserverLink;
import it.unibo.oop.cctan.interpackage_comunication.size_observer.SizeObserverSource;

/**
 * Implementation of SizeAndCommandsLink.
 */
abstract class SizeAndCommandsLinkImpl implements SizeAndCommandsLink {

    private Optional<CommandsObserverSource> commandsObserverSource = Optional.empty();
    private Optional<CommandsObserverLink> commandsSuccessor = Optional.empty();
    private Optional<SizeObserverSource> sizeObserverSource = Optional.empty();
    private Optional<SizeObserverLink> sizeSuccessor = Optional.empty();

    /**
     * Sets the successor to be called if this class implementation do not directly
     * contain the CommandsObserverSource.
     * 
     * @param successor
     *            The successor to ask
     */
    public void setCommandsSuccessor(final CommandsObserverLink commandsSuccessor) {
        this.commandsSuccessor = commandsSuccessor != null ? Optional.of(commandsSuccessor) : Optional.empty();
    }

    /**
     * Sets the CommandsObserverSource to be returned from the
     * getCommandsObserverSource method.
     * 
     * @param source
     *            The CommandsObserverSource
     */
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

    /**
     * Sets the SizeObserverLink to be called if this class implementation do not
     * directly contain the SizeObserverSource.
     * 
     * @param successor
     *            The SizeObserverTransitiveSource
     */
    public void setSizeSuccessor(final SizeObserverLink sizeSuccessor) {
        this.sizeSuccessor = sizeSuccessor != null ? Optional.of(sizeSuccessor) : Optional.empty();
    }

    /**
     * Sets the SizeObserverSource to be returned from the getSizeObserverSource
     * method.
     * 
     * @param source
     *            The SizeObserverSource
     */
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
