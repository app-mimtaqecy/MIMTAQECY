package com.example.data.local

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

object InitialData {

  fun getInitialProfile(): SchoolProfileEntity = SchoolProfileEntity()

  fun getInitialNews(): List<NewsEntity> = listOf(
    NewsEntity(
      id = 1,
      title = "Siswa MIM Tanjung Qencono Raih Juara 1 Olimpiade Matematika dan Sains Tingkat Kabupaten",
      category = "Prestasi",
      date = "02 Oktober 2026",
      summary = "Prestasi membanggakan kembali diukir ananda Muhammad Zaki Al-Fatih yang berhasil meraih medali emas dalam KSM.",
      content = "Alhamdulillah, siswa kelas 5 MI Muhammadiyah Tanjung Qencono berhasil menorehkan prestasi gemilang dengan meraih Juara 1 Kompetisi Sains Madrasah (KSM) bidang Matematika Terintegrasi tingkat Kabupaten Lampung Timur.\n\nKepala Madrasah, H. Ahmad Dahlan, S.Pd.I, M.Pd, menyampaikan apresiasi setinggi-tingginya kepada para guru pembimbing dan orang tua atas kerja sama yang solid dalam mengantarkan peserta didik meraih prestasi terbaik.",
      imagePath = "https://images.unsplash.com/photo-1577896851231-70ef18881754?w=800&q=80",
      author = "Humas MIM TAQECY"
    ),
    NewsEntity(
      id = 2,
      title = "Pawai Taaruf dan Bakti Sosial Peringatan Milad Muhammadiyah ke-112 di Way Bungur",
      category = "Kegiatan",
      date = "25 September 2026",
      summary = "Ratusan santri dan wali murid MIM Tanjung Qencono memeriahkan pawai taaruf keliling desa dengan semarak drum band Hizbul Wathan.",
      content = "Dalam rangka memperingati Milad Muhammadiyah ke-112, MI Muhammadiyah Tanjung Qencono menyelenggarakan rangkaian kegiatan pawai taaruf, khitan massal, dan pembagian sembako dhuafa di lingkungan Desa Tanjung Qencono.\n\nKegiatan ini diikuti dengan antusias oleh segenap civitas akademika, pimpinan ranting Muhammadiyah, Aisyiyah, serta masyarakat sekitar.",
      imagePath = "https://images.unsplash.com/photo-1544717305-2782549b5136?w=800&q=80",
      author = "Panitia Milad"
    ),
    NewsEntity(
      id = 3,
      title = "Kegiatan Tahfidz Camp dan Pembiasaan Ibadah Praktis Semester Ganjil Berlangsung Khidmat",
      category = "Akademik",
      date = "15 September 2026",
      summary = "Program intensif tahfidz Al-Qur'an juz 30 dan pembiasaan sholat fardhu berjamaah untuk seluruh siswa kelas 4, 5, dan 6.",
      content = "Selama tiga hari berturut-turut, MI Muhammadiyah Tanjung Qencono menggelar program Tahfidz Camp di lingkungan madrasah. Seluruh peserta dibimbing langsung oleh para asatidz dengan metode talaqqi dan muroja'ah berpasangan untuk memantapkan hafalan juz 30.",
      imagePath = "https://images.unsplash.com/photo-1580582932707-520aed937b7b?w=800&q=80",
      author = "Tim Ismuba"
    )
  )

