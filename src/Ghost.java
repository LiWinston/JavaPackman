import bagel.Image;
import bagel.Input;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Ghost extends GameUnit {
    private final static Image ghostIMG = new Image("res/ghostRed.png");
    private final static Image ghostFrenzy = new Image("res/ghostFrenzy.png");
    private final static double WIDTH = ghostIMG.getWidth();
    private final static double HEIGHT = ghostIMG.getHeight();

    public Ghost(int coordinateX, int coordinateY) {
        super(coordinateX, coordinateY);
        setHitBox(new Rectangle(coordinateX, coordinateY, ghostIMG.getWidth(), ghostIMG.getHeight()));
    }

    @Override
    public void Draw(Input input) {
        DrawFixUnit();
    }

//    @Override
    public void DrawFixUnit() {
        ghostIMG.drawFromTopLeft(this.getCoordinateX(), this.getCoordinateY());
    }

    @Override
    public int getImageSize() {
        return (int) ghostIMG.getHeight();
    }

    private boolean isToCollideWithWall(int x, int y, ShadowPacLogic_L1 logic) {
        Ghost newGst = new Ghost(x, y);
        Rectangle try_hit = new Rectangle(new Point(x, y), WIDTH, HEIGHT);
        for (Wall wl : logic.getWallList()) {
            if (newGst.isAround(wl)) {
                if (try_hit.intersects(wl.getHitBox())) {
                    return true;
                }
            }
        }
        return false;
    }

}
