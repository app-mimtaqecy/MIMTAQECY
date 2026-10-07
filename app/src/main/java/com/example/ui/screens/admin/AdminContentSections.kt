package com.example.ui.screens.admin

import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MarkEmailRead
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.local.InitialData
import com.example.data.model.AgendaEntity
import com.example.data.model.AnnouncementEntity
import com.example.data.model.ContactMessageEntity
import com.example.data.model.ExtracurricularEntity
import com.example.data.model.FeaturedProgramEntity
import com.example.data.model.GalleryEntity
import com.example.data.model.NewsEntity
import com.example.data.model.SchoolProfileEntity
import com.example.data.model.TeacherEntity
import com.example.data.model.TestimonialEntity
import com.example.ui.components.ImagePickerButton
import com.example.ui.components.SchoolImage
import com.example.viewmodel.SchoolViewModel

// ================= BERITA =================
@Composable
fun AdminNewsSection(viewModel: SchoolViewModel) {
  val context = LocalContext.current
  val newsList by viewModel.newsList.collectAsState()
  var showDialog by remember { mutableStateOf(false) }
  var editingItem by remember { mutableStateOf<NewsEntity?>(null) }

  if (showDialog || editingItem != null) {
    NewsFormDialog(
      itemToEdit = editingItem,
      onDismiss = { showDialog = false; editingItem = null },
      onSave = { entity ->
        viewModel.saveNews(entity)
        showDialog = false
        editingItem = null
        Toast.makeText(context, "Berita berhasil disimpan!", Toast.LENGTH_SHORT).show()
      }
    )
  }

  Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
      Text("Kelola Berita Sekolah", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
      Button(onClick = { showDialog = true }, shape = RoundedCornerShape(8.dp)) {
        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text("Tambah Berita")
      }
    }
    Spacer(modifier = Modifier.height(14.dp))
    newsList.forEach { item ->
      Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp)
      ) {
        Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
          SchoolImage(model = item.imagePath, contentDescription = item.title, modifier = Modifier.size(70.dp).clip(RoundedCornerShape(8.dp)), contentScale = ContentScale.Crop)
          Spacer(modifier = Modifier.width(12.dp))
          Column(modifier = Modifier.weight(1f)) {
            Text(item.title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall, maxLines = 1)
            Text("${item.category} • ${item.date}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
            Text(item.summary, style = MaterialTheme.typography.bodySmall, maxLines = 2)
          }
          IconButton(onClick = { editingItem = item }) { Icon(Icons.Default.Edit, contentDescription = "Edit", tint = MaterialTheme.colorScheme.primary) }
          IconButton(onClick = { viewModel.deleteNews(item) }) { Icon(Icons.Default.Delete, contentDescription = "Hapus", tint = MaterialTheme.colorScheme.error) }
        }
      }
    }
  }
}

@Composable
fun NewsFormDialog(itemToEdit: NewsEntity?, onDismiss: () -> Unit, onSave: (NewsEntity) -> Unit) {
  var title by remember { mutableStateOf(itemToEdit?.title ?: "") }
  var category by remember { mutableStateOf(itemToEdit?.category ?: "Kegiatan") }
  var date by remember { mutableStateOf(itemToEdit?.date ?: "06 Okt 2026") }
  var summary by remember { mutableStateOf(itemToEdit?.summary ?: "") }
  var content by remember { mutableStateOf(itemToEdit?.content ?: "") }
  var imagePath by remember { mutableStateOf(itemToEdit?.imagePath ?: "") }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text(if (itemToEdit != null) "Edit Berita" else "Tambah Berita", fontWeight = FontWeight.Bold) },
    text = {
      Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {
        OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Judul Berita *") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("Kategori (Prestasi, Kegiatan, dll)") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(value = date, onValueChange = { date = it }, label = { Text("Tanggal") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        ImagePickerButton(label = "Unggah Foto Berita (JPG, JPEG, PNG)", currentImagePath = imagePath, onImageSaved = { imagePath = it }, onImageRemoved = { imagePath = "" }, prefix = "news")
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = summary, onValueChange = { summary = it }, label = { Text("Ringkasan Berita") }, modifier = Modifier.fillMaxWidth().height(80.dp))
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(value = content, onValueChange = { content = it }, label = { Text("Isi Berita Lengkap *") }, modifier = Modifier.fillMaxWidth().height(120.dp))
      }
    },
    confirmButton = { Button(onClick = { if (title.isNotBlank()) onSave(itemToEdit?.copy(title = title, category = category, date = date, summary = summary, content = content, imagePath = imagePath) ?: NewsEntity(title = title, category = category, date = date, summary = summary, content = content, imagePath = imagePath)) }) { Text("Simpan") } },
    dismissButton = { OutlinedButton(onClick = onDismiss) { Text("Batal") } }
  )
}

