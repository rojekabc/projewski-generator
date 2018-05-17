/**
 *
 */
package pl.projewski.free.stream.scanner;

/**
 * @author piotrek
 */
public interface IScanner {
    public Class<?> getGroupClass();

    /**
     * Ustawienie dla skanera klasy grupujacej dany skaner, laczacej pomiedzy
     * soba inne skanery
     *
     * @param c klasa grupujaca
     */
    void setGroupClass(Class<?> c);

    void read(byte b, int streamPosition, ScannedInputStream stream);

    void setListener(IScannerListener listener);

    void clearListeners();

    boolean hasMatches();

}
