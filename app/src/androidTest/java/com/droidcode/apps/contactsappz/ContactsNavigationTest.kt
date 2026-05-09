package com.droidcode.apps.contactsappz

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.espresso.Espresso.pressBack
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ContactsNavigationTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun opensContactDetailsAndReturnsToContactsList() {
        composeRule.onNodeWithText("Kontakty")
            .assertIsDisplayed()

        composeRule.onAllNodesWithText("Jane")[0]
            .performClick()

        composeRule.onNodeWithText("Jane Smith")
            .assertIsDisplayed()
        composeRule.onNodeWithText("jane.smith@example.com")
            .assertIsDisplayed()

        pressBack()

        composeRule.onNodeWithText("Kontakty")
            .assertIsDisplayed()
        composeRule.onAllNodesWithText("Jane")[0]
            .assertIsDisplayed()
    }
}
