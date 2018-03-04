package pk.ie.proj.generproj;
public class ExtensionFilter implements java.io.FileFilter
{
	private String extension;
	
	public ExtensionFilter( String ext )
	{
		extension = "." + ext;
	}
	
	public boolean accept( java.io.File pathname )
	{
		if ( pathname.isDirectory() )
			return false;
		if ( pathname.getName().endsWith(extension) )
			return true;
		return false;
	}
}
