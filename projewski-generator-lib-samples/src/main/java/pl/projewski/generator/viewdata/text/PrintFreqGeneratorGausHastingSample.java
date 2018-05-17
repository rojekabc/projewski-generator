package pl.projewski.generator.viewdata.text;

import pl.projewski.generator.common.NumberWriter;
import pl.projewski.generator.enumeration.ClassEnumerator;
import pl.projewski.generator.generator.GeneratorGausHastings;
import pl.projewski.generator.generator.GeneratorLCG;
import pl.projewski.generator.interfaces.GeneratorInterface;
import pl.projewski.generator.interfaces.LaborDataInterface;
import pl.projewski.generator.interfaces.NumberInterface;
import pl.projewski.generator.interfaces.ViewDataInterface;
import pl.projewski.generator.labordata.Frequency;
import pl.projewski.generator.tools.NumberStoreOne;

public class PrintFreqGeneratorGausHastingSample {

    private static void testForGaus(final NumberInterface store) {
        final GeneratorInterface internalGenerator = new GeneratorLCG();
        internalGenerator.setParameter(GeneratorLCG.M, 1073741823);
        internalGenerator.setParameter(GeneratorLCG.A, 742938285);
        internalGenerator.setParameter(GeneratorLCG.C, 0);
        internalGenerator.setParameter(GeneratorLCG.SEED, 1130656722138l);

        final GeneratorInterface gi = new GeneratorGausHastings();
        gi.setParameter(GeneratorGausHastings.GENERATOR, internalGenerator);
        gi.init();

        final NumberWriter writer = store.getNumberWriter();
        store.setDataSource(gi);
        gi.rawFill(writer, ClassEnumerator.DOUBLE, 5000);
        writer.close();
    }

    public static void main(final String[] args) {
        final NumberInterface ni = new NumberStoreOne();
        testForGaus(ni);

        final LaborDataInterface li = new Frequency();
        final ViewDataInterface vi = new PrintFreq();
        final NumberInterface freq = new NumberStoreOne();
        li.setParameter(Frequency.CLASSAMMOUNT, 20);
        li.setInputData(ni);
        li.getOutputData(freq);
        freq.setDataSource(li);
        vi.setParameter(PrintFreq.LINESAMMOUNT, 10);
        vi.setData(freq);
        vi.showView();
    }
}
