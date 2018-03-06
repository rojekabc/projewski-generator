/**
 *
 */
package pl.projewski.generator.interfaces;

public interface PackageInterface {
    Class<? extends GeneratorInterface>[] listGenerator();

    Class<? extends LaborDataInterface>[] listLaborData();

    Class<? extends ViewDataInterface>[] listViewData();

    Class<? extends DistributionInterface>[] listDistribution();
}
