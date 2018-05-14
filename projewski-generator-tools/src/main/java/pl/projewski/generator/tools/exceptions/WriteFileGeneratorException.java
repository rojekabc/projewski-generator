package pl.projewski.generator.tools.exceptions;

import pl.projewski.generator.exceptions.GeneratorException;
import pl.projewski.generator.tools.messages.GeneratorToolMessage;

public class WriteFileGeneratorException extends GeneratorException {
    public WriteFileGeneratorException(final String filename, final Throwable cause) {
        super(GeneratorToolMessage.WRITE_FILE_FAILURE, cause, filename);
    }
}
