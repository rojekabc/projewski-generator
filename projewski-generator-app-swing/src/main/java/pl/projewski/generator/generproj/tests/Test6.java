/**
 * Sprawdzenie sposobu funkcjonowania ladowania
 * pakietow i klas przy pomocy ClassLoader'a 
 */
package pl.projewski.generator.generproj.tests;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @author projewski
 *
 */
public class Test6 {

	public static Class<?>[] getClasses(String pckgname) throws ClassNotFoundException {
ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
// Get a File object for the package
File directory = null;
try {
	ClassLoader cld = ClassLoader.getSystemClassLoader();//Thread.currentThread().getContextClassLoader();
//	ClassLoader.
	if (cld == null) {
		throw new ClassNotFoundException("Can't get class loader.");
	}
	String path = '/' + pckgname.replace('.', '/'); // Maybe better is: String path = pckgname.replace('.', '/');
	
	URL resource = ClassLoader.getSystemResource(path);
//	URL resource = cld.getResource(path);
	if (resource == null) {
		throw new ClassNotFoundException("No resource for " + path);
	}
	directory = new File(resource.getFile());
} catch (NullPointerException x) {
	throw new ClassNotFoundException(pckgname + " (" + directory
			+ ") does not appear to be a valid package");
}
if (directory.exists()) {
	// Get the list of the files contained in the package
	String[] files = directory.list();
	for (int i = 0; i < files.length; i++) {
		// we are only interested in .class files
		if (files[i].endsWith(".class")) {
			// removes the .class extension
			classes.add(Class.forName(pckgname + '.'
					+ files[i].substring(0, files[i].length() - 6)));
		}
	}
} else {
	throw new ClassNotFoundException(pckgname
			+ " does not appear to be a valid package");
}
Class<?>[] classesA = new Class[classes.size()];
classes.toArray(classesA);
return classesA;
}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Package[] packs = Package.getPackages();
		if ( packs == null )
		{
			System.out.println("No packages found");
			System.exit(0);
		}
		int i;
		for ( i=0; i<packs.length; i++ )
		{
			System.out.println(packs[i].getName());
			Class<?> [] cl = packs[i].getClass().getClasses();
			for (int j=0; j<cl.length; j++ )
				System.out.println( cl[j].getName() );
		}
	
		Class<?> [] cl = null;
		try {
			cl = Test6.getClasses("java.lang");
			for ( i = 0; i<cl.length; i++ )
			{
				System.out.println(cl[i].toString());
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("---");
			Enumeration<?> en = ClassLoader.getSystemResources("");
			while ( en.hasMoreElements() )
			{
				System.out.println(en.nextElement().toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Properties props = System.getProperties();
		props.list(System.out);
		// sciezki classpath
		System.out.println(System.getProperty("java.class.path"));
		System.out.println(System.getProperty("sun.boot.class.path"));

	}

}
