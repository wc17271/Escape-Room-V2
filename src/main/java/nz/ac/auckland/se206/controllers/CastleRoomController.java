package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.NotificationBuilder;
import nz.ac.auckland.se206.SceneManager.AppScene;
import org.controlsfx.control.Notifications;

/**
 * This class handles the logic regarding the castle room scene in which the player is required to
 * unlock a chest to obtain the red orb.
 */
public class CastleRoomController extends ControllerMethods {

  @FXML private Label lblTimer;
  @FXML private Label lblTask;
  @FXML private Label lblHints;

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

  // Chest rectangle:
  @FXML private Rectangle chestRectangle;

  // Back buttons
  @FXML private ImageView backButton;
  @FXML private ImageView backButtonHovered;
  @FXML private ImageView backButtonPressed;

  // Check buttons
  @FXML private ImageView checkButton;
  @FXML private ImageView checkButtonHovered;
  @FXML private ImageView checkButtonPressed;

  // Chest images
  @FXML private ImageView chestLocked;
  @FXML private ImageView chestOpenedOrb;
  @FXML private ImageView chestOpenedOrbOutline;
  @FXML private ImageView chestEmpty;

  // Lock 1:
  @FXML private ImageView lockOneIncrement;
  @FXML private ImageView lockOneIncrementHovered;
  @FXML private ImageView lockOneIncrementPressed;

  @FXML private ImageView lockOneDecrement;
  @FXML private ImageView lockOneDecrementHovered;
  @FXML private ImageView lockOneDecrementPressed;

  @FXML private Label lockOneNumber;
  private int lockOneValue = 0;

  // Lock 2:
  @FXML private ImageView lockTwoIncrement;
  @FXML private ImageView lockTwoIncrementHovered;
  @FXML private ImageView lockTwoIncrementPressed;

  @FXML private ImageView lockTwoDecrement;
  @FXML private ImageView lockTwoDecrementHovered;
  @FXML private ImageView lockTwoDecrementPressed;

  @FXML private Label lockTwoNumber;
  private int lockTwoValue = 0;

  // Lock 3:
  @FXML private ImageView lockThreeIncrement;
  @FXML private ImageView lockThreeIncrementHovered;
  @FXML private ImageView lockThreeIncrementPressed;

  @FXML private ImageView lockThreeDecrement;
  @FXML private ImageView lockThreeDecrementHovered;
  @FXML private ImageView lockThreeDecrementPressed;

  @FXML private Label lockThreeNumber;
  private int lockThreeValue = 0;

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

