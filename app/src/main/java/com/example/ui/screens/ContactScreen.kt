package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ui.components.SchoolFooter
import com.example.ui.components.SchoolImage
import com.example.ui.components.SectionHeader
import com.example.ui.components.openWhatsApp
import com.example.viewmodel.SchoolViewModel

@Composable
fun ContactScreen(
  viewModel: SchoolViewModel,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val profile by viewModel.schoolProfile.collectAsState()

  var name by remember { mutableStateOf("") }
  var email by remember { mutableStateOf("") }
  var phone by remember { mutableStateOf("") }
  var subject by remember { mutableStateOf("") }
  var message by remember { mutableStateOf("") }

  LazyColumn(modifier = modifier.fillMaxSize()) {
    // Header Banner
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .background(MaterialTheme.colorScheme.primary)
          .padding(vertical = 28.dp, horizontal = 20.dp),
        contentAlignment = Alignment.Center
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Text(
            text = "Hubungi Kami",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary
          )
          Text(
            text = "Informasi Layanan & Konsultasi Madrasah",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
          )
        }
      }
    }

    // Contact Information Cards
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp)
      ) {
        SectionHeader(
          badgeText = "LAYANAN INFORMASI",
          title = "Kontak Resmi Madrasah"
        )

        ContactInfoCard(
          icon = Icons.Default.LocationOn,
          title = "Alamat Kampus Madrasah",
          content = profile.address,
          actionText = "Buka di Google Maps",
          onAction = {
            openMaps(context, profile.address)
          }
        )

        Spacer(modifier = Modifier.height(8.dp))

        ContactInfoCard(
          icon = Icons.Default.Phone,
          title = "Telepon & WhatsApp",
          content = "${profile.phone} / WhatsApp Aktif",
          actionText = "Chat WhatsApp",
          onAction = {
            openWhatsApp(context, profile.whatsapp, "Assalamu'alaikum, saya ingin bertanya tentang MI Muhammadiyah Tanjung Qencono.")
          }
        )

        Spacer(modifier = Modifier.height(8.dp))

        ContactInfoCard(
          icon = Icons.Default.Email,
          title = "Alamat Email",
          content = profile.email,
          actionText = "Kirim Email",
          onAction = {
            try {
              val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:${profile.email}")
              }
              context.startActivity(emailIntent)
            } catch (_: Exception) {}
          }
        )

        Spacer(modifier = Modifier.height(8.dp))

        ContactInfoCard(
          icon = Icons.Default.Schedule,
          title = "Jam Pelayanan Kantor",
          content = "Senin - Sabtu : 07.00 - 14.00 WIB\nAhad & Libur Nasional Tutup",
          actionText = null,
          onAction = {}
        )
      }
    }

    // Google Maps Section
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Map, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Lokasi Madrasah di Google Maps", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
          }
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "Lokasi strategis di pusat Desa Tanjung Qencono, Kecamatan Way Bungur, Lampung Timur. Mudah diakses dengan kendaraan roda 2 dan 4.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
          Spacer(modifier = Modifier.height(12.dp))
          Button(
            onClick = { openMaps(context, "MI Muhammadiyah Tanjung Qencono, Way Bungur, Lampung Timur") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp)
          ) {
            Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)
            Spacer(modifier = Modifier.width(6.dp))
            Text("Buka Petunjuk Arah di Google Maps", fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    // Contact Form
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp)
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          SectionHeader(
            badgeText = "FORMULIR PESAN",
            title = "Kirim Pesan Langsung"
          )

          OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nama Lengkap Anda *") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
          )
          Spacer(modifier = Modifier.height(8.dp))

          OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email Anda") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
          )
          Spacer(modifier = Modifier.height(8.dp))

          OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Nomor WhatsApp / HP *") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
          )
          Spacer(modifier = Modifier.height(8.dp))

          OutlinedTextField(
            value = subject,
            onValueChange = { subject = it },
            label = { Text("Subjek / Topik Pertanyaan *") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
          )
          Spacer(modifier = Modifier.height(8.dp))

          OutlinedTextField(
            value = message,
            onValueChange = { message = it },
            label = { Text("Tuliskan Pesan Anda Disini *") },
            modifier = Modifier.fillMaxWidth().height(120.dp)
          )
          Spacer(modifier = Modifier.height(16.dp))

          Button(
            onClick = {
              if (name.isBlank() || phone.isBlank() || message.isBlank()) {
                Toast.makeText(context, "Harap isi nama, nomor HP, dan pesan!", Toast.LENGTH_SHORT).show()
              } else {
                viewModel.submitContactMessage(
                  name = name,
                  email = email,
                  phone = phone,
                  subject = if (subject.isNotBlank()) subject else "Pesan Pengunjung",
                  message = message,
                  onSuccess = {
                    Toast.makeText(context, "Pesan Anda berhasil dikirim ke madrasah! Kami akan segera menghubungi Anda.", Toast.LENGTH_LONG).show()
                    name = ""
                    email = ""
                    phone = ""
                    subject = ""
                    message = ""
                  }
                )
              }
            },
            modifier = Modifier.fillMaxWidth().height(48.dp),
            shape = RoundedCornerShape(10.dp)
          ) {
            Icon(imageVector = Icons.Default.Send, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Kirim Pesan Sekarang", fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    // Footer
    item {
      Spacer(modifier = Modifier.height(20.dp))
      SchoolFooter(
        profile = profile,
        onNavigate = { viewModel.navigateTo(it) }
      )
    }
  }
}

@Composable
private fun ContactInfoCard(
  icon: ImageVector,
  title: String,
  content: String,
  actionText: String?,
  onAction: () -> Unit
) {
  Card(
    modifier = Modifier.fillMaxWidth(),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    shape = RoundedCornerShape(12.dp),
    elevation = CardDefaults.cardElevation(1.dp)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(44.dp)
          .clip(CircleShape)
          .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
      ) {
        Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
      }
      Spacer(modifier = Modifier.width(12.dp))
      Column(modifier = Modifier.weight(1f)) {
        Text(text = title, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text = content, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
        if (actionText != null) {
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "$actionText →",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable { onAction() }
          )
        }
      }
    }
  }
}

fun openMaps(context: Context, query: String) {
  try {
    val uri = Uri.parse("geo:0,0?q=" + Uri.encode(query))
    val mapIntent = Intent(Intent.ACTION_VIEW, uri)
    context.startActivity(mapIntent)
  } catch (e: Exception) {
    try {
      val webUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=" + Uri.encode(query))
      context.startActivity(Intent(Intent.ACTION_VIEW, webUri))
    } catch (_: Exception) {}
  }
}
