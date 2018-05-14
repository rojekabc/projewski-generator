package pl.projewski.generator.messages;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum GeneratorApiMessages implements ILangMessage {
    NOT_ALLOWED_PARAMETER_TYPE("Value of parameter {} has not allowed type {}");

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
