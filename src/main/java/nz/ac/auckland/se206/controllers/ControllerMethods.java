package nz.ac.auckland.se206.controllers;

import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppScene;

/** This class contains logic that is shared between various controllers. */
public class ControllerMethods {

  // String properties for the timer, task and hints
  protected static StringProperty displayTime = new SimpleStringProperty(GameState.timerString);
  protected static StringProperty displayTask = new SimpleStringProperty(GameState.taskString);
  protected static StringProperty displayHints = new SimpleStringProperty(GameState.hintString);

  // Text fill property for the timer label
  protected static ObjectProperty<Paint> timerTextFill =
      new SimpleObjectProperty<Paint>(Color.BLACK);

  // String properties for the game finished scene
  protected static StringProperty titleMessage = new SimpleStringProperty();
  protected static StringProperty subtitleMessage = new SimpleStringProperty();

  // Object properties for all the images in the inventory
  protected static ObjectProperty<Image> fishingRodIconImageProperty =
      new SimpleObjectProperty<>(null);
  protected static ObjectProperty<Image> axeIconImageProperty = new SimpleObjectProperty<>(null);
  protected static ObjectProperty<Image> fishIconImageProperty = new SimpleObjectProperty<>(null);
  protected static ObjectProperty<Image> planksIconImageProperty = new SimpleObjectProperty<>(null);
  protected static ObjectProperty<Image> blueOrbImageProperty = new SimpleObjectProperty<>(null);
  protected static ObjectProperty<Image> greenOrbImageProperty = new SimpleObjectProperty<>(null);
  protected static ObjectProperty<Image> redOrbImageProperty = new SimpleObjectProperty<>(null);

  // Object properties for background image behind the chat
  protected static ObjectProperty<Image> backgroundImageProperty = new SimpleObjectProperty<>(null);
  protected static ObjectProperty<Image> layerOneProperty = new SimpleObjectProperty<>(null);
  protected static ObjectProperty<Image> layerTwoProperty = new SimpleObjectProperty<>(null);
  protected static ObjectProperty<Image> layerThreeProperty = new SimpleObjectProperty<>(null);
  protected static ObjectProperty<Image> layerFourProperty = new SimpleObjectProperty<>(null);
  protected static ObjectProperty<Image> layerFiveProperty = new SimpleObjectProperty<>(null);
  protected static ObjectProperty<Image> layerSixProperty = new SimpleObjectProperty<>(null);

  // Object properties for trees images
  protected static ObjectProperty<Image> treesImageProperty = new SimpleObjectProperty<>(null);
  protected static ObjectProperty<Image> treesOutlineImageProperty =
      new SimpleObjectProperty<>(null);
  protected static ObjectProperty<Image> miniTreesImageProperty = new SimpleObjectProperty<>(null);
  protected static ObjectProperty<Image> treeHitImageProperty = new SimpleObjectProperty<>(null);

  // Object properties for portal images
  protected static ObjectProperty<Image> portalImageProperty = new SimpleObjectProperty<>(null);
  protected static ObjectProperty<Image> portalOutlineImageProperty =
      new SimpleObjectProperty<>(null);

  // Instance variables to be accessible to all controllers
  protected static Timer timer = new Timer(true);

  /**
   * This method is called when the restart button is pressed. It clears the hashmap of stored
   * scenes and resets all game states.
   */
  public static void restartGame() {

    // Cancel timer:
    timer.cancel();

    // Reset inventory items:
    fishingRodIconImageProperty.set(null);
    axeIconImageProperty.set(null);
    fishIconImageProperty.set(null);
    planksIconImageProperty.set(null);
    blueOrbImageProperty.set(null);
    greenOrbImageProperty.set(null);
    redOrbImageProperty.set(null);

    // Reset timer label text fill
    timerTextFill.set(Color.BLACK);

    Task<Void> restartTask =
        new Task<Void>() {
          @Override
          protected Void call() throws Exception {
            // Remove current scenes from the hashmap
            SceneManager.clearHashMap();

            // Re-initialize game scenes and reset game states
            App.initializeGame();

            return null;
          }
        };

    // On succeeded, set the scene to the start scene
    restartTask.setOnSucceeded(
        (event) -> {
          // Make new timer object
          timer = new Timer(true);

          // Remove background images from both setting and game finished scenes
          restartImages();

          App.setScene(AppScene.OPTIONS);
        });

    // Run the task in a new thread
    Thread restartThread = new Thread(restartTask);
    restartThread.start();
  }

