/**
 * 
 */
package pl.projewski.generator.tools.stream;

import java.io.IOException;

import pk.ie.proj.exceptions.NumberStoreException;
import pk.ie.proj.tools.NumberStoreOne;
import pl.projewski.generator.exceptions.NumberStoreException;

/**
 * @author projewski
 *
 */
public class NumberStoreWriter implements NumberWriter {
	
	NumberStoreOne numberStore = null;

	public NumberStoreWriter(NumberStoreOne numberStoreOne) {
		numberStore = numberStoreOne;
	}

	/* (non-Javadoc)
	 * @see pk.ie.proj.tools.stream.NumberWriter#close()
	 */
	public void close() throws IOException {
	}

	/* (non-Javadoc)
	 * @see pk.ie.proj.tools.stream.NumberWriter#write(int)
	 */
	public void write(int a) throws IOException {
		try {
			numberStore.add(a);
		} catch (NumberStoreException e) {
			throw new IOException("Nie mo�na dodawa� danych: " + e.toString() );
		}
	}

	/* (non-Javadoc)
	 * @see pk.ie.proj.tools.stream.NumberWriter#write(long)
	 */
	public void write(long a) throws IOException {
		try {
			numberStore.add(a);
		} catch (NumberStoreException e) {
			throw new IOException("Nie można dodawać danych: " + e.toString() );
		}
	}

	/* (non-Javadoc)
	 * @see pk.ie.proj.tools.stream.NumberWriter#write(float)
	 */
	public void write(float a) throws IOException {
		try {
			numberStore.add(a);
		} catch (NumberStoreException e) {
			throw new IOException("Nie można dodawać danych: " + e.toString() );
		}

	}

	/* (non-Javadoc)
	 * @see pk.ie.proj.tools.stream.NumberWriter#write(double)
	 */
	public void write(double a) throws IOException {
		try {
			numberStore.add(a);
		} catch (NumberStoreException e) {
			throw new IOException("Nie można dodawać danych: " + e.toString() );
		}

	}

	/* (non-Javadoc)
	 * @see pk.ie.proj.tools.stream.NumberWriter#write(int[])
	 */
	public void write(int[] a) throws IOException {
		try {
			numberStore.add(a);
		} catch (NumberStoreException e) {
			throw new IOException("Nie można dodawać danych: " + e.toString() );
		}

	}

	/* (non-Javadoc)
	 * @see pk.ie.proj.tools.stream.NumberWriter#write(long[])
	 */
	public void write(long[] a) throws IOException {
		try {
			numberStore.add(a);
		} catch (NumberStoreException e) {
			throw new IOException("Nie można dodawać danych: " + e.toString() );
		}

	}

	/* (non-Javadoc)
	 * @see pk.ie.proj.tools.stream.NumberWriter#write(float[])
	 */
	public void write(float[] a) throws IOException {
		try {
			numberStore.add(a);
		} catch (NumberStoreException e) {
			throw new IOException("Nie można dodawać danych: " + e.toString() );
		}

	}

	/* (non-Javadoc)
	 * @see pk.ie.proj.tools.stream.NumberWriter#write(double[])
	 */
	public void write(double[] a) throws IOException {
		try {
			numberStore.add(a);
		} catch (NumberStoreException e) {
			throw new IOException("Nie można dodawać danych: " + e.toString() );
		}

	}

}
