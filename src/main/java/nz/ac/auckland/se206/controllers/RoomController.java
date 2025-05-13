package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.NotificationBuilder;
import nz.ac.auckland.se206.SceneManager.AppScene;
import org.controlsfx.control.Notifications;

/**
 * This class handles the logic for the main central room in which they are able to access the
 * forest room and lava room.
 */
public class RoomController extends ControllerMethods {

  @FXML private Label lblTimer;
  @FXML private Label lblTask;
  @FXML private Label lblHints;

  @FXML private ImageView rightArrow;
  @FXML private ImageView rightArrowHover;
  @FXML private ImageView rightArrowPressed;
  @FXML private ImageView leftArrow;
  @FXML private ImageView leftArrowHover;
  @FXML private ImageView leftArrowPressed;
  @FXML private ImageView cabinetOutline;
  @FXML private ImageView rugOutline;
  @FXML private ImageView windowOutline;
  @FXML private ImageView mapOutline;
  @FXML private ImageView terminalOutline;
  @FXML private ImageView switchOn;
  @FXML private ImageView switchOff;
  @FXML private ImageView switchOnOutline;
  @FXML private ImageView switchOffOutline;
  @FXML private ImageView darkOverlay;
  @FXML private ImageView hiddenCode;
  @FXML private ImageView codeBackground;
  @FXML private ImageView codeBackgroundOutline;
  @FXML private ImageView firstDigit;
  @FXML private ImageView secondDigit;
  @FXML private ImageView thirdDigit;
  @FXML private ImageView glowParticleOne;
  @FXML private ImageView glowParticleTwo;
  @FXML private ImageView glowParticleThree;
  @FXML private ImageView map;
  @FXML private ImageView portalBaseOutline;
  @FXML private ImageView portal;
  @FXML private ImageView portalOutline;
  @FXML private ImageView cabinetOpenedEmpty;
  @FXML private ImageView cabinetOpenedEmptyOutline;
  @FXML private ImageView cabinetOpenedWithOrb;
  @FXML private ImageView cabinetOpenedWithOrbOutline;
  @FXML private ImageView cabinetOrbSelected;
  @FXML private ImageView floorBlueOrb;
  @FXML private ImageView floorBlueOrbOutline;
  @FXML private ImageView floorRug;

  // Book Items:
  @FXML private ImageView book;
  @FXML private ImageView bookOutline;

  // Riddle items
  @FXML private Polygon cabinet;
  @FXML private Polygon carpet;
  @FXML private Polygon cabinetOrb;
  @FXML private Rectangle orbRectangleRug;

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

  @FXML private Polygon codedText;
  @FXML private ImageView helpOne;
  @FXML private ImageView helpTwo;
  @FXML private ImageView helpThree;
  @FXML private ImageView settingsOne;
  @FXML private ImageView settingsTwo;
  @FXML private ImageView settingsThree;

  private int spamCount = 0;
  private boolean isDrawerOpen = false;
  private boolean isRugPresent = true;

  /** Initializes the room view, it is called when the room loads. */
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

    // Bind the portal images to their image properties
    portal.imageProperty().bind(ControllerMethods.portalImageProperty);
    portalOutline.imageProperty().bind(ControllerMethods.portalOutlineImageProperty);
    initialisePortal();

    // Set the digit images behind the map
    setCodeImages();
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

    // Store scene to stack
    GameState.lastScene = AppScene.ROOM;

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
   * Opens the settings scene.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void onSettingsReleased(MouseEvent event) {
    settingsThree.setOpacity(0);

    // Store scene to stack
    GameState.lastScene = AppScene.ROOM;

    App.setScene(AppScene.SETTINGS);
  }

  /**
   * Updates the task label with a given message.
   *
   * @param message The message to be displayed.
   */
  public void updateTaskLabel(String message) {
    lblTask.setText("Task: " + message);
  }

  /**
   * When the right arrow is no longer hovered over.
   *
   * @param event Mouse click event.
   */
  @FXML
  private void rightButtonUnhovered(MouseEvent event) {
    rightArrowHover.setOpacity(0);
  }

