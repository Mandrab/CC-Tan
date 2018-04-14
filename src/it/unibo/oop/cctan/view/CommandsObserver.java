package it.unibo.oop.cctan.view;

import InterPackageComunication.Commands;

interface CommandsObserver {

	/** Notify that a new command has been called.
	 * 
	 * @param command is the type of the command, chosen from the Commands enum.
	 */
    void newCommand(Commands command);
	
}
