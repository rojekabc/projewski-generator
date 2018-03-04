package pl.projewski.generator.tools;
/**
	* Zapewnia istnienie konfiguracji własnego systemu z następującymi
	* możliwościami:
	* - ustwaienie kodowania wyjściowego na stdout i udostępnienie metod
	*		przekierunkowywujących na owo wyjście
	* - Ustwaianie podstawowych ustawień pakietu
	*		kodowanie plików xml'a
	*		nazwy plików językowych z opisem kodów błędów i innych komunikatów
	*/
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;

import pk.ie.proj.exceptions.NumberStoreException;
import pk.ie.proj.tools.stream.NumberReader;
import pk.ie.proj.tools.stream.NumberWriter;
import pl.projewski.generator.exceptions.NumberStoreException;
import pl.projewski.generator.tools.stream.NumberReader;
import pl.projewski.generator.tools.stream.NumberWriter;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;

public class Mysys extends DefaultHandler {
	private java.io.Writer out;
	private java.io.Writer err;
	
	private String encoding = "iso8859-2"; // setNum = 1
	private String langFolderName = "lang";
	private String langName = "pl";
	private int packageSize = 8000; // setNum = 4
	private boolean isDebug = false;
	
	private java.io.File tempFolder = null;
	private java.io.File langFolder = null;
	
	private static Mysys factoredMysys = null;
	
	private Mysys()
	{
		initConfiguration();
	}
	
	private static Mysys factorMysys()
	{
		if ( factoredMysys == null )
			factoredMysys = new Mysys();
		return factoredMysys;
	}


	private void writeDefaultConfiguration( java.io.File file )
	{
		try {
			java.io.PrintStream savefile = new java.io.PrintStream(
					new java.io.FileOutputStream( file ) );
			savefile.println("<?xml version='1.0' encoding='"+encoding+"'?>");
			savefile.println("<Configuration"
					+ " encoding=\"" + encoding +"\""
					+ " langFolder=\"" + langFolderName + "\""
					+ " language=\"" + langName + "\""
					+ " packageSize=\"" + packageSize + "\""
					+ " debug=\"" + isDebug + "\""
					+ " />");
			savefile.close();
		} catch ( Exception e ) {
			System.out.println( e.toString() );
		}
	}
	// odczytuje podstawowy plik konfiguracyjny i inicjuje zmienne systemowe
	private void initConfiguration()
	{
		try {
			java.io.File plik = new java.io.File( "mysys.cnf" );
			if ( !plik.exists() ) {
				URL resource = this.getClass().getResource("/mysys.cnf");
				if (resource != null ) {
					plik = new File(resource.toURI());
				}
				if ( !plik.exists()) {
					writeDefaultConfiguration(plik);
				}
			}
			DefaultHandler han = this;
			SAXParserFactory fac = SAXParserFactory.newInstance();
			SAXParser par = fac.newSAXParser();
			par.parse( plik, han );
			out = new java.io.OutputStreamWriter(System.out, encoding);
			err = new java.io.OutputStreamWriter(System.err, encoding);
		} catch ( Exception e ) {
			System.out.println("Mysys: " + e.toString());
		}
	}

	public void startDocument()
		throws SAXException
		{
			System.out.print("Czytanie pliku konfiguracyjnego ...");
//			isConfig = false;
//			rdBuf = new String("");
		}
	public void endDocument()
		throws SAXException
		{
//			rdBuf = null;
			System.out.println("Ok.");
		}
	public void startElement( String uri, String sn, String qn, Attributes att )
		throws SAXException
		{
			try {
				String name = sn;
				if ( name.equals("") )
					name = qn;
				if ( name.equals("Configuration") )
				{
					encoding = att.getValue("encoding");
					langFolderName = att.getValue("langFolder");
					langName = att.getValue("language");
					packageSize = Integer.parseInt(att.getValue("packageSize"));
					isDebug = att.getValue("debug").equals("true");
					if ( att.getValue("tmpFolder") != null )
						tempFolder = new File(att.getValue("tmpFolder")); 
										
					if (( langFolderName != null ) && ( langName != null ))
					{
						langFolder = new File(
								langFolderName
								+ File.separator
								+ langName
								);
						if (!langFolder.exists()) {
							URL resource = Mysys.class.getResource("/" + langFolderName + "/" + langName);
							if (resource != null) {
								langFolder = new File(resource.toURI());
							}
						}
						if ( langFolder.exists() == false )
						{
							System.out.println("[WARN] Nie odnaleziono folderu z napisami: " + langFolder.toString());
							langFolder = null;
							return;
						}
						if ( langFolder.isDirectory() == false )
						{
							System.out.println("[WARN] Podana pozycja wskazuje plik: " + langFolder.toString());
							langFolder = null;
							return;
						}
					}
				}
			} catch ( Exception e ) {
				System.out.println("Mysys.startElement: " + e.toString());
			}
		}
	
