/**
 * 
 */
package tests;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import pk.ie.proj.tools.stream.ScanedInputStream;
import pk.ie.proj.tools.stream.interfaces.IScaner;
import pk.ie.proj.tools.stream.interfaces.IScanerListener;
import pk.ie.proj.tools.stream.scaner.ContainScaner;
import pk.ie.proj.tools.stream.scaner.PositionScaner;

/**
 * @author piotrek
 *
 */
public class ScanedInputStreamTest implements IScanerListener {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) 
	{
		ByteArrayInputStream bais = new ByteArrayInputStream(
				"To jest taki tam sobie napis, co prawie nic nie zawiera do skanowania".getBytes());
		ScanedInputStream isScaned = new ScanedInputStream(bais);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		IScanerListener listener = new ScanedInputStreamTest();
		IScaner scaner = null;
		
		scaner = new ContainScaner("ta".getBytes());
		scaner.setListener(listener);
		isScaned.addScaner(scaner);

		scaner = new PositionScaner(0, "ta".getBytes());
		scaner.setListener(listener);
		isScaned.addScaner(scaner);

		scaner = new PositionScaner(0, "To".getBytes());
		scaner.setListener(listener);
		isScaned.addScaner(scaner);
		
		int x;
		try {
			while ((x = isScaned.read()) != -1)
				baos.write(x);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void match(IScaner scaner, int streamposition, ScanedInputStream stream)
	{
		System.out.println("Scaner match: " + scaner.getClass().getName());
		System.out.println("at streamposition: " + streamposition);
	}

	public void notMatch(IScaner scaner, int streamposition, ScanedInputStream stream) {
		System.out.println("Scaner notMatch: " + scaner.getClass().getName());
		System.out.println("at streamposition: " + streamposition);
		stream.remScaner(scaner);
	}

}