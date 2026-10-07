package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.data.model.GalleryEntity
import com.example.ui.components.LightboxDialog
import com.example.ui.components.SchoolFooter
import com.example.ui.components.SchoolImage
import com.example.ui.components.SectionHeader
import com.example.viewmodel.SchoolViewModel

@Composable
fun GalleryScreen(
  viewModel: SchoolViewModel,
  modifier: Modifier = Modifier
) {
  val profile by viewModel.schoolProfile.collectAsState()
  val galleryList by viewModel.galleryList.collectAsState()
  val lightboxItem by viewModel.lightboxGallery.collectAsState()

  var selectedCategory by remember { mutableStateOf("Semua") }

  val categories = listOf(
    "Semua",
    "Kegiatan sekolah",
    "Pembelajaran",
    "Upacara",
    "Prestasi",
    "Ekstrakurikuler",
    "Fasilitas"
  )

  val filteredGallery = galleryList.filter {
    selectedCategory == "Semua" || it.category.equals(selectedCategory, ignoreCase = true)
  }

  if (lightboxItem != null) {
    LightboxDialog(
      imageUrl = lightboxItem!!.imagePath,
      title = lightboxItem!!.title,
      description = "${lightboxItem!!.category} • ${lightboxItem!!.description}",
      onDismiss = { viewModel.setLightboxGallery(null) }
    )
  }

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
            text = "Dokumentasi & Galeri Foto",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary
          )
          Text(
            text = "Koleksi Potret Kegiatan, Lingkungan, dan Momen Bersejarah Madrasah",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f),
            textAlign = TextAlign.Center
          )
        }
      }
    }

    // Category Filter Chips
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 12.dp)
      ) {
        LazyRow(
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          items(categories) { cat ->
            val isSelected = selectedCategory == cat
            FilterChip(
              selected = isSelected,
              onClick = { selectedCategory = cat },
              label = { Text(cat, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium) },
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = MaterialTheme.colorScheme.primary,
                selectedLabelColor = MaterialTheme.colorScheme.onPrimary
              )
            )
          }
        }
      }
    }

    // Gallery Grid as rows
    if (filteredGallery.isEmpty()) {
      item {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(40.dp),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "Belum ada foto dalam kategori ini.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }
    } else {
      // Chunk into pairs of 2 for 2-column grid in LazyColumn
      val chunks = filteredGallery.chunked(2)
      items(chunks) { rowItems ->
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          rowItems.forEach { item ->
            Card(
              modifier = Modifier
                .weight(1f)
                .clickable { viewModel.setLightboxGallery(item) },
              colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
              shape = RoundedCornerShape(12.dp),
              elevation = CardDefaults.cardElevation(2.dp)
            ) {
              Column {
                Box {
                  SchoolImage(
                    model = item.imagePath,
                    contentDescription = item.title,
                    modifier = Modifier
                      .fillMaxWidth()
                      .height(130.dp),
                    contentScale = ContentScale.Crop
                  )
                  Surface(
                    color = Color.Black.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(topStart = 8.dp),
                    modifier = Modifier.align(Alignment.BottomEnd)
                  ) {
                    Icon(
                      imageVector = Icons.Default.ZoomIn,
                      contentDescription = "Buka",
                      tint = Color.White,
                      modifier = Modifier
                        .size(24.dp)
                        .padding(4.dp)
                    )
                  }
                }
                Column(modifier = Modifier.padding(10.dp)) {
                  Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(4.dp)
                  ) {
                    Text(
                      text = item.category,
                      style = MaterialTheme.typography.labelSmall,
                      color = MaterialTheme.colorScheme.onPrimaryContainer,
                      fontWeight = FontWeight.Bold,
                      modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                  }
                  Spacer(modifier = Modifier.height(4.dp))
                  Text(
                    text = item.title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                  )
                }
              }
            }
          }
          if (rowItems.size == 1) {
            Spacer(modifier = Modifier.weight(1f))
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
