package com.example.ui.screens.admin

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.model.PpdbEntity
import com.example.ui.components.SchoolImage
import com.example.viewmodel.SchoolViewModel

@Composable
fun AdminPpdbSection(viewModel: SchoolViewModel) {
  val context = LocalContext.current
  val ppdbList by viewModel.ppdbList.collectAsState()

  var selectedStatusFilter by remember { mutableStateOf("Semua") }
  val statusFilters = listOf("Semua", "Menunggu Verifikasi", "Diterima", "Ditolak")

  var viewingApplicant by remember { mutableStateOf<PpdbEntity?>(null) }
  var documentPreviewPath by remember { mutableStateOf<String?>(null) }

  val filteredPpdb = ppdbList.filter {
    selectedStatusFilter == "Semua" || it.status.equals(selectedStatusFilter, ignoreCase = true)
  }

  // Detail Applicant Dialog
  if (viewingApplicant != null) {
    PpdbApplicantDetailDialog(
      applicant = viewingApplicant!!,
      onDismiss = { viewingApplicant = null },
      onStatusChange = { newStatus, notes ->
        viewModel.updatePpdbStatus(viewingApplicant!!, newStatus, notes)
        viewingApplicant = viewingApplicant!!.copy(status = newStatus, notes = notes)
        Toast.makeText(context, "Status berhasil diperbarui!", Toast.LENGTH_SHORT).show()
      },
      onViewDocument = { docPath ->
        documentPreviewPath = docPath
      },
      onDownloadDossier = { app ->
        downloadApplicantDossier(context, app)
      }
    )
  }

  // Document Lightbox Preview Dialog
  if (documentPreviewPath != null) {
    AlertDialog(
      onDismissRequest = { documentPreviewPath = null },
      title = { Text("Pratinjau Dokumen Unggahan") },
      text = {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
          contentAlignment = Alignment.Center
        ) {
          SchoolImage(
            model = documentPreviewPath,
            contentDescription = "Dokumen",
            modifier = Modifier.fillMaxWidth().height(290.dp).clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Fit
          )
        }
      },
      confirmButton = {
        Button(onClick = { documentPreviewPath = null }) { Text("Tutup") }
      }
    )
  }

  Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
    // Header
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column {
        Text("Kelola Pendaftaran PPDB", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        Text("Total Pendaftar: ${ppdbList.size} Calon Siswa", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
      }
      Button(
        onClick = { exportAllPpdbSummary(context, ppdbList) },
        shape = RoundedCornerShape(8.dp)
      ) {
        Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text("Unduh Semua Data", style = MaterialTheme.typography.labelSmall)
      }
    }

    Spacer(modifier = Modifier.height(12.dp))

    // Filter Chips
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
      items(statusFilters.size) { i ->
        val status = statusFilters[i]
        FilterChip(
          selected = selectedStatusFilter == status,
          onClick = { selectedStatusFilter = status },
          label = { Text(status) }
        )
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // Table / List
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(12.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
      elevation = CardDefaults.cardElevation(2.dp)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(8.dp)
          .horizontalScroll(rememberScrollState())
      ) {
        // Table Header
        Row(
          modifier = Modifier
            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(6.dp))
            .padding(vertical = 10.dp, horizontal = 8.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text("NO REG", Modifier.width(130.dp), color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelMedium)
          Text("NAMA SISWA", Modifier.width(180.dp), color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelMedium)
          Text("TGL DAFTAR", Modifier.width(110.dp), color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelMedium)
          Text("WHATSAPP", Modifier.width(120.dp), color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelMedium)
          Text("STATUS", Modifier.width(150.dp), color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelMedium)
          Text("AKSI", Modifier.width(130.dp), color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelMedium)
        }

        if (filteredPpdb.isEmpty()) {
          Box(modifier = Modifier.width(820.dp).padding(24.dp), contentAlignment = Alignment.Center) {
            Text("Tidak ada data pendaftar pada filter ini.")
          }
        } else {
          filteredPpdb.forEach { item ->
            val statusColor = when (item.status) {
              "Diterima" -> Color(0xFF16A34A)
              "Ditolak" -> MaterialTheme.colorScheme.error
              else -> Color(0xFFEA580C)
            }

            Row(
              modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 8.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(item.registrationNumber, Modifier.width(130.dp), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
              Text(item.studentName, Modifier.width(180.dp), fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.bodySmall)
              Text(item.registrationDate, Modifier.width(110.dp), style = MaterialTheme.typography.bodySmall)
              Text(item.whatsappNumber, Modifier.width(120.dp), style = MaterialTheme.typography.bodySmall)

              Surface(
                modifier = Modifier.width(150.dp).padding(end = 8.dp),
                color = statusColor.copy(alpha = 0.15f),
                shape = RoundedCornerShape(6.dp)
              ) {
                Text(
                  text = item.status,
                  color = statusColor,
                  fontWeight = FontWeight.Bold,
                  style = MaterialTheme.typography.labelSmall,
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                )
              }

              Row(Modifier.width(130.dp)) {
                IconButton(onClick = { viewingApplicant = item }, modifier = Modifier.size(32.dp)) {
                  Icon(Icons.Default.Visibility, contentDescription = "Lihat Detail", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                }
                IconButton(onClick = { downloadApplicantDossier(context, item) }, modifier = Modifier.size(32.dp)) {
                  Icon(Icons.Default.Download, contentDescription = "Unduh Berkas", tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(18.dp))
                }
                IconButton(onClick = { viewModel.deletePpdb(item) }, modifier = Modifier.size(32.dp)) {
                  Icon(Icons.Default.Delete, contentDescription = "Hapus", tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(18.dp))
                }
              }
            }
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
          }
        }
      }
    }
  }
}

@Composable
fun PpdbApplicantDetailDialog(
  applicant: PpdbEntity,
  onDismiss: () -> Unit,
  onStatusChange: (String, String) -> Unit,
  onViewDocument: (String) -> Unit,
  onDownloadDossier: (PpdbEntity) -> Unit
) {
  var selectedStatus by remember { mutableStateOf(applicant.status) }
  var notes by remember { mutableStateOf(applicant.notes) }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Default.Description, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.width(8.dp))
        Text("Berkas Pendaftar: ${applicant.registrationNumber}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
      }
    },
    text = {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .verticalScroll(rememberScrollState())
      ) {
        Text("Data Calon Siswa:", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        Text("• Nama: ${applicant.studentName}")
        Text("• NISN: ${if (applicant.nisn.isNotBlank()) applicant.nisn else "-"}")
        Text("• NIK: ${applicant.nik}")
        Text("• Tempat/Tgl Lahir: ${applicant.birthPlace}, ${applicant.birthDate}")
        Text("• Jenis Kelamin: ${applicant.gender}")
        Text("• Alamat: ${applicant.address}")
        Text("• WhatsApp: ${applicant.whatsappNumber}")
        Text("• Asal Sekolah: ${if (applicant.previousSchool.isNotBlank()) applicant.previousSchool else "-"}")

        Spacer(modifier = Modifier.height(10.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(10.dp))

        Text("Data Orang Tua:", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        Text("• Ayah: ${applicant.fatherName} (NIK: ${applicant.fatherNik})")
        Text("• Lahir Ayah: ${applicant.fatherBirthPlace}, ${applicant.fatherBirthDate}")
        Text("• Pendidikan/Pekerjaan Ayah: ${applicant.fatherEducation} / ${applicant.fatherOccupation}")
        Spacer(modifier = Modifier.height(4.dp))
        Text("• Ibu: ${applicant.motherName} (NIK: ${applicant.motherNik})")
        Text("• Lahir Ibu: ${applicant.motherBirthPlace}, ${applicant.motherBirthDate}")
        Text("• Pendidikan/Pekerjaan Ibu: ${applicant.motherEducation} / ${applicant.motherOccupation}")

        Spacer(modifier = Modifier.height(10.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(10.dp))

        Text("Dokumen Unggahan Calon Siswa:", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(6.dp))

        DocumentViewRow("1. Kartu Keluarga (KK)", applicant.kkDocumentPath, onViewDocument)
        DocumentViewRow("2. Ijazah / SKL", applicant.ijazahDocumentPath, onViewDocument)
        DocumentViewRow("3. Akta Kelahiran", applicant.aktaDocumentPath, onViewDocument)

        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(10.dp))

        Text("Status Verifikasi Berkas:", fontWeight = FontWeight.Bold)
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(vertical = 4.dp)) {
          listOf("Menunggu Verifikasi", "Diterima", "Ditolak").forEach { st ->
            FilterChip(
              selected = selectedStatus == st,
              onClick = { selectedStatus = st },
              label = { Text(st, style = MaterialTheme.typography.labelSmall) }
            )
          }
        }

        OutlinedTextField(
          value = notes,
          onValueChange = { notes = it },
          label = { Text("Catatan Verifikasi Panitia") },
          modifier = Modifier.fillMaxWidth().height(80.dp)
        )
      }
    },
    confirmButton = {
      Button(onClick = {
        onStatusChange(selectedStatus, notes)
        onDismiss()
      }) {
        Text("Simpan Status")
      }
    },
    dismissButton = {
      Row {
        OutlinedButton(onClick = { onDownloadDossier(applicant) }) {
          Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("Unduh Berkas")
        }
        Spacer(modifier = Modifier.width(6.dp))
        OutlinedButton(onClick = onDismiss) { Text("Tutup") }
      }
    }
  )
}

