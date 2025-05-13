package nz.ac.auckland.se206;

import java.util.HashMap;
import javafx.scene.Parent;

/**
 * This class handles the logic required to manage the scenes in the game - storing the states to be
 * accessed later.
 */
public class SceneManager {

  /** This enum represents the scenes in the game. */
  public enum AppScene {
    START,
    OPTIONS,
    ROOM,
    TERMINAL,
    FOREST,
    FISHING,
    TREES,
    LAVA,
    BRIDGE_GAME,
    CASTLE,
    TUTORIAL,
    CHAT,
    SETTINGS,
    GAMEFINISHED,
    HELP,
    STORY
  }

  // Hashmap which stores the states of scenes
  public static HashMap<AppScene, Parent> sceneMap = new HashMap<AppScene, Parent>();

  /**
   * Adds the inputted scene to the hashmap.
   *
   * @param scene The scene to be added (key).
   * @param root The root of the scene to be added (value).
   */
  public static void addScene(AppScene scene, Parent root) {
    sceneMap.put(scene, root);
  }

  /**
   * Retrieves the inputted scene from the hashmap.
   *
   * @param scene The scene to be retrieved.
   * @return The scene associated with the inputted scene.
   */
  public static Parent getScene(AppScene scene) {
    return sceneMap.get(scene);
  }

  /**
   * Removes all scenes from the hashmap that are required to be reset during restarting the game.
   */
  public static void clearHashMap() {
    // Remove all scenes from the hash map BUT game finished, game over, help, settings, start,
    // story, tutorial, options
    sceneMap.remove(AppScene.BRIDGE_GAME);
    sceneMap.remove(AppScene.CASTLE);
    sceneMap.remove(AppScene.CHAT);
    sceneMap.remove(AppScene.FISHING);
    sceneMap.remove(AppScene.FOREST);
    sceneMap.remove(AppScene.LAVA);
    sceneMap.remove(AppScene.ROOM);
    sceneMap.remove(AppScene.TERMINAL);
    sceneMap.remove(AppScene.TREES);
    sceneMap.remove(AppScene.STORY);
    sceneMap.remove(AppScene.OPTIONS);
  }
}
