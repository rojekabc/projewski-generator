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
import pl.projewski.generator.exceptions.ParameterException;
import pl.projewski.generator.interfaces.GeneratorInterface;
import pl.projewski.generator.tools.Convert;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class GeneratorSimConst
        extends GeneratorBase {

    public final static String CONST = "constans";
    public final static String GENERATOR = "generator";
    public final static String MULTIP = "multiplier";
    public final static String XN = "zm. losowa";

    @Override
    public void initParameters() {
        parameters.put(CONST, Double.valueOf(0.0));
        parameters.put(GENERATOR, null);
        parameters.put(MULTIP, Double.valueOf(1.0));
        parameters.put(XN, Double.valueOf(0.0));
    }


    @Override
    public List<Class<?>> getAllowedClass(final String param) throws ParameterException {
        if (param.equals(XN)
                || param.equals(CONST)
                || param.equals(MULTIP)
                ) {
            return Arrays.asList(Double.class);
        } else if (param.equals(GENERATOR)) {
            return Arrays.asList(GeneratorInterface.class);
        } else {
            return ListUtils.EMPTY_LIST;
        }
    }

    @Override
    public void init()
            throws GeneratorException {
        final Object generator = parameters.get(GENERATOR);
        if (parameters.get(CONST) == null) {
            throw new GeneratorException(
                    GeneratorException.NULL_PARAMETER_ERROR, CONST
            );
        }

        if (parameters.get(MULTIP) == null) {
            throw new GeneratorException(
                    GeneratorException.NULL_PARAMETER_ERROR, MULTIP
            );
        }

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
            parameters.put(XN, nextDouble());
        } else {
            throw new GeneratorException(
                    GeneratorException.WRONG_PARAMETER_INSTANCE_ERROR,
                    GENERATOR);
        }
    }

    @Override
    public void reinit()
            throws GeneratorException {
        final Object generator = parameters.get(GENERATOR);
        if (parameters.get(CONST) == null) {
            throw new GeneratorException(
                    GeneratorException.NULL_PARAMETER_ERROR, CONST
            );
        }

        if (parameters.get(MULTIP) == null) {
            throw new GeneratorException(
                    GeneratorException.NULL_PARAMETER_ERROR, MULTIP
            );
        }

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
            parameters.put(XN, nextDouble());
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
        final Object objConst = parameters.get(CONST);
        final Object objMultip = parameters.get(MULTIP);
        final Object generator = parameters.get(GENERATOR);
        if (objConst == null) {
            throw new GeneratorException(
                    GeneratorException.NULL_PARAMETER_ERROR, CONST
            );
        }

        if (objMultip == null) {
            throw new GeneratorException(
                    GeneratorException.NULL_PARAMETER_ERROR, MULTIP
            );
        }

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
        double g = gi.nextDouble();
        double r = Convert.tryToDouble(objConst);
        g *= Convert.tryToDouble(objMultip);
        r += g;
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
