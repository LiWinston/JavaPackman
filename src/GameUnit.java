import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * GameUnit Class
 * Implement basic game unit behaviors, especially the direction, coordinates, collision box, original position,
 * and game manager that can manipulate this unit (in fact, it is mutually exclusive for each unit; if there is 1, there is
 * no 0, and vice versa).
 *
 * This class serves as a base class for various game units in the game.
 *
 * @author YongchunLi
 */
public abstract class GameUnit {
    private static final double TOLEFT = -Math.PI;
    private static final double TORIGHT = 0.0;
    private static final double TOUP = -Math.PI / 2;
    private static final double TODOWN = Math.PI / 2;
    private final ShadowPacLogic_L1 logicL1;
    private ShadowPacLogic_L0 logicL0;
    private double coordinateX;
    private double coordinateY;
    private Rectangle hitBox;
    private Point originPos;// initial position of the game-unit
    private double checkScope;//= Math.max(ShadowPac.getWindowWidth(),ShadowPac.getWindowHeight());


    /**
     * Creates a new GameUnit object with the specified coordinates and with default logic null.
     *
     * @param coordinateX the x-coordinate of the player
     * @param coordinateY the y-coordinate of the player
     */
    public GameUnit(double coordinateX, double coordinateY) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        setOriginPos(new Point(coordinateX, coordinateY));// initial position of the player
        this.setLogicL0(null);
        this.logicL1 = null;
    }

    /**
     * Creates a new GameUnit object with the specified coordinates.
     *
     * @param coordinateX  the x-coordinate of the player
     * @param coordinateY  the y-coordinate of the player
     * @param logicManager the gameLogic manager instance to initiate this unit
     */

    public GameUnit(double coordinateX, double coordinateY, Object logicManager) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        setOriginPos(new Point(coordinateX, coordinateY));// initial position of the player
        if (logicManager instanceof ShadowPacLogic_L0) {
            this.setLogicL0((ShadowPacLogic_L0) logicManager);
            this.logicL1 = null;
        } else if (logicManager instanceof ShadowPacLogic_L1) {
            this.logicL1 = (ShadowPacLogic_L1) logicManager;
            this.setLogicL0(null);
        } else {
            this.setLogicL0(null);
            this.logicL1 = null;
        }
    }


    /**
     * Gets the constant value for moving to the left.
     *
     * @return the constant value for moving to the left
     */
    protected static double getTOLEFT() {
        return TOLEFT;
    }

    /**
     * Gets the constant value for moving to the right.
     *
     * @return the constant value for moving to the right
     */
    protected static double getTORIGHT() {
        return TORIGHT;
    }

    /**
     * Gets the constant value for moving up.
     *
     * @return the constant value for moving up
     */
    protected static double getTOUP() {
        return TOUP;
    }

    /**
     * Gets the constant value for moving down.
     *
     * @return the constant value for moving down
     */
    protected static double getTODOWN() {
        return TODOWN;
    }

    /**
     * Calculate the Euclidean distance between two points
     * distance = sqrt{(x2 - x1)^2 + (y2 - y1)^2}
     *
     * @param x1 coordinate x of first point
     * @param y1 coordinate y of second point
     * @param x2 coordinate x of first point
     * @param y2 coordinate y of second point
     * @return the Euclidean distance
     */
    private static double distance(double x1, double y1, double x2, double y2) {
        double dx = x1 - x2;
        double dy = y1 - y2;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Gets the X coordinate of the player.
     *
     * @return the X coordinate of the player
     */
    public double getCoordinateX() {
        return coordinateX;
    }

    /**
     * Sets the X coordinate of the player.
     *
     * @param coordinateX the new X coordinate of the player
     */
    public void setCoordinateX(double coordinateX) {
        this.coordinateX = coordinateX;
    }

    /**
     * Gets the Y coordinate of the player.
     *
     * @return the Y coordinate of the player
     */
    public double getCoordinateY() {
        return coordinateY;
    }

    /**
     * Sets the Y coordinate of the player.
     *
     * @param coordinateY the new Y coordinate of the player
     */

    public void setCoordinateY(double coordinateY) {
        this.coordinateY = coordinateY;
    }

    /**
     * allow exposure of Hitbox safely
     * allow other objects get this Hitbox for collision detection
     *
     * @return hitbox of this unit
     */
    public Rectangle getHitBox() {
        return hitBox;
    }

    /**
     * direct setter for hitbox
     *
     * @param hitBox receive the existing Rectangle obj for setting.
     */
    public void setHitBox(Rectangle hitBox) {
        this.hitBox = hitBox;
    }

    /**
     * check if a GameUnit is within 'NEAR' distance with this GameUnit.
     * distinguish which specific class does the obj belongs to and set the checkScope accordingly
     *
     * @param unit the examined GameUnit.
     * @return true if unit isAround this within a specific circle, the centre point of which is x,y coord of this unit.
     */
    protected boolean isAround(GameUnit unit) {
        final double l1 = this.getImageSize();
        final double l2 = unit.getImageSize();
        final double sqrt2 = Math.sqrt(2.0);
        if (this instanceof Player) {
            checkScope = ((Player) this).getSTEP_SIZE() + sqrt2 * Math.max(l1, l2);
        } else if (this instanceof Ghost) {
            checkScope = ((Ghost) this).getSTEP_SIZE() + sqrt2 * Math.max(l1, l2);
        }
        final double EPSILON = 1e-10;
        return distance(unit.coordinateX, unit.coordinateY, this.coordinateX, this.coordinateY) - checkScope <= EPSILON;
    }

    /**
     * Retrieves the size of the image.
     *
     * @return the size of the image as a double value
     */
    public abstract double getImageSize();

    /**
     * Retrieves the origin position.
     *
     * @return the origin position as a Point object
     */
    protected Point getOriginPos() {
        return originPos;
    }

    /**
     * Sets the origin position of this Unit.
     *
     * @param originPos the origin position to be set
     */
    protected void setOriginPos(Point originPos) {
        this.originPos = originPos;
    }

    /**
     * Retrieves the Level 0 logic for this Unit.
     *
     * @return the Level 0 logic as a ShadowPacLogic_L0 object
     */
    protected ShadowPacLogic_L0 getLogicL0() {
        return logicL0;
    }

    /**
     * Sets the Level 0 logic for this Unit.
     *
     * @param logicL0 the Level 0 logic to be set
     */
    protected void setLogicL0(ShadowPacLogic_L0 logicL0) {
        this.logicL0 = logicL0;
    }

    /**
     * Retrieves the Level 1 logic for this Unit.
     *
     * @return the Level 1 logic as a ShadowPacLogic_L1 object
     */
    protected ShadowPacLogic_L1 getLogicL1() {
        return logicL1;
    }

}
