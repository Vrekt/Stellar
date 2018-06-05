package stellar.core.world.map;

import com.sun.istack.internal.Nullable;
import stellar.core.collision.BoundingBox2D;
import stellar.core.location.Location;
import stellar.core.tile.Tile;
import stellar.core.utilities.BasicTimer;
import stellar.log.DebugLogger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class WorldTileMap {

    private final BasicTimer timer = new BasicTimer();

    // TODO: Later make this a global enum and change it in SpriteManager.
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    protected final Map<Location, Tile> tileMap = new HashMap<>();
    protected double tileWidth, tileHeight;

    /**
     * Add a tile. The tiles bounding box is created in this method.
     *
     * @param x    the x
     * @param y    the y
     * @param tile the tile.
     */
    public final void setTile(double x, double y, Tile tile) {
        tileWidth = tile.getWidth();
        tileHeight = tile.getHeight();

        tile.setX(x);
        tile.setY(y);

        BoundingBox2D box = new BoundingBox2D(x, y, tileWidth, tileHeight);
        tileMap.put(new Location(x, y, box), tile);
    }

    /**
     * Add a tile.
     *
     * @param tile the tile.
     */
    public final void setTile(Tile tile) {
        setTile(tile.getX(), tile.getY(), tile);
    }

    /**
     * Add multiple tiles in one direction.
     *
     * @param tile              the tile to use.
     * @param startX            the starting X coordinate.
     * @param startY            the starting Y coordinate.
     * @param direction         the direction in which to add tiles.
     * @param amount            the amount of tiles to add.
     * @param threadedOperation whether you want this operation to be threaded.
     */
    public final void setTiles(Tile tile, double startX, double startY, Direction direction, int amount, boolean
            threadedOperation) {
        if (threadedOperation) {
            // create the ExecutorService.
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                // maybe later check the result or progress of the thread.
                setTiles(tile, startX, startY, direction, amount);
            });
            //shutdown the service
            executor.shutdown();
        } else {
            setTiles(tile, startX, startY, direction, amount);
        }
    }

    /**
     * Add multiple tiles in one direction.
     *
     * @param tile      the tile to use.
     * @param startX    the starting X coordinate.
     * @param startY    the starting Y coordinate.
     * @param direction the direction in which to add tiles.
     * @param amount    the amount of tiles to add.
     */
    private void setTiles(Tile tile, double startX, double startY, Direction direction, int amount) {

        // see how long it takes for the operation to finish.
        timer.start();

        // width and height.
        double width = tile.getWidth();
        double height = tile.getHeight();
        for (int i = 0; i < amount; i++) {
            // set the tile properties and add it to the tile map
            tile.setX(startX);
            tile.setY(startY);
            setTile(tile);
            // update the X and Y
            startX = direction == Direction.RIGHT ? startX + width : direction == Direction.LEFT ? startX - width : startX;
            startY = direction == Direction.DOWN ? startY + height : direction == Direction.UP ? startY - height : startY;

        }

        // log the time it took to finish.
        DebugLogger.i("Finished adding " + amount + " tiles, took: " + timer.stop() + "ms");
    }

    /**
     * Remove a tile at the X and Y coordinate. This method rounds and will find the tile the coordinates are in.
     *
     * @param x the X coordinate.
     * @param y the Y coordinate.
     */
    public final void removeTile(double x, double y) {

        // TODO: Concurrency exceptions?

        Tile tile = getTileFromAllTiles(x, y);
        if (tile == null) {
            return;
        }
        //remove the tile.
        tileMap.remove(tile.getLocation(), tile);
    }

    /**
     * Remove multiple tiles in one direction.
     *
     * @param tile              the tile to use.
     * @param startX            the starting X coordinate.
     * @param startY            the starting Y coordinate.
     * @param direction         the direction in which to add tiles.
     * @param amount            the amount of tiles to add.
     * @param threadedOperation whether you want this operation to be threaded.
     */
    public final void removeTiles(Tile tile, int startX, int startY, Direction direction, int amount, boolean threadedOperation) {
        if (threadedOperation) {
            // create the ExecutorService.
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                // maybe later check the result or progress of the thread.
                removeTiles(tile, startX, startY, direction, amount);
            });
            //shutdown the service
            executor.shutdown();
        } else {
            removeTiles(tile, startX, startY, direction, amount);
        }
    }

    /**
     * Remove multiple tiles in one direction.
     *
     * @param tile      the tile to use.
     * @param startX    the starting X coordinate.
     * @param startY    the starting Y coordinate.
     * @param direction the direction in which to add tiles.
     * @param amount    the amount of tiles to add.
     */
    private void removeTiles(Tile tile, double startX, double startY, Direction direction, int amount) {
        double width = tile.getWidth();
        double height = tile.getHeight();

        // measure how long this operation takes.
        timer.start();

        for (int i = 0; i < amount; i++) {
            //remove the tile.
            removeTile(startX, startY);

            // update the X and Y.
            startX = direction == Direction.RIGHT ? startX + width : direction == Direction.LEFT ? startX - width : startX;
            startY = direction == Direction.DOWN ? startY + height : direction == Direction.UP ? startY - height : startY;

        }

        DebugLogger.i("Finished removing " + amount + " tiles, took: " + timer.stop() + "ms");
    }

    /**
     * Rounds the X and Y to match whatever tile it is on.
     *
     * @param x the X coordinate.
     * @param y the Y coordinate.
     * @return the tile at the X and Y.
     */

    @Nullable
    public final Tile getTileFromAllTiles(double x, double y) {

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
        Location findTileLocation = tileMap.keySet().stream().filter(location -> location.getBoundingBox().contains(x, y)).findAny().orElse(null);
        // none found log this and return null.
        if (findTileLocation == null) {
            DebugLogger.e("Could not find tile at: " + roundedX + "," + roundedY);
            return null;
        }

        Tile tile = tileMap.get(findTileLocation);
        // make sure we set the tiles location incase anything tries to access it.
        tile.setLocation(thisLocation);
        return tile;
    }

}
