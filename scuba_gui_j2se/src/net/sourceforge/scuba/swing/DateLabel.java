package net.sourceforge.scuba.swing;

import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import net.sourceforge.scuba.util.Icons;

public class DateLabel extends Box
{
	private static final long serialVersionUID = 4580680157430310683L;

	private static final SimpleDateFormat SDF = new SimpleDateFormat("dd MMM yyyy");
	private static final Icon DATE_ICON = new ImageIcon(Icons.getFamFamFamSilkIcon("date"));
	
	private Date date;
	private JLabel textLabel;

	public DateLabel(Date date) {
		super(BoxLayout.X_AXIS);
		textLabel = new JLabel();
		add(new JLabel(DATE_ICON));
		add(Box.createHorizontalStrut(10));
		add(textLabel);
		setDate(date);
	}
	
	public void setDate(Date date) {
		this.date = date;
		textLabel.setText(SDF.format(date));
	}

	public void setFont(Font font) {
		super.setFont(font);
		textLabel.setFont(font);
	}

	public Date getDate() {
		return date;
	}
}
