package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;

/** This class handles the logic for the settings screen. */
public class SettingsController extends ControllerMethods {

  @FXML private Label lblTimer;
  @FXML private Label lblTask;
  @FXML private Label lblHints;

  @FXML private ImageView backButtonTwo;
  @FXML private ImageView backButtonThree;
  @FXML private ImageView restartTwoButton;
  @FXML private ImageView restartThreeButton;
  @FXML private ImageView quitTwoButton;
  @FXML private ImageView quitThreeButton;
  @FXML private Rectangle backButton;
  @FXML private Rectangle restartButton;

  // Restart images:
  @FXML private ImageView restartBackground;
  @FXML private ImageView restartAnimation;

  /** This method is called when the scene is first loaded. */
  public void initialize() {
    // Bind the labels to the display values and styles
    lblTimer.textFillProperty().bind(ControllerMethods.timerTextFill);
    lblTimer.textProperty().bind(ControllerMethods.displayTime);
    lblTask.textProperty().bind(ControllerMethods.displayTask);
    lblHints.textProperty().bind(ControllerMethods.displayHints);
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
   * "sink" indicating that it has been pressed.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void backPressed(MouseEvent event) {
    backButtonThree.setOpacity(1);
  }

  /**
   * This method is called when the back button is pressed, returning the user back to the previous
   * scene.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void backReleased(MouseEvent event) {
    backButtonThree.setOpacity(0);
    // Return to previous scene by popping stack:
    App.setScene(GameState.lastScene);
  }

  /**
   * This method is called when the back button is hovered over, placing a shadow over the button.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void restartHover(MouseEvent event) {
    restartTwoButton.setOpacity(1);
  }

  /**
   * This method is called when the back button is un-hovered, restoring the button to its original
   * state.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void restartUnhover(MouseEvent event) {
    restartTwoButton.setOpacity(0);
  }

  // Restart Button
  /**
   * This method is called when the restart button is pressed, causing the image of the button to
   * "sink" indicating that it has been pressed.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void restartPressed(MouseEvent event) {
    restartThreeButton.setOpacity(1);
  }

  /**
   * This method is called when the restart button is released, causing the game to restart -
   * re-initializing the scenes and resetting game states.
   *
   * @param event The mouse event that triggered this method.
   * @throws IOException If the fxml file cannot be found.
   */
  @FXML
  private void restartReleased(MouseEvent event) throws IOException {
    // Enable and show restarting images
    restartBackground.setDisable(false);
    restartAnimation.setDisable(false);
    restartBackground.setOpacity(1);
    restartAnimation.setOpacity(1);

    restartThreeButton.setOpacity(0);

    // Restart game by resetting game states and re-initializing scenes
    restartGame();
  }

  // Quit Button
  /**
   * This method is called when the quit button is hovered over, placing a shadow over the button.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void quitHover(MouseEvent event) {
    quitTwoButton.setOpacity(1);
  }

  /**
   * This method is called when the quit button is un-hovered, restoring the button to its original
   * state.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void quitUnhover(MouseEvent event) {
    quitTwoButton.setOpacity(0);
  }

  /**
   * This method is called when the quit button is pressed, causing the image of the button to
   * "sink" indicating that it has been pressed.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void quitPressed(MouseEvent event) {
    quitThreeButton.setOpacity(1);
  }

  /**
   * Quits the game, closing the application.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void quitReleased(MouseEvent event) {
    quitThreeButton.setOpacity(0);
    System.exit(0);
  }

  /**
   * Returns the background node for the restart animation.
   *
   * @return The background node for the restart animation.
   */
  public Node getRestartBackground() {
    return null;
  }

  /** Helper function that disables the restart images from the settings controller. */
  public void disableRestartImages() {
    restartBackground.setDisable(true);
    restartAnimation.setDisable(true);
    restartBackground.setOpacity(0);
    restartAnimation.setOpacity(0);
  }
}
