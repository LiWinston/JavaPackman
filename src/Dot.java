import bagel.Image;
import bagel.util.Rectangle;

public class Dot extends GameUnit {
    private Image dotIMG = new Image("res/dot.png");
    boolean isExist;
    private final String type;

    public String getType() {
        return type;
    }

    protected int score = 10;

    public Dot(double coordinateX, double coordinateY) {
        super(coordinateX, coordinateY);
        this.isExist = true;
        type = "Dot";
        dotIMG = new Image("res/dot.png");
        this.setHitBox(new Rectangle(coordinateX, coordinateY, dotIMG.getWidth(), dotIMG.getHeight())) ;
    }
    public Dot(double coordinateX, double coordinateY, ShadowPacLogic_L1 lg1, String str) {
        super(coordinateX, coordinateY, lg1);
        this.type = str;
        this.isExist = true;
        switch (type) {
            case "Dot":break;
            case "Cherry":
                dotIMG = new Image("res/cherry.png");
                score = 20;
                break;
            case "Pellet":
                dotIMG = new Image("res/pellet.png");
                score = 0;
                break;
            default:
                System.out.println("Invalid dot type: " + type);
                break;
        }
        this.setHitBox(new Rectangle(coordinateX, coordinateY, dotIMG.getWidth(), dotIMG.getHeight()));
    }
    public int getScore() {
        return score;
    }

    public void Draw() {
        DrawFixUnit();
    }

//    @Override
    public void DrawFixUnit() {
        if (this.isExist) dotIMG.drawFromTopLeft(getCoordinateX(), getCoordinateY());
    }

    @Override
    public int getImageSize() {
        return (int) dotIMG.getHeight();
    }
}
