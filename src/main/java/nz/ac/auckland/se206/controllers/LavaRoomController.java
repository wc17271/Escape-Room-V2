package nz.ac.auckland.se206.controllers;

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
 * This class handles the logic for the lava room where users are able to feed the dragon or play
 * the bridge mini-game.
 */
public class LavaRoomController extends ControllerMethods {

  @FXML private Label lblTimer;
  @FXML private Label lblTask;
  @FXML private Label lblHints;

  @FXML private ImageView rightArrow;
  @FXML private ImageView rightArrowHover;
  @FXML private ImageView rightArrowPressed;

  // Game Master
  @FXML private ImageView gameMasterDefault;
  @FXML private ImageView gameMasterChat;

  // Door state
  @FXML private ImageView doorOutline;

  // Dragon state
  @FXML private ImageView dragon;
  @FXML private ImageView dragonOutline;
  @FXML private ImageView blueDragon;
  @FXML private ImageView blueDragonOutline;

  // Normal bridge state
  @FXML private ImageView bridge;
  @FXML private ImageView bridgeOutline;
  @FXML private Polygon bridgeOutlinePolygon;

  // Broken bridge state
  @FXML private ImageView brokenBridge;
  @FXML private ImageView brokenBridgeOutline;
  @FXML private Polygon brokenBridgeOutlinePolygon;

  // Fixed broken bridge state
  @FXML private ImageView fixedBridge;
  @FXML private ImageView fixedBridgeOutline;
  @FXML private Polygon fixedBridgeOutlinePolygon;

  // Help and Settings Icons
  @FXML private ImageView helpOne;
  @FXML private ImageView helpTwo;
  @FXML private ImageView helpThree;
  @FXML private ImageView settingsOne;
  @FXML private ImageView settingsTwo;
  @FXML private ImageView settingsThree;

  // Inventory Items
  @FXML private ImageView fishingRodIcon;
  @FXML private ImageView axeIcon;
  @FXML private ImageView fishIcon;
  @FXML private ImageView planksIcon;
  @FXML private ImageView blueOrb;
  @FXML private ImageView greenOrb;
  @FXML private ImageView redOrb;

  // Random booleans
  private boolean isBlueDragon;

  // Notifications
  private String dragonImage;

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

    // Random number 0 or 1, if 0, show red dragon otherwise show blue dragon
    int random = (int) (Math.random() * 2);
    if (random == 0) {
      blueDragon.setOpacity(1);
      dragon.setOpacity(0);
      isBlueDragon = true;
    } else {
      dragon.setOpacity(1);
      blueDragon.setOpacity(0);
      isBlueDragon = false;
    }

