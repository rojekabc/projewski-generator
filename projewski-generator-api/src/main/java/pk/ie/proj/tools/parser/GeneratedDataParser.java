/**
 * 
 */
package pk.ie.proj.tools.parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import pk.ie.proj.enumeration.ClassEnumerator;
import pk.ie.proj.interfaces.ParameterInterface;
import pk.ie.proj.tools.Convert;
import pk.ie.proj.tools.GeneratedData;
import pk.ie.proj.tools.Mysys;
import pk.ie.proj.tools.ParameterStoreFile;

/**
 * @author projewski
 *
 */
public class GeneratedDataParser extends DefaultHandler {

	private ParameterStoreFile readSource = null;
	private String xmlBuf = "";
	
	GeneratedData gdt = null;
	
	public GeneratedDataParser(GeneratedData gdt)
	{
		this.gdt = gdt;
	}

	public void startDocument()
		throws SAXException
	{
		Mysys.debugln("Start DOCUMENT");
	}

	public void endDocument()
		throws SAXException
	{
	}

	public void startElement( String uri, String sn, String qn, Attributes att )
		throws SAXException
	{
		String name = sn;
		if ( name.equals("") )
			name = qn;
//		aElm = name;
		if ( name.equals("Pack") )
		{
			String classname = att.getValue("classtype");
			this.gdt.setSize( Convert.tryToInt(att.getValue("count")) );
			if ( classname.equalsIgnoreCase("int") )
				this.gdt.setStoreClass(ClassEnumerator.INTEGER);
			else if ( classname.equalsIgnoreCase("long") )
				this.gdt.setStoreClass(ClassEnumerator.LONG);
			else if ( classname.equalsIgnoreCase("float") )
				this.gdt.setStoreClass(ClassEnumerator.FLOAT);
			else if ( classname.equalsIgnoreCase("double") )
				this.gdt.setStoreClass(ClassEnumerator.DOUBLE);
		}
		else if ( name.equals("Source") )
		{
			if (readSource == null)
			{
				readSource = new ParameterStoreFile();
			}
			readSource.openObject("Source", att.getValue("class"));
		}
		else if (( name.equals("parameter") ) && (readSource != null) )
		{
			// ustawiany jest parametr zrodla
			String paramname=null, classname=null;
			int i;
			// wyciagniecie wymaganych ustawien parametru
			for (i=0; i<att.getLength(); i++)
			{
				if ( att.getQName(i).equals("class") )
					classname = att.getValue(i);
				else if ( att.getQName(i).equals("name") )
					paramname = att.getValue(i);
			}
			// otworzenie obiektu podrzednego
			readSource.openObject(paramname, classname);
		}
		else if ( name.equals("DateTime") )
		{
			this.gdt.setDatumTick( Convert.tryToLong( att.getValue("tick") ) );
		}

	}
	public void endElement( String uri, String sn, String qn )
		throws SAXException
	{
		try
		{
			String name = sn;
			if ( name.equals("") )
				name = qn;
			xmlBuf = xmlBuf.trim();
			// Przesyłaj paczki danych po ich odczytaniu
			if ( (name.equals("parameter")) && (readSource != null) )
			{
				readSource.setObjectValue(xmlBuf);
				readSource.closeObject();
			}
			else if ( name.equals("Source") )
			{
				this.gdt.setDataSource( (ParameterInterface)readSource.getLastObject() );
				readSource = null;
				Mysys.debugln("I finitialy build source object");
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			xmlBuf = "";
		}
	}
	public void characters( char [] buf, int off, int len )
		throws SAXException 
	{
		String s = new String( buf, off, len );
		xmlBuf += s;
	}
}
