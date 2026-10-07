package com.example.ui.components

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.local.ImageStorageHelper

@Composable
fun ImagePickerButton(
  label: String,
  currentImagePath: String?,
  onImageSaved: (String) -> Unit,
  onImageRemoved: () -> Unit,
  modifier: Modifier = Modifier,
  prefix: String = "upload",
  isDocument: Boolean = false
) {
  val context = LocalContext.current

  // PhotoPicker launcher supporting JPG, JPEG, PNG
  val photoPickerLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.PickVisualMedia()
  ) { uri: Uri? ->
    if (uri != null) {
      val savedPath = ImageStorageHelper.saveImageToInternalStorage(
        context = context,
        uri = uri,
        prefix = prefix
      )
      if (savedPath != null) {
        onImageSaved(savedPath)
        Toast.makeText(context, "Gambar berhasil diunggah!", Toast.LENGTH_SHORT).show()
      } else {
        Toast.makeText(context, "Gagal memproses gambar", Toast.LENGTH_SHORT).show()
      }
    }
  }

  Column(modifier = modifier.fillMaxWidth()) {
    Text(
      text = label,
      style = MaterialTheme.typography.bodyMedium,
      fontWeight = FontWeight.SemiBold,
      color = MaterialTheme.colorScheme.onSurface
    )
    Spacer(modifier = Modifier.height(6.dp))

    if (!currentImagePath.isNullOrBlank()) {
      Surface(
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          SchoolImage(
            model = currentImagePath,
            contentDescription = label,
            modifier = Modifier
              .size(64.dp)
              .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
          )
          Spacer(modifier = Modifier.width(12.dp))
          Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = Color(0xFF16A34A),
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = if (isDocument) "Dokumen Tersimpan" else "Foto Terpilih",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF16A34A)
              )
            }
            Text(
              text = if (currentImagePath.startsWith("/")) "Tersimpan di Penyimpanan HP" else "Gambar URL / Web",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }

          IconButton(onClick = {
            photoPickerLauncher.launch(
              PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
          }) {
            Icon(
              imageVector = Icons.Default.AddPhotoAlternate,
              contentDescription = "Ganti Foto",
              tint = MaterialTheme.colorScheme.primary
            )
          }

          IconButton(onClick = onImageRemoved) {
            Icon(
              imageVector = Icons.Default.Delete,
              contentDescription = "Hapus",
              tint = MaterialTheme.colorScheme.error
            )
          }
        }
      }
    } else {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(10.dp))
          .border(
            width = 1.5.dp,
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
            shape = RoundedCornerShape(10.dp)
          )
          .clickable {
            photoPickerLauncher.launch(
              PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
          }
          .padding(vertical = 18.dp, horizontal = 16.dp),
        contentAlignment = Alignment.Center
      ) {
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center
        ) {
          Icon(
            imageVector = Icons.Default.FileUpload,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(32.dp)
          )
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = "Unggah Gambar dari HP",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
          )
          Text(
            text = "Format: JPG, JPEG, PNG (Maks 10 MB)",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }
    }
  }
}
