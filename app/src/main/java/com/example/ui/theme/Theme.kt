package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
  primary = SchoolBluePrimaryDark,
  onPrimary = SchoolBlueOnPrimaryDark,
  primaryContainer = SchoolBlueContainerDark,
  onPrimaryContainer = SchoolOnBlueContainerDark,
  secondary = SchoolGoldSecondaryDark,
  onSecondary = SchoolGoldOnSecondaryDark,
  secondaryContainer = SchoolGoldContainerDark,
  onSecondaryContainer = SchoolOnGoldContainerDark,
  tertiary = SchoolTealTertiary,
  onTertiary = SchoolTealOnTertiary,
  background = SchoolBackgroundDark,
  onBackground = SchoolOnBackgroundDark,
  surface = SchoolSurfaceDark,
  onSurface = SchoolOnSurfaceDark,
  surfaceVariant = SchoolSurfaceVariantDark,
  outline = SchoolOutlineDark
)

private val LightColorScheme = lightColorScheme(
  primary = SchoolBluePrimary,
  onPrimary = SchoolBlueOnPrimary,
  primaryContainer = SchoolBlueContainer(0xFFE2ECFA),
  onPrimaryContainer = SchoolOnBlueContainer,
  secondary = SchoolGoldSecondary,
  onSecondary = SchoolGoldOnSecondary,
  secondaryContainer = SchoolGoldContainer,
  onSecondaryContainer = SchoolOnGoldContainer,
  tertiary = SchoolTealTertiary,
  onTertiary = SchoolTealOnTertiary,
  background = SchoolBackgroundLight,
  onBackground = SchoolOnBackgroundLight,
  surface = SchoolSurfaceLight,
  onSurface = SchoolOnSurfaceLight,
  surfaceVariant = SchoolSurfaceVariantLight,
  outline = SchoolOutlineLight
)

private fun SchoolBlueContainer(colorLong: Long): Color = Color(colorLong)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false, // keep branded palette consistent
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}
