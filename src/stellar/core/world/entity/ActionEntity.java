package stellar.core.world.entity;

import stellar.core.entity.Entity;

public class ActionEntity {

    public enum EntityAction {
        ADD, REMOVE
    }

    private Entity entity;
    private EntityAction action;

    public ActionEntity(Entity entity, EntityAction action) {
        this.entity = entity;
        this.action = action;
    }

    /**
     * @return this entity.
     */
    public Entity getEntity() {
        return entity;
    }

    /**
     * @return the operation to carry out with this entity.
     */
    public EntityAction getAction() {
        return action;
    }
}
