package pl.projewski.generator.common;

import pl.projewski.generator.enumeration.ClassEnumerator;

import java.io.Closeable;

public interface NumberReader extends Closeable {
    boolean hasNext();

    int readInt();

    long readLong();

    float readFloat();

    double readDouble();

    int read(int[] a);

    int read(long[] a);

    int read(float[] a);

    int read(double[] a);

    Object readAsObject(ClassEnumerator c);

    @Override
    void close();
}
