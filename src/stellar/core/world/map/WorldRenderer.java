package stellar.core.world.map;

import com.sun.istack.internal.Nullable;
import stellar.core.entity.Entity;
import stellar.core.location.Location;
import stellar.core.tile.Tile;
import stellar.log.DebugLogger;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class WorldRenderer extends WorldTileMap {

    /**
     * This map represents all tiles you can see on the screen.
     */
    protected final Map<Location, Tile> visibleTileMap = new HashMap<>();

    /**
     * Draw all entities in the world.
     *
     * @param worldEntities the list of world entities.
     * @param graphics      the graphics context
     * @param interpolation whether or not to draw with interpolation.
     */
    public final void drawAllEntities(final List<Entity> worldEntities, Graphics2D graphics, boolean interpolation) {
        worldEntities.forEach(entity -> draw(graphics, entity.getTexture(), entity.getX(), entity.getY(), entity.getWidth(), entity
                .getHeight(), interpolation));
    }

    /**
     * Draw all tiles in the world.
     *
     * @param graphics      the graphics context.
     * @param interpolation whether or not to draw with interpolation.
     */
    public final void drawAllTiles(Graphics2D graphics, boolean interpolation) {
        for (Location location : tileMap.keySet()) {
            Tile tile = tileMap.get(location);
            draw(graphics, tile.getTexture(), location.getX(), location.getY(), tile.getWidth(), tile.getHeight(), interpolation);
        }
    }

    /**
     * Draw all visible tiles.
     *
     * @param graphics      the graphics context.
     * @param interpolation whether or not to draw with interpolation.
     */
    public final void drawAllVisibleTiles(Graphics2D graphics, boolean interpolation) {
        for (Location location : visibleTileMap.keySet()) {
            Tile tile = visibleTileMap.get(location);
            draw(graphics, tile.getTexture(), location.getX(), location.getY(), tile.getWidth(), tile.getHeight(), interpolation);
        }
    }

    /**
     * Update the hashMap with new tiles.
     *
     * @param tileMap the new tileMap.
     */
    public final void updateVisibleTileMap(Map<Location, Tile> tileMap) {
        visibleTileMap.clear();

        for (Location key : tileMap.keySet()) {
            Tile value = tileMap.get(key);
            visibleTileMap.put(key, value);
        }

    }

    /**
     * Add more visible tiles to the set.
     * NOTE: Make sure the tile actually has a location!
     *
     * @param tile the Tile to add.
     */
    public final void addVisibleTile(Tile tile) {
        visibleTileMap.put(tile.getLocation(), tile);
    }

    /**
     * Remove a visible tile from the set.
     * NOTE: Make sure the tile actually has a location!
     *
     * @param tile the Tile to remove.
     */
    public final void removeVisibleTile(Tile tile) {
        visibleTileMap.remove(tile.getLocation(), tile);
    }

    /**
     * Rounds the X and Y to match whatever tile it is on.
     *
     * @param x the X coordinate.
     * @param y the Y coordinate.
     * @return the tile at the X and Y.
     */

    @Nullable
    public final Tile getTileFromVisibleTiles(double x, double y) {
        double roundedX, roundedY;

        // round the X and Y so we check if they are within a tile!
        if (x % tileWidth == 0 && y % tileHeight == 0) {
            // round up
            roundedX = tileWidth * (Math.ceil(Math.abs(x / tileWidth)));
            roundedY = tileHeight * (Math.ceil(Math.abs(y / tileHeight)));
        } else {
            // round down!
            roundedX = tileWidth * (Math.floor(Math.abs(x / tileWidth)));
            roundedY = tileHeight * (Math.floor(Math.abs(y / tileHeight)));
        }

        // Check the tileMap for a location matching this one!
        Location thisLocation = new Location(x, y);
        Location findTileLocation = visibleTileMap.keySet().stream().filter(location -> location.getBoundingBox().contains(x, y)).findAny()
                .orElse(null);
        // none found log this and return null.
        if (findTileLocation == null) {
            DebugLogger.e("Could not find tile at: " + roundedX + "," + roundedY);
            return null;
        }

        Tile tile = visibleTileMap.get(findTileLocation);
        // make sure we set the tiles location incase anything tries to access it.
        tile.setLocation(thisLocation);
        return tile;
    }

    /**
     * Draw an object.
     *
     * @param graphics      the graphics context.
     * @param texture       the texture to draw.
     * @param x             the X coordinate.
     * @param y             the Y coordinate.
     * @param width         the width of the object.
     * @param height        the height of the object.
     * @param interpolation whether or not to draw with interpolation.
     */
    public final void draw(Graphics2D graphics, BufferedImage texture, double x, double y, double width, double height, boolean
            interpolation) {

        AffineTransform proper = graphics.getTransform();
        // set hint and translate/scale to the entities bounds.
        if (interpolation) {
            graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        }
        graphics.translate(x, y);
        graphics.scale(width / texture.getWidth(), height / texture.getHeight());

        //draw
        graphics.drawImage(texture, 0, 0, null);
        graphics.setTransform(proper);
    }

}
