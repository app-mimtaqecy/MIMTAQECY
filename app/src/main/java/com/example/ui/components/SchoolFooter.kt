package com.example.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.model.SchoolProfileEntity
import com.example.viewmodel.AppScreen

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SchoolFooter(
  profile: SchoolProfileEntity,
  onNavigate: (AppScreen) -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current

  Surface(
    modifier = modifier.fillMaxWidth(),
    color = MaterialTheme.colorScheme.surfaceVariant,
    tonalElevation = 6.dp
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp)
    ) {
      // Identity Section
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 12.dp)
      ) {
        SchoolImage(
          model = profile.logoUrlOrPath,
          contentDescription = "Logo",
          modifier = Modifier.size(44.dp).clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
          Text(
            text = profile.schoolName,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
          Text(
            text = "\"${profile.slogan}\"",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Medium
          )
        }
      }

      Text(
        text = profile.description,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.85f),
        modifier = Modifier.padding(bottom = 16.dp)
      )

      HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
      Spacer(modifier = Modifier.height(16.dp))

      // Contact Info
      Text(
        text = "KONTAK & INFORMASI",
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
      )
      Spacer(modifier = Modifier.height(8.dp))

      FooterContactItem(
        icon = Icons.Default.LocationOn,
        text = profile.address
      )
      FooterContactItem(
        icon = Icons.Default.Phone,
        text = "Telepon: ${profile.phone}"
      )
      FooterContactItem(
        icon = Icons.Default.Email,
        text = "Email: ${profile.email}"
      )
      FooterContactItem(
        icon = Icons.Default.Schedule,
        text = "Jam Layanan: Senin - Sabtu (07.00 - 14.00 WIB)"
      )

      Spacer(modifier = Modifier.height(16.dp))
      HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
      Spacer(modifier = Modifier.height(16.dp))

      // Quick Nav Links
      Text(
        text = "MENU UTAMA",
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
      )
      Spacer(modifier = Modifier.height(8.dp))

      FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        listOf(
          "Beranda" to AppScreen.HOME,
          "Profil Sekolah" to AppScreen.PROFILE,
          "Akademik" to AppScreen.ACADEMIC,
          "Berita Terbaru" to AppScreen.NEWS,
          "Galeri Foto" to AppScreen.GALLERY,
          "Pendaftaran PPDB" to AppScreen.PPDB,
          "Hubungi Kami" to AppScreen.CONTACT
        ).forEach { (label, screen) ->
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.clickable { onNavigate(screen) }
          ) {
            Text(
              text = label,
              style = MaterialTheme.typography.labelMedium,
              color = MaterialTheme.colorScheme.onSurface,
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Social Media Links
      Text(
        text = "MEDIA SOSIAL",
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
      )
      Spacer(modifier = Modifier.height(8.dp))

      Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        SocialMediaBadge(label = "Facebook", url = profile.facebookUrl, context = context)
        SocialMediaBadge(label = "Instagram", url = profile.instagramUrl, context = context)
        SocialMediaBadge(label = "YouTube", url = profile.youtubeUrl, context = context)
        SocialMediaBadge(label = "TikTok", url = profile.tiktokUrl, context = context)
      }

      Spacer(modifier = Modifier.height(20.dp))
      HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
      Spacer(modifier = Modifier.height(16.dp))

      // Copyright
      Text(
        text = "© 2026 MI MUHAMMADIYAH TANJUNG QENCONO\nAkreditasi: ${profile.accreditation} • Berdiri Sejak ${profile.establishedYear}",
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
        fontWeight = FontWeight.Medium
      )
    }
  }
}

@Composable
private fun FooterContactItem(
  icon: ImageVector,
  text: String
) {
  Row(
    verticalAlignment = Alignment.Top,
    modifier = Modifier.padding(vertical = 4.dp)
  ) {
    Icon(
      imageVector = icon,
      contentDescription = null,
      tint = MaterialTheme.colorScheme.primary,
      modifier = Modifier.size(18.dp).padding(top = 2.dp)
    )
    Spacer(modifier = Modifier.width(8.dp))
    Text(
      text = text,
      style = MaterialTheme.typography.bodySmall,
      color = MaterialTheme.colorScheme.onSurfaceVariant
    )
  }
}

@Composable
private fun SocialMediaBadge(
  label: String,
  url: String,
  context: Context
) {
  Surface(
    shape = RoundedCornerShape(16.dp),
    color = MaterialTheme.colorScheme.primary,
    modifier = Modifier.clickable {
      if (url.isNotBlank()) {
        try {
          context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        } catch (_: Exception) {}
      }
    }
  ) {
    Text(
      text = label,
      style = MaterialTheme.typography.labelSmall,
      color = MaterialTheme.colorScheme.onPrimary,
      fontWeight = FontWeight.SemiBold,
      modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
    )
  }
}
