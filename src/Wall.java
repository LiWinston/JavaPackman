import bagel.*;
import bagel.util.Rectangle;

public class Wall extends GameUnit{
    final static Image wall = new Image("res/wall.png");

    public Wall(int coordinateX, int coordinateY) {
        super(coordinateX, coordinateY);
        hitBox = new Rectangle(coordinateX,coordinateY,wall.getWidth(),wall.getHeight());
    }

    @Override
    public void DrawFixUnit() {
        wall.drawFromTopLeft(coordinateX,coordinateY);
    }

    @Override
    public int getImageSize() {
        return (int) wall.getHeight();
    }
}
