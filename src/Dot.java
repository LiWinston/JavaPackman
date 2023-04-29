import bagel.Image;
import bagel.util.Rectangle;

public class Dot extends GameUnit {
    private Image dotIMG = new Image("res/dot.png");
    private boolean isExist;
    private final String type;

    public String getType() {
        return type;
    }

    private int score = 10;

    public Dot(double coordinateX, double coordinateY) {
        super(coordinateX, coordinateY);
        this.setExist(true);
        type = "Dot";
        setDotIMG(new Image("res/dot.png"));
        this.setHitBox(new Rectangle(coordinateX, coordinateY, getDotIMG().getWidth(), getDotIMG().getHeight())) ;
    }
    public Dot(double coordinateX, double coordinateY, ShadowPacLogic_L1 lg1, String str) {
        super(coordinateX, coordinateY, lg1);
        this.type = str;
        this.setExist(true);
        switch (getType()) {
            case "Dot":break;
            case "Cherry":
                setDotIMG(new Image("res/cherry.png"));
                setScore(20);
                break;
            case "Pellet":
                setDotIMG(new Image("res/pellet.png"));
                setScore(0);
                break;
            default:
                System.out.println("Invalid dot type: " + getType());
                break;
        }
        this.setHitBox(new Rectangle(coordinateX, coordinateY, getDotIMG().getWidth(), getDotIMG().getHeight()));
    }
    public int getScore() {
        return score;
    }

//    @Override
    public void DrawFixUnit() {
        if (this.isExist()) getDotIMG().drawFromTopLeft(getCoordinateX(), getCoordinateY());
    }

    @Override
    public double getImageSize() {
        return getDotIMG().getHeight();
    }

    protected Image getDotIMG() {
        return dotIMG;
    }

    protected void setDotIMG(Image dotIMG) {
        this.dotIMG = dotIMG;
    }

    protected boolean isExist() {
        return isExist;
    }

    protected void setExist(boolean exist) {
        isExist = exist;
    }

    protected void setScore(int score) {
        this.score = score;
    }
}
