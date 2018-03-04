package pk.ie.proj.tools.stream;

import java.io.IOException;
import java.io.InputStream;

import pk.ie.proj.tools.stream.interfaces.IScaner;
import pk.ie.proj.tools.stream.scaner.ContainScaner;

public class StreamUtil
{
	public static boolean contains(InputStream is, byte [] bytea) throws IOException
	{
		ScanedInputStream isScaned = new ScanedInputStream(is);
		ContainScaner scaner = new ContainScaner(bytea);
		isScaned.addScaner(scaner);
		while (isScaned.read() != -1) {}
		if (scaner.getCounter() > 0)
			return true;
		return false;
	}
	
	public static boolean startsWith(InputStream is, byte [] bytea)
	{
		return false;
	}
	
	public static void makeStatistic(InputStream is, int [] counter)
	{
	}
}
