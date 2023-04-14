/**
 * Skeleton Code for SWEN20003 Project 1, Semester 1, 2023
 *
 * Please enter your name below
 * @YongchunLi
 */
import bagel.*;
public class ShadowPac extends AbstractGame  {
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static int MID_WIDTGH = WINDOW_WIDTH / 2;
    private final static int MID_HEIGHT = WINDOW_HEIGHT / 2;

    private final static String GAME_TITLE = "SHADOW PAC";
    private final Image BACKGROUND_IMAGE = new Image("res/background0.png");

    protected final static int STEP_SIZE = 3;


    protected enum gameStage {
        Welcome, Gaming, GameOver, Success
    }

    private gameStage gs;


    public static int getWindowWidth(){
        return WINDOW_WIDTH;
    }
    public static int getWindowHeight(){
        return WINDOW_HEIGHT;
    }

    public ShadowPac(){
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
    }

    /**
     * Method used to read file and create objects (you can change
     * this method as you wish).
     */
    private void readCSV() {

    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowPac game = new ShadowPac();
        game.gs = gameStage.Welcome;

        game.run();
    }

    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     */
    @Override
    protected void update(Input input) {

        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }
        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
        if(this.gs == gameStage.Welcome){
            ShowMessage SM_SHADOW_PAC = new ShowMessage("SHADOW PAC",260,250);
            SM_SHADOW_PAC.Show();
            ShowMessage SM_PRESS_SPACE_TO_START = new ShowMessage("PRESS SPACE TO START",320,440,24);
            SM_PRESS_SPACE_TO_START.Show();
            ShowMessage SM_USE_ARROW_KEYS_TO_MOVE = new ShowMessage("USE ARROW KEYS TO MOVE",320,540,24);
            SM_USE_ARROW_KEYS_TO_MOVE.Show();

            if(input.wasPressed(Keys.SPACE)) {
                gs = gameStage.Gaming;
            }
        }
        if(this.gs == gameStage.Gaming) {
            ShowMessage SM_Score = new ShowMessage("SCORE",25,25,20);
            SM_Score.Show();
        }
    }
}
