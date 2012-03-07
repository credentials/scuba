/*
 * SCUBA smart card framework.
 *
 * Copyright (C) 2009 - 2012  The SCUBA team.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * $Id: $
 */

package net.sourceforge.scuba.util;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.filechooser.FileFilter;

/**
 * Some helper methods for handling the local file system.
 * 
 * @author The JMRTD team (info@jmrtd.org)
 * 
 * @version $Id: $
 */
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

	public static final FileFilter TXT_FILE_FILTER = new FileFilter() {
		public boolean accept(File f) { return f.isDirectory()
			|| f.getName().endsWith("txt") || f.getName().endsWith("TXT"); }
		public String getDescription() { return "Text files"; }              
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

	public static URI getBaseDirAsURI(Class<?> c) {
		try {

			URL url = getBaseDir(c);

			System.out.println("DEBUG: url.getProtocol() = " + url.getProtocol());
			System.out.println("DEBUG: url.getUserInfo() = " + url.getUserInfo());
			System.out.println("DEBUG: url.getHost() = " + url.getHost());
			System.out.println("DEBUG: url.getPort() = " + url.getPort());
			System.out.println("DEBUG: url.getPath() = " + url.getPath());			
			System.out.println("DEBUG: url.getQuery() = " + url.getQuery());
			System.out.println("DEBUG: url.getRef() = " + url.getRef());

			String protocol = url.getProtocol();
			if (protocol.equalsIgnoreCase("file")) {
				File file = new File(url.getPath().replace("%20", " "));
				return file.toURI();
			} else {
				return url.toURI();
			}
		} catch (URISyntaxException use) {
			use.printStackTrace();
			return null;
		}
		//		return new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), path, url.getQuery(), url.getRef());
	}

	public static File getBaseDirAsFile() {
		return toFile(getBaseDir());
	}

	public static File getApplicationDataDir(String appName) {
		String osName = System.getProperty("os.name").toLowerCase();
		String userHomeName = System.getProperty("user.home");
		if (osName.indexOf("windows") > -1) {
			String appDataDirName = System.getenv("APPDATA");   
			File appDataDir = appDataDirName != null ? new File(appDataDirName) : new File (userHomeName, "Application Data");
			File myAppDataDir = new File(appDataDir, appName.toUpperCase());
			if (!myAppDataDir.isDirectory()) { myAppDataDir.mkdirs(); }
			return myAppDataDir;
		} else {
			File myAppDataDir = new File(userHomeName, "." + appName.toLowerCase());
			if (!myAppDataDir.isDirectory()) {
				if (!myAppDataDir.mkdirs()) { /* FIXME: throw exception? */ }
			}
			return myAppDataDir;
		}
	}

	public static File toFile(URL url) {
		File file = null;
		try {
			/* TODO: this used to be new File(url.toURI()), but that didn't work?!?! Needs testing. */
			file = new File(url.getPath().replace("%20", " "));
			if (file.isDirectory()) {
				return file;
			}
		} catch(IllegalArgumentException iae) {
			throw iae;
		}
		return new File(url.getPath().replace("%20", " "));
	}
}
