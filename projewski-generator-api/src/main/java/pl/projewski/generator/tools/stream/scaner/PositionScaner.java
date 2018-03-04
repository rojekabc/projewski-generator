/**
 * 
 */
package pl.projewski.generator.tools.stream.scaner;

import pk.ie.proj.tools.stream.ScanedInputStream;
import pk.ie.proj.tools.stream.interfaces.AbstractScaner;
import pl.projewski.generator.tools.stream.interfaces.AbstractScaner;

/**
 * @author piotrek
 *
 */
public class PositionScaner extends AbstractScaner
{
	private int position;
	private byte [] bytea;
	private boolean status;
	
	public PositionScaner(int position, byte [] bytea)
	{
		if (( bytea == null ) || ( bytea.length == 0 ))
			throw new IllegalArgumentException();
		this.position = position;
		this.bytea = bytea;
	}


	/* (non-Javadoc)
	 * @see pk.ie.proj.tools.stream.interfaces.IScaner#read(byte, int, pk.ie.proj.tools.stream.ScanedInputStream)
	 */
	public void read(byte b, int streamposition, ScanedInputStream stream)
	{
		if ( listener == null )
			return;		
		if (( streamposition >= position ) && ( streamposition < position + bytea.length ))
		{
			if ( bytea[streamposition - position] != b )
			{
				status = false;
				listener.notMatch(this, position, stream);
			}
			else if ( streamposition == position + bytea.length -1 )
			{
				status = true;
				listener.match(this, position, stream);
			}
		}
	}


	/**
	 * @return Returns the status.
	 */
	public boolean isStatus() {
		return status;
	}

}