  fun getInitialGallery(): List<GalleryEntity> = listOf(
    GalleryEntity(
      id = 1,
      title = "Upacara Bendera dan Pembiasaan Sholat Dhuha",
      category = "Upacara",
      imagePath = "https://images.unsplash.com/photo-1509062522246-3755977927d7?w=800&q=80",
      description = "Rutinitas pagi sebelum memulai pelajaran untuk memupuk disiplin dan spiritualitas siswa.",
      date = "Senin, 28 Sep 2026"
    ),
    GalleryEntity(
      id = 2,
      title = "Pembelajaran Sains Interaktif Kurikulum Merdeka",
      category = "Pembelajaran",
      imagePath = "https://images.unsplash.com/photo-1503676260728-1c00da094a0b?w=800&q=80",
      description = "Eksplorasi tanaman obat keluarga di kebun madrasah bersama guru kelas.",
      date = "Rabu, 23 Sep 2026"
    ),
    GalleryEntity(
      id = 3,
      title = "Latihan Kepanduan Hizbul Wathan (HW)",
      category = "Ekstrakurikuler",
      imagePath = "https://images.unsplash.com/photo-1472162072942-cd5147eb3902?w=800&q=80",
      description = "Membina kecakapan tali temali, semaphore, dan sandi pramuka islami.",
      date = "Jumat, 18 Sep 2026"
    ),
    GalleryEntity(
      id = 4,
      title = "Gedung Perpustakaan dan Laboratorium Komputer",
      category = "Fasilitas",
      imagePath = "https://images.unsplash.com/photo-1497633762265-9d179a990aa6?w=800&q=80",
      description = "Sarana penunjang literasi baca dan teknologi informasi bagi seluruh murid.",
      date = "Tahun Ajaran 2026/2027"
    ),
    GalleryEntity(
      id = 5,
      title = "Penyerahan Trophy Kejuaraan Pencak Silat Tapak Suci",
      category = "Prestasi",
      imagePath = "https://images.unsplash.com/photo-1567427017947-545c5f8d16ad?w=800&q=80",
      description = "Atlet cilik MIM Tanjung Qencono memboyong 3 medali emas antar perguruan se-Lampung Timur.",
      date = "Ahad, 13 Sep 2026"
    ),
    GalleryEntity(
      id = 6,
      title = "Kunjungan Edukasi Outing Class Pertanian",
      category = "Kegiatan sekolah",
      imagePath = "https://images.unsplash.com/photo-1516627145497-ae6968895b74?w=800&q=80",
      description = "Pengenalan kearifan lokal pertanian padi dan perkebunan di Way Bungur.",
      date = "Kamis, 03 Sep 2026"
    )
  )

  fun getInitialExtracurricular(): List<ExtracurricularEntity> = listOf(
    ExtracurricularEntity(
      id = 1,
      name = "Hizbul Wathan (HW)",
      coach = "Kak Ramdani, S.Pd",
      schedule = "Jumat, 14.00 - 16.00 WIB",
      imagePath = "https://images.unsplash.com/photo-1472162072942-cd5147eb3902?w=800&q=80",
      description = "Kepanduan Islami melatih kedisiplinan, kemandirian, dan kepemimpinan."
    ),
    ExtracurricularEntity(
      id = 2,
      name = "Tapak Suci Putera Muhammadiyah",
      coach = "Pendekar Hendro, S.Or",
      schedule = "Sabtu, 15.30 - 17.30 WIB",
      imagePath = "https://images.unsplash.com/photo-1555597673-b21d5c935865?w=800&q=80",
      description = "Seni bela diri raga dan mental berlandaskan akhlak mulia dan ketangkasan fisik."
    ),
    ExtracurricularEntity(
      id = 3,
      name = "Tahfidzul Qur'an",
      coach = "Ustadz Fauzi, S.Th.I",
      schedule = "Senin & Rabu, 15.00 - 16.30 WIB",
      imagePath = "https://images.unsplash.com/photo-1609599006353-e629aaabfeae?w=800&q=80",
      description = "Bimbingan intensif hafalan Al-Qur'an juz 30 dan 29 dengan metode talaqqi tajwid."
    ),
    ExtracurricularEntity(
      id = 4,
      name = "Seni Hadrah & Sholawat",
      coach = "Ibu Nur Hasanah, S.Pd.I",
      schedule = "Kamis, 14.00 - 15.30 WIB",
      imagePath = "https://images.unsplash.com/photo-1511671782779-c97d3d27a1d4?w=800&q=80",
      description = "Pelatihan seni musik islami dan sholawat membina kecintaan pada Rasulullah SAW."
    ),
    ExtracurricularEntity(
      id = 5,
      name = "Klub Sains & Matematika",
      coach = "Bapak Tri Wahyudi, S.Si",
      schedule = "Selasa, 14.00 - 15.30 WIB",
      imagePath = "https://images.unsplash.com/photo-1532094349884-543bc11b234d?w=800&q=80",
      description = "Eksperimen sains seru dan pembekalan persiapan kompetisi olimpiade KSM/OSN."
    ),
    ExtracurricularEntity(
      id = 6,
      name = "Futsal & Olahraga Atletik",
      coach = "Bapak M. Ridwan, S.Pd",
      schedule = "Ahad, 07.30 - 09.30 WIB",
      imagePath = "https://images.unsplash.com/photo-1574629810360-7efbbe195018?w=800&q=80",
      description = "Pengembangan kebugaran jasmani, teknik dasar futsal, dan sportivitas tim."
    )
  )

