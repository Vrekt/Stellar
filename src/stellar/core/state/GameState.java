package stellar.core.state;

import java.awt.Graphics2D;

public interface GameState {

    void onTick();

    void onDraw(Graphics2D graphics);

}
