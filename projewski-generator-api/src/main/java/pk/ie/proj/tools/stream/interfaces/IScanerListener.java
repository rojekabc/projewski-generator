/**
 * 
 */
package pk.ie.proj.tools.stream.interfaces;

import pk.ie.proj.tools.stream.ScanedInputStream;

/**
 * @author piotrek
 *
 */
public interface IScanerListener {

	public void match(IScaner scaner, int streamposition, ScanedInputStream stream);

	public void notMatch(IScaner scaner, int streamposition, ScanedInputStream stream);

}
