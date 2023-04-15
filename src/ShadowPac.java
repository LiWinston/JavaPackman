/**
 * Skeleton Code for SWEN20003 Project 1, Semester 1, 2023
 * Please enter your name below
 * @YongchunLi
 */
import bagel.*;
import bagel.Image;
import bagel.Window;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;


public class ShadowPac extends AbstractGame  {
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static int supposedGhostNum = 4;
    private final static int supposedWallNum = 145;
    private final static int supposedDotNum = 121;
    private final static int MID_WIDTH = WINDOW_WIDTH / 2;
    private final static int MID_HEIGHT = WINDOW_HEIGHT / 2;

    private final static String GAME_TITLE = "SHADOW PAC";
    private final Image BACKGROUND_IMAGE = new Image("res/background0.png");

    private final static int STEP_SIZE = 3;
    private Ghost[] ghostList = new Ghost[supposedGhostNum];
    private Wall[] wallList = new Wall[supposedWallNum];
    private Dot[] dotList = new Dot[supposedDotNum];
    private ShadowPacLogic glog;

    public static int getSTEP_SIZE() {
        return STEP_SIZE;
    }

    private enum gameStage {
        Welcome, Gaming, Lose, Success
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
        glog = new ShadowPacLogic(this);
    }
    public int getSupposedDotNum() {
        return supposedDotNum;
    }

    /**
     * Method used to read file and create objects
     * With Error handling
     */
    private void readCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader("res/level0.csv"))) {
            String line;
            int ghostNum = 0, wallNum = 0, dotNum = 0;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String type = data[0];
                int x = Integer.parseInt(data[1]);
                int y = Integer.parseInt(data[2]);
                switch (type) {
                    case "Player":
                        glog.setPlayer(x,y,glog);
                        break;
                    case "Ghost":
                        ghostList[ghostNum++] = new Ghost(x, y);
                        break;
                    case "Wall":
                        wallList[wallNum++] = new Wall(x, y);
                        break;
                    case "Dot":
                        dotList[dotNum++] = new Dot(x, y);
                        break;
                    default:
                        System.out.println("invalid csv data!");
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not exist:" + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unknown error:" + e.getMessage());
        }
    }

    public Ghost[] getGhostList() {
        return ghostList;
    }

    public Wall[] getWallList() {
        return wallList;
    }

    public Dot[] getDotList() {
        return dotList;
    }

    public void setGameStageLOSE() {
        gs = ShadowPac.gameStage.Lose;
    }
    public void setGameStageWIN() {
        gs = gameStage.Success;
    }

    /**
     * The entry point for the program.
     * DO:set game stage, read CSV and generate game units, call run Func.
     */
    public static void main(String[] args) {
        ShadowPac game = new ShadowPac();
        game.gs = gameStage.Welcome;
        game.readCSV();
        game.run();
    }

    /**
     * Updates the game state based on the user's input.
     * If the user presses the ESCAPE key, the window will be closed.
     * If the game state is set to Welcome, a welcome message and instructions are displayed.
     * The user must press the SPACE key to start the game.
     * If the game state is set to Gaming, the player's score & Lives remaining are displayed, and the player and game objects are drawn.
     * If the game state is set to Success or Lose, a message is displayed with the appropriate outcome of the game.
     * The user can also press the SPACE key to close the "Success or Lose" UI.(Not mentioned as Projcet Spec requirement)
     * @param input The input object that holds the user's input.
     */
    @Override
    protected void update(Input input) {

        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }
        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
        if(this.gs == gameStage.Welcome){
            ShowMessage SM_SHADOW_PAC = new ShowMessage(GAME_TITLE,260,250);
            SM_SHADOW_PAC.Show();
            ShowMessage SM_PRESS_SPACE_TO_START = new ShowMessage("PRESS SPACE TO START",320,440,24);
            SM_PRESS_SPACE_TO_START.Show();
            ShowMessage SM_USE_ARROW_KEYS_TO_MOVE = new ShowMessage("USE ARROW KEYS TO MOVE",310,480,24);
            SM_USE_ARROW_KEYS_TO_MOVE.Show();

            if(input.wasPressed(Keys.SPACE)) {
                gs = gameStage.Gaming;
            }
        }
        if(this.gs == gameStage.Gaming) {
            ShowMessage SM_Score = new ShowMessage("SCORE " + glog.getPlayer().getScore(),25,25,20);
            SM_Score.Show();
            Image redHeart = new Image("res/heart.png");
            switch (glog.getPlayer().getLife()){
                case 3 :
                    redHeart.drawFromTopLeft(900,10);
                case 2 :
                    redHeart.drawFromTopLeft(930,10);
                case 1 :
                    redHeart.drawFromTopLeft(960,10);
            }
            glog.getPlayer().Draw(input);
            for(Ghost gst : ghostList){
                gst.DrawFixUnit();
            }
            for(Wall wl : wallList){
                wl.DrawFixUnit();
            }
            for(Dot dt : dotList){
                dt.DrawFixUnit();
            }
            glog.letPlayerCheckArround();

        }
        if(this.gs == gameStage.Success) {
            ShowMessage SM_SHADOW_PAC = new ShowMessage("WELL DONE!",
                    MID_WIDTH - 4*ShowMessage.SPECIFIC_FONTSIZE,MID_HEIGHT + ShowMessage.SPECIFIC_FONTSIZE/2);
            SM_SHADOW_PAC.Show();
            if(input.wasPressed(Keys.SPACE)) {
                Window.close();
            }
        }
        if(this.gs == gameStage.Lose) {
            ShowMessage SM_SHADOW_PAC = new ShowMessage("GAME OVER!",
                    MID_WIDTH - 4*ShowMessage.SPECIFIC_FONTSIZE,MID_HEIGHT + ShowMessage.SPECIFIC_FONTSIZE/2);
            SM_SHADOW_PAC.Show();
            if(input.wasPressed(Keys.SPACE)) {
                Window.close();
            }
        }

    }
}
