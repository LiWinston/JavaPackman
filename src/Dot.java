import bagel.Image;
import bagel.util.Rectangle;

public class Dot extends GameUnit {
    private static Image dotIMG;
    boolean isExist;
    private String type;

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
        if (type.equals("Dot")) {
            dotIMG = new Image("res/dot.png");
        } else if (type.equals("Cherry")) {
            dotIMG = new Image("res/cherry.png");
            score = 20;
        } else if (type.equals("Pellet")) {
            dotIMG = new Image("res/pellet.png");
            score = 0;
        } else {
            System.out.println("Invalid dot type: " + type);
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
