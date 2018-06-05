package stellar.core.location;

import stellar.core.collision.BoundingBox2D;

public class Location {

    private double x, y;
    private boolean onGround;

    private BoundingBox2D boundingBox;

    public Location(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Location(double x, double y, boolean onGround) {
        this(x, y);
        this.onGround = onGround;
    }

    public Location(double x, double y, BoundingBox2D boundingBox) {
        this(x, y);
        this.boundingBox = boundingBox;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    /**
     * Set the boundingBox.
     */
    public void setBoundingBox(BoundingBox2D boundingBox) {
        this.boundingBox = boundingBox;
    }

    /**
     * @return the boundingBox that surrounds this tile.
     */
    public BoundingBox2D getBoundingBox() {
        return boundingBox;
    }

    /**
     * Update the boundingBox with the X and Y.
     */
    public void updateBoundingBox(double width, double height) {
        if (boundingBox == null) {
            boundingBox = new BoundingBox2D(x, y, width, height);
        }

        boundingBox.set(x, y, width, height);
    }

    /**
     * Two locations are considered equual if their x's and y's are the same.
     * Adopted from Lunar (github.com/Vrekt/Lunar). Special thanks to rickbau5.
     *
     * @param obj the other object
     * @return true/false whether the objects are referentially or logically equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        if (obj instanceof Location) {
            Location other = (Location) obj;
            return this.x == other.x && this.y == other.y;
        }
        return super.equals(obj);
    }

    /**
     * // The hash code is calculated by combining the fields checked by the equals function
     * // into an int.
     * // <p>
     * // Implementation taken from this thread on StackOverflow:
     * // - https://stackoverflow.com/a/113600
     * <p>
     * Modified since we use doubles for X and Y values now.
     *
     * @return the object's hash code
     */
    @Override
    public int hashCode() {
        int result = 11;
        result = 31 * new Double(this.x).hashCode() + result;
        result = 31 * new Double(this.y).hashCode() + result;
        return result;
    }

}
