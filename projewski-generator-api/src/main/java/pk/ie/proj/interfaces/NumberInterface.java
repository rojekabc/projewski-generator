package pk.ie.proj.interfaces;

import pk.ie.proj.enumeration.ClassEnumerator;
import pk.ie.proj.exceptions.NumberStoreException;
import pk.ie.proj.tools.stream.NumberReader;
import pk.ie.proj.tools.stream.NumberWriter;

public interface NumberInterface {
	// ustawienie typu danych, jakie będą przechowywane
	public void setStoreClass(ClassEnumerator c) throws NumberStoreException;
	// przechowywany typ danych
	public ClassEnumerator getStoreClass();
	// Za każdym razem jest tworzona nowa instancja
	public NumberReader getNumberReader() throws NumberStoreException;
	// Mozliwe jest utworzenie tylko jednej instancji dla danego obiektu
	public NumberWriter getNumberWriter() throws NumberStoreException;
	// podanie ilości przechowywanych danych
	public int getSize() throws NumberStoreException;
	public void setSize(int size);
	// określanie źródła danych
	public ParameterInterface getDataSource() throws NumberStoreException;
	// ustawienie źródła danych
	public void setDataSource(ParameterInterface dataSource) throws NumberStoreException;
}
