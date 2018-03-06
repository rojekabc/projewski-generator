/*
 * Interfejs Generatora, pozwalający na jego wykorzystywanie w programach
 * z ustawionym dowolnym generatorem. Równocześnie pomocny do wykonywania
 * testów i obliczeń na danych wynikowych generatora.
 */
/*
 * Init:
 * X0 = SystemTime
 * Generation:
 * X[i] = X[i-1]
 */
package pl.projewski.generator.generator;

import org.apache.commons.collections.ListUtils;
import pl.projewski.generator.abstracts.GeneratorBase;
import pl.projewski.generator.common.NumberWriter;
import pl.projewski.generator.enumeration.ClassEnumerator;
import pl.projewski.generator.exceptions.GeneratorException;
import pl.projewski.generator.exceptions.ParameterException;
import pl.projewski.generator.tools.Convert;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class GeneratorSystemTime
        extends GeneratorBase {

    public final static String XN = "zm. losowa";

    @Override
    public void initParameters() {
        parameters.put(XN, Integer.valueOf(0));
    }

    /* zainicjowanie generatora wedle określonych uprzednio paramertrów */
    @Override
    public void init()
            throws GeneratorException {
        final long r = System.currentTimeMillis();
        parameters.put(XN, Long.valueOf(r));
    }

    /* przeinicjowanie generatora po zmianie parametrów */
    @Override
    public void reinit()
            throws GeneratorException {
    }

    @Override
    public List<Class<?>> getAllowedClass(final String param)
            throws ParameterException {
        if (param.equals(XN)) {
            return Arrays.asList(Integer.class, Long.class);
        }
        return ListUtils.EMPTY_LIST;
    }

    @Override
    public long nextLong()
            throws GeneratorException {
        try {
            final Object xn = parameters.get(XN);
            if (xn != null) {
                return Convert.tryToLong(xn);
            }
        } catch (final ClassCastException e) {
        }
        return 0;
    }

    @Override
    public int nextInt()
            throws GeneratorException {
        try {
            final Object xn = parameters.get(XN);
            if (xn != null) {
                return Convert.tryToInt(xn);
            }
        } catch (final ClassCastException e) {
        }
        return 0;
    }

    @Override
    public float nextFloat()
            throws GeneratorException {
        try {
            final Object xn = parameters.get(XN);
            if (xn != null) {
                return Convert.tryToFloat(xn);
            }
        } catch (final ClassCastException e) {
        }
        return 0;
    }

    @Override
    public double nextDouble()
            throws GeneratorException {
        try {
            final Object xn = parameters.get(XN);
            if (xn != null) {
                return Convert.tryToDouble(xn);
            }
        } catch (final ClassCastException e) {
        }
        return 0;
    }

    @Override
    public void rawFill(final Object table)
            throws GeneratorException {
        try {
            if (table instanceof int[]) {
                final int[] tmp = Convert.tryToTInt(table);
                final int f = nextInt();
                for (int i = 0; i < tmp.length; i++) {
                    tmp[i] = f;
                }
            } else if (table instanceof long[]) {
                final long[] tmp = Convert.tryToTLong(table);
                final long f = nextLong();
                for (int i = 0; i < tmp.length; i++) {
                    tmp[i] = f;
                }
            } else if (table instanceof float[]) {
                final float[] tmp = Convert.tryToTFloat(table);
                final float f = nextFloat();
                for (int i = 0; i < tmp.length; i++) {
                    tmp[i] = f;
                }
            } else if (table instanceof double[]) {
                final double[] tmp = Convert.tryToTDouble(table);
                final double f = nextDouble();
                for (int i = 0; i < tmp.length; i++) {
                    tmp[i] = f;
                }
            }
        } catch (final ClassCastException e) {
        }
        return;
    }

    @Override
    public void rawFill(final NumberWriter writer, final ClassEnumerator c, int size)
            throws GeneratorException {
        try {
            if (c == ClassEnumerator.INTEGER) {
                while (size-- > 0) {
                    writer.write(nextInt());
                }
            } else if (c == ClassEnumerator.FLOAT) {
                while (size-- > 0) {
                    writer.write(nextFloat());
                }
            } else if (c == ClassEnumerator.LONG) {
                while (size-- > 0) {
                    writer.write(nextLong());
                }
            } else if (c == ClassEnumerator.DOUBLE) {
                while (size-- > 0) {
                    writer.write(nextDouble());
                }
            }
        } catch (final ClassCastException e) {
            throw new GeneratorException(e);
        } catch (final IOException e) {
            throw new GeneratorException(e);
        }

    }
};
