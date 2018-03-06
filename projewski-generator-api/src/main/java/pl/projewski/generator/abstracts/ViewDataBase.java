package pl.projewski.generator.abstracts;

import lombok.extern.slf4j.Slf4j;
import pl.projewski.generator.interfaces.ParameterInterface;
import pl.projewski.generator.interfaces.ViewDataInterface;
import pl.projewski.generator.interfaces.ViewDataListener;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public abstract class ViewDataBase extends AbstractParameter implements ViewDataInterface {
    private final List<ViewDataListener> vecViewDataListeners = new ArrayList<>();

    @Override
    public void addViewDataListener(final ViewDataListener listener) {
        Objects.requireNonNull(listener);
        vecViewDataListeners.add(listener);
    }

    @Override
    public void sendMouseEventToListeners(final Frame frame, final ParameterInterface pi, final MouseEvent event) {
        log.debug("Send mouse event");
        if (this.vecViewDataListeners == null) {
            return;
        }
        for (int i = 0; i < this.vecViewDataListeners.size(); i++) {
            log.debug("Send Mouse Event to " + i);
            ((ViewDataListener) this.vecViewDataListeners.get(i)).onMouseEvent(frame, pi, event);
        }
    }

    @Override
    public boolean canViewData(final ParameterInterface dataSource) {
        return true;
    }

    @Override
    protected String getTypeName() {
        return "viewdata";
    }
}
