import bagel.Image;

public class GhostBlue extends Ghost{
    private final static Image ghostIMG = new Image("res/ghostRed.png");
    public GhostBlue(int coordinateX, int coordinateY, ShadowPacLogic_L1 lg1) {
        super(coordinateX, coordinateY, lg1);
    }

}
