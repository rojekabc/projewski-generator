package pk.ie.proj.tools;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.helpers.DefaultHandler;

import pk.ie.proj.enumeration.ClassEnumerator;
import pk.ie.proj.exceptions.NumberStoreException;
import pk.ie.proj.interfaces.NumberInterface;
import pk.ie.proj.interfaces.ParameterInterface;
import pk.ie.proj.tools.parser.GeneratedDataParser;
import pk.ie.proj.tools.stream.NumberReader;
import pk.ie.proj.tools.stream.NumberWriter;
import pk.ie.proj.tools.stream.SeparatorStreamReader;
import pk.ie.proj.tools.stream.SeparatorStreamWriter;

public class GeneratedData implements NumberInterface
//extends DefaultHandler
{
	// jezeli plik danych juz istnieje to bedzie nastepowalo dopisywanie do
	// jego konca nowych paczek danych
	private long datumTick = 0; // czas generacji
	private String name = null; // nazwa (z niej powstaja nazwy plikow)
	private ParameterInterface source = null;
	private int dataCount = 0;
	private ClassEnumerator classType = null;
	
	private NumberWriter writer = null;
	
	private java.io.PrintStream infoStream = null;	
	
	public GeneratedData( String generationName )
	{
		this( generationName, System.currentTimeMillis(), null );
	}
	
	public GeneratedData( )
	{
		this( null, System.currentTimeMillis(), null );
	}

	// zalozenie obiektu
	public GeneratedData( long dataTick )
	{
		this( null, dataTick, null);
	}

	// zalozenie obiektu
	public GeneratedData( String generationName, long dataTick )
	{
		this( generationName, dataTick, null );
	}

	public GeneratedData( String generationName, ParameterInterface src )
	{
		this( generationName, System.currentTimeMillis(), src );
	}

	public GeneratedData( String generationName, long dataTick, ParameterInterface src )
	{
		if ( (generationName != null) && (generationName.trim().length() != 0) )
			name = generationName.trim();
		datumTick = dataTick;
		source = src;
// PROPONOWANE MARKOWANIE
//		java.io.File plik = new java.io.File( name + ".gdt" );
//		if ( plik.exists() )
//		{
//			readFile(plik);
//		}
	}
	
	public static GeneratedData createTemporary() throws IOException
	{
		File tmp = Mysys.getTempFile();
		GeneratedData gdt = new GeneratedData(tmp.getAbsolutePath());
		gdt.setDatumTick( System.currentTimeMillis() );
		if ( !tmp.delete() )
			Mysys.encString("Nieudane usuwanie tymczasowego pliku " + tmp.getName());
		return gdt;
	}
	
	public boolean delete()
	{
		boolean ret = true;
		File f = new File(getFileName());
		if ( f.exists() )
			ret &= f.delete();
		f = new File(getDataFilename());
		if ( f.exists() )
			ret &= f.delete();
		return ret;
	}
	
	public void setDatumTick(long datumTick)
	{
		this.datumTick = datumTick;
	}
	
	public void setDataSource(ParameterInterface source)
	{
		this.source = source;
	}
	
	protected boolean readFile( java.io.File plik ) throws NumberStoreException
	{
			try {
				DefaultHandler han = new GeneratedDataParser(this);
				SAXParserFactory fac = SAXParserFactory.newInstance();
				SAXParser par = fac.newSAXParser();
				par.parse( plik, han );
				Mysys.debugln("Read file");
				return true;
			} catch ( Exception e ) {
				throw new NumberStoreException(e);
			}
	}
	
	public boolean readFile() throws NumberStoreException
	{
		File f = new File( getFileName() );
		if ( f.exists() )
			return readFile( new File( getFileName() ) );
		return false;
	}
	
	public boolean wrtieFile() throws FileNotFoundException
	{
		datumTick = System.currentTimeMillis();
		infoStream=new java.io.PrintStream(new java.io.FileOutputStream(this.getFileName()));
		infoStream.println("<?xml version='1.0' encoding='Latin1'?>");
		infoStream.println("<GeneratedData>");
		java.util.Date date = new java.util.Date( datumTick );
		java.text.DateFormat df = java.text.DateFormat.getDateTimeInstance();
		infoStream.println("<DateTime tick='" + datumTick + "' date='" + df.format(date) + "' />");
		
		String classtypename = null;
		if ( classType == ClassEnumerator.INTEGER )
			classtypename="Int";
		else if ( classType == ClassEnumerator.LONG )
			classtypename="Long";
		else if ( classType == ClassEnumerator.FLOAT )
			classtypename="Float";
		else if ( classType == ClassEnumerator.DOUBLE )
			classtypename="Double";
		else
			return false;
		
		if ( source != null )
		{
			ParameterStoreFile.writeParameterInterface( infoStream, source, "Source" );
		}
		infoStream.println("<Pack count='"
				+ this.dataCount + "' file='"
				+ this.getDataFilename() + "' classtype='"
				+ classtypename + "'/>");
		
		infoStream.println("</GeneratedData>");
		infoStream.close();
		return true;
	}
	
	// zwraca nazwe pliku, jaki jest uzywany do przechowywania/pobierania danych
	public String getFileName()
	{
		return name+".gdt";
	}
	
	public String getDataFilename()
	{
		return name+".dat";
	}
	
	public long getDataTick()
	{
		return datumTick;
	}
	
	public String getName()
	{
		return name;
	}
	
	// toString
	public String toString()
	{
		return name;
	}

	public boolean equals(Object o)
	{
		if ( o == null )
			return false;
		if ( ! (o instanceof GeneratedData) )
			return false;
		return name.equals(o.toString());
	}
	
	public int hashCode()
	{
		if ( name != null )
			return name.hashCode();
		else
			return 0;
	}

	public NumberReader getNumberReader() throws NumberStoreException {
		try {
			return new SeparatorStreamReader(new FileInputStream(this.getDataFilename()));
		} catch (FileNotFoundException e) {
			throw new NumberStoreException(e);
		}
	}

	public NumberWriter getNumberWriter() throws NumberStoreException {
		if ( writer == null )
		{
			try {
				writer = new SeparatorStreamWriter(new FileOutputStream(this.getDataFilename()));
			} catch (FileNotFoundException e) {
				throw new NumberStoreException(e);
			}
		}
		return writer;
	}

	public int getSize() {
		return this.dataCount;
	}

	public ClassEnumerator getStoreClass() {
		return this.classType;
	}

	public void setStoreClass(ClassEnumerator c) {
		this.classType = c;
	}

	public void setSize(int size) {
		this.dataCount = size;		
	}

	public ParameterInterface getDataSource() throws NumberStoreException {
		return source;
	}
	
}
