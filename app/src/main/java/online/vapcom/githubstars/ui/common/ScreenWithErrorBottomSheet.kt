/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.ui.common

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.dp
import online.vapcom.githubstars.data.ErrorState

/**
 * Top layer composable of screen with error bottom sheet.
 *
 * If screen can process some errors it should not pass isError
 *
 * @param isError if true ModalBottomSheet with error description will be shown.
 * It should be set to true only in a case of error which screen can't process by itself.
 * @param error error description.
 * @param clearErrorState top level function to clear screen's error state after user close bottom sheet.
 * @param content screen's top level composable, usually Scaffold.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ScreenWithErrorBottomSheet(isError: Boolean, error: ErrorState, clearErrorState: () -> Unit, content: @Composable () -> Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { targetState ->
            // after user hide bottom sheet we turn off error state
            if (targetState == ModalBottomSheetValue.Hidden) {
                clearErrorState()
            }

            true
        }
    )

    if (isError) { // pull up bottom sheet on error
        LaunchedEffect(Unit) { // we need CoroutineScope to change bottom sheet's state
            modalBottomSheetState.show()
        }
    }

    ModalBottomSheetLayout(
        sheetContent = {
            ErrorDescriptionSheet(isError, error)
        },
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
        sheetElevation = 4.dp,
        sheetBackgroundColor = MaterialTheme.colorScheme.surface,
        sheetContentColor = MaterialTheme.colorScheme.onSurface,
        scrimColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.50f),
        content = content
    )
}
