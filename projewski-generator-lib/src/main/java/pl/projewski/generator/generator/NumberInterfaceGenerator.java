/**
 *
 */
package pl.projewski.generator.generator;

import pk.ie.proj.enumeration.ClassEnumerator;
import pk.ie.proj.exceptions.GeneratorException;
import pk.ie.proj.exceptions.NumberStoreException;
import pk.ie.proj.exceptions.ParameterException;
import pk.ie.proj.interfaces.GeneratorInterface;
import pk.ie.proj.interfaces.NumberInterface;
import pk.ie.proj.tools.Convert;
import pk.ie.proj.tools.stream.NumberReader;
import pk.ie.proj.tools.stream.NumberWriter;

import java.io.IOException;

/**
 * @author projewski
 * <p>
 * To jest generator, który dane czerpie z interfejsu NumberInterface.
 * Dzięki temu można podpišć wygenerowany plik, jako generator zestawu liczb.
 */
public class NumberInterfaceGenerator extends GeneratorInterface {

    public static final String NUMBERSOURCE = "ródło danych";
    public static final String ROLLING = "Przewijanie";
    NumberInterface ni = null;
    NumberReader reader = null;
    boolean rolling = false;

    @Override
    public Class<?>[] getAllowedClass(final String param) throws ParameterException {
        return super.getAllowedClass(param);
    }

    @Override
    public void initParameterInterface() {
        super.initParameterInterface();
        parameters.put(NUMBERSOURCE, ni);
        parameters.put(ROLLING, rolling);
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.interfaces.GeneratorInterface#nextDouble()
     */
    @Override
    public double getDouble() throws GeneratorException {
        try {
            if (!reader.hasNext()) {
                reader.close();
                if (rolling) {
                    reader = ni.getNumberReader();
                }
            }
            return reader.readDouble();
        } catch (final NumberStoreException e) {
            throw new GeneratorException(e);
        }
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.interfaces.GeneratorInterface#nextFloat()
     */
    @Override
    public float getFloat() throws GeneratorException {
        try {
            if (!reader.hasNext()) {
                reader.close();
                if (rolling) {
                    reader = ni.getNumberReader();
                }
            }
            return reader.readFloat();
        } catch (final NumberStoreException e) {
            throw new GeneratorException(e);
        }
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.interfaces.GeneratorInterface#nextInt()
     */
    @Override
    public int getInt() throws GeneratorException {
        try {
            if (!reader.hasNext()) {
                reader.close();
                if (rolling) {
                    reader = ni.getNumberReader();
                }
            }
            return reader.readInt();
        } catch (final NumberStoreException e) {
            throw new GeneratorException(e);
        }
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.interfaces.GeneratorInterface#nextLong()
     */
    @Override
    public long getLong() throws GeneratorException {
        try {
            if (!reader.hasNext()) {
                reader.close();
                if (rolling) {
                    reader = ni.getNumberReader();
                }
            }
            return reader.readLong();
        } catch (final NumberStoreException e) {
            throw new GeneratorException(e);
        }
    }

    private void initGenerator(final boolean isReinit) throws GeneratorException {
        Object o = parameters.get(NUMBERSOURCE);
        if (o == null) {
            throw new GeneratorException(GeneratorException.NULL_PARAMETER_ERROR, NUMBERSOURCE);
        }
        ni = (NumberInterface) o;
        o = parameters.get(ROLLING);
        if (o == null) {
            throw new GeneratorException(GeneratorException.NULL_PARAMETER_ERROR, ROLLING);
        }
        rolling = ((Boolean) o).booleanValue();

        try {
            if (reader != null) {
                reader.close();
            }
            reader = ni.getNumberReader();
        } catch (final NumberStoreException e) {
            throw new GeneratorException(e);
        }
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.interfaces.GeneratorInterface#init()
     */
    @Override
    public void init() throws GeneratorException {
        initGenerator(false);
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.interfaces.GeneratorInterface#rawFill(java.lang.Object)
     */
    @Override
    public void rawFill(final Object tablica) throws GeneratorException {
        try {
            if (tablica instanceof int[]) {
                final int[] tmp = Convert.tryToTInt(tablica);
                for (int i = 0; i < tmp.length; i++) {
                    tmp[i] = getInt();
                }
            } else if (tablica instanceof float[]) {
                final float[] tmp = Convert.tryToTFloat(tablica);
                for (int i = 0; i < tmp.length; i++) {
                    tmp[i] = getFloat();
                }
            } else if (tablica instanceof long[]) {
                final long[] tmp = Convert.tryToTLong(tablica);
                for (int i = 0; i < tmp.length; i++) {
                    tmp[i] = getLong();
                }
            } else if (tablica instanceof double[]) {
                final double[] tmp = Convert.tryToTDouble(tablica);
                for (int i = 0; i < tmp.length; i++) {
                    tmp[i] = getDouble();
                }
            }
        } catch (final ClassCastException e) {
            throw new GeneratorException(e);
        }
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.interfaces.GeneratorInterface#rawFill(pk.ie.proj.tools.stream.NumberWriter, pk.ie.proj.enumeration.ClassEnumerator, int)
     */
    @Override
    public void rawFill(final NumberWriter writer, final ClassEnumerator cl, int size)
            throws GeneratorException {
        try {
            if (cl == ClassEnumerator.INTEGER) {
                while (size-- > 0) {
                    writer.write(getInt());
                }
            } else if (cl == ClassEnumerator.FLOAT) {
                while (size-- > 0) {
                    writer.write(getFloat());
                }
            } else if (cl == ClassEnumerator.LONG) {
                while (size-- > 0) {
                    writer.write(getLong());
                }
            } else if (cl == ClassEnumerator.DOUBLE) {
                while (size-- > 0) {
                    writer.write(getDouble());
                }
            }
        } catch (final ClassCastException e) {
            throw new GeneratorException(e);
        } catch (final IOException e) {
            throw new GeneratorException(e);
        }
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.interfaces.GeneratorInterface#reinit()
     */
    @Override
    public void reinit() throws GeneratorException {
        initGenerator(true);
    }

}
