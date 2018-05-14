package pl.projewski.generator.exceptions;

import pl.projewski.generator.messages.GeneratorLibMessages;

public class MissingDataGeneratorException extends GeneratorException {
    public MissingDataGeneratorException(final int expected, final int actual) {
        super(GeneratorLibMessages.MISSING_DATA, Integer.toString(expected), Integer.toString(actual));
    }
}