  fun getInitialFeaturedPrograms(): List<FeaturedProgramEntity> = listOf(
    FeaturedProgramEntity(
      id = 1,
      name = "Program Tahfidz Target 1 Juz Per Jenjang",
      description = "Setiap lulusan dibimbing memiliki hafalan minimal juz 30 mutqin lengkap dengan makhraj huruf dan tajwid.",
      imagePath = "https://images.unsplash.com/photo-1609599006353-e629aaabfeae?w=800&q=80",
      targetLevel = "Kelas 1 - 6"
    ),
    FeaturedProgramEntity(
      id = 2,
      name = "Pembiasaan Sholat Dhuha & Dzuhur Berjamaah",
      description = "Penanaman karakter islami melalui pelaksanaan ibadah sholat wajib dan sunnah, dzikir, serta kultum santri setiap hari.",
      imagePath = "https://images.unsplash.com/photo-1564769625905-50e93615e769?w=800&q=80",
      targetLevel = "Semua Siswa"
    ),
    FeaturedProgramEntity(
      id = 3,
      name = "Bilingual Arab & English Daily Vocabulary",
      description = "Pengenalan kosa kata percakapan bahasa Arab dan Inggris secara praktis dalam pergaulan lingkungan madrasah.",
      imagePath = "https://images.unsplash.com/photo-1456513080510-7bf3a84b82f8?w=800&q=80",
      targetLevel = "Kelas 3 - 6"
    ),
    FeaturedProgramEntity(
      id = 4,
      name = "Literasi Digital & Komputer Dasar",
      description = "Edukasi pengenalan perangkat komputer, aplikasi ketik, dan pemanfaatan internet sehat sejak usia sekolah dasar.",
      imagePath = "https://images.unsplash.com/photo-1516321318423-f06f85e504b3?w=800&q=80",
      targetLevel = "Kelas 4 - 6"
    )
  )

  fun getInitialAgendas(): List<AgendaEntity> = listOf(
    AgendaEntity(
      id = 1,
      title = "Penilaian Akhir Semester (PAS) Ganjil TA 2026/2027",
      date = "01 - 10 Desember 2026",
      time = "07.30 - 11.30 WIB",
      location = "Ruang Kelas MIM Tanjung Qencono",
      description = "Evaluasi pembelajaran tatap muka untuk seluruh mata pelajaran umum dan Ismuba.",
      imagePath = "https://images.unsplash.com/photo-1434030216411-0b793f4b4173?w=800&q=80"
    ),
    AgendaEntity(
      id = 2,
      title = "Pawai Taaruf & Karnaval Budaya Islami",
      date = "18 November 2026",
      time = "07.00 - 10.30 WIB",
      location = "Halaman Madrasah - Rute Desa Tanjung Qencono",
      description = "Pawai menyambut Milad Muhammadiyah bersama seluruh ortom dan wali murid.",
      imagePath = "https://images.unsplash.com/photo-1544717305-2782549b5136?w=800&q=80"
    ),
    AgendaEntity(
      id = 3,
      title = "Parenting Day dan Pembagian Rapor Semester 1",
      date = "19 Desember 2026",
      time = "08.00 - 12.00 WIB",
      location = "Aula Pertemuan MI Muhammadiyah",
      description = "Silaturahmi wali murid dan konsultasi perkembangan belajar siswa bersama wali kelas.",
      imagePath = "https://images.unsplash.com/photo-1577896851231-70ef18881754?w=800&q=80"
    )
  )

