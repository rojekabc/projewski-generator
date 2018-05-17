/**
 *
 */
package pl.projewski.free.stream.scanner;

/**
 * @author piotrek
 */
public class PositionScanner extends AbstractScanner {
    private final int position;
    private final byte[] bytea;
    private boolean status;

    public PositionScanner(final int position, final byte[] bytea) {
        if ((bytea == null) || (bytea.length == 0)) {
            throw new IllegalArgumentException();
        }
        this.position = position;
        this.bytea = bytea;
    }


    /* (non-Javadoc)
     * @see pk.ie.proj.tools.stream.interfaces.IScanner#read(byte, int, pk.ie.proj.tools.stream.ScannedInputStream)
     */
    @Override
    public void read(final byte b, final int streamposition, final ScannedInputStream stream) {
        if (listener == null) {
            return;
        }
        if ((streamposition >= position) && (streamposition < position + bytea.length)) {
            if (bytea[streamposition - position] != b) {
                status = false;
                listener.notMatch(this, position, stream);
            } else if (streamposition == position + bytea.length - 1) {
                status = true;
                listener.match(this, position, stream);
            }
        }
    }

    @Override
    public boolean hasMatches() {
        return status;
    }

}
