package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.SchoolTopBar
import com.example.ui.components.WhatsAppFloatingButton
import com.example.ui.screens.AcademicScreen
import com.example.ui.screens.AdminLoginScreen
import com.example.ui.screens.ContactScreen
import com.example.ui.screens.GalleryScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.NewsScreen
import com.example.ui.screens.PpdbScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.admin.AdminDashboardScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.AppScreen
import com.example.viewmodel.SchoolViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      val schoolViewModel: SchoolViewModel = viewModel()
      val isDarkMode by schoolViewModel.isDarkMode.collectAsState()

      MyApplicationTheme(darkTheme = isDarkMode) {
        SchoolMainContent(viewModel = schoolViewModel)
      }
    }
  }
}

@Composable
fun SchoolMainContent(viewModel: SchoolViewModel) {
  val currentScreen by viewModel.currentScreen.collectAsState()
  val profile by viewModel.schoolProfile.collectAsState()
  val isDarkMode by viewModel.isDarkMode.collectAsState()
  val isAdminLoggedIn by viewModel.isAdminLoggedIn.collectAsState()

  // Handle system Back button properly: return to HOME screen from any other sub-screen
  if (currentScreen != AppScreen.HOME) {
    BackHandler {
      if (currentScreen == AppScreen.ADMIN_DASHBOARD) {
        // From admin dashboard, return to HOME without logging out, or stay
        viewModel.navigateTo(AppScreen.HOME)
      } else {
        viewModel.navigateTo(AppScreen.HOME)
      }
    }
  }

  val isPublicScreen = currentScreen !in listOf(AppScreen.ADMIN_LOGIN, AppScreen.ADMIN_DASHBOARD)

  Scaffold(
    modifier = Modifier.fillMaxSize(),
    topBar = {
      if (isPublicScreen) {
        SchoolTopBar(
          schoolName = profile.schoolName,
          slogan = profile.slogan,
          logoUrl = profile.logoUrlOrPath,
          currentScreen = currentScreen,
          isDarkMode = isDarkMode,
          isAdminLoggedIn = isAdminLoggedIn,
          onScreenSelected = { viewModel.navigateTo(it) },
          onToggleDarkMode = { viewModel.toggleDarkMode() },
          onAdminClick = {
            if (isAdminLoggedIn) {
              viewModel.navigateTo(AppScreen.ADMIN_DASHBOARD)
            } else {
              viewModel.navigateTo(AppScreen.ADMIN_LOGIN)
            }
          }
        )
      }
    }
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      when (currentScreen) {
        AppScreen.HOME -> HomeScreen(viewModel = viewModel)
        AppScreen.PROFILE -> ProfileScreen(viewModel = viewModel)
        AppScreen.ACADEMIC -> AcademicScreen(viewModel = viewModel)
        AppScreen.NEWS -> NewsScreen(viewModel = viewModel)
        AppScreen.GALLERY -> GalleryScreen(viewModel = viewModel)
        AppScreen.PPDB -> PpdbScreen(viewModel = viewModel)
        AppScreen.CONTACT -> ContactScreen(viewModel = viewModel)
        AppScreen.ADMIN_LOGIN -> AdminLoginScreen(viewModel = viewModel)
        AppScreen.ADMIN_DASHBOARD -> AdminDashboardScreen(viewModel = viewModel)
      }

      // Floating WhatsApp button on all public screens
      if (isPublicScreen) {
        WhatsAppFloatingButton(
          phoneNumber = profile.whatsapp,
          schoolName = profile.schoolName,
          modifier = Modifier.align(Alignment.BottomEnd)
        )
      }
    }
  }
}