  /**
   * Removes and disables the background images from the settings and game finished scenes when
   * restarting.
   */
  private static void restartImages() {
    // get the settings controller
    App.getSettingsController().disableRestartImages();
    App.getGameFinishedController().disableRestartingImages();
  }

  // The duration of the game in seconds
  protected int count;

  // Inventory Items
  @FXML private ImageView fishingRodIcon = new ImageView();
  @FXML private ImageView axeIcon = new ImageView();
  @FXML private ImageView fishIcon = new ImageView();
  @FXML private ImageView planksIcon = new ImageView();
  @FXML private ImageView blueOrb = new ImageView();
  @FXML private ImageView greenOrb = new ImageView();
  @FXML private ImageView redOrb = new ImageView();

  /** Starts the count down timer for game. */
  protected void startTimer() {
    // Retrieve the duration of the game
    count = GameState.timerCount;

    timer.scheduleAtFixedRate(
        new TimerTask() {
          @Override
          public void run() {

            // If the user has escaped from the room, cancel timer
            if (GameState.isRoomEscaped) {
              timer.cancel();
            } else {
              // Decrement the count and update the timer.
              count--;
              GameState.timeTaken++;

              // If the timer reaches 0, cancel the timer and switch scene to game over.
              if (count == 0) {
                timer.cancel();
                gameOver();
              } else if (count == 60) {
                timerTextFill.set(Color.RED);
              }
              updateTimer();
            }
          }
        },
        0,
        1000);
  }

  /** Updates the timer and checks if game over. */
  private void updateTimer() {
    String extra = (count % 60) < 10 ? "0" : "";
    String time = (count / 60) + ":" + extra + (count % 60);
    Platform.runLater(
        () -> {
          displayTime.setValue("Time Left: " + time);
        });
  }

  /** Changes scene to game over scene. */
  protected void gameOver() {
    setMessages("Game Over!", "You ran out of time!");
    App.setScene(AppScene.GAMEFINISHED);
  }

  /** Updates the task label based on current game state. */
  protected void updateTask() {
    if (!GameState.isRiddleFound) {
      // If the riddle has not been found by clicking on the book
      GameState.taskString = "Task: Search for a riddle";

    } else if (!GameState.isRiddleResolved) {
      // If the riddle has not been solved
      GameState.taskString = "Task: Try to solve the riddle";

    } else if (GameState.isRug && !GameState.isRoomOrbCollected) {
      // If the room orb has not been collected under the rug
      GameState.taskString = "Task: Have a look under the rug";

    } else if (GameState.isCabinet && !GameState.isRoomOrbCollected) {
      // If the room orb has not been collected in the cabinet
      GameState.taskString = "Task: Check the cabinet";

    } else if ((GameState.isForestFishing && !GameState.isFishingRodTaken)
        || (GameState.isForestTreeChopping && !GameState.isAxeTaken)) {
      // If the fishing rod or axe has not been taken
      GameState.taskString = "Task: Try exploring other rooms";

    } else if (GameState.isFishingRodTaken && !GameState.isForestGameCompleted) {
      // If the fishing rod has been taken but the forrest game has not been completed
      GameState.taskString = "Task: Go fishing with the rod";

    } else if (GameState.isAxeTaken && !GameState.isForestGameCompleted) {
      // If the axe has been taken but the forrest game has not been completed
      GameState.taskString = "Task: Chop trees with the axe";

    } else if (GameState.isForestTreeChopping
        && GameState.isForestGameCompleted
        && !GameState.isForestOrbCollected) {
      // If the forest orb has not been collected
      GameState.taskString = "Task: Don't forget the green orb";

    } else if (GameState.isLavaDragon && !GameState.isLavaGameCompleted) {
      // If the lava game has not been completed - dragon has not been distracted
      GameState.taskString = "Task: Distract dragon with food";

    } else if (GameState.isLavaBridge && !GameState.isLavaGameCompleted) {
      // If the lava game has not been completed - bridge has not been built
      GameState.taskString = "Task: Use the planks you made";

    } else if (GameState.isLavaGameCompleted && !GameState.isChestFound) {
      // If the chest has not been found
      GameState.taskString = "Task: What's inside the castle?";

    } else if (GameState.isLavaGameCompleted && !GameState.isCodeFound) {
      // If the code has not been found
      GameState.taskString = "Task: Search for a code";

    } else if (GameState.isLavaGameCompleted && !GameState.isChestUnlocked) {
      // If the chest has not been unlocked
      GameState.taskString = "Task: Try opening the chest";

    } else if (!GameState.isCastleOrbCollected) {
      // If the castle orb has not been collected
      GameState.taskString = "Task: Don't forget the red orb";

    } else if (GameState.isRoomOrbCollected
        && GameState.isForestOrbCollected
        && GameState.isCastleOrbCollected
        && !GameState.isOrbsPlaced) {
      // If the orbs have not been placed in the terminal
      GameState.taskString = "Task: Place orbs in the terminal";

    } else if (GameState.isPortalOpen && !GameState.isRoomEscaped) {
      // If the portal has not been entered
      GameState.taskString = "Task: Enter the portal!";
    }

    // Set the task to the current task
    displayTask.setValue(GameState.taskString);
  }

