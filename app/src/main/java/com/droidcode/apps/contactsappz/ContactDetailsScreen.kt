package com.droidcode.apps.contactsappz

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.droidcode.apps.contactsappz.ui.theme.ContactsAppZTheme

data class ContactDetailsUiState(
    val name: String,
    val relationship: String,
    val email: String,
    val phone: String,
    val city: String,
    val birthday: String,
    val imageUrl: String?
)

@Composable
fun ContactDetailsScreen(
    state: ContactDetailsUiState,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onFavoriteClick: () -> Unit = {},
    onCallClick: () -> Unit = {},
    onEmailClick: () -> Unit = {},
    onEditClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        ContactDetailsTopBar(
            onBackClick = onBackClick,
            onFavoriteClick = onFavoriteClick
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(18.dp))
            ContactHeader(
                name = state.name,
                relationship = state.relationship,
                imageUrl = state.imageUrl
            )
            Spacer(modifier = Modifier.height(12.dp))
            ContactActions(
                onCallClick = onCallClick,
                onEmailClick = onEmailClick
            )
            Spacer(modifier = Modifier.height(8.dp))
            ContactInformation(state = state)
            Spacer(modifier = Modifier.height(12.dp))
            ContactMap(onEditClick = onEditClick)
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun ContactDetailsTopBar(
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    val topPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorScheme.primary)
            .padding(top = topPadding)
            .height(64.dp)
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Wstecz",
                tint = colorScheme.onPrimary
            )
        }
        IconButton(onClick = onFavoriteClick) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Ulubiony",
                tint = colorScheme.onPrimary
            )
        }
    }
}

@Composable
private fun ContactHeader(
    name: String,
    relationship: String,
    imageUrl: String?
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        ContactAvatar(
            name = name,
            imageUrl = imageUrl,
            modifier = Modifier.size(174.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = name,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 30.sp,
            lineHeight = 34.sp,
            fontWeight = FontWeight.Normal
        )
        Text(
            text = relationship,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun ContactActions(
    onCallClick: () -> Unit,
    onEmailClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SquareActionButton(onClick = onCallClick) {
            Icon(
                imageVector = Icons.Default.Call,
                contentDescription = "Zadzwoń",
                modifier = Modifier.size(30.dp),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        SquareActionButton(onClick = onEmailClick) {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Napisz email",
                modifier = Modifier.size(30.dp),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
private fun SquareActionButton(
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = Modifier
            .size(72.dp)
            .clickable(onClick = onClick),
        color = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(4.dp),
        shadowElevation = 2.dp
    ) {
        Box(contentAlignment = Alignment.Center) {
            content()
        }
    }
}

@Composable
private fun ContactInformation(state: ContactDetailsUiState) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        DetailRow(label = "Adres email", value = state.email)
        DetailRow(label = "Telefon", value = state.phone)
        DetailRow(label = "Miejscowość", value = state.city)
        DetailRow(label = "Urodziny", value = state.birthday)
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 12.sp,
            lineHeight = 14.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = value,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 18.sp,
            lineHeight = 22.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun ContactMap(
    onEditClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
    ) {
        MapPreview(
            modifier = Modifier
                .fillMaxSize()
                .border(1.dp, MaterialTheme.colorScheme.outline)
        )
        Button(
            onClick = onEditClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp)
                .height(66.dp)
                .width(96.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            shape = RoundedCornerShape(18.dp)
        ) {
            Text(
                text = "Edytuj",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun ContactAvatar(
    name: String,
    imageUrl: String?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ) {
        if (imageUrl != null) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Text(
                text = contactInitials(name),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = 48.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

private fun contactInitials(name: String): String =
    name
        .split(" ")
        .filter { it.isNotBlank() }
        .take(2)
        .joinToString(separator = "") { it.first().uppercaseChar().toString() }

@Composable
private fun MapPreview(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.background(Color(0xFFFFFBF4))) {
        val w = size.width
        val h = size.height

        drawRect(Color(0xFFE6F2EA), topLeft = Offset(0f, h * 0.33f), size = Size(w * 0.46f, h * 0.67f))
        drawRect(Color(0xFFF9F0E0), topLeft = Offset(w * 0.33f, 0f), size = Size(w * 0.67f, h * 0.52f))
        drawRect(Color(0xFFE9EEF2), topLeft = Offset(0f, 0f), size = Size(w * 0.28f, h * 0.47f))

        drawRoad(Offset(w * 0.04f, h * 0.07f), Offset(w * 0.96f, h * 0.05f))
        drawRoad(Offset(w * 0.06f, h * 0.87f), Offset(w * 0.94f, h * 0.82f))
        drawRoad(Offset(w * 0.16f, 0f), Offset(w * 0.31f, h))
        drawRoad(Offset(w * 0.41f, 0f), Offset(w * 0.57f, h))
        drawRoad(Offset(0f, h * 0.55f), Offset(w, h * 0.33f))

        listOf(
            Offset(w * 0.15f, h * 0.20f),
            Offset(w * 0.30f, h * 0.56f),
            Offset(w * 0.70f, h * 0.22f),
            Offset(w * 0.85f, h * 0.62f)
        ).forEach { point ->
            drawCircle(Color(0xFF2FBCD2), radius = 5.dp.toPx(), center = point)
        }

        drawCircle(Color.Black, radius = 13.dp.toPx(), center = Offset(w * 0.50f, h * 0.47f))
        val pin = Path().apply {
            moveTo(w * 0.50f, h * 0.61f)
            lineTo(w * 0.45f, h * 0.49f)
            lineTo(w * 0.55f, h * 0.49f)
            close()
        }
        drawPath(pin, Color.Black)
        drawCircle(Color.White, radius = 4.dp.toPx(), center = Offset(w * 0.50f, h * 0.47f))
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawRoad(
    start: Offset,
    end: Offset
) {
    drawLine(
        color = Color.White,
        start = start,
        end = end,
        strokeWidth = 9.dp.toPx(),
        cap = StrokeCap.Round
    )
    drawLine(
        color = Color(0xFFD9DEE2),
        start = start,
        end = end,
        strokeWidth = 1.dp.toPx(),
        cap = StrokeCap.Round
    )
}

@Preview(
    showBackground = true,
    widthDp = 412,
    heightDp = 917
)
@Composable
private fun ContactDetailsScreenPreview() {
    ContactsAppZTheme {
        ContactDetailsScreen(
            state = ContactDetailsUiState(
                name = "Olivia Eklund",
                relationship = "Siostra",
                email = "olivia.eklund@gmail.com",
                phone = "+48 500 600 700",
                city = "Krakow",
                birthday = "9 czerwca",
                imageUrl = "https://picsum.photos/seed/olivia/300/300"
            )
        )
    }
}
