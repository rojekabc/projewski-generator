package pk.ie.proj.tools.stream;

import pk.ie.proj.enumeration.ClassEnumerator;
import pk.ie.proj.exceptions.NumberStoreException;

public interface NumberReader {
	public boolean hasNext() throws NumberStoreException;
	public int readInt() throws NumberStoreException;
	public long readLong() throws NumberStoreException;
	public float readFloat() throws NumberStoreException;
	public double readDouble() throws NumberStoreException;
	public int read(int [] a) throws NumberStoreException;
	public int read(long [] a) throws NumberStoreException;
	public int read(float [] a) throws NumberStoreException;	
	public int read(double [] a) throws NumberStoreException;
	public Object readAsObject(ClassEnumerator c) throws NumberStoreException;
	public void close() throws NumberStoreException;
}