  /**
   * Updates the hints available/remaining depending on the difficulty of the game as selected by
   * the player.
   */
  protected void updateHintsRemaining() {
    if (GameState.isEasySelected) {
      displayHints.setValue("Hints: Infinite");
    } else if (GameState.isMediumSelected) {
      displayHints.setValue("Hints: " + GameState.hintCount);
    } else if (GameState.isHardSelected) {
      displayHints.setValue("Hints: None");
    }
  }

  /**
   * Sets the messages to be displayed on the game finished scene.
   *
   * @param message The title message.
   * @param subMessage The subtitle message.
   */
  protected void setMessages(String message, String subMessage) {
    titleMessage.setValue(message);
    subtitleMessage.setValue(subMessage);
  }

  /** Binds inventory images to their image properties. */
  protected void bindInventory() {
    // Bind the inventory images to their image properties
    fishingRodIcon.imageProperty().bind(fishingRodIconImageProperty);
    axeIcon.imageProperty().bind(axeIconImageProperty);
    fishIcon.imageProperty().bind(fishIconImageProperty);
    planksIcon.imageProperty().bind(planksIconImageProperty);
    blueOrb.imageProperty().bind(blueOrbImageProperty);
    greenOrb.imageProperty().bind(greenOrbImageProperty);
    redOrb.imageProperty().bind(redOrbImageProperty);
  }

  /** Binds inventory images to their image properties without the orbs. */
  protected void bindInventoryWithoutOrbs() {
    // Bind the inventory images to their image properties
    fishingRodIcon.imageProperty().bind(fishingRodIconImageProperty);
    axeIcon.imageProperty().bind(axeIconImageProperty);
    fishIcon.imageProperty().bind(fishIconImageProperty);
    planksIcon.imageProperty().bind(planksIconImageProperty);
  }

  /**
   * Retrieves the fishing rod image for it to be displayed in the inventory.
   *
   * @return The fishing rod image.
   */
  protected ImageView getFishingRodIcon() {
    return fishingRodIcon;
  }

  /**
   * Retrieves the axe image for it to be displayed in the inventory.
   *
   * @return The axe image.
   */
  protected ImageView getAxeIcon() {
    return axeIcon;
  }

  /**
   * Retrieves the fish image for it to be displayed in the inventory.
   *
   * @return The fish image.
   */
  protected ImageView getFishIcon() {
    return fishIcon;
  }

  /**
   * Retrieves the planks image for it to be displayed in the inventory.
   *
   * @return The planks image.
   */
  protected ImageView getPlanksIcon() {
    return planksIcon;
  }

