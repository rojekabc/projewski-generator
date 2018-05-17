/**
 *
 */
package pl.projewski.free.stream.scanner;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author piotrek
 */
public class ScannedInputStream extends InputStream {
    private final InputStream is;
    private List<IScanner> scaners;
    private List<IScanner> scanersRemoved = null;
    private List<IScanner> scanersAdded = null;
    private boolean lock = false;
    private int streamposition = 0;

    public ScannedInputStream(final InputStream is) {
        this.is = is;
    }

    public void addScaner(final IScanner scaner) {
        if (lock) {
            if (scanersAdded == null) {
                scanersAdded = new ArrayList<>();
            }
            scanersAdded.add(scaner);
        } else {
            if (scaners == null) {
                scaners = new ArrayList<>();
            }
            scaners.add(scaner);
        }
    }

    public void remScaner(final IScanner scaner) {
        if (lock) {
            if (scanersRemoved == null) {
                scanersRemoved = new ArrayList<>();
            }
            scanersRemoved.add(scaner);
        } else {
            if (scaners != null) {
                scaners.remove(scaner);
            }
        }
    }

    public void clearScaners() {
        if (lock) {
            if (scanersRemoved == null) {
                scanersRemoved = new ArrayList<>(scaners);
            }
        } else {
            if (scaners != null) {
                scaners.clear();
            }
        }
    }

    /**
     * Usuniecie grupy skanerow nalezacych do okreslonej klasy grupujacej
     *
     * @param c
     */
    public void remScanersGroup(final Class c) {
        for (final IScanner scaner : scaners) {
            if (scaner.getGroupClass() == c) {
                scanersRemoved.add(scaner);
            }
        }
        if (!lock) {
            scaners.removeAll(scanersRemoved);
            scanersRemoved.clear();
            scanersRemoved = null;
        }
    }

    @Override
    public int read() throws IOException {
        final int r = is.read();
        if (r >= 0) {
            lock = true;
            for (final IScanner scaner : scaners) {
                if ((scanersRemoved != null) && (scanersRemoved.contains(scaner))) {
                    continue;
                }
                scaner.read((byte) r, streamposition, this);
            }
            lock = false;
            if (scanersRemoved != null) {
                scaners.removeAll(scanersRemoved);
                scanersRemoved.clear();
                scanersRemoved = null;
            }
            if (scanersAdded != null) {
                scaners.addAll(scanersAdded);
                scanersAdded.clear();
                scanersAdded = null;
            }
        }
        streamposition++;
        return r;
    }

}
