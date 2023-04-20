import bagel.Image;
import bagel.util.Rectangle;

public class Wall extends GameUnit {
    final static Image wall = new Image("res/wall.png");

    public Wall(int coordinateX, int coordinateY) {
        super(coordinateX, coordinateY);
        setHitBox(new Rectangle(coordinateX, coordinateY, wall.getWidth(), wall.getHeight()));
    }

    @Override
    public void DrawFixUnit() {
        wall.drawFromTopLeft(getCoordinateX(), getCoordinateY());
    }

    @Override
    public int getImageSize() {
        return (int) wall.getHeight();
    }
}
