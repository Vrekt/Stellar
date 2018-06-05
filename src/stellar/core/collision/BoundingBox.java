package stellar.core.collision;

import java.awt.Rectangle;

public class BoundingBox {

    /**
     * The x origin of this BoundingBox.
     */
    private int x;

    /**
     * The y origin of this BoundingBox.
     */
    private int y;

    /**
     * The width of this BoundingBox.
     */
    private int width;

    /**
     * The height of this BoundingBox.
     */
    private int height;

    /**
     * The bounds of this BoundingBox.
     */
    private Rectangle bounds;

    /**
     * Initialize the BoundingBox.
     *
     * @param x      The x origin to initialize this BoundingBox with.
     * @param y      The y origin to initialize this BoundingBox with.
     * @param width  The width to initialize this BoundingBox with.
     * @param height The height to initialize this BoundingBox with.
     */
    public BoundingBox(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.bounds = new Rectangle(x, y, width, height);
    }

    /**
     * Set the values of this BoundingBox.
     *
     * @param x      The x origin to set.
     * @param y      The y origin to set.
     * @param width  The width to set.
     * @param height The height to set.
     */
    public void set(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        bounds.setBounds(x, y, width, height);
    }

    /**
     * Return if the current BoundingBox intersects with another.
     *
     * @param box The other BoundingBox to check against.
     * @return True if the passed in BoundingBox intersects with this one,
     * otherwise false.
     */
    public boolean doesIntersect(BoundingBox box) {
        return bounds.intersects(box.getBounds());
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }
}
