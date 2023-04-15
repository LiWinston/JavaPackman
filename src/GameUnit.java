import bagel.*;
import bagel.util.Rectangle;

/**
 * GameUnit Class
 * Implement basic game unit behaviours
 * @YongchunLi
 */
public abstract class GameUnit {
    protected int coordinateX;
    protected int coordinateY;
    protected Rectangle hitBox;

    /**
     *
     *Creates a new GameUnit object with the specified coordinates.
     *@param coordinateX the x-coordinate of the player
     *@param coordinateY the y-coordinate of the player
     */
    public GameUnit(int coordinateX, int coordinateY) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    /**
     *Draws the object based on the given input.
     * @param input the input that determines how the object should be drawn
     */
    public void Draw(Input input){}

    /**
     * Draws the object as a fixed unit, not subject to an input.
     */
    protected void DrawFixUnit(){}

    /**
     * check if a GameUnit is within 'NEAR' distance with this GameUnit in both coordinates.
     * @param unit the examined GameUnit.
     * @return true if unit isAround this.
     */
    protected boolean isAround(GameUnit unit){
        final int NEARDISTANCE = ShadowPac.getSTEP_SIZE() + (this.getImageSize() + unit.getImageSize());
        return (Math.abs(unit.coordinateX - this.coordinateX) < NEARDISTANCE &&
                Math.abs(unit.coordinateY - this.coordinateY) < NEARDISTANCE);
    }

    public abstract int getImageSize();

}
