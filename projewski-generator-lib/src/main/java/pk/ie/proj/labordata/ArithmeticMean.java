package pk.ie.proj.labordata;
import pk.ie.proj.enumeration.ClassEnumerator;
import pk.ie.proj.exceptions.LaborDataException;
import pk.ie.proj.exceptions.NumberStoreException;
import pk.ie.proj.interfaces.LaborDataInterface;
import pk.ie.proj.interfaces.NumberInterface;
import pk.ie.proj.tools.Mysys;
import pk.ie.proj.tools.stream.NumberReader;
import pk.ie.proj.tools.stream.NumberWriter;

public class ArithmeticMean
	extends LaborDataInterface
{
	double _mean;
	int _cnt;

	public void initParameterInterface()
	{
	}

	public ArithmeticMean()
	{
		_mean = 0.0;
		_cnt = 0;
	}

	// podawanie danych wejściowych
	public void setInputData(NumberInterface data)
		throws LaborDataException
	{
		NumberReader is = null;
		try
		{
			is = data.getNumberReader();
			
			// Odczytywanie bez wzgledu na rodzaj danych zapisanych
			// jako double
			while ( is.hasNext() )
			{
				_cnt++;
				_mean += (is.readDouble() - _mean)/_cnt;
			}
		} catch (NumberStoreException e) {
			throw new LaborDataException(e);
		} finally {
			Mysys.closeQuiet(is);
		}
	}
	
	public double getArithmeticMean()
	{
		return _mean;
	}

	public Class<?> [] getAllowedClass(String param)
	{
		return new Class[0];
	}

	@Override
	public boolean getOutputData(NumberInterface gdt) throws LaborDataException {
		NumberWriter writer = null;
		try {
			gdt.setStoreClass( ClassEnumerator.DOUBLE );
			gdt.setSize(1);
			writer = gdt.getNumberWriter();
			writer.write(_mean);
		} catch (Exception e) {
			throw new LaborDataException(e);
		} finally {
			Mysys.closeQuiet(writer);
		}
		return true;
	}
}
