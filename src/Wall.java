import bagel.Image;
import bagel.util.Rectangle;

/**
 * Wall class, stationary unit inherit from GameUnit.
 *
 * @author YongchunLi
 */
public class Wall extends GameUnit {
    private final static Image wallIMG = new Image("res/wall.png");

    public Wall(double coordinateX, double coordinateY) {
        super(coordinateX, coordinateY, (ShadowPacLogic_L0) null);
        setHitBox(new Rectangle(coordinateX, coordinateY, getWallIMG().getWidth(), getWallIMG().getHeight()));
    }

    protected static Image getWallIMG() {
        return wallIMG;
    }

    public void DrawFixUnit() {
        getWallIMG().drawFromTopLeft(getCoordinateX(), getCoordinateY());
    }

    @Override
    public double getImageSize() {
        return getWallIMG().getHeight();
    }
}
