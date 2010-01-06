package net.sourceforge.scuba.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;

import net.sourceforge.scuba.data.Country;

/**
 * Utility class with methods for reading icons and other images from file.
 * Assumes file structure (relative to class path) something like this:
 * <ul>
 *    <li>images/</li>
 *    <ul>
 *       <li>flags/</li>
 *       <ul>
 *          <li><i>alpha2countrycode</i>.png</li>
 *       </ul>
 *       <li>icons/</li>
 *       <ul>
 *          <li><i>action</i>.png</li>
 *       </ul>
 *       <li><i>image</i>.png</li>
 *    </ul>
 * </ul>
 * 
 * @author Martijn Oostdijk (martijn.oostdijk@gmail.com)
 */
public class Icons
{	
	private static final Image DEFAULT_16X16_IMAGE =  new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB),
	DEFAULT_16X11_IMAGE =  new BufferedImage(16, 11, BufferedImage.TYPE_INT_ARGB);

	private static URL getImagesDir() {
		try {
			URL basePathURL = Files.getBaseDir();
			URL imagesDirURL = new URL(basePathURL + "/images");
			return imagesDirURL;
		} catch (Exception e) {
			return null;
		}
	}
	
	private static URL getImagesDir(Class<?> c) {
		try {
			URL basePathURL = Files.getBaseDir(c);
			URL imagesDirURL = new URL(basePathURL + "/images");
			return imagesDirURL;
		} catch (Exception e) {
			return null;
		}
	}
	
	private static URL getIconsDir() {
		return getImagesDir(Icons.class);
	}

	public static Image getImage(String imageName, Class<?> c) {
		try {
			URL imagesDir = getImagesDir(c);
			String fileName = imageName.toLowerCase() + ".png";
			Image image = ImageIO.read(new URL(imagesDir + "/" + fileName));
			return image;
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Image getImage(String imageName) {
		try {
			URL imagesDir = getImagesDir();
			String fileName = imageName.toLowerCase() + ".png";
			Image image = ImageIO.read(new URL(imagesDir + "/" + fileName));
			return image;
		} catch (Exception e) {
			return null;
		}
	}

	public static Image getFlagImage(Country country) {
		return getImageFromCollection("flags", country.toString().toLowerCase(), DEFAULT_16X11_IMAGE);
	}

	/**
	 * Gets icon from file.
	 * 
	 * @param iconName name without the .png or .gif
	 * @return an image
	 */
	public static Image getFamFamFamSilkIcon(String iconName) {
		return getImageFromCollection("famfamfam_silk", iconName.toLowerCase(), DEFAULT_16X16_IMAGE);
	}
	
	private static Image getImageFromCollection(String collectionName, String imageName, Image defaultImage) {
		/* TODO: check if directory with name collectionName or zip file with name collectionName.zip exists */
		return getImageFromZippedCollection(collectionName, imageName, defaultImage);
	}

	private static Image getImageFromZippedCollection(String collectionName, String imageName, Image defaultImage) {
		try {
			URL collectionURL = new URL(getIconsDir() + "/" + collectionName + ".zip");
			ZipInputStream zipIn = new ZipInputStream(collectionURL.openStream());
			ZipEntry entry;
			while ((entry = zipIn.getNextEntry()) != null) {
				String fileName = imageName + ".png";
				String entryName = entry.getName();
				if (entryName != null && entryName.equals(fileName)) {
					Image flagImage = ImageIO.read(zipIn);
					return flagImage;
				}
			}
		} catch (Exception e) {
			return defaultImage;
		}
		return defaultImage;
	}
}
