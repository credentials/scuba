/*
 * $Id: $
 */

package net.sourceforge.scuba.swing;

import java.awt.Font;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import net.sourceforge.scuba.data.Gender;
import net.sourceforge.scuba.util.IconUtil;

public class GenderLabel extends Box
{
	private static final long serialVersionUID = -4210216741229320608L;

	private Gender gender;
	private JLabel  textLabel;

	public GenderLabel(Gender gender) {
		super(BoxLayout.X_AXIS);
		this.gender = gender;
		String name = null;
		Image image = null;
		switch (gender) {
		case MALE: name = "Male"; image = IconUtil.getFamFamFamSilkIcon("male"); break;
		case FEMALE: name = "Female"; image = IconUtil.getFamFamFamSilkIcon("female"); break;
		case UNKNOWN: name = "Unknown"; image = IconUtil.getFamFamFamSilkIcon("error"); break;
		case UNSPECIFIED: name = "Unspecified"; IconUtil.getFamFamFamSilkIcon("error"); break;
		}
		if (name != null) {	
			if (image != null) {
				add(new JLabel(new ImageIcon(image)));
				add(Box.createHorizontalStrut(10));
			}
			textLabel = new JLabel(name);
			add(textLabel);
		}
	}

	public void setFont(Font font) {
		super.setFont(font);
		textLabel.setFont(font);
	}

	public Gender getGender() {
		return gender;
	}
	
	public String toString() {
		return gender.toString();
	}
}
