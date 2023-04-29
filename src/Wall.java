import bagel.Image;
import bagel.util.Rectangle;

public class Wall extends GameUnit {
    final static Image wall = new Image("res/wall.png");

    public Wall(double coordinateX, double coordinateY) {
        super(coordinateX, coordinateY, (ShadowPacLogic_L0) null);
        setHitBox(new Rectangle(coordinateX, coordinateY, wall.getWidth(), wall.getHeight()));
    }

//    @Override
    public void DrawFixUnit() {
        wall.drawFromTopLeft(getCoordinateX(), getCoordinateY());
    }

    @Override
    public int getImageSize() {
        return (int) wall.getHeight();
    }
}
