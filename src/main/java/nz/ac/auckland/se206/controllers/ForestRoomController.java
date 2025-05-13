package nz.ac.auckland.se206.controllers;

import java.util.Random;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.NotificationBuilder;
import nz.ac.auckland.se206.SceneManager.AppScene;
import org.controlsfx.control.Notifications;

/**
 * This class handles the logic for the forest room where players are able to play the fishing
 * mini-game or the tree chopping mini-game.
 */
public class ForestRoomController extends ControllerMethods {
  @FXML private ImageView leftArrow;
  @FXML private ImageView leftArrowHover;
  @FXML private ImageView leftArrowPressed;
  @FXML private Label lblTimer;
  @FXML private Label lblTask;
  @FXML private Label lblHints;
  @FXML private ImageView rock;
  @FXML private ImageView rockOutline;
  @FXML private ImageView fishingRod;
  @FXML private ImageView fishingRodOutline;
  @FXML private ImageView dock;
  @FXML private ImageView dockOutline;
  @FXML private ImageView trees;
  @FXML private ImageView treesOutline;
  @FXML private ImageView treesRemoved;
  @FXML private ImageView treesRemovedOutline;
  @FXML private ImageView axe;
  @FXML private ImageView axeOutline;
  @FXML private ImageView axeRemoved;
  @FXML private ImageView axeRemovedOutline;

  // Game Master
  @FXML private ImageView gameMasterDefault;
  @FXML private ImageView gameMasterChat;

  // Inventory Items
  @FXML private ImageView fishingRodIcon;
  @FXML private ImageView axeIcon;
  @FXML private ImageView fishIcon;
  @FXML private ImageView planksIcon;
  @FXML private ImageView blueOrb;
  @FXML private ImageView greenOrb;
  @FXML private ImageView redOrb;

  @FXML private Polygon treesMini;
  @FXML private Polygon choppedTrees;
  @FXML private Polygon axeGrab;
  @FXML private Polygon emptyLog;
  @FXML private Polygon fishingMini;
  @FXML private Polygon dockWithoutRod;
  @FXML private ImageView helpOne;
  @FXML private ImageView helpTwo;
  @FXML private ImageView helpThree;
  @FXML private ImageView settingsOne;
  @FXML private ImageView settingsTwo;
  @FXML private ImageView settingsThree;

  /**
   * This method is called when the scene is first loaded. Timer labels, hints and items in the game
   * are initialized.
   */
  public void initialize() {
    // Bind the labels to the display values and styles
    lblTimer.textFillProperty().bind(ControllerMethods.timerTextFill);
    lblTimer.textProperty().bind(ControllerMethods.displayTime);
    lblTask.textProperty().bind(ControllerMethods.displayTask);
    lblHints.textProperty().bind(ControllerMethods.displayHints);

    // Initialize the inventory items
    fishingRodIcon = getFishingRodIcon();
    axeIcon = getAxeIcon();
    fishIcon = getFishIcon();
    planksIcon = getPlanksIcon();
    blueOrb = getBlueOrb();
    greenOrb = getGreenOrb();
    redOrb = getRedOrb();

    // Bind the inventory images to their image properties
    bindInventory();

    // Bind the trees image to its image property
    trees.imageProperty().bind(ControllerMethods.treesImageProperty);
    treesOutline.imageProperty().bind(ControllerMethods.treesOutlineImageProperty);

    int randomInt = new Random().nextInt(10);
    if (randomInt > 4) {
      initialiseGreenTrees();
      GameState.isTreesPink = false;
    } else {
      initialisePinkTrees();
      GameState.isTreesPink = true;
    }

    // Based on mini-game selected, either show dragon scenario or broken bridge scenario:
    if (GameState.isForestTreeChopping) {
      // Mini-game 1: Trees need to be chopped - Disable and hide fishing rod components

      // Disable fishing rod polygon and enable empty dock polygon
      fishingMini.setDisable(true);
      dockWithoutRod.setDisable(false);

      // Make fishing rod image invisible and empty dock visible
      fishingRod.setOpacity(0);
      dock.setOpacity(1);
    } else {
      // Mini-game 2: Fish needs to be caught - Disable and hide tree chopping components

      // Disable axe polygon and enable empty log polygon
      axeGrab.setDisable(true);
      emptyLog.setDisable(false);

      // Make axe image invisible and empty stump image visible
      axe.setOpacity(0);
      axeRemoved.setOpacity(1);
    }
  }

