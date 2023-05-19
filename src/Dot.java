import bagel.Image;
import bagel.util.Rectangle;

/**
 * The Dot class represents different types of dots in the game.
 * It inherits from the GameUnit class and provides functionality for dot objects.
 * Dots can have different types, such as normal dots, cherries, or pellets.
 *
 * Example Usage:
 * Dot dot = new Dot(100, 200);
 * dot.DrawFixUnit();
 *
 * @see GameUnit
 * @see ShadowPacLogic_L1
 * @see ShadowPac
 * @see <a href="https://people.eng.unimelb.edu.au/mcmurtrye/bagel-doc/bagel/util/Rectangle.html">bagel.util.Rectangle</a>
 * @see <a href="https://people.eng.unimelb.edu.au/mcmurtrye/bagel-doc/bagel/Image.html">bagel.Image</a>
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
    public void setScore(int score) {
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
    public Image getDotIMG() {
        return dotIMG;
    }

    /**
     * Sets the dot appearance.
     *
     * @param dotIMG The dot image to set.
     */
    public void setDotIMG(Image dotIMG) {
        this.dotIMG = dotIMG;
    }

    /**
     * Checks if the object exists.
     *
     * @return {@code true} if the object exists, {@code false} otherwise.
     */
    public boolean isExist() {
        return isExist;
    }

    /**
     * Sets the existence of the object,.
     *
     * @param exist The existence to set.
     */
    public void setExist(boolean exist) {
        isExist = exist;
    }
}