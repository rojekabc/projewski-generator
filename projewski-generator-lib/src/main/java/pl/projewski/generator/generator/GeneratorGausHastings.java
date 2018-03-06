/*
 * Generator rozkładu normalnego. Stosuje metodę C. Hastingsa.
 * W przybliżeniu powstaje rozkład Gauss'a N(0,1), czyli podstawie
 * 0.0 i odchyleniu stadardowym 1.0
 */

package pl.projewski.generator.generator;

import org.apache.commons.collections.ListUtils;
import pl.projewski.generator.abstracts.GeneratorBase;
import pl.projewski.generator.common.NumberWriter;
import pl.projewski.generator.enumeration.ClassEnumerator;
import pl.projewski.generator.exceptions.GeneratorException;
import pl.projewski.generator.interfaces.GeneratorInterface;
import pl.projewski.generator.tools.Convert;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class GeneratorGausHastings
        extends GeneratorBase {

    public final static String XN = "zm. losowa";
    public final static String GENERATOR = "generator";

    @Override
    public void initParameters() {
        this.parameters.put(XN, new Double(0.0));
        this.parameters.put(GENERATOR, null);
    }

    @Override
    public List<Class<?>> getAllowedClass(final String param) {
        if (param.equals(XN)) {
            return Arrays.asList(Float.class, Double.class);
        } else if (param.equals(GENERATOR)) {
            return Arrays.asList(GeneratorInterface.class);
        } else {
            return ListUtils.EMPTY_LIST;
        }
    }

    @Override
    public void init()
            throws GeneratorException {
        final Object generator = this.parameters.get(GENERATOR);
        if (generator == null) {
            throw new GeneratorException(
                    GeneratorException.NULL_PARAMETER_ERROR, GENERATOR
            );
        }

        if (generator instanceof GeneratorInterface) {
            // inicjuj generator podrzędny
            final GeneratorInterface gi = (GeneratorInterface) generator;
            gi.init();
            // pobierz pierwszą wartość
            this.parameters.put(XN, nextDouble());
        } else {
            throw new GeneratorException(
                    GeneratorException.WRONG_PARAMETER_INSTANCE_ERROR,
                    GENERATOR);
        }
    }

    @Override
    public void reinit()
            throws GeneratorException {
        final Object generator = this.parameters.get(GENERATOR);
        if (generator == null) {
            throw new GeneratorException(
                    GeneratorException.NULL_PARAMETER_ERROR, GENERATOR
            );
        }

        if (generator instanceof GeneratorInterface) {
            // reinicjuj generator podrzędny
            final GeneratorInterface gi = (GeneratorInterface) generator;
            gi.reinit();
            // pobierz pierwszą wartość
            this.parameters.put(XN, nextDouble());
        } else {
            throw new GeneratorException(
                    GeneratorException.WRONG_PARAMETER_INSTANCE_ERROR,
                    GENERATOR);
        }
    }

    @Override
    public long nextLong()
            throws GeneratorException {
        throw new GeneratorException(
                GeneratorException.NOT_ALLOWED_USE_ERROR,
                "Long value generation");
    }

    @Override
    public int nextInt()
            throws GeneratorException {
        throw new GeneratorException(
                GeneratorException.NOT_ALLOWED_USE_ERROR,
                "Integer value generation");
    }

    @Override
    public float nextFloat()
            throws GeneratorException {
        final float r = (float) nextDouble();
        return r;
    }

    @Override
    public double nextDouble()
            throws GeneratorException {
        final Object generator = this.parameters.get(GENERATOR);
        if (generator == null) {
            throw new GeneratorException(
                    GeneratorException.NULL_PARAMETER_ERROR, GENERATOR
            );
        }

        if (!(generator instanceof GeneratorInterface)) {
            throw new GeneratorException(
                    GeneratorException.WRONG_PARAMETER_INSTANCE_ERROR,
                    GENERATOR);
        }

        final GeneratorInterface gi = (GeneratorInterface) generator;
        final double g = gi.nextDouble();
        double r = 0.0;
        double y = 0.0;

        if (g >= 0.5) {
            y = Math.sqrt(-2 * Math.log(1 - g));
            r = y - ((2.30753 + 0.27061 * y) / (1 + 0.99229 * y + 0.04481 * y * y));
        } else {
            y = Math.sqrt(-2 * Math.log(g));
            r = ((2.30753 + 0.27061 * y) / (1 + 0.99229 * y + 0.04481 * y * y)) - y;
        }

        return r;
    }

    @Override
    public void rawFill(final Object tablica)
            throws GeneratorException {
        try {
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
        } catch (final ClassCastException e) {
        }
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
}
