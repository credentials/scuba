package net.sourceforge.scuba.util;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.filechooser.FileFilter;

public class Files
{
	public static final FileFilter CERTIFICATE_FILE_FILTER = new FileFilter() {
		public boolean accept(File f) { return f.isDirectory()
			|| f.getName().endsWith("pem") || f.getName().endsWith("PEM")
			|| f.getName().endsWith("cer") || f.getName().endsWith("CER")
			|| f.getName().endsWith("der") || f.getName().endsWith("DER")
			|| f.getName().endsWith("crt") || f.getName().endsWith("CRT")
			|| f.getName().endsWith("cert") || f.getName().endsWith("CERT"); }
		public String getDescription() { return "Certificate files"; }				
	};

	public static final FileFilter CV_CERTIFICATE_FILE_FILTER = new FileFilter() {
		public boolean accept(File f) { return f.isDirectory()
			|| f.getName().endsWith("cvcert") || f.getName().endsWith("CVCERT"); }
		public String getDescription() { return "CV Certificate files"; }              
	};

	public static final FileFilter KEY_FILE_FILTER = new FileFilter() {
		public boolean accept(File f) { return f.isDirectory()
			|| f.getName().endsWith("cer") || f.getName().endsWith("CER")
			|| f.getName().endsWith("der") || f.getName().endsWith("DER")
			|| f.getName().endsWith("x509") || f.getName().endsWith("X509")
			|| f.getName().endsWith("pkcs8") || f.getName().endsWith("PKCS8")
			|| f.getName().endsWith("key") || f.getName().endsWith("KEY"); }
		public String getDescription() { return "Key files"; }				
	};

	public static final FileFilter ZIP_FILE_FILTER = new FileFilter() {
		public boolean accept(File f) { return f.isDirectory() || f.getName().endsWith("zip") || f.getName().endsWith("ZIP"); }
		public String getDescription() { return "ZIP archives"; }				
	};

	public static final FileFilter IMAGE_FILE_FILTER = new FileFilter() {
		public boolean accept(File f) { return f.isDirectory()
			|| f.getName().endsWith("jpg") || f.getName().endsWith("JPG")
			|| f.getName().endsWith("png") || f.getName().endsWith("PNG")
			|| f.getName().endsWith("bmp") || f.getName().endsWith("BMP"); }
		public String getDescription() { return "Image files"; }				
	};

	private Files() {
	}

	/**
	 * Gets the application base directory.
	 * 
	 * @return the base directory
	 */
	public static URL getBaseDir() {
		try {
			Class<?> c = (new Object() {
				public String toString() { return super.toString(); }
			}).getClass();
			
			/* We're using the classloader to determine the base URL. */
			ClassLoader cl = c.getClassLoader();
			URL url = cl.getResource(".");
		
			String protocol = url.getProtocol().toLowerCase();
			String host = url.getHost().toLowerCase();
			String basePathString = url.getFile();

			if (basePathString.endsWith("/bin")
					|| basePathString.endsWith("/bin/")
					|| basePathString.endsWith("/build")
					|| basePathString.endsWith("/build/")) {
				basePathString = (new File(basePathString)).getParent();
			}
			return new URL(protocol, host, basePathString);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Gets the base directory where class <code>c</code> resides.
	 *
	 * @param c
	 * @return the base directory
	 */
	public static URL getBaseDir(Class<?> c) {
		try {
			String className = c.getCanonicalName();
			if (className == null) { className = c.getName(); }
			String pathToClass = "/" + className.replace(".", "/") + ".class";
			URL url = c.getResource(pathToClass);

			String protocol = url.getProtocol().toLowerCase();
			String host = url.getHost().toLowerCase();
			String dirString = url.getFile();
			int classNameIndex = dirString.indexOf(pathToClass);
			String basePathString = dirString.substring(0, classNameIndex);
			if (basePathString.endsWith("/bin")
					|| basePathString.endsWith("/bin/")
					|| basePathString.endsWith("/build")
					|| basePathString.endsWith("/build/")) {
				basePathString = (new File(basePathString)).getParent();
			}
			URL basePathURL = new URL(protocol, host, basePathString);
			return basePathURL;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static File getBaseDirAsFile() {
		return toFile(getBaseDir());
	}
	
	public static File toFile(URL url) {
		File file = null;
		try {
			file = new File(url.toURI());
			if (file.isDirectory()) {
				return file;
			}
		} catch(URISyntaxException e) {
			/* Fall through */
		}
		return new File(url.getPath().replace("%20", " "));
	}
}