	public static String getDescription(String classname, String descname, String typename, String [] args)
	{
		File descriptionFile = null;
		Mysys mysys = factorMysys();
		if ( mysys.langFolder == null )
		{
			Mysys.error("[WARN] Mysys: nie określono folderu napisów");
			return "";
		}
		descriptionFile = new File(
				mysys.langFolder.toString() +	File.separator +
				typename + File.separator +
				classname +	".properties");
		if ( descriptionFile.exists() == false )
		{
			Mysys.error("[WARN] Nie odnaleziono pliku napisow: " + descriptionFile.toString());
			return "";
		}
		if ( descriptionFile.isFile() == false )
		{
			Mysys.error("[WARN] Pozycja nie jest plikiem napisow: " + descriptionFile.toString());
			return "";
		}
		try {
			PropertyResourceBundle prop = new PropertyResourceBundle(new FileInputStream(descriptionFile));
			String description = prop.getString(descname);
			if ( args != null )
			{
				for (int i=0; i<args.length; i++)
					description = description.replace("$"+i, args[i]);
			}
			return encString( description );
		} catch (FileNotFoundException e) {
			Mysys.println(e.toString());
		} catch (IOException e) {
			Mysys.println(e.toString());
		} catch (MissingResourceException e) {
			Mysys.println(e.toString());
		}
		return "";
	}
	
	public void endElement( String uri, String sn, String qn )
		throws SAXException
	{
	}
	
	public void characters( char [] buf, int off, int len )
		throws SAXException 
	{
	}

	public static String encString(String inStr)
	{
		Mysys mysys = factorMysys();
		if ( inStr == null )
			return null;
		try {
			return new String( inStr.getBytes( "iso8859-1" ), mysys.encoding );
		} catch ( java.io.UnsupportedEncodingException e ) {
			System.out.println(e.toString());
			return inStr;
		}
	}
	
	public static String getEncoding()
	{
		Mysys mysys = factorMysys();
		return mysys.encoding;
	}

/*	public static String getErrCodeFile()
	{
		if (out == null)
			initConfiguration();
		return errcodefile;
	}

	public static String getDlgCodeFile()
	{
		if (out == null)
			initConfiguration();
		return dlgcodefile;
	}
*/
	public static int getPackageSize()
	{
		Mysys mysys = factorMysys();
		return mysys.packageSize;
	}
	
	public static void error(Throwable t)
	{
		Mysys mysys = factorMysys();
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		StackTraceElement last = stack[3];
		try {
			mysys.err.write("ERROR [ " + last.toString() + " ]\n");
			while ( t != null )
			{
				mysys.err.write(t.toString() + "\n");
				mysys.err.flush();
				t = t.getCause();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void error(String s)
	{
		Mysys mysys = factorMysys();
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		try {
			StackTraceElement last = stack[2];
			mysys.err.write("ERROR [ " + last.toString() + " ] " + s + "\n");
			mysys.err.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void debugln(String s)
	{
		try
		{
			Mysys mysys = factorMysys();
			if ( mysys.isDebug )
			{
				StackTraceElement[] stack = Thread.currentThread().getStackTrace();
				StackTraceElement last = stack[3];
				println("DEBUG [ " + last.toString() + " ]" + s);
			}
		}
		catch (Exception e)
		{
			System.err.println("Mysys.java: " + e.toString());
		}
	}

	public static void debug(String s)
	{
		try
		{
			Mysys mysys = factorMysys();
			if ( mysys.isDebug )
				print(s);
		}
		catch (Exception e)
		{
			System.err.println("Mysys.java: " + e.toString());
		}
	}

	public static void print(String s)
	{
		try {
			Mysys mysys = factorMysys();
			mysys.out.write(s);
			mysys.out.flush();
		} catch (Exception e) {
			System.err.println("Niedozwolony błąd !!!");
			System.err.println(e.toString());
			System.err.println("Niedozwolony błąd !!!");
		}
	}

	public static void println() {
		try {
			Mysys mysys = factorMysys();
			mysys.out.write('\n');
			mysys.out.flush();
		} catch (Exception e) {
			System.err.println("Niedozwolony błąd !!!");
			System.err.println(e.toString());
			System.err.println("Niedozwolony błąd !!!");
		}
	}
	
	public static void println(String s) {
		try {
			Mysys mysys = factorMysys();
			mysys.out.write(s);
			mysys.out.write('\n');
			mysys.out.flush();
		} catch (Exception e) {
			System.err.println("Niedozwolony błąd !!!");
			System.err.println(e.toString());
			System.err.println("Niedozwolony błąd !!!");
		}
	}

	public static File getTempFile() throws IOException
	{
		Mysys mysys = factorMysys();
		if ( mysys.tempFolder != null )
			return File.createTempFile("genprj_", ".tmp", mysys.tempFolder);
		else
			return File.createTempFile("genprj_", ".tmp");
	}
	
	public static boolean closeQuiet(NumberWriter writer)
	{
		if ( writer != null )
		{
			try {
				writer.close();
			} catch (IOException e) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean closeQuiet(NumberReader reader)
	{
		if ( reader != null )
		{
			try {
				reader.close();
			} catch (NumberStoreException e) {
				return false;
			}
		}
		return true;
	}

}
