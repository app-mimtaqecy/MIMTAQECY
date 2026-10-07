package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ui.components.SchoolFooter
import com.example.ui.components.SectionHeader
import com.example.viewmodel.SchoolViewModel

@Composable
fun AcademicScreen(
  viewModel: SchoolViewModel,
  modifier: Modifier = Modifier
) {
  val profile by viewModel.schoolProfile.collectAsState()
  var selectedDay by remember { mutableStateOf("Senin") }
  val days = listOf("Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu")

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
              text = "LAYANAN AKADEMIK",
              style = MaterialTheme.typography.labelSmall,
              color = MaterialTheme.colorScheme.secondary,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
            )
          }
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "Akademik & Pembelajaran",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary
          )
          Text(
            text = "Kurikulum Berbasis Karakter Qurani & Kurikulum Merdeka",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f),
            textAlign = TextAlign.Center
          )
        }
      }
    }

    // 1. KURIKULUM MADRASAH
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
            Icon(imageVector = Icons.Default.MenuBook, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Struktur Kurikulum Terintegrasi",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface
            )
          }
          Spacer(modifier = Modifier.height(10.dp))
          Text(
            text = "MI Muhammadiyah Tanjung Qencono mengimplementasikan Kurikulum Terpadu yang menggabungkan standar nasional pendidikan dan nilai-nilai luhur kepesantrenan Muhammadiyah:",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
          Spacer(modifier = Modifier.height(12.dp))

          CurriculumBadgeCard(
            title = "1. Kurikulum Merdeka (Kemendikbudristek)",
            desc = "Fokus pada penguatan literasi dasar, numerasi, proyek penguatan profil pelajar Pancasila (P5), dan pembelajaran berdiferensiasi."
          )
          CurriculumBadgeCard(
            title = "2. Kurikulum Standar Kemenag RI",
            desc = "Mata pelajaran Pendidikan Agama Islam khas madrasah: Al-Qur'an Hadits, Akidah Akhlak, Fiqih, Sejarah Kebudayaan Islam (SKI), dan Bahasa Arab."
          )
          CurriculumBadgeCard(
            title = "3. Kurikulum ISMUBA (Kemuhammadiyahan)",
            desc = "Pendidikan Al-Islam, Kemuhammadiyahan, dan Kepanduan Hizbul Wathan (HW) yang menanamkan jiwa kepemimpinan serta pergerakan Islam berkemajuan."
          )
        }
      }
    }

    // 2. DAFTAR MATA PELAJARAN (TABEL RAPI)
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp)
      ) {
        SectionHeader(
          badgeText = "MATA PELAJARAN",
          title = "Daftar Mata Pelajaran",
          subtitle = "Distribusi mata pelajaran umum dan keislaman"
        )

        Card(
          modifier = Modifier.fillMaxWidth(),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          shape = RoundedCornerShape(14.dp)
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            // Header Row
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(8.dp))
                .padding(horizontal = 10.dp, vertical = 8.dp),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text("Kelompok / Mata Pelajaran", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelMedium)
              Text("Alokasi Waktu", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelMedium)
            }

            listOf(
              "Al-Qur'an Hadits" to "2 JP / Minggu",
              "Akidah Akhlak" to "2 JP / Minggu",
              "Fiqih Ibadah" to "2 JP / Minggu",
              "Sejarah Kebudayaan Islam (SKI)" to "2 JP / Minggu",
              "Bahasa Arab Madrasah" to "2 JP / Minggu",
              "Kemuhammadiyahan" to "2 JP / Minggu",
              "Pendidikan Pancasila" to "4 JP / Minggu",
              "Bahasa Indonesia" to "6 JP / Minggu",
              "Matematika" to "5 JP / Minggu",
              "Ilmu Pengetahuan Alam & Sosial (IPAS)" to "5 JP / Minggu",
              "Pendidikan Jasmani & Kesehatan (PJOK)" to "3 JP / Minggu",
              "Seni Budaya & Prakarya" to "3 JP / Minggu",
              "Muatan Lokal (Bahasa Lampung & Tahfidz)" to "4 JP / Minggu"
            ).forEachIndexed { index, (mapel, jp) ->
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .background(if (index % 2 == 0) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f))
                  .padding(horizontal = 10.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text(mapel, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                Text(jp, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.primary)
              }
              HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
            }
          }
        }
      }
    }

    // 3. JADWAL PELAJARAN MINGGUAN (INTERAKTIF DENGAN TABS)
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp)
      ) {
        SectionHeader(
          badgeText = "JADWAL KBM",
          title = "Jadwal Pelajaran Harian",
          subtitle = "Jadwal kegiatan belajar mengajar per hari"
        )

        TabRow(
          selectedTabIndex = days.indexOf(selectedDay),
          containerColor = MaterialTheme.colorScheme.surfaceVariant,
          contentColor = MaterialTheme.colorScheme.primary,
          modifier = Modifier.clip(RoundedCornerShape(10.dp))
        ) {
          days.forEach { day ->
            Tab(
              selected = selectedDay == day,
              onClick = { selectedDay = day },
              text = { Text(day, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold) }
            )
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Card(
          modifier = Modifier.fillMaxWidth(),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          shape = RoundedCornerShape(14.dp),
          elevation = CardDefaults.cardElevation(2.dp)
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            val scheduleForDay = when (selectedDay) {
              "Senin" -> listOf(
                "07.00 - 07.45" to "Upacara Bendera Merah Putih",
                "07.45 - 08.15" to "Sholat Dhuha Berjamaah & Tadarus Al-Qur'an",
                "08.15 - 09.35" to "Al-Qur'an Hadits & Tajwid",
                "09.35 - 10.00" to "Istirahat & Snack Pagi",
                "10.00 - 11.20" to "Matematika / Berhitung Cerdas",
                "11.20 - 12.00" to "Pendidikan Pancasila",
                "12.00 - 12.45" to "Sholat Dzuhur Berjamaah & Pulang"
              )
              "Jumat" -> listOf(
                "07.00 - 07.30" to "Senam Ceria & Jalan Sehat Santri",
                "07.30 - 08.00" to "Sholat Dhuha & Kultum Siswa",
                "08.00 - 09.20" to "Kemuhammadiyahan & Keislaman",
                "09.20 - 10.30" to "Tahfidz Al-Qur'an & Setoran Hafalan",
                "10.30 - 11.00" to "Persiapan Sholat Jumat / Keputrian",
                "11.00 - 12.30" to "Sholat Jumat Berjamaah di Masjid Madrasah"
              )
              else -> listOf(
                "07.00 - 07.30" to "Pembiasaan Sholat Dhuha & Doa Pagi",
                "07.30 - 08.50" to "Bahasa Indonesia / Literasi",
                "08.50 - 10.10" to "IPAS / Sains Eksperimen",
                "10.10 - 10.30" to "Istirahat Sehat",
                "10.30 - 11.50" to "Bahasa Arab / Fiqih Ibadah",
                "11.50 - 12.30" to "Sholat Dzuhur Berjamaah & Pulang"
              )
            }

            scheduleForDay.forEach { (time, activity) ->
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Surface(
                  shape = RoundedCornerShape(6.dp),
                  color = MaterialTheme.colorScheme.primaryContainer
                ) {
                  Text(
                    text = time,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                  )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                  text = activity,
                  style = MaterialTheme.typography.bodyMedium,
                  color = MaterialTheme.colorScheme.onSurface,
                  fontWeight = FontWeight.Medium
                )
              }
              HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
            }
          }
        }
      }
    }

    // 4. KALENDER AKADEMIK
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp)
      ) {
        SectionHeader(
          badgeText = "AGENDA TAHUNAN",
          title = "Kalender Akademik 2026/2027",
          subtitle = "Jadwal semester dan pekan evaluasi pembelajaran"
        )

        listOf(
          "15 Juli 2026" to "Awal Masuk Tahun Ajaran Baru & Matsama",
          "17 Agustus 2026" to "Peringatan HUT Kemerdekaan RI ke-81",
          "18 - 25 September 2026" to "Penilaian Tengah Semester (PTS) Ganjil",
          "18 November 2026" to "Milad Muhammadiyah ke-112",
          "01 - 10 Desember 2026" to "Penilaian Akhir Semester (PAS) Ganjil",
          "19 Desember 2026" to "Pembagian Rapor Semester Ganjil",
          "21 Des 2026 - 03 Jan 2027" to "Libur Semester Ganjil",
          "04 Januari 2027" to "Hari Pertama Masuk Semester Genap"
        ).forEach { (date, event) ->
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 4.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(10.dp)
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(20.dp)
              )
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Text(
                  text = event,
                  style = MaterialTheme.typography.bodyMedium,
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                  text = date,
                  style = MaterialTheme.typography.labelSmall,
                  color = MaterialTheme.colorScheme.primary,
                  fontWeight = FontWeight.SemiBold
                )
              }
            }
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

@Composable
private fun CurriculumBadgeCard(title: String, desc: String) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
    shape = RoundedCornerShape(10.dp)
  ) {
    Column(modifier = Modifier.padding(10.dp)) {
      Text(
        text = title,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
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
