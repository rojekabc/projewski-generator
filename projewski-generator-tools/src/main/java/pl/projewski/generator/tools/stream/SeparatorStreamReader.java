package pl.projewski.generator.tools.stream;

import pl.projewski.generator.common.NumberReader;
import pl.projewski.generator.enumeration.ClassEnumerator;
import pl.projewski.generator.tools.exceptions.EmptyNumberStoreGeneratorException;
import pl.projewski.generator.tools.exceptions.ReadFileGeneratorException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author projewski
 */
public class SeparatorStreamReader implements NumberReader {
    private final InputStreamReader is;
    private final int separator = ' ';

    public SeparatorStreamReader(final InputStream in) {
        this.is = new InputStreamReader(in);
    }

    private String readToSeparator() {
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
            throw new ReadFileGeneratorException("", e);
        }
        if ((str.length() == 0) && (c == -1)) {
            throw new EmptyNumberStoreGeneratorException();
        }

        return str.toString();

    }

    @Override
    public int readInt() {
        return Integer.parseInt(readToSeparator());
    }

    @Override
    public long readLong() {
        return Long.parseLong(readToSeparator());
    }

    @Override
    public float readFloat() {
        return Float.parseFloat(readToSeparator());
    }

    @Override
    public double readDouble() {
        return Double.parseDouble(readToSeparator());
    }

    @Override
    public int read(final int[] a) {
        int i = 0;
        try {
            while (is.ready() && (i < a.length)) {
                a[i++] = readInt();
            }
        } catch (final IOException e) {
            throw new ReadFileGeneratorException("", e);
        }
        return i;
    }

    @Override
    public int read(final long[] a) {
        int i = 0;
        try {
            while (is.ready() && (i < a.length)) {
                a[i++] = readLong();
            }
        } catch (final IOException e) {
            throw new ReadFileGeneratorException("", e);
        }
        return i;
    }

    @Override
    public int read(final float[] a) {
        int i = 0;
        try {
            while (is.ready() && (i < a.length)) {
                a[i++] = readFloat();
            }
        } catch (final IOException e) {
            throw new ReadFileGeneratorException("", e);
        }
        return i;
    }

    @Override
    public int read(final double[] a) {
        int i = 0;
        try {
            while (is.ready() && (i < a.length)) {
                a[i++] = readDouble();
            }
        } catch (final IOException e) {
            throw new ReadFileGeneratorException("", e);
        }
        return i;
    }

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
        } else {
            return null;
        }
    }

    @Override
    public boolean hasNext() {
        try {
            return is.ready();
        } catch (final IOException e) {
            throw new ReadFileGeneratorException("", e);
        }
    }

    @Override
    public void close() {
        try {
            is.close();
        } catch (final IOException e) {
            throw new ReadFileGeneratorException("", e);
        }
    }
}
