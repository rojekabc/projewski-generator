package pl.projewski.generator.abstracts;

import pl.projewski.generator.interfaces.GeneratorInterface;

public abstract class GeneratorBase extends AbstractParameter implements GeneratorInterface {
    @Override
    public String getTypeName() {
        return "generator";
    }

}
