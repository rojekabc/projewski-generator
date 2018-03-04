/**
 * 
 */
package pk.ie.proj.tools.stream;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * @author projewski
 *
 */
public class SeparatorStreamWriter extends OutputStreamWriter implements NumberWriter {

	public SeparatorStreamWriter(OutputStream arg0) {
		super(arg0);
	}

	int separator = ' ';
	
	public void write(int a) throws IOException
	{
		super.write(Integer.toString(a));
		super.write(separator);
	}
	
	public void write(long a) throws IOException
	{
		super.write(Long.toString(a));
		super.write(separator);
	}
	
	public void write(float a) throws IOException
	{
		super.write(Float.toString(a));
		super.write(separator);
	}

	public void write(double a) throws IOException
	{
		super.write(Double.toString(a));
		super.write(separator);
	}
	
	public void write(int [] a) throws IOException
	{
		for (int i=0; i<a.length; i++)
			write(a[i]);
	}
	public void write(long [] a) throws IOException
	{
		for (int i=0; i<a.length; i++)
			write(a[i]);
	}
	public void write(float [] a) throws IOException
	{
		for (int i=0; i<a.length; i++)
			write(a[i]);
	}
	public void write(double [] a) throws IOException
	{
		for (int i=0; i<a.length; i++)
			write(a[i]);
	}
}
