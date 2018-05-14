package pl.projewski.generator.exceptions;

import pl.projewski.generator.messages.GeneratorLibMessages;

public class NotImplementedGeneratorException extends GeneratorException {
    public NotImplementedGeneratorException() {
        super(GeneratorLibMessages.NOT_IMPLEMENTED);
    }
}
