package pl.projewski.free.stream.scanner;

import java.io.IOException;
import java.io.InputStream;

public class StreamUtil {
    public static boolean contains(final InputStream is, final byte[] bytea) throws IOException {
        final ScannedInputStream scannedInputStream = new ScannedInputStream(is);
        final IScanner scanner = new ContainScanner(bytea);
        scannedInputStream.addScaner(scanner);
        while (scannedInputStream.read() != -1) {
        }
        return scanner.hasMatches();
    }
}
