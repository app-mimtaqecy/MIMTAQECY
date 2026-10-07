package com.example.ui.screens.admin

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.HowToReg
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SportsKabaddi
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.components.SchoolImage
import com.example.ui.components.StatCard
import com.example.viewmodel.AdminTab
import com.example.viewmodel.AppScreen
import com.example.viewmodel.SchoolViewModel

@Composable
fun AdminDashboardScreen(
  viewModel: SchoolViewModel,
  modifier: Modifier = Modifier
) {
  val profile by viewModel.schoolProfile.collectAsState()
  val currentTab by viewModel.adminTab.collectAsState()
  val studentCount by viewModel.studentCount.collectAsState()
  val teacherCount by viewModel.teacherCount.collectAsState()
  val ppdbCount by viewModel.ppdbCount.collectAsState()
  val unreadMessages by viewModel.unreadMessageCount.collectAsState()

  val adminTabs = listOf(
    Triple(AdminTab.OVERVIEW, "Ringkasan", Icons.Default.Dashboard),
    Triple(AdminTab.STUDENTS, "Data Siswa", Icons.Default.Groups),
    Triple(AdminTab.PPDB, "Data PPDB", Icons.Default.HowToReg),
    Triple(AdminTab.TEACHERS, "Data Guru", Icons.Default.School),
    Triple(AdminTab.NEWS, "Berita", Icons.Default.Newspaper),
    Triple(AdminTab.GALLERY, "Galeri", Icons.Default.Collections),
    Triple(AdminTab.EXTRACURRICULAR, "Ekstrakurikuler", Icons.Default.SportsKabaddi),
    Triple(AdminTab.PROGRAMS, "Program Unggulan", Icons.Default.Star),
    Triple(AdminTab.ORGANIZATION, "Struktur Organisasi", Icons.Default.AccountTree),
    Triple(AdminTab.TESTIMONIALS, "Testimoni", Icons.Default.FormatQuote),
    Triple(AdminTab.MESSAGES, "Pesan Masuk", Icons.Default.Mail),
    Triple(AdminTab.SETTINGS, "Pengaturan", Icons.Default.Settings)
  )

  Column(modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
    // Admin Top App Bar
    Surface(
      color = MaterialTheme.colorScheme.primary,
      shadowElevation = 4.dp,
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 14.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          SchoolImage(
            model = profile.logoUrlOrPath,
            contentDescription = "Logo",
            modifier = Modifier.size(36.dp).clip(CircleShape)
          )
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(
              text = "PANEL ADMINISTRATOR",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = Color.White
            )
            Text(
              text = profile.schoolName,
              style = MaterialTheme.typography.labelSmall,
              color = MaterialTheme.colorScheme.secondary
            )
          }
        }

        Row {
          // View Public Web Button
          IconButton(onClick = { viewModel.navigateTo(AppScreen.HOME) }) {
            Icon(Icons.Default.Visibility, contentDescription = "Lihat Halaman Depan", tint = Color.White)
          }
          // Logout Button
          IconButton(onClick = { viewModel.logoutAdmin() }) {
            Icon(Icons.Default.ExitToApp, contentDescription = "Keluar", tint = Color.White)
          }
        }
      }
    }

    // Scrollable Admin Tab Chips
    Surface(
      color = MaterialTheme.colorScheme.surface,
      tonalElevation = 2.dp,
      modifier = Modifier.fillMaxWidth()
    ) {
      LazyRow(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 10.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        items(adminTabs) { (tab, label, icon) ->
          val isSelected = currentTab == tab
          FilterChip(
            selected = isSelected,
            onClick = { viewModel.setAdminTab(tab) },
            leadingIcon = {
              Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(16.dp))
            },
            label = {
              Text(label, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium)
            },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = MaterialTheme.colorScheme.primary,
              selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
              selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimary
            )
          )
        }
      }
    }

    // Tab Content Body
    LazyColumn(modifier = Modifier.fillMaxSize().weight(1f)) {
      item {
        when (currentTab) {
          AdminTab.OVERVIEW -> {
            AdminOverviewTab(
              viewModel = viewModel,
              studentCount = studentCount,
              teacherCount = teacherCount,
              ppdbCount = ppdbCount,
              unreadMessages = unreadMessages,
              onSelectTab = { viewModel.setAdminTab(it) }
            )
          }
          AdminTab.STUDENTS -> AdminStudentsSection(viewModel = viewModel)
          AdminTab.PPDB -> AdminPpdbSection(viewModel = viewModel)
          AdminTab.TEACHERS -> AdminTeachersSection(viewModel = viewModel)
          AdminTab.NEWS -> AdminNewsSection(viewModel = viewModel)
          AdminTab.GALLERY -> AdminGallerySection(viewModel = viewModel)
          AdminTab.EXTRACURRICULAR -> AdminExtracurricularSection(viewModel = viewModel)
          AdminTab.PROGRAMS -> AdminProgramsSection(viewModel = viewModel)
          AdminTab.ORGANIZATION -> AdminOrganizationSection(viewModel = viewModel)
          AdminTab.TESTIMONIALS -> AdminTestimonialsSection(viewModel = viewModel)
          AdminTab.MESSAGES -> AdminMessagesSection(viewModel = viewModel)
          AdminTab.SETTINGS -> AdminSettingsSection(viewModel = viewModel)
          else -> {}
        }
      }
    }
  }
}