  /**
   * When the right arrow is hovered over.
   *
   * @param event Mouse click event.
   */
  @FXML
  private void rightButtonHovered(MouseEvent event) {
    rightArrowHover.setOpacity(1);
  }

  /**
   * When the right arrow is pressed.
   *
   * @param event Mouse click event.
   */
  @FXML
  private void rightButtonClicked(MouseEvent event) {
    rightArrowPressed.setOpacity(1);
  }

  /**
   * When the right arrow is no longer pressed.
   *
   * @param event Mouse click event.
   */
  @FXML
  private void rightButtonUnclick(MouseEvent event) {
    rightArrowPressed.setOpacity(0);
    App.setScene(AppScene.FOREST);
  }

  /**
   * When the left arrow is hovered over.
   *
   * @param event Mouse click event.
   */
  @FXML
  private void leftButtonUnhovered(MouseEvent event) {
    leftArrowHover.setOpacity(0);
  }

  /**
   * When the left arrow is no longer hovered over.
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
    App.setScene(AppScene.LAVA);
  }

  /**
   * If the cabinet riddle is selected and solved, the user can interact with the cabinet to see
   * clue.
   *
   * @param event Mouse click event.
   */
  @FXML
  private void cabinetClick(MouseEvent event) {

    // If it is NOT the cabinet riddle
    if (!GameState.isCabinet) {
      showNothingNotification();

      return;
    }

    // If the item has already been clicked, do not let them click again.
    if (isDrawerOpen) {
      return;
    }

    // if cabinet riddle is selected and solved solved:
    if (GameState.isCabinet && GameState.isRiddleResolved) {

      isDrawerOpen = true;
      cabinetOpenedWithOrb.setOpacity(1);
      cabinetOpenedWithOrbOutline.setOpacity(1);
      cabinetOpenedEmpty.setOpacity(1);
      cabinetOrb.setDisable(false);
    } else {
      // If the riddle has NOT been solved and the user is stuck, give help
      giveRiddleHelp();
    }
  }

  /**
   * This method is called when when the cabinet is hovered over, displaying an outline around it.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void cabinetHover(MouseEvent event) {
    // If the drawer is open and the room orb has been collected
    if (isDrawerOpen == true && GameState.isRoomOrbCollected) {
      cabinetOpenedEmptyOutline.setOpacity(1);
    } else if (isDrawerOpen == true && !GameState.isRoomOrbCollected) {
      // If the drawer is open and the room orb has NOT been collected
      cabinetOpenedWithOrbOutline.setOpacity(1);
    } else {
      cabinetOutline.setOpacity(1);
    }
  }

  /**
   * This method is called when when the cabinet is no longer hovered over, removing the outline
   * from it.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void cabinetUnhover(MouseEvent event) {
    cabinetOutline.setOpacity(0);
    cabinetOpenedWithOrbOutline.setOpacity(0);
    cabinetOpenedEmptyOutline.setOpacity(0);
  }

  /**
   * This method is called when the cabinet is opened and the orb is clicked.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void cabinetOrbClicked(MouseEvent event) {
    if (GameState.itemClicked) {
      return;
    }

    if (GameState.isCabinet && GameState.isRiddleResolved) {
      GameState.itemClicked = true;
      cabinetOpenedWithOrb.setOpacity(0);
      cabinetOpenedWithOrbOutline.setOpacity(0);
      cabinetOrb.setDisable(true);

      // Show notification - alerting user that they have found an orb
      orbFoundNotification();

      // Make the blue orb appear in the inventory
      findBlueOrb();

      // Update game state
      GameState.isRoomOrbCollected = true;
      updateTask();
    }
  }

  /**
   * This method is called when the user hovers over the cabinet, displaying an outline around it.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void cabinetOrbHover(MouseEvent event) {
    cabinetOrbSelected.setOpacity(1);
  }

  /**
   * This method is called when the user un-hovers over the cabinet, removing the outline around it.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void cabinetOrbUnhover(MouseEvent event) {
    cabinetOrbSelected.setOpacity(0);
  }

  /**
   * If the rug riddle is selected and solved, the user can interact with the rug to see clue.
   *
   * @param event Mouse click event.
   */
  @FXML
  private void carpetClick(MouseEvent event) {

    // If it is NOT the carpet riddle
    if (!GameState.isRug) {
      showNothingNotification();

      return;
    }

    // If the item has already been clicked, dont let them click again.
    if (GameState.itemClicked) {
      return;
    }

    // if rug riddle is selected and solved
    if (GameState.isRug && GameState.isRiddleResolved) {
      // Remove the rug
      isRugPresent = false;
      floorRug.setOpacity(0);
      rugOutline.setOpacity(0);
      carpet.setDisable(true);

      // Reveal the blue orb
      floorBlueOrbOutline.setDisable(false);
      orbRectangleRug.setDisable(false);
      floorBlueOrb.setOpacity(1);
    } else {
      // If the riddle has NOT been solved and the user is stuck, give help
      giveRiddleHelp();
    }
  }

