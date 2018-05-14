package tests;

import pl.projewski.generator.generator.GeneratorLCG;
import pl.projewski.generator.generator.GeneratorSystemTime;
import pl.projewski.generator.interfaces.GeneratorInterface;
import pl.projewski.generator.interfaces.LaborDataInterface;
import pl.projewski.generator.interfaces.NumberInterface;
import pl.projewski.generator.interfaces.ViewDataInterface;
import pl.projewski.generator.labordata.TestChiSquare;
import pl.projewski.generator.tools.Mysys;
import pl.projewski.generator.tools.NumberStoreOne;
import pl.projewski.generator.tools.VectorDouble;
import pl.projewski.generator.viewdata.text.PrintTestChiSquare;

/**
 * @author projewski
 */
public class PrintTestChiSquareTest {

    public static GeneratorInterface testForLCG() {
        final GeneratorInterface gi = new GeneratorLCG();
        gi.setParameter(GeneratorLCG.M, 1073741823);
        gi.setParameter(GeneratorLCG.A, 742938285);
        gi.setParameter(GeneratorLCG.C, 0);
        gi.setParameter(GeneratorLCG.SEED, new GeneratorSystemTime());
        // gi.setParameter(GeneratorLCG.SEED, Long.valueOf(1130656722138l));
        gi.init();
        return gi;
    }

    public static void main(final String[] args) {
        try {
            final LaborDataInterface test = new TestChiSquare();
            final ViewDataInterface view = new PrintTestChiSquare();
            final NumberInterface testResult = new NumberStoreOne();
            test.setParameter(TestChiSquare.GENERATOR, testForLCG());
            test.setParameter(TestChiSquare.GENERATIONAMMOUNT, 1000);
            test.getOutputData(testResult);
            testResult.setDataSource(test);
            view.setParameter(PrintTestChiSquare.SECTIONS, new VectorDouble(new double[]{0.01, 0.025, 0.05, 0.1, 0.2}));
            view.setData(testResult);
            view.showView();
        } catch (final Exception e) {
            Mysys.error(e);
        }


    }

}
