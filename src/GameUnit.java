import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * GameUnit Class
 * Implement basic game unit behaviours
 *
 * @YongchunLi
 */
public abstract class GameUnit {
    private static final double TOLEFT = -Math.PI;
    private static final double TORIGHT = 0.0;
    private static final double TOUP = -Math.PI / 2;
    private static final double TODOWN = Math.PI / 2;
    private final ShadowPacLogic_L1 logicL1;
    private double coordinateX;
    private double coordinateY;
    private Rectangle hitBox;
    private Point originPos;// initial position of the player
    private ShadowPacLogic_L0 logicL0;
    private double checkScope ;//= Math.max(ShadowPac.getWindowWidth(),ShadowPac.getWindowHeight());



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

    public GameUnit(double coordinateX, double coordinateY, ShadowPacLogic_L0 lg0) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        setOriginPos(new Point(coordinateX, coordinateY));// initial position of the player
        this.setLogicL0(lg0);
        this.logicL1 = null;
    }

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
     *
     * @param hitBox receive the existing Rectangle obj for setting.
     */
    public void setHitBox(Rectangle hitBox) {
        this.hitBox = hitBox;
    }

    /**
     * check if a GameUnit is within 'NEAR' distance with this GameUnit in both coordinates.
     * @param unit the examined GameUnit.
     * @return true if unit isAround this.
     */
    protected boolean isAround(GameUnit unit) {
        double l1 = this.getImageSize();
        double l2 = unit.getImageSize();
        final double sqrt2 = Math.sqrt(2.0);
        if(this.getClass().equals(Player_L0.class)){
            checkScope = ShadowPac.getSTEP_SIZE() + sqrt2 * Math.max(l1, l2);
        } else if (this instanceof Player_L1) {
            checkScope = ((Player_L1)this).getSTEP_SIZE() + sqrt2 * Math.max(l1, l2);
        } else if (this instanceof Ghost) {
            checkScope = ((Ghost)this).getSTEP_SIZE() + sqrt2 * Math.max(l1, l2);
        }
        final double EPSILON = 1e-10;
//        if(distance(unit.coordinateX, unit.coordinateY, this.coordinateX, this.coordinateY) - checkScope <= EPSILON){
//            System.out.println(this.getClass().toString() +"  "+ unit.getClass().toString() +"  "  + distance(unit.coordinateX, unit.coordinateY, this.coordinateX, this.coordinateY) + " 比较半径" + checkScope);
//            System.out.println(distance(unit.coordinateX, unit.coordinateY, this.coordinateX, this.coordinateY) - checkScope <= EPSILON);
//        }
        return distance(unit.coordinateX, unit.coordinateY, this.coordinateX, this.coordinateY) - checkScope <= EPSILON;
    }
    private static double distance(double x1, double y1, double x2, double y2) {
        double dx = x1 - x2;
        double dy = y1 - y2;
        return Math.sqrt(dx * dx + dy * dy);
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
