/**
 * ProtocolCommand.java
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) Pim Vullers, Radboud University Nijmegen, September 2012.
 */

package net.sourceforge.scuba.smartcards;

/**
 * Simple data structure for storing APDU commands for smart cards together 
 * with some meta data.
 * 
 * @author Maarten Everts
 * @author Pim Vullers
 */
public class ProtocolCommand {
	
	/**
	 * A short string used to uniquely identify this command. 
	 */
	private String key;
	
	/**
	 * A brief description of the command.
	 */
	private String description;
	
	/**
	 * The actual command APDU to be send to the smart card.
	 */
	private CommandAPDU command;

	/**
	 * A map to translate smart card status bytes to error strings, can be null.
	 */
	private ProtocolErrors error = null;
	
	/**
	 * Construct a new ProtocolCommand.
	 * 
	 * @param key used to identify this command.
	 * @param description of the command.
	 * @param apdu to be send to the smart card.
	 */
	public ProtocolCommand(String key, String description, CommandAPDU apdu) {
		this.key = key;
		this.description = description;
		this.command = apdu;
	}
	
	/**
	 * Construct a new ProtocolCommand.
	 * 
	 * @param key used to identify this command.
	 * @param description of this command.
	 * @param apdu to be send to the smart card.
	 * @param error mapping from status bytes to error strings.
	 */
	public ProtocolCommand(String key, String description, CommandAPDU apdu, ProtocolErrors error) {
		this.key = key;
		this.description = description;
		this.command = apdu;
		this.error = error;
	}
	
	/**
	 * Get the key used to identify this command.
	 * 
	 * @return the key used to identify this command. 
	 */
	public String getKey() {
		return key;
	}
	
	/**
	 * Get the description of this command.
	 * 
	 * @return the description of this command. 
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Get the command APDU for the smart card.
	 * 
	 * @return the command for the smart card.
	 */
	public CommandAPDU getAPDU() {
		return command;
	}

	/**
	 * Set a new command APDU for the smart card.
	 * 
	 * @param apdu to be send to the smart card. 
	 */
	public void setAPDU(CommandAPDU apdu) {
		this.command = apdu;
	}

	/**
	 * Get the error message for the given status.
	 * 
	 * @param status received from the smart card.
	 * @return the corresponding error message, if available.
	 */
	public String getErrorMessage(int status) {
		if (error != null && error.containsKey(status)) {
			return error.get(status);
		} else {
			return null;
		}
	}
}
