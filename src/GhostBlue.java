import bagel.Image;

public class GhostBlue extends Ghost{
    private final static Image ghostIMG = new Image("res/ghostRed.png");
    public GhostBlue(int coordinateX, int coordinateY) {
        super(coordinateX, coordinateY);
    }
}
