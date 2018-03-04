/**
 * 
 */
package pl.projewski.generator.interfaces;

public interface PackageInterface {
	Class<?> [] listGenerator();
	Class<?> [] listLaborData();
	Class<?> [] listViewData();
	Class<?> [] listDistribution();
}