  /**
   * Retrieves the blue orb image for it to be displayed in the inventory.
   *
   * @return The blue orb image.
   */
  protected ImageView getBlueOrb() {
    return blueOrb;
  }

  /**
   * Retrieves the green orb image for it to be displayed in the inventory.
   *
   * @return The green orb image.
   */
  protected ImageView getGreenOrb() {
    return greenOrb;
  }

  /**
   * Retrieves the red orb image for it to be displayed in the inventory.
   *
   * @return The red orb image.
   */
  protected ImageView getRedOrb() {
    return redOrb;
  }

  /** Method for adding the fishing rod to inventory. */
  protected void findFishingRod() {
    fishingRodIcon.setLayoutX(292);
    fishingRodIcon.setLayoutY(566);
    fishingRodIcon.setFitHeight(30);
    fishingRodIcon.setFitWidth(30);
    Image fishingRodImage = new Image(getClass().getResourceAsStream("/images/fishingRodIcon.png"));
    fishingRodIconImageProperty.set(fishingRodImage);
  }

  /** Method for adding the axe to inventory. */
  protected void findAxe() {
    axeIcon.setLayoutX(296);
    axeIcon.setLayoutY(566);
    axeIcon.setFitHeight(30);
    axeIcon.setFitWidth(30);
    Image axeImage = new Image(getClass().getResourceAsStream("/images/axeIcon.png"));
    axeIconImageProperty.set(axeImage);
  }

  /** Method for adding the fish to inventory. */
  protected void findFish() {
    fishIcon.setLayoutX(330);
    fishIcon.setLayoutY(566);
    fishIcon.setFitHeight(30);
    fishIcon.setFitWidth(30);
    Image fishImage = new Image(getClass().getResourceAsStream("/images/fishIcon.png"));
    fishIconImageProperty.set(fishImage);
  }

  /** Method for adding the fish to inventory. */
  protected void findPlanks() {
    planksIcon.setLayoutX(334);
    planksIcon.setLayoutY(566);
    planksIcon.setFitHeight(30);
    planksIcon.setFitWidth(30);
    Image planksImage = new Image(getClass().getResourceAsStream("/images/planksIcon.png"));
    planksIconImageProperty.set(planksImage);
  }

  /** Method for adding the blue orb to inventory. */
  protected void findBlueOrb() {
    blueOrb.setLayoutX(416.75);
    blueOrb.setLayoutY(570.75);
    blueOrb.setFitHeight(21);
    blueOrb.setFitWidth(21);
    Image blueOrbImage = new Image(getClass().getResourceAsStream("/images/blueOrb.png"));
    blueOrbImageProperty.set(blueOrbImage);
  }

  /** Method for adding the green orb to inventory. */
  protected void findGreenOrb() {
    greenOrb.setLayoutX(450.25);
    greenOrb.setLayoutY(570.75);
    greenOrb.setFitHeight(21);
    greenOrb.setFitWidth(21);
    Image greenOrbImage = new Image(getClass().getResourceAsStream("/images/greenOrb.png"));
    greenOrbImageProperty.set(greenOrbImage);
  }

  /** Method for adding the red orb to inventory. */
  protected void findRedOrb() {
    redOrb.setLayoutX(482.75);
    redOrb.setLayoutY(570.25);
    redOrb.setFitHeight(21);
    redOrb.setFitWidth(21);
    Image redOrbImage = new Image(getClass().getResourceAsStream("/images/redOrb.png"));
    redOrbImageProperty.set(redOrbImage);
  }

  /** Method for removing the fish from the inventory. */
  protected void removeFish() {
    fishIconImageProperty.set(null);
  }

  /** Method for removing the planks from the inventory. */
  protected void removePlanks() {
    planksIconImageProperty.set(null);
  }

  /** Method for removing the orbs from the inventory. */
  protected void removeOrbs() {
    blueOrbImageProperty.set(null);
    greenOrbImageProperty.set(null);
    redOrbImageProperty.set(null);
  }

