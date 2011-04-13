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
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
public class VascoTester
{
	private CardManager manager;
	private SIMService service;
	private Logger logger;

	public VascoTester() {
		logger = Logger.getLogger("SIMApp");
		logger.setLevel(Level.ALL);
		manager = CardManager.getInstance();
		manager.addAPDUListener(new APDUListener() {
			public void exchangedAPDU(APDUEvent e) {
				CommandAPDU capdu = e.getCommandAPDU();
				ResponseAPDU rapdu = e.getResponseAPDU();
				//				logger.info("\n   C: " + Hex.bytesToHexString(capdu.getBytes()) + "\n   R: " + Hex.bytesToHexString(rapdu.getBytes()));
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

	private CardTerminalListener getMyCardTerminalListener() {
		return new CardTerminalListener() {

			public void cardInserted(CardEvent ce) {
				try {
					open(ce.getService());
				} catch (CardServiceException e) {
					e.printStackTrace();
				}
			}

			public void cardRemoved(CardEvent ce) {
				close(ce.getService());
				logger.info("closed");
			}
		};
	}

	private void open(CardService service) throws CardServiceException {
		if (service == null) { throw new CardServiceException("Cannot open null"); }
		try {
			service.open();
			long startTime = System.currentTimeMillis();
			logger.info("opened " + service);
			experiment(service instanceof SIMService ? (SIMService)service : new SIMService(service), 30000);
			service.close();
			logger.info("closed (" + (System.currentTimeMillis() - startTime) / 1000 + " seconds)");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void close(CardService service) {
		if (service == null) { return; }
		try {
			service.close();
		} catch (Exception e) {
			// Oh well..
		}
	}

	private void experiment(SIMService service, int n) throws CardServiceException {
		try {
			int[][] digits = new int[6][10];
			DataOutputStream out = new DataOutputStream(new FileOutputStream("/t:/dp.txt"));
			for (int i = 0; i < n; i++) {
				String otp = experiment(service);
				out.writeUTF(otp + "\n");
				try {
					for (int digitNr = 0; digitNr < 6; digitNr ++) {
						int digitValue = Integer.parseInt(otp.substring(digitNr, digitNr + 1));
						digits[digitNr][digitValue] ++;
					}
				} catch (NumberFormatException nfe) {
					throw new IllegalStateException(nfe.getMessage());
				}
				System.out.println((i + 1) + ". " + otp);
			}
			out.close();
			for (int digitValue = 0; digitValue < 10; digitValue++) {
				System.out.print(digitValue + "\t");
				for (int digitNr = 0; digitNr < 6; digitNr++) {
					System.out.print("\t" + digits[digitNr][digitValue]);
				}
				System.out.println();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private String experiment(SIMService service) throws CardServiceException {
		//					service.verifyCHV(SIMService.CHV1, "0000");

		String otp = null;

		int keyPad = ProactiveCommand.KEYPAD_DEVICE_ID,
		sim = ProactiveCommand.SIM_DEVICE_ID,
		me = ProactiveCommand.ME_DEVICE_ID;

		/* Tell SIM user selected DIGIPASS menu. */
		ProactiveCommand commandCommand = new SelectMenuProactiveCommand(keyPad, sim, 1);
		int length = service.envelope(commandCommand);
		ProactiveCommand responseCommand = service.fetch(length);
		//		printCommand(responseCommand);
		/* Response contains alphaIdentifier DIGIPASS and items 1->Authentication, 2->Information */

		/* Tell SIM user selected Authentication item. */
		commandCommand = new SelectItemProactiveCommand(me, sim, 0, 1);
		length = service.terminalResponse(commandCommand);
		responseCommand = service.fetch(length);
		//		printCommand(responseCommand);
		/* Response requests provideLocalInformation: 0x01, 0x26 (Date), 0x03 */

		/* Provide SIM with current date. */
		commandCommand = new ProvideLocalInformationProactiveCommand(me, sim, System.currentTimeMillis());
		length = service.terminalResponse(commandCommand);
		responseCommand = service.fetch(length);
		//		printCommand(responseCommand);
		/* Response request display text of OTP */

		if (responseCommand instanceof DisplayTextProactiveCommand) {
			DisplayTextProactiveCommand displayTextProactiveCommand = (DisplayTextProactiveCommand)responseCommand;
			String displayText = displayTextProactiveCommand.getText().trim();
			otp = displayText.substring(displayText.length() - 6, displayText.length());
		}

		//		commandCommand = new SelectItemProactiveCommand(me, sim, 0, 2);
		//		length = service.terminalResponse(commandCommand);
		//		responseCommand = service.fetch(length);
		//		printCommand(responseCommand);

		/* Tell SIM displaying went ok. */
		commandCommand = new DisplayTextProactiveCommand(me, sim, 0);
		length = service.terminalResponse(commandCommand);
		responseCommand = service.fetch(length);
		//		printCommand(responseCommand);

		return otp;

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
		new VascoTester();
	}
}
