/*
 * Copyright 2000-2012 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.ui;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.colors.EditorColors;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.PlatformColors;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import consulo.annotations.RequiredDispatchThread;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;

/**
 * @author Dmitry Avdeev
 */
public class EditorNotificationPanel extends JPanel {
  protected final JLabel myLabel = new JLabel();
  protected final JLabel myGearLabel = new JLabel();
  protected final JPanel myLinksPanel;
  private final Color myBackgroundColor;

  public EditorNotificationPanel() {
    this(null);
  }

  public EditorNotificationPanel(@Nullable Color backgroundColor) {
    super(new BorderLayout());
    myBackgroundColor = backgroundColor;
    setBorder(JBUI.Borders.empty(1, 10, 1, 10));

    setPreferredSize(JBUI.size(-1, 24));

    add(myLabel, BorderLayout.CENTER);

    myLinksPanel = new JPanel(new FlowLayout());
    myLinksPanel.setBackground(getBackground());

    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(getBackground());
    myGearLabel.setBorder(JBUI.Borders.empty(0, 3, 0, 0));
    panel.add(myLinksPanel, BorderLayout.WEST);
    panel.add(myGearLabel, BorderLayout.EAST);
    add(panel, BorderLayout.EAST);
  }

  public void setText(String text) {
    myLabel.setText(text);
  }

  public EditorNotificationPanel text(@NotNull String text) {
    myLabel.setText(text);
    return this;
  }

  public EditorNotificationPanel icon(@NotNull Icon icon) {
    myLabel.setIcon(icon);
    return this;
  }

  @Override
  public final Color getBackground() {
    if(myBackgroundColor != null) {
      return myBackgroundColor;
    }
    Color color = EditorColorsManager.getInstance().getGlobalScheme().getColor(EditorColors.NOTIFICATION_BACKGROUND);
    return color == null ? UIUtil.getToolTipBackground() : color;
  }

  public HyperlinkLabel createActionLabel(final String text, @NonNls final String actionId) {
    return createActionLabel(text, () -> executeAction(actionId));
  }

  public HyperlinkLabel createActionLabel(final String text, final Runnable action) {
    HyperlinkLabel label = new HyperlinkLabel(text, PlatformColors.BLUE, getBackground(), PlatformColors.BLUE);
    label.addHyperlinkListener(new HyperlinkAdapter() {
      @Override
      protected void hyperlinkActivated(HyperlinkEvent e) {
        action.run();
      }
    });
    myLinksPanel.add(label);
    return label;
  }

  @RequiredDispatchThread
  protected void executeAction(final String actionId) {
    final AnAction action = ActionManager.getInstance().getAction(actionId);
    final AnActionEvent event = new AnActionEvent(null, DataManager.getInstance().getDataContext(this), ActionPlaces.UNKNOWN,
                                                  action.getTemplatePresentation(), ActionManager.getInstance(),
                                                  0);
    action.beforeActionPerformedUpdate(event);
    action.update(event);

    if (event.getPresentation().isEnabled() && event.getPresentation().isVisible()) {
      action.actionPerformed(event);
    }
  }

  @Override
  public Dimension getMinimumSize() {
    return new Dimension(0, 0);
  }
}
