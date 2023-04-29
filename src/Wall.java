import bagel.Image;
import bagel.util.Rectangle;

public class Wall extends GameUnit {
    private final static Image wall = new Image("res/wall.png");

    public Wall(double coordinateX, double coordinateY) {
        super(coordinateX, coordinateY, (ShadowPacLogic_L0) null);
        setHitBox(new Rectangle(coordinateX, coordinateY, getWall().getWidth(), getWall().getHeight()));
    }

    protected static Image getWall() {
        return wall;
    }

    //    @Override
    public void DrawFixUnit() {
        getWall().drawFromTopLeft(getCoordinateX(), getCoordinateY());
    }

    @Override
    public double getImageSize() {
        return getWall().getHeight();
    }
}
