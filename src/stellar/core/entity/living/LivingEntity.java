package stellar.core.entity.living;

import stellar.core.collision.BoundingBox2D;
import stellar.core.entity.Entity;

import java.awt.image.BufferedImage;

public abstract class LivingEntity extends Entity {

    protected float health;
    protected double movementSpeed;

    /**
     * Initialize this entity.
     *
     * @param x             The x position of this entity.
     * @param y             The y position of this entity.
     * @param width         The width of this entity.
     * @param height        The height of this entity.
     * @param entityID      a number that uniquely identifies this entity.
     * @param health        the health of this entity.
     * @param movementSpeed the speed at which this entity moves.
     */
    public LivingEntity(double x, double y, double width, double height, int entityID, float health, double movementSpeed) {
        super(x, y, width, height, entityID);

        this.health = health;
        this.movementSpeed = movementSpeed;
    }

    /**
     * Initialize this entity.
     *
     * @param texture       The image to use as a texture for this entity.
     * @param x             The x position of this entity.
     * @param y             The y position of this entity.
     * @param width         The width of this entity.
     * @param height        The height of this entity.
     * @param entityID      a number that uniquely identifies this entity.
     * @param health        the health of this entity.
     * @param movementSpeed the speed at which this entity moves.
     */
    public LivingEntity(BufferedImage texture, double x, double y, double width, double height, int entityID, float health, double movementSpeed) {
        super(texture, x, y, width, height, entityID);

        this.health = health;
        this.movementSpeed = movementSpeed;
    }

    /**
     * Initialize this entity.
     *
     * @param x             The x position of this entity.
     * @param y             The y position of this entity.
     * @param width         The width of this entity.
     * @param height        The height of this entity.
     * @param entityID      a number that uniquely identifies this entity.
     * @param bb            The collision box for this entity.
     * @param health        the health of this entity.
     * @param movementSpeed the speed at which this entity moves.
     */
    public LivingEntity(double x, double y, double width, double height, int entityID, BoundingBox2D bb, float health, double movementSpeed) {
        super(x, y, width, height, entityID, bb);
        this.health = health;
        this.movementSpeed = movementSpeed;
    }

    /**
     * Initialize this entity.
     *
     * @param texture       The image to use as a texture for this entity.
     * @param x             The x position of this entity.
     * @param y             The y position of this entity.
     * @param width         The width of this entity.
     * @param height        The height of this entity.
     * @param entityID      a number that uniquely identifies this entity.
     * @param bb            The collision box for this entity.
     * @param health        the health of this entity.
     * @param movementSpeed the speed at which this entity moves.
     */
    public LivingEntity(BufferedImage texture, double x, double y, double width, double height, int entityID, BoundingBox2D bb, float health, double movementSpeed) {
        super(texture, x, y, width, height, entityID, bb);
        this.health = health;
        this.movementSpeed = movementSpeed;
    }

    /**
     * @return the health of this entity.
     */
    public float getHealth() {
        return health;
    }


    /**
     * Set the health of this entity.
     *
     * @param health the health value.
     */
    public void setHealth(float health) {
        this.health = health;
    }

    /**
     * @return if this entities health if less than or equal to 0.
     */
    public boolean isDead() {
        return health <= 0;
    }

    /**
     * Damage the entity. This method will not deal damage that ticks their health into the negative values.
     *
     * @param amount the amount of damage to deal.
     */
    public void damageEntity(float amount) {
        health = health - amount < 0 ? 0 : health - amount;
    }

    /**
     * @return the movement speed of this entity.
     */
    public double getMovementSpeed() {
        return movementSpeed;
    }

    /**
     * Set the movement speed of this entity.
     *
     * @param movementSpeed the speed value.
     */
    public void setMovementSpeed(double movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    /**
     * Scale the movement speed.
     *
     * @param amount the amount to scale by.
     */
    public void scaleMovementSpeed(double amount) {
        movementSpeed *= amount;
    }

}