  /** Method for disabling all other layer other than the main background for GPT. */
  protected void hideAllLayers() {
    layerOneProperty.set(null);
    layerTwoProperty.set(null);
    layerThreeProperty.set(null);
    layerFourProperty.set(null);
    layerFiveProperty.set(null);
    layerSixProperty.set(null);
  }

  /** Method for adding the bottom blue orb behind GPT in terminal. */
  protected void bottomBlueOrbOpacity() {
    layerOneProperty.set(new Image(getClass().getResourceAsStream("/images/bottomBlueOrb.png")));
  }

  /** Method for adding the bottom green orb behind GPT in terminal. */
  protected void bottomGreenOrbOpacity() {
    layerTwoProperty.set(new Image(getClass().getResourceAsStream("/images/bottomGreenOrb.png")));
  }

  /** Method for adding the bottom red orb behind GPT in terminal. */
  protected void bottomRedOrbOpacity() {
    layerThreeProperty.set(new Image(getClass().getResourceAsStream("/images/bottomRedOrb.png")));
  }

  /** Method for adding the top blue orb behind GPT in terminal. */
  protected void topBlueOrbOpacity() {
    layerFourProperty.set(new Image(getClass().getResourceAsStream("/images/topBlueOrb.png")));
    layerOneProperty.set(null);
  }

  /** Method for adding the top green orb behind GPT in terminal. */
  protected void topGreenOrbOpacity() {
    layerFiveProperty.set(new Image(getClass().getResourceAsStream("/images/topGreenOrb.png")));
    layerTwoProperty.set(null);
  }

  /** Method for adding the top red orb behind GPT in terminal. */
  protected void topRedOrbOpacity() {
    layerSixProperty.set(new Image(getClass().getResourceAsStream("/images/topRedOrb.png")));
    layerThreeProperty.set(null);
  }

  /** Method for adding the red orb behind GPT. */
  protected void redOrbInChestOpacity() {
    layerOneProperty.set(new Image(getClass().getResourceAsStream("/images/redOrbInChest.png")));
  }

  /** Method for adding the bottom plank behind GPT. */
  protected void bridgeMiniPlankOneOpacity() {
    layerOneProperty.set(new Image(getClass().getResourceAsStream("/images/bottomPlank.png")));
  }

  /** Method for adding the middle plank behind GPT. */
  protected void bridgeMiniPlankTwoOpacity() {
    layerTwoProperty.set(new Image(getClass().getResourceAsStream("/images/middlePlank.png")));
  }

  /** Method for adding the top plank behind GPT. */
  protected void bridgeMiniPlankThreeOpacity() {
    layerThreeProperty.set(new Image(getClass().getResourceAsStream("/images/topPlank.png")));
  }

  /** Enables axe image depending on game state. */
  protected void setForestAxeOpacity() {
    backgroundImageProperty.set(new Image(getClass().getResourceAsStream("/images/forestAxe.png")));
  }

  /** Enables fishing rod image depending on game state. */
  protected void setForestRodOpacity() {
    backgroundImageProperty.set(new Image(getClass().getResourceAsStream("/images/forestRod.png")));
  }

  /** Enables pink trees and axe image depending on game state. */
  protected void setPinkForestAxeOpacity() {
    backgroundImageProperty.set(
        new Image(getClass().getResourceAsStream("/images/forestAxePink.png")));
  }

  /** Enables pink trees and fishing rod image depending on game state. */
  protected void setPinkForestRodOpacity() {
    backgroundImageProperty.set(
        new Image(getClass().getResourceAsStream("/images/forestRodPink.png")));
  }

  /** Enables forest tress removed image depending on game state. */
  protected void setForestTreesRemovedOpacity() {
    backgroundImageProperty.set(
        new Image(getClass().getResourceAsStream("/images/forestTreesRemoved.png")));
  }

  /** Enables main room image when map has not been removed. */
  protected void setMainMapOpacity() {
    backgroundImageProperty.set(new Image(getClass().getResourceAsStream("/images/mainMap.png")));
  }

  /** Enables main room image when map has been removed from the wall. */
  protected void setMainMapRemovedOpacity() {
    backgroundImageProperty.set(
        new Image(getClass().getResourceAsStream("/images/mainMapRemoved.png")));
  }

