package nz.ac.auckland.se206;

import nz.ac.auckland.se206.gpt.ChatMessage;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionRequest;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult.Choice;

/** This class handles the logic required to run the game master. */
public class GameMaster {

  private ChatCompletionRequest chatCompletionRequest;

  /** Creates a new chat completion request. */
  public void chatCompletionRequest() {
    chatCompletionRequest =
        new ChatCompletionRequest().setN(1).setTemperature(1.3).setTopP(0.65).setMaxTokens(100);
  }

  /**
   * Executes the chat completion request and returns the response message.
   *
   * @param message The message to be processed by the GPT API.
   * @return The response message.
   * @throws ApiProxyException If the API request fails.
   */
  public ChatMessage runGameMaster(ChatMessage message) throws ApiProxyException {

    // Add the user's message to the chat completion request:
    chatCompletionRequest.addMessage(message);

    // Run the chat completion request:
    ChatCompletionResult chatCompletionResult = chatCompletionRequest.execute();
    Choice result = chatCompletionResult.getChoices().iterator().next();

    // Add the response message to the chat completion request:
    chatCompletionRequest.addMessage(result.getChatMessage());

    return result.getChatMessage(); // Returns the current response message
  }
}
