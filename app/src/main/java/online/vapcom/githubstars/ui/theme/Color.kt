/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.ui.theme

import androidx.compose.ui.graphics.Color

// Colors schemes
//
// May be made by https://material-foundation.github.io/material-theme-builder/ (likes Chrome)
// or Figma plugin https://goo.gle/material-theme-builder-figma

// Light theme
val md_theme_light_primary = Color(0xFF7168E8)
val md_theme_light_onPrimary = Color(0xFFF3EEFF)
val md_theme_light_primaryContainer = Color(0xFFE3DFFF)
val md_theme_light_onPrimaryContainer = Color(0xFF130068)
val md_theme_light_secondary = Color(0xFF6750A4)
val md_theme_light_onSecondary = Color(0xFFFFFFFF)
val md_theme_light_secondaryContainer = Color(0xFFE9DDFF)
val md_theme_light_onSecondaryContainer = Color(0xFF22005D)
val md_theme_light_tertiary = Color(0xFF604FAF)
val md_theme_light_onTertiary = Color(0xFFFFFFFF)
val md_theme_light_tertiaryContainer = Color(0xFFE6DEFF)
val md_theme_light_onTertiaryContainer = Color(0xFF1C0062)
val md_theme_light_error = Color(0xFFD63E3E)
val md_theme_light_errorContainer = Color(0xFFFFDAD6)
val md_theme_light_onError = Color(0xFFFFFFFF)
val md_theme_light_onErrorContainer = Color(0xFF410002)
val md_theme_light_background = Color(0xFFFFFFFF) // pinky Color(0xFFFFFBFF)
val md_theme_light_onBackground = Color(0xFF1C1B1F)
val md_theme_light_surface = Color(0xFFF2F2F2) // pinky Color(0xFFFFFBFF)
val md_theme_light_onSurface = Color(0xFF1C1B1F)
val md_theme_light_surfaceVariant = Color(0xFFF3EFFA)
val md_theme_light_onSurfaceVariant = Color(0xFF94929B) // too low contrast Color(0xFFC8C5D0)
val md_theme_light_outline = Color(0xFF787680)
val md_theme_light_inverseOnSurface = Color(0xFFF4EFF4)
val md_theme_light_inverseSurface = Color(0xFF313034)
val md_theme_light_inversePrimary = Color(0xFFC5C0FF)

// val md_theme_light_shadow = Color(0xFF000000)
val md_theme_light_surfaceTint = Color(0xFF554BCB)
val md_theme_light_outlineVariant = Color(0xFFE0E0E0) //  0xFFC8C5D0
val md_theme_light_scrim = Color(0xFF000000)

// Dark theme
val md_theme_dark_primary = Color(0xFF7168E8)
val md_theme_dark_onPrimary = Color(0xFFF3EEFF)
val md_theme_dark_primaryContainer = Color(0xFF3D2FB2)
val md_theme_dark_onPrimaryContainer = Color(0xFFE3DFFF)
val md_theme_dark_secondary = Color(0xFF6750A4)
val md_theme_dark_onSecondary = Color(0xFFFFFFFF)
val md_theme_dark_secondaryContainer = Color(0xFF4F378A)
val md_theme_dark_onSecondaryContainer = Color(0xFFE9DDFF)
val md_theme_dark_tertiary = Color(0xFF604FAF)
val md_theme_dark_onTertiary = Color(0xFFFFFFFF)
val md_theme_dark_tertiaryContainer = Color(0xFF483696)
val md_theme_dark_onTertiaryContainer = Color(0xFFE6DEFF)
val md_theme_dark_error = Color(0xFFFFB4AB)
val md_theme_dark_errorContainer = Color(0xFF93000A)
val md_theme_dark_onError = Color(0xFF690005)
val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
val md_theme_dark_background = Color(0xFF151515) // old 0xFF1C1B1F
val md_theme_dark_onBackground = Color(0xFFFFFBFF)
val md_theme_dark_surface = Color(0xFF242424) // old 0xFF1C1B1F
val md_theme_dark_onSurface = Color(0xFFFFFFFF)
val md_theme_dark_surfaceVariant = Color(0xFF3D3D3D) // old 0xFF47464F
val md_theme_dark_onSurfaceVariant = Color(0xFFC8C5D0)
val md_theme_dark_outline = Color(0xFF928F99)
val md_theme_dark_inverseOnSurface = Color(0xFF1C1B1F)
val md_theme_dark_inverseSurface = Color(0xFFE5E1E6)
val md_theme_dark_inversePrimary = Color(0xFF554BCB)

// val md_theme_dark_shadow = Color(0xFF000000)
val md_theme_dark_surfaceTint = Color(0xFFC5C0FF)
val md_theme_dark_outlineVariant = Color(0xFF242424) // old  0xFF47464F
val md_theme_dark_scrim = Color(0xFF000000)

// Custom colors unused in color scheme
val OkColor = Color(0xFF33CC00)

/*
 * From ColorScheme.kt doc:

primary - The primary color is the color displayed most frequently across your app’s screens and components.
onPrimary - Color used for text and icons displayed on top of the primary color.
primaryContainer - The preferred tonal color of containers.
onPrimaryContainer - The color (and state variants) that should be used for content on top of primaryContainer.
inversePrimary - Color to be used as a "primary" color in places where the inverse color scheme is needed, such as the button on a SnackBar.
secondary - The secondary color provides more ways to accent and distinguish your product. Secondary colors are best for:
Floating action buttons
Selection controls, like checkboxes and radio buttons
Highlighting selected text
Links and headlines
onSecondary - Color used for text and icons displayed on top of the secondary color.
secondaryContainer - A tonal color to be used in containers.
onSecondaryContainer - The color (and state variants) that should be used for content on top of secondaryContainer.
tertiary - The tertiary color that can be used to balance primary and secondary colors, or bring heightened attention to an element such as an input field.
onTertiary - Color used for text and icons displayed on top of the tertiary color.
tertiaryContainer - A tonal color to be used in containers.
onTertiaryContainer - The color (and state variants) that should be used for content on top of tertiaryContainer.
background - The background color that appears behind scrollable content.
onBackground - Color used for text and icons displayed on top of the background color.
surface - The surface color that affect surfaces of components, such as cards, sheets, and menus.
onSurface - Color used for text and icons displayed on top of the surface color.
surfaceVariant - Another option for a color with similar uses of surface.
onSurfaceVariant - The color (and state variants) that can be used for content on top of surface.
surfaceTint - This color will be used by components that apply tonal elevation and is applied on top of surface. The higher the elevation the more this color is used.
inverseSurface - A color that contrasts sharply with surface. Useful for surfaces that sit on top of other surfaces with surface color.
inverseOnSurface - A color that contrasts well with inverseSurface. Useful for content that sits on top of containers that are inverseSurface.
error - The error color is used to indicate errors in components, such as invalid text in a text field.
onError - Color used for text and icons displayed on top of the error color.
errorContainer - The preferred tonal color of error containers.
onErrorContainer - The color (and state variants) that should be used for content on top of errorContainer.
outline - Subtle color used for boundaries. Outline color role adds contrast for accessibility purposes.
outlineVariant - Utility color used for boundaries for decorative elements when strong contrast is not required.
scrim - Color of a scrim that obscures content.
 */

fun Color.withAlpha(alpha: Float): Color = this.copy(alpha = alpha)
