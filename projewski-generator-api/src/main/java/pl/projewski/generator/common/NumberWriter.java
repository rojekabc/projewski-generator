package pl.projewski.generator.common;

import java.io.Closeable;

/**
 * @author projewski
 */
public interface NumberWriter extends Closeable {
    void write(int a);

    void write(long a);

    void write(float a);

    void write(double a);

    void write(int[] a);

    void write(long[] a);

    void write(float[] a);

    void write(double[] a);

    @Override
    void close();
}
