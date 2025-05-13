package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.PopOverBuilder;
import nz.ac.auckland.se206.SceneManager.AppScene;
import org.controlsfx.control.PopOver;

/** This class handles the logic for the option screen. */
public class OptionScreenController extends ControllerMethods {
  @FXML private ImageView easyOneButton;
  @FXML private ImageView easyTwoButton;
  @FXML private ImageView easyThreeButton;
  @FXML private ImageView mediumOneButton;
  @FXML private ImageView mediumTwoButton;
  @FXML private ImageView mediumThreeButton;
  @FXML private ImageView hardOneButton;
  @FXML private ImageView hardTwoButton;
  @FXML private ImageView hardThreeButton;
  @FXML private ImageView shortOneButton;
  @FXML private ImageView shortTwoButton;
  @FXML private ImageView shortThreeButton;
  @FXML private ImageView mediumTimeOneButton;
  @FXML private ImageView mediumTimeTwoButton;
  @FXML private ImageView mediumTimeThreeButton;
  @FXML private ImageView longOneButton;
  @FXML private ImageView longTwoButton;
  @FXML private ImageView longThreeButton;
  @FXML private ImageView backOneButton;
  @FXML private ImageView backTwoButton;
  @FXML private ImageView backThreeButton;
  @FXML private Rectangle easyButtonBox;
  @FXML private Rectangle mediumButtonBox;
  @FXML private Rectangle hardButtonBox;
  @FXML private Rectangle shortButtonBox;
  @FXML private Rectangle mediumTimeButtonBox;
  @FXML private Rectangle longButtonBox;

  private PopOver easyPopOver = PopOverBuilder.createPopOver("  Infinite Hints  ");
  private PopOver mediumPopOver = PopOverBuilder.createPopOver("  Five Hints  ");
  private PopOver hardPopOver = PopOverBuilder.createPopOver("  Zero hints  ");

