package it.unibo.oop.cctan.view;

import it.unibo.oop.cctan.Controller.Commands;

interface CommandsObserver {

	/** Notify that a new command has been called.
	 * 
	 * @param command is the type of the command, chosen from the Commands enum.
	 */
    void newCommand(Commands command);
	
}
