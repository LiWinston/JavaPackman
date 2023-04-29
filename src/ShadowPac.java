/**
 * Code for SWEN20003 Project 2 Part B, Semester 1, 2023
 * Please enter your name below
 *
 * @YongchunLi
 */

import bagel.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ShadowPac extends AbstractGame {
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static int MID_WIDTH = WINDOW_WIDTH / 2;
    private final static int MID_HEIGHT = WINDOW_HEIGHT / 2;
    private final static String GAME_TITLE = "SHADOW PAC";
    private final static int STEP_SIZE = 3;
    private static final List<ShadowPac> allGames = new ArrayList<ShadowPac>();
    private final static int supposedGhostNum_L0 = 4;
    private final static int supposedWallNum_L0 = 145;
    private final static int supposedDotNum_L0 = 121;
    private final short gamePID;
    private final Image BACKGROUND_IMAGE = new Image("res/background0.png");
    private final Ghost[] ghostList_L0 = new Ghost[supposedGhostNum_L0];
    private final Wall[] wallList_L0 = new Wall[supposedWallNum_L0];
    private final Dot[] dotList_L0 = new Dot[supposedDotNum_L0];
    private final ShadowPacLogic_L0 gameManager_L0;
    private final ShadowPacLogic_L1 gameManager_L1;
    private final List<Ghost> ghostList_L1;
    private final List<Wall> wallList_L1;
    private final List<Dot> dotList_L1;
    private gameStage stage;
    private int counter_LevelComplete;

    public void setFrenzy(ShadowPacLogic_L1 lg1) {
        if(getPID()!= lg1.getPID()) return;
        isFrenzy = true;
    }

    private boolean isFrenzy = false;


    public ShadowPac() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
        short ID;
        do {
            ID = generateID();
        } while (!isUniqueId(ID));
        this.gamePID = ID;
        this.gameManager_L0 = new ShadowPacLogic_L0(this);
        this.gameManager_L1 = new ShadowPacLogic_L1(this);

        allGames.add(this);
        System.out.println("New game Initialized, ID : " + gamePID);
        counter_LevelComplete = 0;
        ghostList_L1 = new ArrayList<>();
        wallList_L1 = new ArrayList<>();
        dotList_L1 = new ArrayList<>();
    }

    public static int getSTEP_SIZE() {
        return STEP_SIZE;
    }

    public static int getWindowWidth() {
        return WINDOW_WIDTH;
    }

    public static int getWindowHeight() {
        return WINDOW_HEIGHT;
    }

    /**
     * The entry point for the program.
     * DO:set game stage, read CSV and generate game units, call run Func.
     */
    public static void main(String[] args) {
        ShadowPac game = new ShadowPac();
        game.stage = gameStage.Welcome;
        game.readCSVLevelZero();
        game.readCSVLevelOne();
        game.run();
    }

    public short getPID() {
        return gamePID;
    }

    /**
     * generate random Hash ID for game.
     * @return next random short value.
     */
    private short generateID() {
        Random random = new Random();
        return (short) random.nextInt(Short.MAX_VALUE + 1);
    }

    /**
     * Judge if ID is Unique among all instances of game
     * @param id examined ID
     * @return true for yes and vice versa
     */
    private boolean isUniqueId(short id) {
        for (ShadowPac game : allGames) {
            if (game.gamePID == id) {
                return false;
            }
        }
        return true;
    }

    public int getSupposedDotNum() {
        return supposedDotNum_L0;
    }

    /**
     * Method used to read file and create objects
     * With Error handling
     */
    private void readCSVLevelZero() {
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
                        gameManager_L0.setPlayer_L0(x, y, gameManager_L0);
                        break;
                    case "Ghost":
                        ghostList_L0[ghostNum++] = new Ghost(x, y);
                        break;
                    case "Wall":
                        wallList_L0[wallNum++] = new Wall(x, y);
                        break;
                    case "Dot":
                        dotList_L0[dotNum++] = new Dot(x, y);
                        break;
                    default:
                        System.out.println("invalid csv data!");
                        break;
                }
            }
            if (ghostNum != supposedGhostNum_L0 || dotNum != supposedDotNum_L0 || wallNum != supposedWallNum_L0) {
                System.err.println("CSV File" + "res/level0.csv" + " maybe Wrong!" + "\n");
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not exist:" + e.getMessage() + "\n");
        } catch (Exception e) {
            System.err.println("Unknown error:" + e.getMessage() + "\n");
        }
    }
    private void readCSVLevelOne() {
        try (BufferedReader br = new BufferedReader(new FileReader("res/level1.csv"))) {
            String line;
            int ghostNum = 0, wallNum = 0, dotNum = 0;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String type = data[0];
                int x = Integer.parseInt(data[1]);
                int y = Integer.parseInt(data[2]);
                switch (type) {
                    case "Player":
                        gameManager_L1.setPlayer_L1(x, y, gameManager_L1);
                        break;
                    case "GhostRed":
                    case "GhostBlue":
                    case "GhostGreen":
                    case "GhostPink":
                        ghostList_L1.add(ghostNum++, new Ghost(x, y, this.gameManager_L1, type));
                        break;
                    case "Wall":
                        wallList_L1.add(wallNum++, new Wall(x, y));
                        break;
                    case "Dot":
                        dotList_L1.add(dotNum++, new Dot(x, y));
                        break;
                    case "Cherry":
//                        dotList_L1[dotNum++] = new Cherry(x, y);
                        dotList_L1.add(dotNum++, new Dot(x, y));
                        break;
                    case "Pellet":
//                        dotList_L1[dotNum++] = new Pellet(x, y);
                        dotList_L1.add(dotNum++, new Dot(x, y));
                        break;
                    default:
                        System.out.println("invalid csv data!");
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not exist:" + e.getMessage() + "\n");
        } catch (Exception e) {
            System.err.println("Unknown error:" + e.getMessage() + "\n");
        }
    }

    public Ghost[] getGhostList_L0() {
        return ghostList_L0;
    }

    public Wall[] getWallList_L0() {
        return wallList_L0;
    }

    public Dot[] getDotList_L0() {
        return dotList_L0;
    }

    public List<Ghost> getGhostList_L1() {
        return ghostList_L1;
    }

    public List<Dot> getDotList_L1() {
        return dotList_L1;
    }

    public List<Wall> getWallList_L1() {
        return wallList_L1;
    }

    /**
     * V2.3 prevent alien gameLogic from change game status
     * @param lgc ShadowPacLogic for verification
     */
    public void setGameStageLOSE(ShadowPacLogic_L0 lgc) {
        if (lgc.getPID() != this.getPID()) System.err.println("Unauthorized access:" + lgc.getPID() + "\n");
        stage = ShadowPac.gameStage.Lose;
    }
    public void setGameStageLOSE(ShadowPacLogic_L1 lgc) {
        if (lgc.getPID() != this.getPID()) System.err.println("Unauthorized access:" + lgc.getPID() + "\n");
        stage = ShadowPac.gameStage.Lose;
    }


    /**
     * V2.3 prevent alien gameLogic from change game status
     * @param lgc ShadowPacLogic for verification
     */
    public void setGameStageWIN(ShadowPacLogic_L0 lgc) {
        if (lgc.getPID() != this.getPID()) System.err.println("Unauthorized access:" + lgc.getPID() + "\n");
        stage = gameStage.LEVEL_COMPLETE;
    }
    public void setGameStageWIN(ShadowPacLogic_L1 lgc) {
        if (lgc.getPID() != this.getPID()) System.err.println("Unauthorized access:" + lgc.getPID() + "\n");
        stage = gameStage.Success;
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

        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }
        if (input.wasPressed(Keys.NUM_1)) {
            stage = gameStage.GamingL1;
        }
        BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
        if (this.stage == gameStage.Welcome) {
            updateWelcome(input);
        }
        if (this.stage == gameStage.GamingL0) {
            updateGamingL0(input);
        }
        if(this.stage == gameStage.LEVEL_COMPLETE){
            updateLevel_Complete(input);
        }
        if(this.stage == gameStage.L1Welcome){
            updateLevel1_Welcome(input);
        }

        if(this.stage == gameStage.GamingL1){
            updateGamingL1(input);
        }

        if (this.stage == gameStage.Success) {
            updateSuccess(input);
        }
        if (this.stage == gameStage.Lose) {
            updateLose(input);
        }
    }

    private void updateWelcome(Input input) {
        ShowMessage SM_Welcome = new ShowMessage(GAME_TITLE, 260, 250);
        SM_Welcome.Show();
        ShowMessage SM_PRESS_SPACE_TO_START = new ShowMessage("PRESS SPACE TO START", 320, 440, 24);
        SM_PRESS_SPACE_TO_START.Show();
        ShowMessage SM_USE_ARROW_KEYS_TO_MOVE = new ShowMessage("USE ARROW KEYS TO MOVE", 310, 480, 24);
        SM_USE_ARROW_KEYS_TO_MOVE.Show();

        if (input.wasPressed(Keys.SPACE)) {
            stage = gameStage.GamingL0;
        }
    }

    private void updateLevel1_Welcome(Input input) {
        ShowMessage SM_PRESS_SPACE_TO_START = new ShowMessage("PRESS SPACE TO START", 200, 350, 40);
        SM_PRESS_SPACE_TO_START.Show();
        ShowMessage SM_USE_ARROW_KEYS_TO_MOVE = new ShowMessage("USE ARROW KEYS TO MOVE", 190, 406, 40);
        SM_USE_ARROW_KEYS_TO_MOVE.Show();
        ShowMessage SM_EAT_PELLET= new ShowMessage("EAT THE PELLET TO ATTACK", 160, 462, 40);
        SM_EAT_PELLET.Show();


        if (input.wasPressed(Keys.SPACE)) {
            stage = gameStage.GamingL1;
        }

    }

    private void updateGamingL0(Input input) {
        if (input.wasPressed(Keys.W)) {
            stage = gameStage.LEVEL_COMPLETE;
        }

        ShowMessage SM_Score = new ShowMessage("SCORE " + gameManager_L0.getPlayer().getScore(), 25, 25, 20);
        SM_Score.Show();
        Image redHeart = new Image("res/heart.png");
        switch (gameManager_L0.getPlayer().getLife()) {
            case 3:
                redHeart.drawFromTopLeft(960, 10);
            case 2:
                redHeart.drawFromTopLeft(930, 10);
            case 1:
                redHeart.drawFromTopLeft(900, 10);
        }
        gameManager_L0.getPlayer().Draw(input);
        for (Ghost gst : ghostList_L0) {
            gst.DrawFixUnit();
        }
        for (Wall wl : wallList_L0) {
            wl.DrawFixUnit();
        }
        for (Dot dt : dotList_L0) {
            dt.DrawFixUnit();
        }
        gameManager_L0.letPlayerCheckAround();
    }
    private void updateGamingL1(Input input) {
        if (input.wasPressed(Keys.W)) {
            stage = gameStage.Success;
        }

        ShowMessage SM_Score = new ShowMessage("SCORE " + gameManager_L1.getPlayer().getScore(), 25, 25, 20);
        SM_Score.Show();
        Image redHeart = new Image("res/heart.png");
        switch (gameManager_L1.getPlayer().getLife()) {
            case 3:
                redHeart.drawFromTopLeft(960, 10);
            case 2:
                redHeart.drawFromTopLeft(930, 10);
            case 1:
                redHeart.drawFromTopLeft(900, 10);
        }
        gameManager_L1.getPlayer().Draw(input);
        for (Ghost gst : ghostList_L1) {
            gst.Draw();
        }
        for (Wall wl : wallList_L1) {
            wl.DrawFixUnit();
        }
        for (Dot dt : dotList_L1) {
            dt.DrawFixUnit();
        }
        gameManager_L0.letPlayerCheckAround();
    }


    private void updateSuccess(Input input) {
        ShowMessage SM_WELL_DONE = new ShowMessage("WELL DONE!",
                MID_WIDTH - 4 * ShowMessage.SPECIFIC_FONTSIZE, MID_HEIGHT + ShowMessage.SPECIFIC_FONTSIZE / 2);
        SM_WELL_DONE.Show();
        if (input.wasPressed(Keys.SPACE)) {
            Window.close();
        }
    }

    private void updateLevel_Complete(Input input) {
        ShowMessage SM_LEVELCOMPLETE = new ShowMessage("LEVEL COMPLETE!",
                MID_WIDTH - 6 * ShowMessage.SPECIFIC_FONTSIZE, MID_HEIGHT + ShowMessage.SPECIFIC_FONTSIZE / 2);
        SM_LEVELCOMPLETE.Show();
        ++ counter_LevelComplete;
        if(counter_LevelComplete == 300){
            stage = gameStage.L1Welcome;
        }
    }

    private void updateLose(Input input) {
        ShowMessage SM_GAME_OVER = new ShowMessage("GAME OVER!",
                MID_WIDTH - 4 * ShowMessage.SPECIFIC_FONTSIZE, MID_HEIGHT + ShowMessage.SPECIFIC_FONTSIZE / 2);
        SM_GAME_OVER.Show();
        if (input.wasPressed(Keys.SPACE)) {
            Window.close();
        }
    }

    public boolean getisFrenzy() {
        return isFrenzy;
    }

    private enum gameStage {
        Welcome, GamingL0, LEVEL_COMPLETE, L1Welcome, GamingL1, Lose, Success
    }
}

