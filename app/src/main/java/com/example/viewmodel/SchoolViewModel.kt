package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.InitialData
import com.example.data.local.SchoolDatabase
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

enum class AppScreen {
  HOME,
  PROFILE,
  ACADEMIC,
  NEWS,
  GALLERY,
  PPDB,
  CONTACT,
  ADMIN_LOGIN,
  ADMIN_DASHBOARD
}

enum class AdminTab {
  OVERVIEW,
  NEWS,
  GALLERY,
  EXTRACURRICULAR,
  PROGRAMS,
  AGENDAS,
  ANNOUNCEMENTS,
  TEACHERS,
  STUDENTS,
  PPDB,
  ORGANIZATION,
  TESTIMONIALS,
  MESSAGES,
  SETTINGS
}

class SchoolViewModel(application: Application) : AndroidViewModel(application) {
  private val dao = SchoolDatabase.getDatabase(application).schoolDao()

  // Navigation State
  private val _currentScreen = MutableStateFlow(AppScreen.HOME)
  val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

  // Admin Tab State
  private val _adminTab = MutableStateFlow(AdminTab.OVERVIEW)
  val adminTab: StateFlow<AdminTab> = _adminTab.asStateFlow()

  // Selected item for detail/view dialogs
  private val _selectedNews = MutableStateFlow<NewsEntity?>(null)
  val selectedNews: StateFlow<NewsEntity?> = _selectedNews.asStateFlow()

  private val _lightboxGallery = MutableStateFlow<GalleryEntity?>(null)
  val lightboxGallery: StateFlow<GalleryEntity?> = _lightboxGallery.asStateFlow()

  // Dark Mode
  private val _isDarkMode = MutableStateFlow(false)
  val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

  // Admin Auth State
  private val _isAdminLoggedIn = MutableStateFlow(false)
  val isAdminLoggedIn: StateFlow<Boolean> = _isAdminLoggedIn.asStateFlow()