    // Based on mini-game selected, either show dragon scenario or broken bridge scenario:
    if (GameState.isLavaBridge && GameState.isForestTreeChopping) {
      // Mini-game 1: Bridge is Broken - Disable dragon components and fixed bridge components

      // Disable bridge components
      bridge.setOpacity(0);
      bridge.setDisable(true);
      bridgeOutline.setDisable(true);
      bridgeOutlinePolygon.setDisable(true);

      // Disable dragon components
      dragon.setOpacity(0);
      blueDragon.setOpacity(0);
      dragon.setDisable(true);
      blueDragon.setDisable(true);
      dragonOutline.setDisable(true);
      blueDragonOutline.setDisable(true);

      // Disable fixed bridge components - these need to be re-enabled when the bridge is fixed
      // (mini-game completed)
      fixedBridge.setDisable(true);
      fixedBridgeOutline.setDisable(true);
      fixedBridgeOutlinePolygon.setDisable(true);

      // Show broken bridge
      brokenBridge.setOpacity(1);

    } else {
      // Mini-game 2: Dragon is blocking the bridge - Disable broken bridge components

      brokenBridge.setDisable(true);
      brokenBridgeOutline.setDisable(true);
      brokenBridgeOutlinePolygon.setDisable(true);

      fixedBridge.setDisable(true);
      fixedBridgeOutline.setDisable(true);
      fixedBridgeOutlinePolygon.setDisable(true);
    }
  }

  /**
   * This method is called when the right arrow is un-hovered, restoring the button to its original
   * state.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void rightButtonUnhovered(MouseEvent event) {
    rightArrowHover.setOpacity(0);
  }

  /**
   * This method is called when the right arrow is hovered over, placing a shadow over the button.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void rightButtonHovered(MouseEvent event) {
    rightArrowHover.setOpacity(1);
  }

  /**
   * This method is called when the right arrow is clicked, causing the image of the button to
   * "sink" indicating that it has been pressed.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void rightButtonClicked(MouseEvent event) {
    rightArrowPressed.setOpacity(1);
  }

  /**
   * This method is called when the right arrow is released, changing the scene to the room scene.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void rightButtonUnclick(MouseEvent event) {
    rightArrowPressed.setOpacity(0);
    App.setScene(AppScene.ROOM);
  }

  // Methods for mini-game 1: fix bridge
  /**
   * This method is called when the broken bridge is hovered over, causing an outline to be shown.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void brokenBridgeHovered(MouseEvent event) {
    brokenBridgeOutline.setOpacity(1);
  }

  /**
   * This method is called when the broken bridge is un-hovered, removing the outline.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void brokenBridgeUnhovered(MouseEvent event) {
    brokenBridgeOutline.setOpacity(0);
  }

  /**
   * This method is called when the broken bridge is clicked, if the user has completed the forrest
   * mini game, they are prompted to fix the bridge, else, they are prompted to get wood.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void brokenBridgeClicked(MouseEvent event) {
    if (GameState.isForestGameCompleted) {

      // If the orb in the forest room has not been collected, prompt the player to collect it
      if (!GameState.isForestOrbCollected) {
        findForestOrb();

        return;
      }

      App.setScene(AppScene.BRIDGE_GAME);
    } else {
      // Forrest game NOT COMPLETED, prompt user to get wood.
      Notifications message =
          NotificationBuilder.createNotification(
              "The bridge is broken. Try fixing it with\nsome wood!", "robot");
      message.show();
    }
  }

  /**
   * This method is called when the fixed bridge is hovered over, causing an outline to be shown.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void fixedBridgeHovered(MouseEvent event) {
    fixedBridgeOutline.setOpacity(1);
  }

  /**
   * This method is called when the fixed bridge is un-hovered, causing the outline to be removed.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void fixedBridgeUnhovered(MouseEvent event) {
    fixedBridgeOutline.setOpacity(0);
  }

  /**
   * This method is called when the fixed bridge is clicked. The user is returned to the bridge
   * mini-game.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void fixedBridgeClicked(MouseEvent event) {
    // Change scene to bridge mini-game:
    App.setScene(AppScene.BRIDGE_GAME);
  }

  /** This helper function is called to update the state of the bridge once it has been fixed. */
  public void setFixedBridge() {
    fixedBridge.setDisable(false);
    fixedBridge.setOpacity(1);
    fixedBridgeOutline.setDisable(false);
    fixedBridgeOutlinePolygon.setDisable(false);
  }

  // Methods for mini-game 2: Tame dragon
  /**
   * This method is called when the dragon is hovered over, causing an outline to be shown.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void dragonHovered(MouseEvent event) {
    if (isBlueDragon) {
      blueDragonOutline.setOpacity(1);
    } else {
      dragonOutline.setOpacity(1);
    }
  }

  /**
   * This method is called when the dragon is un-hovered, causing the outline to be removed.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void dragonUnhovered(MouseEvent event) {
    dragonOutline.setOpacity(0);
    blueDragonOutline.setOpacity(0);
  }

  /**
   * This method is called when the dragon is clicked.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void dragonClicked(MouseEvent event) {
    // Choose red or blue dragon for notification image
    if (isBlueDragon) {
      dragonImage = "blueDragon";
    } else {
      dragonImage = "redDragon";
    }

    if (GameState.isForestGameCompleted) {
      // Update boolean
      GameState.isDragonGone = true;

      // if user has collected the fish, prompt the user to feed the dragon
      Notifications message =
          NotificationBuilder.createNotification("Mmmm, yummy! You may pass now!", dragonImage);
      message.show();

      // set lava game state to completed
      GameState.isLavaGameCompleted = true;
      removeFish();
      updateTask();

      // disable dragon and dragon outline
      dragon.setDisable(true);
      dragonOutline.setDisable(true);
      dragon.setOpacity(0);

      blueDragon.setDisable(true);
      blueDragonOutline.setDisable(true);
      blueDragon.setOpacity(0);

    } else {
      // if the user has not collected the fish, prompt the user to collect the fish
      Notifications message =
          NotificationBuilder.createNotification("ROARRR! YOU SHALL NOT PASS!", dragonImage);
      message.show();
    }
  }

  /**
   * This method is called when the bridge is hovered over, causing an outline to be shown shown.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void bridgeHovered(MouseEvent event) {
    bridgeOutline.setOpacity(1);
  }

  /**
   * This method is called when the bridge is un-hovered, causing the outline to be removed.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void bridgeUnhovered(MouseEvent event) {
    bridgeOutline.setOpacity(0);
  }

  /**
   * This method is called when the player clicks on the bridge.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void bridgeClicked(MouseEvent event) {

    if (GameState.isLavaGameCompleted) {
      // if lava game room is COMPLETEd (dragon tamed), prompt user to enter the castle
      Notifications message =
          NotificationBuilder.createNotification("You may now enter the castle!", "robot");
      message.show();
    } else {
      // if lava game room is NOT COMPLETED (dragon has not been tamed), prompt user to catch fish
      Notifications message =
          NotificationBuilder.createNotification(
              "A hungry dragon is blocking your path.\nTry feeding it fish!", "robot");
      message.show();
    }
  }

  // Door methods:
  /**
   * This method is called when the door is hovered over, causing an outline to be shown.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void doorHovered(MouseEvent event) {
    doorOutline.setOpacity(1);
  }

  /**
   * This method is called when the door is un-hovered, causing the outline to be removed.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void doorUnhovered(MouseEvent event) {
    doorOutline.setOpacity(0);
  }

  /**
   * This method is called when the door to the castle has been clicked.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void doorClicked(MouseEvent event) {

    if (GameState.isLavaGameCompleted) {
      // if lava game room is COMPLETED, allow user to enter the castle
      GameState.isChestFound = true;
      updateTask();
      App.setScene(AppScene.CASTLE);
    } else {
      // lava game room is NOT COMPLETED
      if (GameState.isLavaBridge) {
        // if lava game room is minigame 1, prompt user to fix the bridge
        Notifications message =
            NotificationBuilder.createNotification(
                "Door is inaccessible... The bridge is\nbroken!", "robot");
        message.show();

      } else {
        // if lava game room is minigame 2, prompt user to feed the dragon
        Notifications message =
            NotificationBuilder.createNotification(
                "A hungry dragon is blocking your path.\nTry feeding it fish!", "robot");
        message.show();
      }
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
   * "sink" indicating that it has been pressed.
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

    // Store lava room scene in stack:
    GameState.lastScene = AppScene.LAVA;

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
   * Opens the settings scene, switching the current scene to the settings scene.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void onSettingsReleased(MouseEvent event) {
    settingsThree.setOpacity(0);

    // Store lava room scene in stack:
    GameState.lastScene = AppScene.LAVA;

    App.setScene(AppScene.SETTINGS);
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
    // Red dragon conditional
    if (GameState.isLavaDragon && !GameState.isDragonGone) {
      setLavaDragonOpacity();
    } else if (GameState.isLavaDragon && GameState.isDragonGone) {
      setLavaNoDragonOpacity();
    }

    // Blue dragon conditional
    if (isBlueDragon && !GameState.isDragonGone) {
      setBlueLavaDragonOpacity();
    } else if (isBlueDragon && GameState.isDragonGone) {
      setLavaNoDragonOpacity();
    }

    // Broken or fixed bridge conditional
    if (GameState.isLavaBridge
        && GameState.isForestTreeChopping
        && !GameState.isLavaGameCompleted) {
      setBrokenBridgeOpacity();
    } else if (GameState.isLavaBridge
        && GameState.isForestTreeChopping
        && GameState.isLavaGameCompleted) {
      setFixedBridgeOpacity();
    }

    // Store current scene in scene stack
    GameState.lastScene = AppScene.LAVA;

    // Change scene
    App.setScene(AppScene.CHAT);
  }

  /**
   * Helper function which generates a notification, prompting the user to collect the orb from the
   * forest room first.
   */
  private void findForestOrb() {
    Notifications message =
        NotificationBuilder.createNotification("Try collecting the green orb first!", "robot");
    message.show();
  }
}
