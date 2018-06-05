package stellar.core.sprite;

import com.sun.istack.internal.Nullable;
import stellar.core.tile.Tile;
import stellar.log.DebugLogger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpriteManager {

    /**
     * An enum to determine which direction to take.
     */
    public enum Directional {
        UP, DOWN, LEFT, RIGHT
    }

    private final Map<String, BufferedImage> bufferedImageMap = new HashMap<>();

    /**
     * @param path the path of the resource.
     * @return an image created from the file.
     */
    @Nullable
    public BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException exception) {
            DebugLogger.e("Could not load resource: " + path + "!");
        }
        return null;
    }

    /**
     * @param image  the image
     * @param x      the origin X.
     * @param y      the origin Y.
     * @param width  the width of the section.
     * @param height the height of the section.
     * @return the sub-image of the image provided.
     */
    public BufferedImage getSectionAt(BufferedImage image, int x, int y, int width, int height) {
        return image.getSubimage(x, y, width, height);
    }

    /**
     * Add an image.
     *
     * @param name  the name of the image.
     * @param image the image.
     */
    public void addImage(String name, BufferedImage image) {
        bufferedImageMap.put(name, image);
    }

    /**
     * Remove an image from the map. This will notify the console if we cannot remove the element.
     *
     * @param name the name of the image.
     */
    public void removeImage(String name) {
        if (bufferedImageMap.containsKey(name)) {
            bufferedImageMap.remove(name);
            return;
        }

        DebugLogger.w("[SpriteManager] Could not remove image: " + name);
    }

    /**
     * @param name the name of the image
     * @return the image that corresponds with the name, this can be null if the element wasn't found.
     */
    @Nullable
    public BufferedImage getImage(String name) {
        String key = bufferedImageMap.keySet().stream().filter(n -> n.equals(name)).findAny().orElse(null);
        if (key == null) {
            // element not found!
            DebugLogger.w("[SpriteManager] Could not get image: " + name);
            return null;
        }
        return bufferedImageMap.get(key);
    }

    /**
     * Get multiple images from an image.
     *
     * @param x           the origin X coordinate.
     * @param y           the origin Y coordinate.
     * @param xModifier   the offset to add/subtract since spacing between sprites can vary.
     * @param yModifier   the offset to add/subtract since spacing between sprites can vary.
     * @param width       the width of the image.
     * @param height      the height of the image.
     * @param direction   the direction in which to continue.
     * @param totalImages the amount of images to get.
     * @return an array of sprites.
     */
    public BufferedImage[] getImages(BufferedImage image, int x, int y, int xModifier, int yModifier, int width, int height,
                                             Directional direction, int totalImages) {

        // create an array of images that we are going to be getting.
        BufferedImage[] frames = new BufferedImage[totalImages];
        int newWidth = width + xModifier, newHeight = height + yModifier;
        for (int i = 0; i < totalImages; i++) {
            frames[i] = getSectionAt(image, x, y, width, height);

            // change the X and Y based on direction.
            x = direction == Directional.RIGHT ? x + newWidth : direction == Directional.LEFT ? x - newWidth : x;
            y = direction == Directional.DOWN ? y + newHeight : direction == Directional.UP ? y - newHeight : y;
        }
        return frames;
    }

    /**
     * Get images and then create a Tile for each of them.
     *
     * @param image       the image to use.
     * @param x           the origin X coordinate.
     * @param y           the origin Y coordinate.
     * @param xModifier   the offset to add/subtract since spacing between sprites can vary.
     * @param yModifier   the offset to add/subtract since spacing between sprites can vary.
     * @param width       the width of the image.
     * @param height      the height of the image.
     * @param direction   the direction in which to continue.
     * @param totalImages the amount of images to get.
     * @param properties  an array of properties to apply to the tiles, a size greater or equal to 1 is required.
     * @param tileIds     an array of tileIds to use when creating the tiles, this can be null (ids will be created).
     * @return an array of tiles created from images.
     */
    public Tile[] createTilesFromImages(BufferedImage image, int x, int y, int xModifier, int yModifier, int width, int height,
                                                Directional direction, int totalImages, Tile.TileProperties[] properties, int[] tileIds) {
        // get the images and initialize the tileId set if needed.
        BufferedImage[] images = getImages(image, x, y, xModifier, yModifier, width, height, direction, totalImages);
        if (tileIds == null) {
            tileIds = new int[totalImages];
        }

        List<Tile> tiles = new ArrayList<>();
        // handle the size of properties.
        int len = properties.length;
        for (int i = 0; i < totalImages; i++) {
            // loop through all the images we got.
            BufferedImage img = images[i];
            // create the tile and add it.
            Tile tile = new Tile(img, tileIds[i], width, height, len == 1 ? properties[0] : properties[i]);
            tiles.add(tile);
        }
        return tiles.toArray(new Tile[0]);
    }

}
