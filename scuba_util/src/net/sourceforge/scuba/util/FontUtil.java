/*
 * SCUBA smart card framework.
 *
 * Copyright (C) 2009 - 2012  The SCUBA team.
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * $Id: $
 */

package net.sourceforge.scuba.util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

/**
 * Utility class with static methods for getting fonts.
 * 
 * @author The JMRTD team (info@jmrtd.org)
 * 
 * @version $Id: $
 */
public class FontUtil
{
	public static Font getFont(String fontFileName, int style, float size) throws FontFormatException {
		try {
			File fontDir = new File(Files.getBaseDirAsFile(), "fonts");
			Font baseFont = Font.createFont(Font.TRUETYPE_FONT, new File(fontDir, fontFileName));
			return baseFont.deriveFont(style, size);
		} catch (IOException e) {
			throw new FontFormatException("Could not open font file");
		}
	}
}
