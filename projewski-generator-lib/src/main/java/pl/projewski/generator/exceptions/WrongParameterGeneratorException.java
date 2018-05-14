package pl.projewski.generator.exceptions;

import pl.projewski.generator.messages.GeneratorLibMessages;

public class WrongParameterGeneratorException extends GeneratorException {
    public WrongParameterGeneratorException(final String parameter) {
        super(GeneratorLibMessages.WRONG_PARAMETER, parameter);
    }
}