  /**
   * This method is called when when the rug is hovered over, displaying an outline around it.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void carpetHover(MouseEvent event) {
    rugOutline.setOpacity(1);
  }

  /**
   * This method is called when when the rug is no longer hovered over, removing the outline from
   * it.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void carpetUnhover(MouseEvent event) {
    rugOutline.setOpacity(0);
  }

  /**
   * This method is called when the rug is removed and the orb is clicked, adding the orb to the
   * user's inventory.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void rugOrbClicked(MouseEvent event) {
    if (GameState.itemClicked) {
      return;
    }

    if (GameState.isRug && GameState.isRiddleResolved) {
      GameState.itemClicked = true;

      // Hide the orb once retrieved
      floorBlueOrbOutline.setDisable(true);
      orbRectangleRug.setDisable(true);
      floorBlueOrb.setOpacity(0);
      floorBlueOrbOutline.setOpacity(0);

      // Show notification - alerting user that they have found an orb
      orbFoundNotification();

      // Make the blue orb appear in the inventory
      findBlueOrb();

      // Set game state:
      GameState.isRoomOrbCollected = true;
      updateTask();
    }
  }

  /**
   * This method is called when the user hovers over the rug, displaying an outline around it.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void rugOrbHover(MouseEvent event) {
    floorBlueOrbOutline.setOpacity(1);
  }

  /**
   * This method is called when the user un-hovers over the rug, removing the outline around it.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void rugOrbUnhover(MouseEvent event) {
    floorBlueOrbOutline.setOpacity(0);
  }

  // Window
  /**
   * This method is called when the user clicks on the window.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void paneClick(MouseEvent event) {
    Notifications message = NotificationBuilder.createNotification("Ha ha ha... yeah no.", "robot");
    message.show();
  }

  /**
   * This method is called when the user hovers over the window, displaying an outline around it.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void paneHover(MouseEvent event) {
    windowOutline.setOpacity(1);
  }

  /**
   * This method is called when the user un-hovers over the window, removing the outline around it.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void paneUnhover(MouseEvent event) {
    windowOutline.setOpacity(0);
  }

  // Map
  /**
   * This method is called when the user clicks on the map - removing the map from the wall and
   * revealing
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void mapClick(MouseEvent event) {
    map.setOpacity(0);
    mapOutline.setOpacity(0);
    GameState.isMapOnWall = false;
    codedText.setDisable(false);
    // Update Code
    if (GameState.isLightOn) {
      codeBackground.setOpacity(1);
      hiddenCode.setOpacity(1);
    } else {
      codeBackground.setOpacity(1);
      hiddenCode.setOpacity(0);
      firstDigit.setOpacity(1);
      secondDigit.setOpacity(1);
      thirdDigit.setOpacity(1);
      glowParticleOne.setOpacity(1);
      glowParticleTwo.setOpacity(1);
      glowParticleThree.setOpacity(1);
      GameState.isCodeFound = true;
      updateTask();
    }
  }

  /**
   * This method is called when the user hovers over the map, displaying an outline around it.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void mapHover(MouseEvent event) {
    if (GameState.isMapOnWall) {
      mapOutline.setOpacity(1);
    }
  }

  /**
   * This method is called when the user un-hovers over the map, removing the outline around it.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void mapUnhover(MouseEvent event) {
    if (GameState.isMapOnWall) {
      mapOutline.setOpacity(0);
    }
  }

  // Terminal
  /**
   * This method is called when the user clicks on the terminal.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void terminalClick(MouseEvent event) {
    // If the orbs have NOT been found, prompt user to find the orbs:
    if (GameState.isRoomOrbCollected
        && GameState.isForestOrbCollected
        && GameState.isCastleOrbCollected) {
      App.setScene(AppScene.TERMINAL);
    } else {
      Notifications message =
          NotificationBuilder.createNotification("Find the orbs to access the terminal!", "robot");
      message.show();
    }
  }

  /**
   * This method is called when the user hovers over the terminal, displaying an outline around it.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void terminalHover(MouseEvent event) {
    terminalOutline.setOpacity(1);
  }

  /**
   * This method is called when the user un-hovers over the terminal, removing the outline around
   * it.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void terminalUnhover(MouseEvent event) {
    terminalOutline.setOpacity(0);
  }

  // Light Switch
  /**
   * This method is called when the user clicks on the light switch. If the map has been removed,
   * this will reveal the code to the chest to the user.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void switchClick(MouseEvent event) {
    if (GameState.isLightOn) {
      // Update Switch Images
      switchOn.setOpacity(0);
      switchOnOutline.setOpacity(0);
      switchOff.setOpacity(1);
      switchOffOutline.setOpacity(1);
      // Update Overlay
      darkOverlay.setOpacity(1);
      // Update Code
      if (!GameState.isMapOnWall) {
        codeBackground.setOpacity(1);
        hiddenCode.setOpacity(0);
        firstDigit.setOpacity(1);
        secondDigit.setOpacity(1);
        thirdDigit.setOpacity(1);
        glowParticleOne.setOpacity(1);
        glowParticleTwo.setOpacity(1);
        glowParticleThree.setOpacity(1);
        GameState.isCodeFound = true;
        updateTask();
      }
      // Update GameState
      GameState.isLightOn = false;
    } else {
      // Update Switch Images
      switchOn.setOpacity(1);
      switchOnOutline.setOpacity(1);
      switchOff.setOpacity(0);
      switchOffOutline.setOpacity(0);
      // Update Overlay
      darkOverlay.setOpacity(0);
      // Update Code
      if (!GameState.isMapOnWall) {
        hiddenCode.setOpacity(1);
      }
      firstDigit.setOpacity(0);
      secondDigit.setOpacity(0);
      thirdDigit.setOpacity(0);
      glowParticleOne.setOpacity(0);
      glowParticleTwo.setOpacity(0);
      glowParticleThree.setOpacity(0);
      // Update GameState
      GameState.isLightOn = true;
    }
  }

  /**
   * This method is called when the user hovers over the light switch, displaying an outline around
   * it.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void switchHover(MouseEvent event) {
    if (GameState.isLightOn) {
      switchOnOutline.setOpacity(1);
    } else {
      switchOffOutline.setOpacity(1);
    }
  }

  /**
   * This method is called when the user un-hovers over the light switch, removing the outline
   * around it.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void switchUnhover(MouseEvent event) {
    if (GameState.isLightOn) {
      switchOnOutline.setOpacity(0);
    } else {
      switchOffOutline.setOpacity(0);
    }
  }

  // Portal
  /**
   * This method is called when the user clicks on the portal. If the orbs have been placed in the
   * terminal, the user is directed to a game finished screen.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void portalClick(MouseEvent event) {
    // If the portal is open, allow the user to escape
    if (GameState.isPortalOpen) {
      GameState.isRoomEscaped = true;
      int minutes = GameState.timeTaken / 60;
      int seconds = GameState.timeTaken % 60;
      String minutesPlural = minutes == 1 ? "" : "s";
      String secondsPlural = seconds == 1 ? "!" : "s!";
      if (minutes == 0) {
        setMessages("Congratulations!", "You escaped in " + seconds + " second" + secondsPlural);
      } else {
        setMessages(
            "Congratulations!",
            "You escaped in "
                + minutes
                + " minute"
                + minutesPlural
                + " and "
                + seconds
                + " second"
                + secondsPlural);
      }

      // Switch scenes to game finished
      App.setScene(AppScene.GAMEFINISHED);
    } else {
      // If the portal is not open, prompt the user to find the orbs
      Notifications message =
          NotificationBuilder.createNotification("Try finding orbs to open the portal!", "robot");
      message.show();
    }
  }

  /**
   * This method is called when the user hovers over the portal, displaying an outline around it.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void portalHover(MouseEvent event) {
    portalBaseOutline.setOpacity(1);
    portalOutline.setOpacity(1);
  }

  /**
   * This method is called when the user un-hovers over the portal, removing the outline around it.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void portalUnhover(MouseEvent event) {
    portalBaseOutline.setOpacity(0);
    portalOutline.setOpacity(0);
  }

  // Code
  /**
   * This method is called when the user clicks on the code. If the light is on, the user is
   * prompted to turn off the lights. Else, the code is revealed.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void codeClick(MouseEvent event) {
    if (GameState.isLightOn) {
      // If light has been turned on, prompt user to turn off the light
      Notifications message =
          NotificationBuilder.createNotification(
              "That looks like fluorescent text...\nTry turning off the lights!", "robot");
      message.show();
    } else {
      // If the light has been turned off, tell user the code
      Notifications message =
          NotificationBuilder.createNotification(
              "Hmm "
                  + GameState.firstDigit
                  + GameState.secondDigit
                  + GameState.thirdDigit
                  + "... I wonder what that means.",
              "robot");
      message.show();
    }
  }

  /**
   * This method is called when the user hovers over the code, displaying an outline around it.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void codeHover(MouseEvent event) {
    codeBackgroundOutline.setOpacity(1);
  }

  /**
   * This method is called when the user un-hovers over the code, removing the outline around it.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void codeUnhover(MouseEvent event) {
    codeBackgroundOutline.setOpacity(0);
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
    backgroundHelper();
    // Store current scene in scene stack
    GameState.lastScene = AppScene.ROOM;

    // Switch scenes
    App.setScene(AppScene.CHAT);
  }

  // Book methods:
  /** This method is called when the user hovers over the book, displaying an outline around it. */
  @FXML
  private void bookHovered() {
    book.setOpacity(0);
    bookOutline.setOpacity(1);
  }