// ================= GALERI =================
@Composable
fun AdminGallerySection(viewModel: SchoolViewModel) {
  val context = LocalContext.current
  val list by viewModel.galleryList.collectAsState()
  var showDialog by remember { mutableStateOf(false) }
  var editingItem by remember { mutableStateOf<GalleryEntity?>(null) }

  if (showDialog || editingItem != null) {
    GalleryFormDialog(
      itemToEdit = editingItem,
      onDismiss = { showDialog = false; editingItem = null },
      onSave = { entity ->
        viewModel.saveGallery(entity)
        showDialog = false
        editingItem = null
        Toast.makeText(context, "Foto galeri berhasil disimpan!", Toast.LENGTH_SHORT).show()
      }
    )
  }

  Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
      Text("Kelola Galeri Foto", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
      Button(onClick = { showDialog = true }, shape = RoundedCornerShape(8.dp)) {
        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text("Tambah Galeri")
      }
    }
    Spacer(modifier = Modifier.height(14.dp))
    list.forEach { item ->
      Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp)
      ) {
        Row(modifier = Modifier.fillMaxWidth().padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
          SchoolImage(model = item.imagePath, contentDescription = item.title, modifier = Modifier.size(60.dp).clip(RoundedCornerShape(8.dp)), contentScale = ContentScale.Crop)
          Spacer(modifier = Modifier.width(12.dp))
          Column(modifier = Modifier.weight(1f)) {
            Text(item.title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
            Text(item.category, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
            if (item.description.isNotBlank()) Text(item.description, style = MaterialTheme.typography.bodySmall, maxLines = 1)
          }
          IconButton(onClick = { editingItem = item }) { Icon(Icons.Default.Edit, contentDescription = "Edit", tint = MaterialTheme.colorScheme.primary) }
          IconButton(onClick = { viewModel.deleteGallery(item) }) { Icon(Icons.Default.Delete, contentDescription = "Hapus", tint = MaterialTheme.colorScheme.error) }
        }
      }
    }
  }
}

@Composable
fun GalleryFormDialog(itemToEdit: GalleryEntity?, onDismiss: () -> Unit, onSave: (GalleryEntity) -> Unit) {
  var title by remember { mutableStateOf(itemToEdit?.title ?: "") }
  var category by remember { mutableStateOf(itemToEdit?.category ?: "Kegiatan sekolah") }
  var description by remember { mutableStateOf(itemToEdit?.description ?: "") }
  var imagePath by remember { mutableStateOf(itemToEdit?.imagePath ?: "") }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text(if (itemToEdit != null) "Edit Foto Galeri" else "Tambah Foto Galeri", fontWeight = FontWeight.Bold) },
    text = {
      Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {
        OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Judul Foto *") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("Kategori (Kegiatan sekolah, Pembelajaran, dll)") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        ImagePickerButton(label = "Unggah Foto Galeri (JPG, JPEG, PNG)", currentImagePath = imagePath, onImageSaved = { imagePath = it }, onImageRemoved = { imagePath = "" }, prefix = "gallery")
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Deskripsi / Keterangan Foto") }, modifier = Modifier.fillMaxWidth().height(80.dp))
      }
    },
    confirmButton = { Button(onClick = { if (title.isNotBlank()) onSave(itemToEdit?.copy(title = title, category = category, description = description, imagePath = imagePath) ?: GalleryEntity(title = title, category = category, description = description, imagePath = imagePath)) }) { Text("Simpan") } },
    dismissButton = { OutlinedButton(onClick = onDismiss) { Text("Batal") } }
  )
}

