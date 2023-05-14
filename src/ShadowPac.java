import bagel.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Code for SWEN20003 Project 2 Part B, Semester 1, 2023
 * Please enter your name below
 * Changing to singleton mode failed, and rolled back to the multi-object version, but the concurrency was not realized,
 * due to Bagel's window drawing and simultaneous access to some resources.
 *
 * @author YongchunLi
 */
public class ShadowPac extends AbstractGame {
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static int MID_WIDTH = WINDOW_WIDTH / 2;
    private final static int MID_HEIGHT = WINDOW_HEIGHT / 2;
    private final static String GAME_TITLE = "SHADOW PAC";
    private final static int STEP_SIZE = 3;

    //game obj pool, no need to consider but has been implemented and lazy to delete
    private static final List<ShadowPac> allGames = new ArrayList<>();
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
    private int counter_Frenzy;
    private boolean isFrenzy = false;


    /**
     * Initialize the game class with two new logic managers, generate the gamePID, add the game object to the object pool,
     * and initialize three level1 object lists. Note that it is only initialized but also needs to read csv to import object information
     */
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
        game.readCSV(0);
        game.readCSV(1);
        game.run();
    }

    /**
     * Allow the bonded ShadowPacLogic_L1 to set the game mode to Frenzy mode and begin frame counter
     *
     * @param lg1 level 1 Game Manager
     */
    public void setFrenzy(ShadowPacLogic_L1 lg1) {
        if (getPID() != lg1.getPID()) return;
        isFrenzy = true;
        System.out.println("Frenzy Mode Begin!");
        counter_Frenzy = 0;
    }

    public short getPID() {
        return gamePID;
    }

    /**
     * generate random Hash ID for game.
     *
     * @return a proper random short value.
     */
    private short generateID() {
        Random random = new Random();
        return (short) random.nextInt(Short.MAX_VALUE + 1);
    }

    /**
     * Judge if ID is Unique among all instances of game
     *
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
     * Method used to read file and create objects With Error handling
     */
    private void readCSV(int level) {
        if (level == 1) {
            readCSVLevelOne();
        } else if (level == 0) {
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
        } else {
            System.err.println("Wrong level!" + "\n");
        }
    }

    /**
     * Method used to read file for level 1
     */
    private void readCSVLevelOne() {
        try (BufferedReader br = new BufferedReader(new FileReader("res/level1.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String type = data[0];
                double x = Double.parseDouble(data[1]);
                double y = Double.parseDouble(data[2]);
                switch (type) {
                    case "Player":
                        gameManager_L1.setPlayer_L1(x, y, gameManager_L1);
                        break;
                    case "GhostRed":
                    case "GhostBlue":
                    case "GhostGreen":
                    case "GhostPink":
                        ghostList_L1.add(new Ghost(x, y, this.gameManager_L1, type));
                        break;
                    case "Wall":
                        wallList_L1.add(new Wall(x, y));
                        break;
                    case "Cherry":
                    case "Dot":
                    case "Pellet":
                        dotList_L1.add(new Dot(x, y, gameManager_L1, type));
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

    /**
     * get level 0 manager
     *
     * @return bonded level 0 manager
     */
    public Ghost[] getGhostList_L0() {
        return ghostList_L0;
    }

    /**
     * get level 0 WallList
     *
     * @return level 0 WallList
     */
    public Wall[] getWallList_L0() {
        return wallList_L0;
    }

    /**
     * get level 0 DotList
     *
     * @return level 0 DotList
     */
    public Dot[] getDotList_L0() {
        return dotList_L0;
    }

    /**
     * get level 1 GhostList
     *
     * @return level 1 GhostList
     */
    public List<Ghost> getGhostList_L1() {
        return ghostList_L1;
    }

    /**
     * get level 1 DotList
     *
     * @return level 1 DotList
     */
    public List<Dot> getDotList_L1() {
        return dotList_L1;
    }

    /**
     * get level 1 WallList
     *
     * @return level 1 WallList
     */
    public List<Wall> getWallList_L1() {
        return wallList_L1;
    }

    /**
     * check if the access of logic manager to the game is valid(with same ID). Please ignore these stuff since no dup
     *
     * @param lgc logic manager to be checked
     */
    private boolean checkAccess(Object lgc) {
        if (lgc instanceof ShadowPacLogic_L0) {
            if (((ShadowPacLogic_L0) lgc).getPID() != this.getPID()) {
                System.err.println("Unauthorized access:" + ((ShadowPacLogic_L0) lgc).getPID() + "\n");
                return false;
            }
        } else if (lgc instanceof ShadowPacLogic_L1) {
            if (((ShadowPacLogic_L1) lgc).getPID() != this.getPID()) {
                System.err.println("Unauthorized access:" + ((ShadowPacLogic_L1) lgc).getPID() + "\n");
                return false;
            }
        } else return false;
        return true;
    }

    /**
     * V2.3 prevent alien gameLogic from change game status
     *
     * @param lgc ShadowPacLogic for verification
     */
    public void setGameStageLOSE(Object lgc) {
        if (checkAccess(lgc)) stage = ShadowPac.gameStage.Lose;
    }

    public void setGameStageWIN(ShadowPacLogic_L0 lgc) {
        if (checkAccess(lgc)) stage = gameStage.LEVEL_COMPLETE;
    }

    public void setGameStageWIN(ShadowPacLogic_L1 lgc) {
        if (checkAccess(lgc)) stage = gameStage.Success;
    }


    /**
     * Updates the game state based on the user's input.
     * If the user presses the ESCAPE key, the window will be closed.
     * If the game state is set to Welcome, a welcome message and instructions are displayed.
     * The user must press the SPACE key to start the game.
     * If the game state is set to Gaming, the player's score & Lives remaining are displayed, and the player and game objects are drawn.
     * If the game state is set to Success or Lose, a message is displayed with the appropriate outcome of the game.
     * The user can also press the SPACE key to close the "Success or Lose" UI.(Not mentioned as Projcet Spec requirement)
     *
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
        if (input.wasPressed(Keys.LEFT_CTRL)) {
            stage = gameStage.GamingL1;
            gameManager_L1.getPlayer().setLife(3);
        }

        BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
        if (this.stage == gameStage.Welcome) {
            updateWelcome(input);
        }
        if (this.stage == gameStage.GamingL0) {
            updateGamingL0(input);
        }
        if (this.stage == gameStage.LEVEL_COMPLETE) {
            updateLevel_Complete();
        }
        if (this.stage == gameStage.L1Welcome) {
            updateLevel1_Welcome(input);
        }

        if (this.stage == gameStage.GamingL1) {
            updateGamingL1(input);
        }

        if (this.stage == gameStage.Success) {
            updateSuccess(input);
        }
        if (this.stage == gameStage.Lose) {
            updateLose(input);
        }
    }

    /**
     * The first Welcome after game initialized
     * if space is pressed then skip this part and get into GameL0
     *
     * @param input IO input from keyboard, mouse etc., defined in Bagel
     */
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

    /**
     * The second Welcome after Level 0 complete scene finished
     * if space is pressed then skip this part and get into GameL1
     *
     * @param input IO input from keyboard, mouse etc., defined in Bagel
     */
    private void updateLevel1_Welcome(Input input) {
        ShowMessage SM_PRESS_SPACE_TO_START = new ShowMessage("PRESS SPACE TO START", 200, 350, 40);
        SM_PRESS_SPACE_TO_START.Show();
        ShowMessage SM_USE_ARROW_KEYS_TO_MOVE = new ShowMessage("USE ARROW KEYS TO MOVE", 190, 406, 40);
        SM_USE_ARROW_KEYS_TO_MOVE.Show();
        ShowMessage SM_EAT_PELLET = new ShowMessage("EAT THE PELLET TO ATTACK", 160, 462, 40);
        SM_EAT_PELLET.Show();


        if (input.wasPressed(Keys.SPACE)) {
            stage = gameStage.GamingL1;
        }

    }

    /**
     * This is the core method to implement the level0 game interface, including:
     * Detect W pressed, jump to LEVEL_COMPLETE stage
     * Displaying Score, Life according to instantaneous value
     * Draw all units in traversals
     * Conduct player's checking the surroundings through the level0 game manager
     *
     * @param input IO input from keyboard, mouse etc., defined in Bagel
     */
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
            gst.Draw();
        }
        for (Wall wl : wallList_L0) {
            wl.DrawFixUnit();
        }
        for (Dot dt : dotList_L0) {
            dt.DrawFixUnit();
        }
        gameManager_L0.letPlayerCheckAround();
    }

    /**
     * This is the core method to implement the level1 game interface, including:
     * Detect W pressed, skip the level and win at once
     * Displaying Score, Life according to instantaneous value
     * Draw all units in traversals
     * Conduct player's checking the surroundings through the level1 game manager
     * Update and count for Frenzy frames ,if Frenzy time over then reset to Normal mode.
     *
     * @param input IO input from keyboard, mouse etc., defined in Bagel
     */
    private void updateGamingL1(Input input) {
        if (input.wasPressed(Keys.W)) {
            stage = gameStage.Success;
        }
        if (isFrenzy) {
            if (++counter_Frenzy >= 1000) {
                System.out.println("Frenzy Mode End!");
                counter_Frenzy = 0;
                isFrenzy = false;
                for (Ghost gh : gameManager_L1.getGhostList()) {
                    if (gh.getHidden()) {
                        gh.setHidden(false);
                        gh.reset();
                    }
                }
            }
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
        gameManager_L1.letPlayerCheckAround();
    }

    /**
     * The final win scene
     * if space is pressed then close the window
     *
     * @param input IO input from keyboard, mouse etc., defined in Bagel
     */

    private void updateSuccess(Input input) {
        ShowMessage SM_WELL_DONE = new ShowMessage("WELL DONE!",
                MID_WIDTH - 4 * ShowMessage.getSpecificFontsize(), MID_HEIGHT + ShowMessage.getSpecificFontsize() / 2.0);
        SM_WELL_DONE.Show();
        if (input.wasPressed(Keys.SPACE)) {
            Window.close();
        }
    }

    /**
     * The Level_Complete scene after Level 0 complete
     * display for 300 frames, counted by private int counter_LevelComplete
     */

    private void updateLevel_Complete() {
        ShowMessage SM_LEVELCOMPLETE = new ShowMessage("LEVEL COMPLETE!",
                MID_WIDTH - 5.75 * ShowMessage.getSpecificFontsize(), MID_HEIGHT + ShowMessage.getSpecificFontsize() / 2.0);
        SM_LEVELCOMPLETE.Show();
        ++counter_LevelComplete;
        if (counter_LevelComplete == 300) {
            stage = gameStage.L1Welcome;
        }
    }

    /**
     * The defeated scene
     * if space is pressed then close the window
     *
     * @param input IO input from keyboard, mouse etc., defined in Bagel
     */

    private void updateLose(Input input) {
        ShowMessage SM_GAME_OVER = new ShowMessage("GAME OVER!",
                MID_WIDTH - 4 * ShowMessage.getSpecificFontsize(), MID_HEIGHT + ShowMessage.getSpecificFontsize() / 2.0);
        SM_GAME_OVER.Show();
        if (input.wasPressed(Keys.SPACE)) {
            Window.close();
        }
    }

    /**
     * get the on time game mode
     * @return true for frenzy ,false for not
     */
    public boolean getisFrenzy() {
        return isFrenzy;
    }

    /**
     * Game progress identifier, used to determine the drawing scene
     */
    private enum gameStage {
        Welcome, GamingL0, LEVEL_COMPLETE, L1Welcome, GamingL1, Lose, Success
    }
}