  /**
   * This method is called when the help button is hovered over, placing a shadow over the button.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void onHelpHover(MouseEvent event) {
    helpTwo.setOpacity(1);
  }

  /**
   * This method is called when the help button is un-hovered, restoring the button to its original
   * state.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void onHelpUnhover(MouseEvent event) {
    helpTwo.setOpacity(0);
  }

  /**
   * This method is called when the help button is pressed, causing the image of the button to
   * "sink", indicating that it has been pressed.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void onHelpPressed(MouseEvent event) {
    helpThree.setOpacity(1);
  }

  /**
   * Opens the help window GUI.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void onHelpReleased(MouseEvent event) {
    helpThree.setOpacity(0);

    // Store forrest room scene in stack:
    GameState.lastScene = AppScene.FOREST;

    App.setScene(AppScene.HELP);
  }

  /**
   * This method is called when the settings button is hovered over, placing a shadow over the
   * button.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void onSettingsHover(MouseEvent event) {
    settingsTwo.setOpacity(1);
  }

  /**
   * This method is called when the settings button is un-hovered, restoring the button to its
   * original state.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void onSettingsUnhover(MouseEvent event) {
    settingsTwo.setOpacity(0);
  }

  /**
   * This method is called when the settings button is pressed, causing the image of the button to
   * "sink" indicating that it has been pressed.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void onSettingsPressed(MouseEvent event) {
    settingsThree.setOpacity(1);
  }

  /**
   * Opens the settings menu scene.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void onSettingsReleased(MouseEvent event) {
    settingsThree.setOpacity(0);

    // Store forrest room scene in stack:
    GameState.lastScene = AppScene.FOREST;

    App.setScene(AppScene.SETTINGS);
  }

  /**
   * When the left arrow is no longer hovered over.
   *
   * @param event Mouse click event.
   */
  @FXML
  private void leftButtonUnhovered(MouseEvent event) {
    leftArrowHover.setOpacity(0);
  }

  /**
   * When the left arrow is hovered over.
   *
   * @param event Mouse click event.
   */
  @FXML
  private void leftButtonHovered(MouseEvent event) {
    leftArrowHover.setOpacity(1);
  }

  /**
   * When the left arrow pressed.
   *
   * @param event Mouse click event.
   */
  @FXML
  private void leftButtonClicked(MouseEvent event) {
    leftArrowPressed.setOpacity(1);
  }

  /**
   * When the left arrow is no longer pressed.
   *
   * @param event Mouse click event.
   */
  @FXML
  private void leftButtonUnclick(MouseEvent event) {
    leftArrowPressed.setOpacity(0);
    App.setScene(AppScene.ROOM);
  }

