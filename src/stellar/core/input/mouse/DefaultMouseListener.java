package stellar.core.input.mouse;

import com.sun.istack.internal.Nullable;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DefaultMouseListener implements MouseListener {

    private Point lastClick;
    private boolean isMouseDown;

    private Component enteredComponent;

    /**
     * @return the last point where the user clicked.
     */
    public Point getLastClick() {
        return lastClick;
    }

    /**
     * @return if the mouse is being held down.
     */
    public boolean isMouseDown() {
        return isMouseDown;
    }

    /**
     * @return the component the mouse entered.
     */
    public @Nullable
    Component getEnteredComponent() {
        return enteredComponent;
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        lastClick = event.getPoint();
    }

    @Override
    public void mouseEntered(MouseEvent event) {
        enteredComponent = event.getComponent();
    }

    @Override
    public void mouseExited(MouseEvent event) {
        // invalidate the component.
        enteredComponent = null;
    }

    @Override
    public void mousePressed(MouseEvent event) {
        isMouseDown = true;
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        isMouseDown = false;
    }

}