  fun getInitialAnnouncements(): List<AnnouncementEntity> = listOf(
    AnnouncementEntity(
      id = 1,
      title = "Penerimaan Peserta Didik Baru (PPDB) Tahun Pelajaran 2026/2027 Telah Dibuka!",
      date = "01 Oktober 2026",
      priority = "Tinggi",
      content = "Daftarkan segera putra-putri tercinta di MI Muhammadiyah Tanjung Qencono. Pendaftaran online melalui menu PPDB di aplikasi ini atau langsung di kantor madrasah. Dapatkan beasiswa bagi santri berprestasi dan yatim piatu."
    ),
    AnnouncementEntity(
      id = 2,
      title = "Pengumuman Jadwal Uji Hafalan Al-Qur'an (Tasmi') Juz 30",
      date = "26 September 2026",
      priority = "Sedang",
      content = "Uji tasmi' juz 30 sekali duduk bagi santri tingkat akhir akan diselenggarakan mulai hari Senin pekan depan."
    )
  )

  fun getInitialTeachers(): List<TeacherEntity> = listOf(
    TeacherEntity(
      id = 1,
      name = "H. Ahmad Dahlan, S.Pd.I, M.Pd",
      nip = "197508122003121002",
      role = "Kepala Madrasah & Pengampu Al-Islam",
      education = "S2 Magister Pendidikan Islam",
      imagePath = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=800&q=80"
    ),
    TeacherEntity(
      id = 2,
      name = "Siti Aminah, S.Pd.I",
      nip = "198204152008012015",
      role = "Waka Kurikulum & Guru Fiqih",
      education = "S1 PAI UIN Raden Intan",
      imagePath = "https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?w=800&q=80"
    ),
    TeacherEntity(
      id = 3,
      name = "M. Ridwan, S.Pd",
      nip = "198611202011011009",
      role = "Waka Kesiswaan & PJOK",
      education = "S1 Pendidikan Olahraga Unila",
      imagePath = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=800&q=80"
    ),
    TeacherEntity(
      id = 4,
      name = "Ustadz Fauzi, S.Th.I",
      nip = "199002142015031004",
      role = "Koordinator Ismuba & Guru Bahasa Arab",
      education = "S1 Tafsir Hadits",
      imagePath = "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=800&q=80"
    ),
    TeacherEntity(
      id = 5,
      name = "Nurul Hidayah, S.Pd",
      nip = "199203252019032011",
      role = "Wali Kelas 1 & Guru Tematik",
      education = "S1 PGMI",
      imagePath = "https://images.unsplash.com/photo-1580489944761-15a19d654956?w=800&q=80"
    ),
    TeacherEntity(
      id = 6,
      name = "Tri Wahyudi, S.Si",
      nip = "198906102014021003",
      role = "Guru IPAS & Matematika",
      education = "S1 Matematika",
      imagePath = "https://images.unsplash.com/photo-1519085360753-af0119f7cbe7?w=800&q=80"
    ),
    TeacherEntity(
      id = 7,
      name = "Dewi Sartika, S.Pd",
      nip = "199407182020122008",
      role = "Guru Bahasa Indonesia & Seni Budaya",
      education = "S1 Pendidikan Bahasa",
      imagePath = "https://images.unsplash.com/photo-1544005313-94ddf0286df2?w=800&q=80"
    ),
    TeacherEntity(
      id = 8,
      name = "Budi Santoso, S.Kom",
      nip = "199105122018021005",
      role = "Kepala Tata Usaha & Operator EMIS",
      education = "S1 Sistem Informasi",
      imagePath = "https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?w=800&q=80"
    )
  )

  // 12 Rombel: Ar Rahman, Ar Rahiim, Al Malik, Al Quddus, As Salaam, Al Mu`min, Al Muhaimin, Al Aziz, Al Jabbar, Al Mutakabbir, Al Khaliq, Al Baari
  val rombelList = listOf(
    "Ar Rahman", "Ar Rahiim", "Al Malik", "Al Quddus", "As Salaam", "Al Mu`min",
    "Al Muhaimin", "Al Aziz", "Al Jabbar", "Al Mutakabbir", "Al Khaliq", "Al Baari"
  )

