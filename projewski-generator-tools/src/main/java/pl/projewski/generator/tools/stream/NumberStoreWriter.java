package pl.projewski.generator.tools.stream;

import pl.projewski.generator.common.NumberWriter;
import pl.projewski.generator.tools.NumberStoreOne;

/**
 * @author projewski
 */
public class NumberStoreWriter implements NumberWriter {

    private final NumberStoreOne numberStore;

    public NumberStoreWriter(final NumberStoreOne numberStoreOne) {
        numberStore = numberStoreOne;
    }

    @Override
    public void close() {
    }

    @Override
    public void write(final int a) {
        numberStore.add(a);
    }

    @Override
    public void write(final long a) {
        numberStore.add(a);
    }

    @Override
    public void write(final float a) {
        numberStore.add(a);
    }

    @Override
    public void write(final double a) {
        numberStore.add(a);
    }

    @Override
    public void write(final int[] a) {
        numberStore.add(a);
    }

    @Override
    public void write(final long[] a) {
        numberStore.add(a);
    }

    @Override
    public void write(final float[] a) {
        numberStore.add(a);
    }

    @Override
    public void write(final double[] a) {
        numberStore.add(a);
    }

}
