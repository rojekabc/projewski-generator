package pl.projewski.generator.interfaces;

public interface DescriptionInterface {
    /**
     * Get the message for the key.
     *
     * @param key identifier of message
     * @return message
     */
    String getMessage(String key);

    /**
     * Set up language.
     *
     * @param countryId country identifier
     */
    void setLanguage(String countryId);
}
