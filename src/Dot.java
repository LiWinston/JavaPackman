import bagel.Image;
import bagel.Input;
import bagel.util.Rectangle;

public class Dot extends GameUnit {
    private final static Image dot = new Image("res/dot.png");
    boolean isExist;

    public Dot(int coordinateX, int coordinateY) {
        super(coordinateX, coordinateY);
        this.isExist = true;
        this.setHitBox(new Rectangle(coordinateX, coordinateY, dot.getWidth(), dot.getHeight())) ;
    }

    @Override
    public void Draw(Input input) {
        DrawFixUnit();
    }

//    @Override
    public void DrawFixUnit() {
        if (this.isExist) dot.drawFromTopLeft(getCoordinateX(), getCoordinateY());
    }

    @Override
    public int getImageSize() {
        return (int) dot.getHeight();
    }
}
