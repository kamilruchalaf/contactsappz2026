package com.droidcode.apps.contactsappz

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onFirst
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
    fun clickContact_opensPreview_andBackReturnsToList() {
        composeRule.onAllNodesWithTag("contact-item-Jane-Smith")
            .onFirst()
            .assertIsDisplayed()
            .performClick()

        composeRule.onNodeWithTag("contact-details")
            .assertIsDisplayed()
        composeRule.onNodeWithText("Jane Smith")
            .assertIsDisplayed()
        composeRule.onNodeWithText("Kontakt")
            .assertIsDisplayed()

        pressBack()

        composeRule.onAllNodesWithTag("contact-item-Jane-Smith")
            .onFirst()
            .assertIsDisplayed()
    }
}
