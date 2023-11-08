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

val Star: ImageVector
    get() {
        if (_icStar != null) {
            return _icStar!!
        }
        _icStar = Builder(name = "IcStar", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 2.0f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveToRelative(18.9999f, 22.0002f)
                lineToRelative(-7.0f, -4.831f)
                lineToRelative(-7.0f, 4.831f)
                lineToRelative(2.6f, -7.9734f)
                lineToRelative(-6.6f, -5.0266f)
                horizontalLineToRelative(8.2f)
                lineToRelative(2.8f, -8.0f)
                lineToRelative(2.8f, 7.9982f)
                lineToRelative(8.2f, 0.002f)
                lineToRelative(-6.6f, 5.0609f)
                close()
            }
        }
        .build()
        return _icStar!!
    }

private var _icStar: ImageVector? = null
