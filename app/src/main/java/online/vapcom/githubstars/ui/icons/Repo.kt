/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Repo: ImageVector
    get() {
        if (_icRepo != null) {
            return _icRepo!!
        }
        _icRepo = Builder(name = "IcRepo", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0x00000000)),
                    strokeLineWidth = 1.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveToRelative(2.0f, 4.0f)
                curveToRelative(-0.0f, -2.1039f, 1.8961f, -4.0f, 4.0f, -4.0f)
                horizontalLineToRelative(13.0f)
                curveToRelative(0.6312f, 0.0f, 1.0f, 0.3688f, 1.0f, 1.0f)
                verticalLineToRelative(19.0f)
                curveToRelative(0.0f, 0.6312f, -0.3688f, 1.0f, -1.0f, 1.0f)
                horizontalLineToRelative(-3.6667f)
                curveToRelative(-1.3136f, 0.0f, -1.3136f, -2.0f, 0.0f, -2.0f)
                horizontalLineToRelative(2.6667f)
                verticalLineToRelative(-3.0f)
                lineTo(6.0f, 16.0f)
                curveToRelative(-1.5078f, 0.079f, -2.0396f, 1.5212f, -1.2785f, 2.5905f)
                curveToRelative(1.0667f, 1.089f, -0.5669f, 2.689f, -1.6335f, 1.6f)
                curveToRelative(-0.6983f, -0.7118f, -1.0891f, -1.6695f, -1.088f, -2.6667f)
                close()
                moveTo(18.0f, 2.0f)
                lineTo(6.0f, 2.0f)
                curveToRelative(-0.8416f, 0.0f, -2.0f, 1.1584f, -2.0f, 2.0f)
                verticalLineToRelative(10.5568f)
                curveToRelative(0.428f, -0.499f, 1.4756f, -0.5577f, 2.0f, -0.5568f)
                lineTo(18.0f, 14.0f)
                close()
                moveTo(6.9524f, 18.4302f)
                curveToRelative(0.0f, -0.2104f, 0.1706f, -0.4302f, 0.381f, -0.4302f)
                lineTo(12.6404f, 18.0f)
                curveToRelative(0.2104f, 0.0f, 0.3596f, 0.2198f, 0.3596f, 0.4302f)
                verticalLineToRelative(4.821f)
                curveToRelative(-0.0f, 0.3139f, -0.3108f, 0.4931f, -0.5619f, 0.3048f)
                lineToRelative(-2.2095f, -1.6564f)
                curveToRelative(-0.1353f, -0.1021f, -0.3219f, -0.1021f, -0.4571f, 0.0f)
                lineToRelative(-2.2095f, 1.6564f)
                curveToRelative(-0.2511f, 0.1884f, -0.6095f, 0.009f, -0.6095f, -0.3048f)
                close()
            }
        }
        .build()
        return _icRepo!!
    }

private var _icRepo: ImageVector? = null
