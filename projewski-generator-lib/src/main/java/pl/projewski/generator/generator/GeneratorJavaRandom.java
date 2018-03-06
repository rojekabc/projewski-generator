/*
 *	Generator implementujący interfejs GeneratorInterface.
 *	Generator oparty na klasie javy java.util.Random.
 */
// PO WSTRZYMANIU PRACY GENERATOR JAVY NIE POZWLA NA PONOWNE WZNOWIENIE
// POWOD - NALEZABLOBY PRZEJSC TYLE SAMO KROKOW OD INICJACI DO OTRZYMANIA
// DANEJ WARTOSCI
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
import java.util.Random;

public class GeneratorJavaRandom
        extends GeneratorBase {
    public final static String XN = "zm. losowa";
    public final static String BOUND = "maksimum";
    public final static String SEED = "ziarno";
    protected Random javaRandom = null;

    @Override
    public void initParameters() {
        this.parameters.put(XN, Long.valueOf(0));
        this.parameters.put(BOUND, Integer.valueOf(0));
        this.parameters.put(SEED, Integer.valueOf(0));
    }

    /* zainicjowanie generatora wedle określonych uprzednio paramertrów */
    @Override
    public void init()
            throws GeneratorException {
        final Object seed = this.parameters.get(SEED);
        Object xn = null;
        if (seed != null) {
            if (seed instanceof GeneratorInterface) {
                final GeneratorInterface gi = (GeneratorInterface) seed;
                gi.init();
                final Object obj = gi.nextLong();
                if (obj != null) {
                    xn = obj;
                }
            } else {
                xn = seed;
            }
        }
        try {
            this.parameters.put(XN, xn);
            javaRandom = new java.util.Random(Convert.tryToLong(this.parameters.get(XN)));
        } catch (final ClassCastException e) {
            System.out.println("Sytuacja anormalna - Random ma nieznany seed");
            javaRandom = new java.util.Random();
        }
    }

    /* przeinicjowanie generatora po zmianie parametrów */
    @Override
    public void reinit()
            throws GeneratorException {
        final Object seed = this.parameters.get(SEED);
        Object xn = null;
        if (seed != null) {
            if (seed instanceof GeneratorInterface) {
                final GeneratorInterface gi = (GeneratorInterface) seed;
                gi.reinit();
                final Object obj = gi.nextLong();
                if (obj != null) {
                    xn = obj;
                }
            } else {
                xn = seed;
            }
        }
        try {
            this.parameters.put(XN, xn);
            javaRandom = new java.util.Random(Convert.tryToLong(xn));
        } catch (final ClassCastException e) {
            System.out.println("Sytuacja anormalna - Random ma nieznany seed");
            javaRandom = new java.util.Random();
        }
    }

    @Override
    public List<Class<?>> getAllowedClass(final String param) {
        if (param.equals(XN)) {
            return Arrays.asList(Integer.class, Long.class, Float.class, Double.class);
        } else if (param.equals(BOUND)) {
            return Arrays.asList(Integer.class);
        } else if (param.equals(SEED)) {
            return Arrays.asList(GeneratorInterface.class, Integer.class, Long.class);
        } else {
            return ListUtils.EMPTY_LIST;
        }
    }

    @Override
    public int nextInt()
            throws GeneratorException {
        int ret = 0;
        if (javaRandom != null) {
            final Object seed = this.parameters.get(SEED);
            if (seed == null) {
                ret = javaRandom.nextInt();
            } else {
                try {
                    ret = javaRandom.nextInt(Convert.tryToInt(seed));
                } catch (final ClassCastException e) {
                }
            }
        }
        this.parameters.put(XN, Integer.valueOf(ret));
        return ret;
    }

    @Override
    public float nextFloat()
            throws GeneratorException {
        float ret = 0.0f;
        if (javaRandom != null) {
            ret = javaRandom.nextFloat();
        }
        this.parameters.put(XN, Float.valueOf(ret));
        return ret;
    }

    @Override
    public long nextLong()
            throws GeneratorException {
        long ret = 0;
        if (javaRandom != null) {
            ret = javaRandom.nextLong();
        }
        this.parameters.put(XN, Long.valueOf(ret));
        return ret;
    }

    @Override
    public double nextDouble()
            throws GeneratorException {
        double ret = 0.0;
        if (javaRandom != null) {
            ret = javaRandom.nextDouble();
        }
        this.parameters.put(XN, Double.valueOf(ret));
        return ret;
    }

    @Override
    public void rawFill(final Object tablica)
            throws GeneratorException {
        int bound = 0;
        if (javaRandom == null) {
            return;
        }

        try {
            final Object obj = this.parameters.get(BOUND);
            if (obj == null) {
                bound = Convert.tryToInt(obj);
            }
        } catch (final ClassCastException e) {
        }

        try {
            if (tablica instanceof int[]) {
                final int[] tmp = Convert.tryToTInt(tablica);
                if (bound == 0) {
                    for (int i = 0; i < tmp.length; i++) {
                        tmp[i] = javaRandom.nextInt();
                    }
                } else {
                    for (int i = 0; i < tmp.length; i++) {
                        tmp[i] = javaRandom.nextInt(bound);
                    }
                }
                this.parameters.put(XN, Integer.valueOf(tmp[tmp.length - 1]));
            } else if (tablica instanceof float[]) {
                final float[] tmp = Convert.tryToTFloat(tablica);
                for (int i = 0; i < tmp.length; i++) {
                    tmp[i] = javaRandom.nextFloat();
                }
                this.parameters.put(XN, Float.valueOf(tmp[tmp.length - 1]));
            } else if (tablica instanceof long[]) {
                final long[] tmp = Convert.tryToTLong(tablica);
                for (int i = 0; i < tmp.length; i++) {
                    tmp[i] = javaRandom.nextLong();
                }
                this.parameters.put(XN, Long.valueOf(tmp[tmp.length - 1]));
            } else if (tablica instanceof double[]) {
                final double[] tmp = Convert.tryToTDouble(tablica);
                for (int i = 0; i < tmp.length; i++) {
                    tmp[i] = javaRandom.nextDouble();
                }
                this.parameters.put(XN, Double.valueOf(tmp[tmp.length - 1]));
            }
        } catch (final ClassCastException e) {
        }
    }

    @Override
    public void rawFill(final NumberWriter writer, final ClassEnumerator c, int size)
            throws GeneratorException {
        int bound = 0;
        if (javaRandom == null) {
            return;
        }

        try {
            final Object obj = this.parameters.get(BOUND);
            if (obj == null) {
                bound = Convert.tryToInt(obj);
            }
        } catch (final ClassCastException e) {
        }

        try {
            if (c == ClassEnumerator.INTEGER) {
                int tmp = 0;
                if (bound == 0) {
                    while (size-- > 0) {
                        writer.write(tmp = javaRandom.nextInt());
                    }
                } else {
                    while (size-- > 0) {
                        writer.write(tmp = javaRandom.nextInt(bound));
                    }
                }
                this.parameters.put(XN, Integer.valueOf(tmp));
            } else if (c == ClassEnumerator.FLOAT) {
                float tmp = 0.0f;
                while (size-- > 0) {
                    writer.write(tmp = javaRandom.nextFloat());
                }
                this.parameters.put(XN, Float.valueOf(tmp));
            } else if (c == ClassEnumerator.LONG) {
                long tmp = 0l;
                while (size-- > 0) {
                    writer.write(tmp = javaRandom.nextLong());
                }
                this.parameters.put(XN, Long.valueOf(tmp));
            } else if (c == ClassEnumerator.DOUBLE) {
                double tmp = 0.0;
                while (size-- > 0) {
                    writer.write(tmp = javaRandom.nextDouble());
                }
                this.parameters.put(XN, Double.valueOf(tmp));
            }
        } catch (final ClassCastException e) {
            throw new GeneratorException(e);
        } catch (final IOException e) {
            throw new GeneratorException(e);
        }
    }

};
