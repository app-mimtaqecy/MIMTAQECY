package com.example

import android.app.Application
import android.graphics.Bitmap
import com.google.firebase.FirebaseApp
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import com.example.data.local.SchoolDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SchoolApplication : Application(), ImageLoaderFactory {
  val database: SchoolDatabase by lazy {
    SchoolDatabase.getDatabase(this)
  }

  override fun onCreate() {
    super.onCreate()
    FirebaseApp.initializeApp(this)
    // Pre-warm database initialization in background so initial data is seeded immediately
    CoroutineScope(Dispatchers.IO).launch {
      database.schoolDao().getSchoolProfileSync()
    }
  }

  override fun newImageLoader(): ImageLoader {
    return ImageLoader.Builder(this)
      .bitmapConfig(Bitmap.Config.ARGB_8888)
      .allowHardware(false) // Prevents emulator EGL / Unknown dataspace buffer warnings
      .memoryCache {
        MemoryCache.Builder(this)
          .maxSizePercent(0.25)
          .build()
      }
      .diskCache {
        DiskCache.Builder()
          .directory(cacheDir.resolve("image_cache"))
          .maxSizePercent(0.02)
          .build()
      }
      .crossfade(true)
      .build()
  }
}
