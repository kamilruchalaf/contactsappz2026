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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.droidcode.apps.contactsappz.ui.theme.ContactsAppZTheme

data class ContactDetailsUiState(
    val name: String,
    val relation: String,
    val email: String,
    val phone: String,
    val city: String,
    val birthday: String,
    val isFavorite: Boolean
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
    val headerColor = Color(0xFF7BC0DF)

    Scaffold(
        modifier = modifier,
        topBar = {
            ContactDetailsTopBar(
                isFavorite = state.isFavorite,
                backgroundColor = headerColor,
                onBackClick = onBackClick,
                onFavoriteClick = onFavoriteClick
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(18.dp))
                ContactAvatar(modifier = Modifier.size(176.dp))
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = state.name,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = state.relation,
                    color = Color(0xFFC4C4C4),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(12.dp))
                ContactActions(
                    onCallClick = onCallClick,
                    onEmailClick = onEmailClick
                )
                Spacer(modifier = Modifier.height(12.dp))
                ContactInfo(
                    email = state.email,
                    phone = state.phone,
                    city = state.city,
                    birthday = state.birthday
                )
                Spacer(modifier = Modifier.height(14.dp))
                ContactMap(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.95f)
                )
                Spacer(
                    modifier = Modifier
                        .height(118.dp)
                        .navigationBarsPadding()
                )
            }

            Button(
                onClick = onEditClick,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .navigationBarsPadding()
                    .padding(end = 24.dp, bottom = 28.dp)
                    .height(70.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = headerColor,
                    contentColor = Color(0xFF1F2933)
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ) {
                Text(
                    text = "Edytuj",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun ContactDetailsTopBar(
    isFavorite: Boolean,
    backgroundColor: Color,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Surface(color = backgroundColor) {
        Column {
            Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(78.dp)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Wstecz",
                        modifier = Modifier.size(28.dp),
                        tint = Color(0xFF1F2933)
                    )
                }
                IconButton(onClick = onFavoriteClick) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Star else Icons.Outlined.StarBorder,
                        contentDescription = "Ulubiony",
                        modifier = Modifier.size(28.dp),
                        tint = Color(0xFF1F2933)
                    )
                }
            }
        }
    }
}

@Composable
private fun ContactActions(
    onCallClick: () -> Unit,
    onEmailClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ActionButton(onClick = onCallClick) {
            Icon(
                imageVector = Icons.Filled.Phone,
                contentDescription = "Zadzwoń",
                modifier = Modifier.size(28.dp),
                tint = Color(0xFF1F2933)
            )
        }
        ActionButton(onClick = onEmailClick) {
            Icon(
                imageVector = Icons.Filled.Email,
                contentDescription = "Napisz email",
                modifier = Modifier.size(30.dp),
                tint = Color(0xFF1F2933)
            )
        }
    }
}

