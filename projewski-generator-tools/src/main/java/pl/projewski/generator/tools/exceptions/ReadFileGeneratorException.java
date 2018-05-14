package pl.projewski.generator.tools.exceptions;

import pl.projewski.generator.exceptions.GeneratorException;
import pl.projewski.generator.tools.messages.GeneratorToolMessage;

public class ReadFileGeneratorException extends GeneratorException {
    public ReadFileGeneratorException(final String filename, final Throwable cause) {
        super(GeneratorToolMessage.READ_FILE_FAILURE, cause, filename);
    }
}
