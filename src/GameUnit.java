import bagel.Input;
import bagel.util.Rectangle;

/**
 * GameUnit Class
 * Implement basic game unit behaviours
 *
 * @YongchunLi
 */
public abstract class GameUnit {
    private int coordinateX;
    private int coordinateY;
    private Rectangle hitBox;

    /**
     * Creates a new GameUnit object with the specified coordinates.
     *
     * @param coordinateX the x-coordinate of the player
     * @param coordinateY the y-coordinate of the player
     */
    public GameUnit(int coordinateX, int coordinateY) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    public int getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(int coordinateX) {
        this.coordinateX = coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(int coordinateY) {
        this.coordinateY = coordinateY;
    }
    /**
     * allow exposure of Hitbox safely
     * allow other objects get this Hitbox
     */
    public Rectangle getHitBox() {
        return hitBox;
    }

    /**
     * direct setter for hitbox
     * @param hitBox receive the existing Rectangle obj for setting.
     */
    public void setHitBox(Rectangle hitBox) {
        this.hitBox = hitBox;
    }

    /**
     * Detailed setter for Hitbox, alternative.
     * @param X Hitbox X
     * @param Y Hitbox Y
     */
    public void setHitBox(int X,int Y) {
        this.hitBox = new Rectangle(X,Y,getImageSize(),getImageSize());
    }




    /**
     * Draws the object based on the given input.
     *
     * @param input the input that determines how the object should be drawn
     */
    public void Draw(Input input) {
    }

    /**
     * Draws the object as a fixed unit, not subject to an input.
     */
    protected void DrawFixUnit() {
    }

    /**
     * check if a GameUnit is within 'NEAR' distance with this GameUnit in both coordinates.
     *
     * @param unit the examined GameUnit.
     * @return true if unit isAround this.
     */
    protected boolean isAround(GameUnit unit) {
        final int definedDistance = ShadowPac.getSTEP_SIZE() + (this.getImageSize() + unit.getImageSize());
        return (Math.abs(unit.coordinateX - this.coordinateX) < definedDistance &&
                Math.abs(unit.coordinateY - this.coordinateY) < definedDistance);
    }


    public abstract int getImageSize();

}
