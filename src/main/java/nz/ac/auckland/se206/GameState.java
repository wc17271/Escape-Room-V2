package nz.ac.auckland.se206;

import java.util.Random;
import nz.ac.auckland.se206.SceneManager.AppScene;

/** This class represents the state of the game. */
public class GameState {

  // Indicates which riddle has been selected:
  public static boolean isCabinet;
  public static boolean isRug;

  // Indicates if the riddle book is open:
  public static boolean isRiddleBookOpen;

  // Indicates which minigame has been selected:
  public static boolean isLavaBridge;
  public static boolean isForestTreeChopping;

  public static boolean isLavaDragon;
  public static boolean isDragonGone;
  public static boolean isForestFishing;
  public static boolean isTreesPink;

  // Indicates if the minigame has been completed:
  public static boolean isLavaGameCompleted;
  public static boolean isForestGameCompleted;

  // Indicates if light switch is on or off
  public static boolean isLightOn;

  // Indicates if map has been removed off the wall
  public static boolean isMapOnWall;

  // Indicates if the code has been found
  public static boolean isCodeFound;

  // Indicates if orbs have been collected:
  public static boolean isForestOrbCollected;
  public static boolean isCastleOrbCollected;
  public static boolean isRoomOrbCollected;

  // Indicates if all the orbs have been placed in the terminal
  public static boolean isOrbsPlaced;

  // Indicates if trees have been chopped down
  public static boolean isChopped;

  // Indicates if the axe has been taken
  public static boolean isAxeTaken;

  // Indicates if the fishing rod has been taken
  public static boolean isFishingRodTaken;

  // Indicates if the fish has been caught
  public static boolean isFishCaught;

  // Indicates if the chest has been found
  public static boolean isChestFound;

  // Indicates if the chest has been unlocked
  public static boolean isChestUnlocked;

  // Indicates if the riddle has been found
  public static boolean isRiddleFound;

  /** Indicates whether the riddle has been resolved. */
  public static boolean isRiddleResolved;

  // check if item as told by the riddle has been clicked - this will allow the TV to be accessed
  public static boolean itemClicked;

  // Indicated if the portal is open
  public static boolean isPortalOpen;

  // Check if the player has escaped
  public static boolean isRoomEscaped;

  // Difficulty and Time Selection (Default)
  public static boolean isEasySelected;
  public static boolean isMediumSelected;
  public static boolean isHardSelected;
  public static boolean isShortTimeSelected;
  public static boolean isMediumTimeSelected;
  public static boolean isLongTimeSelected;

  // Hints (Default)
  public static int hintCount;
  public static String hintString;

  // Timer (Default)
  public static int timerCount;
  public static String timerString;
  public static int timeTaken;

  public static String taskString;

  // Last game scene:
  public static AppScene lastScene;

  // Code digits:
  public static int firstDigit;
  public static int secondDigit;
  public static int thirdDigit;

  /** This method resets the game state to the default values. */
  public static void resetGameState() {

    // Set mini game states:
    int randomInt = new Random().nextInt(10);

    if (randomInt > 4) {
      isLavaBridge = true;
      isForestTreeChopping = true;
      isLavaDragon = false;
      isForestFishing = false;
      isDragonGone = true;
    } else {
      isLavaBridge = false;
      isForestTreeChopping = false;
      isLavaDragon = true;
      isForestFishing = true;
      isDragonGone = false;
    }

    // Indicates which riddle has been selected:
    isCabinet = false;
    isRug = false;

    // Indicates if the riddle book is open:
    isRiddleBookOpen = false;

    // Indicates if the minigame has been completed:
    isLavaGameCompleted = false;
    isForestGameCompleted = false;

    // Indicates if light switch is on or off
    isLightOn = true;

    // Indicates if map has been removed off the wall
    isMapOnWall = true;

    // Indicates if the code has been found
    isCodeFound = false;

    // Indicates if orbs have been collected:
    isForestOrbCollected = false;
    isCastleOrbCollected = false;
    isRoomOrbCollected = false;

    // Indicates if all the orbs have been placed in the terminal
    isOrbsPlaced = false;

    // Indicates if trees have been chopped down
    isChopped = false;

    // Indicates if the axe has been taken
    isAxeTaken = false;

    // Indicates if the fishing rod has been taken
    isFishingRodTaken = false;

    // Indicates if the fish has been caught
    isFishCaught = false;

    // Indicates if the chest has been found
    isChestFound = false;

    // Indicates if the chest has been unlocked
    isChestUnlocked = false;

    // Indicates if the riddle has been found
    isRiddleFound = false;

    // Indicates whether the riddle has been resolved. This is set to true when the GPT thing sees
    // correct.
    isRiddleResolved = false;

    // check if item as told by the riddle has been clicked - this will allow the TV to be accessed
    itemClicked = false;

    // Indicated if the portal is open
    isPortalOpen = false;

    // Check if the player has escaped
    isRoomEscaped = false;

    // Difficulty and Time Selection (Default)
    isEasySelected = false;
    isMediumSelected = true;
    isHardSelected = false;
    isShortTimeSelected = false;
    isMediumTimeSelected = true;
    isLongTimeSelected = false;

    // Hints (Default)
    hintCount = 5;
    hintString = "Hints: 5";

    // Timer (Default)
    timerCount = 240;
    timerString = "Time Left: 4:00";
    timeTaken = 0;

    taskString = "Task: Search for a riddle";

    // Last game scene:
    lastScene = null;

    // Code digits:
    firstDigit = new Random().nextInt(10);
    secondDigit = new Random().nextInt(10);
    thirdDigit = new Random().nextInt(10);
  }
}