@Composable
fun AdminOverviewTab(
  viewModel: SchoolViewModel,
  studentCount: Int,
  teacherCount: Int,
  ppdbCount: Int,
  unreadMessages: Int,
  onSelectTab: (AdminTab) -> Unit
) {
  val profile by viewModel.schoolProfile.collectAsState()

  Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
    Text(
      text = "Selamat Datang di Dasbor Administrator",
      style = MaterialTheme.typography.titleLarge,
      fontWeight = FontWeight.Bold,
      color = MaterialTheme.colorScheme.primary
    )
    Text(
      text = "Kelola seluruh data website resmi dan pendaftaran online madrasah.",
      style = MaterialTheme.typography.bodySmall,
      color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(modifier = Modifier.height(16.dp))

    // 4 Key Statistics Cards
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
      StatCard(
        value = studentCount.toString(),
        label = "Total Siswa",
        icon = Icons.Default.Groups,
        modifier = Modifier.weight(1f).clickable { onSelectTab(AdminTab.STUDENTS) }
      )
      StatCard(
        value = teacherCount.toString(),
        label = "Total Guru",
        icon = Icons.Default.School,
        modifier = Modifier.weight(1f).clickable { onSelectTab(AdminTab.TEACHERS) }
      )
    }

    Spacer(modifier = Modifier.height(10.dp))

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
      StatCard(
        value = ppdbCount.toString(),
        label = "Pendaftar PPDB",
        icon = Icons.Default.HowToReg,
        modifier = Modifier.weight(1f).clickable { onSelectTab(AdminTab.PPDB) }
      )
      StatCard(
        value = unreadMessages.toString(),
        label = "Pesan Belum Dibaca",
        icon = Icons.Default.Mail,
        modifier = Modifier.weight(1f).clickable { onSelectTab(AdminTab.MESSAGES) }
      )
    }

    Spacer(modifier = Modifier.height(20.dp))

    // Quick Menu Shortcut Grid
    Text(
      text = "Pintasan Pengelolaan Data",
      style = MaterialTheme.typography.titleMedium,
      fontWeight = FontWeight.Bold,
      color = MaterialTheme.colorScheme.onSurface
    )
    Spacer(modifier = Modifier.height(10.dp))

    listOf(
      Triple("Kelola Siswa (Filter Kelas 1-6 & 12 Rombel)", "Tambah individu atau impor serentak dari Excel", AdminTab.STUDENTS),
      Triple("Verifikasi PPDB & Unduh Berkas", "Lihat kartu keluarga, ijazah, dan akta pendaftar", AdminTab.PPDB),
      Triple("Publikasi Berita & Foto Baru", "Tambah berita kegiatan dan prestasi siswa", AdminTab.NEWS),
      Triple("Unggah Foto Galeri & Ekstrakurikuler", "Dokumentasikan momen belajar dan kepanduan HW", AdminTab.GALLERY),
      Triple("Ubah Struktur Organisasi & Testimoni", "Edit nama pejabat madrasah dan testimoni alumni", AdminTab.ORGANIZATION),
      Triple("Pengaturan Logo, Akreditasi & Tahun Berdiri", "Perbarui identitas resmi sekolah dan medsos", AdminTab.SETTINGS)
    ).forEach { (title, subtitle, targetTab) ->
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 4.dp)
          .clickable { onSelectTab(targetTab) },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(1.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth().padding(14.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
          }
          Text("Buka →", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
        }
      }
    }
  }
}
