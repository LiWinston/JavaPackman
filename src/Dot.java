import bagel.Image;
import bagel.Input;
import bagel.util.Rectangle;

public class Dot extends GameUnit{
    boolean isExist;
    private final static Image dot = new Image("res/dot.png");

    public Dot(int coordinateX, int coordinateY) {
        super(coordinateX, coordinateY);
        this.isExist = true;
        hitBox = new Rectangle(coordinateX,coordinateY,dot.getWidth(),dot.getHeight());
    }

    @Override
    public void Draw(Input input) {
        DrawFixUnit();
    }

    @Override
    public void DrawFixUnit() {
        if(this.isExist) dot.drawFromTopLeft(coordinateX,coordinateY);
    }

    @Override
    public int getImageSize() {
        return (int) dot.getHeight();
    }
}
