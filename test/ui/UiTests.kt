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

package ui

import com.intellij.remoterobot.fixtures.ComponentFixture
import com.intellij.remoterobot.search.locators.byXpath
import com.intellij.remoterobot.stepsProcessing.step
import com.intellij.remoterobot.utils.keyboard
import com.intellij.remoterobot.utils.waitFor
import org.assertj.swing.core.MouseButton
import org.intellij.examples.simple.plugin.steps.JavaExampleSteps
import org.junit.Ignore
import org.junit.Test
import ui.pages.actionMenu
import ui.pages.actionMenuItem
import ui.pages.dialog
import ui.pages.editor
import ui.pages.gutter
import ui.pages.idea
import ui.pages.welcomeFrame
import ui.utils.StepsLogger
import ui.utils.uiTest
import java.awt.event.KeyEvent
import kotlin.test.assertEquals

class UiTests {
  init {
    StepsLogger.init()
  }

  @Test
  @Ignore("Please start it manually")
  fun ideaVimTest() = uiTest {
    val sharedSteps = JavaExampleSteps(this)

    welcomeFrame {
      createNewProjectLink.click()
      dialog("New Project") {
        findText("Java").click()
        find(
          ComponentFixture::class.java,
          byXpath("//div[@class='FrameworksTree']")
        ).findText("Kotlin/JVM").click()
        runJs("robot.pressAndReleaseKey(${KeyEvent.VK_SPACE})")
        button("Next").click()
        button("Finish").click()
      }
    }
    sharedSteps.closeTipOfTheDay()
    idea {
      step("Create App file") {
        with(projectViewTree) {
          findText(projectName).doubleClick()
          waitFor { hasText("src") }
          findText("src").click(MouseButton.RIGHT_BUTTON)
        }
        actionMenu("New").click()
        actionMenuItem("File").click()
        keyboard { enterText("MyDocument.txt"); enter() }
      }
      val editor = editor("MyDocument.txt") {
        step("Write a text") {
          keyboard {
            enterText("i")
            enterText(
              """
                |One
                |Two
                |Three
            """.trimMargin()
            )
            escape()
          }
        }
      }
      gutter() {
        findText("2").click()
      }

      assertEquals("Two\n", editor.selectedText)

    }
  }
}