  // Rock
  /**
   * This method is called when the user clicks on the rock, displaying a notification to the user.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void rockClick(MouseEvent event) {
    // Notification to the user
    Notifications message =
        NotificationBuilder.createNotification("Nothing under there...", "robot");
    message.show();
  }

  /**
   * This method is called when the user hovers over the rock, displaying an outline around it.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void rockHover(MouseEvent event) {
    rockOutline.setOpacity(1);
  }

  /**
   * This method is called when the user un-hovers over the rock, removing the outline around the
   * rock.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void rockUnhover(MouseEvent event) {
    rockOutline.setOpacity(0);
  }

  /**
   * This method is called when the user clicks on the fishing rod. Fishing rod disappears from the
   * dock and is added to the inventory.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void fishingClick(MouseEvent event) {
    // Add the fishing rod to inventory
    if (GameState.isForestFishing) {
      GameState.isFishingRodTaken = true;
      updateTask();
      fishingRod.setOpacity(0);
      fishingRodOutline.setOpacity(0);
      dock.setOpacity(1);
      dockWithoutRod.setDisable(false);
      fishingMini.setDisable(true);
      findFishingRod();

      // Show a notification that the fishing rod has been taken
      Notifications message =
          NotificationBuilder.createNotification("You found a fishing rod!", "fishingRod");
      message.show();
    }
  }

  /**
   * This method is called when the user hovers over the fishing rod, displaying an outline around
   * it.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void fishingHover(MouseEvent event) {
    if (GameState.isForestFishing) {
      fishingRodOutline.setOpacity(1);
    }
  }

  /**
   * This method is called when the user un-hovers over the fishing rod, removing the outline around
   * it.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void fishingUnhover(MouseEvent event) {
    if (GameState.isForestFishing) {
      fishingRodOutline.setOpacity(0);
    }
  }

  /**
   * This method is called when the user clicks on the dock.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void dockClick(MouseEvent event) {

    // If the game selected is the fishing game:
    if (GameState.isForestFishing) {
      // If the riddle has not been found prompt user to find the riddle:
      if (!GameState.isRiddleFound) {
        findRiddle();

        return;
      } else if (!GameState.isRiddleResolved) {
        // If the riddle has been found but not solved, prompt user to solve the riddle:
        solveRiddle();

        return;
      } else if (!GameState.isRoomOrbCollected) {
        // If the riddle has been solved but the orb has not been collected, prompt user to find
        findRoomOrb();

        return;
      } else if (GameState.isFishingRodTaken) {
        App.setScene(AppScene.FISHING);
      }
    } else {
      // If it is the tree chopping game.
      Notifications message =
          NotificationBuilder.createNotification("Hah! Good luck trying to swim away.", "robot");
      message.show();
    }
  }

  /**
   * This method is called when the user hovers over the dock, displaying an outline around it.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void dockHover(MouseEvent event) {
    if (!GameState.isForestFishing || GameState.isFishingRodTaken) {
      dockOutline.setOpacity(1);
    }
  }

  /**
   * This method is called when the user un-hovers over the dock, removing the outline around it.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void dockUnhover(MouseEvent event) {
    if (!GameState.isForestFishing || GameState.isFishingRodTaken) {
      dockOutline.setOpacity(0);
    }
  }

  /**
   * This method is called when the user clicks on the trees.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void treesClick(MouseEvent event) {
    if (GameState.isForestTreeChopping) {
      if (!GameState.isRiddleFound) {
        // Prompt user to find the riddle first before interacting with the trees
        findRiddle();

        return;
      } else if (!GameState.isRiddleResolved) {
        // Prompt user to solve the riddle first before interacting with the trees
        solveRiddle();

        return;
      } else if (!GameState.isRoomOrbCollected) {
        // Prompt user to find the orb first before interacting with the trees
        findRoomOrb();

        return;
      } else if (GameState.isAxeTaken) {
        // If the axe has been taken, take user to tree chopping game:
        App.setScene(AppScene.TREES);
      } else {
        // Axe not taken but riddle solved and orb collected
        Notifications message =
            NotificationBuilder.createNotification(
                "Find something to cut down the trees!", "robot");
        message.show();
      }
    } else {
      // Do something else if they are in the other version of the game
      Notifications message =
          NotificationBuilder.createNotification("Leaf me alone, I'm trying to escape", "robot");
      message.show();
    }
  }

  /**
   * This method is called when the user hovers over the trees, displaying an outline around it.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void treesHover(MouseEvent event) {
    if (!GameState.isChopped) {
      treesOutline.setOpacity(1);
    }
  }

  /**
   * This method is called when the user un-hovers over the trees, removing the outline around it.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void treesUnhover(MouseEvent event) {
    if (!GameState.isChopped) {
      treesOutline.setOpacity(0);
    }
  }

  /** This method is called to swap the trees image to the chopped trees image. */
  public void chopTrees() {
    trees.setOpacity(0);
    treesOutline.setOpacity(0);
    treesMini.setDisable(true);
    choppedTrees.setDisable(false);
    treesRemoved.setOpacity(1);
  }

  /** This method is called when the user clicks on the chopped trees. */
  @FXML
  private void choppedClick(MouseEvent event) {
    if (GameState.isForestTreeChopping && GameState.isChopped) {
      App.setScene(AppScene.TREES);
    }
  }

