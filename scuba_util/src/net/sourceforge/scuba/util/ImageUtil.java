package net.sourceforge.scuba.util;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;

public class ImageUtil
{
	public static BufferedImage read(InputStream in, long imageLength, String mimeType) throws IOException {
		Iterator<ImageReader> readers = ImageIO.getImageReadersByMIMEType(mimeType);
		while (readers.hasNext()) {
			try {
				ImageReader reader = readers.next();
				System.out.println("DEBUG: reader = " + reader + " : " + reader.getClass().getCanonicalName());
				ImageInputStream iis = ImageIO.createImageInputStream(in);
				long posBeforeImage = iis.getStreamPosition();
				reader.setInput(iis);
				BufferedImage image = reader.read(0);
				long posAfterImage = iis.getStreamPosition();
				if ((posAfterImage - posBeforeImage) != imageLength) {
					throw new IOException("Image may not have been correctly read");
				}
				if (image != null) { return image; }
			} catch (Exception e) {
				e.printStackTrace();
				/* NOTE: this reader doesn't work? Try next one... */
				continue;
			}
		}
		/* Tried all readers */
		throw new IOException("Could not decode \"" + mimeType + "\" image!");
	}

	public static void write(Image image, String mimeType, OutputStream out) throws IOException {
		Iterator<ImageWriter> writers = ImageIO.getImageWritersByMIMEType(mimeType);
		if (!writers.hasNext()) {
			throw new IOException("No writers for \"" + mimeType + "\"");
		}
		ImageOutputStream ios = ImageIO.createImageOutputStream(out);
		while (writers.hasNext()) {
			try {
				ImageWriter writer = (ImageWriter)writers.next();
				writer.setOutput(ios);
				ImageWriteParam pm = writer.getDefaultWriteParam();
				RenderedImage renderedImage = toBufferedImage(image);
				pm.setSourceRegion(new Rectangle(0, 0, renderedImage.getWidth(), renderedImage.getHeight()));
				writer.write(renderedImage);
				return;
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			} finally {
				ios.flush();
			}
		}
	}

	/**
	 * This method returns a buffered image with the contents of an image
	 * From Java Developers Almanac site. TODO: Check license.
	 */
	private static BufferedImage toBufferedImage(Image image) {
		if (image instanceof BufferedImage) {
			return (BufferedImage)image;
		}

		// This code ensures that all the pixels in the image are loaded
		image = new ImageIcon(image).getImage();

		// Determine if the image has transparent pixels; for this method's
		// implementation, see e661 Determining If an Image Has Transparent Pixels
		boolean hasAlpha = hasAlpha(image);

		// Create a buffered image with a format that's compatible with the screen
		BufferedImage bimage = null;
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			// Determine the type of transparency of the new buffered image
			int transparency = Transparency.OPAQUE;
			if (hasAlpha) {
				transparency = Transparency.BITMASK;
			}

			// Create the buffered image
			GraphicsDevice gs = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gs.getDefaultConfiguration();
			bimage = gc.createCompatibleImage(
					image.getWidth(null), image.getHeight(null), transparency);
		} catch (HeadlessException e) {
			// The system does not have a screen
		}

		if (bimage == null) {
			// Create a buffered image using the default color model
			int type = BufferedImage.TYPE_INT_RGB;
			if (hasAlpha) {
				type = BufferedImage.TYPE_INT_ARGB;
			}
			bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
		}

		// Copy image to buffered image
		Graphics g = bimage.createGraphics();

		// Paint the image onto the buffered image
		g.drawImage(image, 0, 0, null);
		g.dispose();

		return bimage;
	}

	// This method returns true if the specified image has transparent pixels
	private static boolean hasAlpha(Image image) {
		// If buffered image, the color model is readily available
		if (image instanceof BufferedImage) {
			BufferedImage bimage = (BufferedImage)image;
			return bimage.getColorModel().hasAlpha();
		}

		// Use a pixel grabber to retrieve the image's color model;
		// grabbing a single pixel is usually sufficient
		PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
		try {
			pg.grabPixels();
		} catch (InterruptedException e) {
		}

		// Get the image's color model
		ColorModel cm = pg.getColorModel();
		return cm.hasAlpha();
	}
}
