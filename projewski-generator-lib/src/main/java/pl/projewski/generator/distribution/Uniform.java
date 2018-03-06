package pl.projewski.generator.distribution;

import org.apache.commons.collections.ListUtils;
import pl.projewski.generator.abstracts.DistributionBase;
import pl.projewski.generator.common.Fraction;

import java.util.List;

public class Uniform
        extends DistributionBase {
    // INVERSE CDF
    @Override
    public double getInverse(final double propability) {
        if ((propability >= 0.0) && (propability <= 1.0)) {
            return propability;
        } else {
            return Double.NaN;
        }
    }

    // PDF
    @Override
    public double getDensity(final double normvalue) {
        if (normvalue >= 0.0 && normvalue <= 1.0) {
            return 1.0;
        }
        return 0.0;
    }

    // CDF
    @Override
    public double getPropability(final double normvalue) {
        if (normvalue <= 0.0) {
            return 0.0;
        } else if (normvalue <= 1.0) {
            return normvalue;
        } else {
            return 1.0;
        }
    }

    @Override
    public Fraction getPropability(final Fraction normvalue) {
        if (normvalue.getDouble() < 0.0) {
            return new Fraction(0, 0);
        } else if (normvalue.getDouble() <= 1.0) {
            return new Fraction(normvalue);
        } else {
            return new Fraction(1, 1);
        }
    }

    @Override
    public List<Class<?>> getAllowedClass(final String param) {
        return ListUtils.EMPTY_LIST;
    }

    @Override
    protected void initParameters() {

    }
}
