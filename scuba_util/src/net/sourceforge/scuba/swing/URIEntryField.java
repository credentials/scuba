package net.sourceforge.scuba.swing;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * A GUI component to help the user to enter a file, web address
 * or other URI.
 */
public class URIEntryField extends JPanel
{
	private static final long serialVersionUID = 8288112681922969368L;

	private Collection<ActionListener> actionListeners;
	private URI uri;
	private int eventCount;
	private JTextField uriTextField;

	public URIEntryField() {
		super(new FlowLayout());
		final JPanel panel = this;
		this.actionListeners = new ArrayList<ActionListener>();
		this.eventCount = 0;
		final Component c = this;
		uriTextField = new JTextField(20);
		final JButton browseButton = new JButton("...");
		add(uriTextField);
		add(browseButton);
		browseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int choice = fileChooser.showOpenDialog(c);
				switch (choice) {
				case JFileChooser.APPROVE_OPTION:
					File file = fileChooser.getSelectedFile();
					if (file.toURI().equals(uri)) { return; }
					setURI(file.toURI());
					uriTextField.setText(file.toURI().toString());
					notifyActionListeners(new ActionEvent(panel, eventCount++, "Select"));
					break;
				default: break;
				}
			}
		});
		uriTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					setURI(new URI(uriTextField.getText()));
					notifyActionListeners(e);
				} catch (URISyntaxException use) {
					use.printStackTrace();
				}
			}
		});
	}

	public URI getURI() {
		try {
			this.uri = new URI(uriTextField.getText());
		} catch (URISyntaxException use) {
			uri = null;
		}
		return uri;
	}

	public void setURI(URI uri) {
		this.uri = uri;
		if (uri != null) {
			uriTextField.setText(uri.toString());
		} else {
			uriTextField.setText("");
		}
	}

	public void addActionListener(ActionListener l) { actionListeners.add(l); }

	private void notifyActionListeners(ActionEvent e) { for (ActionListener l: actionListeners) { l.actionPerformed(e); } }
}