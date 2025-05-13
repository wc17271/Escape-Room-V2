package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameMaster;
import nz.ac.auckland.se206.NotificationBuilder;
import nz.ac.auckland.se206.SceneManager.AppScene;
import nz.ac.auckland.se206.gpt.ChatMessage;
import nz.ac.auckland.se206.gpt.GptPromptEngineering;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;
import org.controlsfx.control.Notifications;

/** This class handles the logic for the tutorial screen. */
public class TutorialController extends ControllerMethods {

  private ChatMessage chatMessage;
  private GameMaster gameMaster;
  @FXML private ImageView startOneButton;
  @FXML private ImageView startTwoButton;
  @FXML private ImageView startThreeButton;
  @FXML private Label taskText;

  /**
   * This method is called when the scene is first loaded.
   *
   * @throws ApiProxyException If the API key is invalid.
   */
  public void initialize() throws ApiProxyException {

    // Update tutorial text
    taskText.setText("If in doubt, refer to the task list\nat the top left!");

    // Initialize game master object:
    gameMaster = new GameMaster();
    gameMaster.chatCompletionRequest();

    // Pass message to AI and fetch response:
    ChatMessage msg =
        new ChatMessage(
            "user", GptPromptEngineering.chatWithGameMaster("the player being stuck in the room"));
    chatMessage = gameMaster.runGameMaster(msg);
  }

  /**
   * Switches the scene from the tutorial GUI to start GUI and starts timer.
   *
   * @throws IOException If input or ouput error occurs.
   */
  private void onLaunchGame() throws IOException {

    // Start timer, update task and update hints remaining
    startTimer();
    updateTask();
    updateHintsRemaining();

    // Fetch message from AI and show:
    Notifications message =
        NotificationBuilder.createNotification(chatMessage.getContent(), "robot");

    // Add a 0.5sec delay to the initial notification
    PauseTransition delay = new PauseTransition(Duration.millis(350));
    delay.setOnFinished(
        event -> {
          message.show();
        });
    delay.play();

    App.setScene(AppScene.ROOM);
  }

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
   * This method is called when the start button is released, progressing the escape room.
   *
   * @param event The mouse event that triggered this method.
   * @throws IOException If I/O error occurs.
   */
  @FXML
  private void startReleased(MouseEvent event) throws IOException {
    startThreeButton.setOpacity(0);
    onLaunchGame();
  }
}
