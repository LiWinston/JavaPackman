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

    /**
     * Retrieves the type of the object.
     *
     * @return The type of the object.
     */
    public String getType() {
        return type;
    }

    /**
     * Retrieves the score of the object.
     *
     * @return The score of the object.
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the score of the object.
     *
     * @param score The score to set.
     */
    protected void setScore(int score) {
        this.score = score;
    }

    /**
     * Draws the FixUnit if it exists.
     */
    public void DrawFixUnit() {
        if (this.isExist()) getDotIMG().drawFromTopLeft(getCoordinateX(), getCoordinateY());
    }

    /**
     * Retrieves the size of the image.
     *
     * @return The size of the image.
     */
    @Override
    public double getImageSize() {
        return getDotIMG().getHeight();
    }

    /**
     * Retrieves the dot image as Image object.
     *
     * @return The dot image.
     */
    protected Image getDotIMG() {
        return dotIMG;
    }

    /**
     * Sets the dot appearance.
     *
     * @param dotIMG The dot image to set.
     */
    protected void setDotIMG(Image dotIMG) {
        this.dotIMG = dotIMG;
    }

    /**
     * Checks if the object exists.
     *
     * @return {@code true} if the object exists, {@code false} otherwise.
     */
    protected boolean isExist() {
        return isExist;
    }

    /**
     * Sets the existence of the object,.
     *
     * @param exist The existence to set.
     */
    protected void setExist(boolean exist) {
        isExist = exist;
    }
}