package it.unibo.oop.cctan.view;

import it.unibo.oop.cctan.interPackageComunication.CommandsObserverChainOfResponsibility;
import it.unibo.oop.cctan.interPackageComunication.SizeObserverChainOfResponsibility;

interface SizeAndControlChainOfResponsibility extends CommandsObserverChainOfResponsibility, SizeObserverChainOfResponsibility {

}
