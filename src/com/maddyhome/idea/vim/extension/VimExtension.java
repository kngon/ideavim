/*
 * IdeaVim - Vim emulator for IDEs based on the IntelliJ platform
 * Copyright (C) 2003-2020 The IdeaVim authors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package com.maddyhome.idea.vim.extension;

import com.intellij.openapi.extensions.ExtensionPointName;
import com.intellij.openapi.util.NlsSafe;
import com.maddyhome.idea.vim.VimPlugin;
import com.maddyhome.idea.vim.key.MappingOwner;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * @author vlan
 */
public interface VimExtension {
  @NotNull ExtensionPointName<ExtensionBeanClass> EP_NAME = ExtensionPointName.create("IdeaVIM.vimExtension");

  /**
   * @deprecated This property is not used anymore, but we'll remove it much later to keep the compatibility of IdeaVim
   *   extensions with previous versions of IdeaVim.
   */
  @Deprecated
  @ApiStatus.ScheduledForRemoval(inVersion = "0.65")
  @NlsSafe @NotNull String getName();

  default MappingOwner getOwner() {
    return MappingOwner.Plugin.Companion.get(getName());
  }

  void init();

  default void dispose() {
    VimPlugin.getKey().removeKeyMapping(getOwner());
  }
}
