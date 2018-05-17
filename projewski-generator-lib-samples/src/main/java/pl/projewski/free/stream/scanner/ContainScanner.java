/**
 *
 */
package pl.projewski.free.stream.scanner;

/**
 * @author piotrek
 */
public class ContainScanner extends AbstractScanner {
    private final byte[] searchBytes;
    private int position;
    private int counter = 0;

    public ContainScanner(final byte[] searchBytes) {
        if ((searchBytes == null) || (searchBytes.length == 0)) {
            throw new IllegalArgumentException();
        }
        this.searchBytes = searchBytes;
    }

    public byte[] getSearchedBytes() {
        return searchBytes;
    }

    @Override
    public void read(final byte b, final int streamposition, final ScannedInputStream stream) {
        if (listener == null) {
            return;
        }
        if (searchBytes[position] != b) {
            position = 0;
        } else {
            position++;
            if (position == searchBytes.length) {
                counter++;
                position = 0;
                listener.match(this, streamposition - searchBytes.length + 1, stream);
            }
        }
    }

    @Override
    public boolean hasMatches() {
        return counter != 0;
    }

    /**
     * @return Returns the counter.
     */
    public int getCounter() {
        return counter;
    }
}
