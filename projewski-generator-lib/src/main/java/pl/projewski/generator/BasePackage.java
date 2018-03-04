/**
 * 
 */
package pl.projewski.generator;

import pk.ie.proj.distribution.ChiSquare;
import pk.ie.proj.distribution.FromVector;
import pk.ie.proj.distribution.Uniform;
import pk.ie.proj.generator.GeneratorGausHastings;
import pk.ie.proj.generator.GeneratorJavaRandom;
import pk.ie.proj.generator.GeneratorLCG;
import pk.ie.proj.generator.GeneratorSimConst;
import pk.ie.proj.generator.GeneratorSystemTime;
import pk.ie.proj.generator.GenericLCG;
import pk.ie.proj.interfaces.PackageInterface;
import pk.ie.proj.labordata.ArithmeticMean;
import pk.ie.proj.labordata.ExternalSort;
import pk.ie.proj.labordata.FindGCD;
import pk.ie.proj.labordata.FindMax;
import pk.ie.proj.labordata.FindMin;
import pk.ie.proj.labordata.Frequency;
import pk.ie.proj.labordata.InternalSort;
import pk.ie.proj.labordata.TestChiSquare;
import pk.ie.proj.labordata.TestSpeed;
import pk.ie.proj.viewdata.swing.ViewFreq;
import pk.ie.proj.viewdata.swing.ViewRowSwingTable;
import pk.ie.proj.viewdata.swing.ViewSpaceStructure;
import pk.ie.proj.viewdata.swing.ViewTestChiSquare;
import pk.ie.proj.viewdata.text.PrintFreq;
import pk.ie.proj.viewdata.text.PrintTestChiSquare;
import pk.ie.proj.viewdata.text.PrintTestSpeed;
import pk.ie.proj.viewdata.text.ViewRowTextTable;
import pl.projewski.generator.distribution.ChiSquare;
import pl.projewski.generator.distribution.FromVector;
import pl.projewski.generator.distribution.Uniform;
import pl.projewski.generator.generator.*;
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
