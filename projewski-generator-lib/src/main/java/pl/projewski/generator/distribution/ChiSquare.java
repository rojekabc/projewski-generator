package pl.projewski.generator.distribution;

import org.apache.commons.collections.ListUtils;
import pl.projewski.generator.abstracts.DistributionBase;
import pl.projewski.generator.common.Fraction;
import pl.projewski.generator.tools.Convert;

import java.util.Collections;
import java.util.List;

public class ChiSquare extends DistributionBase {
    public static final String V = "liczba stopni swobody";

    public static final Object[] INVERSE =
            {
                    new int[]{19, 20},
                    new double[][]
                            {
                                    {0.001, 0.005, 0.01, 0.025, 0.05, 0.1, 0.2, 0.3,
                                            0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 0.95, 0.975,
                                            0.99, 0.995, 0.999}, // 19
                                    {0.001, 0.005, 0.01, 0.025, 0.05, 0.1, 0.2, 0.3,
                                            0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 0.95, 0.975,
                                            0.99, 0.995, 0.999} // 20
                            },
                    new double[][]
                            {
                                    {5.407, 6.844, 7.633, 8.907, 10.117, 11.651, 13.716, 15.352,
                                            16.850, 18.338, 19.910, 21.689, 23.900, 27.204, 30.144, 32.852,
                                            36.191, 38.582, 43.819}, //19
                                    {5.921, 7.434, 8.260, 9.591, 10.851, 12.443, 14.578, 16.266,
                                            17.809, 19.337, 20.951, 22.775, 25.038, 28.412, 31.410, 34.170,
                                            37.566, 39.997, 45.314} // 20
                            }
            };

    // INVERSE CDF
    @Override
    public double getInverse(final double propability) {
        final Object parV = this.parameters.get(V);
        if (parV == null) {
            System.out.println("[WARN][" + this.getClass().getName() + "] Pusty stopien swobody");
            return Double.NaN;
        }
        if ((propability > 0.0) && (propability < 1.0)) {
            final int f = Convert.tryToInt(parV);
            final int[] t = (int[]) INVERSE[0];
            int i;
            int j;
            //			System.out.println("t.length="+t.length+" f="+f);
            for (i = 0; i < t.length; i++) {
                if (t[i] == f) {
                    break;
                }
            }
            if (i == t.length) {
                System.out.println("Nie odnaleziony stopien swobody");
                return Double.NaN;
            }
            final double[][] p = ((double[][]) (INVERSE[1]));
            for (j = 0; j < p[i].length; j++) {
                if (propability == p[i][j]) {
                    break;
                }
            }
            if (j == p[i].length) {
                System.out.println("Nie odnalezione prawdopodobienstwo " + propability);
                return Double.NaN;
            }
            return ((double[][]) INVERSE[2])[i][j];
        } else {
            return Double.NaN;
        }
    }

    // PDF
    @Override
    public double getDensity(final double normvalue) {
        return 0.0;
    }

    // CDF
    @Override
    public double getPropability(final double normvalue) {
        return 0.0;
    }

    @Override
    public Fraction getPropability(final Fraction normvalue) {
        return new Fraction(0, 0);
    }

    @Override
    public void initParameters() {
        this.parameters.put(V, 20);
    }

    @Override
    public List<Class<?>> getAllowedClass(final String param) {
        if (param.equals(V)) {
            return Collections.singletonList(Integer.class);
        }
        return ListUtils.EMPTY_LIST;
    }
}
