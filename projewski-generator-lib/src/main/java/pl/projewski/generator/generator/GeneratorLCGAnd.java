/**
 *
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

/**
 * @author projewski
 * <p>
 * Generator, który realizuje algorytm:
 * X[n+1] = (A*x[n] + C) & M
 * Jest to odmiana generatora LCG, w którym operację modulo zastępuje się
 * operacjš &. Taki sposób pojmowania generatora LCG przynosi dużo korzyci
 * w sytuacji, w której dla generatora LCG parametr M wynosi 2^n - 1, ponieważ
 * wtedy M = 0x0111..111, a więc można wykonać operację modulo poprzez
 * wykonanie operacji AND, zamiast długiego wyznaczania reszty z dzielenia
 */
public class GeneratorLCGAnd extends GeneratorBase {
    public final static String XN = "zm. losowa";
    public final static String A = "a";
    public final static String C = "c";
    public final static String M = "m";
    public final static String SEED = "ziarno";

    // dane wykorzystywane przy obliczaniu - przyspiesza niesamowicie !
    private long a;
    private long c;
    private long m;
    private long x;

    @Override
    public void initParameters() {
        this.parameters.put(A, Integer.valueOf(7));
        this.parameters.put(C, Integer.valueOf(0));
        this.parameters.put(M, Integer.valueOf(15));
        this.parameters.put(SEED, Integer.valueOf(2));
        this.parameters.put(XN, Long.valueOf(0));
    }

    @Override
    public List<Class<?>> getAllowedClass(final String param) throws ParameterException {
        if (param.equals(XN)
                || param.equals(A)
                || param.equals(C)
                || param.equals(M)
                ) {
            return Arrays.asList(Integer.class, Long.class);
        } else if (param.equals(SEED)) {
            return Arrays.asList(Integer.class, Long.class, GeneratorInterface.class);
        } else {
            return ListUtils.EMPTY_LIST;
        }
    }

    private void initGenerator(final boolean isReinit) throws GeneratorException {
        final Object seed = parameters.get(SEED);
        final Object mObj = parameters.get(M);

        if (seed == null) {
            throw new GeneratorException(
                    GeneratorException.NULL_PARAMETER_ERROR, SEED
            );
        }
        if (parameters.get(A) == null) {
            throw new GeneratorException(
                    GeneratorException.NULL_PARAMETER_ERROR, A
            );
        }
        if (parameters.get(C) == null) {
            throw new GeneratorException(
                    GeneratorException.NULL_PARAMETER_ERROR, C
            );
        }
        if (mObj == null) {
            throw new GeneratorException(
                    GeneratorException.NULL_PARAMETER_ERROR, M
            );
        }

        a = Convert.tryToLong(parameters.get(A));
        c = Convert.tryToLong(parameters.get(C));
        m = Convert.tryToLong(parameters.get(M));

        a &= m;
        c &= m;

        // zainicjowanie initializerem
        if (seed instanceof GeneratorInterface) {
            final GeneratorInterface gi = (GeneratorInterface) seed;
            if (isReinit) {
                gi.reinit();
            } else {
                gi.init();
            }
            x = gi.nextLong();
        } else {
            x = Convert.tryToLong(seed);
        }
        x &= m;
        parameters.put(XN, Long.valueOf(x));
    }

    @Override
    public void init()
            throws GeneratorException {
        initGenerator(false);
    }

    @Override
    public void reinit()
            throws GeneratorException {
        initGenerator(true);
    }

    protected long next() {
        x *= a;
//		x %= m;
        x += c;
        x &= m;
        return x;
    }

    @Override
    public long nextLong()
            throws GeneratorException {
        try {
            parameters.put(XN, Long.valueOf(next()));
            return x;
        } catch (final ClassCastException e) {
            throw new GeneratorException(
                    GeneratorException.PARAMETER_CONVERT_ERROR
            );
        }
    }

    @Override
    public int nextInt()
            throws GeneratorException {
        return (int) nextLong();
    }

    @Override
    public float nextFloat()
            throws GeneratorException {
        // hmm, ciekawe, ale szybciej działa niż (float)nextLong()/(float)m
        return (float) nextDouble();
    }

    @Override
    public double nextDouble()
            throws GeneratorException {
        return (double) nextLong() / (double) m;
    }

    @Override
    public void rawFill(final Object tablica)
            throws GeneratorException {
        try {
            if (tablica instanceof int[]) {
                final int[] tmp = Convert.tryToTInt(tablica);
                for (int i = 0; i < tmp.length; i++) {
                    tmp[i] = (int) next();
                }
            } else if (tablica instanceof float[]) {
                final float[] tmp = Convert.tryToTFloat(tablica);
                for (int i = 0; i < tmp.length; i++) {
                    tmp[i] = (float) next() / (float) m;
                }
            } else if (tablica instanceof long[]) {
                final long[] tmp = Convert.tryToTLong(tablica);
                for (int i = 0; i < tmp.length; i++) {
                    tmp[i] = next();
                }
            } else if (tablica instanceof double[]) {
                final double[] tmp = Convert.tryToTDouble(tablica);
                for (int i = 0; i < tmp.length; i++) {
                    tmp[i] = (double) next() / (double) m;
                }
            }
            parameters.put(XN, Long.valueOf(x));
        } catch (final ClassCastException e) {
        }
    }

    @Override
    public void rawFill(final NumberWriter writer, final ClassEnumerator cl, int size)
            throws GeneratorException {
        try {
            if (cl == ClassEnumerator.INTEGER) {
                while (size-- > 0) {
                    writer.write((int) next());
                }
            } else if (cl == ClassEnumerator.FLOAT) {
                while (size-- > 0) {
                    writer.write((float) next() / (float) m);
                }
            } else if (cl == ClassEnumerator.LONG) {
                while (size-- > 0) {
                    writer.write(next());
                }
            } else if (cl == ClassEnumerator.DOUBLE) {
                while (size-- > 0) {
                    writer.write((double) next() / (double) m);
                }
            }
            parameters.put(XN, Long.valueOf(x));
        } catch (final ClassCastException e) {
            throw new GeneratorException(e);
        } catch (final IOException e) {
            throw new GeneratorException(e);
        }
    }
}
