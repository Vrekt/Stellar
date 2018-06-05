package stellar.core.world;

import com.sun.istack.internal.Nullable;
import stellar.core.entity.Entity;
import stellar.core.world.entity.ActionEntity;
import stellar.core.world.map.WorldRenderer;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public abstract class World extends WorldRenderer {
    protected final List<ActionEntity> actionList = new ArrayList<>();
    protected final List<Entity> worldEntities = new ArrayList<>();

    protected String worldName;
    protected int width, height, tileWidth, tileHeight;

    /**
     * Initialize a new world.
     *
     * @param worldName the name of this world.
     * @param width     the width of the world.
     * @param height    the height of the world.
     */
    public World(String worldName, int width, int height) {
        this.worldName = worldName;
        this.width = width;
        this.height = height;
    }

    /**
     * Initialize a new world.
     *
     * @param worldName  the name of this world.
     * @param width      the width of the world.
     * @param height     the height of the world.
     * @param tileWidth  the width of the tiles that are in the world.
     * @param tileHeight the height of the tiles that are in the world.
     */
    public World(String worldName, int width, int height, int tileWidth, int tileHeight) {
        this(worldName, width, height);
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }

    /**
     * @return the name of this world.
     */
    public final String getWorldName() {
        return worldName;
    }

    /**
     * Add an entity to the world.
     * Using this method is unsafe and could cause concurrency exceptions.
     *
     * @param entity the entity to add.
     */
    public final void addEntity(Entity entity) {
        worldEntities.add(entity);
    }

    /**
     * Remove an entity from this world.
     * Using this method is unsafe and could cause concurrency exceptions.
     *
     * @param entity the entity to remove.
     */
    public final void removeEntity(Entity entity) {
        worldEntities.remove(entity);
    }

    /**
     * Queue an entity to be added.
     *
     * @param entity the entity to be added.
     */
    public final void queueEntityForAdd(Entity entity) {
        ActionEntity actionEntity = new ActionEntity(entity, ActionEntity.EntityAction.ADD);
        actionList.add(actionEntity);
    }

    /**
     * Queue an entity to be removed.
     *
     * @param entity the entity to be removed.
     */
    public final void queueEntityForRemoval(Entity entity) {
        ActionEntity actionEntity = new ActionEntity(entity, ActionEntity.EntityAction.REMOVE);
        actionList.add(actionEntity);
    }

    public final void doQueueActions() {
        // for entities that need to be added/removed, we call this before any drawing/updating methods.
        for (ActionEntity entity : actionList) {
            switch (entity.getAction()) {
                case ADD:
                    worldEntities.add(entity.getEntity());
                    break;
                case REMOVE:
                    worldEntities.remove(entity.getEntity());
                    break;
            }
        }
        // clear the entity action list.
        actionList.clear();
    }

    /**
     * @param entityID the ID of the entity.
     * @return an entity that has the matching ID.
     */
    @Nullable
    public final Entity getEntityFromID(int entityID) {
        return worldEntities.stream().filter(entity -> entity.getEntityID() == entityID).findAny().orElse(null);
    }

    /**
     * @param x the X coordinate to check.
     * @param y the Y coordinate to check.
     * @return true if the entity is at the exact coordinates.
     */
    public final boolean isAnyEntityAt(double x, double y) {
        return worldEntities.stream().anyMatch(entity -> entity.isAt(x, y));
    }

    /**
     * @param x the X coordinate to check.
     * @param y the Y coordinate to check.
     * @return the entity at the exact coordinates.
     */
    @Nullable
    public final Entity getEntityAt(double x, double y) {
        return worldEntities.stream().filter(entity -> entity.isAt(x, y)).findAny().orElse(null);
    }

    /**
     * Gets executed when the world is drawn.
     *
     * @param graphics the graphics context.
     */
    public abstract void onDraw(Graphics2D graphics);

    /**
     * Gets executed when the world updates.
     */
    public void onTick() {
        doQueueActions();
    }

}
