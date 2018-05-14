package pl.projewski.generator.messages;

import org.slf4j.helpers.MessageFormatter;
import pl.projewski.generator.interfaces.DescriptionInterface;

import java.util.ServiceLoader;

public class DescriptionManager {
    private static DescriptionManager instance;

    private final DescriptionInterface descriptionInterface;

    private DescriptionManager() {
        final ServiceLoader<DescriptionInterface> serviceLoader = ServiceLoader.load(DescriptionInterface.class);
        // Take the first one possible
        descriptionInterface = serviceLoader.iterator().hasNext() ? serviceLoader.iterator().next() : null;
    }

    private synchronized static DescriptionManager getInstance() {
        if (instance == null) {
            instance = new DescriptionManager();
        }
        return instance;
    }

    public static String formatMessage(final String key, final String defaultMessage, final String... args) {
        return MessageFormatter.arrayFormat(getMessage(key, defaultMessage), args).getMessage();
    }

    public static String getMessage(final String key, final String defaultMessage) {
        final DescriptionManager manager = getInstance();
        if (manager.descriptionInterface == null) {
            return defaultMessage;
        } else {
            final String message = manager.descriptionInterface.getMessage(key);
            return message == null ? defaultMessage : message;
        }
    }

    public void setLanguage(final String code) {
        if (descriptionInterface != null) {
            descriptionInterface.setLanguage(code);
        }
    }

}
