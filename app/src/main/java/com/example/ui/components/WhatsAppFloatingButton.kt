package com.example.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun WhatsAppFloatingButton(
  phoneNumber: String,
  schoolName: String,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val cleanNumber = phoneNumber.replace("-", "").replace(" ", "").replace("+", "").let {
    if (it.startsWith("0")) "62" + it.substring(1) else it
  }

  FloatingActionButton(
    onClick = {
      openWhatsApp(context, cleanNumber, "Assalamu'alaikum Admin $schoolName, saya ingin bertanya seputar informasi sekolah dan PPDB.")
    },
    containerColor = Color(0xFF25D366), // WhatsApp Green
    contentColor = Color.White,
    shape = CircleShape,
    modifier = modifier.padding(16.dp)
  ) {
    Icon(
      imageVector = Icons.Default.Chat,
      contentDescription = "Chat WhatsApp",
      modifier = Modifier.size(28.dp)
    )
  }
}

fun openWhatsApp(context: Context, fullPhoneNumber: String, message: String) {
  try {
    val encodedMsg = Uri.encode(message)
    val url = "https://api.whatsapp.com/send?phone=$fullPhoneNumber&text=$encodedMsg"
    val intent = Intent(Intent.ACTION_VIEW).apply {
      data = Uri.parse(url)
    }
    context.startActivity(intent)
  } catch (e: Exception) {
    try {
      // Fallback: open dialer
      val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$fullPhoneNumber"))
      context.startActivity(dialIntent)
    } catch (e2: Exception) {
      Toast.makeText(context, "Nomor WhatsApp: $fullPhoneNumber", Toast.LENGTH_LONG).show()
    }
  }
}
