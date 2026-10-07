package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
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
import kotlinx.coroutines.flow.Flow

@Dao
interface SchoolDao {

  // School Profile
  @Query("SELECT * FROM school_profile WHERE id = 1 LIMIT 1")
  fun getSchoolProfile(): Flow<SchoolProfileEntity?>

  @Query("SELECT * FROM school_profile WHERE id = 1 LIMIT 1")
  suspend fun getSchoolProfileSync(): SchoolProfileEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun upsertSchoolProfile(profile: SchoolProfileEntity)

  // News
  @Query("SELECT * FROM news ORDER BY id DESC")
  fun getAllNews(): Flow<List<NewsEntity>>

  @Query("SELECT * FROM news WHERE id = :id LIMIT 1")
  suspend fun getNewsById(id: Long): NewsEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertNews(news: NewsEntity): Long

  @Update
  suspend fun updateNews(news: NewsEntity)

  @Delete
  suspend fun deleteNews(news: NewsEntity)

  // Gallery
  @Query("SELECT * FROM gallery ORDER BY id DESC")
  fun getAllGallery(): Flow<List<GalleryEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertGallery(item: GalleryEntity): Long

  @Update
  suspend fun updateGallery(item: GalleryEntity)

  @Delete
  suspend fun deleteGallery(item: GalleryEntity)

  // Extracurricular
  @Query("SELECT * FROM extracurricular ORDER BY id ASC")
  fun getAllExtracurricular(): Flow<List<ExtracurricularEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertExtracurricular(item: ExtracurricularEntity): Long

  @Update
  suspend fun updateExtracurricular(item: ExtracurricularEntity)

  @Delete
  suspend fun deleteExtracurricular(item: ExtracurricularEntity)

  // Featured Programs
  @Query("SELECT * FROM featured_programs ORDER BY id ASC")
  fun getAllFeaturedPrograms(): Flow<List<FeaturedProgramEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertFeaturedProgram(item: FeaturedProgramEntity): Long

  @Update
  suspend fun updateFeaturedProgram(item: FeaturedProgramEntity)

  @Delete
  suspend fun deleteFeaturedProgram(item: FeaturedProgramEntity)

  // Agendas
  @Query("SELECT * FROM agendas ORDER BY id DESC")
  fun getAllAgendas(): Flow<List<AgendaEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAgenda(item: AgendaEntity): Long

  @Update
  suspend fun updateAgenda(item: AgendaEntity)

  @Delete
  suspend fun deleteAgenda(item: AgendaEntity)

  // Announcements
  @Query("SELECT * FROM announcements ORDER BY id DESC")
  fun getAllAnnouncements(): Flow<List<AnnouncementEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAnnouncement(item: AnnouncementEntity): Long

  @Update
  suspend fun updateAnnouncement(item: AnnouncementEntity)

  @Delete
  suspend fun deleteAnnouncement(item: AnnouncementEntity)

  // Teachers
  @Query("SELECT * FROM teachers ORDER BY id ASC")
  fun getAllTeachers(): Flow<List<TeacherEntity>>

  @Query("SELECT COUNT(*) FROM teachers")
  fun getTeacherCount(): Flow<Int>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertTeacher(item: TeacherEntity): Long

  @Update
  suspend fun updateTeacher(item: TeacherEntity)

  @Delete
  suspend fun deleteTeacher(item: TeacherEntity)

  // Students
  @Query("SELECT * FROM students ORDER BY classLevel ASC, rombel ASC, name ASC")
  fun getAllStudents(): Flow<List<StudentEntity>>

  @Query("SELECT COUNT(*) FROM students")
  fun getStudentCount(): Flow<Int>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertStudent(item: StudentEntity): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertStudents(items: List<StudentEntity>)

  @Update
  suspend fun updateStudent(item: StudentEntity)

  @Delete
  suspend fun deleteStudent(item: StudentEntity)

  // PPDB
  @Query("SELECT * FROM ppdb_applications ORDER BY id DESC")
  fun getAllPpdb(): Flow<List<PpdbEntity>>

  @Query("SELECT COUNT(*) FROM ppdb_applications")
  fun getPpdbCount(): Flow<Int>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertPpdb(item: PpdbEntity): Long

  @Update
  suspend fun updatePpdb(item: PpdbEntity)

  @Delete
  suspend fun deletePpdb(item: PpdbEntity)

  // Contact Messages
  @Query("SELECT * FROM contact_messages ORDER BY id DESC")
  fun getAllContactMessages(): Flow<List<ContactMessageEntity>>

  @Query("SELECT COUNT(*) FROM contact_messages WHERE isRead = 0")
  fun getUnreadMessageCount(): Flow<Int>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertContactMessage(item: ContactMessageEntity): Long

  @Update
  suspend fun updateContactMessage(item: ContactMessageEntity)

  @Delete
  suspend fun deleteContactMessage(item: ContactMessageEntity)

  // Testimonials
  @Query("SELECT * FROM testimonials ORDER BY id DESC")
  fun getAllTestimonials(): Flow<List<TestimonialEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertTestimonial(item: TestimonialEntity): Long

  @Update
  suspend fun updateTestimonial(item: TestimonialEntity)

  @Delete
  suspend fun deleteTestimonial(item: TestimonialEntity)
}
