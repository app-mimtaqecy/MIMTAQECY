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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Class
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.HowToReg
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.data.model.AgendaEntity
import com.example.data.model.FeaturedProgramEntity
import com.example.data.model.GalleryEntity
import com.example.data.model.NewsEntity
import com.example.data.model.TestimonialEntity
import com.example.ui.components.SchoolFooter
import com.example.ui.components.SchoolImage
import com.example.ui.components.SectionHeader
import com.example.ui.components.StatCard
import com.example.viewmodel.AppScreen
import com.example.viewmodel.SchoolViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
  viewModel: SchoolViewModel,
  modifier: Modifier = Modifier
) {
  val profile by viewModel.schoolProfile.collectAsState()
  val newsList by viewModel.newsList.collectAsState()
  val galleryList by viewModel.galleryList.collectAsState()
  val programsList by viewModel.featuredProgramsList.collectAsState()
  val agendasList by viewModel.agendatList.collectAsState()
  val studentCount by viewModel.studentCount.collectAsState()
  val teacherCount by viewModel.teacherCount.collectAsState()
  val testimonials by viewModel.testimonialsList.collectAsState()

  val listState = rememberLazyListState()
  val coroutineScope = rememberCoroutineScope()

  Box(modifier = modifier.fillMaxSize()) {
    LazyColumn(
      state = listState,
      modifier = Modifier.fillMaxSize()
    ) {
      // 1. HERO SECTION
      item {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(380.dp)
        ) {
          // Hero background image with overlay
          SchoolImage(
            model = "https://images.unsplash.com/photo-1580582932707-520aed937b7b?w=1200&q=80",
            contentDescription = "Gedung Sekolah",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
          )

          // Dark gradient overlay for text readability
          Box(
            modifier = Modifier
              .fillMaxSize()
              .background(
                Brush.verticalGradient(
                  colors = listOf(
                    Color(0xFF0C2B59).copy(alpha = 0.85f),
                    Color(0xFF0A192F).copy(alpha = 0.95f)
                  )
                )
              )
          )

          Column(
            modifier = Modifier
              .fillMaxSize()
              .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Surface(
              shape = RoundedCornerShape(20.dp),
              color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.25f),
              border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.secondary)
            ) {
              Text(
                text = profile.accreditation,
                style = MaterialTheme.typography.labelSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
              )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
              text = profile.schoolName,
              style = MaterialTheme.typography.headlineMedium,
              fontWeight = FontWeight.ExtraBold,
              color = Color.White,
              textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
              text = "\"${profile.slogan}\"",
              style = MaterialTheme.typography.titleSmall,
              fontWeight = FontWeight.SemiBold,
              color = MaterialTheme.colorScheme.secondary,
              textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
              text = profile.description,
              style = MaterialTheme.typography.bodySmall,
              color = Color.White.copy(alpha = 0.9f),
              textAlign = TextAlign.Center,
              maxLines = 3,
              overflow = TextOverflow.Ellipsis,
              modifier = Modifier.padding(horizontal = 12.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
              horizontalArrangement = Arrangement.spacedBy(12.dp),
              modifier = Modifier.fillMaxWidth(),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Button(
                onClick = { viewModel.navigateTo(AppScreen.PPDB) },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.weight(1f)
              ) {
                Icon(imageVector = Icons.Default.HowToReg, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Daftar PPDB", fontWeight = FontWeight.Bold)
              }

              OutlinedButton(
                onClick = { viewModel.navigateTo(AppScreen.PROFILE) },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, Color.White),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.weight(1f)
              ) {
                Text("Lihat Profil", fontWeight = FontWeight.SemiBold)
              }
            }
          }
        }
      }

      // 2. STATISTIK SEKOLAH
      item {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
          SectionHeader(
            badgeText = "FAKTA & ANGKA",
            title = "Statistik Madrasah",
            subtitle = "Data terkini perkembangan madrasah kami"
          )

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            StatCard(
              value = studentCount.toString(),
              label = "Jumlah Siswa",
              icon = Icons.Default.Groups,
              modifier = Modifier.weight(1f)
            )
            StatCard(
              value = teacherCount.toString(),
              label = "Jumlah Guru",
              icon = Icons.Default.School,
              modifier = Modifier.weight(1f)
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            StatCard(
              value = "6",
              label = "Jumlah Kelas",
              icon = Icons.Default.Class,
              modifier = Modifier.weight(1f)
            )
            StatCard(
              value = profile.establishedYear,
              label = "Tahun Berdiri",
              icon = Icons.Default.DateRange,
              modifier = Modifier.weight(1f)
            )
          }
        }
      }

      // 3. SAMBUTAN KEPALA SEKOLAH
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
              badgeText = "TAUSIYAH & PENGANTAR",
              title = "Sambutan Kepala Madrasah"
            )

            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.padding(bottom = 12.dp)
            ) {
              SchoolImage(
                model = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=400&q=80",
                contentDescription = profile.principalName,
                modifier = Modifier
                  .size(72.dp)
                  .clip(CircleShape),
                contentScale = ContentScale.Crop
              )
              Spacer(modifier = Modifier.width(14.dp))
              Column {
                Text(
                  text = profile.principalName,
                  style = MaterialTheme.typography.titleMedium,
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.primary
                )
                Text(
                  text = "Kepala MI Muhammadiyah Tanjung Qencono",
                  style = MaterialTheme.typography.bodySmall,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }
            }

            Box(
              modifier = Modifier
                .fillMaxWidth()
                .background(
                  MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                  RoundedCornerShape(10.dp)
                )
                .padding(14.dp)
            ) {
              Text(
                text = profile.principalSpeech,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
              )
            }
          }
        }
      }

      // 4. KEUNGGULAN SEKOLAH
      item {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
          SectionHeader(
            badgeText = "MENGAPA KAMI?",
            title = "Keunggulan Madrasah",
            subtitle = "Fondasi pendidikan terpadu untuk masa depan putra-putri Anda"
          )

          listOf(
            Triple("Pendidikan Berkarakter Islami", "Pembiasaan sholat fardhu dan dhuha berjamaah, hafalan doa harian, serta penanaman akhlaqul karimah.", Icons.Default.Verified),
            Triple("Tahfidz Al-Qur'an Terbimbing", "Bimbingan hafalan juz 30 dengan metode talaqqi tajwid bersertifikat.", Icons.Default.Star),
            Triple("Kurikulum Terpadu & Berprestasi", "Perpaduan Kurikulum Merdeka Kemendikbud dan Kurikulum Kemenag/ISMUBA yang unggul.", Icons.Default.School),
            Triple("Fasilitas & Lingkungan Asri", "Lingkungan madrasah yang kondusif, ruang kelas representatif, perpustakaan, dan area olahraga.", Icons.Default.LocationOn)
          ).forEach { (title, desc, icon) ->
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
                  .padding(14.dp),
                verticalAlignment = Alignment.Top
              ) {
                Box(
                  modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f)),
                  contentAlignment = Alignment.Center
                ) {
                  Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(22.dp)
                  )
                }
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

      // 5. PROGRAM UNGGULAN
      item {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
          SectionHeader(
            badgeText = "PROGRAM ISTIMEWA",
            title = "Program Unggulan",
            subtitle = "Kembangkan potensi akademik dan spiritual santri"
          )

          LazyRow(
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 6.dp)
          ) {
            items(programsList) { program ->
              Card(
                modifier = Modifier
                  .width(260.dp)
                  .height(300.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(2.dp)
              ) {
                Column {
                  SchoolImage(
                    model = program.imagePath,
                    contentDescription = program.name,
                    modifier = Modifier
                      .fillMaxWidth()
                      .height(130.dp),
                    contentScale = ContentScale.Crop
                  )
                  Column(modifier = Modifier.padding(12.dp)) {
                    Surface(
                      color = MaterialTheme.colorScheme.primaryContainer,
                      shape = RoundedCornerShape(8.dp)
                    ) {
                      Text(
                        text = program.targetLevel,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                      )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                      text = program.name,
                      style = MaterialTheme.typography.titleSmall,
                      fontWeight = FontWeight.Bold,
                      color = MaterialTheme.colorScheme.onSurface,
                      maxLines = 2,
                      overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                      text = program.description,
                      style = MaterialTheme.typography.bodySmall,
                      color = MaterialTheme.colorScheme.onSurfaceVariant,
                      maxLines = 3,
                      overflow = TextOverflow.Ellipsis
                    )
                  }
                }
              }
            }
          }
        }
      }

      // 6. BERITA TERBARU
      item {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
          SectionHeader(
            badgeText = "INFORMASI TERKINI",
            title = "Berita & Publikasi",
            subtitle = "Kabar aktivitas dan prestasi keluarga besar madrasah"
          )

          newsList.take(3).forEach { news ->
            Card(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
                .clickable {
                  viewModel.selectNews(news)
                  viewModel.navigateTo(AppScreen.NEWS)
                },
              colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
              shape = RoundedCornerShape(14.dp),
              elevation = CardDefaults.cardElevation(2.dp)
            ) {
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                SchoolImage(
                  model = news.imagePath,
                  contentDescription = news.title,
                  modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(10.dp)),
                  contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Surface(
                      color = MaterialTheme.colorScheme.secondaryContainer,
                      shape = RoundedCornerShape(6.dp)
                    ) {
                      Text(
                        text = news.category,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                      )
                    }
                    Text(
                      text = news.date,
                      style = MaterialTheme.typography.labelSmall,
                      color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                  }
                  Spacer(modifier = Modifier.height(4.dp))
                  Text(
                    text = news.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                  )
                  Spacer(modifier = Modifier.height(4.dp))
                  Text(
                    text = "Baca Selengkapnya →",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                  )
                }
              }
            }
          }

          OutlinedButton(
            onClick = { viewModel.navigateTo(AppScreen.NEWS) },
            modifier = Modifier
              .fillMaxWidth()
              .padding(top = 8.dp),
            shape = RoundedCornerShape(10.dp)
          ) {
            Text("Lihat Semua Berita")
          }
        }
      }

      // 7. AGENDA & KEGIATAN SEKOLAH
      item {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
          SectionHeader(
            badgeText = "JADWAL KEGIATAN",
            title = "Agenda Madrasah",
            subtitle = "Catat tanggal penting agenda kegiatan madrasah"
          )

          agendasList.forEach { agenda ->
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
                Box(
                  modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.primary),
                  contentAlignment = Alignment.Center
                ) {
                  Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                  )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                  Text(
                    text = agenda.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                  )
                  Text(
                    text = "📅 ${agenda.date} • ⏰ ${agenda.time}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Medium
                  )
                  Text(
                    text = "📍 ${agenda.location}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                  )
                }
              }
            }
          }
        }
      }

      // 8. GALERI FOTO PREVIEW
      item {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
          SectionHeader(
            badgeText = "DOKUMENTASI",
            title = "Galeri Madrasah",
            subtitle = "Potret kegiatan dan lingkungan belajar siswa"
          )

          FlowRow(
            modifier = Modifier.fillMaxWidth(),
            maxItemsInEachRow = 3,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            galleryList.take(6).forEach { item ->
              Card(
                modifier = Modifier
                  .weight(1f)
                  .height(105.dp)
                  .clickable { viewModel.setLightboxGallery(item) },
                shape = RoundedCornerShape(10.dp)
              ) {
                SchoolImage(
                  model = item.imagePath,
                  contentDescription = item.title,
                  modifier = Modifier.fillMaxSize(),
                  contentScale = ContentScale.Crop
                )
              }
            }
          }

          OutlinedButton(
            onClick = { viewModel.navigateTo(AppScreen.GALLERY) },
            modifier = Modifier
              .fillMaxWidth()
              .padding(top = 10.dp),
            shape = RoundedCornerShape(10.dp)
          ) {
            Text("Lihat Galeri Lengkap")
          }
        }
      }

      // 9. TESTIMONI ALUMNI & WALI MURID
      item {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
          SectionHeader(
            badgeText = "APA KATA MEREKA?",
            title = "Testimoni",
            subtitle = "Pengalaman nyata dari wali santri dan alumni tercinta"
          )

          testimonials.forEach { item ->
            Card(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
              colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
              shape = RoundedCornerShape(14.dp),
              elevation = CardDefaults.cardElevation(2.dp)
            ) {
              Column(modifier = Modifier.padding(16.dp)) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                      modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                      contentAlignment = Alignment.Center
                    ) {
                      Icon(
                        imageVector = Icons.Default.FormatQuote,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                      )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                      Text(
                        text = item.name,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                      )
                      Text(
                        text = item.role,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                      )
                    }
                  }

                  // 5 stars
                  Row {
                    repeat(item.rating) {
                      Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(16.dp)
                      )
                    }
                  }
                }

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                  text = "\"${item.content}\"",
                  style = MaterialTheme.typography.bodySmall,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }
            }
          }
        }
      }

      // 10. CALL TO ACTION PPDB
      item {
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
          colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
          ),
          shape = RoundedCornerShape(16.dp)
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Icon(
              imageVector = Icons.Default.Campaign,
              contentDescription = null,
              tint = Color.White,
              modifier = Modifier.size(36.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = "Penerimaan Peserta Didik Baru (PPDB)",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = Color.White,
              textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "Pendaftaran Gelombang Terbuka untuk Tahun Pelajaran Baru. Kuota terbatas! Daftarkan ananda sekarang secara online.",
              style = MaterialTheme.typography.bodySmall,
              color = Color.White.copy(alpha = 0.9f),
              textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
              onClick = { viewModel.navigateTo(AppScreen.PPDB) },
              colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
              shape = RoundedCornerShape(10.dp)
            ) {
              Text("Isi Formulir Pendaftaran Sekarang", fontWeight = FontWeight.Bold)
              Spacer(modifier = Modifier.width(6.dp))
              Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
            }
          }
        }
      }

      // 11. FOOTER LENGKAP
      item {
        SchoolFooter(
          profile = profile,
          onNavigate = { viewModel.navigateTo(it) }
        )
      }
    }

    // Scroll to Top Button
    SmallFloatingActionButton(
      onClick = {
        coroutineScope.launch { listState.animateScrollToItem(0) }
      },
      containerColor = MaterialTheme.colorScheme.surfaceVariant,
      contentColor = MaterialTheme.colorScheme.primary,
      modifier = Modifier
        .align(Alignment.BottomStart)
        .padding(start = 16.dp, bottom = 20.dp)
    ) {
      Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "Scroll to Top")
    }
  }
}
