/**
 * 
 */
package pl.projewski.generator.tools.stream.scaner;

import pl.projewski.generator.tools.stream.ScanedInputStream;
import pl.projewski.generator.tools.stream.interfaces.AbstractScaner;

/**
 * @author piotrek
 *
 */
public class ContainScaner extends AbstractScaner
{
	private byte [] searchBytes;
	private int position;
	private int counter = 0;
	
	public ContainScaner(byte [] searchBytes)
	{
		if (( searchBytes == null ) || ( searchBytes.length == 0 ))
			throw new IllegalArgumentException();
		this.searchBytes = searchBytes;
	}
	
	public byte [] getSearchedBytes()
	{
		return searchBytes;
	}

	public void read(byte b, int streamposition, ScanedInputStream stream)
	{
		if ( listener == null )
			return;
		if (searchBytes[position] != b)
		{
			position = 0;
		}
		else
		{
			position++;
			if ( position == searchBytes.length )
			{
				counter++;
				position = 0;
				listener.match(this, streamposition - searchBytes.length + 1, stream);
			}
		}
	}

	/**
	 * @return Returns the counter.
	 */
	public int getCounter() {
		return counter;
	}
}
