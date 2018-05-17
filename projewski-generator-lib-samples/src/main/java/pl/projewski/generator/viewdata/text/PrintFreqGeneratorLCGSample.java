package pl.projewski.generator.viewdata.text;

import pl.projewski.generator.common.NumberWriter;
import pl.projewski.generator.enumeration.ClassEnumerator;
import pl.projewski.generator.generator.GeneratorLCG;
import pl.projewski.generator.interfaces.GeneratorInterface;
import pl.projewski.generator.interfaces.LaborDataInterface;
import pl.projewski.generator.interfaces.NumberInterface;
import pl.projewski.generator.interfaces.ViewDataInterface;
import pl.projewski.generator.labordata.Frequency;
import pl.projewski.generator.tools.NumberStoreOne;

public class PrintFreqGeneratorLCGSample {

    static void testForLCG(final NumberInterface store) {
        final GeneratorInterface gi = new GeneratorLCG();
        gi.setParameter(GeneratorLCG.M, 1073741823);
        gi.setParameter(GeneratorLCG.A, 742938285);
        gi.setParameter(GeneratorLCG.C, 0);
        gi.setParameter(GeneratorLCG.SEED, 1130656722138l);
        gi.init();

        final NumberWriter writer = store.getNumberWriter();
        store.setDataSource(gi);
        gi.rawFill(writer, ClassEnumerator.DOUBLE, 5000);
        writer.close();
    }

    public static void main(final String[] args) {
        try {
            final NumberInterface ni = new NumberStoreOne();

            testForLCG(ni);

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
        } catch (final Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
