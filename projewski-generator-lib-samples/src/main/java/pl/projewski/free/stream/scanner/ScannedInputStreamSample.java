package pl.projewski.free.stream.scanner;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author piotrek
 */
public class ScannedInputStreamSample implements IScannerListener {

    public static void main(final String[] args) {
        final ByteArrayInputStream bais = new ByteArrayInputStream(
                "To jest taki tam sobie napis, co prawie nic nie zawiera do skanowania".getBytes());
        final ScannedInputStream isScaned = new ScannedInputStream(bais);
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final IScannerListener listener = new ScannedInputStreamSample();
        IScanner scaner = new ContainScanner("ta".getBytes());
        scaner.setListener(listener);
        isScaned.addScaner(scaner);

        scaner = new PositionScanner(0, "ta".getBytes());
        scaner.setListener(listener);
        isScaned.addScaner(scaner);

        scaner = new PositionScanner(0, "To".getBytes());
        scaner.setListener(listener);
        isScaned.addScaner(scaner);

        int x;
        try {
            while ((x = isScaned.read()) != -1) {
                baos.write(x);
            }
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void match(final IScanner scanner, final int streamposition, final ScannedInputStream stream) {
        System.out.println("Scanner match: " + scanner.getClass().getName());
        System.out.println("at stream position: " + streamposition);
    }

    @Override
    public void notMatch(final IScanner scanner, final int streamposition, final ScannedInputStream stream) {
        System.out.println("Scanner notMatch: " + scanner.getClass().getName());
        System.out.println("at stream position: " + streamposition);
        stream.remScaner(scanner);
    }

}