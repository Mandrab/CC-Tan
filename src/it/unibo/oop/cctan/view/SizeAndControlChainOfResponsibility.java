package it.unibo.oop.cctan.view;

import it.unibo.oop.cctan.interPackageComunication.CommandsObserverLink;
import it.unibo.oop.cctan.interPackageComunication.SizeObserverLink;

interface SizeAndControlChainOfResponsibility extends CommandsObserverLink, SizeObserverLink {

}
