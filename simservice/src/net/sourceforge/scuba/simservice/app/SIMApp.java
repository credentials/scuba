/*
 * $Id: $
 */

package net.sourceforge.scuba.simservice.app;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeListener;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.smartcardio.CardTerminal;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.sourceforge.scuba.simservice.DisplayTextProactiveCommand;
import net.sourceforge.scuba.simservice.GSM1111;
import net.sourceforge.scuba.simservice.ProactiveCommand;
import net.sourceforge.scuba.simservice.ProvideLocalInformationProactiveCommand;
import net.sourceforge.scuba.simservice.SIMService;
import net.sourceforge.scuba.simservice.SelectItemProactiveCommand;
import net.sourceforge.scuba.simservice.SelectMenuProactiveCommand;
import net.sourceforge.scuba.smartcards.APDUEvent;
import net.sourceforge.scuba.smartcards.APDUListener;
import net.sourceforge.scuba.smartcards.CardEvent;
import net.sourceforge.scuba.smartcards.CardManager;
import net.sourceforge.scuba.smartcards.CardService;
import net.sourceforge.scuba.smartcards.CardServiceException;
import net.sourceforge.scuba.smartcards.CardTerminalListener;
import net.sourceforge.scuba.swing.FileSystemTree;
import net.sourceforge.scuba.swing.HexViewer;
import net.sourceforge.scuba.util.Hex;

/**
 * Test application for SIM service.
 * 
 * @author Martijn Oostdijk (martijn.oostdijk@gmail.com)
 */
public class SIMApp
{
	private CardManager manager;
	private SIMService service;
	private Logger logger;

	public SIMApp() {
		logger = Logger.getLogger("SIMApp");
		logger.setLevel(Level.ALL);
		manager = CardManager.getInstance();
		manager.addAPDUListener(new APDUListener() {
			public void exchangedAPDU(APDUEvent e) {
				CommandAPDU capdu = e.getCommandAPDU();
				ResponseAPDU rapdu = e.getResponseAPDU();
				logger.info("\n   C: " + Hex.bytesToHexString(capdu.getBytes()) + "\n   R: " + Hex.bytesToHexString(rapdu.getBytes()));
			}
		});
		manager.addCardTerminalListener(getMyCardTerminalListener());
	}

	private String pathToString(short[] path) {
		StringBuffer result = new StringBuffer();
		for (short fid: path) {
			result.append("/" + Integer.toHexString(fid & 0xFFFF));
		}
		return result.toString();
	}

	public String toString() {
		StringBuffer result = new StringBuffer();
		List<CardTerminal> terminals = manager.getTerminals();
		for (CardTerminal terminal: terminals) {
			result.append("[" + (manager.isPolling(terminal) ? "X" : " ")+ "] ");
			result.append(terminal.getName());
			result.append("\n");
		}
		result.append("\n");
		result.append("Service = " + service);
		result.append("\n");
		return result.toString();
	}

	private String showPasswordDialog(String message) {
		final JPasswordField jpf = new JPasswordField();
		JOptionPane jop = new JOptionPane(jpf,
				JOptionPane.QUESTION_MESSAGE,
				JOptionPane.OK_CANCEL_OPTION);
		JDialog dialog = jop.createDialog(message);
		dialog.addWindowFocusListener(new WindowAdapter() {
			public void windowGainedFocus(WindowEvent e){
				jpf.requestFocusInWindow();
			}
		});

		dialog.setVisible(true);
		int result = (Integer)jop.getValue();
		dialog.dispose();
		char[] password = null;
		if(result == JOptionPane.OK_OPTION){
			password = jpf.getPassword();
		}
		return new String(password);
	}

