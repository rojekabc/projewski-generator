/**
 * 
 */
package pl.projewski.generator;

import pl.projewski.generator.distribution.ChiSquare;
import pl.projewski.generator.distribution.FromVector;
import pl.projewski.generator.distribution.Uniform;
import pl.projewski.generator.generator.*;
import pl.projewski.generator.interfaces.PackageInterface;
import pl.projewski.generator.labordata.*;
import pl.projewski.generator.viewdata.swing.ViewFreq;
import pl.projewski.generator.viewdata.swing.ViewRowSwingTable;
import pl.projewski.generator.viewdata.swing.ViewSpaceStructure;
import pl.projewski.generator.viewdata.swing.ViewTestChiSquare;
import pl.projewski.generator.viewdata.text.PrintFreq;
import pl.projewski.generator.viewdata.text.PrintTestChiSquare;
import pl.projewski.generator.viewdata.text.PrintTestSpeed;
import pl.projewski.generator.viewdata.text.ViewRowTextTable;

/**
 * @author maq
 *
 */
public class BasePackage implements PackageInterface {

	public Class<?>[] listDistribution() {
		return new Class [] {
				ChiSquare.class,
				FromVector.class,
				Uniform.class
		};
	}

	public Class<?>[] listGenerator() {
		return new Class [] {
			GeneratorGausHastings.class,
			GeneratorJavaRandom.class,
			GeneratorLCG.class,
			GeneratorSimConst.class,
			GeneratorSystemTime.class,
			GenericLCG.class};
	}

	public Class<?>[] listLaborData() {
		return new Class [] {
				ArithmeticMean.class,
				pl.projewski.generator.labordata.ChiSquare.class,
				ExternalSort.class,
				FindGCD.class,
				FindMax.class,
				FindMin.class,
				Frequency.class,
				InternalSort.class,
				TestChiSquare.class,
				TestSpeed.class
		};
	}

	public Class<?>[] listViewData() {
		return new Class [] {
				ViewFreq.class,
				ViewRowSwingTable.class,
				ViewRowTextTable.class,
				ViewSpaceStructure.class,
				ViewTestChiSquare.class,
				PrintTestSpeed.class,
				PrintFreq.class,
				PrintTestChiSquare.class
		};
	}

}
