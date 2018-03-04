package pk.ie.proj.tools;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import pk.ie.proj.exceptions.GeneratorException;
import pk.ie.proj.interfaces.GeneratorInterface;

public class GeneratorStoreFile
extends DefaultHandler
{
	private ParameterStoreFile buildStructure = null;
	String readBuf = null;
	/** M4_GENERATOR_STORE_FILE_VAR_BUILD_STRUCTURE */
//	private static java.util.Vector buildStructure = new java.util.Vector();

	/** M4_GENERATOR_STORE_FILE_FUN_LOAD_GENERATOR_S */
	public static GeneratorInterface loadGeneratorFromFile( String filename )
		throws GeneratorException
	{
		try {
			GeneratorInterface giRet = null;
			GeneratorStoreFile han = new GeneratorStoreFile();
			SAXParserFactory fac = SAXParserFactory.newInstance();
			SAXParser par = fac.newSAXParser();
			File file = new File(filename);
			if ( !file.exists() )
				throw new GeneratorException(GeneratorException.FILE_NOT_EXISTS, filename);
			par.parse( file, han );
//			if ( buildStructure.size() == 0 )
//				return null;
			giRet = (GeneratorInterface)han.buildStructure.getLastObject();
			han.buildStructure.closeObject();
			return giRet;
//			return (GeneratorInterface)buildStructure.get(0);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (javax.xml.parsers.ParserConfigurationException e) {
			e.printStackTrace();
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/** M4_GENERATOR_STORE_FILE_FUN_SAVE_GENERATOR_TO_FILE_S_GI */
	public static void saveGeneratorToFile( String filename,
			GeneratorInterface gen )
		throws GeneratorException
	{
		try {
			java.io.File f = new java.io.File( filename );

			if ( f.exists() )
				throw new GeneratorException(GeneratorException.FILE_ALREADY_EXISTS,
						filename );

			PrintStream savefile = new PrintStream(new FileOutputStream( filename ));
			savefile.println("<?xml version='1.0' encoding='Latin1' ?>");
			ParameterStoreFile.writeParameterInterface( savefile, gen, "generator" );
		
			savefile.close();
		} catch ( java.io.IOException e ) {
				String [] tab = new String[2];
				tab[0] = filename;
				tab[1] = e.toString();
				throw new GeneratorException(GeneratorException.FILE_WRITE_ERROR,
						tab );
		}
	}
	/** 
	* Metoda wywoływana przy rozpoczęciu analizy dokumentu XML
 */
	public void startDocument()
		throws SAXException
	{
		buildStructure = new ParameterStoreFile();
		readBuf = "";
//		buildStructure.clear();
	}

	/** 
	* Metoda wywoływana przy zakończeniu analizy dokumentu XML
 */
	public void endDocument()
		throws SAXException
	{
	}

	/** 
	* Metoda wywoływana przy rozpoczęciu analizowania znacznika XML
 */
	public void startElement( String uriname, String sname, String qname,
			Attributes attr )
		throws SAXException
	{
		try {
			String name = sname;
			readBuf = "";
			if ( name.equals("") ) name = qname;
			if ( name.equals("parameter") || name.equals("generator") )
			{
				String clName = null, parName = null;
				for ( int i=0; i<attr.getLength(); i++ )
				{
					if ( attr.getQName(i).equals("class") )
						clName = attr.getValue(i);
					else if ( attr.getQName(i).equals("name") )
						parName = attr.getValue(i);
					else
						System.out.println("Nieznany atrybut generatora "+attr.getQName(i));
				}
				Mysys.debugln("Open object "+parName+" of "+clName);
				buildStructure.openObject( parName, clName );
			}
/*			if ( name.equals("generator") ) {
				for ( int i=0; i<attr.getLength(); i++ )
				{
					if ( attr.getQName( i ).equals("class") )
							buildStructure.add( Class.forName(attr.getValue(i)).newInstance() );
					else
						System.out.println("Nieznany atrybut generatora "+attr.getQName(i));
				}
			} else if ( name.equals("Integer") ) {
				return;
			} else if ( name.equals("Float") ) {
				return;
			} else if ( name.equals("Long") ) {
				return;
			} else if ( name.equals("Double") ) {
				return;
			} else if ( name.equals("parameter") ) {
				for ( int i=0; i<attr.getLength(); i++ )
				{
					if ( attr.getQName( i ).equals("name") )
						buildStructure.add( new String(attr.getValue(i)) );
					else
						System.out.println("Nieznany atrybut parametru "+attr.getQName(i));
				}
			} else if ( name.equals("Vector") ) {
				return;
			} else if ( name.equals("NumberStore") ) {
				return;
			}*/
		} catch ( Exception e ) {
			System.out.println( "GeneratorStoreFile.startElement " + e.toString() );
		}
	}

	/** 
	* Metoda wywoływana przy zamknięciu analizowania znacznika XML
 */
	public void endElement( String uriname, String sname, String qname )
	{
		try {
			String name = sname;
			if ( name.equals("") ) name = qname;
			if ( name.equals("parameter") )
			{
				readBuf = readBuf.trim();
				buildStructure.setObjectValue(readBuf);
				readBuf = "";
				Mysys.debugln("Close object ");
				buildStructure.closeObject();
			}
			else if ( name.equals("generator") )
			{
				readBuf = "";
			}
/*				return;
			} else if ( name.equals("Integer") ) {
				Object o = new Integer( buildStructure.lastElement().toString() );
				buildStructure.remove( buildStructure.size() - 1 );
				buildStructure.add( o );
			} else if ( name.equals("Float") ) {
				Object o = new Float( buildStructure.lastElement().toString() );
				buildStructure.remove( buildStructure.size() - 1 );
				buildStructure.add( o );
			} else if ( name.equals("Long") ) {
				Object o = new Long( buildStructure.lastElement().toString() );
				buildStructure.remove( buildStructure.size() - 1 );
				buildStructure.add( o );
			} else if ( name.equals("Double") ) {
				Object o = new Double( buildStructure.lastElement().toString() );
				buildStructure.remove( buildStructure.size() - 1 );
				buildStructure.add( o );
			} else if ( name.equals("parameter") ) {
				int i = buildStructure.size()-1;
				Object paramVal = buildStructure.get(i);
				buildStructure.remove(i);
				if ( paramVal.toString().equals("NULL") )
					paramVal = null;
				i--;
				String paramName = (String)buildStructure.get(i);
				buildStructure.remove(i);
				i--;
				if ( buildStructure.size() == 0 )
					return;
				ParameterInterface pin = (ParameterInterface)buildStructure.get(i);
				pin.setParameter( paramName, paramVal );
				return;
			} else if ( name.equals("Vector") ) {
				return;
			} else if ( name.equals("NumberStore") ) {
				return;
			} else
				System.out.println("Nieznany przełącznik " + name );*/
		} catch ( Exception e ) {
			System.out.println( "endElement " + e.toString() );
		}
	}

	/** 
	* Metoda wywoływana po uzyskaniu zawartości znacznika XML
 */
	public void characters( char[] buf, int offset, int len )
		throws SAXException
	{
		String s = new String( buf, offset, len );
		readBuf += s;
	}
}
