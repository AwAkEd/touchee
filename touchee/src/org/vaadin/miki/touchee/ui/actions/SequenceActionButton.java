package org.vaadin.miki.touchee.ui.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.vaadin.event.Action;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;

/**
 * A button that, when clicked, invokes a series of actions on the passed handler. Any exception (including runtime exceptions) that occurs during handling one
 * of the actions in the sequence stops the sequence and a notification is shown.
 *
 * @author miki
 *
 */
public class SequenceActionButton extends AbstractActionButton {

  private static final long serialVersionUID = 20141002;

  private final List<Action> actions = new ArrayList<Action>();

  /**
   * Creates a button with given caption and associated actions.
   * 
   * @param caption
   *          Caption of the button.
   * @param actions
   *          Actions to perform when the button is clicked.
   */
  public SequenceActionButton(String caption, Action... actions) {
    super();
    this.setCaption(caption);
    this.actions.addAll(Arrays.asList(actions));
  }

  /**
   * Creates a button with given caption, handler and associated actions.
   * 
   * @param caption
   *          Caption of the button.
   * @param handler
   *          Handler to delegate clicks to.
   * @param actions
   *          Actions to perform when the button is clicked.
   */
  public SequenceActionButton(String caption, Action.Handler handler, Action... actions) {
    this(caption, actions);
    this.setHandler(handler);
  }

  /**
   * Creates a button with given caption, handler and associated actions.
   * 
   * @param caption
   *          Caption of the button.
   * @param handler
   *          Handler to delegate clicks to.
   * @param sender
   *          Sender of the action, to be passed to the handler.
   * @param actions
   *          Actions to perform when the button is clicked.
   */
  public SequenceActionButton(String caption, Action.Handler handler, Object sender, Action... actions) {
    this(caption, handler, actions);
    this.setSender(sender);
  }

  @Override
  public void buttonClick(Button.ClickEvent event) {
    if(this.getHandler() != null && this.actions.size() > 0) {
      Object sender = this.getSender() == null ? this : this.getSender();
      try {
        for(Action action: this.actions)
          this.getHandler().handleAction(action, sender, this.getTarget());
      }
      catch(Exception e) {
        Notification.show("Error", e.getMessage(), Notification.Type.ERROR_MESSAGE);
      }
    }
  }

  /**
   * Adds one or more actions to the button.
   *
   * @param actions
   *          Actions to add.
   */
  public void addAction(Action... actions) {
    for(Action a: actions)
      this.actions.add(a);
  }

  /**
   * Removes one or more actions from the button.
   *
   * @param actions
   *          Actions to remove.
   */
  public void removeAction(Action... actions) {
    for(Action a: actions)
      this.actions.remove(a);
  }

  /**
   * Checks whether an action is already contained in the button.
   *
   * @param action
   *          Action to check.
   * @return <b>true</b> if the action was already added at least once, <b>false</b> otherwise.
   */
  public boolean containsAction(Action action) {
    return this.actions.contains(action);
  }

  /**
   * Returns an unmodifiable copy of the list of currently registered actions.
   *
   * @return A collection of registered actions. This collection cannot be modified.
   */
  public Collection<Action> getActions() {
    return Collections.unmodifiableList(this.actions);
  }

}
