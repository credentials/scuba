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
 * $Id: KeyPanel.java 183 2012-09-04 18:54:58Z pimvullers $
 */

package net.sourceforge.scuba.swing;

import java.awt.BorderLayout;
import java.awt.Font;
import java.security.Key;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class KeyPanel extends JPanel
{
	private static final long serialVersionUID = 1447754792790045211L;

	private Key key;
	private JTextArea area;
	
	public KeyPanel(Key key) {
		super(new BorderLayout());
		this.key = key;
		area = new JTextArea(5, 40);
		area.append(keyToString(key));
		area.setEditable(false);
		add(new JScrollPane(area), BorderLayout.CENTER);
	}
	
	public Key getKey() {
		return key;
	}
	
	public void setFont(Font font) {
		super.setFont(font);
		if (area != null) { area.setFont(font); }
	}

	private static String keyToString(Key key) {
		if (key instanceof RSAPublicKey) {
			RSAPublicKey rsaPubKey = (RSAPublicKey)key;
			StringBuffer result = new StringBuffer();
			result.append("RSA Public Key\n");
			result.append("   public exponent = " + rsaPubKey.getPublicExponent() + "\n");
			result.append("   modulus = " + rsaPubKey.getModulus() + "\n");
			return result.toString();
		} else if (key instanceof RSAPrivateKey) {
			RSAPrivateKey rsaPubKey = (RSAPrivateKey)key;
			StringBuffer result = new StringBuffer();
			result.append("RSA Private Key\n");
			result.append("   private exponent = " + rsaPubKey.getPrivateExponent() + "\n");
			result.append("   modulus = " + rsaPubKey.getModulus() + "\n");
			return result.toString();
		} else {
			return key.toString();
		}
	}
}
