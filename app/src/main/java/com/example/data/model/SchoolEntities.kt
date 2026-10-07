package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "school_profile")
data class SchoolProfileEntity(
  @PrimaryKey val id: Int = 1,
  val schoolName: String = "MI MUHAMMADIYAH TANJUNG QENCONO",
  val slogan: String = "Religi, Cerdas, dan Berprestasi",
  val description: String = "Madrasah Ibtidaiyah unggulan berlandaskan nilai-nilai Islam dan kemuhammadiyahan yang mencetak generasi berkarakter qurani, cerdas intelektual, dan berdaya saing.",
  val address: String = "Jl.Pendidikan No.01 Desa Tanjung Qencono Kec.Way Bungur Kab.Lampung Timur",
  val phone: String = "0853-7802-9852",
  val email: String = "mimtaqecy@gmail.com",
  val whatsapp: String = "0853-7802-9852",
  val websiteUrl: String = "https://mimtanjungqencono.sch.id",
  val establishedYear: String = "1988",
  val accreditation: String = "Akreditasi A",
  val logoUrlOrPath: String = "https://blogger.googleusercontent.com/img/a/AVvXsEgJsT4IbbOkW36lqMphrf7qztB9tKAtRROjdvYhplP_8HlBMXvgRLgxmfCfsAknWcKO67BfSXEiGjF8XslARuiLkPu0PPvSG1rdGOJnV3OeOn18nkZ90ULDU370nADqcMOeSOCSa4IHYtRd9iCp3iZ_wNJTy9MT-UITJhLB2rmj1ALvxRP1uGCv2GUQr7Qc",
  val principalName: String = "H. Ahmad Dahlan, S.Pd.I, M.Pd",
  val principalSpeech: String = "Assalamu'alaikum Warahmatullahi Wabarakatuh.\n\nPuji syukur kita panjatkan ke hadirat Allah SWT. Selamat datang di portal resmi MI Muhammadiyah Tanjung Qencono. Madrasah kami berkomitmen memberikan pendidikan dasar Islam terbaik, memadukan kurikulum nasional dan kurikulum keislaman Kemenag & ISMUBA. Dengan lingkungan belajar yang asri, religius, dan didukung tenaga pendidik berdedikasi, kami siap mendampingi putra-putri Anda menjadi pribadi beriman, berilmu, dan berakhlakul karimah.",
  val history: String = "MI Muhammadiyah Tanjung Qencono didirikan pada tahun 1988 oleh para tokoh persyarikatan Muhammadiyah Way Bungur atas dedikasi dan kepedulian terhadap pendidikan Islam anak bangsa. Bermula dari beberapa ruang kelas sederhana, kini madrasah telah berkembang menjadi institusi pendidikan Islam modern dengan fasilitas lengkap dan ratusan alumni yang telah berkiprah di berbagai tingkatan.",
  val vision: String = "Terwujudnya Generasi Muslim yang Taqwa, Cerdas, Mandiri, dan Berprestasi Tingkat Nasional.",
  val mission: String = "1. Menanamkan keimanan dan ketaqwaan melalui pembiasaan ibadah harian.\n2. Menyelenggarakan proses pembelajaran aktif, kreatif, efektif, dan menyenangkan.\n3. Mengembangkan potensi bakat dan minat siswa melalui kegiatan ekstrakurikuler.\n4. Menerapkan disiplin, budi pekerti luhur, dan kepedulian sosial.",
  val goals: String = "Menghasilkan lulusan yang hafal minimal juz 30, mahir membaca Al-Qur'an, memiliki dasar akademik yang kuat, serta berakhlak mulia siap melanjutkan ke jenjang MTs/SMP unggulan.",
  val organizationStructure: String = "Kepala Madrasah: H. Ahmad Dahlan, S.Pd.I, M.Pd\nKetua Komite: Drs. H. Sukardi, M.Si\nWaka Kurikulum: Siti Aminah, S.Pd.I\nWaka Kesiswaan: M. Ridwan, S.Pd\nKepala Tata Usaha: Budi Santoso, S.Kom\nBendahara: Nurul Hidayah, S.E\nKoordinator Ismuba: Ustadz Fauzi, S.Th.I\nKoordinator Sarpras: Hendra Wijaya, A.Md",
  val facebookUrl: String = "https://facebook.com/mimtanjungqencono",
  val instagramUrl: String = "https://instagram.com/mimtaqecy",
  val youtubeUrl: String = "https://youtube.com/@mimtanjungqencono",
  val tiktokUrl: String = "https://tiktok.com/@mimtaqecy"
)