    // Disabled chest rectangle
    chestRectangle.setDisable(true);
  }

  // Methods for back button animations:
  /**
   * This method is called when the back button is hovered over, placing a shadow over the button.
   */
  @FXML
  private void backHover() {
    backButtonHovered.setOpacity(1);
  }

  /**
   * This method is called when the back button is un-hovered, restoring the button to its original
   * state.
   */
  @FXML
  private void backUnhover() {
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

    App.setScene(AppScene.LAVA);
  }

  // Methods for check button animations:
  /**
   * This method is called when the check button is hovered over, placing a shadow over the button.
   */
  @FXML
  private void checkHover() {
    checkButtonHovered.setOpacity(1);
  }

  /**
   * This method is called when the check button is un-hovered, restoring the button to its original
   * state.
   */
  @FXML
  private void checkUnhover() {
    checkButtonHovered.setOpacity(0);
  }

  /**
   * This method is called when the check button is pressed, causing the image of the button to
   * "sink", indicating that it has been pressed.
   */
  @FXML
  private void checkPressed() {
    checkButtonPressed.setOpacity(1);
  }

  /**
   * This method is called when the check button is released, checking if the code the user has
   * inputted is correct. If the code is correct, reveal to the user a red orb
   */
  @FXML
  private void checkReleased() {
    checkButtonPressed.setOpacity(0);

    if (GameState.isChestUnlocked) {
      Notifications message =
          NotificationBuilder.createNotification("Oi! Stop messing with the lock!", "robot");
      message.show();
      return;
    }

    if (lockOneValue == GameState.firstDigit
        && lockTwoValue == GameState.secondDigit
        && lockThreeValue == GameState.thirdDigit) {

      // Enable chest rectangle
      chestRectangle.setDisable(false);

      // Update state of the game:
      GameState.isChestUnlocked = true;
      updateTask();

      // Notify the user that the answer is correct:
      Notifications message =
          NotificationBuilder.createNotification("Ooooooh, I wonder whats inside!", "redOrb");
      message.show();

      // Show the chest with the orb inside:
      chestOpenedOrb.setOpacity(1);

    } else {
      // Notify the user that the answer is incorrect:
      Notifications message = NotificationBuilder.createNotification("Try again!", "robot");
      message.show();
    }
  }

  // Methods for pad lock animations:
  // Lock 1:
  /**
   * This method is called when the first lock increment button is hovered over, placing a shadow
   * over the button.
   */
  @FXML
  private void lockOneIncrementHover() {
    lockOneIncrementHovered.setOpacity(1);
  }

  /**
   * This method is called when the first lock increment button is un-hovered, restoring the button
   * to its original state.
   */
  @FXML
  private void lockOneIncrementUnhover() {
    lockOneIncrementHovered.setOpacity(0);
  }

  /**
   * This method is called when the first lock increment button is pressed, causing the image of the
   * button to "sink", indicating that it has been pressed.
   */
  @FXML
  private void lockOneIncrementPressed() {
    lockOneIncrementPressed.setOpacity(1);
  }

  /**
   * This method is called when the first lock increment button is released, incrementing the value
   * of the first lock.
   */
  @FXML
  private void lockOneIncrementReleased() {
    lockOneIncrementPressed.setOpacity(0);

    if (lockOneValue == 9) {
      return;
    }

    lockOneNumber.setText(String.valueOf(++lockOneValue));
  }

  /**
   * This method is called when the first lock decrement button is hovered over, placing a shadow
   * over the button.
   */
  @FXML
  private void lockOneDecrementHover() {
    lockOneDecrementHovered.setOpacity(1);
  }

  /**
   * This method is called when the first lock decrement button is un-hovered, restoring the button
   * to its original state.
   */
  @FXML
  private void lockOneDecrementUnhover() {
    lockOneDecrementHovered.setOpacity(0);
  }

  /**
   * This method is called when the first lock decrement button is pressed, causing the image of the
   * button to "sink", indicating that it has been pressed.
   */
  @FXML
  private void lockOneDecrementPressed() {
    lockOneDecrementPressed.setOpacity(1);
  }

  /**
   * This method is called when the first lock decrement button is released, decrementing the value
   * of the first lock.
   */
  @FXML
  private void lockOneDecrementReleased() {
    lockOneDecrementPressed.setOpacity(0);

    if (lockOneValue == 0) {
      return;
    }

    lockOneNumber.setText(String.valueOf(--lockOneValue));
  }

  // Lock 2:
  /**
   * This method is called when the second lock increment button is hovered over, placing a shadow
   * over the button.
   */
  @FXML
  private void lockTwoIncrementHover() {
    lockTwoIncrementHovered.setOpacity(1);
  }

  /**
   * This method is called when the second lock increment button is un-hovered, restoring the button
   * to its original state.
   */
  @FXML
  private void lockTwoIncrementUnhover() {
    lockTwoIncrementHovered.setOpacity(0);
  }

  /**
   * This method is called when the second lock increment button is pressed, causing the image of
   * the button to "sink", indicating that it has been pressed.
   */
  @FXML
  private void lockTwoIncrementPressed() {
    lockTwoIncrementPressed.setOpacity(1);
  }

  /**
   * This method is called when the second lock increment button is released, incrementing the value
   * of the second lock.
   */
  @FXML
  private void lockTwoIncrementReleased() {
    lockTwoIncrementPressed.setOpacity(0);

    if (lockTwoValue == 9) {
      return;
    }

    lockTwoNumber.setText(String.valueOf(++lockTwoValue));
  }

  /**
   * This method is called when the second lock decrement button is hovered over, placing a shadow
   * over the button.
   */
  @FXML
  private void lockTwoDecrementHover() {
    lockTwoDecrementHovered.setOpacity(1);
  }

  /**
   * This method is called when the second lock decrement button is un-hovered, restoring the button
   * to its original state.
   */
  @FXML
  private void lockTwoDecrementUnhover() {
    lockTwoDecrementHovered.setOpacity(0);
  }

  /**
   * This method is called when the second lock decrement button is pressed, causing the image of
   * the button to "sink", indicating that it has been pressed.
   */
  @FXML
  private void lockTwoDecrementPressed() {
    lockTwoDecrementPressed.setOpacity(1);
  }

  /**
   * This method is called when the second lock decrement button is released, decrementing the value
   * of the second lock.
   */
  @FXML
  private void lockTwoDecrementReleased() {
    lockTwoDecrementPressed.setOpacity(0);

    if (lockTwoValue == 0) {
      return;
    }

    lockTwoNumber.setText(String.valueOf(--lockTwoValue));
  }

  // Lock 3:
  /**
   * This method is called when the third lock increment button is hovered over, placing a shadow
   * over the button.
   */
  @FXML
  private void lockThreeIncrementHover() {
    lockThreeIncrementHovered.setOpacity(1);
  }

  /**
   * This method is called when the third lock increment button is un-hovered, restoring the button
   * to its original state.
   */
  @FXML
  private void lockThreeIncrementUnhover() {
    lockThreeIncrementHovered.setOpacity(0);
  }

  /**
   * This method is called when the third lock increment button is pressed, causing the image of the
   * button to "sink", indicating that it has been pressed.
   */
  @FXML
  private void lockThreeIncrementPressed() {
    lockThreeIncrementPressed.setOpacity(1);
  }

  /**
   * This method is called when the third lock increment button is released, incrementing the value
   * of the third lock.
   */
  @FXML
  private void lockThreeIncrementReleased() {
    lockThreeIncrementPressed.setOpacity(0);

    if (lockThreeValue == 9) {
      return;
    }

    lockThreeNumber.setText(String.valueOf(++lockThreeValue));
  }

  /**
   * This method is called when the third lock decrement button is hovered over, placing a shadow
   * over the button.
   */
  @FXML
  private void lockThreeDecrementHover() {
    lockThreeDecrementHovered.setOpacity(1);
  }

  /**
   * This method is called when the third lock decrement button is un-hovered, restoring the button
   * to its original state.
   */
  @FXML
  private void lockThreeDecrementUnhover() {
    lockThreeDecrementHovered.setOpacity(0);
  }

  /**
   * This method is called when the third lock decrement button is pressed, causing the image of the
   * button to "sink", indicating that it has been pressed.
   */
  @FXML
  private void lockThreeDecrementPressed() {
    lockThreeDecrementPressed.setOpacity(1);
  }

  /**
   * This method is called when the third lock decrement button is released, decrementing the value
   * of the third lock.
   */
  @FXML
  private void lockThreeDecrementReleased() {
    lockThreeDecrementPressed.setOpacity(0);

    if (lockThreeValue == 0) {
      return;
    }

    lockThreeNumber.setText(String.valueOf(--lockThreeValue));
  }

  // Methods for chest animations:
  /** This method is called when the orb is hovered over, showing an outline around the orb. */
  @FXML
  private void orbHovered() {
    chestOpenedOrbOutline.setOpacity(1);
  }

  /** This method is called when the orb is un-hovered, removing the outline around the orb. */
  @FXML
  private void orbUnhovered() {
    chestOpenedOrbOutline.setOpacity(0);
  }

  /**
   * This method is called when the orb is pressed, removing the orb from the chest and placing it
   * in the inventory.
   */
  @FXML
  private void orbPressed() {
    chestEmpty.setOpacity(1);

    // Update orb state:
    GameState.isCastleOrbCollected = true;
    updateTask();

    // Put the orb into inventory
    findRedOrb();
  }

  // Bottom Right Game Master Button
  /**
   * This method is called when the game master button is hovered over, causing a "chat" icon to
   * appear.
   */
  @FXML
  private void gameMasterOnHover() {
    gameMasterDefault.setOpacity(0);
    gameMasterChat.setOpacity(1);
  }

  /**
   * This method is called when the game master button is un-hovered, causing the "chat" icon to
   * disappear.
   */
  @FXML
  private void gameMasterOnUnhover() {
    gameMasterDefault.setOpacity(1);
    gameMasterChat.setOpacity(0);
  }

  /**
   * This method is called when the game master button is pressed, changing the current scene to the
   * chat scene.
   */
  @FXML
  private void gameMasterOnClick() {
    hideAllLayers();
    // Close chest
    if (!GameState.isChestUnlocked) {
      setChestMiniOpacity();
      // Remove red orb from chest
    } else if (GameState.isChestUnlocked && GameState.isCastleOrbCollected) {
      setOpenChestMiniOpacity();
      // Add red orb in chest
    } else {
      setOpenChestMiniOpacity();
      redOrbInChestOpacity();
    }

    // Store the current scene in the scene stack:
    GameState.lastScene = AppScene.CASTLE;

    App.setScene(AppScene.CHAT);
  }
}