  /**
   * This method is called when the user hovers over the chopped trees, displaying an outline around
   * it.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void choppedHover(MouseEvent event) {
    if (GameState.isChopped) {
      treesRemovedOutline.setOpacity(1);
    }
  }

  /**
   * This method is called when the user un-hovers over the chopped trees, removing the outline
   * around it.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void choppedUnhover(MouseEvent event) {
    if (GameState.isChopped) {
      treesRemovedOutline.setOpacity(0);
    }
  }

  // Axe logic
  /**
   * This method is called when the user clicks on the axe. The axe is removed from the scene and is
   * added to the inventory.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void axeClick(MouseEvent event) {
    // If the axe has not been taken:
    if (!GameState.isAxeTaken) {
      // Remove the axe from inventory
      findAxe();

      // Show a notification that the axe has been taken
      Notifications message = NotificationBuilder.createNotification("You found an axe!", "axe");
      message.show();

      // Remove axe image from the scene
      axe.setOpacity(0);
      axeOutline.setOpacity(0);
      axeRemoved.setOpacity(1);

      GameState.isAxeTaken = true;
      updateTask();

      // Disable the axe and enable the empty log
      axeGrab.setDisable(true);
      emptyLog.setDisable(false);
    }
  }

  /**
   * This method is called when the user hovers over the axe, displaying an outline around it.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void axeHover(MouseEvent event) {
    if (!GameState.isAxeTaken) {
      axeOutline.setOpacity(1);
    }
  }

  /**
   * This method is called when the user un-hovers over the axe, removing the outline around it.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void axeUnhover(MouseEvent event) {
    if (!GameState.isAxeTaken) {
      axeOutline.setOpacity(0);
    }
  }

  // Axe Removed
  /**
   * This method is called when the user clicks on the tree stump.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void emptyLogClick(MouseEvent event) {
    // if it isnt the tree chopping game:
    if (!GameState.isForestTreeChopping) {
      Notifications message =
          NotificationBuilder.createNotification(
              "I am the Lorax and I speak for the\ntrees!", "lorax");
      message.show();
    } else {
      // If it is the tree chopping game
      Notifications message =
          NotificationBuilder.createNotification("The axe has already been taken!", "robot");
      message.show();
    }
  }

  /**
   * This method is called when the user hovers over the tree stump, displaying an outline around
   * it.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void emptyLogHover(MouseEvent event) {
    if (GameState.isAxeTaken || !GameState.isForestTreeChopping) {
      axeRemovedOutline.setOpacity(1);
    }
  }

  /**
   * This method is called when the user un-hovers over the tree stump, removing the outline around
   * it.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void emptyLogUnhover(MouseEvent event) {
    if (GameState.isAxeTaken || !GameState.isForestTreeChopping) {
      axeRemovedOutline.setOpacity(0);
    }
  }

  // Bottom Right Game Master Button
  /**
   * This method is called when the game master button is hovered over, causing a "chat" icon to
   * appear.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void gameMasterOnHover(MouseEvent event) {
    gameMasterDefault.setOpacity(0);
    gameMasterChat.setOpacity(1);
  }

  /**
   * This method is called when the game master button is un-hovered, causing the "chat" icon to
   * disappear.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void gameMasterOnUnhover(MouseEvent event) {
    gameMasterDefault.setOpacity(1);
    gameMasterChat.setOpacity(0);
  }

  /**
   * This method is called when the game master button is clicked, changing the current scene to be
   * the chat scene.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void gameMasterOnClick(MouseEvent event) {
    hideAllLayers();
    // When the axe is chosen
    if (GameState.isForestTreeChopping && !GameState.isTreesPink) {
      setForestAxeOpacity();
    } else if (GameState.isForestTreeChopping && GameState.isTreesPink) {
      setPinkForestAxeOpacity();
    }

    // When the axe has been taken
    if (GameState.isAxeTaken && !GameState.isTreesPink) {
      setForestRodOpacity();
    } else if (GameState.isAxeTaken && GameState.isTreesPink) {
      setPinkForestRodOpacity();
    }

    // When the trees have been chopped
    if (GameState.isChopped) {
      setForestTreesRemovedOpacity();
    }

    // When the rod is chosen
    if (!GameState.isForestTreeChopping && !GameState.isTreesPink) {
      setForestRodOpacity();
    } else if (!GameState.isForestTreeChopping && GameState.isTreesPink) {
      setPinkForestRodOpacity();
    }

    // Store forrest room scene in stack:
    GameState.lastScene = AppScene.FOREST;

    App.setScene(AppScene.CHAT);
  }

  /**
   * Helper method which displays a notification to the player, prompting them to find the riddle
   * first.
   */
  public void findRiddle() {
    // Initialize orb notification message
    Notifications orbMessage =
        NotificationBuilder.createNotification("See if you can find a riddle first!", "robot");
    orbMessage.show();
  }

  /**
   * Helper method which displays a notification to the player, prompting them to solve the riddle
   * first.
   */
  public void solveRiddle() {
    // Initialize orb notification message
    Notifications orbMessage =
        NotificationBuilder.createNotification("Hmm... Try solving the riddle first!", "robot");
    orbMessage.show();
  }

  /**
   * Helper method which displays a notification to the player, prompting them to find the orb
   * first.
   */
  public void findRoomOrb() {
    // Initialize orb notification message
    Notifications orbMessage =
        NotificationBuilder.createNotification("Try searching for an orb first!", "robot");
    orbMessage.show();
  }
}
