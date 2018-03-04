/**
 * 
 */
package tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import pk.ie.proj.exceptions.NumberStoreException;
import pk.ie.proj.tools.stream.SeparatorStreamReader;
import pk.ie.proj.tools.stream.SeparatorStreamWriter;

/**
 * @author projewski
 *
 * Test na SeparatorStreamReader/Writer
 */
public class Test10 {

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		int i = 100000;
		SeparatorStreamReader reader;
		SeparatorStreamWriter writer;
		try {
			writer = new SeparatorStreamWriter(new FileOutputStream("test.bin"));
			Random random = new Random();
			while ( i > 0 )
			{
				writer.write(random.nextInt());
				i--;
			}
			writer.close();
			reader = new SeparatorStreamReader(new FileInputStream("test.bin"));
			while ( reader.hasNext() )
			{
				reader.readInt();
			}
			System.out.println();
			reader.close();
			
		} catch (NumberStoreException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
