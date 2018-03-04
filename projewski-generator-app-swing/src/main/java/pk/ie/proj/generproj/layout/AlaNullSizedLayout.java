package pk.ie.proj.generproj.layout;
import java.awt.LayoutManager;
import java.awt.Container;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;

// NIE MOŻE BYĆ UŻYWANE W SCROLL PANE
// NIE MOŻNA DODAWAĆ NOWYH KOMPONENTÓW PODCZAS DZIAŁANIA

public class AlaNullSizedLayout implements LayoutManager
{
	java.util.Vector<Rectangle> vecSizes = new java.util.Vector<Rectangle>();
	int orgHeight = 0;
	int orgWidth = 0;
	int orgParam = CHANGE_X | CHANGE_Y;

	public final static int CHANGE_X=1;
	public final static int CHANGE_Y=2;

	AlaNullSizedLayout()
	{
	}

	public AlaNullSizedLayout(int width, int height)
	{
		orgWidth = width;
		orgHeight = height;
	}

	public AlaNullSizedLayout(int width, int height, int param)
	{
		orgWidth = width;
		orgHeight = height;
		orgParam = param;
	}
	
	public void addLayoutComponent(String name, Component c)
	{
	}
	
	public void removeLayoutComponent(Component c)
	{
		vecSizes.clear();
	}
	
	public Dimension preferredLayoutSize(Container parent)
	{
		return new Dimension( orgWidth, orgHeight );
	}
	
	public Dimension minimumLayoutSize(Container p)
	{
		return preferredLayoutSize(p);
	}
	
	public void layoutContainer(Container parent)
	{
		if ( parent.getHeight() <= 0 || parent.getWidth() <= 0 )
			return;
		int n = parent.getComponentCount();
		if ( vecSizes.size() != n )
		{
			orgHeight = parent.getHeight();
			orgWidth = parent.getWidth();
			vecSizes.clear();
			for ( int i = 0; i < n; i++ )
			{
				Component c = parent.getComponent( i );
				vecSizes.add( c.getBounds() );
			}
			return;
		}
		float chw = 1.0f;
		float chh = 1.0f;

		if ( (orgParam & CHANGE_X) == CHANGE_X )
			chw = (float)parent.getWidth()/(float)orgWidth;
		if ( (orgParam & CHANGE_Y) == CHANGE_Y )
			chh = (float)parent.getHeight()/(float)orgHeight;

//		int pos;
		for ( int i = 0; i < n; i++ )
		{
			Component c = parent.getComponent( i );
			java.awt.Rectangle r = (java.awt.Rectangle)vecSizes.get( i );
			int cx = (int)(r.getX()*chw);
			int cy = (int)(r.getY()*chh);
			int cw = (int)(r.getWidth()*chw);
			int ch = (int)(r.getHeight()*chh);
			c.setBounds(cx, cy, cw, ch);
		}
	}
}
