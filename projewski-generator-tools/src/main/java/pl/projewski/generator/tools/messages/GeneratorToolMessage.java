package pl.projewski.generator.tools.messages;

import lombok.AllArgsConstructor;
import pl.projewski.generator.messages.GeneratorApiMessages;
import pl.projewski.generator.messages.ILangMessage;

@AllArgsConstructor
public enum GeneratorToolMessage implements ILangMessage {
    READ_FILE_FAILURE("Failure when reading from the file {}"),
    WRITE_FILE_FAILURE("Failure when writing to the file {}"),
    NOT_ALLOWED_TYPE("Not allowed type used {}"),
    EMPTY_NUMBER_COTAINER("Empty number container"),
    TEMPORARY_FILE_FAILURE("Cannot use temporary file."),
    WRITE_STREAM_FAILURE("Failure while write to stream.");

    private String defaultMessage;

    @Override
    public String getDefaultMessage() {
        return defaultMessage;
    }

    @Override
    public String getMessageKey() {
        return this.name();
    }

    @Override
    public String getMessageSet() {
        return GeneratorApiMessages.class.getSimpleName();
    }
}