@Composable
fun DocumentViewRow(
  label: String,
  path: String,
  onView: (String) -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 3.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(label, style = MaterialTheme.typography.bodySmall)
    if (path.isNotBlank()) {
      Button(
        onClick = { onView(path) },
        shape = RoundedCornerShape(6.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
        modifier = Modifier.height(30.dp)
      ) {
        Text("Lihat Foto", style = MaterialTheme.typography.labelSmall)
      }
    } else {
      Text("Tidak dilampirkan", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
    }
  }
}

fun downloadApplicantDossier(context: Context, applicant: PpdbEntity) {
  val text = """
    ==================================================
    BERKAS RESMI PENDAFTARAN PPDB MI MUHAMMADIYAH TANJUNG QENCONO
    ==================================================
    Nomor Registrasi : ${applicant.registrationNumber}
    Tanggal Daftar   : ${applicant.registrationDate}
    Status           : ${applicant.status}
    Catatan Panitia  : ${applicant.notes}
    --------------------------------------------------
    DATA SISWA:
    Nama Lengkap     : ${applicant.studentName}
    NISN             : ${applicant.nisn}
    NIK              : ${applicant.nik}
    Tempat/Tgl Lahir : ${applicant.birthPlace}, ${applicant.birthDate}
    Jenis Kelamin    : ${applicant.gender}
    Alamat           : ${applicant.address}
    No. WhatsApp     : ${applicant.whatsappNumber}
    Asal Sekolah     : ${applicant.previousSchool}
    --------------------------------------------------
    DATA ORANG TUA:
    Nama Ayah        : ${applicant.fatherName} (NIK: ${applicant.fatherNik})
    Pendidikan/Kerja : ${applicant.fatherEducation} / ${applicant.fatherOccupation}
    Nama Ibu         : ${applicant.motherName} (NIK: ${applicant.motherNik})
    Pendidikan/Kerja : ${applicant.motherEducation} / ${applicant.motherOccupation}
    --------------------------------------------------
    DOKUMEN LAMPIRAN TERSIMPAN:
    - Kartu Keluarga : ${if (applicant.kkDocumentPath.isNotBlank()) "TERSEDIA DI PENYIMPANAN" else "BELUM"}
    - Ijazah / SKL   : ${if (applicant.ijazahDocumentPath.isNotBlank()) "TERSEDIA DI PENYIMPANAN" else "BELUM"}
    - Akta Kelahiran : ${if (applicant.aktaDocumentPath.isNotBlank()) "TERSEDIA DI PENYIMPANAN" else "BELUM"}
    ==================================================
  """.trimIndent()

  try {
    val sendIntent = Intent().apply {
      action = Intent.ACTION_SEND
      putExtra(Intent.EXTRA_TEXT, text)
      putExtra(Intent.EXTRA_SUBJECT, "Berkas_PPDB_${applicant.registrationNumber}.txt")
      type = "text/plain"
    }
    context.startActivity(Intent.createChooser(sendIntent, "Unduh / Bagikan Berkas PPDB"))
  } catch (e: Exception) {
    Toast.makeText(context, "Berkas siap diunduh!", Toast.LENGTH_SHORT).show()
  }
}

fun exportAllPpdbSummary(context: Context, list: List<PpdbEntity>) {
  val sb = StringBuilder()
  sb.append("NO,NO REGISTRASI,NAMA SISWA,NISN,NIK,JK,NO WA,ASAL SEKOLAH,NAMA AYAH,NAMA IBU,STATUS\n")
  list.forEachIndexed { index, app ->
    sb.append("${index + 1},${app.registrationNumber},\"${app.studentName}\",${app.nisn},${app.nik},${app.gender},${app.whatsappNumber},\"${app.previousSchool}\",\"${app.fatherName}\",\"${app.motherName}\",${app.status}\n")
  }

  try {
    val sendIntent = Intent().apply {
      action = Intent.ACTION_SEND
      putExtra(Intent.EXTRA_TEXT, sb.toString())
      type = "text/csv"
    }
    context.startActivity(Intent.createChooser(sendIntent, "Ekspor Rekapitulasi Data PPDB"))
  } catch (e: Exception) {
    Toast.makeText(context, "Ekspor disiapkan!", Toast.LENGTH_SHORT).show()
  }
}
