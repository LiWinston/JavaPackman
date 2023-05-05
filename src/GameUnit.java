import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * GameUnit Class
 * Implement basic game unit behaviours, Especially the direction, coordinates, collision box, original position,
 * game manager that can manipulate this unit (in fact, it is mutually exclusive for each unit, if there is 1, there is
 * no 0, and vice versa)
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
     * Creates a new GameUnit object with the specified coordinates.
     *
     * @param coordinateX the x-coordinate of the player
     * @param coordinateY the y-coordinate of the player
     *                    ShadowPacLogic_L0 optional
     *                    ShadowPacLogic_L1 optional
     *                    if Not given then set null.
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
     * @param coordinateX the x-coordinate of the player
     * @param coordinateY the y-coordinate of the player
     *                    ShadowPacLogic_L0 optional
     *                    ShadowPacLogic_L1 optional
     *                    if Not given then set null.
     * @param lg0         the game manager of logic0 instance to initiate this unit
     */

    public GameUnit(double coordinateX, double coordinateY, ShadowPacLogic_L0 lg0) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        setOriginPos(new Point(coordinateX, coordinateY));// initial position of the player
        this.setLogicL0(lg0);
        this.logicL1 = null;
    }

    /**
     * Creates a new GameUnit object with the specified coordinates.
     *
     * @param coordinateX the x-coordinate of the player
     * @param coordinateY the y-coordinate of the player
     *                    ShadowPacLogic_L0 optional
     *                    ShadowPacLogic_L1 optional
     *                    if Not given then set null.
     * @param lg1         the game manager of logic1 instance to initiate this unit
     */

    public GameUnit(double coordinateX, double coordinateY, ShadowPacLogic_L1 lg1) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        setOriginPos(new Point(coordinateX, coordinateY));// initial position of the player
        this.logicL1 = lg1;
        this.setLogicL0(null);
    }

    protected static double getTOLEFT() {
        return TOLEFT;
    }

    protected static double getTORIGHT() {
        return TORIGHT;
    }

    protected static double getTOUP() {
        return TOUP;
    }

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
     * check if a GameUnit is within 'NEAR' distance with this GameUnit in both coordinates.
     * distinguish which specific class does the obj belongs to and set the checkScope accordingly
     *
     * @param unit the examined GameUnit.
     * @return true if unit isAround this within a specific circle, the centre point of which is x,y coord of this unit.
     */
    protected boolean isAround(GameUnit unit) {
        final double l1 = this.getImageSize();
        final double l2 = unit.getImageSize();
        final double sqrt2 = Math.sqrt(2.0);
        if (this.getClass().equals(Player_L0.class)) {
            checkScope = ShadowPac.getSTEP_SIZE() + sqrt2 * Math.max(l1, l2);
        } else if (this instanceof Player_L1) {
            checkScope = ((Player_L1) this).getSTEP_SIZE() + sqrt2 * Math.max(l1, l2);
        } else if (this instanceof Ghost) {
            checkScope = ((Ghost) this).getSTEP_SIZE() + sqrt2 * Math.max(l1, l2);
        }
        final double EPSILON = 1e-10;
        return distance(unit.coordinateX, unit.coordinateY, this.coordinateX, this.coordinateY) - checkScope <= EPSILON;
    }

    public abstract double getImageSize();

    protected Point getOriginPos() {
        return originPos;
    }

    protected void setOriginPos(Point originPos) {
        this.originPos = originPos;
    }

    protected ShadowPacLogic_L0 getLogicL0() {
        return logicL0;
    }

    protected void setLogicL0(ShadowPacLogic_L0 logicL0) {
        this.logicL0 = logicL0;
    }

    protected ShadowPacLogic_L1 getLogicL1() {
        return logicL1;
    }
}
