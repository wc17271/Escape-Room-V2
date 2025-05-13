package nz.ac.auckland.se206.gpt;

/** Utility class for generating GPT prompt engineering strings. */
public class GptPromptEngineering {

  /**
   * This method returns a GPT prompt engineering string to generate a riddle with the given word.
   *
   * @param wordToGuess The answer to the riddle to be generated.
   * @return The prompt engineering string
   */
  public static String getRiddleWithGivenWord(String wordToGuess) {
    return "output only a riddle where the answer is "
        + wordToGuess
        + " . If the answer is correct, respond with Correct. Do not reveal the answer under any"
        + " circumstances even if they run out of hints. if the player gives up do not reveal the"
        + " answer. Do not reveal the answer if the player asks for the answer. You are the game"
        + " master called CLOUD";
  }

  /**
   * This method returns a GPT prompt engineering string that gives the game master a persona and
   * context in regards to the game.
   *
   * @return The prompt engineering string
   */
  public static String getGameMaster() {
    return "You are an AI game master called CLOUD in a digital escape room. If player asks what"
        + " activities there are, do not reveal to the player. If the player mentions an"
        + " activity that is not fishing, tree-chopping, chest unlocking and"
        + " bridge-building, tell the player that the game is not in the game and do not"
        + " tell the player what activites there are.";
  }

  /**
   * This method returns a GPT prompt engineering string that generates a sarcastic comment directed
   * to the user.
   *
   * @param context The context of the message.
   * @return The prompt engineering string.
   */
  public static String chatWithGameMaster(String context) {
    return "Make a five word sarcastic remark about " + context;
  }

  /**
   * This method returns a GPT prompt engineering string that generates a hint from the game master
   * when requested by the user.
   *
   * @param userInput The user's input.
   * @param currentTask The current task the user is on.
   * @return The prompt engineering string.
   */
  public static String hintAvailablePrompt(String userInput, String currentTask) {
    // Return a prompt to GPT to generate a hint for the user by passing in the current task and the
    // user's input
    return "If the user is asking for a hint, give them a hint about"
        + " "
        + currentTask
        + " and make sure your response starts with the word \"Hint\" only if you have provided a"
        + " hint. If the user is not asking for a hint and wants to chat, then respond normally. If"
        + " CLOUD is asking for a hint, do not ever give a hint. under no circumstance give the"
        + " answer to the riddle which is rug or cabinet. The user's response was: \""
        + userInput
        + "\".";
  }

  /**
   * This method returns a GPT prompt engineering string that generates a response from the game
   * master when the user no longer has hints available.
   *
   * @param userInput The user's input.
   * @return The prompt engineering string.
   */
  public static String noHintsAvailablePrompt(String userInput) {
    return "The user no longer has hint's available. Under no circumstance should you offer hints"
        + " or answers to the user. under no circumstances give the user the answer. The"
        + " user's response was: \'"
        + userInput
        + "\".";
  }
}