@Composable
private fun ActionButton(
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .size(78.dp)
            .background(Color(0xFF7BC0DF), RoundedCornerShape(4.dp))
            .border(1.dp, Color(0x553A5A6A), RoundedCornerShape(4.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
private fun ContactInfo(
    email: String,
    phone: String,
    city: String,
    birthday: String
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        ContactInfoItem(label = "Adres email", value = email)
        ContactInfoItem(label = "Telefon", value = phone)
        ContactInfoItem(label = "Miejscowość", value = city)
        ContactInfoItem(label = "Urodziny", value = birthday)
    }
}

@Composable
private fun ContactInfoItem(
    label: String,
    value: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = label,
            color = Color(0xFFC4C4C4),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = value,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun ContactAvatar(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        drawCircle(Color(0xFF129DD0), radius = w * 0.5f, center = center)
        drawCircle(Color(0x332EECFF), radius = w * 0.08f, center = Offset(w * 0.58f, h * 0.06f))
        drawCircle(Color(0x332EECFF), radius = w * 0.12f, center = Offset(w * 0.86f, h * 0.35f))
        drawCircle(Color(0x442EECFF), radius = w * 0.06f, center = Offset(w * 0.92f, h * 0.58f))

        val hair = Path().apply {
            moveTo(w * 0.17f, h * 0.78f)
            cubicTo(w * 0.16f, h * 0.38f, w * 0.35f, h * 0.12f, w * 0.64f, h * 0.18f)
            cubicTo(w * 0.88f, h * 0.23f, w * 0.94f, h * 0.55f, w * 0.83f, h * 0.77f)
            cubicTo(w * 0.66f, h * 0.91f, w * 0.37f, h * 0.92f, w * 0.17f, h * 0.78f)
            close()
        }
        drawPath(hair, Color(0xFF1F2C38))

        val face = Path().apply {
            moveTo(w * 0.30f, h * 0.58f)
            cubicTo(w * 0.28f, h * 0.37f, w * 0.43f, h * 0.26f, w * 0.55f, h * 0.31f)
            cubicTo(w * 0.72f, h * 0.39f, w * 0.78f, h * 0.57f, w * 0.72f, h * 0.78f)
            cubicTo(w * 0.60f, h * 0.87f, w * 0.42f, h * 0.86f, w * 0.30f, h * 0.58f)
            close()
        }
        drawPath(face, Color(0xFFFFC9BC))

        val neck = Path().apply {
            moveTo(w * 0.45f, h * 0.77f)
            lineTo(w * 0.61f, h * 0.77f)
            lineTo(w * 0.66f, h * 0.99f)
            lineTo(w * 0.37f, h * 0.99f)
            close()
        }
        drawPath(neck, Color(0xFFFFC9BC))

        val bang = Path().apply {
            moveTo(w * 0.24f, h * 0.47f)
            cubicTo(w * 0.34f, h * 0.38f, w * 0.48f, h * 0.27f, w * 0.54f, h * 0.14f)
            cubicTo(w * 0.62f, h * 0.29f, w * 0.74f, h * 0.34f, w * 0.82f, h * 0.40f)
            cubicTo(w * 0.70f, h * 0.18f, w * 0.39f, h * 0.12f, w * 0.24f, h * 0.47f)
            close()
        }
        drawPath(bang, Color(0xFF1F2C38))

        drawLine(Color(0xFF313946), Offset(w * 0.40f, h * 0.56f), Offset(w * 0.45f, h * 0.57f), 3f, StrokeCap.Round)
        drawLine(Color(0xFF313946), Offset(w * 0.63f, h * 0.56f), Offset(w * 0.68f, h * 0.55f), 3f, StrokeCap.Round)
        drawLine(Color(0xFF8A5F5A), Offset(w * 0.54f, h * 0.57f), Offset(w * 0.52f, h * 0.66f), 2.6f, StrokeCap.Round)
        drawArc(
            color = Color(0xFF8A5F5A),
            startAngle = 20f,
            sweepAngle = 140f,
            useCenter = false,
            topLeft = Offset(w * 0.46f, h * 0.62f),
            size = Size(w * 0.14f, h * 0.08f),
            style = Stroke(width = 2.4f, cap = StrokeCap.Round)
        )

        drawCircle(Color.Transparent, radius = w * 0.12f, center = Offset(w * 0.39f, h * 0.52f), style = Stroke(3f))
        drawCircle(Color(0xFFE8EDF0), radius = w * 0.12f, center = Offset(w * 0.39f, h * 0.52f), style = Stroke(3f))
        drawCircle(Color(0xFFE8EDF0), radius = w * 0.12f, center = Offset(w * 0.64f, h * 0.52f), style = Stroke(3f))
        drawLine(Color(0xFFE8EDF0), Offset(w * 0.51f, h * 0.52f), Offset(w * 0.52f, h * 0.52f), 3f)

        drawCircle(Color(0xFF1F2C38), radius = w * 0.09f, center = Offset(w * 0.27f, h * 0.14f))
    }
}

@Composable
private fun ContactMap(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(Color(0xFFFFF4E3))
            .border(1.dp, Color(0xFFDAD6CF))
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            drawRect(Color(0xFFFFF4E3))

            val park = Path().apply {
                moveTo(0f, h * 0.42f)
                lineTo(w * 0.30f, h * 0.20f)
                lineTo(w * 0.45f, h)
                lineTo(0f, h)
                close()
            }
            drawPath(park, Color(0xFFD8F1DF))

            val river = Path().apply {
                moveTo(0f, h * 0.63f)
                cubicTo(w * 0.20f, h * 0.56f, w * 0.25f, h * 0.95f, w * 0.42f, h * 0.90f)
                cubicTo(w * 0.52f, h * 0.86f, w * 0.55f, h * 0.70f, w, h * 0.76f)
            }
            drawPath(river, Color(0xFFEAF3FB), style = Stroke(width = 18f, cap = StrokeCap.Round))

            listOf(0.08f, 0.22f, 0.36f, 0.52f, 0.68f, 0.83f).forEach { x ->
                drawLine(Color(0xFFE3D9C8), Offset(w * x, 0f), Offset(w * (x - 0.10f), h), 4f)
            }
            listOf(0.12f, 0.30f, 0.48f, 0.66f, 0.84f).forEach { y ->
                drawLine(Color(0xFFE3D9C8), Offset(0f, h * y), Offset(w, h * (y - 0.12f)), 4f)
            }
            drawLine(Color.White, Offset(w * 0.02f, h * 0.26f), Offset(w * 0.58f, h * 0.18f), 10f, StrokeCap.Round)
            drawLine(Color.White, Offset(w * 0.46f, 0f), Offset(w * 0.35f, h), 10f, StrokeCap.Round)
            drawLine(Color.White, Offset(0f, h * 0.82f), Offset(w, h * 0.62f), 10f, StrokeCap.Round)

            drawTextLabel("Rynek Główny", Offset(w * 0.48f, h * 0.28f), Color(0xFF3EA9B9))
            drawTextLabel("Uniwersytet", Offset(w * 0.27f, h * 0.42f), Color(0xFF8A99A8))
            drawTextLabel("Bazylika", Offset(w * 0.63f, h * 0.53f), Color(0xFF8A99A8))

            repeat(9) { index ->
                val x = w * (0.12f + (index % 5) * 0.18f)
                val y = h * (0.20f + (index / 5) * 0.46f)
                drawCircle(Color(0xFF64C6D4), radius = 7f, center = Offset(x, y))
            }
            repeat(4) { index ->
                drawCircle(
                    Color(0xFFF7A531),
                    radius = 7f,
                    center = Offset(w * (0.16f + index * 0.20f), h * (0.34f + index * 0.07f))
                )
            }

            val pinCenter = Offset(w * 0.50f, h * 0.58f)
            drawCircle(Color.Black, radius = 16f, center = Offset(pinCenter.x, pinCenter.y - 10f))
            val pin = Path().apply {
                moveTo(pinCenter.x - 13f, pinCenter.y - 2f)
                lineTo(pinCenter.x + 13f, pinCenter.y - 2f)
                lineTo(pinCenter.x, pinCenter.y + 28f)
                close()
            }
            drawPath(pin, Color.Black)
            drawCircle(Color.White, radius = 5f, center = Offset(pinCenter.x, pinCenter.y - 10f))
        }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawTextLabel(
    text: String,
    position: Offset,
    color: Color
) {
    drawContext.canvas.nativeCanvas.drawText(
        text,
        position.x,
        position.y,
        android.graphics.Paint().apply {
            this.color = color.toArgb()
            textSize = 18f
            isFakeBoldText = true
            isAntiAlias = true
        }
    )
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
                city = "Kraków",
                birthday = "9 czerwca",
                isFavorite = false
            ),
            onBackClick = {},
            onFavoriteClick = {},
            onCallClick = {},
            onEmailClick = {},
            onEditClick = {}
        )
    }
}