// ================= EKSTRAKURIKULER =================
@Composable
fun AdminExtracurricularSection(viewModel: SchoolViewModel) {
  val context = LocalContext.current
  val list by viewModel.extracurricularList.collectAsState()
  var showDialog by remember { mutableStateOf(false) }
  var editingItem by remember { mutableStateOf<ExtracurricularEntity?>(null) }

  if (showDialog || editingItem != null) {
    ExtracurricularFormDialog(
      itemToEdit = editingItem,
      onDismiss = { showDialog = false; editingItem = null },
      onSave = { entity ->
        viewModel.saveExtracurricular(entity)
        showDialog = false
        editingItem = null
        Toast.makeText(context, "Ekstrakurikuler berhasil disimpan!", Toast.LENGTH_SHORT).show()
      }
    )
  }

  Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
      Text("Kelola Ekstrakurikuler", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
      Button(onClick = { showDialog = true }, shape = RoundedCornerShape(8.dp)) {
        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text("Tambah Ekstrakurikuler")
      }
    }
    Spacer(modifier = Modifier.height(14.dp))
    list.forEach { item ->
      Card(modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), shape = RoundedCornerShape(12.dp)) {
        Row(modifier = Modifier.fillMaxWidth().padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
          SchoolImage(model = item.imagePath, contentDescription = item.name, modifier = Modifier.size(60.dp).clip(RoundedCornerShape(8.dp)), contentScale = ContentScale.Crop)
          Spacer(modifier = Modifier.width(12.dp))
          Column(modifier = Modifier.weight(1f)) {
            Text(item.name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
            Text("Pembina: ${item.coach} • ${item.schedule}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
            Text(item.description, style = MaterialTheme.typography.bodySmall, maxLines = 1)
          }
          IconButton(onClick = { editingItem = item }) { Icon(Icons.Default.Edit, contentDescription = "Edit", tint = MaterialTheme.colorScheme.primary) }
          IconButton(onClick = { viewModel.deleteExtracurricular(item) }) { Icon(Icons.Default.Delete, contentDescription = "Hapus", tint = MaterialTheme.colorScheme.error) }
        }
      }
    }
  }
}

@Composable
fun ExtracurricularFormDialog(itemToEdit: ExtracurricularEntity?, onDismiss: () -> Unit, onSave: (ExtracurricularEntity) -> Unit) {
  var name by remember { mutableStateOf(itemToEdit?.name ?: "") }
  var coach by remember { mutableStateOf(itemToEdit?.coach ?: "") }
  var schedule by remember { mutableStateOf(itemToEdit?.schedule ?: "") }
  var description by remember { mutableStateOf(itemToEdit?.description ?: "") }
  var imagePath by remember { mutableStateOf(itemToEdit?.imagePath ?: "") }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text(if (itemToEdit != null) "Edit Ekstrakurikuler" else "Tambah Ekstrakurikuler", fontWeight = FontWeight.Bold) },
    text = {
      Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nama Ekstrakurikuler *") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(value = coach, onValueChange = { coach = it }, label = { Text("Nama Pembina") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(value = schedule, onValueChange = { schedule = it }, label = { Text("Jadwal Latihan (Contoh: Jumat 14.00)") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        // Explicitly supports image upload from HP
        ImagePickerButton(label = "Unggah Foto Ekstrakurikuler (JPG, JPEG, PNG)", currentImagePath = imagePath, onImageSaved = { imagePath = it }, onImageRemoved = { imagePath = "" }, prefix = "ekskul")
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Deskripsi Kegiatan") }, modifier = Modifier.fillMaxWidth().height(80.dp))
      }
    },
    confirmButton = { Button(onClick = { if (name.isNotBlank()) onSave(itemToEdit?.copy(name = name, coach = coach, schedule = schedule, description = description, imagePath = imagePath) ?: ExtracurricularEntity(name = name, coach = coach, schedule = schedule, description = description, imagePath = imagePath)) }) { Text("Simpan") } },
    dismissButton = { OutlinedButton(onClick = onDismiss) { Text("Batal") } }
  )
}

