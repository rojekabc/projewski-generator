package pk.ie.proj.generproj.layout;
import java.awt.LayoutManager;
import java.awt.Container;
import java.awt.Component;
import java.awt.Dimension;

public class AlaNullLayout implements LayoutManager
{
    public void addLayoutComponent(String name, Component c)
    {
    	System.out.println("Add Layout Component");
    }

    public void removeLayoutComponent(Component c)
    {
    	System.out.println("Rem Layout Component");
    }
    
    public Dimension preferredLayoutSize(Container p)
    {
	Component [] c = p.getComponents();
	int maxH = 0, maxW = 0, h, w;
	for (int i=0; i<c.length; i++)
	{
	    h = c[i].getY() + c[i].getHeight();
	    w = c[i].getX() + c[i].getWidth();
	    if (maxH < h) maxH = h;
	    if (maxW < w) maxW = w;
	}
	System.out.println("MaxW " + maxW + " maxH " + maxH );
	return new Dimension(maxW, maxH);
    }

    public Dimension minimumLayoutSize(Container p)
    {
    	System.out.println("minLayout");
	return preferredLayoutSize(p);
    }

    public void layoutContainer(Container parent)
    {
    	System.out.println("layContainer");
    }
}
