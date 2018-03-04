/**
 * 
 */
package pl.projewski.generator.viewdata.text;

import pl.projewski.generator.exceptions.LaborDataException;
import pl.projewski.generator.exceptions.NumberStoreException;
import pl.projewski.generator.exceptions.ParameterException;
import pl.projewski.generator.exceptions.ViewDataException;
import pl.projewski.generator.interfaces.NumberInterface;
import pl.projewski.generator.interfaces.ParameterInterface;
import pl.projewski.generator.interfaces.ViewDataInterface;
import pl.projewski.generator.labordata.FindMax;
import pl.projewski.generator.labordata.Frequency;
import pl.projewski.generator.tools.Convert;
import pl.projewski.generator.tools.Mysys;
import pl.projewski.generator.tools.stream.NumberReader;

/**
 * @author projewski
 *
 */
public class PrintFreq extends ViewDataInterface {

	NumberInterface data;
	public final static String LINESAMMOUNT = "Liczba linii";

	@Override
	public boolean canViewData(ParameterInterface dataSource) {
		return dataSource instanceof Frequency;
	}

	/* (non-Javadoc)
	 * @see pk.ie.proj.interfaces.ViewDataInterface#getView()
	 */
	@Override
	public Object getView() throws ViewDataException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see pk.ie.proj.interfaces.ViewDataInterface#refreshView()
	 */
	@Override
	public void refreshView() throws ViewDataException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see pk.ie.proj.interfaces.ViewDataInterface#setData(pk.ie.proj.interfaces.NumberInterface)
	 */
	@Override
	public void setData(NumberInterface data) throws ViewDataException {
		this.data = data;
	}

	/* (non-Javadoc)
	 * @see pk.ie.proj.interfaces.ViewDataInterface#showView()
	 */
	@Override
	public void showView() throws ViewDataException {
		try {
			ParameterInterface source = data.getDataSource();
			NumberReader reader = data.getNumberReader();
			FindMax maxLD = new FindMax();
			maxLD.setInputData(data);
			int max = Convert.tryToInt( maxLD.getMaximum() );
			int numClass = Convert.tryToInt( source.getParameter( Frequency.CLASSAMMOUNT ) );
			int numLines = Convert.tryToInt( parameters.get(LINESAMMOUNT) );
//			Mysys.debugln( "Odczytano: " + numClass );
//			Mysys.debugln("Max: " + max);
			char [][] tabs = new char[numLines][numClass];
			for ( int i=0; i<numClass; i++ )
			{
				for ( int j=0; j<numLines; j++ )
				{
					if ( j!=0 )
						tabs[j][i] = ' ';
					else
						tabs[j][i] = '-';
				}
//				Mysys.debugln("Pozycja " + pos);
				int pos = reader.readInt() * (numLines-1) / max;
				if ( pos != 0 )
					tabs[pos][i] = '-';
				else
					tabs[pos][i] = '=';
			}
			
			Mysys.println("Widok: " + this.toString());
			Mysys.println("Test: " + source.toString());
			
			// TODO: Zaraz numerowania linii powinna by� liczebno��
			System.out.println("  ^");
			while ( numLines-- > 0 )
			{
				if ( numLines < 10 )
				{
					if ( numLines > 0 )
						System.out.println( " " + numLines + "|" + new String(tabs[numLines]) );
					else
						System.out.println( " " + numLines + "*" + new String(tabs[numLines]) + ">" );
				}
				else
					System.out.println( "" + numLines + "|" + new String(tabs[numLines]) );
			}
			System.out.println("Maksymalna liczebność: " + max);
		} catch (NumberStoreException e) {
			throw new ViewDataException(e);
		} catch (ParameterException e) {
			throw new ViewDataException(e);
		} catch (LaborDataException e) {
			throw new ViewDataException(e);
		}
	}

	/* (non-Javadoc)
	 * @see pk.ie.proj.abstracts.ParameterAbstract#initParameterInterface()
	 */
	@Override
	public void initParameterInterface() {
		parameters.put(LINESAMMOUNT, Integer.valueOf(10));
	}

	/* (non-Javadoc)
	 * @see pk.ie.proj.interfaces.ParameterInterface#getAllowedClass(java.lang.String)
	 */
	public Class<?>[] getAllowedClass(String param) throws ParameterException {
		if ( param.equals(LINESAMMOUNT) )
			return new Class<?>[] {Integer.class};
		return new Class<?>[] {};
	}

}
