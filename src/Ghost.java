import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.Random;

public class Ghost extends GameUnit{
    private String type = "Normal";
    private Image ghostIMG = new Image("res/ghostRed.png");
    private final static Image ghostFrenzy = new Image("res/ghostFrenzy.png");
    private final double WIDTH = ghostIMG.getWidth();
    private final double HEIGHT = ghostIMG.getHeight();
    private double stepSize = 0;
    protected int score = 30;
    private double direction;


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
                direction = TORIGHT;
                break;
            case "GhostBlue":
                ghostIMG = new Image("res/ghostBlue.png");
                stepSize = 2;
                direction = TODOWN;
                break;
            case "GhostGreen":
                ghostIMG = new Image("res/ghostGreen.png");
                stepSize = 4;
                direction = getRandomDirectionPositive();
                break;
            case "GhostPink":
                ghostIMG = new Image("res/ghostPink.png");
                stepSize = 3;
                direction = getRandomDirection();
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
        double[] directions = new double[]{TORIGHT,TODOWN,TOLEFT,TOUP};
        Random rand = new Random();
        int index = rand.nextInt(4);
        return directions[index];
    }

    //use Random int to generate random direction of TWO positive directions
    private double getRandomDirectionPositive() {
        double[] directions = new double[]{TORIGHT,TODOWN};
        Random rand = new Random();
        int index = rand.nextInt(2);
        return directions[index];
    }

    /**
     * If in GAMESTAGE gaming1, ghost moves before drawing
     * Use logicL1 not null for object origin tracking
     */
    public void Draw() {
        if(this.logicL1 != null){
            move();
            if(logicL1.getisFrenzy()){
                ghostFrenzy.drawFromTopLeft(this.getCoordinateX(), this.getCoordinateY());
            }else{
                ghostIMG.drawFromTopLeft(this.getCoordinateX(), this.getCoordinateY());
            }
        }else{
            ghostIMG.drawFromTopLeft(this.getCoordinateX(), this.getCoordinateY());
        }

    }

    public void DrawFixUnit() {
        ghostIMG.drawFromTopLeft(this.getCoordinateX(), this.getCoordinateY());
    }

    @Override
    public int getImageSize() {
        return (int) ghostIMG.getHeight();
    }

    /**
     * Wall collision Detection and accordingly action defined
     * @param x received intended X position
     * @param y received intended Y position
     * @param logic The gameManager, used for ObjList accessing
     * @return T/F with direction converting in required ways
     */
    private boolean isToCollideWithWall(double x, double y, ShadowPacLogic_L1 logic) {
        Ghost newGst = new Ghost(x, y);
        Rectangle try_hit = new Rectangle(new Point(x, y), WIDTH, HEIGHT);
        for (Wall wl : logic.getWallList()) {
            if (newGst.isAround(wl)) {
                if (try_hit.intersects(wl.getHitBox())) {
                    if (this.type.equals("GhostRed") || this.type.equals("GhostBlue") || this.type.equals("GhostGreen")) {
                        direction = getReverseDirection(direction);
                    } else if (this.type.equals("GhostPink")) {
                        direction = getRandomDirection();
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
        if (direction == TORIGHT) {
            return TOLEFT;
        } else if (direction == TOLEFT) {
            return TORIGHT;
        } else if (direction == TOUP) {
            return TODOWN;
        } else if (direction == TODOWN) {
            return TOUP;
        }
        return direction;
    }

    private boolean isValidPosition(double X, double Y) {
        return X >= 0 && (X < ShadowPac.getWindowWidth()) && Y >= 0 && (Y < ShadowPac.getWindowHeight()
                && !(isToCollideWithWall(X, Y, logicL1)));
    }

    /*
    Conduct the automatic movement together with its Hitbox
     */
    public void move() {
        double X = getCoordinateX(), Y = getCoordinateY();
        double STEP_SIZE = logicL1.getisFrenzy()? getSTEP_SIZE()-0.5 : getSTEP_SIZE();
        if (direction == TOLEFT) {
            if (isValidPosition(X - STEP_SIZE, Y)) setCoordinateX(X - STEP_SIZE);
        } else if (direction == TORIGHT) {
            if (isValidPosition(X + STEP_SIZE, Y)) setCoordinateX(X + STEP_SIZE);
        } else if (direction == TOUP) {
            if (isValidPosition(X, Y - STEP_SIZE)) setCoordinateY(Y - STEP_SIZE);
        } else if (direction == TODOWN) {
            if (isValidPosition(X, Y + STEP_SIZE)) setCoordinateY(Y + STEP_SIZE);
        }
        this.getHitBox().moveTo(new Point(getCoordinateX(), getCoordinateY()));
    }

    public double getSTEP_SIZE() {
        return stepSize;
    }

    public int getScore() {
        return score;
    }
}
