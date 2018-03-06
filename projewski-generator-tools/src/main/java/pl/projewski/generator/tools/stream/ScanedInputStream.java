/**
 * 
 */
package pl.projewski.generator.tools.stream;

import pl.projewski.generator.tools.stream.interfaces.IScaner;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author piotrek
 *
 */
public class ScanedInputStream extends InputStream
{
	private InputStream is;
	private List<IScaner> scaners;
	private List<IScaner> scanersRemoved = null;
	private List<IScaner> scanersAdded = null;
	private boolean lock = false;
	private int streamposition = 0;
	
	public ScanedInputStream(InputStream is)
	{
		this.is = is;
	}
	
	public void addScaner(IScaner scaner)
	{
		if ( lock )
		{
			if ( scanersAdded == null )
				scanersAdded = new ArrayList<IScaner>();
			scanersAdded.add(scaner);
		}
		else
		{
			if ( scaners == null )
				scaners = new ArrayList<IScaner>();
			scaners.add(scaner);
		}
	}
	
	public void remScaner(IScaner scaner)
	{
		if ( lock )
		{
			if ( scanersRemoved == null )
				scanersRemoved = new ArrayList<IScaner>();
			scanersRemoved.add(scaner);			
		}
		else
		{
			if ( scaners != null )
				scaners.remove(scaner);
		}
	}
	
	public void clearScaners()
	{
		if ( lock )
		{
			if ( scanersRemoved == null )
				scanersRemoved = new ArrayList<IScaner>(scaners);
		}
		else
		{
			if ( scaners != null )
				scaners.clear();
		}
	}
	
	/**
	 * Usuniecie grupy skanerow nalezacych do okreslonej klasy grupujacej
	 * @param c
	 */
	public void remScanersGroup(Class c)
	{
		for (IScaner scaner : scaners)
		{
			if ( scaner.getGroupClass() == c )
				scanersRemoved.add(scaner);
		}
		if ( !lock )
		{
			scaners.removeAll(scanersRemoved);
			scanersRemoved.clear();
			scanersRemoved = null;
		}
	}

	@Override
	public int read() throws IOException
	{
		int r = is.read();
		if ( r >= 0 )
		{
			lock = true;
			for (IScaner scaner : scaners)
			{
				if (( scanersRemoved != null ) && ( scanersRemoved.contains(scaner) ))
					continue;
				scaner.read((byte)r, streamposition, this);
			}
			lock = false;
			if ( scanersRemoved != null )
			{
				scaners.removeAll(scanersRemoved);
				scanersRemoved.clear();
				scanersRemoved = null;
			}
			if ( scanersAdded != null )
			{
				scaners.addAll(scanersAdded);
				scanersAdded.clear();
				scanersAdded = null;
			}
		}
		streamposition++;
		return r;
	}

}
