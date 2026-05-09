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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.droidcode.apps.contactsappz.ui.theme.ContactsAppZTheme

@Composable
fun ContactDetailsView(
    data: ContactDetailsData,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onCallClick: () -> Unit,
    onEmailClick: () -> Unit,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
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
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(horizontal = 22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(18.dp))
            ContactHeader(data = data)
            Spacer(modifier = Modifier.height(12.dp))
            ContactActions(
                onCallClick = onCallClick,
                onEmailClick = onEmailClick
            )
            Spacer(modifier = Modifier.height(12.dp))
            ContactInfo(data = data)
            Spacer(modifier = Modifier.height(14.dp))
            ContactMap(onEditClick = onEditClick)
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun ContactDetailsTopBar(
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(ContactDetailsColors.TopBar)
            .statusBarsPadding()
            .height(78.dp)
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButtonSurface(onClick = onBackClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Wroc",
                modifier = Modifier.size(26.dp),
                tint = ContactDetailsColors.PrimaryText
            )
        }
        IconButtonSurface(onClick = onFavoriteClick) {
            Icon(
                imageVector = Icons.Outlined.StarBorder,
                contentDescription = "Ulubiony",
                modifier = Modifier.size(26.dp),
                tint = ContactDetailsColors.PrimaryText
            )
        }
    }
}

@Composable
private fun IconButtonSurface(
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
private fun ContactHeader(
    data: ContactDetailsData,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ContactAvatar(
            imageUrl = data.imageUrl,
            modifier = Modifier.size(174.dp)
        )
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = data.name,
            color = ContactDetailsColors.PrimaryText,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = 30.sp,
                lineHeight = 34.sp
            ),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = data.relationship,
            color = ContactDetailsColors.LabelText,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun ContactActions(
    onCallClick: () -> Unit,
    onEmailClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        ContactActionButton(onClick = onCallClick) {
            Icon(
                imageVector = Icons.Outlined.Call,
                contentDescription = "Zadzwon",
                modifier = Modifier.size(28.dp),
                tint = ContactDetailsColors.PrimaryText
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        ContactActionButton(onClick = onEmailClick) {
            Icon(
                imageVector = Icons.Outlined.Email,
                contentDescription = "Napisz email",
                modifier = Modifier.size(28.dp),
                tint = ContactDetailsColors.PrimaryText
            )
        }
    }
}

@Composable
private fun ContactActionButton(
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = Modifier
            .size(width = 78.dp, height = 66.dp)
            .clip(RoundedCornerShape(6.dp))
            .clickable(onClick = onClick),
        color = ContactDetailsColors.TopBar,
        shape = RoundedCornerShape(6.dp),
        shadowElevation = 2.dp
    ) {
        Box(contentAlignment = Alignment.Center) {
            content()
        }
    }
}

@Composable
private fun ContactInfo(
    data: ContactDetailsData,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        ContactInfoField(label = "Adres email", value = data.email)
        ContactInfoField(label = "Telefon", value = data.phone)
        ContactInfoField(label = "Miejscowosc", value = data.city)
        ContactInfoField(label = "Urodziny", value = data.birthday)
    }
}

@Composable
private fun ContactInfoField(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp)
    ) {
        Text(
            text = label,
            color = ContactDetailsColors.LabelText,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = value,
            color = ContactDetailsColors.SecondaryText,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 17.sp,
                lineHeight = 22.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}

@Composable
private fun ContactMap(
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp)
            .clip(RoundedCornerShape(2.dp))
    ) {
        StylizedMap(modifier = Modifier.fillMaxSize())
        Icon(
            imageVector = Icons.Filled.LocationOn,
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.Center)
                .size(42.dp),
            tint = Color.Black
        )
        Button(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 6.dp, bottom = 28.dp)
                .height(64.dp),
            onClick = onEditClick,
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ContactDetailsColors.TopBar,
                contentColor = ContactDetailsColors.PrimaryText
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Text(
                text = "Edytuj",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Composable
private fun ContactAvatar(
    imageUrl: String?,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = null,
        modifier = modifier
            .clip(CircleShape)
            .border(1.dp, MaterialTheme.colorScheme.outline, CircleShape)
    )
}

