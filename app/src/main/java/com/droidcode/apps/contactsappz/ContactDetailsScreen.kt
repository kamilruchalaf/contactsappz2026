package com.droidcode.apps.contactsappz

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.droidcode.apps.contactsappz.ui.theme.ContactsAppZTheme

data class ContactDetailsUiState(
    val name: String,
    val relation: String,
    val email: String,
    val phone: String,
    val city: String,
    val birthday: String,
    val imageUrl: String? = null
)

@Composable
fun ContactDetailsScreen(
    state: ContactDetailsUiState,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onCallClick: () -> Unit,
    onEmailClick: () -> Unit,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.testTag("contact-details"),
        topBar = {
            ContactDetailsTopBar(
                onBackClick = onBackClick,
                onFavoriteClick = onFavoriteClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 22.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ContactHero(
                name = state.name,
                relation = state.relation,
                imageUrl = state.imageUrl
            )
            Spacer(modifier = Modifier.height(12.dp))
            ContactQuickActions(
                onCallClick = onCallClick,
                onEmailClick = onEmailClick
            )
            Spacer(modifier = Modifier.height(10.dp))
            ContactInfo(
                state = state,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(14.dp))
            ContactLocationCard(
                onEditClick = onEditClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun ContactDetailsTopBar(
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        modifier = modifier.fillMaxWidth()
    ) {
        Column {
            Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
            Row(
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Wroc"
                    )
                }
                IconButton(onClick = onFavoriteClick) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Dodaj do ulubionych"
                    )
                }
            }
        }
    }
}

@Composable
private fun ContactHero(
    name: String,
    relation: String,
    imageUrl: String?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ContactAvatar(
            imageUrl = imageUrl,
            modifier = Modifier.size(176.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = name,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = relation,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
private fun ContactAvatar(
    imageUrl: String?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .border(1.dp, MaterialTheme.colorScheme.outline, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun ContactQuickActions(
    onCallClick: () -> Unit,
    onEmailClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        QuickActionButton(
            icon = Icons.Default.Call,
            contentDescription = "Zadzwon",
            onClick = onCallClick
        )
        QuickActionButton(
            icon = Icons.Default.Email,
            contentDescription = "Napisz email",
            onClick = onEmailClick
        )
    }
}

@Composable
private fun QuickActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        color = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        shape = MaterialTheme.shapes.extraSmall,
        shadowElevation = 2.dp,
        modifier = modifier.size(width = 72.dp, height = 64.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription
            )
        }
    }
}

@Composable
private fun ContactInfo(
    state: ContactDetailsUiState,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        InfoRow(label = "Adres email", value = state.email)
        InfoRow(label = "Telefon", value = state.phone)
        InfoRow(label = "Miejscowosc", value = state.city)
        InfoRow(label = "Urodziny", value = state.birthday)
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(vertical = 4.dp)) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelMedium
        )
        Text(
            text = value,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun ContactLocationCard(
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .aspectRatio(1.8f)
            .clip(MaterialTheme.shapes.extraSmall)
    ) {
        MapPreview(modifier = Modifier.fillMaxSize())
        ElevatedButton(
            onClick = onEditClick,
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp)
                .height(58.dp)
        ) {
            Text(
                text = "Edytuj",
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Composable
private fun MapPreview(modifier: Modifier = Modifier) {
    val backgroundColor = MaterialTheme.colorScheme.surfaceVariant
    val parkColor = MaterialTheme.colorScheme.tertiaryContainer
    val buildingColor = MaterialTheme.colorScheme.surface
    val waterColor = MaterialTheme.colorScheme.secondaryContainer
    val roadColor = MaterialTheme.colorScheme.surface
    val mainRoadColor = MaterialTheme.colorScheme.tertiary
    val minorRoadColor = MaterialTheme.colorScheme.outlineVariant
    val pointColor = MaterialTheme.colorScheme.secondary
    val pinColor = MaterialTheme.colorScheme.onSurface
    val pinInnerColor = MaterialTheme.colorScheme.surface

    Canvas(
        modifier = modifier.background(backgroundColor)
    ) {
        val w = size.width
        val h = size.height

        drawRect(
            color = parkColor,
            topLeft = Offset(0f, h * 0.18f),
            size = Size(w * 0.34f, h * 0.82f)
        )
        drawRect(
            color = buildingColor,
            topLeft = Offset(w * 0.05f, h * 0.05f),
            size = Size(w * 0.26f, h * 0.26f)
        )
        drawRect(
            color = waterColor,
            topLeft = Offset(w * 0.46f, h * 0.46f),
            size = Size(w * 0.22f, h * 0.18f)
        )
        drawLine(roadColor, Offset(w * 0.02f, h * 0.82f), Offset(w * 0.96f, h * 0.38f), 8f)
        drawLine(roadColor, Offset(w * 0.18f, 0f), Offset(w * 0.42f, h), 7f)
        drawLine(roadColor, Offset(0f, h * 0.34f), Offset(w, h * 0.58f), 6f)
        drawLine(mainRoadColor, Offset(w * 0.02f, h * 0.80f), Offset(w * 0.96f, h * 0.36f), 2.5f)
        drawLine(minorRoadColor, Offset(w * 0.18f, 0f), Offset(w * 0.42f, h), 2.5f)
        drawLine(minorRoadColor, Offset(0f, h * 0.34f), Offset(w, h * 0.58f), 2.5f)

        repeat(9) { index ->
            val x = w * (0.1f + index * 0.09f)
            val y = h * if (index % 2 == 0) 0.78f else 0.24f
            drawCircle(pointColor, radius = 6f, center = Offset(x, y))
        }

        val pinCenter = Offset(w * 0.50f, h * 0.55f)
        drawCircle(pinColor, radius = 15f, center = pinCenter)
        drawPath(
            path = Path().apply {
                moveTo(pinCenter.x - 12f, pinCenter.y + 8f)
                lineTo(pinCenter.x + 12f, pinCenter.y + 8f)
                lineTo(pinCenter.x, pinCenter.y + 35f)
                close()
            },
            color = pinColor
        )
        drawCircle(pinInnerColor, radius = 5f, center = pinCenter)
    }
}

@Preview(showBackground = true)
@Composable
private fun ContactDetailsScreenPreview() {
    ContactsAppZTheme {
        ContactDetailsScreen(
            state = ContactDetailsUiState(
                name = "Olivia Eklund",
                relation = "Siostra",
                email = "olivia.eklund@gmail.com",
                phone = "+48 500 600 700",
                city = "Krakow",
                birthday = "9 czerwca",
                imageUrl = "https://picsum.photos/seed/olivia/400/400"
            ),
            onBackClick = {},
            onFavoriteClick = {},
            onCallClick = {},
            onEmailClick = {},
            onEditClick = {}
        )
    }
}
