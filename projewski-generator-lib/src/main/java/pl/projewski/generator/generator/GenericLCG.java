package pl.projewski.generator.generator;

import org.apache.commons.collections.ListUtils;
import pl.projewski.generator.abstracts.GeneratorBase;
import pl.projewski.generator.common.NumberWriter;
import pl.projewski.generator.enumeration.ClassEnumerator;
import pl.projewski.generator.exceptions.GeneratorException;
import pl.projewski.generator.exceptions.WrongParameterGeneratorException;
import pl.projewski.generator.interfaces.GeneratorInterface;
import pl.projewski.generator.tools.ArrayUtil;
import pl.projewski.generator.tools.Convert;
import pl.projewski.generator.tools.VectorLong;

import java.util.Arrays;
import java.util.List;

/**
 * @author projewski
 * <p>
 * Ogólny schemat generatora LCG, który działa zgodnie z regułš
 * s
 * _
 * X[n+1] = >   A[k]*X[n-k] + C mod M, gdzie s < n
 * -
 * k = 0
 * <p>
 * Elementów s będzie przechowywanych tyle, ile zostanie podanych parametrów do tablicy SEED,
 * która inicjuje poczštkowy zestaw parametrów X[0..s], a jeżeli jest to generator to będzie ona
 * zgodna z liczbš danych podanych w A
 */
public class GenericLCG extends GeneratorBase {

    public final static String XN = "zm. losowa";
    public final static String A = "a";
    public final static String C = "c";
    public final static String M = "m";
    public final static String SEED = "ziarno";

    private long[] a;
    private long c;
    private long[] xn;
    private long m;

    @Override
    public List<Class<?>> getAllowedClass(final String param) {
        switch (param) {
        case A:
        case XN:
            return Arrays.asList(VectorLong.class);
        case C:
        case M:
            return Arrays.asList(Long.class);
        case SEED:
            return Arrays.asList(VectorLong.class, GeneratorInterface.class);
        default:
            return ListUtils.EMPTY_LIST;
        }
    }

    @Override
    public void initParameters() {
        parameters.put(A, new VectorLong());
        parameters.put(C, 0L);
        parameters.put(M, 0L);
        parameters.put(SEED, 2L);
        parameters.put(XN, new VectorLong());
    }

    /**
     * Wyznacza kolejnš wartoć generatora,
     * Nie zmienia jego stanu w parametrach
     * Aktualizuje tablicę z aktualnymi wartociami
     * Zwraca nowootrzymanš wartoć
     */
    private long next() {
        long newxn = 0;

        for (int i = 0; i < xn.length; i++) {
            newxn += (a[i] * xn[xn.length - i - 1]) % m;
            newxn %= m;
        }

        newxn += c;
        newxn %= m;
        ArrayUtil.constPutLast(xn, newxn);
        return newxn;
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.interfaces.GeneratorInterface#nextDouble()
     */
    @Override
    public double nextDouble() throws GeneratorException {
        final long m = Convert.tryToLong(parameters.get(M));
        return (double) nextLong() / (double) m;
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.interfaces.GeneratorInterface#nextFloat()
     */
    @Override
    public float nextFloat() throws GeneratorException {
        final long m = Convert.tryToLong(parameters.get(M));
        return (float) nextLong() / (float) m;
    }

    @Override
    public int nextInt() {
        return (int) nextLong();
    }

    @Override
    public long nextLong() {
        final long n = next();
        parameters.put(XN, new VectorLong(xn));
        return n;
    }

    private void initGenerator(final boolean isReinit) {
        // pobranie parametrów poczštkowych
        final Object seedObj = parameters.get(SEED);
        final Object aObj = parameters.get(A);
        final Object cObj = parameters.get(C);
        final Object mObj = parameters.get(M);

        if (seedObj == null) {
            throw new WrongParameterGeneratorException(SEED);
        }

        if (aObj == null) {
            throw new WrongParameterGeneratorException(A);
        }

        if (cObj == null) {
            throw new WrongParameterGeneratorException(C);
        }

        if (mObj == null) {
            throw new WrongParameterGeneratorException(M);
        }

        //		VectorLong a = (VectorLong)aObj;
        a = Convert.tryToTLong(aObj);
        //		VectorLong xn = new VectorLong();
        m = Convert.tryToLong(mObj);
        c = Convert.tryToLong(cObj);

        // budowanie poczatkowego xn z wyrównaniem parametrów
        if (seedObj instanceof GeneratorInterface) {
            final GeneratorInterface seedGI = (GeneratorInterface) seedObj;

            if (isReinit) {
                seedGI.reinit();
            } else {
                seedGI.init();
            }

            xn = new long[a.length];
            for (int i = 0; i < a.length; i++) {
                xn[i] = seedGI.nextLong();
                xn[i] %= m;
            }
        } else {
            final VectorLong seed = (VectorLong) seedObj;
            xn = new long[seed.size()];
            for (int i = 0; i < seed.size(); i++) {
                xn[i] = seed.get(i);
                xn[i] %= m;
            }
        }
        parameters.put(XN, new VectorLong(xn));
    }

    @Override
    public void init() {
        initGenerator(false);
    }

    @Override
    public void rawFill(final Object arg0) {
    }

    @Override
    public void rawFill(final NumberWriter writer, final ClassEnumerator cl, int size) {
        if (cl == ClassEnumerator.LONG) {
            while (size-- > 0) {
                writer.write(this.nextLong());
            }
        } else if (cl == ClassEnumerator.INTEGER) {
            while (size-- > 0) {
                writer.write(this.nextInt());
            }
        } else if (cl == ClassEnumerator.FLOAT) {
            while (size-- > 0) {
                writer.write(this.nextFloat());
            }
        } else if (cl == ClassEnumerator.DOUBLE) {
            while (size-- > 0) {
                writer.write(this.nextDouble());
            }
        }
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.interfaces.GeneratorInterface#reinit()
     */
    @Override
    public void reinit() {
        initGenerator(true);
    }
}
