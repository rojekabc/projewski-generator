/**
 * 
 */
package pk.ie.proj.viewdata.text;

import pk.ie.proj.exceptions.NumberStoreException;
import pk.ie.proj.exceptions.ParameterException;
import pk.ie.proj.exceptions.ViewDataException;
import pk.ie.proj.interfaces.NumberInterface;
import pk.ie.proj.interfaces.ParameterInterface;
import pk.ie.proj.interfaces.ViewDataInterface;
import pk.ie.proj.labordata.TestSpeed;
import pk.ie.proj.tools.Convert;
import pk.ie.proj.tools.Mysys;
import pk.ie.proj.tools.stream.NumberReader;

/**
 * @author projewski
 *
 */
public class PrintTestSpeed extends ViewDataInterface {
	
	@Override
	public boolean canViewData(ParameterInterface dataSource)
	{
		return dataSource instanceof TestSpeed;
	}

	NumberInterface data;

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
	public void setData(NumberInterface inputData) throws ViewDataException {
		data = inputData;
	}

	/* (non-Javadoc)
	 * @see pk.ie.proj.interfaces.ViewDataInterface#showView()
	 */
	@Override
	public void showView() throws ViewDataException {
		NumberReader reader = null;
		ParameterInterface src = null;
		try {
			reader = data.getNumberReader();
			src = data.getDataSource();
			if ( src == null )
				throw new ViewDataException(ViewDataException.UNKNOWN_ERROR); // TODO
			int testNum = Convert.tryToInt( src.getParameter(TestSpeed.NUMBEROFTESTS) );
			if ( (Boolean)src.getParameter(TestSpeed.TESTINTEGER) )
			{
				int i = testNum;
				System.out.println("Test of getInt()");
				while ( i-- > 0 )
					System.out.print( "" + reader.readLong() + "ms ");
				System.out.println();
			}
			if ( (Boolean)src.getParameter(TestSpeed.TESTLONG) )
			{
				int i = testNum;
				System.out.println("Test of getLong()");
				while ( i-- > 0 )
					System.out.print( "" + reader.readLong() + "ms ");
				System.out.println();
			}
			if ( (Boolean)src.getParameter(TestSpeed.TESTFLOAT) )
			{
				int i = testNum;
				System.out.println("Test of getFloat()");
				while ( i-- > 0 )
					System.out.print( "" + reader.readLong() + "ms ");
				System.out.println();
			}
			if ( (Boolean)src.getParameter(TestSpeed.TESTDOUBLE) )
			{
				int i = testNum;
				System.out.println("Test of getDouble()");
				while ( i-- > 0 )
					System.out.print( "" + reader.readLong() + "ms ");
				System.out.println();
			}
		} catch (NumberStoreException e) {
			throw new ViewDataException(e);
		} catch (NumberFormatException e) {
			throw new ViewDataException(e);
		} catch (ClassCastException e) {
			throw new ViewDataException(e);
		} catch (ParameterException e) {
			throw new ViewDataException(e);
		} finally {
			Mysys.closeQuiet(reader);
		}
	}

	/* (non-Javadoc)
	 * @see pk.ie.proj.abstracts.ParameterAbstract#initParameterInterface()
	 */
	@Override
	public void initParameterInterface() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see pk.ie.proj.interfaces.ParameterInterface#getAllowedClass(java.lang.String)
	 */
	public Class<?>[] getAllowedClass(String arg0) throws ParameterException {
		return null;
	}

}
