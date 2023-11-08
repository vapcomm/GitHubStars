/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val ArrowLeft: ImageVector
    get() {
        if (_icArrowLeft != null) {
            return _icArrowLeft!!
        }
        _icArrowLeft = Builder(
            name = "ArrowLeft",
            defaultWidth = 24.0.dp,
            defaultHeight =
            24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0x00000000)),
                stroke = SolidColor(Color(0xFFffffff)),
                strokeLineWidth = 1.5f,
                strokeLineCap = Round,
                strokeLineJoin =
                StrokeJoin.Companion.Round,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveToRelative(15.8448f, 19.9201f)
                lineToRelative(-6.52f, -6.52f)
                curveToRelative(-0.77f, -0.77f, -0.77f, -2.03f, 0.0f, -2.8f)
                lineTo(15.8448f, 4.0801f)
            }
        }
            .build()
        return _icArrowLeft!!
    }

private var _icArrowLeft: ImageVector? = null