// ================= PROGRAM UNGGULAN =================
@Composable
fun AdminProgramsSection(viewModel: SchoolViewModel) {
  val context = LocalContext.current
  val list by viewModel.featuredProgramsList.collectAsState()
  var showDialog by remember { mutableStateOf(false) }
  var editingItem by remember { mutableStateOf<FeaturedProgramEntity?>(null) }

  if (showDialog || editingItem != null) {
    ProgramFormDialog(
      itemToEdit = editingItem,
      onDismiss = { showDialog = false; editingItem = null },
      onSave = { entity ->
        viewModel.saveFeaturedProgram(entity)
        showDialog = false
        editingItem = null
        Toast.makeText(context, "Program unggulan berhasil disimpan!", Toast.LENGTH_SHORT).show()
      }
    )
  }

  Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
      Text("Kelola Program Unggulan", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
      Button(onClick = { showDialog = true }, shape = RoundedCornerShape(8.dp)) {
        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text("Tambah Program")
      }
    }
    Spacer(modifier = Modifier.height(14.dp))
    list.forEach { item ->
      Card(modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), shape = RoundedCornerShape(12.dp)) {
        Row(modifier = Modifier.fillMaxWidth().padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
          SchoolImage(model = item.imagePath, contentDescription = item.name, modifier = Modifier.size(60.dp).clip(RoundedCornerShape(8.dp)), contentScale = ContentScale.Crop)
          Spacer(modifier = Modifier.width(12.dp))
          Column(modifier = Modifier.weight(1f)) {
            Text(item.name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
            Text(item.targetLevel, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
            Text(item.description, style = MaterialTheme.typography.bodySmall, maxLines = 1)
          }
          IconButton(onClick = { editingItem = item }) { Icon(Icons.Default.Edit, contentDescription = "Edit", tint = MaterialTheme.colorScheme.primary) }
          IconButton(onClick = { viewModel.deleteFeaturedProgram(item) }) { Icon(Icons.Default.Delete, contentDescription = "Hapus", tint = MaterialTheme.colorScheme.error) }
        }
      }
    }
  }
}

@Composable
fun ProgramFormDialog(itemToEdit: FeaturedProgramEntity?, onDismiss: () -> Unit, onSave: (FeaturedProgramEntity) -> Unit) {
  var name by remember { mutableStateOf(itemToEdit?.name ?: "") }
  var description by remember { mutableStateOf(itemToEdit?.description ?: "") }
  var targetLevel by remember { mutableStateOf(itemToEdit?.targetLevel ?: "Semua Kelas") }
  var imagePath by remember { mutableStateOf(itemToEdit?.imagePath ?: "") }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text(if (itemToEdit != null) "Edit Program Unggulan" else "Tambah Program Unggulan", fontWeight = FontWeight.Bold) },
    text = {
      Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nama Program *") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(value = targetLevel, onValueChange = { targetLevel = it }, label = { Text("Sasaran (Contoh: Kelas 1-6)") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        ImagePickerButton(label = "Unggah Foto Program (JPG, JPEG, PNG)", currentImagePath = imagePath, onImageSaved = { imagePath = it }, onImageRemoved = { imagePath = "" }, prefix = "program")
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Deskripsi Program") }, modifier = Modifier.fillMaxWidth().height(90.dp))
      }
    },
    confirmButton = { Button(onClick = { if (name.isNotBlank()) onSave(itemToEdit?.copy(name = name, description = description, targetLevel = targetLevel, imagePath = imagePath) ?: FeaturedProgramEntity(name = name, description = description, targetLevel = targetLevel, imagePath = imagePath)) }) { Text("Simpan") } },
    dismissButton = { OutlinedButton(onClick = onDismiss) { Text("Batal") } }
  )
}

