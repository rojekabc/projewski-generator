package pl.projewski.generator.tools.exceptions;

import pl.projewski.generator.exceptions.GeneratorException;
import pl.projewski.generator.tools.messages.GeneratorToolMessage;

public class EmptyNumberStoreGeneratorException extends GeneratorException {
    public EmptyNumberStoreGeneratorException() {
        super(GeneratorToolMessage.EMPTY_NUMBER_COTAINER);
    }

}
