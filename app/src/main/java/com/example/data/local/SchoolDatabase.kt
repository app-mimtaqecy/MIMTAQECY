package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.model.AgendaEntity
import com.example.data.model.AnnouncementEntity
import com.example.data.model.ContactMessageEntity
import com.example.data.model.ExtracurricularEntity
import com.example.data.model.FeaturedProgramEntity
import com.example.data.model.GalleryEntity
import com.example.data.model.NewsEntity
import com.example.data.model.PpdbEntity
import com.example.data.model.SchoolProfileEntity
import com.example.data.model.StudentEntity
import com.example.data.model.TeacherEntity
import com.example.data.model.TestimonialEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
  entities = [
    SchoolProfileEntity::class,
    NewsEntity::class,
    GalleryEntity::class,
    ExtracurricularEntity::class,
    FeaturedProgramEntity::class,
    AgendaEntity::class,
    AnnouncementEntity::class,
    TeacherEntity::class,
    StudentEntity::class,
    PpdbEntity::class,
    ContactMessageEntity::class,
    TestimonialEntity::class
  ],
  version = 1,
  exportSchema = false
)
abstract class SchoolDatabase : RoomDatabase() {

  abstract fun schoolDao(): SchoolDao

  companion object {
    @Volatile
    private var INSTANCE: SchoolDatabase? = null

    fun getDatabase(context: Context): SchoolDatabase {
      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          SchoolDatabase::class.java,
          "mim_taqecy_school_database"
        )
          .addCallback(SchoolDatabaseCallback(context.applicationContext))
          .fallbackToDestructiveMigration()
          .build()
        INSTANCE = instance
        instance
      }
    }
  }

  private class SchoolDatabaseCallback(
    private val context: Context
  ) : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
      super.onCreate(db)
      CoroutineScope(Dispatchers.IO).launch {
        populateInitialData(getDatabase(context))
      }
    }

    private suspend fun populateInitialData(database: SchoolDatabase) {
      val dao = database.schoolDao()
      dao.upsertSchoolProfile(InitialData.getInitialProfile())
      InitialData.getInitialNews().forEach { dao.insertNews(it) }
      InitialData.getInitialGallery().forEach { dao.insertGallery(it) }
      InitialData.getInitialExtracurricular().forEach { dao.insertExtracurricular(it) }
      InitialData.getInitialFeaturedPrograms().forEach { dao.insertFeaturedProgram(it) }
      InitialData.getInitialAgendas().forEach { dao.insertAgenda(it) }
      InitialData.getInitialAnnouncements().forEach { dao.insertAnnouncement(it) }
      InitialData.getInitialTeachers().forEach { dao.insertTeacher(it) }
      dao.insertStudents(InitialData.getInitialStudents())
      InitialData.getInitialPpdb().forEach { dao.insertPpdb(it) }
      InitialData.getInitialMessages().forEach { dao.insertContactMessage(it) }
      InitialData.getInitialTestimonials().forEach { dao.insertTestimonial(it) }
    }
  }
}
