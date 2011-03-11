package acs.jni;

import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * ACR120U JNI Class Package provides the programming interfaces for developer to develop application
 * for ACR120U using Java programming language.
 * The implementation consists of a wrapper DLL developed in C++ and a Java class package that exports
 * the programming interfaces for access to native methods implemented in the DLL.
 */
public class ACR120U {

	private static final String ClassVersion = new String("Version: 1.0.0.0");

	/** This constant field defines the instance of ACR120U connected to the USB port. */
	public static final short
	ACR120_USB1 = 0,
	ACR120_USB2 = 1,
	ACR120_USB3 = 2,
	ACR120_USB4 = 3,
	ACR120_USB5 = 4,
	ACR120_USB6 = 5,
	ACR120_USB7 = 6,
	ACR120_USB8 = 7;

	/** This constant field defines the key type to use to login a sector of Mifare card. */
	public static final short
	AC_MIFARE_LOGIN_KEYTYPE_A = 170,
	AC_MIFARE_LOGIN_KEYTYPE_B = 187,
	AC_MIFARE_LOGIN_KEYTYPE_DEFAULT_A = 173,
	AC_MIFARE_LOGIN_KEYTYPE_DEFAULT_B = 189,
	AC_MIFARE_LOGIN_KEYTYPE_DEFAULT_F = 253,
	AC_MIFARE_LOGIN_KEYTYPE_STORED_A = 175,
	AC_MIFARE_LOGIN_KEYTYPE_STORED_B = 191;

	/** This constant field defines the default buffer size used with Mifare card. */
	public static final int ACR120UJNI_DEFAULT_BUFFER_SIZE = 80;

	/** This constant field defines the default buffer size used with ISO-14443 card. */
	public static final int ACR120UJNI_14443_BUFFER_SIZE = 512;

	/** This constant field defines the maximum number of tags that can be listed using listTags method. */
	public static final int ACR120UJNI_MAX_NUM_TAGS = 4;

	/**  This constant field defines the maximum tag length returned by a card. */
	public static final int ACR120UJNI_MAX_TAG_LENGTH = 10;

	/** This constant field defines the maximum key length of Mifare card. */
	public static final int ACR120UJNI_MIFARE_KEY_LENGTH = 6;

	/** This constant field defines the block length of Mifare card. */
	public static final int ACR120UJNI_MIFARE_BLOCK_LENGTH = 16;

	/** This constant field defines the error code returned by this JNI class package. */
	public static final short
	ACR120JNI_ERROR_SUCCESS = 0,
	ACR120JNI_ERROR_SEND_BUFFER_OVERFLOW = -5000,
	ACR120JNI_ERROR_RECV_BUFFER_OVERFLOW = -5001,
	ACR120JNI_ERROR_EXCEPTION_OCCURED = -5002;

	private String pFirmwareVersion;
	private byte MifareInterfaceType;
	private int CardsSupported;
	private byte CardOpMode;
	private byte FWI;

	static {
		AccessController.doPrivileged(new PrivilegedAction<Object>() {
			public Object run() {
				try {
					System.loadLibrary("ACR120UJNI");
				} catch (Exception exception) {
					exception.printStackTrace();
				}
				return null;
			}

		});
	}

	public ACR120U() {
	}

	/**
	 * Sets the firmware version string retrieved from the ACR120U device.
	 * Reserved for <code>ACR120UJNI.DLL</code> use only.
	 * This routine will be called by the native method invoked by {@link #status(short)} method.
	 * 
	 * @param jFWVersion the firmware string retrieved from the ACR120U device. 
	 * 
	 * @return 0
	 */
	public int setFirmwareVersion(String jFWVersion) {
		pFirmwareVersion = jFWVersion;
		return 0;
	}

	/**
	 * Sets the interface type of Mifare card retrieved from the ACR120U device.
	 * Reserved for <code>ACR120UJNI.DLL</code> use only.
	 * This routine will be called by the native method invoked by {@link #status(short)} method.
	 * 
	 * @param jMifareInterfaceType the interface type retrieved from the ACR120U device. 
	 * 
	 * @return 0
	 */
	public int setMifareInterfaceType(byte jMifareInterfaceType) {
		MifareInterfaceType = jMifareInterfaceType;
		return 0;
	}

	/**
	 * Sets the supported Mifare card retrieved from the ACR120U device.
	 * Reserved for <code>ACR120UJNI.DLL</code> use only.
	 * This routine will be called by the native method invoked by {@link #status(short)} method.
	 * 
	 * @param jCardsSupported the supported card type retrieved from the ACR120U device. 
	 * 
	 * @return 0
	 */
	public int setCardsSupported(int jCardsSupported) {
		CardsSupported = jCardsSupported;
		return 0;
	}

