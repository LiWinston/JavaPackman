import bagel.Image;
import bagel.util.Rectangle;

/**
 * Dots in a broad sense aggregate several things with similar functions and properties.
 * inherited from GameUnit
 *
 * @author YongchunLi
 */
public class Dot extends GameUnit {
    private final String type;
    private Image dotIMG = new Image("res/dot.png");
    private boolean isExist;
    private int score = 10;

    /**
     * default constructor for normal dot object, mainly used in level 0
     *
     * @param coordinateX position x
     * @param coordinateY position y
     */
    public Dot(double coordinateX, double coordinateY) {
        super(coordinateX, coordinateY);
        this.setExist(true);
        type = "Dot";
        setDotIMG(new Image("res/dot.png"));
        this.setHitBox(new Rectangle(coordinateX, coordinateY, getDotIMG().getWidth(), getDotIMG().getHeight()));
    }

    /**
     * Specialized constructor for different kinds of dot,including normal Dot, Cherry and Pellet
     *
     * @param coordinateX position x
     * @param coordinateY position y
     * @param lg1         set the game logic1 manager for parent class -- gameunit
     * @param str         the dot type given, namely "Dot", "Cherry" or "Pellet"
     */
    public Dot(double coordinateX, double coordinateY, ShadowPacLogic_L1 lg1, String str) {
        super(coordinateX, coordinateY, lg1);
        this.type = str;
        this.setExist(true);
        switch (getType()) {
            case "Dot":
                break;
            case "Cherry":
                setDotIMG(new Image("res/cherry.png"));
                setScore(20);
                break;
            case "Pellet":
                setDotIMG(new Image("res/pellet.png"));
                setScore(0);
                break;
            default:
                System.out.println("Invalid dot type: " + getType());
                break;
        }
        this.setHitBox(new Rectangle(coordinateX, coordinateY, getDotIMG().getWidth(), getDotIMG().getHeight()));
    }

    public String getType() {
        return type;
    }

    public int getScore() {
        return score;
    }

    protected void setScore(int score) {
        this.score = score;
    }

    public void DrawFixUnit() {
        if (this.isExist()) getDotIMG().drawFromTopLeft(getCoordinateX(), getCoordinateY());
    }

    @Override
    public double getImageSize() {
        return getDotIMG().getHeight();
    }

    protected Image getDotIMG() {
        return dotIMG;
    }

    protected void setDotIMG(Image dotIMG) {
        this.dotIMG = dotIMG;
    }

    protected boolean isExist() {
        return isExist;
    }

    protected void setExist(boolean exist) {
        isExist = exist;
    }
}
