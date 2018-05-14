package pl.projewski.generator.exceptions;

import pl.projewski.generator.messages.GeneratorLibMessages;

public class NotAllowedOperationGeneratorException extends GeneratorException {
    public NotAllowedOperationGeneratorException() {
        super(GeneratorLibMessages.NOT_ALLOWED_OPERATION);
    }
}
