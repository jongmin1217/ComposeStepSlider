package com.bellminp.stepslider

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontSynthesis
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.LocaleList
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextGeometricTransform
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.unit.TextUnit

@Immutable
class StepSliderTextStyle(
    val activeTextColor: ColorOrBrush = ColorOrBrush.SingleColor(Color.Red),
    val inactiveTextColor: ColorOrBrush = ColorOrBrush.SingleColor(Color.Gray),
    val disabledActiveTextColor: ColorOrBrush = ColorOrBrush.SingleColor(Color.Gray),
    val disabledInactiveTextColor: ColorOrBrush = ColorOrBrush.SingleColor(Color.LightGray),
    val fontSize: TextUnit = TextUnit.Unspecified,
    val fontWeight: FontWeight? = null,
    val fontStyle: FontStyle? = null,
    val fontSynthesis: FontSynthesis? = null,
    val fontFamily: FontFamily? = null,
    val fontFeatureSettings: String? = null,
    val letterSpacing: TextUnit = TextUnit.Unspecified,
    val baselineShift: BaselineShift? = null,
    val textGeometricTransform: TextGeometricTransform? = null,
    val localeList: LocaleList? = null,
    val background: Color = Color.Unspecified,
    val textDecoration: TextDecoration? = null,
    val shadow: Shadow? = null,
    val textAlign: TextAlign = TextAlign.Unspecified,
    val textDirection: TextDirection = TextDirection.Unspecified,
    val lineHeight: TextUnit = TextUnit.Unspecified,
    val textIndent: TextIndent? = null,
    val platformStyle: PlatformTextStyle? = null,
    val textMotion: TextMotion? = null,
) {
    fun copy(
        activeTextColor: ColorOrBrush = this.activeTextColor,
        inactiveTextColor: ColorOrBrush = this.inactiveTextColor,
        disabledActiveTextColor: ColorOrBrush = this.disabledActiveTextColor,
        disabledInactiveTextColor: ColorOrBrush = this.disabledInactiveTextColor,
        fontSize: TextUnit = this.fontSize,
        fontWeight: FontWeight? = this.fontWeight,
        fontStyle: FontStyle? = this.fontStyle,
        fontSynthesis: FontSynthesis? = this.fontSynthesis,
        fontFamily: FontFamily? = this.fontFamily,
        fontFeatureSettings: String? = this.fontFeatureSettings,
        letterSpacing: TextUnit = this.letterSpacing,
        baselineShift: BaselineShift? = this.baselineShift,
        textGeometricTransform: TextGeometricTransform? = this.textGeometricTransform,
        localeList: LocaleList? = this.localeList,
        background: Color = this.background,
        textDecoration: TextDecoration? = this.textDecoration,
        shadow: Shadow? = this.shadow,
        textAlign: TextAlign = this.textAlign,
        textDirection: TextDirection = this.textDirection,
        lineHeight: TextUnit = this.lineHeight,
        textIndent: TextIndent? = this.textIndent,
        platformStyle: PlatformTextStyle? = this.platformStyle,
        textMotion: TextMotion? = this.textMotion,
    ) = StepSliderTextStyle(
        activeTextColor = this.activeTextColor,
        inactiveTextColor = this.inactiveTextColor,
        disabledActiveTextColor = this.disabledActiveTextColor,
        disabledInactiveTextColor = this.disabledInactiveTextColor,
        fontSize = this.fontSize,
        fontWeight = this.fontWeight,
        fontStyle = this.fontStyle,
        fontSynthesis = this.fontSynthesis,
        fontFamily = this.fontFamily,
        fontFeatureSettings = this.fontFeatureSettings,
        letterSpacing = this.letterSpacing,
        baselineShift = this.baselineShift,
        textGeometricTransform = this.textGeometricTransform,
        localeList = this.localeList,
        background = this.background,
        textDecoration = this.textDecoration,
        shadow = this.shadow,
        textAlign = this.textAlign,
        textDirection = this.textDirection,
        lineHeight = this.lineHeight,
        textIndent = this.textIndent,
        platformStyle = this.platformStyle,
        textMotion = this.textMotion
    )

    @Stable
    internal fun textStyle(enabled: Boolean, active: Boolean) = TextStyle(
        fontSize = this.fontSize,
        fontWeight = this.fontWeight,
        fontStyle = this.fontStyle,
        fontSynthesis = this.fontSynthesis,
        fontFamily = this.fontFamily,
        fontFeatureSettings = this.fontFeatureSettings,
        letterSpacing = this.letterSpacing,
        baselineShift = this.baselineShift,
        textGeometricTransform = this.textGeometricTransform,
        localeList = this.localeList,
        background = this.background,
        textDecoration = this.textDecoration,
        shadow = this.shadow,
        textAlign = this.textAlign,
        textDirection = this.textDirection,
        lineHeight = this.lineHeight,
        textIndent = this.textIndent,
        platformStyle = this.platformStyle,
        textMotion = this.textMotion
    ).run {
        when(val textColor = textColor(enabled, active)){
            is ColorOrBrush.SingleColor -> {
                copy(color = textColor.color)
            }
            is ColorOrBrush.GradientBrush -> {
                copy(brush = textColor.brush)
            }
        }
    }
    @Stable
    internal fun textColor(enabled: Boolean, active: Boolean): ColorOrBrush =
        if (enabled) {
            if (active) activeTextColor else inactiveTextColor
        } else {
            if (active) disabledActiveTextColor else disabledInactiveTextColor
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is StepSliderTextStyle) return false

        if (activeTextColor != other.activeTextColor) return false
        if (inactiveTextColor != other.inactiveTextColor) return false
        if (disabledActiveTextColor != other.disabledActiveTextColor) return false
        if (disabledInactiveTextColor != other.disabledInactiveTextColor) return false
        if (fontSize != other.fontSize) return false
        if (fontWeight != other.fontWeight) return false
        if (fontStyle != other.fontStyle) return false
        if (fontSynthesis != other.fontSynthesis) return false
        if (fontFamily != other.fontFamily) return false
        if (fontFeatureSettings != other.fontFeatureSettings) return false
        if (letterSpacing != other.letterSpacing) return false
        if (baselineShift != other.baselineShift) return false
        if (textGeometricTransform != other.textGeometricTransform) return false
        if (localeList != other.localeList) return false
        if (background != other.background) return false
        if (textDecoration != other.textDecoration) return false
        if (shadow != other.shadow) return false
        if (textAlign != other.textAlign) return false
        if (textDirection != other.textDirection) return false
        if (lineHeight != other.lineHeight) return false
        if (textIndent != other.textIndent) return false
        if (platformStyle != other.platformStyle) return false
        if (textMotion != other.textMotion) return false

        return true
    }

    override fun hashCode(): Int {
        var result = activeTextColor.hashCode()
        result = 31 * result +inactiveTextColor.hashCode()
        result = 31 * result +disabledActiveTextColor.hashCode()
        result = 31 * result +disabledInactiveTextColor.hashCode()
        result = 31 * result +fontSize.hashCode()
        result = 31 * result +fontWeight.hashCode()
        result = 31 * result +fontStyle.hashCode()
        result = 31 * result +fontSynthesis.hashCode()
        result = 31 * result +fontFamily.hashCode()
        result = 31 * result +fontFeatureSettings.hashCode()
        result = 31 * result +letterSpacing.hashCode()
        result = 31 * result +baselineShift.hashCode()
        result = 31 * result +textGeometricTransform.hashCode()
        result = 31 * result +localeList.hashCode()
        result = 31 * result +background.hashCode()
        result = 31 * result +textDecoration.hashCode()
        result = 31 * result +shadow.hashCode()
        result = 31 * result +textAlign.hashCode()
        result = 31 * result +textDirection.hashCode()
        result = 31 * result +lineHeight.hashCode()
        result = 31 * result +textIndent.hashCode()
        result = 31 * result +platformStyle.hashCode()
        result = 31 * result +textMotion.hashCode()
        return result
    }
}
