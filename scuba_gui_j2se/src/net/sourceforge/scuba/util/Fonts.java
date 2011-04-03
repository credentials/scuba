package net.sourceforge.scuba.util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

public class Fonts
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
