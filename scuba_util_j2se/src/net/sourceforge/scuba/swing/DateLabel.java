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
 * $Id: DateLabel.java 183 2012-09-04 18:54:58Z pimvullers $
 */

package net.sourceforge.scuba.swing;

import java.awt.Font;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import net.sourceforge.scuba.util.IconUtil;

public class DateLabel extends Box
{
	private static final long serialVersionUID = 4580680157430310683L;

	private static final SimpleDateFormat
	PRESENTATION_SDF = new SimpleDateFormat("dd MMM yyyy"),
	PARSER_6_DIGITS_SDF = new SimpleDateFormat("yyMMdd"),
	PARSER_8_DIGITS_SDF = new SimpleDateFormat("yyyyMMdd");

	private static final Icon DATE_ICON = new ImageIcon(IconUtil.getFamFamFamSilkIcon("date"));

	private Date date;
	private JLabel textLabel;

	private DateLabel() {
		super(BoxLayout.X_AXIS);
		textLabel = new JLabel();
		add(new JLabel(DATE_ICON));
		add(Box.createHorizontalStrut(10));
		add(textLabel);
	}

	public DateLabel(String dateString) throws ParseException {
		this();
		if (dateString == null) {
			throw new IllegalArgumentException("Cannot parse null date");
		}
		dateString = dateString.trim();
		switch(dateString.length()) {
		case 6:
			setDate(PARSER_6_DIGITS_SDF.parse(dateString));
			break;
		case 8:
			setDate(PARSER_8_DIGITS_SDF.parse(dateString));
			break;
		default: throw new IllegalArgumentException("Cannot parse date " + dateString);
		}
	}

	public DateLabel(Date date) {
		this();
		setDate(date);
	}

	public void setDate(Date date) {
		this.date = date;
		textLabel.setText(PRESENTATION_SDF.format(date));
	}

	public void setFont(Font font) {
		super.setFont(font);
		textLabel.setFont(font);
	}

	public Date getDate() {
		return date;
	}
}