  // Data Flows
  val schoolProfile: StateFlow<SchoolProfileEntity> = dao.getSchoolProfile()
    .map { it ?: InitialData.getInitialProfile() }
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5000),
      initialValue = InitialData.getInitialProfile()
    )

  val newsList: StateFlow<List<NewsEntity>> = dao.getAllNews()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val galleryList: StateFlow<List<GalleryEntity>> = dao.getAllGallery()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val extracurricularList: StateFlow<List<ExtracurricularEntity>> = dao.getAllExtracurricular()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val featuredProgramsList: StateFlow<List<FeaturedProgramEntity>> = dao.getAllFeaturedPrograms()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val agendatList: StateFlow<List<AgendaEntity>> = dao.getAllAgendas()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val announcementsList: StateFlow<List<AnnouncementEntity>> = dao.getAllAnnouncements()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val teachersList: StateFlow<List<TeacherEntity>> = dao.getAllTeachers()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val teacherCount: StateFlow<Int> = dao.getTeacherCount()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 8)

  val studentsList: StateFlow<List<StudentEntity>> = dao.getAllStudents()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val studentCount: StateFlow<Int> = dao.getStudentCount()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 24)

  val ppdbList: StateFlow<List<PpdbEntity>> = dao.getAllPpdb()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val ppdbCount: StateFlow<Int> = dao.getPpdbCount()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 2)

  val contactMessagesList: StateFlow<List<ContactMessageEntity>> = dao.getAllContactMessages()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val unreadMessageCount: StateFlow<Int> = dao.getUnreadMessageCount()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 1)

  val testimonialsList: StateFlow<List<TestimonialEntity>> = dao.getAllTestimonials()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // Navigation functions
  fun navigateTo(screen: AppScreen) {
    _currentScreen.value = screen
  }

  fun setAdminTab(tab: AdminTab) {
    _adminTab.value = tab
  }

  fun selectNews(news: NewsEntity?) {
    _selectedNews.value = news
  }

  fun setLightboxGallery(item: GalleryEntity?) {
    _lightboxGallery.value = item
  }

  fun toggleDarkMode() {
    _isDarkMode.value = !_isDarkMode.value
  }

  // Admin authentication
  fun loginAdmin(password: String): Boolean {
    return if (password == "MIMTAQECY") {
      _isAdminLoggedIn.value = true
      _currentScreen.value = AppScreen.ADMIN_DASHBOARD
      true
    } else {
      false
    }
  }

  fun logoutAdmin() {
    _isAdminLoggedIn.value = false
    _currentScreen.value = AppScreen.HOME
  }

  // School Profile operations
  fun updateSchoolProfile(profile: SchoolProfileEntity) {
    viewModelScope.launch {
      dao.upsertSchoolProfile(profile)
    }
  }

  // News operations
  fun saveNews(news: NewsEntity) {
    viewModelScope.launch {
      if (news.id == 0L) {
        dao.insertNews(news)
      } else {
        dao.updateNews(news)
      }
    }
  }

  fun deleteNews(news: NewsEntity) {
    viewModelScope.launch {
      dao.deleteNews(news)
    }
  }

  // Gallery operations
  fun saveGallery(item: GalleryEntity) {
    viewModelScope.launch {
      if (item.id == 0L) {
        dao.insertGallery(item)
      } else {
        dao.updateGallery(item)
      }
    }
  }

  fun deleteGallery(item: GalleryEntity) {
    viewModelScope.launch {
      dao.deleteGallery(item)
    }
  }

  // Extracurricular operations
  fun saveExtracurricular(item: ExtracurricularEntity) {
    viewModelScope.launch {
      if (item.id == 0L) {
        dao.insertExtracurricular(item)
      } else {
        dao.updateExtracurricular(item)
      }
    }
  }

  fun deleteExtracurricular(item: ExtracurricularEntity) {
    viewModelScope.launch {
      dao.deleteExtracurricular(item)
    }
  }

  // Featured Programs
  fun saveFeaturedProgram(item: FeaturedProgramEntity) {
    viewModelScope.launch {
      if (item.id == 0L) {
        dao.insertFeaturedProgram(item)
      } else {
        dao.updateFeaturedProgram(item)
      }
    }
  }

  fun deleteFeaturedProgram(item: FeaturedProgramEntity) {
    viewModelScope.launch {
      dao.deleteFeaturedProgram(item)
    }
  }

  // Agendas
  fun saveAgenda(item: AgendaEntity) {
    viewModelScope.launch {
      if (item.id == 0L) {
        dao.insertAgenda(item)
      } else {
        dao.updateAgenda(item)
      }
    }
  }

  fun deleteAgenda(item: AgendaEntity) {
    viewModelScope.launch {
      dao.deleteAgenda(item)
    }
  }

  // Announcements
  fun saveAnnouncement(item: AnnouncementEntity) {
    viewModelScope.launch {
      if (item.id == 0L) {
        dao.insertAnnouncement(item)
      } else {
        dao.updateAnnouncement(item)
      }
    }
  }

  fun deleteAnnouncement(item: AnnouncementEntity) {
    viewModelScope.launch {
      dao.deleteAnnouncement(item)
    }
  }

  // Teachers
  fun saveTeacher(item: TeacherEntity) {
    viewModelScope.launch {
      if (item.id == 0L) {
        dao.insertTeacher(item)
      } else {
        dao.updateTeacher(item)
      }
    }
  }

  fun deleteTeacher(item: TeacherEntity) {
    viewModelScope.launch {
      dao.deleteTeacher(item)
    }
  }

  // Students
  fun saveStudent(item: StudentEntity) {
    viewModelScope.launch {
      if (item.id == 0L) {
        dao.insertStudent(item)
      } else {
        dao.updateStudent(item)
      }
    }
  }

  fun deleteStudent(item: StudentEntity) {
    viewModelScope.launch {
      dao.deleteStudent(item)
    }
  }

  /**
   * Bulk import students from CSV content
   * Format: NO,NAMA,NISN,TEMPAT LAHIR,TGL LAHIR,ROMBEL
   */
  fun importStudentsFromCsv(csvText: String): Int {
    var count = 0
    viewModelScope.launch {
      val lines = csvText.lines()
      val newStudents = mutableListOf<StudentEntity>()
      for (line in lines) {
        val trimmed = line.trim()
        if (trimmed.isEmpty() || trimmed.startsWith("NO", ignoreCase = true) || trimmed.startsWith("#")) {
          continue
        }
        val tokens = trimmed.split(",").map { it.trim() }
        if (tokens.size >= 6) {
          val name = tokens[1]
          val nisn = tokens[2]
          val birthPlace = tokens[3]
          val birthDate = tokens[4]
          val rombel = tokens[5]
          // Determine class level based on rombel or default to 1
          val classLevel = when {
            rombel.contains("1", true) -> 1
            rombel.contains("2", true) -> 2
            rombel.contains("3", true) -> 3
            rombel.contains("4", true) -> 4
            rombel.contains("5", true) -> 5
            rombel.contains("6", true) -> 6
            else -> 1
          }
          if (name.isNotEmpty()) {
            newStudents.add(
              StudentEntity(
                name = name,
                nisn = nisn,
                birthPlace = birthPlace,
                birthDate = birthDate,
                classLevel = classLevel,
                rombel = rombel
              )
            )
            count++
          }
        }
      }
      if (newStudents.isNotEmpty()) {
        dao.insertStudents(newStudents)
      }
    }
    return count
  }

  // PPDB
  fun submitPpdbApplication(
    studentName: String,
    nisn: String,
    nik: String,
    birthPlace: String,
    birthDate: String,
    gender: String,
    address: String,
    whatsappNumber: String,
    previousSchool: String,
    fatherName: String,
    fatherNik: String,
    fatherBirthPlace: String,
    fatherBirthDate: String,
    fatherEducation: String,
    fatherOccupation: String,
    motherName: String,
    motherNik: String,
    motherBirthPlace: String,
    motherBirthDate: String,
    motherEducation: String,
    motherOccupation: String,
    kkDocPath: String,
    ijazahDocPath: String,
    aktaDocPath: String,
    onSuccess: (String) -> Unit
  ) {
    viewModelScope.launch {
      val regNumber = "PPDB-" + SimpleDateFormat("yyyy", Locale.getDefault()).format(Date()) + "-" +
          (100 + (1..899).random()).toString()
      val dateStr = SimpleDateFormat("dd MMM yyyy", Locale("id", "ID")).format(Date())
      val item = PpdbEntity(
        registrationNumber = regNumber,
        registrationDate = dateStr,
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
        kkDocumentPath = kkDocPath,
        ijazahDocumentPath = ijazahDocPath,
        aktaDocumentPath = aktaDocPath,
        status = "Menunggu Verifikasi"
      )
      dao.insertPpdb(item)
      onSuccess(regNumber)
    }
  }

  fun updatePpdbStatus(item: PpdbEntity, newStatus: String, notes: String = "") {
    viewModelScope.launch {
      dao.updatePpdb(item.copy(status = newStatus, notes = notes))
    }
  }

  fun deletePpdb(item: PpdbEntity) {
    viewModelScope.launch {
      dao.deletePpdb(item)
    }
  }

  // Contact Message
  fun submitContactMessage(
    name: String,
    email: String,
    phone: String,
    subject: String,
    message: String,
    onSuccess: () -> Unit
  ) {
    viewModelScope.launch {
      val dateStr = SimpleDateFormat("dd MMM yyyy HH:mm", Locale("id", "ID")).format(Date())
      val item = ContactMessageEntity(
        name = name,
        email = email,
        phone = phone,
        subject = subject,
        message = message,
        date = dateStr,
        isRead = false
      )
      dao.insertContactMessage(item)
      onSuccess()
    }
  }

  fun markMessageRead(item: ContactMessageEntity) {
    viewModelScope.launch {
      dao.updateContactMessage(item.copy(isRead = true))
    }
  }

  fun deleteContactMessage(item: ContactMessageEntity) {
    viewModelScope.launch {
      dao.deleteContactMessage(item)
    }
  }

  // Testimonials
  fun saveTestimonial(item: TestimonialEntity) {
    viewModelScope.launch {
      if (item.id == 0L) {
        dao.insertTestimonial(item)
      } else {
        dao.updateTestimonial(item)
      }
    }
  }

  fun deleteTestimonial(item: TestimonialEntity) {
    viewModelScope.launch {
      dao.deleteTestimonial(item)
    }
  }
}