  /**
   * This method is called when the user un-hovers over the book, removing the outline around it.
   */
  @FXML
  private void bookUnhovered() {
    book.setOpacity(1);
    bookOutline.setOpacity(0);
  }

  /**
   * This method is called when the user clicks on the book, changing the current scene to be the
   * riddle scene.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void bookPressed(MouseEvent event) {
    // Update game state
    GameState.isRiddleFound = true;
    updateTask();

    // If the user has already resolved the riddle, disability the ability to traverse back to the
    // riddle scene
    if (GameState.isRiddleResolved) {
      Notifications message =
          NotificationBuilder.createNotification("You've already solved the riddle!", "robot");
      message.show();
      return;
    }

    // Set boolean
    GameState.isRiddleBookOpen = true;

    // Get chat controller
    ChatController chatController = App.getChatController();

    // Enable riddleBook and riddleTextArea
    chatController.setRiddleBookOpacity();
    chatController.requestFocus();

    // Logic for which background GPT should have
    backgroundHelper();

    // Store current scene in scene stack
    GameState.lastScene = AppScene.ROOM;

    // Switch scenes
    App.setScene(AppScene.CHAT);
  }

  /**
   * This helper method provides the user with a notification if they are unable to locate the book.
   */
  private void giveRiddleHelp() {
    // If the riddle has not been resolved, give help to the user
    if (!GameState.isRiddleResolved) {
      spamCount++;

      // If the user has clicked on the cabinet or rug 5 times:
      if (spamCount >= 3) {

        // Prompt user to find the book
        Notifications message2 =
            NotificationBuilder.createNotification(
                "In a book, you will find your first clue!", "robot");
        message2.show();
      } else {
        interestingNotification();
      }
      return;
    }
  }