	/**
	 * Sets the card operation mode retrieved from the ACR120U device.
	 * Reserved for <code>ACR120UJNI.DLL</code> use only.
	 * This routine will be called by the native method invoked by {@link #status(short)} method.
	 * Meaning of this value is yet to be defined.
	 * 
	 * @param jCardOpMode the card operation mode retrieved from the ACR120U device. 
	 * 
	 * @return 0
	 */
	public int setCardOpMode(byte jCardOpMode) {
		CardOpMode = jCardOpMode;
		return 0;
	}

	/**
	 * Sets the current FWI timeout value retrieved from the ACR120U device.
	 * Reserved for <code>ACR120UJNI.DLL</code> use only.
	 * This routine will be called by the native method invoked by {@link #status(short)} method. 
	 * 
	 * @param jFWI the FWI timeout value to set. 
	 * @return 0
	 */
	public int setFWI(byte jFWI) {
		FWI = jFWI;
		return 0;
	}

	/**
	 * Gets the version of this JNI wrapper class for ACR120U native methods.
	 * 
	 * @return the version of this class.
	 */
	public String getClassVersion() {
		return ClassVersion;
	}

	/**
	 * Get the retrieved firmware version of ACR120U device.
	 * {@link #status(short)} method must be called first.
	 * 
	 * @return the retrieved firmware string.
	 */
	public String getFirmwareVersion() {
		return pFirmwareVersion;
	}

	/**
	 * Get the retrieved interface byte of ACR120U device.
	 * {@link #status(short)} method must be called first.
	 * Possible values are:
	 * <table>
	 * <tr><td>0x01</td><td>Type A</td></tr>
	 * <tr><td>0x02</td><td>Type B</td></tr>
	 * <tr><td>0x03</td><td>Type A + Type B</td></tr>
	 * </table>
	 *     
	 * @return the retrieved interface byte.
	 */
	public byte getMifareInterfaceType() {
		return MifareInterfaceType;
	}

	/**
	 * Get the supported card type of ACR120U device.
	 * {@link #status(short)} method must be called first.
	 * Meaning of this value is interpreted in bit level:
	 * <table>
	 * <tr><td>Bit 0</td><td>Mifare Light</td></tr>
	 * <tr><td>Bit 1</td><td>Mifare 1K</td></tr>
	 * <tr><td>Bit 2</td><td>Mifare 4K</td></tr>
	 * <tr><td>Bit 3</td><td>Mifare DESFire</td></tr>
	 * <tr><td>Bit 4</td><td>Mifare UltraLight</td></tr>
	 * <tr><td>Bit 5</td><td>JCOP30</td></tr>
	 * <tr><td>Bit 6</td><td>Shanghai Transport</td></tr>
	 * <tr><td>Bit 7</td><td>MPCOS Combi</td></tr>
	 * <tr><td>Bit 8</td><td>ISO Type B, Calypso</td></tr>
	 * <tr><td>Bit 9 - Bit 31</td><td>To be defined</td></tr>
	 * </table>
	 *
	 * @return the retrieved supported card type.
	 */
	public int getCardsSupported() {
		return CardsSupported;
	}

	/**
	 * Gets the card operation mode of ACR120U device.
	 * {@link #status(short)} method must be called first.
	 * Meaning of this value is yet to be defined.
	 * 
	 * @return the retrieved supported card type.
	 */
	public byte getCardOpMode() {
		return CardOpMode;
	}

	/**
	 * Gets the FWI timeout value of ACR120U device. status() method must be called first. Meaning of this value is yet to be defined.
	 * 
	 * @return the retrieved FWI timeout value. 
	 */
	public byte getFWI() {
		return FWI;
	}

	/**
	 * Opens the connection to reader.
	 * This should be the first method to call before other methods (except RequestDLLVersion).
	 * The returned value will be used as the input handle for the other methods (except RequestDLLVersion).
	 * 
	 * @param hReaderPort
	 *
	 * @return a value representing the returned handle from the native method.
	 */
	public short open(short hReaderPort) {
		return ACR120Open(hReaderPort);
	}

	/**
	 * Closes the connection to reader.
	 * 
	 * @param hReader the USB handle returned by a prior call to open() method.
	 *
	 * @return a decimal error code value; 0 = success, <0 = error.
	 */
	public short close(short hReader) {
		return ACR120Close(hReader);
	}

