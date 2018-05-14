package pl.projewski.generator.interfaces;

import java.awt.*;
import java.awt.event.MouseEvent;


/**
 * The interface of presentation data.
 */
public interface ViewDataInterface extends ParameterInterface {

    // zwrócenie obiektu jakiegoś dowolnego typu, jaki może byc utworzony
    // w interfejsie do reprezentacji danych

    /**
     * Get the presentation object.
     *
     * @return the presentation object
     */
    Object getView();

    /**
     * Show the view.
     */
    void showView();

    // jeżeli jest to wygenerowany widok, to po zmianie parametrów może być
    // konieczne jakieś odświeżenie informacji. Niekiedy jest to całkowicie
    // zbędna operacja

    /**
     * Refresh the view. May be used if data changes in meanwhile.
     */
    void refreshView();

    /**
     * Set data for the view.
     *
     * @param data the data
     */
    void setData(NumberInterface data);

    /**
     * Check, that data may be shown by this view.
     *
     * @param dataSource source of data
     */
    boolean canViewData(ParameterInterface dataSource);

    /**
     * Append view data listener.
     *
     * @param listener the listener
     */
    void addViewDataListener(ViewDataListener listener);

    /**
     * Send mouse event to the view.
     *
     * @param frame
     * @param pi
     * @param event
     */
    void sendMouseEventToListeners(Frame frame, ParameterInterface pi, MouseEvent event);

}
