package pl.projewski.generator.messages;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum GeneratorLibMessages implements ILangMessage {
    MISSING_DATA("Missing data size. Expected: {}, actual: {}."),
    NOT_IMPLEMENTED("Functionality not implemented yet."),
    WRONG_PARAMETER("Wrong value of parameter {}."),
    WRONG_DATA("Wrong incoming data."),
    NOT_ALLOWED_OPERATION("Operation not allowed.");

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
