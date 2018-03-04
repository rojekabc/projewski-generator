package pl.projewski.generator.viewdata.swing;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import pl.projewski.generator.exceptions.GeneratorException;
import pl.projewski.generator.exceptions.NumberStoreException;
import pl.projewski.generator.exceptions.ViewDataException;
import pl.projewski.generator.interfaces.GeneratorInterface;
import pl.projewski.generator.interfaces.NumberInterface;
import pl.projewski.generator.interfaces.ViewDataInterface;
import pl.projewski.generator.tools.Convert;
import pl.projewski.generator.tools.Mysys;
import pl.projewski.generator.tools.stream.NumberReader;

public class ViewRowSwingTable
	extends ViewDataInterface
	implements WindowListener
{
	public final static int F_START_LINE = 1;
	public final static int F_END_LINE = 2;
	public final static int F_BOUND_COL = 4;
	public final static int F_ROW_LINE = 8;
	public final static int F_TITLE_LINE = 16;
	public final static int F_NAME_COL_LINE = 32;
	public final static int F_NAME_LINE = 64;

	private final static String TITLENAME = "TableName";
	private final static String ROWNAMES = "RowNames";
	private final static String FLAGS = "Flags";
	private final static String TABLELENGTH = "TableLength";
	private final static String DATALENGTH = "DataLength";
	private final static String NAMELENGTH = "NameLength";
	private final static String STRINGBETWEENDATA = "StringBetweenData";
//	private final static String INPUTDATA = "InputData";
	private final static String NUMOFDATA = "NumOfData";
	private final static String TYPEOFDATA = "TypeOfData"; // na co ma odbywac sie konwersja
	
	private File dataFile;
	private Vector<Object> vec;

	public void initParameterInterface()
	{
		parameters.put(TITLENAME, null);
		parameters.put(ROWNAMES, new String[0]);
		parameters.put(FLAGS, Integer.valueOf(0));
		parameters.put(TABLELENGTH, Integer.valueOf(60));
		parameters.put(DATALENGTH, Integer.valueOf(8));
		parameters.put(NAMELENGTH, Integer.valueOf(12));
		parameters.put(STRINGBETWEENDATA, " ");
//		parameters.put(INPUTDATA, null);
		parameters.put(NUMOFDATA, null);
		parameters.put(TYPEOFDATA, null);
	}

	/** M4_GEN_PI_GETALLOWEDCLASS_I */
	public Class<?> [] getAllowedClass(String param) {
		Class<?> [] tmp = new Class[1];
		if (param.equals(TITLENAME) || param.equals(STRINGBETWEENDATA))
		{
			tmp[0] = String.class;
		}
		else if ( param.equals(ROWNAMES) )
		{
			tmp[0] = String[].class;			
		}
		else if ( param.equals(FLAGS) || param.equals(TABLELENGTH) || param.equals(DATALENGTH)
			|| param.equals(NAMELENGTH) )
		{
			tmp[0] = Integer.class;			
		}
//		else if ( param.equals(INPUTDATA) )
//		{
//			tmp = new Class[2];
//			tmp[0] = NumberInterface.class;
//			tmp[1] = GeneratorInterface.class;
//		}
		else if ( param.equals(TYPEOFDATA) )
		{
			tmp = new Class[5]; // Jezeli jako pierwszy to klasa to nastepne okreslaja
			// dostepne klasy do wyboru. A jesli jest to tablica to moze to byc
			// tablica, w ktorej elementy - klasy sa do wyboru
			tmp[0] = Class.class;
			tmp[1] = Long.class;
			tmp[2] = Integer.class;
			tmp[3] = Float.class;
			tmp[4] = Double.class;
		}
		else
		{
			tmp = new Class[0];
		}
		return tmp;
	}
	/** M4_GEN_VDI_GETVIEW  */
	public Object getView()
		throws ViewDataException
	{
		return null;
	}

	/** M4_GEN_VDI_SHOWVIEW  */
	public void showView()
		throws ViewDataException
	{
		String title = null;
		String [] rownames = new String[0];
		String beedata = ""; // ciag między danymi
		int flags = 0, tLength = 60, dLength = 8, nLength = 12;
//		String bData = null;
		byte [] formLine = null;
//		Vector<Object> data = null;
		int numOfData = -1;
		Class<?> []types = null;

		JFrame frame;
		JScrollPane scrDesc;
		JTextPane paneDescr;
		dataFile = null;
		java.io.PrintStream dataStream = null;
		try {
			dataFile = Mysys.getTempFile();
			dataStream = new java.io.PrintStream(
					new FileOutputStream( dataFile ) );
		} catch ( Exception e ) {
//			if ( dataStream != null )
//				dataStream.close();
			if ( dataFile != null )
			{
				if ( !dataFile.delete() )
					Mysys.error("Nieudane usuwanie tymczasowego pliku " + dataFile.getName());
			}
			throw new ViewDataException(e);
		}
		// Tymczasowy plik do odczytania

		frame = new JFrame("Rezultaty");
		scrDesc = new JScrollPane();
		paneDescr = new JTextPane();
		paneDescr.setEditable( false );
		scrDesc.getViewport().setView( paneDescr );
//		frame.setContentPane( new javax.swing.JPanel() );
//		frame.getContentPane().setLayout(new java.awt.GridLayout(1, 1));
		frame.getContentPane().add( scrDesc );
		frame.addWindowListener(this);
		
		// Przeinicjowanie zmiennych
//		if ( parameters.get(INPUTDATA) != null )
//			data = (java.util.Vector)parameters.get( INPUTDATA );
//		else
		if ( vec == null )
			throw new ViewDataException("No inputData"); // TODO: Tekst
		// Test na zgodnosc klas w wektorze
		for ( int i = 0; i < vec.size(); i++ ) {
			if ( vec.get( i ) instanceof NumberInterface)
				continue;
			if ( vec.get( i ) instanceof GeneratorInterface)
				continue;
			throw new ViewDataException("Incorrect Input Data"); // TODO: Tekst
		}

		if ( parameters.get(TITLENAME) != null )
			title = (String)parameters.get(TITLENAME);
		if ( parameters.get(ROWNAMES) != null )
			rownames = (String[])parameters.get(ROWNAMES);
		if ( parameters.get(FLAGS) != null )
			flags = Convert.tryToInt( parameters.get(FLAGS) );
		if ( parameters.get(TABLELENGTH) != null )
			tLength = Convert.tryToInt( parameters.get(TABLELENGTH) );
		if ( parameters.get(DATALENGTH) != null )
			dLength = Convert.tryToInt( parameters.get(DATALENGTH) );
		if ( parameters.get(NAMELENGTH) != null )
			nLength = Convert.tryToInt( parameters.get(NAMELENGTH) );
		if ( parameters.get(STRINGBETWEENDATA) != null )
			beedata = (String)parameters.get(STRINGBETWEENDATA);
		if ( parameters.get(NUMOFDATA) != null )
			numOfData = Convert.tryToInt( parameters.get(NUMOFDATA) );
		if ( parameters.get(TYPEOFDATA) != null )
			types = (Class[])parameters.get(TYPEOFDATA);

		// Ustalanie typów używanych do pokazywania
		if (types == null)
		{
			types = new Class[vec.size()];
			for ( int i = 0; i < types.length; i++ )
				types[i] = Long.class;
		}
		if ( types.length < vec.size() )
		{
			Class<?> [] tmp = new Class[vec.size()];
			for ( int i = 0; i < tmp.length; i++ )
				tmp[i] = Long.class;
			for ( int i = 0; i < types.length; i++ )
				tmp[i] = types[i];
			types = tmp;
		}

		// Formowanie linijki długiej do obrysowań
		formLine = new byte[tLength];
		for ( int i = 0; i < formLine.length; i++ )
			formLine[i] = '-';
		
		Map<NumberInterface, NumberReader> readers  = null;
		
		try {
		
			if ( (flags & F_START_LINE) == F_START_LINE )
				dataStream.println( new String(formLine) );
			if ( title != null ) {
				if ( (flags & F_BOUND_COL) == F_BOUND_COL )
					dataStream.println( "|" + formData(title, tLength-2, 1) + "|" );
				else
					dataStream.println( formData(title, tLength, 1) );
			}
			if ( (flags & F_TITLE_LINE) == F_TITLE_LINE )
				dataStream.println( new String(formLine) );
			
			int nj = vec.size();
			int nx = tLength;
			
			// ustala najmniejszą możliwą liczbę danych - zabezpieczenie przed
			// wyjściem poza rozmiar tablicy
			for ( int i = 0; i < nj; i++ ) {
				if ( vec.get( i ) instanceof NumberInterface )
				{
					int tmp = ((NumberInterface)vec.get( i )).getSize();
					if ( numOfData == -1 )
						numOfData = tmp;
					else if ( tmp < numOfData )
						numOfData = tmp;
				}
			}
	
			if ( numOfData < 0 )
				throw new ViewDataException("CANNOT COUNT DATA AMMOUNT + Perhaps only generators in input data or no input data");
	
			// Ustalanie nx, czyli ile danych wypadnie w jednym wierszu na podstawie
			// dlugosci roznych dodatkow itp, ale tak, aby nie wyszly poza obrys
			if ( (flags & F_BOUND_COL) == F_BOUND_COL ) // jeśli będą kolumny
				nx -= 2;
			if (rownames.length>0) // Jeżeli są nazwy wierszy
			{
				nx -= nLength;
				if ( (flags & F_NAME_COL_LINE) == F_NAME_COL_LINE ) // kolumna dzieląca
					nx--;
			}
			nx /= ( dLength+beedata.length() ); // Wydziel przez dlugość danych+podzial
			
			String prow;
			int i = 0;
			String tmpNum;
			Object tmpObj;
			readers = new HashMap<NumberInterface, NumberReader>();
			while (i < numOfData) {
				if ( (i != 0) && ((flags & F_ROW_LINE) == F_ROW_LINE) )
					dataStream.println( new String(formLine) );
					
				for ( int j = 0; j<nj; j++ ) {
					tmpObj = vec.get( j );
					prow = "";
					if ( (flags & F_BOUND_COL) == F_BOUND_COL )
						prow += "| ";
					if ( (j<rownames.length)&&(rownames[j] != null ))
						prow += formData(rownames[j], nLength, 0);
					
					for ( int a=0; (a<nx) && (a+i<numOfData); a++ ) {
						if ( tmpObj instanceof NumberInterface )
						{
							NumberReader reader = readers.get(tmpObj);
							NumberInterface numIn = (NumberInterface)tmpObj;
							if ( reader == null )
								readers.put(numIn, reader = numIn.getNumberReader());
							tmpNum = reader.readAsObject(numIn.getStoreClass()).toString();
						}
						else if ( tmpObj instanceof GeneratorInterface )
							tmpNum = ((GeneratorInterface)tmpObj).getAsObject(types[j]).toString();
						else
							tmpNum = "ERR";
						prow += formData( tmpNum, dLength, 2 );
						if ( a != nx - 1 )
							prow += beedata;
					}
					while ( prow.length()<tLength-1 )
						prow += ' ';
					if ( (flags & F_BOUND_COL) == F_BOUND_COL )
						prow += "| ";
					else
						prow += ' ';
					dataStream.println( prow );
				}
				i += nx;
			}
			
			
			if ( (flags & F_END_LINE) == F_END_LINE )
				dataStream.println( new String(formLine) );
		} catch (NumberStoreException e) {
			throw new ViewDataException(e);
		} catch (GeneratorException e) {
			throw new ViewDataException(e);
		} finally {
			dataStream.flush();
			dataStream.close();
			if ( readers != null )
			{
				Iterator<NumberReader> kolekcja = readers.values().iterator();
				while ( kolekcja.hasNext() )
				{
					Mysys.closeQuiet(kolekcja.next());
				}
			}
//			if ( !dataFile.delete() )
//				Mysys.error("Nie moge usunac danych tymczasowych");
		}

		try {
			paneDescr.setPage( dataFile.toURL() );
		} catch (MalformedURLException e) {
			throw new ViewDataException(e);
		} catch (IOException e) {
			throw new ViewDataException(e);
		}
		paneDescr.setCaretPosition( 0 );
		
		frame.setBounds( 0, 0, 500, 200 );
//		Convert.setCentral( frame, 400, 200 );
//		Convert.setCentral( frame, 400, 200 );
//		frame.pack();
		frame.setVisible(true);
		frame.setBounds( 0, 0, 500, 200 );
//		Convert.setCentral( frame, 400, 200 );


	}
	// justify: 0 Left   1 Center   2 Right
	private String formData(String text, int length, int justify) {
		byte [] tmp;
		String out;

		if ( text.length() < length ) {
			if ( justify == 1 )
				tmp = new byte [(length-text.length())/2];
			else
				tmp = new byte [length-text.length()];
			for ( int i = 0; i < tmp.length; i++ )
				tmp[i] = ' ';

			switch ( justify ) {
				case 0:
					out = text + (new String(tmp));
					break;
				case 1:
					out = (new String(tmp)) + text + (new String(tmp));
					break;
				case 2:
					out = (new String(tmp)) + text;
					break;
				default:
					return "";
			}
		} else {
			out = text.substring( 0, length );
		}
		while ( out.length() < length )
			out += ' ';
		return out;

	}
	/** M4_GEN_VDI_REFRESHVIEW  */
	public void refreshView()
		throws ViewDataException
	{
	}
	/** M4_GEN_VDI_SETDATA_NSI  */
	public void setData(NumberInterface data)
		throws ViewDataException
	{
//		Object obj = parameters.get(INPUTDATA);
		if ( vec == null )
			vec = new Vector<Object>();
		vec.add(data);
//		parameters.put(INPUTDATA, vec);
	}

	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowClosing(WindowEvent e) {
		((JFrame)e.getSource()).dispose();
		if ( dataFile != null )
		{
			if ( !dataFile.delete() )
				Mysys.error("Problem z usunięciem pliku tymczasowego");
		}
	}

	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
