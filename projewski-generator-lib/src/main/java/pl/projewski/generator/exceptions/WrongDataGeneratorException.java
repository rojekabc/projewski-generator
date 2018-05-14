package pl.projewski.generator.exceptions;

import pl.projewski.generator.messages.GeneratorLibMessages;

public class WrongDataGeneratorException extends GeneratorException {
    public WrongDataGeneratorException() {
        super(GeneratorLibMessages.WRONG_DATA);
    }
}
