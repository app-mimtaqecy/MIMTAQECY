package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.HowToReg
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ui.components.ImagePickerButton
import com.example.ui.components.SchoolFooter
import com.example.ui.components.SectionHeader
import com.example.viewmodel.SchoolViewModel

@Composable
fun PpdbScreen(
  viewModel: SchoolViewModel,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val profile by viewModel.schoolProfile.collectAsState()

  var selectedTab by remember { mutableStateOf(0) } // 0: Informasi PPDB, 1: Formulir Pendaftaran

  // Form State
  var studentName by remember { mutableStateOf("") }
  var nisn by remember { mutableStateOf("") }
  var nik by remember { mutableStateOf("") }
  var birthPlace by remember { mutableStateOf("") }
  var birthDate by remember { mutableStateOf("") }
  var gender by remember { mutableStateOf("Laki-laki") }
  var address by remember { mutableStateOf("") }
  var whatsappNumber by remember { mutableStateOf("") }
  var previousSchool by remember { mutableStateOf("") }

  // Father
  var fatherName by remember { mutableStateOf("") }
  var fatherNik by remember { mutableStateOf("") }
  var fatherBirthPlace by remember { mutableStateOf("") }
  var fatherBirthDate by remember { mutableStateOf("") }
  var fatherEducation by remember { mutableStateOf("SMA/SMK") }
  var fatherOccupation by remember { mutableStateOf("Wiraswasta") }

  // Mother
  var motherName by remember { mutableStateOf("") }
  var motherNik by remember { mutableStateOf("") }
  var motherBirthPlace by remember { mutableStateOf("") }
  var motherBirthDate by remember { mutableStateOf("") }
  var motherEducation by remember { mutableStateOf("SMA/SMK") }
  var motherOccupation by remember { mutableStateOf("Ibu Rumah Tangga") }

  // Uploaded Documents
  var kkPath by remember { mutableStateOf("") }
  var ijazahPath by remember { mutableStateOf("") }
  var aktaPath by remember { mutableStateOf("") }

  // Submission Dialog
  var showSuccessDialog by remember { mutableStateOf(false) }
  var generatedRegNumber by remember { mutableStateOf("") }

  if (showSuccessDialog) {
    AlertDialog(
      onDismissRequest = { showSuccessDialog = false },
      icon = {
        Icon(
          imageVector = Icons.Default.CheckCircle,
          contentDescription = null,
          tint = Color(0xFF16A34A),
          modifier = Modifier.size(54.dp)
        )
      },
      title = {
        Text(
          text = "Pendaftaran Berhasil Disimpan!",
          fontWeight = FontWeight.Bold,
          textAlign = TextAlign.Center
        )
      },
      text = {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Text(
            text = "Selamat, data formulir pendaftaran ananda $studentName telah terkirim ke panitia PPDB MI Muhammadiyah Tanjung Qencono.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
          )
          Spacer(modifier = Modifier.height(12.dp))
          Surface(
            color = MaterialTheme.colorScheme.primaryContainer,
            shape = RoundedCornerShape(8.dp)
          ) {
            Text(
              text = "Nomor Registrasi: $generatedRegNumber",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onPrimaryContainer,
              modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
            )
          }
          Spacer(modifier = Modifier.height(10.dp))
          Text(
            text = "Silakan simpan atau bagikan kartu bukti pendaftaran di bawah ini untuk dibawa saat verifikasi fisik berkas.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            shareRegistrationReceipt(
              context = context,
              regNumber = generatedRegNumber,
              studentName = studentName,
              nisn = nisn,
              gender = gender,
              address = address,
              phone = whatsappNumber
            )
          }
        ) {
          Icon(imageVector = Icons.Default.Download, contentDescription = null, modifier = Modifier.size(18.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("DOWNLOAD / CETAK BUKTI")
        }
      },
      dismissButton = {
        OutlinedButton(onClick = { showSuccessDialog = false }) {
          Text("Tutup")
        }
      }
    )
  }

  LazyColumn(modifier = modifier.fillMaxSize()) {
    // Top Banner
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .background(MaterialTheme.colorScheme.primary)
          .padding(vertical = 28.dp, horizontal = 20.dp),
        contentAlignment = Alignment.Center
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f),
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.secondary)
          ) {
            Text(
              text = "PPDB ONLINE 2026/2027",
              style = MaterialTheme.typography.labelSmall,
              color = MaterialTheme.colorScheme.secondary,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
            )
          }
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "Penerimaan Peserta Didik Baru",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary
          )
          Text(
            text = "MI Muhammadiyah Tanjung Qencono",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
          )
        }
      }
    }

    // Tabs for Info vs Formulir
    item {
      TabRow(
        selectedTabIndex = selectedTab,
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(16.dp).clip(RoundedCornerShape(12.dp))
      ) {
        Tab(
          selected = selectedTab == 0,
          onClick = { selectedTab = 0 },
          text = { Text("Informasi & Syarat", fontWeight = FontWeight.Bold) }
        )
        Tab(
          selected = selectedTab == 1,
          onClick = { selectedTab = 1 },
          text = { Text("Formulir Online", fontWeight = FontWeight.Bold) }
        )
      }
    }

    if (selectedTab == 0) {
      // 1. INFORMASI & PERSYARATAN
      item {
        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
          // CTA to Form
          Card(
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            shape = RoundedCornerShape(14.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth().padding(14.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = "Siap Mendaftar Sekarang?",
                  style = MaterialTheme.typography.titleSmall,
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                  text = "Formulir online dapat diisi dari smartphone Anda dengan melampirkan foto dokumen.",
                  style = MaterialTheme.typography.bodySmall,
                  color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                )
              }
              Spacer(modifier = Modifier.width(8.dp))
              Button(
                onClick = { selectedTab = 1 },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                shape = RoundedCornerShape(8.dp)
              ) {
                Text("Daftar Sekarang", fontWeight = FontWeight.Bold)
              }
            }
          }

          // Persyaratan
          Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(14.dp),
            elevation = CardDefaults.cardElevation(2.dp)
          ) {
            Column(modifier = Modifier.padding(16.dp)) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.AssignmentTurnedIn, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Persyaratan Pendaftaran", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
              }
              Spacer(modifier = Modifier.height(10.dp))
              listOf(
                "Usia minimal 6 tahun pada 1 Juli tahun pelajaran berjalan (prioritas usia 7 tahun).",
                "Fotokopi / Unggahan Foto Kartu Keluarga (KK).",
                "Fotokopi / Unggahan Foto Akta Kelahiran calon peserta didik.",
                "Fotokopi / Unggahan Foto Ijazah atau Surat Keterangan Lulus (SKL) dari RA/TK (jika ada).",
                "Mengisi lengkap formulir pendaftaran online di aplikasi ini atau di sekretariat PPDB madrasah.",
                "Pas foto calon siswa ukuran 3x4 (2 lembar diserahkan saat verifikasi fisik)."
              ).forEach { item ->
                Row(modifier = Modifier.padding(vertical = 3.dp), verticalAlignment = Alignment.Top) {
                  Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color(0xFF16A34A), modifier = Modifier.size(16.dp))
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(text = item, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                }
              }
            }
          }

          // Jadwal Pendaftaran
          Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(14.dp),
            elevation = CardDefaults.cardElevation(2.dp)
          ) {
            Column(modifier = Modifier.padding(16.dp)) {
              Text("Jadwal Gelombang Pendaftaran", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
              Spacer(modifier = Modifier.height(8.dp))
              Text("• Gelombang 1 (Jalur Prestasi & Minat): 01 Februari - 30 April 2027", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
              Text("• Gelombang 2 (Jalur Reguler): 02 Mei - 10 Juli 2027", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
              Text("• Pengumuman Verifikasi & Daftar Ulang: 11 - 15 Juli 2027", style = MaterialTheme.typography.bodyMedium)
              Text("• Matsama (Masa Ta'aruf Siswa Madrasah): 17 Juli 2027", style = MaterialTheme.typography.bodyMedium)
            }
          }

          // Alur Pendaftaran
          Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(14.dp),
            elevation = CardDefaults.cardElevation(2.dp)
          ) {
            Column(modifier = Modifier.padding(16.dp)) {
              Text("Alur Pendaftaran PPDB", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
              Spacer(modifier = Modifier.height(8.dp))
              listOf(
                "1. Pendaftaran Online / Offline" to "Mengisi formulir online melalui aplikasi ini dan mengunggah dokumen KK, Akta, dan Ijazah.",
                "2. Validasi & Cetak Kartu Bukti" to "Menyimpan bukti nomor pendaftaran dan melengkapi dokumen fisik ke sekretariat madrasah.",
                "3. Pemetaan Kemampuan Mengaji & Motorik" to "Observasi ringan membaca Al-Qur'an/Iqra dan kesiapan belajar tanpa tes membebani.",
                "4. Pengumuman & Pengambilan Seragam" to "Siswa resmi diterima menerima paket seragam batik khas MIM, HW, dan olahraga."
              ).forEach { (step, desc) ->
                Column(modifier = Modifier.padding(vertical = 4.dp)) {
                  Text(step, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
                  Text(desc, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
              }
            }
          }

          // Biaya Pendidikan
          Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(14.dp),
            elevation = CardDefaults.cardElevation(2.dp)
          ) {
            Column(modifier = Modifier.padding(16.dp)) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.MonetizationOn, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Biaya Pendidikan Ramah & Terjangkau", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
              }
              Spacer(modifier = Modifier.height(8.dp))
              Text("• Biaya Formulir Pendaftaran: GRATIS (Rp 0)", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
              Text("• Uang Gedung / Pembangunan: BEBAS UANG GEDUNG", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = Color(0xFF16A34A))
              Text("• SPP Bulanan: Sangat terjangkau dan disubsidi BOS madrasah.", style = MaterialTheme.typography.bodyMedium)
              Text("• Beasiswa: Beasiswa penuh bagi santri yatim piatu dan santri penghafal juz 30.", style = MaterialTheme.typography.bodyMedium)
            }
          }

          // FAQ
          Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(14.dp),
            elevation = CardDefaults.cardElevation(2.dp)
          ) {
            Column(modifier = Modifier.padding(16.dp)) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.HelpOutline, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.width(8.dp))
                Text("FAQ (Pertanyaan Populer)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
              }
              Spacer(modifier = Modifier.height(8.dp))
              Text("T: Apakah jika belum bisa membaca Al-Qur'an/Iqra bisa mendaftar?", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
              Text("J: Tentu bisa! Di kelas 1 kami memiliki program bimbingan Iqra intensif dari awal hingga lancar.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
              Spacer(modifier = Modifier.height(6.dp))
              Text("T: Bagaimana jika belum memiliki ijazah TK/RA?", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
              Text("J: Calon siswa tetap bisa mendaftar dengan menggunakan Akta Kelahiran dan Kartu Keluarga (KK).", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
          }
        }
      }
    } else {
      // 2. FORMULIR PENDAFTARAN ONLINE LENGKAP
      item {
        Card(
          modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          shape = RoundedCornerShape(16.dp),
          elevation = CardDefaults.cardElevation(2.dp)
        ) {
          Column(modifier = Modifier.padding(18.dp)) {
            Text(
              text = "Formulir Pendaftaran Siswa Baru",
              style = MaterialTheme.typography.titleLarge,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.primary
            )
            Text(
              text = "Mohon isi formulir dengan data yang valid sesuai dokumen resmi.",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(12.dp))

            // A. DATA CALON SISWA
            Text(
              text = "A. DATA CALON SISWA",
              style = MaterialTheme.typography.titleSmall,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
              value = studentName,
              onValueChange = { studentName = it },
              label = { Text("Nama Lengkap Siswa *") },
              modifier = Modifier.fillMaxWidth(),
              singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
              value = nisn,
              onValueChange = { nisn = it },
              label = { Text("NISN (Lihat di Ijazah TK/RA jika ada)") },
              modifier = Modifier.fillMaxWidth(),
              singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
              value = nik,
              onValueChange = { nik = it },
              label = { Text("NIK Siswa (Lihat di KK) *") },
              modifier = Modifier.fillMaxWidth(),
              singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
              OutlinedTextField(
                value = birthPlace,
                onValueChange = { birthPlace = it },
                label = { Text("Tempat Lahir *") },
                modifier = Modifier.weight(1f),
                singleLine = true
              )
              OutlinedTextField(
                value = birthDate,
                onValueChange = { birthDate = it },
                label = { Text("Tgl Lahir (Contoh: 12 Mei 2019) *") },
                modifier = Modifier.weight(1f),
                singleLine = true
              )
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Jenis Kelamin
            Text("Jenis Kelamin *", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
            Row(verticalAlignment = Alignment.CenterVertically) {
              RadioButton(selected = gender == "Laki-laki", onClick = { gender = "Laki-laki" })
              Text("Laki-laki", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.clickable { gender = "Laki-laki" })
              Spacer(modifier = Modifier.width(20.dp))
              RadioButton(selected = gender == "Perempuan", onClick = { gender = "Perempuan" })
              Text("Perempuan", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.clickable { gender = "Perempuan" })
            }
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
              value = address,
              onValueChange = { address = it },
              label = { Text("Alamat Tempat Tinggal Lengkap *") },
              modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
              value = whatsappNumber,
              onValueChange = { whatsappNumber = it },
              label = { Text("Nomor WhatsApp Aktif Wali Murid *") },
              placeholder = { Text("Contoh: 085378029852") },
              modifier = Modifier.fillMaxWidth(),
              singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
              value = previousSchool,
              onValueChange = { previousSchool = it },
              label = { Text("Asal Sekolah (TK / RA / PAUD)") },
              modifier = Modifier.fillMaxWidth(),
              singleLine = true
            )

            Spacer(modifier = Modifier.height(18.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(12.dp))

            // B. DATA ORANG TUA (AYAH)
            Text(
              text = "B. DATA ORANG TUA (AYAH)",
              style = MaterialTheme.typography.titleSmall,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
              value = fatherName,
              onValueChange = { fatherName = it },
              label = { Text("Nama Lengkap Ayah *") },
              modifier = Modifier.fillMaxWidth(),
              singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
              value = fatherNik,
              onValueChange = { fatherNik = it },
              label = { Text("NIK Ayah *") },
              modifier = Modifier.fillMaxWidth(),
              singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
              OutlinedTextField(
                value = fatherBirthPlace,
                onValueChange = { fatherBirthPlace = it },
                label = { Text("Tempat Lahir Ayah") },
                modifier = Modifier.weight(1f),
                singleLine = true
              )
              OutlinedTextField(
                value = fatherBirthDate,
                onValueChange = { fatherBirthDate = it },
                label = { Text("Tgl Lahir Ayah") },
                modifier = Modifier.weight(1f),
                singleLine = true
              )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
              OutlinedTextField(
                value = fatherEducation,
                onValueChange = { fatherEducation = it },
                label = { Text("Pendidikan") },
                modifier = Modifier.weight(1f),
                singleLine = true
              )
              OutlinedTextField(
                value = fatherOccupation,
                onValueChange = { fatherOccupation = it },
                label = { Text("Pekerjaan") },
                modifier = Modifier.weight(1f),
                singleLine = true
              )
            }

            Spacer(modifier = Modifier.height(18.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(12.dp))

            // C. DATA ORANG TUA (IBU)
            Text(
              text = "C. DATA ORANG TUA (IBU)",
              style = MaterialTheme.typography.titleSmall,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
              value = motherName,
              onValueChange = { motherName = it },
              label = { Text("Nama Lengkap Ibu *") },
              modifier = Modifier.fillMaxWidth(),
              singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
              value = motherNik,
              onValueChange = { motherNik = it },
              label = { Text("NIK Ibu *") },
              modifier = Modifier.fillMaxWidth(),
              singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
              OutlinedTextField(
                value = motherBirthPlace,
                onValueChange = { motherBirthPlace = it },
                label = { Text("Tempat Lahir Ibu") },
                modifier = Modifier.weight(1f),
                singleLine = true
              )
              OutlinedTextField(
                value = motherBirthDate,
                onValueChange = { motherBirthDate = it },
                label = { Text("Tgl Lahir Ibu") },
                modifier = Modifier.weight(1f),
                singleLine = true
              )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
              OutlinedTextField(
                value = motherEducation,
                onValueChange = { motherEducation = it },
                label = { Text("Pendidikan") },
                modifier = Modifier.weight(1f),
                singleLine = true
              )
              OutlinedTextField(
                value = motherOccupation,
                onValueChange = { motherOccupation = it },
                label = { Text("Pekerjaan") },
                modifier = Modifier.weight(1f),
                singleLine = true
              )
            }

            Spacer(modifier = Modifier.height(18.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(12.dp))

            // D. UPLOAD DOKUMEN (JPG, JPEG, PNG DARI PRANGKAT HP)
            Text(
              text = "D. UNGGAH DOKUMEN (DARI HP)",
              style = MaterialTheme.typography.titleSmall,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.primary
            )
            Text(
              text = "Pilih gambar dari galeri HP berformat JPG, JPEG, atau PNG.",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(12.dp))

            ImagePickerButton(
              label = "1. Upload Kartu Keluarga (KK) *",
              currentImagePath = kkPath,
              onImageSaved = { kkPath = it },
              onImageRemoved = { kkPath = "" },
              prefix = "doc_kk",
              isDocument = true
            )
            Spacer(modifier = Modifier.height(10.dp))

            ImagePickerButton(
              label = "2. Upload Ijazah / SKL TK / RA (Jika ada)",
              currentImagePath = ijazahPath,
              onImageSaved = { ijazahPath = it },
              onImageRemoved = { ijazahPath = "" },
              prefix = "doc_ijazah",
              isDocument = true
            )
            Spacer(modifier = Modifier.height(10.dp))

            ImagePickerButton(
              label = "3. Upload Akta Kelahiran *",
              currentImagePath = aktaPath,
              onImageSaved = { aktaPath = it },
              onImageRemoved = { aktaPath = "" },
              prefix = "doc_akta",
              isDocument = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            // TOMBOL SUBMIT
            Button(
              onClick = {
                if (studentName.isBlank() || whatsappNumber.isBlank() || address.isBlank() || fatherName.isBlank() || motherName.isBlank()) {
                  Toast.makeText(context, "Harap lengkapi semua kolom wajib bertanda *", Toast.LENGTH_LONG).show()
                } else {
                  viewModel.submitPpdbApplication(
                    studentName = studentName,
                    nisn = nisn,
                    nik = nik,
                    birthPlace = birthPlace,
                    birthDate = birthDate,
                    gender = gender,
                    address = address,
                    whatsappNumber = whatsappNumber,
                    previousSchool = previousSchool,
                    fatherName = fatherName,
                    fatherNik = fatherNik,
                    fatherBirthPlace = fatherBirthPlace,
                    fatherBirthDate = fatherBirthDate,
                    fatherEducation = fatherEducation,
                    fatherOccupation = fatherOccupation,
                    motherName = motherName,
                    motherNik = motherNik,
                    motherBirthPlace = motherBirthPlace,
                    motherBirthDate = motherBirthDate,
                    motherEducation = motherEducation,
                    motherOccupation = motherOccupation,
                    kkDocPath = kkPath,
                    ijazahDocPath = ijazahPath,
                    aktaDocPath = aktaPath,
                    onSuccess = { regNo ->
                      generatedRegNumber = regNo
                      showSuccessDialog = true
                    }
                  )
                }
              },
              modifier = Modifier.fillMaxWidth().height(50.dp),
              shape = RoundedCornerShape(12.dp)
            ) {
              Icon(imageVector = Icons.Default.HowToReg, contentDescription = null)
              Spacer(modifier = Modifier.width(8.dp))
              Text("Kirim Formulir Pendaftaran Sekarang", fontWeight = FontWeight.Bold)
            }
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

fun shareRegistrationReceipt(
  context: Context,
  regNumber: String,
  studentName: String,
  nisn: String,
  gender: String,
  address: String,
  phone: String
) {
  val receiptText = """
    ========================================
    BUKTI PENDAFTARAN PPDB ONLINE
    MI MUHAMMADIYAH TANJUNG QENCONO
    TAHUN PELAJARAN 2026/2027
    ========================================
    No. Registrasi : $regNumber
    Nama Calon     : $studentName
    NISN           : ${if (nisn.isNotBlank()) nisn else "-"}
    Jenis Kelamin  : $gender
    Alamat         : $address
    No. WhatsApp   : $phone
    Status Berkas  : Diterima Sistem (Menunggu Verifikasi Fisik)
    
    Catatan Panitia:
    Harap simpan bukti pendaftaran ini dan serahkan fotokopi KK & Akta ke kantor madrasah:
    Jl. Pendidikan No.01 Desa Tanjung Qencono Kec. Way Bungur Kab. Lampung Timur.
    Narahubung PPDB: 0853-7802-9852
    ========================================
  """.trimIndent()

  try {
    val sendIntent = Intent().apply {
      action = Intent.ACTION_SEND
      putExtra(Intent.EXTRA_TEXT, receiptText)
      type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, "Simpan / Bagikan Bukti PPDB")
    context.startActivity(shareIntent)
  } catch (e: Exception) {
    Toast.makeText(context, "Berhasil mencetak bukti: $regNumber", Toast.LENGTH_SHORT).show()
  }
}