  /* Difficulty */
  // Easy
  /**
   * This method is called when the easy button is hovered over, placing a shadow over the button
   * and the number of hints available to the user.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void easyHover(MouseEvent event) {
    easyTwoButton.setOpacity(1);
    easyPopOver.show(easyButtonBox);
  }

  /**
   * This method is called when the easy button is un-hovered, restoring the button to its original
   * state.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void easyUnhover(MouseEvent event) {
    easyTwoButton.setOpacity(0);
    easyPopOver.hide();
  }

  /**
   * This method is called when the easy button is clicked - selecting the number of hints available
   * to the user.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void easyClicked(MouseEvent event) {
    if (GameState.isEasySelected) {
      return;
    }
    // Unselect all difficulty buttons
    unselectDifficultyButtons();

    // Select the clicked button
    easyPopOver.hide();
    GameState.isEasySelected = true;
    GameState.hintCount = 9999;
    easyThreeButton.setOpacity(1);
  }

  // Medium
  /**
   * This method is called when the medium button is hovered over, placing a shadow over the button.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void mediumHover(MouseEvent event) {
    mediumTwoButton.setOpacity(1);
    mediumPopOver.show(mediumButtonBox);
  }

  /**
   * This method is called when the medium button is un-hovered, restoring the button to its
   * original state.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void mediumUnhover(MouseEvent event) {
    mediumTwoButton.setOpacity(0);
    mediumPopOver.hide();
  }

  /**
   * This method is called when the medium button is clicked - selecting the number of hints
   * available to the user.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void mediumClicked(MouseEvent event) {
    if (GameState.isMediumSelected) {
      return;
    }
    // Unselect all difficulty buttons
    unselectDifficultyButtons();

    // Select the clicked button
    GameState.isMediumSelected = true;
    mediumPopOver.hide();
    GameState.hintCount = 5;
    mediumThreeButton.setOpacity(1);
  }

  // Hard
  /**
   * This method is called when the hard button is hovered over, placing a shadow over the button.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void hardHover(MouseEvent event) {
    hardTwoButton.setOpacity(1);
    hardPopOver.show(hardButtonBox);
  }

  /**
   * This method is called when the hard button is un-hovered, restoring the button to its original
   * state.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void hardUnhover(MouseEvent event) {
    hardTwoButton.setOpacity(0);
    hardPopOver.hide();
  }

  /**
   * This method is called when the hard button is clicked - selecting the number of hints available
   * to the user.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void hardClicked(MouseEvent event) {
    if (GameState.isHardSelected) {
      return;
    }
    // Unselect all difficulty buttons
    unselectDifficultyButtons();

    // Select the clicked button
    GameState.isHardSelected = true;
    hardPopOver.hide();
    GameState.hintCount = 0;
    hardThreeButton.setOpacity(1);
  }

  /* Time */
  // Short Time
  /**
   * This method is called when the short time button is hovered over, placing a shadow over the
   * button.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void shortTimeHover(MouseEvent event) {
    shortTwoButton.setOpacity(1);
  }

  /**
   * This method is called when the short time button is un-hovered, restoring the button to its
   * original state.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void shortTimeUnhover(MouseEvent event) {
    shortTwoButton.setOpacity(0);
  }

  /**
   * This method is called when the short time button is clicked - selecting the amount of time the
   * user has to complete the game.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void shortTimeClicked(MouseEvent event) {
    if (GameState.isShortTimeSelected) {
      return;
    }
    // Unselect all time buttons
    unselectTimeButtons();

    // Select the clicked button
    GameState.isShortTimeSelected = true;
    GameState.timerCount = 120;
    GameState.timerString = "Time Left: 2:00";
    shortThreeButton.setOpacity(1);
  }

  // Medium Time
  /**
   * This method is called when the medium time button is hovered over, placing a shadow over the
   * button.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void mediumTimeHover(MouseEvent event) {
    mediumTimeTwoButton.setOpacity(1);
  }

  /**
   * This method is called when the medium time button is un-hovered, restoring the button to its
   * original state.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void mediumTimeUnhover(MouseEvent event) {
    mediumTimeTwoButton.setOpacity(0);
  }

  /**
   * This method is called when the medium time button is clicked - selecting the amount of time the
   * user has to complete the game.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void mediumTimeClicked(MouseEvent event) {
    if (GameState.isMediumTimeSelected) {
      return;
    }
    // Unselect all time buttons
    unselectTimeButtons();

    // Select the clicked button
    GameState.isMediumTimeSelected = true;
    GameState.timerCount = 240;
    GameState.timerString = "Time Left: 4:00";
    mediumTimeThreeButton.setOpacity(1);
  }

  // Long Time
  /**
   * This method is called when the long time button is hovered over, placing a shadow over the
   * button.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void longTimeHover(MouseEvent event) {
    longTwoButton.setOpacity(1);
  }

  /**
   * This method is called when the long time button is un-hovered, restoring the button to its
   * original state.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void longTimeUnhover(MouseEvent event) {
    longTwoButton.setOpacity(0);
  }

  /**
   * This method is called when the long time button is clicked - selecting the amount of time the
   * user has to complete the game.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void longTimeClicked(MouseEvent event) {
    if (GameState.isLongTimeSelected) {
      return;
    }
    // Unselect all time buttons
    unselectTimeButtons();

    // Select the clicked button
    GameState.isLongTimeSelected = true;
    GameState.timerCount = 360;
    GameState.timerString = "Time Left: 6:00";
    longThreeButton.setOpacity(1);
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
   * This method is called when the back button is clicked - returning the user to the story screen.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void backPressed(MouseEvent event) {
    backThreeButton.setOpacity(1);
  }

  /**
   * This method is called when the back button is released, progressing the user to the story
   * screen.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void backReleased(MouseEvent event) {
    backThreeButton.setOpacity(0);
    App.setScene(AppScene.STORY);
  }

  /**
   * This helper method is called when the player selects a difficulty button, unselecting all other
   * difficulty buttons.
   */
  private void unselectDifficultyButtons() {
    GameState.isEasySelected = false;
    GameState.isMediumSelected = false;
    GameState.isHardSelected = false;

    // Set opacity for all difficulty buttons accordingly
    easyThreeButton.setOpacity(0);
    mediumThreeButton.setOpacity(0);
    hardThreeButton.setOpacity(0);
  }

  /**
   * This helper method is called when the player selects a time button, unselecting all other time
   * buttons.
   */
  private void unselectTimeButtons() {
    GameState.isShortTimeSelected = false;
    GameState.isMediumTimeSelected = false;
    GameState.isLongTimeSelected = false;

    // Set opacity for all time buttons accordingly
    shortThreeButton.setOpacity(0);
    mediumTimeThreeButton.setOpacity(0);
    longThreeButton.setOpacity(0);
  }
}
