package stellar.core.entity;

import stellar.core.collision.BoundingBox2D;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class Entity {

    protected double x, y;
    protected double width, height;
    protected int entityID;

    protected BoundingBox2D box2D;
    protected BufferedImage texture;

    /**
     * Initialize this entity; this is the primary constructor.
     *
     * @param x        The x position of this entity.
     * @param y        The y position of this entity.
     * @param width    The width of this entity.
     * @param height   The height of this entity.
     * @param entityID a number that uniquely identifies this entity.
     */
    public Entity(double x, double y, double width, double height, int entityID) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.entityID = entityID;

        box2D = new BoundingBox2D(x, y, width, height);

    }

    /**
     * Convenience constructor that allows you to
     * specify a BoundingBox to use with this entity.
     *
     * @param x        The x position of this entity.
     * @param y        The y position of this entity.
     * @param width    The width of this entity.
     * @param height   The height of this entity.
     * @param entityID a number that uniquely identifies this entity.
     * @param bb       The collision box for this entity.
     */
    public Entity(double x, double y, double width, double height, int entityID, BoundingBox2D bb) {
        this(x, y, width, height, entityID);
        this.box2D = bb;
    }

    /**
     * Convenience constructor that allows you to provide a
     * BufferedImage to use as a texture for this entity.
     *
     * @param texture  The image to use as a texture for this entity.
     * @param x        The x position of this entity.
     * @param y        The y position of this entity.
     * @param width    The width of this entity.
     * @param height   The height of this entity.
     * @param entityID a number that uniquely identifies this entity.
     */
    public Entity(BufferedImage texture, double x, double y, double width, double height, int entityID) {
        this(x, y, width, height, entityID);

        this.texture = texture;
    }

    /**
     * Convenience constructor that allows you to supply both a BufferedImage
     * and a BoundingBox.
     *
     * @param texture  The image to use as a texture for this entity.
     * @param x        The x position of this entity.
     * @param y        The y position of this entity.
     * @param width    The width of this entity.
     * @param height   The height of this entity.
     * @param entityID a number that uniquely identifies this entity.
     * @param bb       The collision box for this entity.
     */
    public Entity(BufferedImage texture, double x, double y, double width, double height, int entityID, BoundingBox2D bb) {
        this(x, y, width, height, entityID, bb);

        this.texture = texture;

    }

    /**
     * Draw the entity.
     *
     * @param graphics the graphics object that is passed.
     */
    public abstract void drawEntity(Graphics graphics);

    /**
     * Update the entity.
     */
    public abstract void updateEntity();

    /**
     * Update the entity boundingBox, this should be done when position is updated.
     */
    public void updateBoundingBox() {
        box2D.set(x, y, width, height);
    }

    /**
     * Get the X
     */
    public double getX() {
        return x;
    }

    /**
     * Get the Y
     */
    public double getY() {
        return y;
    }

    /**
     * Set x
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Set y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Get the width of the entity.
     */
    public double getWidth() {
        return width;
    }

    /**
     * Get the height of the entity.
     */
    public double getHeight() {
        return height;
    }

    /**
     * @param x the X coordinate to check.
     * @param y the Y coordinate to check.
     * @return true if the entity is at the exact coordinates.
     */
    public boolean isAt(double x, double y) {
        return this.x == x && this.y == y;
    }

    /**
     * @return this entities unique identifier.
     */
    public int getEntityID() {
        return entityID;
    }

    /**
     * @return the boundingbox for this entity.
     */
    public BoundingBox2D getBox2D() {
        return box2D;
    }

    /**
     * @return the texture for this entity.
     */
    public BufferedImage getTexture() {
        return texture;
    }

    /**
     * Set the texture for this entity.
     *
     * @param texture the texture for this entity.
     */
    public void setTexture(BufferedImage texture) {
        this.texture = texture;
    }
}
