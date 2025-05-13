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

/** This class handles the logic for the portal terminal scene. */
public class TerminalController extends ControllerMethods {

  @FXML private Label lblTimer;
  @FXML private Label lblTask;
  @FXML private Label lblHints;

  // Inventory Items
  @FXML private ImageView fishingRodIcon;
  @FXML private ImageView axeIcon;
  @FXML private ImageView fishIcon;
  @FXML private ImageView planksIcon;

  @FXML private ImageView backButtonOne;
  @FXML private ImageView backButtonTwo;
  @FXML private ImageView backButtonThree;

  // Check Button:
  @FXML private ImageView redOneButton;
  @FXML private ImageView redTwoButton;
  @FXML private ImageView redThreeButton;

  // Blue Orb:
  @FXML private ImageView blueOrb;
  @FXML private ImageView blueOrbOutline;
  @FXML private ImageView blueOrbFixed;
  @FXML private Rectangle blueOrbPlaced;
  private DragImage blueOrbImage;

  // Green Orb:
  @FXML private ImageView greenOrb;
  @FXML private ImageView greenOrbOutline;
  @FXML private ImageView greenOrbFixed;
  @FXML private Rectangle greenOrbPlaced;
  private DragImage greenOrbImage;

  // Red Orb:
  @FXML private ImageView redOrb;
  @FXML private ImageView redOrbOutline;
  @FXML private ImageView redOrbFixed;
  @FXML private Rectangle redOrbPlaced;
  private DragImage redOrbImage;

  // Game Master
  @FXML private ImageView gameMasterDefault;
  @FXML private ImageView gameMasterChat;

  /** This method is called when the scene is first loaded, binding the labels and items. */
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

    // Bind the inventory images to their image properties
    bindInventoryWithoutOrbs();

    // Initialize the drag and drop helper:
    blueOrbImage = new DragImage(blueOrbOutline, blueOrb, blueOrbFixed, blueOrbPlaced);
    greenOrbImage = new DragImage(greenOrbOutline, greenOrb, greenOrbFixed, greenOrbPlaced);
    redOrbImage = new DragImage(redOrbOutline, redOrb, redOrbFixed, redOrbPlaced);
  }

  // Red Button
  /**
   * This method is called when the red button is hovered over, placing a shadow over the button.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void redButtonHover(MouseEvent event) {
    redTwoButton.setOpacity(1);
  }

  /**
   * This method is called when the red button is un-hovered, restoring the button to its original
   * state.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void redButtonUnhover(MouseEvent event) {
    redTwoButton.setOpacity(0);
  }

  /**
   * This method is called when the red button is pressed, causing the image of the button to
   * "sink", indicating that it has been pressed.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void redButtonPressed(MouseEvent event) {
    redThreeButton.setOpacity(1);
  }

  /**
   * This method is called when the red button is released in which the orbs are checked to see if
   * they are in the correct position. If the orbs have been placed correctly, the portal is opened
   * and the user is able to escape.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void redButtonReleased(MouseEvent event) {
    redThreeButton.setOpacity(0);

    // Check if orbs have been placed:
    if (blueOrbImage.isCorrectPosition()
        && greenOrbImage.isCorrectPosition()
        && redOrbImage.isCorrectPosition()) {

      // Update game state:
      GameState.isOrbsPlaced = true;
      GameState.isPortalOpen = true;
      openPortal();
      removeOrbs();
      updateTask();

      // Notify user they may escape:
      Notifications message =
          NotificationBuilder.createNotification(
              "At last I am free!! The portal has now\nbeen fixed.", "robot");
      message.show();
    } else {
      Notifications message =
          NotificationBuilder.createNotification("Not quite right... Try again!", "robot");
      message.show();
    }
  }

  // Back Button
  /**
   * This method is called when the back button is hovered over, placing a shadow over the button.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void backHover(MouseEvent event) {
    backButtonTwo.setOpacity(1);
  }

  /**
   * This method is called when the back button is un-hovered, restoring the button to its original
   * state.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void backUnhover(MouseEvent event) {
    backButtonTwo.setOpacity(0);
  }

  /**
   * This method is called when the back button is pressed, causing the image of the button to
   * "sink", indicating that it has been pressed.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void backPressed(MouseEvent event) {
    backButtonThree.setOpacity(1);
  }

  /**
   * This method is called when the back button is released, returning the user back to the room.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void backReleased(MouseEvent event) {
    backButtonThree.setOpacity(0);
    App.setScene(AppScene.ROOM);
  }

  // Blue Orb Logic:
  /** This method is called when the blue orb is hovered over, displaying an outline around it. */
  @FXML
  private void blueHovered() {
    blueOrb.setOpacity(0);
  }

  /** This method is called when the blue orb is un-hovered, removing the outline around it. */
  @FXML
  private void blueUnhovered() {
    blueOrb.setOpacity(1);
  }

  /** This method is called when the blue orb is dragged. */
  @FXML
  private void blueDragged(MouseEvent event) {
    blueOrb.setOpacity(0);

    blueOrbImage.drag(event);
  }

  /** This method is called when the blue orb is released. */
  @FXML
  private void blueReleased() {
    blueOrbImage.released();
  }

  // Green Orb Logic:
  /** This method is called when the green orb is hovered over, displaying an outline around it. */
  @FXML
  private void greenHovered() {
    greenOrb.setOpacity(0);
  }

  /** This method is called when the green orb is un-hovered, removing the outline around it. */
  @FXML
  private void greenUnhovered() {
    greenOrb.setOpacity(1);
  }

  /**
   * This method is called when the green orb is dragged.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void greenDragged(MouseEvent event) {
    greenOrb.setOpacity(0);

    greenOrbImage.drag(event);
  }

  /** This method is called when the green orb is released. */
  @FXML
  private void greenReleased() {
    greenOrbImage.released();
  }

  // Red Orb Logic:
  /** This method is called when the red orb is hovered over, displaying an outline around it. */
  @FXML
  private void redHovered() {
    redOrb.setOpacity(0);
  }

  /** This method is called when the red orb is un-hovered, removing the outline around it. */
  @FXML
  private void redUnhovered() {
    redOrb.setOpacity(1);
  }

  /**
   * This method is called when the red orb is dragged.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void redDragged(MouseEvent event) {
    redOrb.setOpacity(0);

    redOrbImage.drag(event);
  }

  /** This method is called when the red orb is released. */
  @FXML
  private void redReleased() {
    redOrbImage.released();
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
    // Initally show all orbs at the bottom
    hideAllLayers();
    setOrbMiniOpacity();
    bottomBlueOrbOpacity();
    bottomGreenOrbOpacity();
    bottomRedOrbOpacity();

    // If an orb has been correctly placed, show it at the top
    // Blue orb
    if (blueOrbImage.isCorrectPosition()) {
      topBlueOrbOpacity();
    }

    // Red orb
    if (redOrbImage.isCorrectPosition()) {
      topRedOrbOpacity();
    }

    // Green rob
    if (greenOrbImage.isCorrectPosition()) {
      topGreenOrbOpacity();
    }

    // Store the current scene in the scene stack:
    GameState.lastScene = AppScene.TERMINAL;

    // Switch to chat scene:
    App.setScene(AppScene.CHAT);
  }
}
