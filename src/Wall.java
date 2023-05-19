import bagel.Image;
import bagel.util.Rectangle;

/**
 * Wall class, a stationary unit inherited from GameUnit.
 * Represents a wall in the game.
 *
 * <p>Walls are stationary game units that cannot be moved or collided with by other units.
 * They serve as obstacles in the game environment.</p>
 *
 * <p>The Wall class extends the GameUnit class and inherits its properties and behaviors.</p>
 *
 * <p><strong>Note:</strong> The wall image is assumed to be located at "res/wall.png".</p>
 *
 * <p><strong>Example Usage:</strong></p>
 * <pre>
 * Wall wall = new Wall(100, 200);
 * wall.DrawFixUnit();
 * </pre>
 *
 * @see GameUnit
 * @see <a href="https://people.eng.unimelb.edu.au/mcmurtrye/bagel-doc/bagel/util/Rectangle.html">bagel.util.Rectangle</a>
 * @see <a href="https://people.eng.unimelb.edu.au/mcmurtrye/bagel-doc/bagel/Image.html">bagel.Image</a>
 *
 * @author YongchunLi
 */
public class Wall extends GameUnit {
    private final static Image wallIMG = new Image("res/wall.png");

    /**
     * Constructs a Wall object with the specified coordinates.
     *
     * @param coordinateX the X-coordinate of the wall
     * @param coordinateY the Y-coordinate of the wall
     */
    public Wall(double coordinateX, double coordinateY) {
        super(coordinateX, coordinateY, null);
        setHitBox(new Rectangle(coordinateX, coordinateY, getWallIMG().getWidth(), getWallIMG().getHeight()));
    }

    /**
     * Retrieves the image of the wall.
     *
     * @return the Image object representing the wall image
     */
    public static Image getWallIMG() {
        return wallIMG;
    }

    /**
     * Draws the wall at its current position.
     */
    public void DrawFixUnit() {
        getWallIMG().drawFromTopLeft(getCoordinateX(), getCoordinateY());
    }

    /**
     * Retrieves the size of the wall image.
     *
     * @return the height of the wall image
     */
    @Override
    public double getImageSize() {
        return getWallIMG().getHeight();
    }
}