package nz.ac.auckland.se206;

import javafx.scene.control.Label;
import org.controlsfx.control.PopOver;

/** This class handles the logic required to create a pop over. */
public class PopOverBuilder {

  /**
   * Creates a pop over with the given text.
   *
   * @param text The text to be displayed in the pop over.
   * @return The pop over.
   */
  public static PopOver createPopOver(String text) {

    Label label = new Label(text);
    label.setStyle("-fx-padding: 5px;");
    PopOver popOver = new PopOver(label);
    popOver.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
    return popOver;
  }
}
