package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.viewmodel.AppScreen

@Composable
fun SchoolTopBar(
  schoolName: String,
  slogan: String,
  logoUrl: String,
  currentScreen: AppScreen,
  isDarkMode: Boolean,
  isAdminLoggedIn: Boolean,
  onScreenSelected: (AppScreen) -> Unit,
  onToggleDarkMode: () -> Unit,
  onAdminClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val menuItems = listOf(
    AppScreen.HOME to "Beranda",
    AppScreen.PROFILE to "Profil",
    AppScreen.ACADEMIC to "Akademik",
    AppScreen.NEWS to "Berita",
    AppScreen.GALLERY to "Galeri",
    AppScreen.PPDB to "PPDB",
    AppScreen.CONTACT to "Kontak"
  )

  Surface(
    modifier = modifier.fillMaxWidth(),
    color = MaterialTheme.colorScheme.surface,
    tonalElevation = 4.dp,
    shadowElevation = 3.dp
  ) {
    Column(modifier = Modifier.fillMaxWidth()) {
      // Top header with branding and action buttons
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Logo
        Box(
          modifier = Modifier
            .size(42.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
          contentAlignment = Alignment.Center
        ) {
          SchoolImage(
            model = logoUrl,
            contentDescription = "Logo Sekolah",
            modifier = Modifier.size(38.dp).clip(CircleShape),
            contentScale = ContentScale.Fit
          )
        }

        Spacer(modifier = Modifier.width(10.dp))

        // Titles
        Column(
          modifier = Modifier
            .weight(1f)
            .clickable { onScreenSelected(AppScreen.HOME) }
        ) {
          Text(
            text = schoolName,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )
          Text(
            text = slogan,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )
        }

        // Dark mode button
        IconButton(onClick = onToggleDarkMode) {
          Icon(
            imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
            contentDescription = "Toggle Dark Mode",
            tint = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }

        // Admin lock / panel button
        IconButton(onClick = onAdminClick) {
          if (isAdminLoggedIn) {
            Icon(
              imageVector = Icons.Default.AdminPanelSettings,
              contentDescription = "Dashboard Admin",
              tint = MaterialTheme.colorScheme.primary
            )
          } else {
            Icon(
              imageVector = Icons.Default.Lock,
              contentDescription = "Login Admin",
              tint = MaterialTheme.colorScheme.outline
            )
          }
        }
      }

      // Horizontal navigation tabs
      LazyRow(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        items(menuItems) { (screen, label) ->
          val isSelected = currentScreen == screen
          FilterChip(
            selected = isSelected,
            onClick = { onScreenSelected(screen) },
            label = {
              Text(
                text = label,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                style = MaterialTheme.typography.labelMedium
              )
            },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = MaterialTheme.colorScheme.primary,
              selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
              containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
              labelColor = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            shape = RoundedCornerShape(20.dp)
          )
        }
      }
    }
  }
}
