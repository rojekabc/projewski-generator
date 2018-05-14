package tests;

import pl.projewski.generator.tools.stream.ScanedInputStream;
import pl.projewski.generator.tools.stream.interfaces.IScaner;
import pl.projewski.generator.tools.stream.interfaces.IScanerListener;
import pl.projewski.generator.tools.stream.scaner.ContainScaner;
import pl.projewski.generator.tools.stream.scaner.PositionScaner;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author piotrek
 */
public class ScanedInputStreamTest implements IScanerListener {

    public static void main(final String[] args) {
        final ByteArrayInputStream bais = new ByteArrayInputStream(
                "To jest taki tam sobie napis, co prawie nic nie zawiera do skanowania".getBytes());
        final ScanedInputStream isScaned = new ScanedInputStream(bais);
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final IScanerListener listener = new ScanedInputStreamTest();
        IScaner scaner = new ContainScaner("ta".getBytes());
        scaner.setListener(listener);
        isScaned.addScaner(scaner);

        scaner = new PositionScaner(0, "ta".getBytes());
        scaner.setListener(listener);
        isScaned.addScaner(scaner);

        scaner = new PositionScaner(0, "To".getBytes());
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
    public void match(final IScaner scaner, final int streamposition, final ScanedInputStream stream) {
        System.out.println("Scaner match: " + scaner.getClass().getName());
        System.out.println("at streamposition: " + streamposition);
    }

    @Override
    public void notMatch(final IScaner scaner, final int streamposition, final ScanedInputStream stream) {
        System.out.println("Scaner notMatch: " + scaner.getClass().getName());
        System.out.println("at streamposition: " + streamposition);
        stream.remScaner(scaner);
    }

}