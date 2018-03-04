/**
 * 
 */
package pl.projewski.generator.tools.stream.interfaces;

import pl.projewski.generator.tools.stream.ScanedInputStream;

/**
 * @author piotrek
 *
 */
public interface IScanerListener {

	public void match(IScaner scaner, int streamposition, ScanedInputStream stream);

	public void notMatch(IScaner scaner, int streamposition, ScanedInputStream stream);

}
