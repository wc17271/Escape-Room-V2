package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;

/** This class handles the logic for the help screen. */
public class HelpController extends ControllerMethods {

  @FXML private Label lblTimer;
  @FXML private Label lblTask;
  @FXML private Label lblHints;

  @FXML private ImageView backTwoButton;
  @FXML private ImageView backThreeButton;
  @FXML private Label taskText;

  /** This method is called when the scene is first loaded. */
  public void initialize() {

    // Bind the labels to the display values and styles
    lblTimer.textFillProperty().bind(ControllerMethods.timerTextFill);

    // Update tutorial text
    taskText.setText("If in doubt, refer to the task list\nat the top left!");

    // Bind the labels to the display values

    lblTimer.textProperty().bind(ControllerMethods.displayTime);
    lblTask.textProperty().bind(ControllerMethods.displayTask);
    lblHints.textProperty().bind(ControllerMethods.displayHints);
  }

  /**
   * This method is called when the back button is hovered over, placing a shadow over the button.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void backHover(MouseEvent event) {
    backTwoButton.setOpacity(1);
  }

  /**
   * This method is called when the back button is un-hovered, restoring the button to its original
   * state.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void backUnhover(MouseEvent event) {
    backTwoButton.setOpacity(0);
  }

  /**
   * This method is called when the back button is pressed, causing the image of the button to
   * "sink" indicating that it has been pressed.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void backPressed(MouseEvent event) {
    backThreeButton.setOpacity(1);
  }

  /**
   * This method is called when the back button is pressed, returning the user back to the previous
   * scene.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void backReleased(MouseEvent event) {
    backThreeButton.setOpacity(0);

    // Return to previous scene by popping stack:
    App.setScene(GameState.lastScene);
  }
}