  /**
   * This helper method is called when the user has found the blue orb, displaying a notification.
   */
  private void orbFoundNotification() {
    // Initialize orb notification message
    Notifications orbMessage =
        NotificationBuilder.createNotification("Congratulations! You've found an orb!", "blueOrb");
    orbMessage.show();
  }

  /** This helper function handles the changing of the background depending on the game state. */
  private void backgroundHelper() {
    hideAllLayers();
    // Logic for which background GPT should have
    if (GameState.isMapOnWall && GameState.isLightOn) {
      setMainMapOpacity();
    } else if (GameState.isMapOnWall && !GameState.isLightOn) {
      setMainDarkOpacity();
    } else if (!GameState.isMapOnWall && GameState.isLightOn) {
      setMainMapRemovedOpacity();
    } else if (!GameState.isMapOnWall && !GameState.isLightOn) {
      setMainDarkMapRemovedOpacity();
    }

    // Handles if the cabinet should be open or not
    if (GameState.isMapOnWall && GameState.isLightOn && isDrawerOpen) {
      setMainCabinetMapOpacity();
    } else if (GameState.isMapOnWall && !GameState.isLightOn && isDrawerOpen) {
      setMainDarkCabinetMapOpacity();
    } else if (!GameState.isMapOnWall && GameState.isLightOn && isDrawerOpen) {
      setMainCabinetMapRemovedOpacity();
    } else if (!GameState.isMapOnWall && !GameState.isLightOn && isDrawerOpen) {
      setMainDarkCabinetMapRemovedOpacity();
    }

    // Handles if the rug should be visible or not
    if (GameState.isMapOnWall && GameState.isLightOn && !isRugPresent) {
      setMainRugMapOpacity();
    } else if (GameState.isMapOnWall && !GameState.isLightOn && !isRugPresent) {
      setMainDarkRugMapOpacity();
    } else if (!GameState.isMapOnWall && GameState.isLightOn && !isRugPresent) {
      setMainRugMapRemovedOpacity();
    } else if (!GameState.isMapOnWall && !GameState.isLightOn && !isRugPresent) {
      setMainDarkRugMapRemovedOpacity();
    }
  }

  /** This helper method displays a notification to the user when an irrelevant item is clicked. */
  private void showNothingNotification() {
    Notifications message = NotificationBuilder.createNotification("Nothing there!", "robot");
    message.show();
  }

  /**
   * This helper method displays a notification to the user when the click on the rug or cabinet -
   * depending on the riddle selected.
   */
  private void interestingNotification() {
    Notifications message =
        NotificationBuilder.createNotification("This looks interesting.. not sure why.", "robot");
    message.show();
  }

  /**
   * This helper function initializes and sets the code displayed on the wall which is used to
   * unlock the chest.
   */
  private void setCodeImages() {
    // Set the code images for the first digit.
    firstDigit.setImage(
        new Image(getClass().getResourceAsStream("/images/digit" + GameState.firstDigit + ".png")));
    // Set the code images for the second digit.
    secondDigit.setImage(
        new Image(
            getClass().getResourceAsStream("/images/digit" + GameState.secondDigit + ".png")));
    // Set the code images for the third digit.
    thirdDigit.setImage(
        new Image(getClass().getResourceAsStream("/images/digit" + GameState.thirdDigit + ".png")));
  }
}