	private CardTerminalListener getMyCardTerminalListener() {
		return new CardTerminalListener() {

			public void cardInserted(CardEvent ce) {
				if (service != null) { close(service); }
				try {
					service = new SIMService(ce.getService());
					service.open();
					logger.info("opened " + service);
					//					service.verifyCHV(SIMService.CHV1, "0000");

					short[][] fileSystem = GSM1111.ALL_EF_PATHS;

					JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

					FileSystemTree tree = new FileSystemTree(fileSystem);
					final JPanel view = new JPanel();
					view.add(new JLabel("<NOT READ YET>"));
					splitPane.add(new JScrollPane(tree));
					splitPane.add(new JScrollPane(view));

					tree.addFilePathSelectionListener(new FileSystemTree.FilePathSelectionListener() {
						public void pathSelected(short[] path) {
							JTextArea area = new JTextArea(40, 40);
							Component currentView = view.getComponent(0);
							view.remove(0);
							try {
								InputStream fileIn = service.readFile(path);								
								currentView = new HexViewer(service.readFile(path));
							} catch (CardServiceException cse) {
								cse.printStackTrace();
								int sw = cse.getSW();
								System.out.println("DEBUG: HIER sw = " + Integer.toHexString(sw));
								if (sw == GSM1111.SW_ACCESS_CONDITION_NOT_FULFILLED) {
									String pin = showPasswordDialog("Enter PIN:");
									System.out.println("DEBUG: pin = \"" + pin + "\"");
								}
								area.setText(cse.getMessage() + "\n");
								currentView = area;
							} catch (Throwable e) {
								e.printStackTrace();
								area.setText(e.getMessage() + "\n");
								currentView = area;
							}
							view.add(currentView);
							view.revalidate(); view.repaint();
						}
					});

					JPanel pinPanel = new JPanel();
					JTextField pinTextField = new JTextField(8);
					JButton checkPinButton = new JButton("Check");
					pinPanel.add(new JLabel("PIN: "));
					pinPanel.add(pinTextField);
					pinPanel.add(checkPinButton);

					pinTextField.setAction(getCheckPINAction(pinTextField));
					checkPinButton.setAction(getCheckPINAction(pinTextField));


					JFrame frame = new JFrame("SIM browser");
					Container contentPane = frame.getContentPane();
					contentPane.setLayout(new BorderLayout());
					contentPane.add(pinPanel, BorderLayout.NORTH);
					contentPane.add(splitPane, BorderLayout.CENTER);
					JButton expButton = new JButton("EXP");
					contentPane.add(expButton, BorderLayout.SOUTH);
					expButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							try {
								experiment();
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					});
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frame.pack();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			public void cardRemoved(CardEvent ce) {
				close(ce.getService());
				close(service);
				logger.info("closed");
			}

			private void close(CardService service) {
				if (service == null) { return; }
				try {
					service.close();
				} catch (Exception e) {
					// Oh well..
				}
			}
		};
	}

	private Action getCheckPINAction(final JTextField pinTextField) {
		return new AbstractAction() {
			private static final long serialVersionUID = 6021512031289472407L;

			public void actionPerformed(ActionEvent e) {
				String pin = pinTextField.getText();
				try {
					int n = service.verifyCHV(SIMService.CHV1, pin);
					System.out.println("DEBUG: verifyCHV returned " + n);
				} catch (CardServiceException cse) {
					// TODO Auto-generated catch block
					cse.printStackTrace();
				}
				System.out.println("DEBUG: pin = " + pin);
				//				JOptionPane.showInputDialog("PIN please", null);
			}
		};
	}

	private void experiment() throws CardServiceException {
		int keyPad = ProactiveCommand.KEYPAD_DEVICE_ID,
		sim = ProactiveCommand.SIM_DEVICE_ID,
		me = ProactiveCommand.ME_DEVICE_ID;

		/* Tell SIM user selected DIGIPASS menu. */
		ProactiveCommand commandCommand = new SelectMenuProactiveCommand(keyPad, sim, 1);
		int length = service.envelope(commandCommand);
		ProactiveCommand responseCommand = service.fetch(length);
		printCommand(responseCommand);
		/* Response contains alphaIdentifier DIGIPASS and items 1->Authentication, 2->Information */

		/* Tell SIM user selected Authentication item. */
		commandCommand = new SelectItemProactiveCommand(me, sim, 0, 1);
		length = service.terminalResponse(commandCommand);
		responseCommand = service.fetch(length);
		printCommand(responseCommand);
		/* Response requests provideLocalInformation: 0x01, 0x26 (Date), 0x03 */

		commandCommand = new ProvideLocalInformationProactiveCommand(me, sim, System.currentTimeMillis());
		length = service.terminalResponse(commandCommand);
		responseCommand = service.fetch(length);
		printCommand(responseCommand);
		/* Response request display text of OTP */

		//		commandCommand = new SelectItemProactiveCommand(me, sim, 0, 2);
		//		length = service.terminalResponse(commandCommand);
		//		responseCommand = service.fetch(length);
		//		printCommand(responseCommand);

				commandCommand = new DisplayTextProactiveCommand(me, sim, 0);
				length = service.terminalResponse(commandCommand);
				responseCommand = service.fetch(length);
				printCommand(responseCommand);
	}

	private void printCommand(ProactiveCommand responseCommand) {
		if (responseCommand == null) {
			System.out.println("Response was <null>");
		} else if (responseCommand instanceof SelectItemProactiveCommand) {
			SelectItemProactiveCommand selectItemProactiveCommand = (SelectItemProactiveCommand)responseCommand;
			System.out.println("DEBUG: response: selectItem");
			System.out.println("DEBUG:    " + selectItemProactiveCommand.getAlphaIdentifier());
			System.out.println("DEBUG:    " + selectItemProactiveCommand.getItemTexts());
		} else if (responseCommand instanceof ProvideLocalInformationProactiveCommand) {
			ProvideLocalInformationProactiveCommand provideLocalInformationProactiveCommand = (ProvideLocalInformationProactiveCommand)responseCommand;
			System.out.println("DEBUG: response: provideLocalInformation");
			System.out.println("DEBUG:    command details " + provideLocalInformationProactiveCommand.getCommandDetails());
			System.out.println("DEBUG:    date " + provideLocalInformationProactiveCommand.getDate());
		} else if (responseCommand instanceof DisplayTextProactiveCommand) {
			DisplayTextProactiveCommand displayTextProactiveCommand = (DisplayTextProactiveCommand)responseCommand;
			System.out.println("DEBUG: response displayText");
			System.out.println("DEBUG:    command details " + displayTextProactiveCommand.getCommandDetails());
			System.out.println("DEBUG:    text " + "\"" + displayTextProactiveCommand.getText().trim() + "\"");
		} else {
			System.out.println("DEBUG: response to terminalResponse(select item) is a " + responseCommand.getClass().getSimpleName());
		}
	}

	public static void main(String[] arg) {
		new SIMApp();
	}
}
