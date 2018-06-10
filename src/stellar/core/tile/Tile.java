package stellar.core.tile;

import stellar.core.collision.BoundingBox2D;
import stellar.core.location.Location;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Tile {

    private BufferedImage texture;
    private TileProperties tileProperties;
    private Location location;
    private double x, y, width, height;
    private int uniqueID;

    /**
     * @param texture  the texture of this tile.
     * @param uniqueID the uniqueID representing this tile.
     */
    public Tile(BufferedImage texture, int uniqueID) {
        this.texture = texture;
        this.uniqueID = uniqueID;
    }

    /**
     * @param texture  the texture of this tile.
     * @param uniqueID the uniqueID representing this tile.
     * @param width    the width of this tile.
     * @param height   the height of this tile.
     */
    public Tile(BufferedImage texture, int uniqueID, double width, double height) {
        this(texture, uniqueID);

        this.width = width;
        this.height = height;

    }

    /**
     * @param texture    the texture of this tile.
     * @param uniqueID   the uniqueID representing this tile.
     * @param width      the width of this tile.
     * @param height     the height of this tile.
     * @param properties the properties of this tile.
     */
    public Tile(BufferedImage texture, int uniqueID, double width, double height, TileProperties properties) {
        this(texture, uniqueID, width, height);

        tileProperties = properties;
    }

    /**
     * @param texture  the texture of this tile.
     * @param uniqueID the uniqueID representing this tile.
     * @param x        the X origin of this tile.
     * @param y        the Y origin of this tile.
     * @param width    the width of this tile.
     * @param height   the height of this tile.
     */
    public Tile(BufferedImage texture, int uniqueID, double x, double y, double width, double height) {
        this(texture, uniqueID, width, height);

        this.x = x;
        this.y = y;
    }

    /**
     * @param texture    the texture of this tile.
     * @param uniqueID   the uniqueID representing this tile.
     * @param x          the X origin of this tile.
     * @param y          the Y origin of this tile.
     * @param width      the width of this tile.
     * @param height     the height of this tile.
     * @param properties the properties of this tile.
     */
    public Tile(BufferedImage texture, int uniqueID, double x, double y, double width, double height, TileProperties properties) {
        this(texture, uniqueID, x, y, width, height);
        tileProperties = properties;
    }

    /**
     * @return the tiles texture.
     */
    public BufferedImage getTexture() {
        return texture;
    }

    /**
     * @return the tiles width.
     */
    public double getWidth() {
        return width;
    }

    /**
     * @return the tiles height.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Set this tiles width.
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * Set this tiles height.
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * @return the tiles X coordinate.
     */
    public double getX() {
        return x;
    }

    /**
     * Set the X.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * @return the tiles Y coordinate.
     */
    public double getY() {
        return y;
    }

    /**
     * Set the Y.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * @return this tiles location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Set the tiles location.
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * @return the tiles unique ID.
     */
    public int getUniqueID() {
        return uniqueID;
    }

    /**
     * @return if this tile is solid or not.
     */
    public boolean isSolid() {
        return tileProperties.isSolid();
    }

    /**
     * Set if the tile is solid or not.
     */
    public void setSolid(boolean isSolid) {
        tileProperties.setSolid(isSolid);
    }

    /**
     * @return if the tile is visible or not.
     */
    public boolean isVisible() {
        return tileProperties.isVisible();
    }

    /**
     * Set if the tile is visible or not.
     */
    public void setVisible(boolean isVisible) {
        tileProperties.setVisible(isVisible);
    }

    /**
     * @return if this tile is passable or not.
     */
    public boolean isPassable() {
        return tileProperties.isPassable();
    }

    /**
     * Set if this tile is passable or not.
     */
    public void setPassable(boolean isPassable) {
        tileProperties.setPassable(isPassable);
    }

    /**
     * @return the properties of this tile.
     */
    public TileProperties getTileProperties() {
        return tileProperties;
    }

    /**
     * Set the properties of this tile.
     */
    public void setProperties(TileProperties properties) {
        tileProperties = properties;
    }

    /**
     * Draws a tile with the coordinates as doubles.
     *
     * @param graphics the graphics object to draw with.
     */
    public void drawTileDouble(Graphics2D graphics) {
        AffineTransform proper = graphics.getTransform();

        graphics.translate(x, y);
        graphics.scale(width / texture.getWidth(), height / texture.getHeight());

        graphics.drawImage(texture, 0, 0, null);
        graphics.setTransform(proper);
    }

    /**
     * Draw the tile.
     *
     * @param graphics the graphics object to draw with.
     */
    public void drawTileInt(Graphics2D graphics) {
        graphics.drawImage(texture, (int) x, (int) y, null);
    }

    /**
     * Draws the tile with interpolation to produce a better quality image.
     *
     * @param graphics the graphics object to draw with.
     */
    public void drawTileDoubleWithInterpolation(Graphics2D graphics) {
        AffineTransform proper = graphics.getTransform();

        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics.translate(x, y);
        graphics.scale(width / texture.getWidth(), height / texture.getHeight());

        graphics.drawImage(texture, 0, 0, null);
        graphics.setTransform(proper);
    }

    /**
     * @return a new BoundingBox that represents this tile.
     */
    public BoundingBox2D createBounds() {
        return new BoundingBox2D(x, y, width, height);
    }

    /**
     * @param x      the X origin of the BoundingBox.
     * @param y      the Y origin of the BoundingBox.
     * @param width  the width of the BoundingBox.
     * @param height the height of the BoundingBox.
     * @return a new BoundingBox created from the bounds specified.
     */
    public BoundingBox2D createBounds(double x, double y, int width, int height) {
        return new BoundingBox2D(x, y, width, height);
    }


    public class TileProperties {
        private boolean isVisible = true, isSolid = false, isPassable = true;

        /**
         * @param isVisible if the tile is visible or not.
         */
        public TileProperties(boolean isVisible) {
            this.isVisible = isVisible;
        }

        /**
         * @param isVisible if the tile is visible or not.
         * @param isSolid   if the tile is solid or not.
         */
        public TileProperties(boolean isVisible, boolean isSolid) {
            this(isVisible);
            this.isSolid = isSolid;
        }

        /**
         * @param isVisible  if the tile is visible or not.
         * @param isSolid    if the tile is solid or not.
         * @param isPassable if the tile can be passed through.
         */
        public TileProperties(boolean isVisible, boolean isSolid, boolean isPassable) {
            this(isVisible, isSolid);
            this.isPassable = isPassable;
        }

        /**
         * @return if the tile is visible or not.
         */
        public boolean isVisible() {
            return isVisible;
        }

        /**
         * @return if the tile is solid.
         */
        public boolean isSolid() {
            return isSolid;
        }

        /**
         * @return if the tile can be passed through.
         */
        public boolean isPassable() {
            return isPassable;
        }

        /**
         * Set if this tile is visible or not.
         *
         * @param visible whether the tile is visible or not.
         */
        public void setVisible(boolean visible) {
            isVisible = visible;
        }

        /**
         * Set if this tile is solid or not.
         *
         * @param solid whether the tile is solid or not.
         */
        public void setSolid(boolean solid) {
            isSolid = solid;
        }

        /**
         * Set if this tile is passable or not.
         *
         * @param passable whether the tile is passable or not.
         */
        public void setPassable(boolean passable) {
            isPassable = passable;
        }
    }

}
