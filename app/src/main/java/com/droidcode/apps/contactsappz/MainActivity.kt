package com.droidcode.apps.contactsappz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.droidcode.apps.contactsappz.ui.theme.ContactsAppZTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContactsAppZTheme {
                ContactsScreen()
            }
        }
    }
}

val sampleFirstNames = listOf("John", "Jane", "Alice", "Bob", "Charlie", "Diana", "Edward", "Fiona", "George", "Hannah")
val sampleLastNames = listOf("Doe", "Smith", "Brown", "Johnson", "Williams", "Jones", "Garcia", "Miller", "Davis", "Wilson")

val sampleContacts = (1..100000).map { index ->
    ContactData(
        firstName = sampleFirstNames[index % sampleFirstNames.size],
        lastName = sampleLastNames[index % sampleLastNames.size],
        isFavorite = index % 5 == 0,
        imageUrl = "https://picsum.photos/seed/$index/100/100"
    )
}

@Composable
fun ContactsScreen() {
    var selectedContact by remember { mutableStateOf<ContactData?>(null) }

    if (selectedContact != null) {
        BackHandler {
            selectedContact = null
        }
        ContactDetailsScreen(
            state = selectedContact!!.toContactDetailsUiState(),
            onBackClick = { selectedContact = null },
            onFavoriteClick = {},
            onCallClick = {},
            onEmailClick = {},
            onEditClick = {}
        )
    } else {
        Scaffold { paddingValues ->
            LazyColumn(
                modifier = Modifier.padding(paddingValues)
            ) {
                stickyHeader {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface)
                    ) {
                        HeaderItem()
                    }
                }
//            item { HeaderItem() }
                items(sampleContacts) { contact ->
                    ContactItem(
                        data = contact,
                        onClick = { selectedContact = contact }
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ContactsAppZTheme {
        Greeting("Android")
    }
}

@Composable
fun HeaderItem() {
    Text(
//        modifier = Modifier
//            .clickable {
//                //
//            }
//            .padding(
//                horizontal = 16.dp,
//                vertical = 8.dp
//            ),
        text = stringResource(R.string.contacts),
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.titleLarge
    )
}

@Preview(showBackground = true)
@Composable
private fun HeaderItemPreview() {
    ContactsAppZTheme {
        HeaderItem()
    }
}

@Composable
fun ContactItem(
    data: ContactData,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("contact-item-${data.firstName}-${data.lastName}")
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = data.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
//                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, MaterialTheme.colorScheme.outline, CircleShape)
            )
            Spacer(modifier = Modifier.size(4.dp))
            Column {
                Text(
                    text = data.firstName
                )
//        Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = data.lastName
                )
            }
        }
        if (data.isFavorite) { // AnimatedVisibility
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ContactItemPreview() {
    ContactsAppZTheme {
        ContactItem(
            data = ContactData(
                firstName = "John",
                lastName = "Doe",
                isFavorite = true,
                imageUrl = null
            ),
            onClick = {}
        )
    }
}

data class ContactData(
    val firstName: String,
    val lastName: String,
    val isFavorite: Boolean,
    val imageUrl: String? = null
)

private fun ContactData.toContactDetailsUiState() = ContactDetailsUiState(
    name = "$firstName $lastName",
    relation = "Kontakt",
    email = "${firstName.lowercase()}.${lastName.lowercase()}@gmail.com",
    phone = "+48 500 600 700",
    city = "Krakow",
    birthday = "9 czerwca",
    imageUrl = imageUrl
)