	public short reset(short hReader) {
		return ACR120Reset(hReader);
	}

	public short status(short hReader) {
		return ACR120Status(hReader);
	}

	public short readRC531Reg(short hReader, byte regNo, byte[] value) {
		return ACR120ReadRC500Reg(hReader, regNo, value);
	}

	public short writeRC531Reg(short hReader, byte regNo, byte value) {
		return ACR120WriteRC500Reg(hReader, regNo, value);
	}

	public short directSend(short word0, byte byte0, byte abyte0[],
			byte abyte1[], byte abyte2[], short word1) {
		return ACR120DirectSend(word0, byte0, abyte0, abyte1, abyte2, word1);
	}

	public short directReceive(short word0, byte byte0, byte abyte0[],
			byte abyte1[], short word1) {
		return ACR120DirectReceive(word0, byte0, abyte0, abyte1, word1);
	}

	public short requestDLLVersion(byte abyte0[], byte abyte1[]) {
		return ACR120RequestDLLVersion(abyte0, abyte1);
	}

	public short readEEPROM(short word0, byte byte0, byte abyte0[]) {
		return ACR120ReadEEPROM(word0, byte0, abyte0);
	}

	public short writeEEPROM(short word0, byte byte0, byte byte1) {
		return ACR120WriteEEPROM(word0, byte0, byte1);
	}

	public short readUserPort(short word0, byte abyte0[]) {
		return ACR120ReadUserPort(word0, abyte0);
	}

	public short writeUserPort(short word0, byte byte0) {
		return ACR120WriteUserPort(word0, byte0);
	}

	public short select(short word0, byte abyte0[], byte abyte1[],
			byte abyte2[]) {
		return ACR120Select(word0, abyte0, abyte1, abyte2);
	}

	public short power(short hReader, byte byte0) {
		return ACR120Power(hReader, byte0);
	}

	public short login(short hReader, byte byte0, byte byte1, byte byte2,
			byte abyte0[]) {
		return ACR120Login(hReader, byte0, byte1, byte2, abyte0);
	}

	public short listTags(short hReader, byte abyte0[], byte abyte1[],
			byte abyte2[], byte abyte3[][]) {
		return ACR120ListTags(hReader, abyte0, abyte1, abyte2, abyte3);
	}

	public short read(short hReader, byte byte0, byte abyte0[]) {
		return ACR120Read(hReader, byte0, abyte0);
	}

	public short readValue(short hReader, byte byte0, int ai[]) {
		return ACR120ReadValue(hReader, byte0, ai);
	}

	public short write(short hReader, byte byte0, byte abyte0[]) {
		return ACR120Write(hReader, byte0, abyte0);
	}

	public short writeValue(short hReader, byte byte0, int i) {
		return ACR120WriteValue(hReader, byte0, i);
	}

	public short writeMasterKey(short hReader, byte byte0, byte abyte0[]) {
		return ACR120WriteMasterKey(hReader, byte0, abyte0);
	}

	public short inc(short hReader, byte byte0, int i, int ai[]) {
		return ACR120Inc(hReader, byte0, i, ai);
	}

	public short dec(short hReader, byte byte0, int i, int ai[]) {
		return ACR120Dec(hReader, byte0, i, ai);
	}

	public short copy(short hReader, byte byte0, byte byte1, int ai[]) {
		return ACR120Copy(hReader, byte0, byte1, ai);
	}

	public short multiTagSelect(short hReader, byte byte0, byte abyte0[],
			byte abyte1[], byte abyte2[], byte abyte3[]) {
		return ACR120MultiTagSelect(hReader, byte0, abyte0, abyte1, abyte2,
				abyte3);
	}

	public short txDataTelegram(short hReader, byte byte0, byte abyte0[],
			byte abyte1[], byte abyte2[]) {
		return ACR120TxDataTelegram(hReader, byte0, abyte0, abyte1, abyte2);
	}

	/**
	 * Resets the block number to be used during the ISO14443 part 4 (T=CL) communication.
	 * This function also sets the frame length of the Card (PICC).
	 * By default the frame length is to 16. The frame length of the card is reported by
	 * the ATS in type A and the ATQB in type B cards.
	 *
	 * @param frameLength an index to a maximum frame size which the card can accept.
	 *
	 * @return a decimal error code value; 0 = success, <0 = error.
	 */
	public short initBlockNumber(short frameLength) {
		return PICCInitBlockNumber(frameLength);
	}

