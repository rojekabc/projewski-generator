package pk.ie.proj.generproj;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
/* Jest to przejety interfejs nasluchu na zdarzenia z okna. Wlasciwie
   ma on na celu ulatiwc zamykanie okna, gdyz przy zamknieciu bez tegoz 
   interfejsu nie jest wywolywany systemowy powrot z aplikacji, co powoduje
   podtrzymanie jej w pamieci jako proces zombie
*/
public class EventSimpleFrame extends WindowAdapter {
	public void windowClosing(WindowEvent e)
	{
		GenerProj.doCloseActualFrame();
	}
}
