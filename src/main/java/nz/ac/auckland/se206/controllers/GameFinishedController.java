package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * This class handles the logic for the game finished screen. It is responsible for displaying
 * either the game over or game won screen.
 */
public class GameFinishedController extends ControllerMethods {
  @FXML private Label lblTitle;
  @FXML private Label lblSubtitle;

  @FXML private ImageView restartTwoButton;
  @FXML private ImageView restartThreeButton;
  @FXML private ImageView quitTwoButton;
  @FXML private ImageView quitThreeButton;

  // Restart images:
  @FXML private ImageView restartingBackground;
  @FXML private ImageView restartingAnimation;

  /**
   * This method is called when the scene is first loaded. Title message and text messages are
   * initialized.
   */
  public void initialize() {
    // Bind the labels to the message values
    lblTitle.textProperty().bind(ControllerMethods.titleMessage);
    lblSubtitle.textProperty().bind(ControllerMethods.subtitleMessage);
  }

  /**
   * This method is called when the restart button is hovered over, placing a shadow over the
   * button.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void restartHover(MouseEvent event) {
    restartTwoButton.setOpacity(1);
  }

  /**
   * This method is called when the restart button is un-hovered, restoring the button to its
   * original state.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void restartUnhover(MouseEvent event) {
    restartTwoButton.setOpacity(0);
  }

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
   * This method is called when the restart button is released. The game will be restarted,
   * resetting all game states and scenes required.
   *
   * @param event The mouse event that triggered this method.
   * @throws IOException If the game cannot be restarted.
   */
  @FXML
  private void restartReleased(MouseEvent event) throws IOException {
    // Enable restarting images:
    restartingBackground.setDisable(false);
    restartingAnimation.setDisable(false);
    restartingBackground.setOpacity(1);
    restartingAnimation.setOpacity(1);

    restartThreeButton.setOpacity(0);

    // Restart game by resetting game states and re-initializing scenes
    restartGame();
  }

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

  /** Quits the game indefinitely, closing the present window. */
  @FXML
  private void quitReleased(MouseEvent event) {
    quitThreeButton.setOpacity(0);
    System.exit(0);
  }

  /**
   * This method is called when the restarting animation has finished. Resetting the game finished
   * scene.
   */
  public void disableRestartingImages() {
    restartingBackground.setDisable(true);
    restartingAnimation.setDisable(true);
    restartingBackground.setOpacity(0);
    restartingAnimation.setOpacity(0);
  }
}
