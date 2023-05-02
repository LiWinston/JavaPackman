import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.Random;

public class Ghost extends GameUnit{
    private String type = "Normal";
    private Image ghostIMG = new Image("res/ghostRed.png");
    private final static Image ghostFrenzy = new Image("res/ghostFrenzy.png");
    private final double WIDTH = ghostIMG.getWidth();
    private final double HEIGHT = ghostIMG.getHeight();
    private double stepSize;
    private double direction;
    private boolean hidden = false;


    public Ghost(double coordinateX, double coordinateY) {
        super(coordinateX, coordinateY, (ShadowPacLogic_L0) null);
        setHitBox(new Rectangle(coordinateX, coordinateY, ghostIMG.getWidth(), ghostIMG.getHeight()));
    }

    /*
     Construct various Ghost based on received str : type
     */
    public Ghost(double coordinateX, double coordinateY, ShadowPacLogic_L1 lg1, String str) {
        super(coordinateX, coordinateY,lg1);
        this.type = str;
        switch (type) {
            case "GhostRed":
                ghostIMG = new Image("res/ghostRed.png");
                stepSize = 1;
                setDirection(getTORIGHT());
                break;
            case "GhostBlue":
                ghostIMG = new Image("res/ghostBlue.png");
                stepSize = 2;
                setDirection(getTODOWN());
                break;
            case "GhostGreen":
                ghostIMG = new Image("res/ghostGreen.png");
                stepSize = 4;
                setDirection(getRandomDirectionPositive());
                break;
            case "GhostPink":
                ghostIMG = new Image("res/ghostPink.png");
                stepSize = 3;
                setDirection(getRandomDirection());
                break;
            default:
                System.err.println("Given Wrong Ghost Type! " + this + " \n");
                System.err.println("Default ghost is constructed. \n");
                break;
        }
        setHitBox(new Rectangle(coordinateX, coordinateY, ghostIMG.getWidth(), ghostIMG.getHeight()));
    }

    //use Random int to generate random direction of four
    private double getRandomDirection() {
        double[] directions = new double[]{getTORIGHT(), getTODOWN(), getTOLEFT(), getTOUP()};
        Random rand = new Random();
        int index = rand.nextInt(4);
        return directions[index];
    }

    //use Random int to generate random direction of TWO positive directions
    private double getRandomDirectionPositive() {
        double[] directions = new double[]{getTORIGHT(), getTODOWN()};
        Random rand = new Random();
        int index = rand.nextInt(2);
        return directions[index];
    }

    /**
     * If in GAMESTAGE gaming1, ghost moves before drawing
     * Use logicL1 not null for object origin tracking
     */
    public void Draw() {
        if(isHidden()) return;
        if(this.getLogicL1() != null){
            move();
            if(getLogicL1().getisFrenzy() && !this.isHidden()){
                ghostFrenzy.drawFromTopLeft(this.getCoordinateX(), this.getCoordinateY());
            }else ghostIMG.drawFromTopLeft(this.getCoordinateX(), this.getCoordinateY());
        }else{
            ghostIMG.drawFromTopLeft(this.getCoordinateX(), this.getCoordinateY());
        }

    }

    @Override
    public double getImageSize() {
        return ghostIMG.getHeight();
    }

    /**
     * Wall collision Detection and accordingly action defined
     * @param x received intended X position
     * @param y received intended Y position
     * @param logic The gameManager, used for ObjList accessing
     * @return T/F with direction converting in required ways
     */
    private boolean isToCollideWithWall(double x, double y, ShadowPacLogic_L1 logic) {
        Ghost newGst = new Ghost(x, y, logic,this.type);
        Rectangle try_hit = new Rectangle(new Point(x, y), WIDTH, HEIGHT);
        for (Wall wl : logic.getWallList()) {
            if (newGst.isAround(wl)) {
                if (try_hit.intersects(wl.getHitBox())) {
                    if (this.type.equals("GhostRed") || this.type.equals("GhostBlue") || this.type.equals("GhostGreen")) {
                        setDirection(getReverseDirection(getDirection()));
                    } else if (this.type.equals("GhostPink")) {
                        setDirection(getRandomDirection());
                    }
                    return true;
                }
            }
        }
        return false;
    }
    /*
    Due to confusion of representation 'Rad * -1'
    use this to get Reverse Direction gracefully
     */
    private double getReverseDirection(double direction) {
        if (direction == getTORIGHT()) {
            return getTOLEFT();
        } else if (direction == getTOLEFT()) {
            return getTORIGHT();
        } else if (direction == getTOUP()) {
            return getTODOWN();
        } else if (direction == getTODOWN()) {
            return getTOUP();
        }
        return direction;
    }

    private boolean isValidPosition(double X, double Y) {
        return X >= 0 && (X < ShadowPac.getWindowWidth()) && Y >= 0 && (Y < ShadowPac.getWindowHeight()
                && !(isToCollideWithWall(X, Y, getLogicL1())));
    }

    /*
    Conduct the automatic movement together with its Hitbox
     */
    public void move() {
        double X = getCoordinateX(), Y = getCoordinateY();
        double STEP_SIZE = getSTEP_SIZE();
        if (getDirection() == getTOLEFT()) {
            if (isValidPosition(X - STEP_SIZE, Y)) setCoordinateX(X - STEP_SIZE);
        } else if (getDirection() == getTORIGHT()) {
            if (isValidPosition(X + STEP_SIZE, Y)) setCoordinateX(X + STEP_SIZE);
        } else if (getDirection() == getTOUP()) {
            if (isValidPosition(X, Y - STEP_SIZE)) setCoordinateY(Y - STEP_SIZE);
        } else if (getDirection() == getTODOWN()) {
            if (isValidPosition(X, Y + STEP_SIZE)) setCoordinateY(Y + STEP_SIZE);
        }
        this.getHitBox().moveTo(new Point(getCoordinateX(), getCoordinateY()));
    }

    public void reset(){
        setCoordinateX(getOriginPos().x);
        setCoordinateY(getOriginPos().y);
        this.getHitBox().moveTo(getOriginPos());
    }
    public double getSTEP_SIZE() {
        return getLogicL1().getisFrenzy() ? stepSize - 0.5 : stepSize;
    }

    public int getScore() {
        return 30;
    }

    public void setHidden() {
        this.setHidden(true);
    }
    public void setHidden(boolean bl) {
        this.hidden = bl;
    }

    public boolean getHidden() {
        return isHidden();
    }

    protected double getDirection() {
        return direction;
    }

    protected void setDirection(double direction) {
        this.direction = direction;
    }

    protected boolean isHidden() {
        return hidden;
    }
}
