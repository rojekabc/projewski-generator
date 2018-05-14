package pl.projewski.generator.exceptions;


import pl.projewski.generator.messages.DescriptionManager;
import pl.projewski.generator.messages.ILangMessage;

public class GeneratorException
        extends RuntimeException {
    private static final long serialVersionUID = 9100069763925843001L;

    protected GeneratorException(final ILangMessage message, final Throwable cause,
            final String... args) {
        super(DescriptionManager.formatMessage(message.getMessageKey(), message.getDefaultMessage(), args), cause);
    }

    protected GeneratorException(final ILangMessage message, final String... args) {
        this(message, null, args);
    }
}
