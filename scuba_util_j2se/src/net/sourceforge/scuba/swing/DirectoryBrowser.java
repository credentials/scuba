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
 * $Id: DirectoryBrowser.java 183 2012-09-04 18:54:58Z pimvullers $
 */

package net.sourceforge.scuba.swing;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

/**
 * A file browser GUI component to search for a directory.
 */
public class DirectoryBrowser extends JPanel
{
	private static final long serialVersionUID = -1280621620589365355L;

	private Collection<ActionListener> actionListeners;
	private File directory;
	private int eventCount;

	public DirectoryBrowser(String title, File defaultDirectory) {
		super(new FlowLayout());
		final JPanel panel = this;
		this.actionListeners = new ArrayList<ActionListener>();
		this.directory = defaultDirectory;
		this.eventCount = 0;
		final Component c = this;
		setBorder(BorderFactory.createTitledBorder(title));
		final JTextField folderTextField = new JTextField(30);
		folderTextField.setText(defaultDirectory.getAbsolutePath());
		final JButton browseCVCADirectoryButton = new JButton("...");
		add(folderTextField);
		add(browseCVCADirectoryButton);
		browseCVCADirectoryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setAcceptAllFileFilterUsed(false);
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.setFileFilter(new FileFilter() {
					public boolean accept(File f) { return f.isDirectory(); }
					public String getDescription() { return "Directories"; }               
				});
				int choice = fileChooser.showOpenDialog(c);
				switch (choice) {
				case JFileChooser.APPROVE_OPTION:
					try {
						File file = fileChooser.getSelectedFile();
						if (file.equals(directory)) { return; }
						setDirectory(file);
						folderTextField.setText(file.getCanonicalPath());
						notifyActionListeners(new ActionEvent(panel, eventCount++, "Select"));
					} catch (IOException ioe) {
						/* NOTE: Do nothing. */
						ioe.printStackTrace();
					}
					break;
				default: break;
				}
			}
		});
	}

	public File getDirectory() {
		return directory;
	}

	public void setDirectory(File directory) {
		this.directory = directory;
	}

	public void addActionListener(ActionListener l) { actionListeners.add(l); }

	private void notifyActionListeners(ActionEvent e) { for (ActionListener l: actionListeners) { l.actionPerformed(e); } }
}