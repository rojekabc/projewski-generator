/**
 * 
 */
package pl.projewski.generator.interfaces;

import java.awt.Frame;
import java.awt.event.MouseEvent;


/**
 * @author projewski
 *
 */
public interface ViewDataListener {
	public void onMouseEvent(Frame frame, ParameterInterface pi, MouseEvent me);
}
