/*
 * Copyright 2000-2009 JetBrains s.r.o.
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

/*
 * @author max
 */
package com.intellij.lang;

import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import consulo.lang.util.LanguageVersionUtil;
import org.jetbrains.annotations.NotNull;

public class LanguageWordCompletion extends LanguageExtension<WordCompletionElementFilter> {
  public static final LanguageWordCompletion INSTANCE = new LanguageWordCompletion();

  private LanguageWordCompletion() {
    super("com.intellij.codeInsight.wordCompletionFilter", new DefaultWordCompletionFilter());
  }

  public boolean isEnabledIn(@NotNull ASTNode astNode) {
    final PsiElement psi = astNode.getPsi();
    if (psi == null) {
      return false;
    }
    IElementType elementType = astNode.getElementType();
    return forLanguage(psi.getLanguage()).isWordCompletionEnabledIn(elementType, LanguageVersionUtil.findLanguageVersion(elementType.getLanguage(), psi));
  }
}