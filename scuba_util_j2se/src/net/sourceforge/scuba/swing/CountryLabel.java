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
 * $Id: CountryLabel.java 183 2012-09-04 18:54:58Z pimvullers $
 */

package net.sourceforge.scuba.swing;

import java.awt.Font;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import net.sourceforge.scuba.data.Country;
import net.sourceforge.scuba.util.IconUtil;

public class CountryLabel extends Box
{
	private static final long serialVersionUID = 4580680157430310682L;

	private Country country;
	private JLabel flagLabel, nameLabel;

	public CountryLabel(Country country) {
		super(BoxLayout.X_AXIS);
		this.country = country;
		flagLabel = new JLabel(getIcon(country));
		nameLabel = new JLabel(country.getName());
		add(flagLabel);
		add(Box.createHorizontalStrut(10));
		add(nameLabel);
	}

	public void setFont(Font font) {
		super.setFont(font);
		nameLabel.setFont(font);
	}

	public Country getCountry() {
		return country;
	}

	private Icon getIcon(Country country) {
		ImageIcon flagIcon = new ImageIcon();
		Image flagImage = IconUtil.getFlagImage(country);
		if (flagImage != null) { flagIcon.setImage(flagImage); }
		return flagIcon;
	}
}
