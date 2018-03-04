package pl.projewski.generator.interfaces;

import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.util.Vector;

import pk.ie.proj.abstracts.AbstractParameter;
import pk.ie.proj.exceptions.ViewDataException;
import pk.ie.proj.tools.Mysys;

/*
 * Interfejs zamiany podawanych elementów na jakiś bardziej sensowny
 * obraz, który można zaprezentować użytkownikowi. Postać wyjściowa
 * prezentacji jest właściwie dowolna i uzależniona od sposobu przedstawienia
 * interfejsu. To jakie parametry ma interfejs jest już uzależnione od
 * parametrów ustawianych poprzez ParameterInterface.
 */
public abstract class ViewDataInterface
	extends AbstractParameter {
	Vector<ViewDataListener> vecViewDataListeners;
	// zwrócenie obiektu jakiegoś dowolnego typu, jaki może byc utworzony
	// w interfejsie do reprezentacji danych
	public abstract Object getView()
		throws ViewDataException;
//	public abstract Object getView(NumberStoreOne data)
//		throws ViewDataException;
//	public abstract Object getView(NumberStoreOne [] data)
//		throws ViewDataException;

	// zarządanie pokazania wyników ustawionych parametrów w sposób jaki jest
	// najbardziej dogodny dla interfejsu
	public abstract void showView()
		throws ViewDataException;
/*	public void showView(NumberStoreOne data)
		throws ViewDataException;
	public void showView(NumberStoreOne [] data)
		throws ViewDataException;
*/
	// jeżeli jest to wygenerowany widok, to po zmianie parametrów może być
	// konieczne jakieś odświeżenie informacji. Niekiedy jest to całkowicie
	// zbędna operacja
	public abstract void refreshView()
		throws ViewDataException;

	// ustawianie danych, jakie mają zorganizować widok
	public abstract void setData(NumberInterface data)
		throws ViewDataException;
//	public abstract void setData(NumberStoreOne data)
//		throws ViewDataException;
/*	public void setData(NumberStoreOne [] data)
		throws ViewDataException;
*/
	public boolean canViewData(ParameterInterface dataSource)
	{
		return true;
	}
	
	public boolean addViewDataListener(ViewDataListener listener)
	{
		if ( listener == null )
			return false;
		if ( this.vecViewDataListeners == null )
			this.vecViewDataListeners = new Vector<ViewDataListener>();
		return this.vecViewDataListeners.add(listener);
	}
	
	public void sendMouseEventToListeners(Frame frame, ParameterInterface pi, MouseEvent event)
	{
		Mysys.debugln("Send Mouse Event");
		if ( this.vecViewDataListeners == null )
			return;
		for ( int i = 0; i < this.vecViewDataListeners.size(); i++ )
		{
			Mysys.debugln("Send Mouse Event to " + i);
			((ViewDataListener)this.vecViewDataListeners.get(i)).onMouseEvent(frame, pi, event);
		}
	}
	
	public String getTypeName() {
		return "viewdata";
	}
	
	public String toString()
	{
		return toString(null);
	}
	
	public String toString(String prefix)
	{
		if ( prefix == null )
			prefix = "";
		StringBuffer sb = new StringBuffer();
		sb.append("ViewData ");
		sb.append(this.getClass().getSimpleName());
		sb.append("\n");
		sb.append(prefix);
		sb.append("[\n");
		sb.append(super.toString(prefix + "\t"));
		sb.append(prefix);
		sb.append("]");
		return sb.toString();
	}
	
	
}
