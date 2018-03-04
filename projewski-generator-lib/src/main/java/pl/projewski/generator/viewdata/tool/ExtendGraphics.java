package pl.projewski.generator.viewdata.tool;

import java.awt.Graphics;

public class ExtendGraphics
{
//	private static int arrowType;
	public final static int ARROW_LINE = 0;
	public final static int ARROW_TRIANGLE = 1;
	public final static int ARROW_FILL_TRIANGLE = 2;

	private boolean bTransformY = false;
	private boolean bTransformX = false;
	private int maxX = 0;
	private int maxY = 0;
	private double grXmin = 0.0;
	private double grXmax = 0.0;
	private double grYmin = 0.0;
	private double grYmax = 0.0;
	private int lmarginX = 0;
	private int tmarginY = 0;
	private int rmarginX = 0;
	private int bmarginY = 0;

	// Zwracana jest wartość jaka jest równoznaczna długości jednego piksla
	public double getXPixelSize()
	{
			return (grXmax - grXmin)/(maxX-rmarginX-lmarginX);
	}
	public double getYPixelSize()
	{
			return (grYmax - grYmin)/(maxY-bmarginY-tmarginY);
	}
	
	public void setTransformX(boolean b)
	{
		bTransformX = b;
	}
	
	public void setTransformY(boolean b)
	{
		bTransformY = b;
	}

	public void setMargin( int lmargX, int tmargY, int rmargX, int bmargY )
	{
		lmarginX = lmargX;
		tmarginY = tmargY;
		rmarginX = rmargX;
		bmarginY = bmargY;
	}

/*	private double transformX( int x )
	{
		if ( bTransformX )
		{
			if ( grXmin == grXmax )
				return (double)((maxX-rmarginX) - x);
			else
				return (double)(grXmax - x);
		}
		else
			return (double)x;
	}*/
	
	private double transformY( int y )
	{
		if ( bTransformY )
		{
			if ( grYmin == grYmax )
				return (double)(maxY-bmarginY - y);
			else
				return (double)(grYmax - y);
		}
		else
			return (double)y;
	}

	private double transformX( double x )
	{
		if ( bTransformX )
		{
			if ( grXmin == grXmax )
				return ((maxX-rmarginX) - x);
			else
				return (grXmax - x);
		}
		else
			return x;
	}
	
	private double transformY( double y )
	{
		if ( bTransformY )
		{
			if ( grYmin == grYmax )
				return (maxY-bmarginY - y);
			else
				return (grYmax - y);
		}
		else
			return y;
	}

	public boolean isXTransform()
	{
		return bTransformX;
	}
	
	public boolean isYTransform()
	{
		return bTransformY;
	}
	
	public boolean doXTransform( int max )
	{
		bTransformX = !bTransformX;
		maxX = max;
		return bTransformX;
	}

	public boolean doYTransform( int max )
	{
		bTransformY = !bTransformY;
		maxY = max;
		return bTransformY;
	}

	private int recountX(double x)
	{
		if ( grXmax != grXmin )
		{
			if ( !bTransformX )
				return lmarginX + (int)(((maxX-rmarginX-lmarginX) * (x-grXmin))/(grXmax - grXmin));
			else
				return lmarginX + (int)(((maxX-rmarginX-lmarginX) * x)/(grXmax - grXmin));
		}
		else
			return lmarginX + (int)x;
	}

	private int recountY(double y)
	{
		if ( grYmax != grYmin )
		{
			if ( bTransformY )
				return tmarginY + (int)(((maxY-bmarginY-tmarginY) * y)/(grYmax - grYmin));
			else
				return tmarginY + (int)(((maxY-bmarginY-tmarginY) * (y-grYmin))/(grYmax - grYmin));
		}
		else
			return tmarginY + (int)y;
	}
	
	public void setGraphScreenSize(int scrMaxX, int scrMaxY)
	{
		maxX = scrMaxX;
		maxY = scrMaxY;
	}
	
	public void setGraphDataBounds(
			double xmin, double xmax,
			double ymin, double ymax
		)
	{
		grXmin = xmin;
		grXmax = xmax;
		grYmin = ymin;
		grYmax = ymax;
	}

