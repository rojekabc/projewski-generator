package pl.projewski.generator.tools.exceptions;

import pl.projewski.generator.exceptions.GeneratorException;
import pl.projewski.generator.tools.messages.GeneratorToolMessage;

public class WriteStreamGeneratorException extends GeneratorException {
    public WriteStreamGeneratorException(final Throwable cause) {
        super(GeneratorToolMessage.WRITE_STREAM_FAILURE, cause);
    }
}
