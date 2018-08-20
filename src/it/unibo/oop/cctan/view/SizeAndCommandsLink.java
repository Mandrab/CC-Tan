package it.unibo.oop.cctan.view;

import it.unibo.oop.cctan.interpackage_comunication.commands_observer.CommandsObserverLink;
import it.unibo.oop.cctan.interpackage_comunication.size_observer.SizeObserverLink;

/**
 * Interface that merge size and command -ObserverLink.
 */
interface SizeAndCommandsLink extends CommandsObserverLink, SizeObserverLink {

}
