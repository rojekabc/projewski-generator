package pk.ie.proj.interfaces;

import pk.ie.proj.abstracts.AbstractParameter;
import pk.ie.proj.exceptions.LaborDataException;

/*
 * Interfejs do wykonywania operacji liczbowych, realizacji algorytmów.
 * Przyjmije na wejściu określone dane, a następnie pozwala odczytać wyniki.
 */
public abstract class LaborDataInterface
	extends AbstractParameter
{
	/**
	 * Ustaw dane wejściowe
	 * 
	 * @param data - dane wejściowe
	 * @throws LaborDataException
	 */
	public abstract void setInputData(NumberInterface data)
		throws LaborDataException;
	/**
	 * Pobierz dane wynikowe
	 * 
	 * @param data - miejsce do zapisania danych wyjściowych
	 * @return true, jeżeli udało się wygenerować dane wynikowe, false jeżeli nie ma takiej możliwości
	 * @throws LaborDataException
	 */
	public abstract boolean getOutputData(NumberInterface data)
		throws LaborDataException;
	
	public String getTypeName() {
		return "labordata";
	}
	
	public String toString()
	{
		return toString(null);
	}
	
	public String toString(String prefix)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("LaborData ");
		sb.append(this.getClass().getSimpleName());
		sb.append("\n");
		if ( prefix != null )
			sb.append(prefix);
		sb.append("[\n");
		if ( prefix != null )
			sb.append(super.toString(prefix + "\t"));
		else
			sb.append(super.toString("\t"));
		if ( prefix != null )
			sb.append(prefix);
		sb.append("]");
		return sb.toString();
	}	
	
}
