/**
 *
 */
package pl.projewski.free.stream.scanner;

/**
 * @author piotrek
 */
abstract class AbstractScanner implements IScanner {
    protected IScannerListener listener = null;
    private Class<?> groupClass = null;

    /* (non-Javadoc)
     * @see pk.ie.proj.tools.stream.interfaces.IScanner#getGroupClass()
     */
    @Override
    public Class<?> getGroupClass() {
        return this.groupClass;
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.tools.stream.interfaces.IScanner#setClass(java.lang.Class)
     */
    @Override
    public void setGroupClass(final Class<?> c) {
        groupClass = c;
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.tools.stream.interfaces.IScanner#clearListeners()
     */
    @Override
    public void clearListeners() {
        listener = null;

    }

    /* (non-Javadoc)
     * @see pk.ie.proj.tools.stream.interfaces.IScanner#setListener(pk.ie.proj.tools.stream.interfaces.IScannerListener)
     */
    @Override
    public void setListener(final IScannerListener listener) {
        this.listener = listener;
    }

}
