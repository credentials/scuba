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

	public static URL getBaseDir() {
		try {
			Class<?> c = (new Object() {
				public String toString() { return super.toString(); }
			}).getClass();
			ClassLoader cl = c.getClassLoader();
			URL url = cl.getResource(".");
		
			String protocol = url.getProtocol().toLowerCase();
			String host = url.getHost().toLowerCase();
			String basePathString = url.getFile();

			if (basePathString.endsWith("/bin")
					|| basePathString.endsWith("/bin/")
					|| basePathString.endsWith("/build")
					|| basePathString.endsWith("/bin/")) {
				basePathString = (new File(basePathString)).getParent();
			}
			System.out.println("DEBUG: getBaseDir() says basePathString = " + basePathString);
			return new URL(protocol, host, basePathString);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static URL getBaseDir(Class<?> c) {
		try {
			String classCanonicalName = c.getCanonicalName();
			if (classCanonicalName == null) { classCanonicalName = c.getName(); }
			String classPathToClass = "/" + classCanonicalName.replace(".", "/") + ".class";
			URL url = c.getResource(classPathToClass);

			String protocol = url.getProtocol().toLowerCase();
			String host = url.getHost().toLowerCase();
			String dirString = url.getFile();
			int classNameIndex = dirString.indexOf(classPathToClass);
			String basePathString = dirString.substring(0, classNameIndex);
			if (basePathString.endsWith("/bin")
					|| basePathString.endsWith("/bin/")
					|| basePathString.endsWith("/build")
					|| basePathString.endsWith("/bin/")) {
				basePathString = (new File(basePathString)).getParent();
			}
			
			System.out.println("DEBUG: basePathString = " + basePathString);
			URL basePathURL = new URL(protocol, host, basePathString);
			return basePathURL;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static File getBaseDirAsFile() {
		URL baseDirURL = getBaseDir();
		File baseDirFile = null;
		try {
			baseDirFile = new File(baseDirURL.toURI());
			if (baseDirFile.isDirectory()) {
				return baseDirFile;
			}
		} catch(URISyntaxException e) {
			/* Fall through */
		}
		return new File(baseDirURL.getPath().replace("%20", " "));
	}
}
