package pl.projewski.generator.tools.stream;

import pl.projewski.generator.common.NumberReader;
import pl.projewski.generator.enumeration.ClassEnumerator;
import pl.projewski.generator.exceptions.NotAllowedTypeGeneratorException;
import pl.projewski.generator.tools.NumberStoreOne;
import pl.projewski.generator.tools.exceptions.EmptyNumberStoreGeneratorException;

/**
 * @author projewski
 */
public class NumberStoreReader implements NumberReader {

    private final NumberStoreOne nso;
    private int pos;

    public NumberStoreReader(final NumberStoreOne ns) {
        nso = ns;
        pos = 0;
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.tools.stream.NumberReader#read(int[])
     */
    @Override
    public int read(final int[] a) {
        int i = 0;
        final int[] table = nso.getTInt();
        if (pos >= table.length) {
            throw new EmptyNumberStoreGeneratorException();
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
    public int read(final long[] a) {
        int i = 0;
        final long[] table = nso.getTLong();
        if (pos >= table.length) {
            throw new EmptyNumberStoreGeneratorException();
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
    public int read(final float[] a) {
        int i = 0;
        final float[] table = nso.getTFloat();
        if (pos >= table.length) {
            throw new EmptyNumberStoreGeneratorException();
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
    public int read(final double[] a) {
        int i = 0;
        final double[] table = nso.getTDouble();
        if (pos >= table.length) {
            throw new EmptyNumberStoreGeneratorException();
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
    public Object readAsObject(final ClassEnumerator c) {
        if (c == ClassEnumerator.INTEGER) {
            return readInt();
        } else if (c == ClassEnumerator.LONG) {
            return readLong();
        } else if (c == ClassEnumerator.FLOAT) {
            return readFloat();
        } else if (c == ClassEnumerator.DOUBLE) {
            return readDouble();
        }
        return null;
    }

    protected double getDouble() {
        final double[] table = nso.getTDouble();
        if (pos >= table.length) {
            throw new EmptyNumberStoreGeneratorException();
        }
        return table[pos++];
    }

    protected float getFloat() {
        final float[] table = nso.getTFloat();
        if (pos >= table.length) {
            throw new EmptyNumberStoreGeneratorException();
        }
        return table[pos++];
    }

    protected int getInt() {
        final int[] table = nso.getTInt();
        if (pos >= table.length) {
            throw new EmptyNumberStoreGeneratorException();
        }
        return table[pos++];
    }

    protected long getLong() {
        final long[] table = nso.getTLong();
        if (pos >= table.length) {
            throw new EmptyNumberStoreGeneratorException();
        }
        return table[pos++];
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.tools.stream.NumberReader#readDouble()
     */
    @Override
    public double readDouble() {
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
        throw new NotAllowedTypeGeneratorException("number-store", c == null ? null : ClassEnumerator.getAsClass(c));
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.tools.stream.NumberReader#readFloat()
     */
    @Override
    public float readFloat() {
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
        throw new NotAllowedTypeGeneratorException("number-store", c == null ? null : ClassEnumerator.getAsClass(c));
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.tools.stream.NumberReader#readInt()
     */
    @Override
    public int readInt() {
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

        throw new NotAllowedTypeGeneratorException("number-store", c == null ? null : ClassEnumerator.getAsClass(c));
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.tools.stream.NumberReader#readLong()
     */
    @Override
    public long readLong() {
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
        }
        throw new NotAllowedTypeGeneratorException("number-store", c == null ? null : ClassEnumerator.getAsClass(c));
    }

    @Override
    public boolean hasNext() {
        if (pos > nso.getSize()) {
            System.out.println("Pos is " + pos);
            System.out.println("Size is " + nso.getSize());
        }
        return (pos < nso.getSize());
    }

    @Override
    public void close() {
    }

}
