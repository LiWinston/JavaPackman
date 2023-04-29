import bagel.Input;
import bagel.Keys;
import bagel.util.Rectangle;

/**
 * GameUnit Class
 * Implement basic game unit behaviours
 *
 * @YongchunLi
 */
public abstract class GameUnit {
    private double coordinateX;
    private double coordinateY;
    private Rectangle hitBox;
    protected static final double TOLEFT = -Math.PI;
    protected static final double TORIGHT = 0.0;
    protected static final double TOUP = -Math.PI/2;
    protected static final double TODOWN = Math.PI/2;
    private double stepSize;
    protected ShadowPacLogic_L0 logicL0;
    protected ShadowPacLogic_L1 logicL1;



    /**
     * Creates a new GameUnit object with the specified coordinates.
     *
     * @param coordinateX the x-coordinate of the player
     * @param coordinateY the y-coordinate of the player
     */
    public GameUnit(double coordinateX, double coordinateY) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.logicL0 = null;
        this.logicL1 = null;
    }

    public GameUnit(double coordinateX, double coordinateY, ShadowPacLogic_L0 lg0) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.logicL0 = lg0;
        this.logicL1 = null;
    }
    public GameUnit(double coordinateX, double coordinateY, ShadowPacLogic_L1 lg1) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.logicL1 = lg1;
        this.logicL0 = null;
    }


    public double getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(double coordinateX) {
        this.coordinateX = coordinateX;
    }

    public double getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(double coordinateY) {
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
    public void setHitBox(double X,double Y) {
        this.hitBox = new Rectangle(X,Y,getImageSize(),getImageSize());
    }



    private double getSTEP_SIZE() {
        return stepSize;
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
//    protected void DrawFixUnit() {
//    }

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
