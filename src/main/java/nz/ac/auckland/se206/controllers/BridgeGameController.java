package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.DragImage;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.NotificationBuilder;
import nz.ac.auckland.se206.SceneManager.AppScene;
import org.controlsfx.control.Notifications;

/**
 * This class handles the logic regarding the bridge mini-game in the lava room where players are
 * required to fix the bridge.
 */
public class BridgeGameController extends ControllerMethods {

  @FXML private Label lblTimer;
  @FXML private Label lblTask;
  @FXML private Label lblHints;

  // Back Button:
  @FXML private ImageView backButton;
  @FXML private ImageView backButtonHovered;
  @FXML private ImageView backButtonPressed;

  // Check Button:
  @FXML private ImageView checkButton;
  @FXML private ImageView checkButtonHovered;
  @FXML private ImageView checkButtonPressed;

  // Small Planks:
  @FXML private ImageView smallPlank;
  @FXML private ImageView smallPlankOutline;
  @FXML private ImageView smallFixed;
  @FXML private Rectangle smallRectangle;
  private DragImage imageSmall;

  // Medium Planks:
  @FXML private ImageView mediumPlank;
  @FXML private ImageView mediumPlankOutline;
  @FXML private ImageView mediumFixed;
  @FXML private Rectangle mediumRectangle;
  private DragImage imageMedium;

  // Large Planks:
  @FXML private ImageView largePlank;
  @FXML private ImageView largePlankOutline;
  @FXML private ImageView largeFixed;
  @FXML private Rectangle largeRectangle;
  private DragImage imageLarge;

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

    imageSmall = new DragImage(smallPlankOutline, smallPlank, smallFixed, smallRectangle);
    imageMedium = new DragImage(mediumPlankOutline, mediumPlank, mediumFixed, mediumRectangle);
    imageLarge = new DragImage(largePlankOutline, largePlank, largeFixed, largeRectangle);
  }

  // Back button logic:
  /**
   * This method is called when the back button is hovered over, placing a shadow over the button.
   */
  @FXML
  private void backHovered() {
    backButtonHovered.setOpacity(1);
  }

  /**
   * This method is called when the back button is un-hovered, restoring the button to its original
   * state.
   */
  @FXML
  private void backUnhovered() {
    backButtonHovered.setOpacity(0);
  }

  /**
   * This method is called when the back button is pressed, causing the image of the button to
   * "sink", indicating that it has been pressed.
   */
  @FXML
  private void backPressed() {
    backButtonPressed.setOpacity(1);
  }

  /**
   * This method is called when the back button is released, changing the current scene to the lava
   * room.
   */
  @FXML
  private void backReleased() {
    backButtonPressed.setOpacity(0);

    // Change scene to lava room
    App.setScene(AppScene.LAVA);
  }

  // Check button logic:
  /**
   * This method is called when the check button is hovered over, placing a shadow over the button.
   */
  @FXML
  private void checkHovered() {
    checkButtonHovered.setOpacity(1);
  }

  /**
   * This method is called when the check button is un-hovered, restoring the button to its original
   * state.
   */
  @FXML
  private void checkUnhovered() {
    checkButtonHovered.setOpacity(0);
  }

  /**
   * This method is called when the check button is pressed, causing the image of the button to
   * "sink" indicating that it has been pressed.
   */
  @FXML
  private void checkPressed() {
    checkButtonPressed.setOpacity(1);
  }

  /**
   * This method is called when the check button is released, checking if the planks have been
   * placed in the correct position. If the planks have been placed correctly, game states are
   * updated accordingly - allowing the user to progress.
   */
  @FXML
  private void checkReleased() {
    checkButtonPressed.setOpacity(0);

    // Check if planks are in the right place:
    if (imageSmall.isCorrectPosition()
        && imageMedium.isCorrectPosition()
        && imageLarge.isCorrectPosition()) {

      // Update game state
      GameState.isLavaGameCompleted = true;
      removePlanks();
      updateTask();

      // Get lava room controller and change bridge state
      LavaRoomController lavaRoomController = App.getLavaRoomController();
      lavaRoomController.setFixedBridge();

      // Notify user that the bridge has been fixed:
      Notifications message =
          NotificationBuilder.createNotification(
              "You have fixed the bridge. You may now\ncross!", "robot");
      message.show();
    } else {
      Notifications message = NotificationBuilder.createNotification("Try again!", "robot");
      message.show();
    }
  }

  // Small Plank Logic:
  /**
   * This method is called when the small plank is hovered over, causing an outline to appear around
   * the plank.
   */
  @FXML
  private void smallHovered() {
    smallPlank.setOpacity(0);
  }

  /**
   * This method is called when the small plank is un-hovered, removing the outline around the
   * plank.
   */
  @FXML
  private void smallUnhovered() {
    smallPlank.setOpacity(1);
  }

  /**
   * This method is called when the small plank is dragged, causing the plank to follow the mouse
   * cursor.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void smallDragged(MouseEvent event) {
    // Disable non outlined image
    smallPlank.setOpacity(0);

    imageSmall.drag(event);
  }

  /**
   * This method is called when the small plank is released, causing the plank to either snap back
   * to its original position or snap to the correct position.
   */
  @FXML
  void smallReleased() {
    imageSmall.released();
  }

  // Medium Plank Logic:
  /** This method is called when the medium plank is hovered over, causing an outline to appear. */
  @FXML
  private void mediumHovered() {
    mediumPlank.setOpacity(0);
  }

  /**
   * This method is called when the medium plank is un-hovered, removing the outline around the
   * plank.
   */
  @FXML
  private void mediumUnhovered() {
    mediumPlank.setOpacity(1);
  }

  /**
   * This method is called when the medium plank is dragged, causing the plank to follow the mouse
   * cursor.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void mediumDragged(MouseEvent event) {
    // Disable non outlined image
    mediumPlank.setOpacity(0);

    imageMedium.drag(event);
  }

  /**
   * This method is called when the medium plank is released, causing the plank to either snap back
   * to its original position or snap to the correct position.
   */
  @FXML
  void mediumReleased() {
    imageMedium.released();
  }

  // Large Plank Logic:
  /** This method is called when the large plank is hovered over, causing an outline to appear. */
  @FXML
  private void largeHovered() {
    largePlank.setOpacity(0);
  }

  /**
   * This method is called when the large plank is un-hovered, removing the outline around the
   * plank.
   */
  @FXML
  private void largeUnhovered() {
    largePlank.setOpacity(1);
  }

  /**
   * This method is called when the large plank is dragged, causing the plank to follow the mouse
   * cursor.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void largeDragged(MouseEvent event) {
    // Disable non outlined image
    largePlank.setOpacity(0);

    imageLarge.drag(event);
  }

  /**
   * This method is called when the large plank is released, causing the plank to either snap back
   * to its original position or snap to the correct position.
   */
  @FXML
  void largeReleased() {
    imageLarge.released();
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
    setBridgeMiniOpacity();
    hideAllLayers();

    // If the small plank is in the correct position update GPT background
    if (imageSmall.isCorrectPosition()) {
      bridgeMiniPlankOneOpacity();
    }

    // If the medium plank is in the correct position update GPT background
    if (imageMedium.isCorrectPosition()) {
      bridgeMiniPlankThreeOpacity();
    }

    // If the large plank is in the correct position update GPT background
    if (imageLarge.isCorrectPosition()) {
      bridgeMiniPlankTwoOpacity();
    }

    // Store current scene in scene stack
    GameState.lastScene = AppScene.BRIDGE_GAME;

    App.setScene(AppScene.CHAT);
  }
}
