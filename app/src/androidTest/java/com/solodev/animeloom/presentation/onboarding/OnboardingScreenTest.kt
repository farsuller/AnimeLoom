package com.solodev.animeloom.presentation.onboarding

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.solodev.animeloom.presentation.screens.onboarding.OnboardingEvent
import com.solodev.animeloom.presentation.screens.onboarding.OnboardingScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OnboardingScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val pageTitles = listOf(
        "Get the Latest",
        "Discover and Read",
        "Animes & Manga ",
    )

    @Test
    fun testOnboardingScreenBackNextGetStartedButtons() {
        var eventInvoked: OnboardingEvent? = null

        composeTestRule.setContent {
            OnboardingScreen { event ->
                eventInvoked = event
            }
        }

        // Buttons
        val nextButton = composeTestRule.onNodeWithText("Next")
        val backButton = composeTestRule.onNodeWithText("Back", useUnmergedTree = true)
        val getStartedButton = composeTestRule.onNodeWithText("Get Started")

        // Check initial state
        nextButton.assertExists()
        backButton.assertDoesNotExist()
        getStartedButton.assertDoesNotExist()

        // Navigate to second page
        nextButton.performClick()
        composeTestRule.waitForIdle()

        // Verify state on second page
        backButton.assertExists()
        nextButton.assertExists()
        getStartedButton.assertDoesNotExist()

        // Navigate to third page
        nextButton.performClick()
        composeTestRule.waitForIdle()

        // Verify state on third page
        backButton.assertExists()
        getStartedButton.assertExists()

        // Trigger Get Started
        getStartedButton.performClick()
        composeTestRule.waitForIdle()

        assert(eventInvoked == OnboardingEvent.SaveAppEntry)
    }

    @Test
    fun testOnboardingScreenPagerIndicator() {
        composeTestRule.setContent {
            OnboardingScreen {}
        }

        // Verify the first page content
        composeTestRule.onNodeWithText(pageTitles[0]).assertExists().assertIsDisplayed()

        // Verify the pager indicator for the first page
        composeTestRule.onNodeWithTag("Indicator_Selected").assertExists() // Selected indicator
        composeTestRule.onAllNodesWithTag("Indicator_Unselected_1").assertCountEquals(1)
        composeTestRule.onAllNodesWithTag("Indicator_Unselected_2").assertCountEquals(1)

        // Navigate to the second page
        composeTestRule.onNodeWithText("Next").performClick()
        composeTestRule.waitForIdle()

        // Verify the second page content
        composeTestRule.onNodeWithText(pageTitles[1]).assertExists().assertIsDisplayed()

        // Verify the pager indicator for the second page
        composeTestRule.onNodeWithTag("Indicator_Selected").assertExists() // Selected indicator
        composeTestRule.onAllNodesWithTag("Indicator_Unselected_0").assertCountEquals(1)
        composeTestRule.onAllNodesWithTag("Indicator_Unselected_2").assertCountEquals(1)

        // Navigate to the third page
        composeTestRule.onNodeWithText("Next").performClick()
        composeTestRule.waitForIdle()

        // Verify the third page content
        composeTestRule.onNodeWithText(pageTitles[2]).assertExists().assertIsDisplayed()

        // Verify the pager indicator for the third page
        composeTestRule.onNodeWithTag("Indicator_Selected").assertExists() // Selected indicator
        composeTestRule.onAllNodesWithTag("Indicator_Unselected_0").assertCountEquals(1)
        composeTestRule.onAllNodesWithTag("Indicator_Unselected_1").assertCountEquals(1)
    }

    @Test
    fun testOnboardingScreenNavigationAndContent() {
        var eventInvoked: OnboardingEvent? = null

        composeTestRule.setContent {
            OnboardingScreen { event ->
                eventInvoked = event
            }
        }

        // Navigate and validate each page
        pageTitles.forEachIndexed { index, title ->
            // Verify title
            composeTestRule.onNodeWithText(title).assertExists()
            composeTestRule.onNodeWithText(title).assertIsDisplayed()

            if (index < pageTitles.size - 1) {
                // Navigate to next page
                composeTestRule.onNodeWithText("Next").performClick()
                composeTestRule.waitForIdle()
            }
        }

        // Verify "Get Started" on the last page
        composeTestRule.onNodeWithText("Get Started").assertExists()
        composeTestRule.onNodeWithText("Get Started").performClick()
        composeTestRule.waitForIdle()

        // Ensure event is triggered
        assert(eventInvoked == OnboardingEvent.SaveAppEntry)
    }
}
