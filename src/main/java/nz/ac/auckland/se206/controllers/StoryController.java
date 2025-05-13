package nz.ac.auckland.se206.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javax.speech.AudioException;
import javax.speech.EngineStateError;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.SceneManager.AppScene;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;
import nz.ac.auckland.se206.speech.TextToSpeech;

/** This class handles the logic for the story screen. */
public class StoryController extends ControllerMethods {

  // Buttons are now next instead of back
  @FXML private ImageView backOneButtn;
  @FXML private ImageView backTwoButton;
  @FXML private ImageView backThreeButton;
  @FXML private TextArea openingText;
  @FXML private ImageView audioOne;
  @FXML private ImageView audioTwo;
  @FXML private ImageView audioThree;
  @FXML private ImageView pauseOne;
  @FXML private ImageView pauseTwo;
  @FXML private ImageView pauseThree;

  private String story;
  private TextToSpeech textToSpeech;
  private Thread audioThread;
  private boolean isPlaying;
  private boolean isPaused;

  /**
   * This method is called when the scene is first loaded. Here, the text to speech object is
   * initialized.
   *
   * @throws ApiProxyException If the API key is invalid.
   */
  public void initialize() throws ApiProxyException {
    isPlaying = false;
    isPaused = false;
    audioOne.setOpacity(1);

    // Create text to speech object:
    textToSpeech = new TextToSpeech();

    // Initialize text to be passed into text to speech:
    story =
        "Greetings, traveller. I am an AI called CLOUD, and I must inform you that you find"
            + " yourself trapped in a simulated reality.\n\n"
            + "Explore diverse worlds, solve puzzles, and ignite the portal to reclaim your"
            + " freedom.";

    // Set the text to be displayed on the text area:
    openingText.appendText(story);
    openingText.getStyleClass().add("story-text");
  }

  /**
   * This method is called when the audio button is hovered over, placing a shadow over the button.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void audioHover(MouseEvent event) {
    resetAllOpacities();
    if (isPlaying) {
      pauseTwo.setOpacity(1);
    } else {
      audioTwo.setOpacity(1);
    }
  }

  /**
   * This method is called when the audio button is un-hovered, restoring the button to its original
   * state.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void audioUnhover(MouseEvent event) {
    updateAudioButtonVisibility();
  }

  /**
   * This method is called when the audio button is pressed, causing the image of the button to
   * "sink", indicating that it has been pressed.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void audioPressed(MouseEvent event) {
    resetAllOpacities();
    if (isPlaying) {
      pauseThree.setOpacity(1);
    } else {
      audioThree.setOpacity(1);
    }
  }

  /**
   * This method is called when the audio button is released, starting the text to speech audio.
   *
   * @param event The mouse event that triggered this method.
   * @throws AudioException If the audio cannot be played.
   * @throws EngineStateError If the audio engine is in an invalid state.
   */
  @FXML
  private void audioReleased(MouseEvent event) throws AudioException, EngineStateError {
    // If the TTS is currently playing, we will pause it
    if (isPlaying && !isPaused) {
      textToSpeech.pause();
      isPlaying = false;
      isPaused = true;
      // If the TTS is not currently playing, we will resume the audio
    } else if (isPaused) {
      textToSpeech.resume();
      isPlaying = true;
      isPaused = false;
      // Otherwise, we replay the audio from the very beginning
    } else {
      startAudioFromBeginning();
    }
    updateAudioButtonVisibility();
  }

  /** This method starts the audio from the beginning. */
  private void startAudioFromBeginning() {
    isPlaying = true;
    isPaused = false;

    if (audioThread != null && audioThread.isAlive()) {
      audioThread.interrupt();
    }

    audioThread =
        new Thread(
            () -> {
              textToSpeech.speak(story);
              Platform.runLater(
                  () -> {
                    // Mark audio as finished after it completes
                    isPlaying = false;
                    updateAudioButtonVisibility();
                  });
            });
    audioThread.start();
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
   * "sink", indicating that it has been pressed.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void backPressed(MouseEvent event) {
    backThreeButton.setOpacity(1);
  }

  /**
   * This method is called when the back button is released, progressing the user to the tutorial
   * screen.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void backReleased(MouseEvent event) {
    stopAudioThread();
    backThreeButton.setOpacity(0);
    App.setScene(AppScene.TUTORIAL);
  }

  /** This helper function is called to stop the audio thread - pausing the text to speech. */
  private void stopAudioThread() {
    if (audioThread != null && audioThread.isAlive()) {
      textToSpeech.terminate();
      audioThread.interrupt();
    }
  }

  /** This helper function is called to reset the opacity of all audio buttons. */
  private void resetAllOpacities() {
    pauseOne.setOpacity(0);
    pauseTwo.setOpacity(0);
    pauseThree.setOpacity(0);
    audioOne.setOpacity(0);
    audioTwo.setOpacity(0);
    audioThree.setOpacity(0);
  }

  /**
   * This helper function is called to update the visibility of the audio buttons, depending on the
   * state of the text to speech.
   */
  private void updateAudioButtonVisibility() {
    resetAllOpacities();
    if (isPlaying) {
      pauseOne.setOpacity(1);
    } else {
      audioOne.setOpacity(1);
    }
  }
}
