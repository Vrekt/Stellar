package stellar.core.collision;

import stellar.core.location.Location;

import java.awt.geom.Rectangle2D;

public class BoundingBox2D {

    private Location location;
    private double x, y, width, height;
    private Rectangle2D rect2D;

    public BoundingBox2D(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;

        this.width = width;
        this.height = height;
        rect2D = new Rectangle2D.Double(x, y, width, height);
    }

    /**
     * Update this BoundingBox.
     *
     * @param x the origin X.
     * @param y the origin Y.
     */
    public void set(double x, double y) {
        this.x = x;
        this.y = y;

        rect2D = new Rectangle2D.Double(x, y, width, height);
    }

    /**
     * Update this BoundingBox.
     *
     * @param x      the origin X.
     * @param y      the origin Y.
     * @param width  the width of this BoundingBox.
     * @param height the height of this BoundingBox.
     */
    public void set(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;

        this.width = width;
        this.height = height;
        rect2D = new Rectangle2D.Double(x, y, width, height);
    }

    /**
     * Return if the current BoundingBox intersects with another.
     *
     * @param box The other BoundingBox to check against.
     * @return True if the passed in BoundingBox intersects with this one,
     * otherwise false.
     */
    public boolean doesIntersect(BoundingBox2D box) {
        return rect2D.intersects(box.getRect2D());
    }

    /**
     * @param x the X coordinate.
     * @param y the Y coordinate.
     * @return true if the passed coordinates are within the bounds.
     */
    public boolean contains(double x, double y) {
        return rect2D.contains(x, y);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Rectangle2D getRect2D() {
        return rect2D;
    }

    public void setRect2D(Rectangle2D rect2D) {
        this.rect2D = rect2D;
    }
}
