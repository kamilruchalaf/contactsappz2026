package com.droidcode.apps.contactsappz

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ContactsScreenNavigationTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun opensContactDetailsFromListAndReturnsBack() {
        val contactIndex = 0
        val contact = sampleContacts[contactIndex]

        composeRule
            .onNodeWithTag(ContactsScreenTestTags.ContactsList)
            .assertIsDisplayed()

        composeRule
            .onNodeWithTag(ContactsScreenTestTags.contactItem(contactIndex))
            .performClick()

        composeRule
            .onNodeWithText("${contact.firstName} ${contact.lastName}")
            .assertIsDisplayed()
        composeRule
            .onNodeWithText("${contact.firstName.lowercase()}.${contact.lastName.lowercase()}@example.com")
            .assertIsDisplayed()

        composeRule
            .onNodeWithContentDescription("Wstecz")
            .performClick()

        composeRule
            .onNodeWithTag(ContactsScreenTestTags.ContactsList)
            .assertIsDisplayed()
        composeRule
            .onNodeWithTag(ContactsScreenTestTags.contactItem(contactIndex))
            .assertIsDisplayed()
    }
}
