package pl.projewski.generator.abstracts;

import pl.projewski.generator.interfaces.LaborDataInterface;

public abstract class LaborDataBase extends AbstractParameter implements LaborDataInterface {
    @Override
    protected String getTypeName() {
        return "labordata";
    }
}