	public void setGraphBounds(
			double xmin, double xmax,
			double ymin, double ymax,
			int scrMaxX, int scrMaxY )
	{
		maxX = scrMaxX;
		maxY = scrMaxY;
		grXmin = xmin;
		grXmax = xmax;
		grYmin = ymin;
		grYmax = ymax;
//		System.out.println("grXmax="+grXmax+" grXmin="+grXmin);
	}
	
	/**
	 * Ustal znak liczby.
	 * @param x Liczba wejściowa
	 * @return 1 dla dodatnich, -1 dla ujemnych, 0 dla zera
	 */
	public int sign(int x) {
		if ( x > 0 )
			return 1;
		else if ( x < 0 )
			return -1;
		else
			return 0;
	}
	
	/**
	 * Ustal znak liczby.
	 * @param x Liczba wejściowa
	 * @return 1 dla dodatnich, -1 dla ujemnych, 0 dla zera
	 */
	public int sign(double x) {
		if ( x > 0 )
			return 1;
		else if ( x < 0 )
			return -1;
		else
			return 0;
	}
	
	/**
	 * Narysuj punkt.
	 * @param g Interfejs graficzny
	 * @param x Położenie x
	 * @param y Położenie y
	 * @return 1 dla dodatnich, -1 dla ujemnych, 0 dla zera
	 */
	public void drawPoint(
			Graphics g, double x, double y )
	{
		g.drawLine( recountX( transformX(x) ), recountY( transformY(y) ),
				recountX( transformX(x) ), recountY( transformY(y) ) );
	}
	
	/**
	 * Narysuj linię.
	 * @param g Interfejs graficzny
	 * @param x0 Położenie x0 punktu na prostej
	 * @param y0 Położenie y0 punktu na prostej
	 * @param m Współczynnik kierunkowy prostej
	 * @param xs X początkowy rysowanej linii
	 * @param xk X końcowy rysowanej linii
	 */
	public void drawLine(
			Graphics g, double x0, double y0, double m, double xs, double xk )
	{
		if ( m != 0 )
		{
//			System.out.println("x0="+xs+" y0="+((xs-x0)/m+y0)+" x1="+xk+" y1="+((xk-x0)/m+y0));
//			System.out.println("a0="+recountX( transformX(xs) )+
//						" b0="+recountY( transformY((int)((xs-x0)/m+y0)) )+
//						" a1="+recountX( transformX(xk) )+
//						" b1="+recountY(transformY((int)((xk-x0)/m+y0))));
			g.drawLine( recountX( transformX(xs) ),
				recountY( transformY((int)((xs-x0)/m+y0)) ),
				recountX( transformX(xk) ), recountY(transformY((int)((xk-x0)/m+y0))));
		}
		else
		{
//			System.out.println("x0="+xs+" y0="+y0+" x1="+xk+" y1="+y0);
//			System.out.println("a0="+recountX( transformX(xs) )+
//						" b0="+recountY( transformY((int)(y0)) )+
//						" a1="+recountX( transformX(xk) )+
//						" b1="+recountY(transformY((int)(y0))));
			g.drawLine( recountX( transformX(xs) ),
				recountY( transformY((int)(y0)) ),
				recountX( transformX(xk) ), recountY(transformY((int)(y0))));
		}
	}

	public void drawLine(Graphics g,
			double x0, double y0, double x1, double y1)
	{
//			System.out.println("c0="+recountX( transformX(x0) )+
//						" v0="+recountY( transformY((y0)) )+
//						" c1="+recountX( transformX(x1) )+
//						" v1="+recountY(transformY((y1))));
		g.drawLine(
				recountX( transformX(x0) ), recountY( transformY(y0) ),
				recountX( transformX(x1) ), recountY( transformY(y1) )
				);
	}

	/**
	  * Narysuj okrąg.
	  * @param g Interfejs graficzny
	  * @param x Położenie X środka okręgu
	  * @param y Położenie Y środka okręgu
	  * @param r Promień okręgu w jednostkach stosowanych
	  *
	  */
	public void drawCircle(
			Graphics g, double x, double y, double r )
	{
		g.drawOval( recountX( transformX(x)-r ), recountY( transformY(y)-r ),
				recountX( 2*r ), recountY( 2*r ) );
	}

