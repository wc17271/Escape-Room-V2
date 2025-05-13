package nz.ac.auckland.se206;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

/** This class handles the logic required to drag and drop items. */
public class DragImage {

  private boolean correctPosition = false;

  private double originalPlankX;
  private double originalPlankY;

  private double rectangleX;
  private double rectangleY;

  private double width;
  private double height;

  private ImageView plankOutline;
  private ImageView plank;
  private ImageView plankFixed;

  /**
   * Constructor for drag image object.
   *
   * @param plankOutline Plank outline image.
   * @param plank Plank image.
   * @param plankFixed Image of the plank placed in the correct location.
   * @param rectangle Rectangle object that the plank snaps to.
   */
  public DragImage(
      ImageView plankOutline, ImageView plank, ImageView plankFixed, Rectangle rectangle) {

    this.plankOutline = plankOutline;
    this.plank = plank;
    this.plankFixed = plankFixed;

    this.width = rectangle.getWidth() / 4;
    this.height = rectangle.getHeight() / 4;

    // Location where the planks snap to
    this.rectangleX = rectangle.getLayoutX();
    this.rectangleY = rectangle.getLayoutY();

    // Location of plank initial spot
    this.originalPlankX = plankOutline.getLayoutX();
    this.originalPlankY = plankOutline.getLayoutY();
  }

  /**
   * Updates the location of the plankOutline to the mouse location.
   *
   * @param mouseEvent Mouse drag event.
   */
  public void drag(MouseEvent mouseEvent) {
    plankOutline.setLayoutX(mouseEvent.getSceneX());
    plankOutline.setLayoutY(mouseEvent.getSceneY());
  }

  /** Places the plank once mouse click is released. */
  public void released() {

    if (plankOutline.getLayoutX() > rectangleX - 20
        && plankOutline.getLayoutX() < rectangleX + width
        && plankOutline.getLayoutY() > rectangleY - 20
        && plankOutline.getLayoutY() < rectangleY + height) {

      // hide/disable testOutline:
      plankOutline.setOpacity(0);
      plankOutline.setDisable(true);

      // hide/disable original plank
      plank.setOpacity(0);
      plank.setDisable(true);

      // enable fixed outline:
      plankFixed.setOpacity(1);

      plankOutline.setLayoutX(rectangleX);
      plankOutline.setLayoutY(rectangleY);

      correctPosition = true;
    } else {
      plankOutline.setLayoutX(originalPlankX);
      plankOutline.setLayoutY(originalPlankY);

      // turn on no outline:
      plank.setOpacity(1);
    }
  }

  /**
   * Returns whether the plank is in the correct position.
   *
   * @return True if the plank is in the correct position, false otherwise.
   */
  public boolean isCorrectPosition() {
    return correctPosition;
  }
}