  /** Enables main room image for the map when the lights have been turned off. */
  protected void setMainDarkOpacity() {
    backgroundImageProperty.set(
        new Image(getClass().getResourceAsStream("/images/mainDarkMap.png")));
  }

  /** Enables main room image for the map has been removed when the lights have been turned off. */
  protected void setMainDarkMapRemovedOpacity() {
    backgroundImageProperty.set(
        new Image(getClass().getResourceAsStream("/images/mainDarkMapRemoved.png")));
  }

  /** Enables main room image for when the cabinet has been opened. */
  protected void setMainCabinetMapOpacity() {
    backgroundImageProperty.set(
        new Image(getClass().getResourceAsStream("/images/mainCabinetOpenMap.png")));
  }

  /** Enables main room image for when the cabinet has been opened and the map has been removed. */
  protected void setMainCabinetMapRemovedOpacity() {
    backgroundImageProperty.set(
        new Image(getClass().getResourceAsStream("/images/mainCabinetOpenMapRemoved.png")));
  }

  /**
   * Enables main room image for when the cabinet has been opened and the lights have been turned
   * off.
   */
  protected void setMainDarkCabinetMapOpacity() {
    backgroundImageProperty.set(
        new Image(getClass().getResourceAsStream("/images/mainCabinetOpenDarkMap.png")));
  }

  /**
   * Enables main room image for when the cabinet has been opened, map has been removed and the
   * lights have been turned off.
   */
  protected void setMainDarkCabinetMapRemovedOpacity() {
    backgroundImageProperty.set(
        new Image(getClass().getResourceAsStream("/images/mainCabinetDarkMapRemoved.png")));
  }

  /** Enables the room image for when the rug has been removed. */
  protected void setMainRugMapOpacity() {
    backgroundImageProperty.set(
        new Image(getClass().getResourceAsStream("/images/mainRugMap.png")));
  }

  /** Enables the room image for when the rug and the map has been removed. */
  protected void setMainRugMapRemovedOpacity() {
    backgroundImageProperty.set(
        new Image(getClass().getResourceAsStream("/images/mainRugMapRemoved.png")));
  }

  /** Enables the room image for the rug has been removed and the lights have been turned off. */
  protected void setMainDarkRugMapOpacity() {
    backgroundImageProperty.set(
        new Image(getClass().getResourceAsStream("/images/mainDarkRugMap.png")));
  }

  /**
   * Enables the room image for the the rug and the map has been removed and the lights have been
   * turned off.
   */
  protected void setMainDarkRugMapRemovedOpacity() {
    backgroundImageProperty.set(
        new Image(getClass().getResourceAsStream("/images/mainDarkRugMapRemoved.png")));
  }

  /** Enables the lava room image for when the dragon is present. */
  protected void setLavaDragonOpacity() {
    backgroundImageProperty.set(
        new Image(getClass().getResourceAsStream("/images/lavaDragon.png")));
  }

  /** Enables the lava room image for when the dragon has been removed. */
  protected void setLavaNoDragonOpacity() {
    backgroundImageProperty.set(
        new Image(getClass().getResourceAsStream("/images/lavaNoDragon.png")));
  }

  /** Enables the wooden fixed bridge image when the bridge has been fixed. */
  protected void setFixedBridgeOpacity() {
    backgroundImageProperty.set(
        new Image(getClass().getResourceAsStream("/images/lavaFixedBridge.png")));
  }

  /** Enables the lava room image for when the blue dragon is present. */
  protected void setBlueLavaDragonOpacity() {
    backgroundImageProperty.set(
        new Image(getClass().getResourceAsStream("/images/lavaBlueDragon.png")));
  }

  /** Enables the broken bridge image when the bridge has not been fixed. */
  protected void setBrokenBridgeOpacity() {
    backgroundImageProperty.set(
        new Image(getClass().getResourceAsStream("/images/lavaBrokenBridge.png")));
  }

  /** Enables forest mini game image for when trees have not been chopped. */
  protected void setForestMiniOpacity() {
    backgroundImageProperty.set(
        new Image(getClass().getResourceAsStream("/images/forestMini.png")));
  }

