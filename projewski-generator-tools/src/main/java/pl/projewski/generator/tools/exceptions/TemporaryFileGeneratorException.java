package pl.projewski.generator.tools.exceptions;

import pl.projewski.generator.exceptions.GeneratorException;
import pl.projewski.generator.tools.messages.GeneratorToolMessage;

public class TemporaryFileGeneratorException extends GeneratorException {
    public TemporaryFileGeneratorException() {
        super(GeneratorToolMessage.TEMPORARY_FILE_FAILURE);
    }

    public TemporaryFileGeneratorException(final Throwable cause) {
        super(GeneratorToolMessage.TEMPORARY_FILE_FAILURE, cause);
    }
}
