package pl.projewski.generator.generator;

import org.apache.commons.collections.ListUtils;
import pl.projewski.generator.abstracts.GeneratorBase;
import pl.projewski.generator.common.NumberReader;
import pl.projewski.generator.common.NumberWriter;
import pl.projewski.generator.enumeration.ClassEnumerator;
import pl.projewski.generator.exceptions.GeneratorException;
import pl.projewski.generator.exceptions.WrongParameterGeneratorException;
import pl.projewski.generator.interfaces.NumberInterface;
import pl.projewski.generator.tools.Convert;

import java.util.Collections;
import java.util.List;

/**
 * @author projewski
 * <p>
 * To jest generator, który dane czerpie z interfejsu NumberInterface.
 * Dzięki temu można podpišć wygenerowany plik, jako generator zestawu liczb.
 */
public class NumberInterfaceGenerator extends GeneratorBase {

    public static final String NUMBERSOURCE = "Data source";
    public static final String ROLLING = "Rolling";

    private NumberInterface ni = null;
    private NumberReader reader = null;
    private boolean rolling = false;

    @Override
    public List<Class<?>> getAllowedClass(final String param) {
        switch (param) {
        case ROLLING:
            return Collections.singletonList(Boolean.class);
        case NUMBERSOURCE:
            return Collections.singletonList(NumberInterface.class);
        default:
            return ListUtils.EMPTY_LIST;
        }
    }

    @Override
    public void initParameters() {
        parameters.put(NUMBERSOURCE, ni);
        parameters.put(ROLLING, rolling);
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.interfaces.GeneratorInterface#nextDouble()
     */
    @Override
    public double nextDouble() {
        if (!reader.hasNext()) {
            reader.close();
            if (rolling) {
                reader = ni.getNumberReader();
            }
        }
        return reader.readDouble();
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.interfaces.GeneratorInterface#nextFloat()
     */
    @Override
    public float nextFloat() throws GeneratorException {
        if (!reader.hasNext()) {
            reader.close();
            if (rolling) {
                reader = ni.getNumberReader();
            }
        }
        return reader.readFloat();
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.interfaces.GeneratorInterface#nextInt()
     */
    @Override
    public int nextInt() throws GeneratorException {
        if (!reader.hasNext()) {
            reader.close();
            if (rolling) {
                reader = ni.getNumberReader();
            }
        }
        return reader.readInt();
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.interfaces.GeneratorInterface#nextLong()
     */
    @Override
    public long nextLong() throws GeneratorException {
        if (!reader.hasNext()) {
            reader.close();
            if (rolling) {
                reader = ni.getNumberReader();
            }
        }
        return reader.readLong();
    }

    private void initGenerator(final boolean isReinit) throws GeneratorException {
        Object o = parameters.get(NUMBERSOURCE);
        if (o == null) {
            throw new WrongParameterGeneratorException(NUMBERSOURCE);
        }
        ni = (NumberInterface) o;
        o = parameters.get(ROLLING);
        if (o == null) {
            throw new WrongParameterGeneratorException(ROLLING);
        }
        rolling = (Boolean) o;

        if (reader != null) {
            reader.close();
        }
        reader = ni.getNumberReader();
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
        if (tablica instanceof int[]) {
            final int[] tmp = Convert.tryToTInt(tablica);
            for (int i = 0; i < tmp.length; i++) {
                tmp[i] = nextInt();
            }
        } else if (tablica instanceof float[]) {
            final float[] tmp = Convert.tryToTFloat(tablica);
            for (int i = 0; i < tmp.length; i++) {
                tmp[i] = nextFloat();
            }
        } else if (tablica instanceof long[]) {
            final long[] tmp = Convert.tryToTLong(tablica);
            for (int i = 0; i < tmp.length; i++) {
                tmp[i] = nextLong();
            }
        } else if (tablica instanceof double[]) {
            final double[] tmp = Convert.tryToTDouble(tablica);
            for (int i = 0; i < tmp.length; i++) {
                tmp[i] = nextDouble();
            }
        }
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.interfaces.GeneratorInterface#rawFill(pk.ie.proj.tools.stream.NumberWriter, pk.ie.proj.enumeration.ClassEnumerator, int)
     */
    @Override
    public void rawFill(final NumberWriter writer, final ClassEnumerator cl, int size)
            throws GeneratorException {
        if (cl == ClassEnumerator.INTEGER) {
            while (size-- > 0) {
                writer.write(nextInt());
            }
        } else if (cl == ClassEnumerator.FLOAT) {
            while (size-- > 0) {
                writer.write(nextFloat());
            }
        } else if (cl == ClassEnumerator.LONG) {
            while (size-- > 0) {
                writer.write(nextLong());
            }
        } else if (cl == ClassEnumerator.DOUBLE) {
            while (size-- > 0) {
                writer.write(nextDouble());
            }
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