	/**
	 * Handles the APDU exchange in T=CL protocol.
	 * This routine will handle the Frame Waiting Time Extension (WTX) and chaining for long messages.
	 * 
	 * @param hReader the USB handle returned by a prior call to {@link #open(short)} method.
	 * @param isTypeA a value indicating the card type, <code>true</code> for type A cards, <code>false</code> for type B cards.
	 * @param xLen a pointer to the location storing the length of the data to transmit, in bytes.
	 * @param xData a pointer to the transmit data storage.
	 * @param rLen a pointer to the location storing the length of the data received, in bytes.
	 * @param rData a pointer to the receive data storage.
	 * 
	 * @return a decimal error code value; 0 = success, <0 = error.
	 */
	public short xchAPDU(short hReader, boolean isTypeA, short[] xLen, byte[] xData, short[] rLen, byte[] rData) {
		return PICCXchAPDU(hReader, isTypeA, xLen, xData, rLen, rData);
	}

	/**
	 * Requests an Answer-to-Select (ATS) message from the card after calling the Select method.
	 * It tells the card how many bytes the reader can handle in a frame and also gets the operation parameters of the card when communicating in ISO14443 mode.
	 * This function is only valid for ISO14443 type A cards.
	 *
	 * @param hReader the USB handle returned by a prior call to open() method.
	 * @param fsdi an index to a maximum frame size which the reader can accept. The value should not exceed 4, i.e. 48 bytes.
	 * @param atsLen a pointer to the location storing the length of the ATS received.
	 * @param ats a pointer to the ATS received. 
	 *
	 * @return a decimal error code value; 0 = success, <0 = error.
	 */
	public short rATS(short hReader, byte fsdi, byte[] atsLen, byte[] ats) {
		return PICCRATS(hReader, fsdi, atsLen, ats);
	}

	public short deselect(short hReader, boolean isTypeA) {
		return PICCDeselect(hReader, isTypeA);
	}

	/* ONLY PRIVATE NATIVE METHODS BELOW */

	private native short ACR120Open(short word0);
	private native short ACR120Close(short word0);
	private native short ACR120Reset(short word0);
	private native short ACR120Status(short word0);
	private native short ACR120ReadRC500Reg(short word0, byte byte0, byte abyte0[]);
	private native short ACR120WriteRC500Reg(short word0, byte byte0, byte byte1);
	private native short ACR120DirectSend(short word0, byte byte0, byte abyte0[], byte abyte1[], byte abyte2[], short word1);
	private native short ACR120DirectReceive(short word0, byte byte0, byte abyte0[], byte abyte1[], short word1);
	private native short ACR120RequestDLLVersion(byte abyte0[], byte abyte1[]);
	private native short ACR120ReadEEPROM(short word0, byte byte0, byte abyte0[]);
	private native short ACR120WriteEEPROM(short word0, byte byte0, byte byte1);
	private native short ACR120ReadUserPort(short word0, byte abyte0[]);
	private native short ACR120WriteUserPort(short word0, byte byte0);
	private native short ACR120Select(short word0, byte abyte0[], byte abyte1[], byte abyte2[]);
	private native short ACR120Power(short word0, byte byte0);
	private native short ACR120Login(short word0, byte byte0, byte byte1, byte byte2, byte abyte0[]);
	private native short ACR120ListTags(short word0, byte abyte0[], byte abyte1[], byte abyte2[], byte abyte3[][]);
	private native short ACR120Read(short word0, byte byte0, byte abyte0[]);
	private native short ACR120ReadValue(short word0, byte byte0, int ai[]);
	private native short ACR120Write(short word0, byte byte0, byte abyte0[]);
	private native short ACR120WriteValue(short word0, byte byte0, int i);
	private native short ACR120WriteMasterKey(short word0, byte byte0, byte abyte0[]);
	private native short ACR120Inc(short word0, byte byte0, int i, int ai[]);
	private native short ACR120Dec(short word0, byte byte0, int i, int ai[]);
	private native short ACR120Copy(short word0, byte byte0, byte byte1, int ai[]);
	private native short ACR120MultiTagSelect(short word0, byte byte0, byte abyte0[], byte abyte1[], byte abyte2[], byte abyte3[]);
	private native short ACR120TxDataTelegram(short word0, byte byte0, byte abyte0[], byte abyte1[], byte abyte2[]);
	private native short PICCInitBlockNumber(short word0);
	private native short PICCXchAPDU(short word0, boolean flag, short aword0[], byte abyte0[], short aword1[], byte abyte1[]);
	private native short PICCRATS(short word0, byte byte0, byte abyte0[], byte abyte1[]);
	private native short PICCDeselect(short word0, boolean flag);
}
