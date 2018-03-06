/**
 *
 */
package pl.projewski.generator.tools.stream;

import pl.projewski.generator.common.NumberReader;
import pl.projewski.generator.enumeration.ClassEnumerator;
import pl.projewski.generator.exceptions.NumberStoreException;
import pl.projewski.generator.tools.NumberStoreOne;

/**
 * @author projewski
 */
public class NumberStoreReader implements NumberReader {

    NumberStoreOne nso = null;
    int pos;

    public NumberStoreReader(final NumberStoreOne ns) {
        nso = ns;
        pos = 0;
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.tools.stream.NumberReader#read(int[])
     */
    @Override
    public int read(final int[] a) throws NumberStoreException {
        int i = 0;
        final int[] table = nso.getTInt();
        if (pos >= table.length) {
            throw new NumberStoreException("Brak danych");
        }
        while ((pos < table.length) && (i < a.length)) {
            a[i++] = table[pos++];
        }
        return i;
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.tools.stream.NumberReader#read(long[])
     */
    @Override
    public int read(final long[] a) throws NumberStoreException {
        int i = 0;
        final long[] table = nso.getTLong();
        if (pos >= table.length) {
            throw new NumberStoreException("Brak danych");
        }
        while ((pos < table.length) && (i < a.length)) {
            a[i++] = table[pos++];
        }
        return i;
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.tools.stream.NumberReader#read(float[])
     */
    @Override
    public int read(final float[] a) throws NumberStoreException {
        int i = 0;
        final float[] table = nso.getTFloat();
        if (pos >= table.length) {
            throw new NumberStoreException("Brak danych");
        }
        while ((pos < table.length) && (i < a.length)) {
            a[i++] = table[pos++];
        }
        return i;
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.tools.stream.NumberReader#read(double[])
     */
    @Override
    public int read(final double[] a) throws NumberStoreException {
        int i = 0;
        final double[] table = nso.getTDouble();
        if (pos >= table.length) {
            throw new NumberStoreException("Brak danych");
        }
        while ((pos < table.length) && (i < a.length)) {
            a[i++] = table[pos++];
        }
        return i;
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.tools.stream.NumberReader#readAsObject(java.lang.Class)
     */
    @Override
    public Object readAsObject(final ClassEnumerator c) throws NumberStoreException {
        if (c == ClassEnumerator.INTEGER) {
            return Integer.valueOf(readInt());
        } else if (c == ClassEnumerator.LONG) {
            return Long.valueOf(readLong());
        } else if (c == ClassEnumerator.FLOAT) {
            return Float.valueOf(readFloat());
        } else if (c == ClassEnumerator.DOUBLE) {
            return Double.valueOf(readDouble());
        }
        return null;
    }

    protected double getDouble() throws NumberStoreException {
        final double[] table = nso.getTDouble();
        if (pos >= table.length) {
            throw new NumberStoreException("Brak danych");
        }
        return table[pos++];
    }

    protected float getFloat() throws NumberStoreException {
        final float[] table = nso.getTFloat();
        if (pos >= table.length) {
            throw new NumberStoreException("Brak danych");
        }
        return table[pos++];
    }

    protected int getInt() throws NumberStoreException {
        final int[] table = nso.getTInt();
        if (pos >= table.length) {
            throw new NumberStoreException("Brak danych");
        }
        return table[pos++];
    }

    protected long getLong() throws NumberStoreException {
        final long[] table = nso.getTLong();
        if (pos >= table.length) {
            throw new NumberStoreException("Brak danych");
        }
        return table[pos++];
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.tools.stream.NumberReader#readDouble()
     */
    @Override
    public double readDouble() throws NumberStoreException {
        final ClassEnumerator c = nso.getStoreClass();
        if (c == ClassEnumerator.DOUBLE) {
            return getDouble();
        }
        if (c == ClassEnumerator.FLOAT) {
            return getFloat();
        }
        if (c == ClassEnumerator.LONG) {
            return getLong();
        }
        if (c == ClassEnumerator.INTEGER) {
            return getInt();
        }
        throw new NumberStoreException("Unknown type");
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.tools.stream.NumberReader#readFloat()
     */
    @Override
    public float readFloat() throws NumberStoreException {
        final ClassEnumerator c = nso.getStoreClass();
        if (c == ClassEnumerator.FLOAT) {
            return getFloat();
        }
        if (c == ClassEnumerator.DOUBLE) {
            return (float) getDouble();
        }
        if (c == ClassEnumerator.LONG) {
            return getLong();
        }
        if (c == ClassEnumerator.INTEGER) {
            return getInt();
        }
        throw new NumberStoreException("Unknown type");
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.tools.stream.NumberReader#readInt()
     */
    @Override
    public int readInt() throws NumberStoreException {
        final ClassEnumerator c = nso.getStoreClass();
        if (c == ClassEnumerator.INTEGER) {
            return getInt();
        }
        if (c == ClassEnumerator.LONG) {
            return (int) getLong();
        }
        if (c == ClassEnumerator.DOUBLE) {
            return (int) getDouble();
        }
        if (c == ClassEnumerator.FLOAT) {
            return (int) getFloat();
        }

        throw new NumberStoreException("Unknown type");
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.tools.stream.NumberReader#readLong()
     */
    @Override
    public long readLong() throws NumberStoreException {
        final ClassEnumerator c = nso.getStoreClass();
        if (c == ClassEnumerator.LONG) {
            return getLong();
        }
        if (c == ClassEnumerator.INTEGER) {
            return getInt();
        }
        if (c == ClassEnumerator.DOUBLE) {
            return (long) getDouble();
        }
        if (c == ClassEnumerator.FLOAT) {
            return (long) getFloat();
        } else {
            throw new NumberStoreException("Unknown type");
        }
    }

    @Override
    public boolean hasNext() throws NumberStoreException {
        if (pos > nso.getSize()) {
            System.out.println("Pos is " + pos);
            System.out.println("Size is " + nso.getSize());
        }
        return (pos < nso.getSize());
    }

    @Override
    public void close() throws NumberStoreException {
    }

}
