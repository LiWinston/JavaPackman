import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.Random;

public class Ghost extends GameUnit{
    private static Image ghostIMG = new Image("res/ghostRed.png");
    private final static Image ghostFrenzy = new Image("res/ghostFrenzy.png");
    private final static double WIDTH = ghostIMG.getWidth();
    private final static double HEIGHT = ghostIMG.getHeight();
    private double stepSize = 0;
    protected int score = 30;
    private double direction;


    public Ghost(int coordinateX, int coordinateY) {
        super(coordinateX, coordinateY, (ShadowPacLogic_L0) null);
        setHitBox(new Rectangle(coordinateX, coordinateY, ghostIMG.getWidth(), ghostIMG.getHeight()));
    }

    public Ghost(int coordinateX, int coordinateY, ShadowPacLogic_L1 lg1, String str) {
        super(coordinateX, coordinateY,lg1);
        switch (str){
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
                System.err.println("Given Wrong Ghost Type! "+ this +" \n");
                System.err.println("Default ghost is constructed. \n");
        }
        setHitBox(new Rectangle(coordinateX, coordinateY, ghostIMG.getWidth(), ghostIMG.getHeight()));
    }

    private double getRandomDirection() {
        double[] directions = new double[]{TORIGHT,TODOWN,TOLEFT,TOUP};
        Random rand = new Random();
        int index = rand.nextInt(directions.length);
        return directions[index];
    }
    private double getRandomDirectionPositive() {
        double[] directions = new double[]{TORIGHT,TODOWN};
        Random rand = new Random();
        int index = rand.nextInt(directions.length);
        return directions[index];
    }


    public void Draw() {
        if(this.logicL1 != null) move();
        DrawFixUnit();
    }

//    @Override
    public void DrawFixUnit() {
        ghostIMG.drawFromTopLeft(this.getCoordinateX(), this.getCoordinateY());
    }

    @Override
    public int getImageSize() {
        return (int) ghostIMG.getHeight();
    }

    private boolean isToCollideWithWall(int x, int y, ShadowPacLogic_L1 logic) {
        Ghost newGst = new Ghost(x, y);
        Rectangle try_hit = new Rectangle(new Point(x, y), WIDTH, HEIGHT);
        for (Wall wl : logic.getWallList()) {
            if (newGst.isAround(wl)) {
                if (try_hit.intersects(wl.getHitBox())) {
                    return true;
                }
            }
        }
        return false;
    }


    public boolean isToCollideWithWall(double X, double Y, ShadowPacLogic_L0 lg0) {
        return false;
    }

    public boolean isToCollideWithWall(double X, double Y, ShadowPacLogic_L1 lg1) {
        return false;
    }


    public void move() {

    }

    public double getSTEP_SIZE() {
        return stepSize;
    }

    public int getScore() {
        return score;
    }
}
