package com.example.ui.screens.admin

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.local.InitialData
import com.example.data.model.StudentEntity
import com.example.viewmodel.SchoolViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminStudentsSection(viewModel: SchoolViewModel) {
  val context = LocalContext.current
  val students by viewModel.studentsList.collectAsState()

  // Filter States
  var selectedClassFilter by remember { mutableIntStateOf(0) } // 0 = Semua, 1..6
  var selectedRombelFilter by remember { mutableStateOf("Semua") }
  var searchQuery by remember { mutableStateOf("") }

  // Dialog States
  var showAddDialog by remember { mutableStateOf(false) }
  var showBulkDialog by remember { mutableStateOf(false) }
  var editingStudent by remember { mutableStateOf<StudentEntity?>(null) }

  // Filtered List
  val filteredStudents = students.filter { student ->
    val matchesClass = (selectedClassFilter == 0 || student.classLevel == selectedClassFilter)
    val matchesRombel = (selectedRombelFilter == "Semua" || student.rombel.equals(selectedRombelFilter, ignoreCase = true))
    val matchesSearch = student.name.contains(searchQuery, ignoreCase = true) || student.nisn.contains(searchQuery, ignoreCase = true)
    matchesClass && matchesRombel && matchesSearch
  }

  // File Picker for Excel / CSV
  val filePickerLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.GetContent()
  ) { uri: Uri? ->
    if (uri != null) {
      try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val text = inputStream?.bufferedReader()?.use { it.readText() } ?: ""
        if (text.isNotBlank()) {
          val imported = viewModel.importStudentsFromCsv(text)
          Toast.makeText(context, "Berhasil mengimpor $imported data siswa!", Toast.LENGTH_SHORT).show()
        }
      } catch (e: Exception) {
        Toast.makeText(context, "Gagal membaca file: ${e.message}", Toast.LENGTH_SHORT).show()
      }
    }
  }

  // Dialog Add/Edit Individu
  if (showAddDialog || editingStudent != null) {
    StudentFormDialog(
      studentToEdit = editingStudent,
      onDismiss = {
        showAddDialog = false
        editingStudent = null
      },
      onSave = { entity ->
        viewModel.saveStudent(entity)
        showAddDialog = false
        editingStudent = null
        Toast.makeText(context, "Data siswa berhasil disimpan!", Toast.LENGTH_SHORT).show()
      }
    )
  }

  // Dialog Bulk Import Excel
  if (showBulkDialog) {
    BulkImportDialog(
      onDismiss = { showBulkDialog = false },
      onPickFile = {
        showBulkDialog = false
        filePickerLauncher.launch("*/*")
      },
      onDownloadTemplate = {
        downloadCsvTemplate(context)
      },
      onPasteSubmit = { csvText ->
        val imported = viewModel.importStudentsFromCsv(csvText)
        showBulkDialog = false
        Toast.makeText(context, "Berhasil mengimpor $imported siswa!", Toast.LENGTH_SHORT).show()
      }
    )
  }

  Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
    // Header actions
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column {
        Text("Kelola Data Siswa", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        Text("Total: ${students.size} Siswa Terdaftar", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
      }
      Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Button(onClick = { showAddDialog = true }, shape = RoundedCornerShape(8.dp)) {
          Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("Tambah Individu", style = MaterialTheme.typography.labelSmall)
        }
        OutlinedButton(onClick = { showBulkDialog = true }, shape = RoundedCornerShape(8.dp)) {
          Icon(Icons.Default.FileUpload, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("Tambah Sekaligus", style = MaterialTheme.typography.labelSmall)
        }
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // Search bar (KOLOM SEARCH NAMA)
    OutlinedTextField(
      value = searchQuery,
      onValueChange = { searchQuery = it },
      placeholder = { Text("Pencarian Nama Siswa / NISN...") },
      leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
      trailingIcon = {
        if (searchQuery.isNotEmpty()) {
          IconButton(onClick = { searchQuery = "" }) {
            Icon(Icons.Default.Close, contentDescription = "Clear")
          }
        }
      },
      singleLine = true,
      shape = RoundedCornerShape(10.dp),
      modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(10.dp))

    // FILTER KELAS (Kelas 1 sampai 6)
    Text("FILTER KELAS:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
    Spacer(modifier = Modifier.height(4.dp))
    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
      item {
        FilterChip(
          selected = selectedClassFilter == 0,
          onClick = { selectedClassFilter = 0 },
          label = { Text("Semua Kelas") }
        )
      }
      items(listOf(1, 2, 3, 4, 5, 6)) { k ->
        FilterChip(
          selected = selectedClassFilter == k,
          onClick = { selectedClassFilter = k },
          label = { Text("Kelas $k") }
        )
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    // FILTER ROMBEL (12 Rombel Asmaul Husna)
    Text("FILTER ROMBEL:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
    Spacer(modifier = Modifier.height(4.dp))
    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
      item {
        FilterChip(
          selected = selectedRombelFilter == "Semua",
          onClick = { selectedRombelFilter = "Semua" },
          label = { Text("Semua Rombel") }
        )
      }
      items(InitialData.rombelList) { r ->
        FilterChip(
          selected = selectedRombelFilter == r,
          onClick = { selectedRombelFilter = r },
          label = { Text(r) }
        )
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // TABEL DATA SISWA
    // NO. NAMA, NISN, TEMPAT LAHIR, TGL LAHIR, ROMBEL
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
          Text("NO", width(45.dp), color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelMedium)
          Text("NAMA SISWA", width(170.dp), color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelMedium)
          Text("NISN", width(110.dp), color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelMedium)
          Text("TEMPAT LAHIR", width(130.dp), color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelMedium)
          Text("TGL LAHIR", width(120.dp), color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelMedium)
          Text("KELAS", width(70.dp), color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelMedium)
          Text("ROMBEL", width(120.dp), color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelMedium)
          Text("AKSI", width(90.dp), color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelMedium)
        }

        if (filteredStudents.isEmpty()) {
          Box(modifier = Modifier.width(855.dp).padding(24.dp), contentAlignment = Alignment.Center) {
            Text("Tidak ada data siswa ditemukan untuk kriteria ini.")
          }
        } else {
          filteredStudents.forEachIndexed { index, st ->
            Row(
              modifier = Modifier
                .background(if (index % 2 == 0) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f))
                .padding(vertical = 8.dp, horizontal = 8.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text("${index + 1}", width(45.dp), style = MaterialTheme.typography.bodySmall)
              Text(st.name, width(170.dp), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
              Text(st.nisn, width(110.dp), style = MaterialTheme.typography.bodySmall)
              Text(st.birthPlace, width(130.dp), style = MaterialTheme.typography.bodySmall)
              Text(st.birthDate, width(120.dp), style = MaterialTheme.typography.bodySmall)
              Text("Kelas ${st.classLevel}", width(70.dp), style = MaterialTheme.typography.bodySmall)
              Text(st.rombel, width(120.dp), color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Medium, style = MaterialTheme.typography.bodySmall)

              Row(width(90.dp)) {
                IconButton(onClick = { editingStudent = st }, modifier = Modifier.size(32.dp)) {
                  Icon(Icons.Default.Edit, contentDescription = "Edit", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                }
                IconButton(onClick = { viewModel.deleteStudent(st) }, modifier = Modifier.size(32.dp)) {
                  Icon(Icons.Default.Delete, contentDescription = "Hapus", tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(16.dp))
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

private fun width(dp: androidx.compose.ui.unit.Dp) = Modifier.width(dp)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentFormDialog(
  studentToEdit: StudentEntity?,
  onDismiss: () -> Unit,
  onSave: (StudentEntity) -> Unit
) {
  var name by remember { mutableStateOf(studentToEdit?.name ?: "") }
  var nisn by remember { mutableStateOf(studentToEdit?.nisn ?: "") }
  var birthPlace by remember { mutableStateOf(studentToEdit?.birthPlace ?: "") }
  var birthDate by remember { mutableStateOf(studentToEdit?.birthDate ?: "") }
  var classLevel by remember { mutableIntStateOf(studentToEdit?.classLevel ?: 1) }
  var rombel by remember { mutableStateOf(studentToEdit?.rombel ?: InitialData.rombelList.first()) }

  var expandedRombel by remember { mutableStateOf(false) }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text(if (studentToEdit != null) "Edit Data Siswa" else "Tambah Siswa Individu", fontWeight = FontWeight.Bold) },
    text = {
      Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
          value = name,
          onValueChange = { name = it },
          label = { Text("Nama Siswa *") },
          modifier = Modifier.fillMaxWidth(),
          singleLine = true
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
          value = nisn,
          onValueChange = { nisn = it },
          label = { Text("NISN *") },
          modifier = Modifier.fillMaxWidth(),
          singleLine = true
        )
        Spacer(modifier = Modifier.height(6.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
          OutlinedTextField(
            value = birthPlace,
            onValueChange = { birthPlace = it },
            label = { Text("Tempat Lahir") },
            modifier = Modifier.weight(1f),
            singleLine = true
          )
          OutlinedTextField(
            value = birthDate,
            onValueChange = { birthDate = it },
            label = { Text("Tgl Lahir") },
            modifier = Modifier.weight(1f),
            singleLine = true
          )
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Kelas
        Text("Tingkat Kelas: Kelas $classLevel", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
          (1..6).forEach { k ->
            FilterChip(
              selected = classLevel == k,
              onClick = { classLevel = k },
              label = { Text("$k") }
            )
          }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Rombel Dropdown
        ExposedDropdownMenuBox(
          expanded = expandedRombel,
          onExpandedChange = { expandedRombel = !expandedRombel }
        ) {
          OutlinedTextField(
            value = rombel,
            onValueChange = {},
            readOnly = true,
            label = { Text("Rombel") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedRombel) },
            modifier = Modifier.menuAnchor().fillMaxWidth()
          )
          ExposedDropdownMenu(
            expanded = expandedRombel,
            onDismissRequest = { expandedRombel = false }
          ) {
            InitialData.rombelList.forEach { r ->
              DropdownMenuItem(
                text = { Text(r) },
                onClick = {
                  rombel = r
                  expandedRombel = false
                }
              )
            }
          }
        }
      }
    },
    confirmButton = {
      Button(onClick = {
        if (name.isNotBlank()) {
          onSave(
            studentToEdit?.copy(
              name = name,
              nisn = nisn,
              birthPlace = birthPlace,
              birthDate = birthDate,
              classLevel = classLevel,
              rombel = rombel
            ) ?: StudentEntity(
              name = name,
              nisn = nisn,
              birthPlace = birthPlace,
              birthDate = birthDate,
              classLevel = classLevel,
              rombel = rombel
            )
          )
        }
      }) {
        Text("Simpan Data")
      }
    },
    dismissButton = {
      OutlinedButton(onClick = onDismiss) { Text("Batal") }
    }
  )
}

@Composable
fun BulkImportDialog(
  onDismiss: () -> Unit,
  onPickFile: () -> Unit,
  onDownloadTemplate: () -> Unit,
  onPasteSubmit: (String) -> Unit
) {
  var pasteText by remember { mutableStateOf("") }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text("Tambah Siswa Sekaligus", fontWeight = FontWeight.Bold) },
    text = {
      Column(modifier = Modifier.fillMaxWidth()) {
        Text(
          text = "Format Excel/CSV:\nNO,NAMA,NISN,TEMPAT LAHIR,TGL LAHIR,ROMBEL",
          style = MaterialTheme.typography.bodySmall,
          fontWeight = FontWeight.SemiBold,
          color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(10.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
          Button(onClick = onDownloadTemplate, modifier = Modifier.weight(1f), shape = RoundedCornerShape(8.dp)) {
            Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Unduh Template Excel", style = MaterialTheme.typography.labelSmall)
          }
          OutlinedButton(onClick = onPickFile, modifier = Modifier.weight(1f), shape = RoundedCornerShape(8.dp)) {
            Icon(Icons.Default.FileUpload, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Pilih File Excel/CSV", style = MaterialTheme.typography.labelSmall)
          }
        }

        Spacer(modifier = Modifier.height(12.dp))
        Text("Atau tempel (paste) baris data di sini:", style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
          value = pasteText,
          onValueChange = { pasteText = it },
          placeholder = { Text("1,Muhammad Ali,0142385901,Lampung Timur,12 Mei 2018,Ar Rahman\n2,Fatimah,0142385902,Way Bungur,18 Juni 2018,Ar Rahman") },
          modifier = Modifier.fillMaxWidth().height(140.dp)
        )
      }
    },
    confirmButton = {
      Button(onClick = {
        if (pasteText.isNotBlank()) onPasteSubmit(pasteText)
      }) {
        Text("Impor Data")
      }
    },
    dismissButton = {
      OutlinedButton(onClick = onDismiss) { Text("Tutup") }
    }
  )
}

fun downloadCsvTemplate(context: Context) {
  val template = """
    NO,NAMA,NISN,TEMPAT LAHIR,TGL LAHIR,ROMBEL
    1,Muhammad Ali Pratama,0142385901,Lampung Timur,12 Mei 2018,Ar Rahman
    2,Aisyah Humaira,0142385902,Way Bungur,18 Juni 2018,Ar Rahiim
    3,Bilal Al-Habibi,0142385903,Metro,04 Januari 2018,Al Malik
    4,Dzakira Talita,0142385904,Tanjung Qencono,22 Agustus 2018,Al Quddus
  """.trimIndent()

  try {
    val sendIntent = Intent().apply {
      action = Intent.ACTION_SEND
      putExtra(Intent.EXTRA_TEXT, template)
      putExtra(Intent.EXTRA_TITLE, "template_siswa_mimtaqecy.csv")
      type = "text/csv"
    }
    context.startActivity(Intent.createChooser(sendIntent, "Unduh Template Excel/CSV"))
  } catch (e: Exception) {
    Toast.makeText(context, "Template disiapkan!", Toast.LENGTH_SHORT).show()
  }
}
