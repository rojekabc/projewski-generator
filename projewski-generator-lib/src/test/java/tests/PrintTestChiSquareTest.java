/**
 * 
 */
package tests;

import pl.projewski.generator.exceptions.*;
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
 *
 */
public class PrintTestChiSquareTest {

	public static GeneratorInterface testForLCG() throws ParameterException, GeneratorException
	{
		GeneratorInterface gi = new GeneratorLCG();
		gi.setParameter(GeneratorLCG.M, Integer.valueOf(1073741823));
		gi.setParameter(GeneratorLCG.A, Integer.valueOf(742938285));
		gi.setParameter(GeneratorLCG.C, Integer.valueOf(0));
		gi.setParameter(GeneratorLCG.SEED, new GeneratorSystemTime());
		// gi.setParameter(GeneratorLCG.SEED, Long.valueOf(1130656722138l));
		gi.init();
		return gi;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			LaborDataInterface test = new TestChiSquare();
			ViewDataInterface view = new PrintTestChiSquare();
			NumberInterface testResult = new NumberStoreOne();
			test.setParameter(TestChiSquare.GENERATOR, testForLCG());
			test.setParameter(TestChiSquare.GENERATIONAMMOUNT, Integer.valueOf(1000));
			test.getOutputData(testResult);
			testResult.setDataSource(test);
			view.setParameter(PrintTestChiSquare.SECTIONS, new VectorDouble(new double[] {0.01, 0.025, 0.05, 0.1, 0.2}));
			view.setData(testResult);
			view.showView();
		} catch (ViewDataException e) {
			Mysys.error(e);
		} catch (LaborDataException e) {
			Mysys.error(e);
		} catch (ParameterException e) {
			Mysys.error(e);
		} catch (GeneratorException e) {
			Mysys.error(e);
		} catch (NumberStoreException e) {
			Mysys.error(e);
		}
		

	}

}
