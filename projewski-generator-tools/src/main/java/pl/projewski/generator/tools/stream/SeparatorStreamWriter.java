/**
 *
 */
package pl.projewski.generator.tools.stream;

import pl.projewski.generator.common.NumberWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * @author projewski
 */
public class SeparatorStreamWriter extends OutputStreamWriter implements NumberWriter {

    int separator = ' ';

    public SeparatorStreamWriter(final OutputStream arg0) {
        super(arg0);
    }

    @Override
    public void write(final int a) throws IOException {
        super.write(Integer.toString(a));
        super.write(separator);
    }

    @Override
    public void write(final long a) throws IOException {
        super.write(Long.toString(a));
        super.write(separator);
    }

    @Override
    public void write(final float a) throws IOException {
        super.write(Float.toString(a));
        super.write(separator);
    }

    @Override
    public void write(final double a) throws IOException {
        super.write(Double.toString(a));
        super.write(separator);
    }

    @Override
    public void write(final int[] a) throws IOException {
        for (int i = 0; i < a.length; i++) {
            write(a[i]);
        }
    }

    @Override
    public void write(final long[] a) throws IOException {
        for (int i = 0; i < a.length; i++) {
            write(a[i]);
        }
    }

    @Override
    public void write(final float[] a) throws IOException {
        for (int i = 0; i < a.length; i++) {
            write(a[i]);
        }
    }

    @Override
    public void write(final double[] a) throws IOException {
        for (int i = 0; i < a.length; i++) {
            write(a[i]);
        }
    }
}