// ================= DATA GURU =================
@Composable
fun AdminTeachersSection(viewModel: SchoolViewModel) {
  val context = LocalContext.current
  val list by viewModel.teachersList.collectAsState()
  var showDialog by remember { mutableStateOf(false) }
  var editingItem by remember { mutableStateOf<TeacherEntity?>(null) }

  if (showDialog || editingItem != null) {
    TeacherFormDialog(
      itemToEdit = editingItem,
      onDismiss = { showDialog = false; editingItem = null },
      onSave = { entity ->
        viewModel.saveTeacher(entity)
        showDialog = false
        editingItem = null
        Toast.makeText(context, "Data guru berhasil disimpan!", Toast.LENGTH_SHORT).show()
      }
    )
  }

  Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
      Text("Kelola Data Guru", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
      Button(onClick = { showDialog = true }, shape = RoundedCornerShape(8.dp)) {
        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text("Tambah Guru")
      }
    }
    Spacer(modifier = Modifier.height(14.dp))
    list.forEach { item ->
      Card(modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), shape = RoundedCornerShape(12.dp)) {
        Row(modifier = Modifier.fillMaxWidth().padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
          SchoolImage(model = item.imagePath, contentDescription = item.name, modifier = Modifier.size(54.dp).clip(CircleShape), contentScale = ContentScale.Crop)
          Spacer(modifier = Modifier.width(12.dp))
          Column(modifier = Modifier.weight(1f)) {
            Text(item.name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
            Text(item.role, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
            Text("NIP: ${item.nip} • ${item.education}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
          }
          IconButton(onClick = { editingItem = item }) { Icon(Icons.Default.Edit, contentDescription = "Edit", tint = MaterialTheme.colorScheme.primary) }
          IconButton(onClick = { viewModel.deleteTeacher(item) }) { Icon(Icons.Default.Delete, contentDescription = "Hapus", tint = MaterialTheme.colorScheme.error) }
        }
      }
    }
  }
}

@Composable
fun TeacherFormDialog(itemToEdit: TeacherEntity?, onDismiss: () -> Unit, onSave: (TeacherEntity) -> Unit) {
  var name by remember { mutableStateOf(itemToEdit?.name ?: "") }
  var nip by remember { mutableStateOf(itemToEdit?.nip ?: "") }
  var role by remember { mutableStateOf(itemToEdit?.role ?: "") }
  var education by remember { mutableStateOf(itemToEdit?.education ?: "") }
  var imagePath by remember { mutableStateOf(itemToEdit?.imagePath ?: "") }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text(if (itemToEdit != null) "Edit Data Guru" else "Tambah Data Guru", fontWeight = FontWeight.Bold) },
    text = {
      Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nama Lengkap & Gelar *") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(value = nip, onValueChange = { nip = it }, label = { Text("NIP / NUPTK") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(value = role, onValueChange = { role = it }, label = { Text("Jabatan / Mata Pelajaran") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(value = education, onValueChange = { education = it }, label = { Text("Pendidikan Terakhir") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        ImagePickerButton(label = "Unggah Foto Profil Guru (JPG, JPEG, PNG)", currentImagePath = imagePath, onImageSaved = { imagePath = it }, onImageRemoved = { imagePath = "" }, prefix = "teacher")
      }
    },
    confirmButton = { Button(onClick = { if (name.isNotBlank()) onSave(itemToEdit?.copy(name = name, nip = nip, role = role, education = education, imagePath = imagePath) ?: TeacherEntity(name = name, nip = nip, role = role, education = education, imagePath = imagePath)) }) { Text("Simpan") } },
    dismissButton = { OutlinedButton(onClick = onDismiss) { Text("Batal") } }
  )
}

