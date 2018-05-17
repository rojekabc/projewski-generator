/**
 *
 */
package pl.projewski.free.stream.scanner;

/**
 * @author piotrek
 */
public interface IScannerListener {

    void match(IScanner scanner, int streamPosition, ScannedInputStream stream);

    void notMatch(IScanner scanner, int streamPosition, ScannedInputStream stream);

}
