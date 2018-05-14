package pl.projewski.generator.distribution;

import pl.projewski.generator.abstracts.DistributionBase;
import pl.projewski.generator.common.Fraction;
import pl.projewski.generator.tools.Convert;

import java.util.Arrays;
import java.util.List;

public class FromVector
        extends DistributionBase {
    public static final String CDF = "CDF";
    public static final String PDF = "PDF";
    public static final String INVERSECDF = "INVERSE CDF";

    // INVERSE CDF
    @Override
    public double getInverse(final double propability) {
        // Pobierz parametr
        final Object obj = this.parameters.get(INVERSECDF);
        if (obj == null) {
            return Double.NaN;
        }
        final double[] vec = Convert.tryToTDouble(obj);
        if (vec.length == 0) {
            return Double.NaN;
        }
        // Ustal wartosc zwrotna
        if ((propability >= 0.0) && (propability <= 1.0)) {
            // TODO: teraz wpasuj w przedzial
            return propability;
        } else {
            return Double.NaN;
        }
    }

    // PDF
    @Override
    public double getDensity(final double normvalue) {
        // Pobierz parametr
        final Object obj = this.parameters.get(PDF);
        if (obj == null) {
            return 0.0;
        }
        final double[] vec = Convert.tryToTDouble(obj);
        if (vec.length == 0) {
            return 0.0;
        }
        // Ustal wartosc zwrotna
        if (normvalue >= 0.0 && normvalue <= 1.0) {
            // TODO: wpasuj w przedzial
            return 1.0;
        }
        return 0.0;
    }

    // CDF
    @Override
    public double getPropability(final double normvalue) {
        // Pobierz parametr
        final Object obj = this.parameters.get(CDF);
        if (obj == null) {
            return 0.0;
        }
        final double[] vec = Convert.tryToTDouble(obj);
        if (vec.length == 0) {
            return 0.0;
        }
        // Ustal wartosc zwrotna
        if (normvalue <= 0.0) {
            return 0.0;
        } else if (normvalue < 1.0) {
            // TODO: wpasuj w przedzial
            return normvalue;
        } else {
            return 1.0;
        }
    }

    @Override
    public Fraction getPropability(final Fraction normvalue) {
        return new Fraction(0, 0);
    }

    public void initParameterInterface() {
        this.parameters.put(CDF, null);
        this.parameters.put(PDF, null);
        this.parameters.put(INVERSECDF, null);
    }

    @Override
    public List<Class<?>> getAllowedClass(final String param) {
        return Arrays.asList(double[].class);
    }

    @Override
    protected void initParameters() {
    }
}
