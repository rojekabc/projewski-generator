package pk.ie.proj.generproj;
public class DirectoryFilter implements java.io.FileFilter
{
	public boolean accept( java.io.File pathname )
	{
		if ( pathname.isDirectory() )
			return true;
		else
			return false;
	}
}