	/**
	  * Narysuj okrąg.
	  * @param g Interfejs graficzny
	  * @param x Położenie X środka okręgu
	  * @param y Położenie Y środka okręgu
	  * @param r Promień okręgu w pikselach
	  *
	  */
	public void drawCircle(
			Graphics g, double x, double y, int r )
	{
		g.drawOval( recountX( transformX(x)-r*getXPixelSize() ),
				recountY( transformY(y)-r*getYPixelSize() ),
				2*r, 2*r );
	}

	public void drawString(
			Graphics g, String s, double x, double y )
	{
		g.drawString( s, recountX( transformX(x) ), recountY( transformY(y) ) );
	}

	/**
		* Narysuj strzałkę.
	  * @param g Interfejs graficzny
		* @param x1 Początek X linii strzałki
		* @param y1 Początek Y linii strzałki
		* @param x2 Koniec X linii strzałki
		* @param y2 Koniec Y linii strzałki
		* @param d Długość końcówki strzałki
		* @param a Kąt nachylenia strzałki w stopniach
		*/
	public void drawArrow(
			Graphics g, double x1, double y1, double x2, double y2, double d, double a )
	{
		double x3, y3; // Punkt w odległości r - przechodzi przezeń prosta tnąca
			// okrąg o promieniu d w punktach zakończeń strzałek
		double r; // Odległość punktu x3, y3 od x2, y2
		double l; // długość prostej
		double lx, ly; // długość prostej po wsółrzędnych x i y ( ale bez abs )
		double dx, dy; // jednostkowy przyrost odległości po x na prostej i po y
		double m12, m3; // współczynniki kierunkowe
		double z, da, db, dc, delta; // zmienne do obliczania równania kwadratowego
		double y41, y42, x41, x42; // punkty wynikowe dla strzałki
		
		g.drawLine(recountX(transformX(x1)), recountY(transformY(y1)),
				recountX(transformX(x2)), recountY(transformY(y2)));
		r = (Math.cos( Math.toRadians( a ) ) * d);
		
		lx = x2 - x1;
		ly = y2 - y1;
		l = Math.sqrt( lx * lx + ly * ly );
		
		dx = Math.abs(lx)/l;
		dy = Math.abs(ly)/l;
		
		x3 = x1 + sign(lx)*( l - r ) * dx;
		y3 = y1 + sign(ly)*( l - r ) * dy;
		
		m12 = lx/ly; // !!! ??? !!! ??? !!! ??? !!! ly/lx !!!!
		m3 = -1.0/m12;
		
		if ( m12 != 0 )
		{
			z = -m3 * y3 + x3 - x2;
			da = m3*m3 + 1;
			db = 2*m3*z - 2*y2;
			dc = -d*d + z*z + y2*y2;
			delta = db*db - 4*da*dc;
			if ( delta < 0 )
				return;
			if ( delta == 0 )
				y41 = y42 = (-db / (2*da));
			else
			{
				y41 = ( (-db - Math.sqrt(delta))/(2*da) );
				y42 = ( (-db + Math.sqrt(delta))/(2*da) );
			}
			x41 = ( m3*(y41-y3)+x3 );
			x42 = ( m3*(y42-y3)+x3 );
		} else { // m3 = Infinity (nieskonczonosc) ( <=> linia pozioma )
			y41 = y42 = y3;
			x41 = x1 + Math.sqrt( d*d - (y2-y3)*(y2-y3) );
			x42 = x1 - Math.sqrt( d*d - (y2-y3)*(y2-y3) );
		}

		System.out.println(
				"x2="+ recountX(transformX(x2))+
				" y2="+ recountY(transformY(y2))+
				" x41="+recountX(transformX(x41))+
				" y41="+recountY(transformY(y41)) );
		System.out.println(
				"x2="+ recountX(transformX(x2))+
				" y2="+ recountY(transformY(y2))+
				" x42="+recountX(transformX(x41))+
				" y42="+recountY(transformY(y41)) );
		g.drawLine( recountX(transformX(x2)), recountY(transformY(y2)),
				recountX(transformX(x41)), recountY(transformY(y41)) );
		g.drawLine( recountX(transformX(x2)), recountY(transformY(y2)),
				recountX(transformX(x42)), recountY(transformY(y42)) );
	}
}
