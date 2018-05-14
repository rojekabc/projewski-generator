package pl.projewski.generator.exceptions;

import pl.projewski.generator.messages.GeneratorApiMessages;

public class NotAllowedTypeGeneratorException extends GeneratorException {
    public NotAllowedTypeGeneratorException(final String name, final Class<?> type) {
        super(GeneratorApiMessages.NOT_ALLOWED_PARAMETER_TYPE, name, type.getName());
    }
}
