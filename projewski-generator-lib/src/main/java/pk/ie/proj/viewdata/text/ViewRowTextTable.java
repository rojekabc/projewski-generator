package pk.ie.proj.viewdata.text;

import pk.ie.proj.exceptions.LaborDataException;
import pk.ie.proj.exceptions.ViewDataException;
import pk.ie.proj.interfaces.LaborDataInterface;
import pk.ie.proj.interfaces.NumberInterface;
import pk.ie.proj.interfaces.ViewDataInterface;
import pk.ie.proj.labordata.FindMax;
import pk.ie.proj.labordata.FindMin;
import pk.ie.proj.tools.Convert;
import pk.ie.proj.tools.Mysys;
import pk.ie.proj.tools.NumberStoreOne;
import pk.ie.proj.tools.stream.NumberReader;

public class ViewRowTextTable
	extends ViewDataInterface
{
	private FindMin _min = null;
	private FindMax _max = null;
	NumberInterface _data = null;
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
//	private final static String TYPEOFDATA = "TypeOfData"; // na co ma odbywac sie konwersja

	public void initParameterInterface()
	{
		parameters.put(TITLENAME, null);
		parameters.put(ROWNAMES, new String[0]);
		parameters.put(FLAGS, Integer.valueOf(F_START_LINE | F_END_LINE));
		parameters.put(TABLELENGTH, Integer.valueOf(79));
		parameters.put(DATALENGTH, null);
		parameters.put(NAMELENGTH, Integer.valueOf(12));
		parameters.put(STRINGBETWEENDATA, " ");
		parameters.put(NUMOFDATA, null);
	}

	/** M4_GEN_PI_GETALLOWEDCLASS_I */
	public Class<?> [] getAllowedClass(String param) {
		Class<?> [] tmp = new Class[1];
		
		if ( param.equals(TITLENAME) )
			tmp[0] = String.class;
		else if ( param.equals(ROWNAMES) )
			tmp[0] = String[].class;
		else if ( param.equals(FLAGS) )
			tmp[0] = Integer.class;
		else if ( param.equals(TABLELENGTH) )
			tmp[0] = Integer.class;
		else if ( param.equals(DATALENGTH) )
			tmp[0] = Integer.class;
		else if ( param.equals(NAMELENGTH) )
			tmp[0] = Integer.class;
		else if ( param.equals(STRINGBETWEENDATA) )
			tmp[0] = String.class;
		else
			tmp = new Class[0];
		
		return tmp;
	}
	/** M4_GEN_VDI_GETVIEW  */
	public Object getView()
		throws ViewDataException
	{
		return null;
	}
	/** M4_GEN_VDI_GETVIEW_NSI  */
	public Object getView(NumberStoreOne data)
		throws ViewDataException
	{
		return null;
	}
	/** M4_GEN_VDI_GETVIEW_tNSI  */
	public Object getView(NumberStoreOne [] data)
		throws ViewDataException
	{
		return null;
	}
	/*
	 * Na podstawie zgromadzonych danych o minimum i maksimum następuje ustalenie
	 * maksymalnej długości danej wypisywanej
	 */
	protected int getDataLength()
	{
		int len = 0;
		try {
			String s = "";
			s += this._min.getMinimum().toString();
			len = s.length();
			s = "";
			s += this._max.getMaximum().toString();
			if ( s.length() > len )
				len = s.length();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		return len;
	}
	/** M4_GEN_VDI_SHOWVIEW  */
	public void showView()
		throws ViewDataException
	{
		String title;
		String [] rownames;
		String beedata = ""; // ciag między danymi
		int flags, tLength, dLength, nLength;
//		String bData = null;
		byte [] formLine = null;
//		java.util.Vector data = null;
		int numOfData = -1;
//		Class []types = null;
		
		// Przeinicjowanie zmiennych
//		if ( paramValue[INPUTDATA] != null )
//			data = (java.util.Vector)paramValue[ INPUTDATA ];
//		else
//			return; // TODO: Exception NO INPUT DATA
		// Test na zgodnosc klas w wektorze
//		for ( int i = 0; i < data.size(); i++ ) {
//			if ( data.get( i ) instanceof NumberStoreOne )
//				continue;
//			if ( data.get( i ) instanceof GeneratorInterface )
//				continue;
//			return; // TODO: Exception INCORRECT INPUT DATA TYPE
//		}
//
		
		title = (String) Convert.assignIfNull(parameters.get(TITLENAME), null);
		rownames = (String[]) Convert.assignIfNull(parameters.get(ROWNAMES), new String[0]);
		flags = Convert.tryToInt( Convert.assignIfNull(parameters.get(FLAGS), Integer.valueOf(0)) );
		tLength = Convert.tryToInt( Convert.assignIfNull(parameters.get(TABLELENGTH), Integer.valueOf(60)) );
		dLength = Convert.tryToInt( Convert.assignIfNull(parameters.get(DATALENGTH), Integer.valueOf(getDataLength())) );
		nLength = Convert.tryToInt( Convert.assignIfNull(parameters.get(NAMELENGTH), Integer.valueOf(12)) );
		beedata = (String) Convert.assignIfNull(parameters.get(STRINGBETWEENDATA), "");
		numOfData = Convert.tryToInt( Convert.assignIfNull(parameters.get(NUMOFDATA), Integer.valueOf(-1)) );
//		if ( paramValue[TYPEOFDATA] != null )
//			types = (Class[])paramValue[TYPEOFDATA];

		// Ustalanie typów używanych do pokazywania
//		if (types == null)
//		{
//			types = new Class[data.size()];
//			for ( int i = 0; i < types.length; i++ )
//				types[i] = Long.class;
//		}
//		if ( types.length < data.size() )
//		{
//			Class [] tmp = new Class[data.size()];
//			for ( int i = 0; i < tmp.length; i++ )
//				tmp[i] = Long.class;
//			for ( int i = 0; i < types.length; i++ )
//				tmp[i] = types[i];
//			types = tmp;
//		}

		// Formowanie linijki długiej do obrysowań
		formLine = new byte[tLength];
		for ( int i = 0; i < formLine.length; i++ )
			formLine[i] = '-';
		
		NumberReader reader = null;
		try {
		
		if ( (flags & F_START_LINE) == F_START_LINE )
			Mysys.println( new String(formLine) );
		if ( title != null ) {
			if ( (flags & F_BOUND_COL) == F_BOUND_COL )
				Mysys.println( "|" + formData(title, tLength-2, 1) + "|" );
			else
				Mysys.println( formData(title, tLength, 1) );
		}
		if ( (flags & F_TITLE_LINE) == F_TITLE_LINE )
			Mysys.println( new String(formLine) );
		
//		int nj = tempFile.size();
		int nx = tLength;
		numOfData = _data.getSize();
		
		// ustala najmniejszą możliwą liczbę danych - zabezpieczenie przed
		// wyjściem poza rozmiar tablicy
/*		for ( int i = 0; i < nj; i++ ) {
			if ( data.get( i ) instanceof NumberStoreOne )
			{
				int tmp = ((NumberStoreOne)data.get( i )).size();
				if ( numOfData == -1 )
					numOfData = tmp;
				else if ( tmp < numOfData )
					numOfData = tmp;
			}
		}*/

		if ( numOfData < 0 )
			return; // TODO: Exception CANNOT COUNT DATA AMMOUNT + Perhaps only generators in input data or no input data

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
		reader = _data.getNumberReader();
//		Object tmpObj;
		while (i < numOfData) {
			if ( (i != 0) && ((flags & F_ROW_LINE) == F_ROW_LINE) )
				Mysys.println( new String(formLine) );
				
//			for ( int j = 0; j<nj; j++ ) {
//				tmpObj = data.get( j );
				prow = "";
				if ( (flags & F_BOUND_COL) == F_BOUND_COL )
					prow += "| ";
//				if ( (j<rownames.length)&&(rownames[j] != null ))
//					prow += formData(rownames[j], nLength, 0);
				
				for ( int a=0; (a<nx) && (a+i<numOfData); a++ ) {
					tmpNum = reader.readAsObject(_data.getStoreClass()).toString();
//					if ( tmpObj instanceof NumberStoreOne )
//						tmpNum = ((NumberStoreOne)tmpObj).getAsString(a+i);
//					else if ( tmpObj instanceof GeneratorInterface )
//						tmpNum = ((GeneratorInterface)tmpObj).getAsObject(types[j]).toString();
//					else
//						tmpNum = "ERR";
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
				Mysys.println( prow );
//			}
			i += nx;
		}
		
		
		if ( (flags & F_END_LINE) == F_END_LINE )
			Mysys.println( new String(formLine) );

		} catch ( Exception e ) {
			Mysys.println( e.toString() );
			e.printStackTrace();
		} finally {
			Mysys.closeQuiet(reader);
		}
	}
	/** M4_GEN_VDI_SHOWVIEW_NSI  */
/*	public void showView(NumberStoreOne data)
		throws ViewDataException
	{
		java.util.Vector svVec;
		if ( paramValue[ INPUTDATA ] == null )
			svVec = null;
		else
			svVec = (java.util.Vector)paramValue[ INPUTDATA ]; // zachowanie
		setData( data ); // ustawienie danych
		try {
		Object o = data.get();
		} catch ( Exception e ) {
		}
		showView(); // pokazanie
		paramValue[ INPUTDATA ] = svVec; // przywrócenie
	}*/
	/** M4_GEN_VDI_SHOWVIEW_tNSI  */
/*	public void showView(NumberStoreOne [] data)
		throws ViewDataException
	{
		java.util.Vector svVec;
		if ( paramValue[ INPUTDATA ] == null )
			svVec = null;
		else
			svVec = (java.util.Vector)paramValue[ INPUTDATA ]; // zachowanie
		setData( data ); // ustawienie danych
		showView(); // pokazanie
		paramValue[ INPUTDATA ] = svVec; // przywrócenie
	}*/
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
		if ( _min == null )
			_min = new FindMin();
		if ( _max == null )
			_max = new FindMax();
		try
		{
			_min.setInputData(data);
			_max.setInputData(data);
			_data = data;
		}
		catch ( LaborDataException e )
		{
			e.printStackTrace();
		}
//		java.util.Vector v = new java.util.Vector();
//		v.add( data );
//		paramValue[ INPUTDATA ] = v;
	}
/*	public void setData(NumberStoreOne [] data)
		throws ViewDataException
	{
		java.util.Vector v = new java.util.Vector();
		for ( int i = 0; i < data.length; i++ )
			v.add( data[i] );
		paramValue[ INPUTDATA ] = v;
	}*/
}
