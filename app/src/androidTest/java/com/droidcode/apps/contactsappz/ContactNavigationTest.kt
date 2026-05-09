package com.droidcode.apps.contactsappz

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.espresso.Espresso.pressBack
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ContactNavigationTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun opensContactDetailsAndReturnsToList() {
        composeRule.onNodeWithText("Kontakty")
            .assertIsDisplayed()

        composeRule.onNodeWithTag("contact-item-1")
            .assertIsDisplayed()
            .performClick()

        composeRule.onNodeWithText("Jane Smith")
            .assertIsDisplayed()
        composeRule.onNodeWithText("jane.smith@gmail.com")
            .assertIsDisplayed()

        pressBack()

        composeRule.onNodeWithText("Kontakty")
            .assertIsDisplayed()
        composeRule.onNodeWithTag("contact-item-1")
            .assertIsDisplayed()
        composeRule.onNodeWithText("Jane Smith")
            .assertDoesNotExist()
    }
}