  fun getInitialStudents(): List<StudentEntity> = listOf(
    StudentEntity(1, "Ahmad Faiz Pratama", "0142385901", "Lampung Timur", "12 Mei 2018", 1, "Ar Rahman"),
    StudentEntity(2, "Aisyah Nur Ramadhani", "0142385902", "Way Bungur", "18 Juni 2018", 1, "Ar Rahman"),
    StudentEntity(3, "Bilal Habibi", "0142385903", "Metro", "04 Januari 2018", 1, "Ar Rahiim"),
    StudentEntity(4, "Dania Putri Azzahra", "0142385904", "Tanjung Qencono", "22 Agustus 2018", 1, "Ar Rahiim"),
    StudentEntity(5, "Fatih Al-Bukhari", "0132174821", "Lampung Timur", "10 Maret 2017", 2, "Al Malik"),
    StudentEntity(6, "Hafizhah Khairunnisa", "0132174822", "Way Bungur", "05 Juli 2017", 2, "Al Malik"),
    StudentEntity(7, "Ibrahim Danendra", "0132174823", "Sukadana", "19 September 2017", 2, "Al Quddus"),
    StudentEntity(8, "Khadijah Syifa", "0132174824", "Tanjung Qencono", "01 Desember 2017", 2, "Al Quddus"),
    StudentEntity(9, "Muhammad Rayhan", "0121083711", "Lampung Timur", "14 Februari 2016", 3, "As Salaam"),
    StudentEntity(10, "Naura Salsabila", "0121083712", "Way Bungur", "29 Oktober 2016", 3, "As Salaam"),
    StudentEntity(11, "Omar Faruq", "0121083713", "Metro", "11 April 2016", 3, "Al Mu`min"),
    StudentEntity(12, "Qanita Zahra", "0121083714", "Tanjung Qencono", "08 Agustus 2016", 3, "Al Mu`min"),
    StudentEntity(13, "Rizky Aditya", "0110948291", "Lampung Timur", "23 Mei 2015", 4, "Al Muhaimin"),
    StudentEntity(14, "Salma Fitriana", "0110948292", "Way Bungur", "17 Juli 2015", 4, "Al Muhaimin"),
    StudentEntity(15, "Tariq Ziyad", "0110948293", "Sukadana", "03 November 2015", 4, "Al Aziz"),
    StudentEntity(16, "Ulya Farhana", "0110948294", "Tanjung Qencono", "25 Januari 2015", 4, "Al Aziz"),
    StudentEntity(17, "Muhammad Zaki Al-Fatih", "0109837181", "Lampung Timur", "16 September 2014", 5, "Al Jabbar"),
    StudentEntity(18, "Wulan Nur Azizah", "0109837182", "Way Bungur", "02 Maret 2014", 5, "Al Jabbar"),
    StudentEntity(19, "Yahya Abdurrahman", "0109837183", "Metro", "14 Juni 2014", 5, "Al Mutakabbir"),
    StudentEntity(20, "Zahra Callista", "0109837184", "Tanjung Qencono", "30 November 2014", 5, "Al Mutakabbir"),
    StudentEntity(21, "Akbar Maulana", "0098726191", "Lampung Timur", "05 April 2013", 6, "Al Khaliq"),
    StudentEntity(22, "Batrisya Qaireen", "0098726192", "Way Bungur", "20 Agustus 2013", 6, "Al Khaliq"),
    StudentEntity(23, "Chairil Anwar", "0098726193", "Sukadana", "12 Oktober 2013", 6, "Al Baari"),
    StudentEntity(24, "Dzakirah Talita", "0098726194", "Tanjung Qencono", "09 Januari 2013", 6, "Al Baari")
  )