  /** Enables pink forest mini game image for when trees have not been chopped. */
  protected void setPinkForestMiniOpacity() {
    backgroundImageProperty.set(
        new Image(getClass().getResourceAsStream("/images/forestMiniPink.png")));
  }

  /** Enables forest mini game image for when the trees have been chopped. */
  protected void setForestMiniTreesRemovedOpacity() {
    backgroundImageProperty.set(
        new Image(getClass().getResourceAsStream("/images/forestMiniTreesRemoved.png")));
  }

  /** Enables fishing mini game image. */
  protected void setFishingMiniOpacity() {
    backgroundImageProperty.set(
        new Image(getClass().getResourceAsStream("/images/fishingMini.png")));
  }

  /** Enables castle room image for when the chest is locked. */
  protected void setChestMiniOpacity() {
    backgroundImageProperty.set(new Image(getClass().getResourceAsStream("/images/chestMini.png")));
  }

  /** Enables castle room image for when the chest is unlocked. */
  protected void setOpenChestMiniOpacity() {
    backgroundImageProperty.set(
        new Image(getClass().getResourceAsStream("/images/chestMiniOpened.png")));
  }

  /** Enables and sets the opacitity of the orbs for the terminal mini-game image. */
  protected void setOrbMiniOpacity() {
    backgroundImageProperty.set(new Image(getClass().getResourceAsStream("/images/orbMini.png")));
  }

  /** Enables and sets the opacitity of the orbs for the terminal mini-game image when completed. */
  protected void setCompletedOrbMiniOpacity() {
    backgroundImageProperty.set(
        new Image(getClass().getResourceAsStream("/images/orbMiniCompleted.png")));
  }

  /** Enables the normal bridge mini-game initial state image. */
  protected void setBridgeMiniOpacity() {
    backgroundImageProperty.set(
        new Image(getClass().getResourceAsStream("/images/bridgeMini.png")));
  }

  /** Enables the fixed bridge mini-game state image. */
  protected void setFixedBridgeMiniOpacity() {
    backgroundImageProperty.set(
        new Image(getClass().getResourceAsStream("/images/bridgeMiniCompleted.png")));
  }

  /** Sets the image for the portal when hovered over. */
  protected void initialisePortal() {
    portalImageProperty.set(new Image(getClass().getResourceAsStream("/images/portalFrame.gif")));
    portalOutlineImageProperty.set(
        new Image(getClass().getResourceAsStream("/images/portalFrameOutline.gif")));
  }

  /** Sets the images for the portal to be glowing. */
  protected void openPortal() {
    portalImageProperty.set(new Image(getClass().getResourceAsStream("/images/glowyPortal.gif")));
    portalOutlineImageProperty.set(
        new Image(getClass().getResourceAsStream("/images/glowyPortalOutline.gif")));
  }

  /** Sets the images for the trees to be green. */
  protected void initialiseGreenTrees() {
    // Handles the tree in the forest room.
    treesImageProperty.set(new Image(getClass().getResourceAsStream("/images/trees.png")));
    treesOutlineImageProperty.set(
        new Image(getClass().getResourceAsStream("/images/treesOutline.png")));
    // Handles the trees in the minigame.
    miniTreesImageProperty.set(new Image(getClass().getResourceAsStream("/images/miniTrees.png")));
    treeHitImageProperty.set(new Image(getClass().getResourceAsStream("/images/treeHitOne.png")));
  }

  /** Sets the images for the trees to be pink. */
  protected void initialisePinkTrees() {
    // Handles the tree in the forest room.
    treesImageProperty.set(new Image(getClass().getResourceAsStream("/images/treesPink.png")));
    treesOutlineImageProperty.set(
        new Image(getClass().getResourceAsStream("/images/treesPinkOutline.png")));
    // Handles the trees in the minigame.
    miniTreesImageProperty.set(
        new Image(getClass().getResourceAsStream("/images/miniTreesPink.png")));
    treeHitImageProperty.set(
        new Image(getClass().getResourceAsStream("/images/treeHitOnePink.png")));
  }
}
