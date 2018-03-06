/**
 *
 */
package pl.projewski.generator.tools.stream;

import pl.projewski.generator.common.NumberWriter;
import pl.projewski.generator.exceptions.NumberStoreException;
import pl.projewski.generator.tools.NumberStoreOne;

import java.io.IOException;

/**
 * @author projewski
 */
public class NumberStoreWriter implements NumberWriter {

    NumberStoreOne numberStore = null;

    public NumberStoreWriter(final NumberStoreOne numberStoreOne) {
        numberStore = numberStoreOne;
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.tools.stream.NumberWriter#close()
     */
    @Override
    public void close() throws IOException {
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.tools.stream.NumberWriter#write(int)
     */
    @Override
    public void write(final int a) throws IOException {
        try {
            numberStore.add(a);
        } catch (final NumberStoreException e) {
            throw new IOException("Nie mo�na dodawa� danych: " + e.toString());
        }
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.tools.stream.NumberWriter#write(long)
     */
    @Override
    public void write(final long a) throws IOException {
        try {
            numberStore.add(a);
        } catch (final NumberStoreException e) {
            throw new IOException("Nie można dodawać danych: " + e.toString());
        }
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.tools.stream.NumberWriter#write(float)
     */
    @Override
    public void write(final float a) throws IOException {
        try {
            numberStore.add(a);
        } catch (final NumberStoreException e) {
            throw new IOException("Nie można dodawać danych: " + e.toString());
        }

    }

    /* (non-Javadoc)
     * @see pk.ie.proj.tools.stream.NumberWriter#write(double)
     */
    @Override
    public void write(final double a) throws IOException {
        try {
            numberStore.add(a);
        } catch (final NumberStoreException e) {
            throw new IOException("Nie można dodawać danych: " + e.toString());
        }

    }

    /* (non-Javadoc)
     * @see pk.ie.proj.tools.stream.NumberWriter#write(int[])
     */
    @Override
    public void write(final int[] a) throws IOException {
        try {
            numberStore.add(a);
        } catch (final NumberStoreException e) {
            throw new IOException("Nie można dodawać danych: " + e.toString());
        }

    }

    /* (non-Javadoc)
     * @see pk.ie.proj.tools.stream.NumberWriter#write(long[])
     */
    @Override
    public void write(final long[] a) throws IOException {
        try {
            numberStore.add(a);
        } catch (final NumberStoreException e) {
            throw new IOException("Nie można dodawać danych: " + e.toString());
        }

    }

    /* (non-Javadoc)
     * @see pk.ie.proj.tools.stream.NumberWriter#write(float[])
     */
    @Override
    public void write(final float[] a) throws IOException {
        try {
            numberStore.add(a);
        } catch (final NumberStoreException e) {
            throw new IOException("Nie można dodawać danych: " + e.toString());
        }

    }

    /* (non-Javadoc)
     * @see pk.ie.proj.tools.stream.NumberWriter#write(double[])
     */
    @Override
    public void write(final double[] a) throws IOException {
        try {
            numberStore.add(a);
        } catch (final NumberStoreException e) {
            throw new IOException("Nie można dodawać danych: " + e.toString());
        }

    }

}