  fun getInitialPpdb(): List<PpdbEntity> = listOf(
    PpdbEntity(
      id = 1,
      registrationNumber = "PPDB-2026-001",
      registrationDate = "02 Okt 2026",
      studentName = "Muhammad Azka Al-Farisi",
      nisn = "0158923741",
      nik = "1807121509190001",
      birthPlace = "Lampung Timur",
      birthDate = "15 September 2019",
      gender = "Laki-laki",
      address = "Desa Tanjung Qencono RT 03 RW 01 Kec. Way Bungur",
      whatsappNumber = "085279182341",
      previousSchool = "RA Perwanida Way Bungur",
      fatherName = "Bambang Irawan",
      fatherNik = "1807121208850002",
      fatherBirthPlace = "Lampung Timur",
      fatherBirthDate = "12 Agustus 1985",
      fatherEducation = "S1",
      fatherOccupation = "Wiraswasta",
      motherName = "Sri Wahyuni",
      motherNik = "1807122005870001",
      motherBirthPlace = "Metro",
      motherBirthDate = "20 Mei 1987",
      motherEducation = "SMA/SMK",
      motherOccupation = "Ibu Rumah Tangga",
      kkDocumentPath = "",
      ijazahDocumentPath = "",
      aktaDocumentPath = "",
      status = "Menunggu Verifikasi",
      notes = "Berkas awal online lengkap, menunggu verifikasi fisik formulir."
    ),
    PpdbEntity(
      id = 2,
      registrationNumber = "PPDB-2026-002",
      registrationDate = "04 Okt 2026",
      studentName = "Nabila Khansa Safitri",
      nisn = "0158923742",
      nik = "1807124103190003",
      birthPlace = "Way Bungur",
      birthDate = "01 Maret 2019",
      gender = "Perempuan",
      address = "Desa Tambah Subur RT 02 RW 01 Way Bungur",
      whatsappNumber = "081369281726",
      previousSchool = "TK ABA Tanjung Qencono",
      fatherName = "Agus Prasetyo",
      fatherNik = "1807121404830004",
      fatherBirthPlace = "Solo",
      fatherBirthDate = "14 April 1983",
      fatherEducation = "D3",
      fatherOccupation = "PNS",
      motherName = "Lestari",
      motherNik = "1807125206860002",
      motherBirthPlace = "Lampung Timur",
      motherBirthDate = "12 Juni 1986",
      motherEducation = "S1",
      motherOccupation = "Guru",
      kkDocumentPath = "",
      ijazahDocumentPath = "",
      aktaDocumentPath = "",
      status = "Diterima",
      notes = "Berkas terverifikasi dan memenuhi kriteria usia serta persyaratan."
    )
  )

  fun getInitialMessages(): List<ContactMessageEntity> = listOf(
    ContactMessageEntity(
      id = 1,
      name = "Hendra Setiawan",
      email = "hendrasetiawan@gmail.com",
      phone = "081273891029",
      subject = "Informasi Pendaftaran Siswa Pindahan Kelas 3",
      message = "Assalamu'alaikum, mohon informasi persyaratan mutasi siswa pindahan dari luar kota untuk kelas 3 semester genap. Terima kasih.",
      date = "03 Okt 2026",
      isRead = false
    ),
    ContactMessageEntity(
      id = 2,
      name = "Siti Marwah",
      email = "sitimarwah99@gmail.com",
      phone = "085381920192",
      subject = "Jadwal Pelayanan Kantor dan Pembayaran Seragam",
      message = "Selamat siang bapak/ibu tata usaha, apakah besok Sabtu kantor pelayanan buka untuk pengukuran seragam PPDB? Terima kasih.",
      date = "01 Okt 2026",
      isRead = true
    )
  )

  fun getInitialTestimonials(): List<TestimonialEntity> = listOf(
    TestimonialEntity(
      id = 1,
      name = "Ahmad Fauzan Al-Ghifari",
      role = "Alumni Angkatan 2022 (Santri Ponpes Modern)",
      content = "Alhamdulillah fondasi agama, hafalan Al-Qur'an, dan kedisiplinan yang diajarkan ustadz-ustadzah di MIM Tanjung Qencono sangat bermanfaat untuk jenjang pendidikan saya saat ini.",
      year = "2024",
      rating = 5,
      imagePath = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=800&q=80"
    ),
    TestimonialEntity(
      id = 2,
      name = "Ibu Suryani, S.Pd",
      role = "Wali Murid Kelas 5 (Ar Rahman)",
      content = "Anak saya menjadi lebih mandiri, rajin sholat tepat waktu, dan senang bersekolah karena lingkungan madrasah yang islami dan guru-gurunya yang ramah serta sabar.",
      year = "2025",
      rating = 5,
      imagePath = "https://images.unsplash.com/photo-1544005313-94ddf0286df2?w=800&q=80"
    ),
    TestimonialEntity(
      id = 3,
      name = "Bapak Hendro Purnomo",
      role = "Wali Murid Kelas 2 (Al Malik)",
      content = "Fasilitas madrasah sangat memadai, lingkungan aman dan asri. Program tahfidz Al-Qur'an dan ekstrakurikuler Tapak Sucinya sangat luar biasa mengasah bakat anak.",
      year = "2026",
      rating = 5,
      imagePath = "https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?w=800&q=80"
    )
  )
}
