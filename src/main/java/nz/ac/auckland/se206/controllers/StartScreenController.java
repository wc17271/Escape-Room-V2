package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.SceneManager.AppScene;

/** This class handles the logic for the start screen controller. */
public class StartScreenController extends ControllerMethods {
  @FXML private ImageView startOneButton;
  @FXML private ImageView startTwoButton;
  @FXML private ImageView startThreeButton;
  @FXML private ImageView quitOneButton;
  @FXML private ImageView quitTwoButton;
  @FXML private ImageView quitThreeButton;

  /**
   * This method is called when the start button is hovered over, placing a shadow over the button.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void startHover(MouseEvent event) {
    startTwoButton.setOpacity(1);
  }

  /**
   * This method is called when the start button is un-hovered, restoring the button to its original
   * state.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void startUnhover(MouseEvent event) {
    startTwoButton.setOpacity(0);
  }

  /**
   * This method is called when the start button is pressed, causing the image of the button to
   * "sink", indicating that it has been pressed.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void startPressed(MouseEvent event) {
    startThreeButton.setOpacity(1);
  }

  /**
   * This method is called when the start button is released, starting the game - directing the user
   * to game mode selection page.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void startReleased(MouseEvent event) {
    startThreeButton.setOpacity(0);
    App.setScene(AppScene.OPTIONS);
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
   * "sink", indicating that it has been pressed.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void quitPressed(MouseEvent event) {
    quitThreeButton.setOpacity(1);
  }

  /**
   * This method is called when the quit button is released, quitting the game and closing the
   * window.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void quitReleased(MouseEvent event) {
    System.exit(0);
  }
}
