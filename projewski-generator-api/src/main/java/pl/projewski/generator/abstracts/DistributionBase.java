package pl.projewski.generator.abstracts;

import pl.projewski.generator.interfaces.DistributionInterface;

public abstract class DistributionBase extends AbstractParameter implements DistributionInterface {
    @Override
    public String getTypeName() {
        return "distribution";
    }

}
