package net.sourceforge.scuba.swing;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.AbstractListModel;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.sourceforge.scuba.util.Icons;

/**
 * A GUI component for composing and editing a list of URIs.
 */
public class URIListEditor extends JPanel
{
	private static final long serialVersionUID = 4150380293406768361L;

	private static final Icon TABLE_ROW_DELETE_SMALL_ICON = new ImageIcon(Icons.getFamFamFamSilkIcon("table_row_delete"));
	private static final Icon TABLE_ROW_DELETE_LARGE_ICON = new ImageIcon(Icons.getFamFamFamSilkIcon("table_row_delete"));
	private static final Icon TABLE_ROW_INSERT_SMALL_ICON = new ImageIcon(Icons.getFamFamFamSilkIcon("table_row_insert"));
	private static final Icon TABLE_ROW_INSERT_LARGE_ICON = new ImageIcon(Icons.getFamFamFamSilkIcon("table_row_insert"));
	
	private Collection<ActionListener> actionListeners;
	private URIListModel uriList;
	private JList uriDisplay;
	private URIEntryField uriEntryField;

	public URIListEditor(String title) {
		this(title, new ArrayList<URI>());
	}
	
	public URIListEditor(String title, List<URI> initialURIList) {
		super(new BorderLayout());
		uriEntryField = new URIEntryField();
		this.actionListeners = new ArrayList<ActionListener>();
		this.uriList = new URIListModel(initialURIList);

		final Action addAction = getAddAction();
		
		uriDisplay = new JList(uriList) {
			private static final long serialVersionUID = -3510897575223413193L;
			public String getToolTipText(MouseEvent e) {
				int index = locationToIndex(e.getPoint());
				Object item = getModel().getElementAt(index);
				if (item != null) { return item.toString(); }
				return null;
			}
		};
		uriDisplay.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		uriDisplay.setPrototypeCellValue("|<---------- Is this wide enough? ---------->|");
		uriDisplay.setVisibleRowCount(3);
		uriDisplay.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
//				URI uri = (URI)uriDisplay.getSelectedValue();
			}
		});
		uriDisplay.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() < 2) { return; }
				int index = uriDisplay.locationToIndex(e.getPoint());
				URI uri = uriList.get(index);
				popupURIEntryField(uri);
			}
		});
		add(new JScrollPane(uriDisplay), BorderLayout.CENTER);
		setBorder(BorderFactory.createTitledBorder(title));
		
		JToolBar toolBar = new JToolBar();
		JButton addButton = new JButton(); toolBar.add(addButton); addButton.setAction(addAction);
		JButton deleteButton = new JButton(); toolBar.add(deleteButton); deleteButton.setAction(getDeleteAction());
		add(toolBar, BorderLayout.NORTH);
	}

	public List<URI> getURIList() {
		return uriList.getURIList();
	}
	
	public void setURIList(List<URI> uriList) {
		this.uriList.setURIList(uriList);
	}

	public void addURI(URI uri) {
		if (uri != null) {
			uriList.add(uri);
		}
	}

	public void addActionListener(ActionListener l) { actionListeners.add(l); }

	private void notifyActionListeners(ActionEvent e) { for (ActionListener l: actionListeners) { l.actionPerformed(e); } }

	private Action getAddAction() {
		Action action = new AbstractAction() {

			private static final long serialVersionUID = -7053795260898666446L;

			public void actionPerformed(ActionEvent e) {
				popupURIEntryField(null);
			}
		};
		action.putValue(Action.SMALL_ICON, TABLE_ROW_INSERT_SMALL_ICON);
		action.putValue(Action.LARGE_ICON_KEY, TABLE_ROW_INSERT_LARGE_ICON);
		action.putValue(Action.SHORT_DESCRIPTION, "Add a new URI to the list...");
		action.putValue(Action.NAME, "Add");
		return action;
	}
	
	private void popupURIEntryField(URI defaultURI) {
		if (defaultURI != null) { uriEntryField.setURI(defaultURI); }
		int choice = JOptionPane.showOptionDialog(uriDisplay, uriEntryField, "Enter URI",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
				null, null, null);
		if (choice == JOptionPane.OK_OPTION) {
			URI uri = uriEntryField.getURI();
			if (uriList.contains(uri)) { return; }
			uriList.add(uri);
			notifyActionListeners(new ActionEvent(uriDisplay, 0, "URI added to list"));
		}
	}

	/**
	 * Delete the selected entry from the store (and thus from the table).
	 */
	private Action getDeleteAction() {
		Action action = new AbstractAction() {

			private static final long serialVersionUID = 8299630020852663643L;

			public void actionPerformed(ActionEvent e) {
				int index = uriDisplay.getSelectedIndex();
				uriList.remove(index);
				notifyActionListeners(e);
			}			
		};
		action.putValue(Action.SMALL_ICON, TABLE_ROW_DELETE_SMALL_ICON);
		action.putValue(Action.LARGE_ICON_KEY, TABLE_ROW_DELETE_LARGE_ICON);
		action.putValue(Action.SHORT_DESCRIPTION, "Delete selected URI from the list");
		action.putValue(Action.NAME, "Delete");
		return action;
	}

	private class URIListModel extends AbstractListModel
	{
		private static final long serialVersionUID = 7187879340562002114L;
		
		private List<URI> uriList;
		
		public URIListModel(List<URI> uriList) {
			this.uriList = uriList;
			fireContentsChanged(this, 0, uriList.size() - 1);
		}
		
		public Object getElementAt(int index) {
			return uriList.get(index);
		}
		
		public int getSize() {
			return uriList.size();
		}
		
		public void add(URI uri) {
			uriList.add(uri);
			int lastIndex = uriList.size() - 1;
			fireIntervalAdded(this, lastIndex, lastIndex);
		}
		
		public Object remove(int index) {
			Object result = uriList.remove(index);
			fireIntervalRemoved(this, index, index);
			return result;
		}
		
		public URI get(int index) {
			return uriList.get(index);
		}
		
		public List<URI> getURIList() {
			return uriList;
		}
		
		public void setURIList(List<URI> uriList) {
			this.uriList = uriList;
			fireContentsChanged(this, 0, uriList.size() - 1);
		}
		
		public boolean contains(Object uri) {
			return uriList.contains(uri);
		}
	}
}
