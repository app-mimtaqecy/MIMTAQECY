package com.example.data.local

import android.content.Context
import android.net.Uri
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

object ImageStorageHelper {
  private const val TAG = "ImageStorageHelper"

  /**
   * Copies an image from a selected content Uri to internal storage.
   * Returns the absolute path of the permanently saved file.
   */
  fun saveImageToInternalStorage(context: Context, uri: Uri, prefix: String = "img"): String? {
    return try {
      val uploadsDir = File(context.filesDir, "uploads")
      if (!uploadsDir.exists()) {
        uploadsDir.mkdirs()
      }

      val mimeType = context.contentResolver.getType(uri) ?: "image/jpeg"
      val extension = when {
        mimeType.contains("png", ignoreCase = true) -> ".png"
        mimeType.contains("webp", ignoreCase = true) -> ".webp"
        else -> ".jpg"
      }

      val destFile = File(uploadsDir, "${prefix}_${System.currentTimeMillis()}$extension")

      val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
      if (inputStream == null) {
        Log.e(TAG, "Cannot open input stream for $uri")
        return null
      }

      inputStream.use { input ->
        FileOutputStream(destFile).use { output ->
          input.copyTo(output)
        }
      }

      Log.d(TAG, "Saved image to: ${destFile.absolutePath}")
      destFile.absolutePath
    } catch (e: Exception) {
      Log.e(TAG, "Failed to save image from URI: $uri", e)
      null
    }
  }
}
