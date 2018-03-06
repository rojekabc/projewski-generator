/**
 * 
 */
package pl.projewski.generator.tools.stream;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author piotrek
 * 
 * Strumien odczytujacy dane z innego strumienia, ale z uwzglednieniem
 * obszarow, jakimi nastepuje zainteresowanie. Pozostale obszary nie
 * sa odczytywane.
 *
 */
public class RangedInputStream extends InputStream
{
	private InputStream is;
	
	public RangedInputStream(InputStream is)
	{
		this.is = is;
	}

	@Override
	public int read() throws IOException {
		return is.read();
	}
	

}
