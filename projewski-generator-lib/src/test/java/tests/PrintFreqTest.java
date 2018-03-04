/**
 *
 */
package tests;

import pl.projewski.generator.enumeration.ClassEnumerator;
import pl.projewski.generator.exceptions.*;
import pl.projewski.generator.generator.GeneratorGausHastings;
import pl.projewski.generator.generator.GeneratorLCG;
import pl.projewski.generator.interfaces.GeneratorInterface;
import pl.projewski.generator.interfaces.LaborDataInterface;
import pl.projewski.generator.interfaces.NumberInterface;
import pl.projewski.generator.interfaces.ViewDataInterface;
import pl.projewski.generator.labordata.Frequency;
import pl.projewski.generator.tools.NumberStoreOne;
import pl.projewski.generator.tools.stream.NumberWriter;
import pl.projewski.generator.viewdata.text.PrintFreq;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author projewski
 */
public class PrintFreqTest {

    public static void testForLCG(NumberInterface store) throws ParameterException, GeneratorException,
            NumberStoreException, IOException {
        GeneratorInterface gi = new GeneratorLCG();
        gi.setParameter(GeneratorLCG.M, Integer.valueOf(1073741823));
        gi.setParameter(GeneratorLCG.A, Integer.valueOf(742938285));
        gi.setParameter(GeneratorLCG.C, Integer.valueOf(0));
        gi.setParameter(GeneratorLCG.SEED, Long.valueOf(1130656722138l));
        NumberWriter writer = store.getNumberWriter();
        store.setDataSource(gi);
        gi.init();
        gi.rawFill(writer, ClassEnumerator.DOUBLE, 5000);
        writer.close();
    }

    public static void testForGaus(NumberInterface store)
            throws ParameterException, NumberStoreException, GeneratorException, IOException {
        GeneratorInterface gi = new GeneratorGausHastings();
        GeneratorInterface internalgi = new GeneratorLCG();

/*			internalgi.setParameter(GeneratorLCG.M, new Integer(101));
		internalgi.setParameter(GeneratorLCG.A, new Integer(11));
		internalgi.setParameter(GeneratorLCG.C, new Integer(17));
		internalgi.setParameter(GeneratorLCG.SEED, new Long(1130656722138l));
*/
        internalgi.setParameter(GeneratorLCG.M, Integer.valueOf(1073741823));
        internalgi.setParameter(GeneratorLCG.A, Integer.valueOf(742938285));
        internalgi.setParameter(GeneratorLCG.C, Integer.valueOf(0));
        internalgi.setParameter(GeneratorLCG.SEED, Long.valueOf(1130656722138l));
        gi.setParameter(GeneratorGausHastings.GENERATOR, internalgi);
        NumberWriter writer = store.getNumberWriter();
        store.setDataSource(gi);
        gi.init();
        gi.rawFill(writer, ClassEnumerator.DOUBLE, 5000);
        writer.close();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            NumberInterface ni = new NumberStoreOne();

            //testForLCG(ni);
            testForGaus(ni);

            LaborDataInterface li = new Frequency();
            ViewDataInterface vi = new PrintFreq();
            NumberInterface freq = new NumberStoreOne();
            li.setParameter(Frequency.CLASSAMMOUNT, Integer.valueOf(20));
            li.setInputData(ni);
            li.getOutputData(freq);
            freq.setDataSource(li);
            vi.setParameter(PrintFreq.LINESAMMOUNT, Integer.valueOf(10));
            vi.setData(freq);
            vi.showView();
        } catch (NumberStoreException e) {
            e.printStackTrace();
        } catch (GeneratorException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LaborDataException e) {
            e.printStackTrace();
        } catch (ViewDataException e) {
            e.printStackTrace();
        } catch (ParameterException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

}
