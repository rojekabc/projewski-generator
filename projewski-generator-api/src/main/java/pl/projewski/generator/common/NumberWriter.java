/**
 *
 */
package pl.projewski.generator.common;

import java.io.IOException;

/**
 * @author projewski
 */
public interface NumberWriter {
    public void write(int a) throws IOException;

    public void write(long a) throws IOException;

    public void write(float a) throws IOException;

    public void write(double a) throws IOException;

    public void write(int[] a) throws IOException;

    public void write(long[] a) throws IOException;

    public void write(float[] a) throws IOException;

    public void write(double[] a) throws IOException;

    public void close() throws IOException;
}
