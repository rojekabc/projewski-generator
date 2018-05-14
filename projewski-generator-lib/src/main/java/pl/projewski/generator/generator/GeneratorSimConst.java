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
import pl.projewski.generator.exceptions.NotAllowedOperationGeneratorException;
import pl.projewski.generator.exceptions.WrongParameterGeneratorException;
import pl.projewski.generator.interfaces.GeneratorInterface;
import pl.projewski.generator.tools.Convert;

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
        parameters.put(CONST, 0.0);
        parameters.put(GENERATOR, null);
        parameters.put(MULTIP, 1.0);
        parameters.put(XN, 0.0);
    }


    @Override
    public List<Class<?>> getAllowedClass(final String param) {
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
    public void init() {
        final Object generator = parameters.get(GENERATOR);
        if (parameters.get(CONST) == null) {
            throw new WrongParameterGeneratorException(CONST);
        }

        if (parameters.get(MULTIP) == null) {
            throw new WrongParameterGeneratorException(MULTIP);
        }

        if (generator == null || !(generator instanceof GeneratorInterface)) {
            throw new WrongParameterGeneratorException(GENERATOR);
        }

        // inicjuj generator podrzędny
        final GeneratorInterface gi = (GeneratorInterface) generator;
        gi.init();
        // pobierz pierwszą wartość
        parameters.put(XN, nextDouble());
    }

    @Override
    public void reinit()
            throws GeneratorException {
        final Object generator = parameters.get(GENERATOR);
        if (parameters.get(CONST) == null) {
            throw new WrongParameterGeneratorException(CONST);
        }

        if (parameters.get(MULTIP) == null) {
            throw new WrongParameterGeneratorException(MULTIP);
        }

        if (generator == null || !(generator instanceof GeneratorInterface)) {
            throw new WrongParameterGeneratorException(GENERATOR);
        }

        // reinicjuj generator podrzędny
        final GeneratorInterface gi = (GeneratorInterface) generator;
        gi.reinit();
        // pobierz pierwszą wartość
        parameters.put(XN, nextDouble());
    }

    @Override
    public long nextLong() {
        throw new NotAllowedOperationGeneratorException();
    }

    @Override
    public int nextInt() {
        throw new NotAllowedOperationGeneratorException();
    }

    @Override
    public float nextFloat() {
        final float r = (float) nextDouble();
        return r;
    }

    @Override
    public double nextDouble() {
        final Object objConst = parameters.get(CONST);
        final Object objMultip = parameters.get(MULTIP);
        final Object generator = parameters.get(GENERATOR);
        if (objConst == null) {
            throw new WrongParameterGeneratorException(CONST);
        }

        if (objMultip == null) {
            throw new WrongParameterGeneratorException(MULTIP);
        }

        if (generator == null || !(generator instanceof GeneratorInterface)) {
            throw new WrongParameterGeneratorException(GENERATOR);
        }

        final GeneratorInterface gi = (GeneratorInterface) generator;
        double g = gi.nextDouble();
        double r = Convert.tryToDouble(objConst);
        g *= Convert.tryToDouble(objMultip);
        r += g;
        return r;
    }

    @Override
    public void rawFill(final Object tablica) {
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


    @Override
    public void rawFill(final NumberWriter writer, final ClassEnumerator c, int size) {
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
    }
}
