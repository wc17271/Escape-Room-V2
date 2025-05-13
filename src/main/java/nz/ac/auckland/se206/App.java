package nz.ac.auckland.se206;

import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import nz.ac.auckland.se206.SceneManager.AppScene;
import nz.ac.auckland.se206.controllers.ChatController;
import nz.ac.auckland.se206.controllers.ForestRoomController;
import nz.ac.auckland.se206.controllers.GameFinishedController;
import nz.ac.auckland.se206.controllers.LavaRoomController;
import nz.ac.auckland.se206.controllers.SettingsController;

/**
 * This is the entry point of the JavaFX application, while you can change this class, it should
 * remain as the class that runs the JavaFX application.
 */
public class App extends Application {

  private static Scene currentScene;

  // Used to store a reference which can be passed:
  private static LavaRoomController lavaRoomController;
  private static ForestRoomController forestRoomController;
  private static ChatController chatController;
  private static GameFinishedController gameFinishedController;
  private static SettingsController settingsController;
  private static Stage currentStage;

  /**
   * The main method that launches the JavaFX application.
   *
   * @param args The command line arguments.
   */
  public static void main(final String[] args) {
    launch();
  }

  /**
   * Returns a stage reference:
   *
   * @return Stage reference.
   */
  public static Stage getStage() {
    return currentStage;
  }

  /**
   * Returns the node associated to the input file. The method expects that the file is located in
   * "src/main/resources/fxml".
   *
   * @param fxml The name of FXML files to load without the .fxml extension.
   * @return The node associated to the input file.
   * @throws IOException If the file is not found.
   */
  private static FXMLLoader loadLoader(String fxml) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/" + fxml + ".fxml"));
    return fxmlLoader;
  }

  /**
   * Used to set scenes that DO require the state the be saved.
   *
   * @param scene The scene to be set.
   */
  public static void setScene(AppScene scene) {
    currentScene.setRoot(SceneManager.getScene(scene));
  }

  /**
   * Returns the forest room controller.
   *
   * @return Reference to the forest room controller.
   */
  public static ForestRoomController getForestRoomController() {
    return forestRoomController;
  }

  /**
   * Returns the lava room controller.
   *
   * @return Reference to the lava room controller.
   */
  public static LavaRoomController getLavaRoomController() {
    return lavaRoomController;
  }

  /**
   * Returns the chat controller.
   *
   * @return Reference to the chat controller.
   */
  public static ChatController getChatController() {
    return chatController;
  }

  /**
   * Returns the game finished controller.
   *
   * @return Reference to the game finished controller.
   */
  public static GameFinishedController getGameFinishedController() {
    return gameFinishedController;
  }

  /**
   * Returns the settings controller.
   *
   * @return Reference to the settings controller.
   */
  public static SettingsController getSettingsController() {
    return settingsController;
  }

  /**
   * This method is invoked when the application starts. It loads and shows the "Start" scene.
   *
   * @param stage The primary stage of the application.
   * @throws IOException If "src/main/resources/fxml/canvas.fxml" is not found.
   */
  @Override
  public void start(final Stage stage) throws IOException {
    InputStream fontStream = App.class.getResourceAsStream("/fonts/Adapa.ttf");
    Font.loadFont(fontStream, 16);
    initializeGame();

    // Add scenes to hashmap - These will not be cleared:
    SceneManager.addScene(AppScene.START, loadLoader("start").load());
    SceneManager.addScene(AppScene.TUTORIAL, loadLoader("tutorial").load());
    SceneManager.addScene(AppScene.HELP, loadLoader("help").load());

    // Store reference to game finished controller:
    FXMLLoader gameFinished = loadLoader("gamefinished");
    SceneManager.addScene(AppScene.GAMEFINISHED, gameFinished.load());
    gameFinishedController = gameFinished.getController();

    // Store refernce to settings controller:
    FXMLLoader settings = loadLoader("settings");
    SceneManager.addScene(AppScene.SETTINGS, settings.load());
    settingsController = settings.getController();

    // Store stage reference:
    currentStage = stage;

    // Fetch start scene from hashmap and set scene:
    currentScene = new Scene(loadLoader("start").load(), 800, 625);
    currentScene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
    stage.setScene(currentScene);
    stage.setResizable(false);
    stage.show();

    stage.setOnCloseRequest(e -> System.exit(0));
  }

  /**
   * This method is invoked when the application starts - re-initializing the game states and
   * scenes.
   *
   * @throws IOException If any of the FXML files are not found.
   */
  public static void initializeGame() throws IOException {
    // Set initial game states:
    GameState.resetGameState();

    SceneManager.addScene(AppScene.BRIDGE_GAME, loadLoader("bridgeGame").load());
    SceneManager.addScene(AppScene.CASTLE, loadLoader("castleRoom").load());
    SceneManager.addScene(AppScene.FISHING, loadLoader("fishingMiniGame").load());
    SceneManager.addScene(AppScene.ROOM, loadLoader("room").load());
    SceneManager.addScene(AppScene.TERMINAL, loadLoader("terminal").load());
    SceneManager.addScene(AppScene.TREES, loadLoader("treeChoppingMiniGame").load());
    SceneManager.addScene(AppScene.STORY, loadLoader("story").load());
    SceneManager.addScene(AppScene.OPTIONS, loadLoader("options").load());

    // Store reference to chat controller
    FXMLLoader chat = loadLoader("chat");
    SceneManager.addScene(AppScene.CHAT, chat.load());
    chatController = chat.getController();

    // Store references to the forest room controller:
    FXMLLoader forestRoom = loadLoader("forestRoom");
    SceneManager.addScene(AppScene.FOREST, forestRoom.load());
    forestRoomController = forestRoom.getController();

    // Store reference to the lava room controller:
    FXMLLoader lavaRoom = loadLoader("lavaRoom");
    SceneManager.addScene(AppScene.LAVA, lavaRoom.load());
    lavaRoomController = lavaRoom.getController();
  }
}
