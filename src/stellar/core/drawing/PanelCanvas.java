package stellar.core.drawing;

import stellar.core.GameManager;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class PanelCanvas extends JPanel {

    private GameManager manager;

    public PanelCanvas(GameManager manager, int width, int height) {
        setDoubleBuffered(true);
        setPreferredSize(new Dimension(width, height));
        this.manager = manager;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        // invoke the stack.
        manager.drawWithGraphics((Graphics2D) graphics);
    }
}
