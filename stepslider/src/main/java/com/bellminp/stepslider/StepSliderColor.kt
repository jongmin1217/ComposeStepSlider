package com.bellminp.stepslider

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color


sealed class ColorOrBrush {
    data class SingleColor(val color: Color) : ColorOrBrush()
    data class GradientBrush(val brush: Brush) : ColorOrBrush()
}

@Immutable
class StepSliderColor(
    val activeTrackColor: ColorOrBrush = ColorOrBrush.SingleColor(Color.Red),
    val inactiveTrackColor: ColorOrBrush = ColorOrBrush.SingleColor(Color.Gray),
    val activeTickColor: ColorOrBrush = ColorOrBrush.SingleColor(Color.White),
    val inactiveTickColor: ColorOrBrush = ColorOrBrush.SingleColor(Color.White),
    val disabledActiveTrackColor: ColorOrBrush = ColorOrBrush.SingleColor(Color.Gray),
    val disabledInactiveTrackColor: ColorOrBrush = ColorOrBrush.SingleColor(Color.LightGray),
    val disabledActiveTickColor: ColorOrBrush = ColorOrBrush.SingleColor(Color.White),
    val disabledInactiveTickColor: ColorOrBrush = ColorOrBrush.SingleColor(Color.White),
) {
    fun copy(
        activeTrackColor: ColorOrBrush = this.activeTrackColor,
        inactiveTrackColor: ColorOrBrush = this.inactiveTrackColor,
        activeTickColor: ColorOrBrush = this.activeTickColor,
        inactiveTickColor: ColorOrBrush = this.inactiveTickColor,
        disabledActiveTrackColor: ColorOrBrush = this.disabledActiveTrackColor,
        disabledInactiveTrackColor: ColorOrBrush = this.disabledInactiveTrackColor,
        disabledActiveTickColor: ColorOrBrush = this.disabledActiveTickColor,
        disabledInactiveTickColor: ColorOrBrush = this.disabledInactiveTickColor,
    ) = StepSliderColor(
        activeTrackColor = this.activeTrackColor,
        inactiveTrackColor = this.inactiveTrackColor,
        activeTickColor = this.activeTickColor,
        inactiveTickColor = this.inactiveTickColor,
        disabledActiveTrackColor = this.disabledActiveTrackColor,
        disabledInactiveTrackColor = this.disabledInactiveTrackColor,
        disabledActiveTickColor = this.disabledActiveTickColor,
        disabledInactiveTickColor = this.disabledInactiveTickColor
    )

    @Stable
    internal fun trackColor(enabled: Boolean, active: Boolean): ColorOrBrush =
        if (enabled) {
            if (active) activeTrackColor else inactiveTrackColor
        } else {
            if (active) disabledActiveTrackColor else disabledInactiveTrackColor
        }


    @Stable
    internal fun tickColor(enabled: Boolean, active: Boolean): ColorOrBrush =
        if (enabled) {
            if (active) activeTickColor else inactiveTickColor
        } else {
            if (active) disabledActiveTickColor else disabledInactiveTickColor
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is StepSliderColor) return false

        if (activeTrackColor != other.activeTrackColor) return false
        if (inactiveTrackColor != other.inactiveTrackColor) return false
        if (activeTickColor != other.activeTickColor) return false
        if (inactiveTickColor != other.inactiveTickColor) return false
        if (disabledActiveTrackColor != other.disabledActiveTrackColor) return false
        if (disabledInactiveTrackColor != other.disabledInactiveTrackColor) return false
        if (disabledActiveTickColor != other.disabledActiveTickColor) return false
        if (disabledInactiveTickColor != other.disabledInactiveTickColor) return false

        return true
    }

    override fun hashCode(): Int {
        var result = activeTrackColor.hashCode()
        result = 31 * result + inactiveTrackColor.hashCode()
        result = 31 * result + activeTickColor.hashCode()
        result = 31 * result + inactiveTickColor.hashCode()
        result = 31 * result + disabledActiveTrackColor.hashCode()
        result = 31 * result + disabledInactiveTrackColor.hashCode()
        result = 31 * result + disabledActiveTickColor.hashCode()
        result = 31 * result + disabledInactiveTickColor.hashCode()
        return result
    }
}