@Composable
private fun StylizedMap(modifier: Modifier = Modifier) {
    Canvas(
        modifier = modifier.background(
            Brush.linearGradient(
                colors = listOf(Color(0xFFF5F0E8), Color(0xFFEAF4EE))
            )
        )
    ) {
        drawPath(
            path = Path().apply {
                moveTo(-size.width * 0.1f, size.height * 0.72f)
                cubicTo(size.width * 0.2f, size.height * 0.6f, size.width * 0.34f, size.height * 0.92f, size.width * 0.62f, size.height * 0.72f)
                cubicTo(size.width * 0.82f, size.height * 0.58f, size.width, size.height * 0.66f, size.width * 1.1f, size.height * 0.52f)
                lineTo(size.width * 1.1f, size.height * 1.1f)
                lineTo(-size.width * 0.1f, size.height * 1.1f)
                close()
            },
            color = Color(0xFFCEEDD8)
        )
        drawLine(Color(0xFFE5D8C8), Offset(0f, size.height * 0.2f), Offset(size.width, size.height * 0.12f), 7f)
        drawLine(Color.White, Offset(0f, size.height * 0.2f), Offset(size.width, size.height * 0.12f), 3f)
        drawLine(Color(0xFFE5D8C8), Offset(size.width * 0.12f, 0f), Offset(size.width * 0.06f, size.height), 6f)
        drawLine(Color.White, Offset(size.width * 0.12f, 0f), Offset(size.width * 0.06f, size.height), 2.5f)
        drawLine(Color(0xFFE5D8C8), Offset(size.width * 0.58f, 0f), Offset(size.width * 0.48f, size.height), 6f)
        drawLine(Color.White, Offset(size.width * 0.58f, 0f), Offset(size.width * 0.48f, size.height), 2.5f)
        drawLine(Color(0xFFE5D8C8), Offset(0f, size.height * 0.42f), Offset(size.width, size.height * 0.38f), 6f)
        drawLine(Color.White, Offset(0f, size.height * 0.42f), Offset(size.width, size.height * 0.38f), 2.5f)
        drawLine(Color(0xFFE5D8C8), Offset(size.width * 0.28f, 0f), Offset(size.width * 0.75f, size.height), 6f)
        drawLine(Color.White, Offset(size.width * 0.28f, 0f), Offset(size.width * 0.75f, size.height), 2.5f)
        repeat(8) { index ->
            val x = size.width * (0.12f + index * 0.1f)
            val y = size.height * (0.12f + (index % 4) * 0.18f)
            drawCircle(Color(0xFF64C7D7), radius = 6f, center = Offset(x, y))
        }
        repeat(6) { index ->
            val x = size.width * (0.04f + index * 0.16f)
            val y = size.height * (0.68f + (index % 2) * 0.18f)
            drawRoundRect(
                color = Color(0xFF5C9FEA),
                topLeft = Offset(x, y),
                size = Size(8f, 8f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(2f, 2f)
            )
        }
    }
}

private object ContactDetailsColors {
    val TopBar = Color(0xFF7EC3E5)
    val PrimaryText = Color(0xFF1E2830)
    val SecondaryText = Color(0xFF4A4A4A)
    val LabelText = Color(0xFFC8C8C8)
}

data class ContactDetailsData(
    val name: String,
    val imageUrl: String?,
    val relationship: String,
    val email: String,
    val phone: String,
    val city: String,
    val birthday: String
)

@Preview(showBackground = true)
@Composable
private fun ContactDetailsViewPreview() {
    ContactsAppZTheme {
        ContactDetailsView(
            data = ContactDetailsData(
                name = "Olivia Eklund",
                imageUrl = "https://picsum.photos/seed/1/100/100",
                relationship = "Siostra",
                email = "olivia.eklund@gmail.com",
                phone = "+48 500 600 700",
                city = "Krakow",
                birthday = "9 czerwca"
            ),
            onBackClick = {},
            onFavoriteClick = {},
            onCallClick = {},
            onEmailClick = {},
            onEditClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ContactDetailsViewLongContentPreview() {
    ContactsAppZTheme {
        ContactDetailsView(
            data = ContactDetailsData(
                name = "Aleksandra Katarzyna Nowakowska",
                imageUrl = "https://picsum.photos/seed/2/100/100",
                relationship = "Najstarsza siostra i kontakt alarmowy",
                email = "aleksandra.nowakowska.long.address@example.com",
                phone = "+48 500 600 700 wew. 123",
                city = "Warszawa-Srodmiescie",
                birthday = "29 listopada 1991"
            ),
            onBackClick = {},
            onFavoriteClick = {},
            onCallClick = {},
            onEmailClick = {},
            onEditClick = {}
        )
    }
}
