/* 
 * This file is part of the SCUBA smart card framework.
 * 
 * SCUBA is free software: you can redistribute it and/or modify it under the 
 * terms of the GNU General Public License as published by the Free Software 
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * SCUBA is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more 
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * SCUBA. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2009-2012 The SCUBA team.
 * 
 * $Id: FileInfo.java 183 2012-09-04 18:54:58Z pimvullers $
 */

package net.sourceforge.scuba.smartcards;

/**
 * File information, usually returned by a select command.
 * 
 * FIXME: work in progress...
 *
 * @author Martijn Oostdijk (martijn.oostdijk@gmail.com)
 */
public abstract class FileInfo
{
	public abstract short getFID();
	
	public abstract int getFileLength();
}