// ================= STRUKTUR ORGANISASI =================
@Composable
fun AdminOrganizationSection(viewModel: SchoolViewModel) {
  val context = LocalContext.current
  val profile by viewModel.schoolProfile.collectAsState()
  var orgText by remember(profile.organizationStructure) { mutableStateOf(profile.organizationStructure) }

  Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
    Text("Struktur Organisasi Madrasah", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
    Text("Format: Jabatan: Nama Pejabat (Satu baris per jabatan)", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    Spacer(modifier = Modifier.height(14.dp))

    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), shape = RoundedCornerShape(12.dp)) {
      Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
          value = orgText,
          onValueChange = { orgText = it },
          label = { Text("Daftar Struktur Organisasi") },
          modifier = Modifier.fillMaxWidth().height(260.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
          onClick = {
            viewModel.updateSchoolProfile(profile.copy(organizationStructure = orgText))
            Toast.makeText(context, "Struktur organisasi berhasil diperbarui!", Toast.LENGTH_SHORT).show()
          },
          modifier = Modifier.fillMaxWidth()
        ) {
          Text("Simpan Perubahan Struktur Organisasi")
        }
      }
    }
  }
}

// ================= TESTIMONI (MENU DI DASBOR BAWAH) =================
@Composable
fun AdminTestimonialsSection(viewModel: SchoolViewModel) {
  val context = LocalContext.current
  val list by viewModel.testimonialsList.collectAsState()
  var showDialog by remember { mutableStateOf(false) }
  var editingItem by remember { mutableStateOf<TestimonialEntity?>(null) }

  if (showDialog || editingItem != null) {
    TestimonialFormDialog(
      itemToEdit = editingItem,
      onDismiss = { showDialog = false; editingItem = null },
      onSave = { entity ->
        viewModel.saveTestimonial(entity)
        showDialog = false
        editingItem = null
        Toast.makeText(context, "Testimoni berhasil disimpan!", Toast.LENGTH_SHORT).show()
      }
    )
  }

  Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
      Text("Kelola Testimoni", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
      Button(onClick = { showDialog = true }, shape = RoundedCornerShape(8.dp)) {
        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text("Tambah Testimoni")
      }
    }
    Spacer(modifier = Modifier.height(14.dp))
    list.forEach { item ->
      Card(modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), shape = RoundedCornerShape(12.dp)) {
        Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
          Column(modifier = Modifier.weight(1f)) {
            Text(item.name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
            Text(item.role, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
            Text("\"${item.content}\"", style = MaterialTheme.typography.bodySmall, maxLines = 2)
          }
          IconButton(onClick = { editingItem = item }) { Icon(Icons.Default.Edit, contentDescription = "Edit", tint = MaterialTheme.colorScheme.primary) }
          IconButton(onClick = { viewModel.deleteTestimonial(item) }) { Icon(Icons.Default.Delete, contentDescription = "Hapus", tint = MaterialTheme.colorScheme.error) }
        }
      }
    }
  }
}

