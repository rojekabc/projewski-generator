package pl.projewski.generator.tools.stream;

import pl.projewski.generator.common.NumberWriter;
import pl.projewski.generator.tools.exceptions.WriteStreamGeneratorException;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author projewski
 */
public class SeparatorStreamWriter implements NumberWriter {

    private final int separator = ' ';
    private final OutputStream outputStream;

    public SeparatorStreamWriter(final OutputStream os) {
        this.outputStream = os;
    }

    @Override
    public void write(final int a) {
        try {
            outputStream.write(Integer.toString(a).getBytes());
            outputStream.write(separator);
        } catch (final IOException e) {
            throw new WriteStreamGeneratorException(e);
        }
    }

    @Override
    public void write(final long a) {
        try {
            outputStream.write(Long.toString(a).getBytes());
            outputStream.write(separator);
        } catch (final IOException e) {
            throw new WriteStreamGeneratorException(e);
        }
    }

    @Override
    public void write(final float a) {
        try {
            outputStream.write(Float.toString(a).getBytes());
            outputStream.write(separator);
        } catch (final IOException e) {
            throw new WriteStreamGeneratorException(e);
        }
    }

    @Override
    public void write(final double a) {
        try {
            outputStream.write(Double.toString(a).getBytes());
            outputStream.write(separator);
        } catch (final IOException e) {
            throw new WriteStreamGeneratorException(e);
        }
    }

    @Override
    public void write(final int[] a) {
        for (final int anA : a) {
            write(anA);
        }
    }

    @Override
    public void write(final long[] a) {
        for (final long anA : a) {
            write(anA);
        }
    }

    @Override
    public void write(final float[] a) {
        for (final float anA : a) {
            write(anA);
        }
    }

    @Override
    public void write(final double[] a) {
        for (final double anA : a) {
            write(anA);
        }
    }

    public void write(final String s) {
        try {
            outputStream.write(s.getBytes());
        } catch (final IOException e) {
            throw new WriteStreamGeneratorException(e);
        }
    }

    public void flush() {
        try {
            outputStream.flush();
        } catch (final IOException e) {
            throw new WriteStreamGeneratorException(e);
        }
    }

    @Override
    public void close() {
        try {
            this.outputStream.close();
        } catch (final IOException e) {
            throw new WriteStreamGeneratorException(e);
        }
    }
}