@Entity(tableName = "news")
data class NewsEntity(
  @PrimaryKey(autoGenerate = true) val id: Long = 0,
  val title: String,
  val category: String,
  val date: String,
  val summary: String,
  val content: String,
  val imagePath: String,
  val author: String = "Humas MIM TAQECY"
)

@Entity(tableName = "gallery")
data class GalleryEntity(
  @PrimaryKey(autoGenerate = true) val id: Long = 0,
  val title: String,
  val category: String, // Kegiatan sekolah, Pembelajaran, Upacara, Prestasi, Ekstrakurikuler, Fasilitas
  val imagePath: String,
  val description: String = "",
  val date: String = ""
)

@Entity(tableName = "extracurricular")
data class ExtracurricularEntity(
  @PrimaryKey(autoGenerate = true) val id: Long = 0,
  val name: String,
  val coach: String,
  val schedule: String,
  val imagePath: String,
  val description: String
)

@Entity(tableName = "featured_programs")
data class FeaturedProgramEntity(
  @PrimaryKey(autoGenerate = true) val id: Long = 0,
  val name: String,
  val description: String,
  val imagePath: String,
  val targetLevel: String = "Semua Kelas"
)

@Entity(tableName = "agendas")
data class AgendaEntity(
  @PrimaryKey(autoGenerate = true) val id: Long = 0,
  val title: String,
  val date: String,
  val time: String,
  val location: String,
  val description: String,
  val imagePath: String = ""
)

@Entity(tableName = "announcements")
data class AnnouncementEntity(
  @PrimaryKey(autoGenerate = true) val id: Long = 0,
  val title: String,
  val date: String,
  val priority: String = "Info",
  val content: String
)

@Entity(tableName = "teachers")
data class TeacherEntity(
  @PrimaryKey(autoGenerate = true) val id: Long = 0,
  val name: String,
  val nip: String,
  val role: String,
  val education: String,
  val imagePath: String
)

@Entity(tableName = "students")
data class StudentEntity(
  @PrimaryKey(autoGenerate = true) val id: Long = 0,
  val name: String,
  val nisn: String,
  val birthPlace: String,
  val birthDate: String,
  val classLevel: Int, // 1 to 6
  val rombel: String // e.g. Ar Rahman, Ar Rahiim, etc.
)

@Entity(tableName = "ppdb_applications")
data class PpdbEntity(
  @PrimaryKey(autoGenerate = true) val id: Long = 0,
  val registrationNumber: String,
  val registrationDate: String,
  val studentName: String,
  val nisn: String,
  val nik: String,
  val birthPlace: String,
  val birthDate: String,
  val gender: String,
  val address: String,
  val whatsappNumber: String,
  val previousSchool: String,
  val fatherName: String,
  val fatherNik: String,
  val fatherBirthPlace: String,
  val fatherBirthDate: String,
  val fatherEducation: String,
  val fatherOccupation: String,
  val motherName: String,
  val motherNik: String,
  val motherBirthPlace: String,
  val motherBirthDate: String,
  val motherEducation: String,
  val motherOccupation: String,
  val kkDocumentPath: String = "",
  val ijazahDocumentPath: String = "",
  val aktaDocumentPath: String = "",
  val status: String = "Menunggu Verifikasi", // Menunggu Verifikasi, Diterima, Ditolak
  val notes: String = ""
)

@Entity(tableName = "contact_messages")
data class ContactMessageEntity(
  @PrimaryKey(autoGenerate = true) val id: Long = 0,
  val name: String,
  val email: String,
  val phone: String,
  val subject: String,
  val message: String,
  val date: String,
  val isRead: Boolean = false
)

@Entity(tableName = "testimonials")
data class TestimonialEntity(
  @PrimaryKey(autoGenerate = true) val id: Long = 0,
  val name: String,
  val role: String, // Alumni / Wali Murid
  val content: String,
  val year: String = "2024",
  val rating: Int = 5,
  val imagePath: String = ""
)