@Composable
fun TestimonialFormDialog(itemToEdit: TestimonialEntity?, onDismiss: () -> Unit, onSave: (TestimonialEntity) -> Unit) {
  var name by remember { mutableStateOf(itemToEdit?.name ?: "") }
  var role by remember { mutableStateOf(itemToEdit?.role ?: "Alumni") }
  var content by remember { mutableStateOf(itemToEdit?.content ?: "") }
  var imagePath by remember { mutableStateOf(itemToEdit?.imagePath ?: "") }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text(if (itemToEdit != null) "Edit Testimoni" else "Tambah Testimoni", fontWeight = FontWeight.Bold) },
    text = {
      Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nama Pengisi Testimoni *") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(value = role, onValueChange = { role = it }, label = { Text("Peran / Status (Wali Murid / Alumni)") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        ImagePickerButton(label = "Unggah Foto (Opsional, JPG/PNG)", currentImagePath = imagePath, onImageSaved = { imagePath = it }, onImageRemoved = { imagePath = "" }, prefix = "testi")
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = content, onValueChange = { content = it }, label = { Text("Isi Pesan Testimoni *") }, modifier = Modifier.fillMaxWidth().height(100.dp))
      }
    },
    confirmButton = { Button(onClick = { if (name.isNotBlank() && content.isNotBlank()) onSave(itemToEdit?.copy(name = name, role = role, content = content, imagePath = imagePath) ?: TestimonialEntity(name = name, role = role, content = content, imagePath = imagePath)) }) { Text("Simpan") } },
    dismissButton = { OutlinedButton(onClick = onDismiss) { Text("Batal") } }
  )
}

