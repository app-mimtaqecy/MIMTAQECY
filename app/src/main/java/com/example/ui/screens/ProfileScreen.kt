package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.MeetingRoom
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.SportsKabaddi
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.components.SchoolFooter
import com.example.ui.components.SchoolImage
import com.example.ui.components.SectionHeader
import com.example.viewmodel.SchoolViewModel

@Composable
fun ProfileScreen(
  viewModel: SchoolViewModel,
  modifier: Modifier = Modifier
) {
  val profile by viewModel.schoolProfile.collectAsState()
  val teachers by viewModel.teachersList.collectAsState()
  val extracurriculars by viewModel.extracurricularList.collectAsState()

  LazyColumn(modifier = modifier.fillMaxSize()) {
    // Header Banner
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .background(MaterialTheme.colorScheme.primary)
          .padding(vertical = 32.dp, horizontal = 20.dp),
        contentAlignment = Alignment.Center
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f),
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.secondary)
          ) {
            Text(
              text = "PROFIL LENGKAP",
              style = MaterialTheme.typography.labelSmall,
              color = MaterialTheme.colorScheme.secondary,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
            )
          }
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "Tentang MI Muhammadiyah Tanjung Qencono",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary
          )
          Text(
            text = "Mengenal Sejarah, Nilai, Struktur, dan Pendidik Kami",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
          )
        }
      }
    }

    // 1. SEJARAH SEKOLAH
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
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.History, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Sejarah Singkat Madrasah",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface
            )
          }
          Spacer(modifier = Modifier.height(10.dp))
          Text(
            text = profile.history,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = MaterialTheme.typography.bodyMedium.lineHeight
          )
        }
      }
    }

    // 2. VISI DAN MISI
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp)
      ) {
        Card(
          modifier = Modifier.fillMaxWidth(),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          shape = RoundedCornerShape(16.dp),
          elevation = CardDefaults.cardElevation(2.dp)
        ) {
          Column(modifier = Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(imageVector = Icons.Default.Visibility, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "Visi Madrasah",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
              )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                .padding(12.dp)
            ) {
              Text(
                text = "\"${profile.vision}\"",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
              )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
              text = "Misi Madrasah:",
              style = MaterialTheme.typography.titleSmall,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = profile.mission,
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
              text = "Tujuan Pendidikan:",
              style = MaterialTheme.typography.titleSmall,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = profile.goals,
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }
      }
    }

    // 3. STRUKTUR ORGANISASI (DAPAT DIEDIT DI ADMIN)
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
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.AccountTree, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Struktur Organisasi",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface
            )
          }
          Spacer(modifier = Modifier.height(10.dp))

          // Render formatted key-value rows from profile.organizationStructure
          profile.organizationStructure.lines().filter { it.isNotBlank() }.forEach { line ->
            val parts = line.split(":", limit = 2)
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = parts.firstOrNull()?.trim() ?: "",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
              )
              Text(
                text = parts.getOrNull(1)?.trim() ?: "",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
              )
            }
          }
        }
      }
    }

    // 4. DATA GURU & TENAGA KEPENDIDIKAN
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 8.dp)
      ) {
        SectionHeader(
          badgeText = "DEWAN GURU",
          title = "Pendidik & Tenaga Kependidikan",
          subtitle = "Guru berdedikasi membimbing dan mendampingi putra-putri Anda"
        )

        teachers.forEach { teacher ->
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 5.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(1.dp)
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              SchoolImage(
                model = teacher.imagePath,
                contentDescription = teacher.name,
                modifier = Modifier
                  .size(60.dp)
                  .clip(CircleShape),
                contentScale = ContentScale.Crop
              )
              Spacer(modifier = Modifier.width(14.dp))
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = teacher.name,
                  style = MaterialTheme.typography.titleSmall,
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                  text = teacher.role,
                  style = MaterialTheme.typography.bodySmall,
                  color = MaterialTheme.colorScheme.primary,
                  fontWeight = FontWeight.Medium
                )
                Text(
                  text = "NIP/NUPTK: ${teacher.nip}",
                  style = MaterialTheme.typography.labelSmall,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                  text = "Pendidikan: ${teacher.education}",
                  style = MaterialTheme.typography.labelSmall,
                  color = MaterialTheme.colorScheme.secondary
                )
              }
            }
          }
        }
      }
    }

    // 5. FASILITAS SEKOLAH
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp)
      ) {
        SectionHeader(
          badgeText = "SARANA & PRASARANA",
          title = "Fasilitas Madrasah",
          subtitle = "Dukungan kenyamanan belajar mengajar yang komprehensif"
        )

        listOf(
          Triple("Gedung & Ruang Kelas Nyaman", "Ruang kelas bersih, ventilasi memadai, dilengkapi papan tulis interaktif dan pojok baca.", "https://images.unsplash.com/photo-1580582932707-520aed937b7b?w=600&q=80"),
          Triple("Perpustakaan Madrasah", "Koleksi buku cerita, referensi islami, ensiklopedia sains, dan buku teks kurikulum merdeka.", "https://images.unsplash.com/photo-1497633762265-9d179a990aa6?w=600&q=80"),
          Triple("Musholla & Sarana Ibadah", "Tempat sholat berjamaah dhuha dan dzuhur yang bersih dengan tempat wudhu terpisah.", "https://images.unsplash.com/photo-1564769625905-50e93615e769?w=600&q=80"),
          Triple("Laboratorium Komputer", "Unit komputer untuk literasi digital santri dan simulasi Asesmen Nasional (ANBK).", "https://images.unsplash.com/photo-1516321318423-f06f85e504b3?w=600&q=80"),
          Triple("Lapangan Olahraga Serbaguna", "Area senam bersama, latihan beladiri Tapak Suci, futsal, dan upacara bendera.", "https://images.unsplash.com/photo-1574629810360-7efbbe195018?w=600&q=80"),
          Triple("UKS & Kantin Sehat", "Unit kesehatan sekolah dan kantin penyedia jajanan higienis, halal, dan bergizi.", "https://images.unsplash.com/photo-1509062522246-3755977927d7?w=600&q=80")
        ).forEach { (title, desc, img) ->
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 5.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(12.dp)
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              SchoolImage(
                model = img,
                contentDescription = title,
                modifier = Modifier
                  .size(75.dp)
                  .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
              )
              Spacer(modifier = Modifier.width(12.dp))
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = title,
                  style = MaterialTheme.typography.titleSmall,
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                  text = desc,
                  style = MaterialTheme.typography.bodySmall,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }
            }
          }
        }
      }
    }

    // 6. EKSTRAKURIKULER
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp)
      ) {
        SectionHeader(
          badgeText = "BAKAT & MINAT",
          title = "Ekstrakurikuler",
          subtitle = "Wadah kreasi dan pengembangan potensi non-akademik siswa"
        )

        extracurriculars.forEach { item ->
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 6.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(14.dp),
            elevation = CardDefaults.cardElevation(2.dp)
          ) {
            Column {
              SchoolImage(
                model = item.imagePath,
                contentDescription = item.name,
                modifier = Modifier
                  .fillMaxWidth()
                  .height(130.dp),
                contentScale = ContentScale.Crop
              )
              Column(modifier = Modifier.padding(14.dp)) {
                Text(
                  text = item.name,
                  style = MaterialTheme.typography.titleMedium,
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.primary
                )
                Text(
                  text = "Pembina: ${item.coach} • Jadwal: ${item.schedule}",
                  style = MaterialTheme.typography.labelMedium,
                  color = MaterialTheme.colorScheme.secondary,
                  fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                  text = item.description,
                  style = MaterialTheme.typography.bodySmall,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }
            }
          }
        }
      }
    }

    // 7. PRESTASI SEKOLAH
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp)
      ) {
        SectionHeader(
          badgeText = "JEJAK PRESTASI",
          title = "Prestasi Membanggakan",
          subtitle = "Capaian santri dalam ajang perlombaan akademik dan seni budaya"
        )

        listOf(
          "Juara 1 Kompetisi Sains Madrasah (KSM) Matematika Terintegrasi Kab. Lampung Timur (2026)",
          "Juara Umum Kejuaraan Pencak Silat Tapak Suci Pelajar Tingkat Kabupaten (2025)",
          "Juara 2 Lomba Musabaqah Hifdzil Qur'an (MHQ) Juz 30 Tingkat Kecamatan Way Bungur (2025)",
          "Juara 1 Lomba Pidato Bahasa Arab Milad Muhammadiyah Daerah Lampung Timur (2024)",
          "Akreditasi Institusi Madrasah Predikat Unggul dari BAN-SM"
        ).forEach { achievement ->
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 4.dp),
            verticalAlignment = Alignment.Top
          ) {
            Icon(
              imageVector = Icons.Default.EmojiEvents,
              contentDescription = null,
              tint = MaterialTheme.colorScheme.secondary,
              modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = achievement,
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.onSurface
            )
          }
        }
      }
    }

    // FOOTER
    item {
      SchoolFooter(
        profile = profile,
        onNavigate = { viewModel.navigateTo(it) }
      )
    }
  }
}
