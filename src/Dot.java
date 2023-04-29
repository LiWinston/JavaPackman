import bagel.Image;
import bagel.Input;
import bagel.util.Rectangle;

public class Dot extends GameUnit {
    private final static Image dot = new Image("res/dot.png");
    boolean isExist;

    protected int score = 10;

    public Dot(int coordinateX, int coordinateY) {
        super(coordinateX, coordinateY);
        this.isExist = true;
        this.setHitBox(new Rectangle(coordinateX, coordinateY, dot.getWidth(), dot.getHeight())) ;
    }

    public int getScore() {
        return score;
    }

    public void Draw() {
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