// ================= PENGATURAN SEKOLAH =================
@Composable
fun AdminSettingsSection(viewModel: SchoolViewModel) {
  val context = LocalContext.current
  val profile by viewModel.schoolProfile.collectAsState()

  var schoolName by remember(profile.schoolName) { mutableStateOf(profile.schoolName) }
  var slogan by remember(profile.slogan) { mutableStateOf(profile.slogan) }
  var address by remember(profile.address) { mutableStateOf(profile.address) }
  var phone by remember(profile.phone) { mutableStateOf(profile.phone) }
  var email by remember(profile.email) { mutableStateOf(profile.email) }
  var whatsapp by remember(profile.whatsapp) { mutableStateOf(profile.whatsapp) }
  var establishedYear by remember(profile.establishedYear) { mutableStateOf(profile.establishedYear) }
  var accreditation by remember(profile.accreditation) { mutableStateOf(profile.accreditation) }
  var logoUrl by remember(profile.logoUrlOrPath) { mutableStateOf(profile.logoUrlOrPath) }

  // Social media links
  var facebookUrl by remember(profile.facebookUrl) { mutableStateOf(profile.facebookUrl) }
  var instagramUrl by remember(profile.instagramUrl) { mutableStateOf(profile.instagramUrl) }
  var youtubeUrl by remember(profile.youtubeUrl) { mutableStateOf(profile.youtubeUrl) }
  var tiktokUrl by remember(profile.tiktokUrl) { mutableStateOf(profile.tiktokUrl) }

  Column(modifier = Modifier.fillMaxWidth().padding(16.dp).verticalScroll(rememberScrollState())) {
    Text("Pengaturan Informasi Madrasah", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
    Text("Perubahan di sini langsung terupdate di halaman depan aplikasi", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    Spacer(modifier = Modifier.height(14.dp))

    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), shape = RoundedCornerShape(12.dp)) {
      Column(modifier = Modifier.padding(16.dp)) {
        // UNGGAH LOGO RESMI DARI HP
        Text("LOGO RESMI SEKOLAH", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(6.dp))
        ImagePickerButton(
          label = "Unggah Logo dari HP (JPG, JPEG, PNG)",
          currentImagePath = logoUrl,
          onImageSaved = { logoUrl = it },
          onImageRemoved = { logoUrl = InitialData.getInitialProfile().logoUrlOrPath },
          prefix = "logo_resmi"
        )

        Spacer(modifier = Modifier.height(14.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(14.dp))

        Text("IDENTITAS & STATISTIK SEKOLAH", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(value = schoolName, onValueChange = { schoolName = it }, label = { Text("Nama Sekolah") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(value = slogan, onValueChange = { slogan = it }, label = { Text("Slogan Sekolah") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(6.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
          // TAHUN BERDIRI DAPAT DI UBAH DI MENU PENGATURAN
          OutlinedTextField(value = establishedYear, onValueChange = { establishedYear = it }, label = { Text("Tahun Berdiri *") }, modifier = Modifier.weight(1f))
          // AKREDITASI
          OutlinedTextField(value = accreditation, onValueChange = { accreditation = it }, label = { Text("Akreditasi *") }, modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(14.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(14.dp))

        // LINK SOSIAL MEDIA
        Text("LINK SOSIAL MEDIA RESMI", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = facebookUrl, onValueChange = { facebookUrl = it }, label = { Text("Link Facebook") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(value = instagramUrl, onValueChange = { instagramUrl = it }, label = { Text("Link Instagram") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(value = youtubeUrl, onValueChange = { youtubeUrl = it }, label = { Text("Link YouTube") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(value = tiktokUrl, onValueChange = { tiktokUrl = it }, label = { Text("Link TikTok") }, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(14.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(14.dp))

        Text("KONTAK SEKOLAH", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = address, onValueChange = { address = it }, label = { Text("Alamat Sekolah") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(6.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
          OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Telepon") }, modifier = Modifier.weight(1f))
          OutlinedTextField(value = whatsapp, onValueChange = { whatsapp = it }, label = { Text("WhatsApp") }, modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email Resmi") }, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(20.dp))

        Button(
          onClick = {
            viewModel.updateSchoolProfile(
              profile.copy(
                schoolName = schoolName,
                slogan = slogan,
                address = address,
                phone = phone,
                email = email,
                whatsapp = whatsapp,
                establishedYear = establishedYear,
                accreditation = accreditation,
                logoUrlOrPath = logoUrl,
                facebookUrl = facebookUrl,
                instagramUrl = instagramUrl,
                youtubeUrl = youtubeUrl,
                tiktokUrl = tiktokUrl
              )
            )
            Toast.makeText(context, "Pengaturan sekolah berhasil diperbarui!", Toast.LENGTH_SHORT).show()
          },
          modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
          Text("Simpan Seluruh Pengaturan", fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}

// ================= PESAN DARI HALAMAN KONTAK =================
@Composable
fun AdminMessagesSection(viewModel: SchoolViewModel) {
  val list by viewModel.contactMessagesList.collectAsState()

  Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
    Text("Pesan Masuk dari Pengunjung", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
    Text("Total: ${list.size} Pesan Masuk", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    Spacer(modifier = Modifier.height(14.dp))

    if (list.isEmpty()) {
      Box(modifier = Modifier.fillMaxWidth().padding(40.dp), contentAlignment = Alignment.Center) {
        Text("Belum ada pesan masuk.")
      }
    } else {
      list.forEach { msg ->
        Card(
          modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
          colors = CardDefaults.cardColors(containerColor = if (!msg.isRead) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f) else MaterialTheme.colorScheme.surface),
          shape = RoundedCornerShape(12.dp)
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
              Text(msg.name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
              Text(msg.date, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
            }
            Text("Subjek: ${msg.subject}", fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
            Text("HP/WA: ${msg.phone} • Email: ${msg.email}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(6.dp))
            Text(msg.message, style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
              if (!msg.isRead) {
                OutlinedButton(onClick = { viewModel.markMessageRead(msg) }, shape = RoundedCornerShape(6.dp), modifier = Modifier.height(32.dp)) {
                  Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(14.dp))
                  Spacer(modifier = Modifier.width(4.dp))
                  Text("Tandai Dibaca", style = MaterialTheme.typography.labelSmall)
                }
                Spacer(modifier = Modifier.width(8.dp))
              }
              IconButton(onClick = { viewModel.deleteContactMessage(msg) }, modifier = Modifier.size(32.dp)) {
                Icon(Icons.Default.Delete, contentDescription = "Hapus", tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(16.dp))
              }
            }
          }
        }
      }
    }
  }
}
