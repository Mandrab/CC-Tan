package it.unibo.oop.cctan.view;

import java.util.ArrayList;
import java.util.List;

import it.unibo.oop.cctan.interPackageComunication.CommandsObserver;

public class CommandsObserversMenager {
    
    private List<CommandsObserver> commandsObservers;
    
    /**
     * Allow to add a "command" observer.
     * 
     * @param commandsObserver
     *            is a class that implements CommandsObserver interface
     */
    public void addCommandsObserver(final CommandsObserver commandsObserver) {
        commandsObservers.add(commandsObserver);
    }
    
    /**
     * Get the copy of list of CommandsObservers.
     * 
     * @return A copy of list of CommandsObservers.
     */
    public List<CommandsObserver> getCommandsObservers() {
        List<CommandsObserver> copia = new ArrayList<>();
        copia.addAll(commandsObservers);
        return copia;
    }

}
