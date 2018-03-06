/**
 *
 */
package pl.projewski.generator.tools.stream;

import pl.projewski.generator.common.NumberReader;
import pl.projewski.generator.enumeration.ClassEnumerator;
import pl.projewski.generator.exceptions.NumberStoreException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author projewski
 */
public class SeparatorStreamReader implements NumberReader {
    InputStreamReader is;
    int separator = ' ';

    public SeparatorStreamReader(final InputStream in) {
        this.is = new InputStreamReader(in);
    }

    protected String readToSeparator() throws NumberStoreException {
        int c;
        final StringBuilder str = new StringBuilder();
        try {
            while ((c = is.read()) != -1) {
                if (c == separator) {
                    break;
                }
                str.append((char) c);
            }
        } catch (final IOException e) {
            throw new NumberStoreException(e);
        }
        if ((str.length() == 0) && (c == -1)) {
            throw new NumberStoreException("Brak danych");
        }

        return str.toString();

    }

    @Override
    public int readInt() throws NumberStoreException {
        return Integer.parseInt(readToSeparator());
    }

    @Override
    public long readLong() throws NumberStoreException {
        return Long.parseLong(readToSeparator());
    }

    @Override
    public float readFloat() throws NumberStoreException {
        return Float.parseFloat(readToSeparator());
    }

    @Override
    public double readDouble() throws NumberStoreException {
        return Double.parseDouble(readToSeparator());
    }

    @Override
    public int read(final int[] a) throws NumberStoreException {
        int i = 0;
        try {
            while (is.ready() && (i < a.length)) {
                a[i++] = readInt();
            }
        } catch (final IOException e) {
            throw new NumberStoreException(e);
        }
        return i;
    }

    @Override
    public int read(final long[] a) throws NumberStoreException {
        int i = 0;
        try {
            while (is.ready() && (i < a.length)) {
                a[i++] = readLong();
            }
        } catch (final IOException e) {
            throw new NumberStoreException(e);
        }
        return i;
    }

    @Override
    public int read(final float[] a) throws NumberStoreException {
        int i = 0;
        try {
            while (is.ready() && (i < a.length)) {
                a[i++] = readFloat();
            }
        } catch (final IOException e) {
            throw new NumberStoreException(e);
        }
        return i;
    }

    @Override
    public int read(final double[] a) throws NumberStoreException {
        int i = 0;
        try {
            while (is.ready() && (i < a.length)) {
                a[i++] = readDouble();
            }
        } catch (final IOException e) {
            throw new NumberStoreException(e);
        }
        return i;
    }

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
        } else {
            return null;
        }
    }

    @Override
    public boolean hasNext() throws NumberStoreException {
        try {
            return is.ready();
        } catch (final IOException e) {
            throw new NumberStoreException(e);
        }
    }

    @Override
    public void close() throws NumberStoreException {
        try {
            is.close();
        } catch (final IOException e) {
            throw new NumberStoreException(e);
        }
    }
}
