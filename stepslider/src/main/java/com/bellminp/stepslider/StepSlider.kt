package com.bellminp.stepslider


import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.TypedValue
import androidx.activity.ComponentActivity
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.round

@Composable
fun StepSlider(
    modifier: Modifier = Modifier,
    value: Int,
    xLabels: List<String> = List(5){"$it"},
    color: StepSliderColor,
    textStyle: StepSliderTextStyle,
    enabled: Boolean,
    tickSize: Dp = 7.dp,
    barHeight: Dp = 8.dp,
    textTopMargin: Dp = 5.dp,
    markerContent: (@Composable () -> Unit) = { DefaultMarker() },
    isVibrate: Boolean = true,
    showTick: Boolean = true,
    showText: Boolean = true,
    onValueChange: ((Int) -> Unit),
    onDetailValueChange: ((Float) -> Unit)? = null,
) {

    val configuration = LocalConfiguration.current
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var markerWidth by remember { mutableIntStateOf(0) }
    var markerHeight by remember { mutableIntStateOf(0) }

    var screenWidth by remember { mutableFloatStateOf(configuration.screenWidthDp.dpToPixel(context)) }
    var endOffset by remember {
        mutableFloatStateOf(
            screenWidth - markerWidth.dpToPixel(context)
        )
    }

    val offsetX = remember { Animatable(endOffset / (xLabels.size - 1) * value) }

    val textMeasurerList = List(xLabels.size) { rememberTextMeasurer() }

    var textHeight by remember { mutableIntStateOf(0) }

    val xLabelSize by rememberUpdatedState(newValue = xLabels.size - 1)
    val dragEnable by rememberUpdatedState(newValue = enabled)


    LaunchedEffect(xLabelSize) {
        offsetX.snapTo(endOffset / xLabelSize * value)
        if (value > xLabelSize) {
            onValueChange(xLabelSize)
        }
    }


    LaunchedEffect(
        screenWidth,
        endOffset
    ) {
        offsetX.snapTo(endOffset / (xLabels.size - 1) * value)
    }

    LaunchedEffect(offsetX.value) {
        val interval = endOffset / (xLabels.size - 1)
        val detailValue = offsetX.value / interval
        onDetailValueChange?.invoke(detailValue)

        if (value != round(detailValue).toInt()) {
            if (isVibrate) vibrator(context)
            onValueChange(round(detailValue).toInt())
        }
    }

    Box(
        modifier = modifier.then(
            Modifier
                .pointerInput(Unit) {
                    detectTapGestures {
                        if(dragEnable){
                            scope.launch {
                                offsetX.animateTo(
                                    calculateNewOffset(
                                        it.x - (markerWidth / 2).dpToPixel(context),
                                        xLabelSize,
                                        endOffset
                                    )
                                )
                            }
                        }
                    }
                }
                .onGloballyPositioned {
                    screenWidth = it.size.width.toFloat()
                    endOffset = it.size.width.toFloat() - markerWidth.dpToPixel(context)
                    offsetX.updateBounds(0f, endOffset)
                }
                .background(Color.Transparent)
        )
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(
                    (max(
                        markerHeight,
                        barHeight.value.toInt()
                    ) + (if (showText) textHeight
                        .toFloat()
                        .pixelToDp(context) + textTopMargin.value else 0f)).dp
                )
                .padding(horizontal = (markerWidth / 2).dp)
                .align(Alignment.TopCenter)
                .background(Color.Transparent)
        ) {

            drawSliderList(
                colorOrBrush = color.trackColor(
                    enabled = enabled,
                    active = false
                ),
                start = Offset(0f, max((markerHeight / 2).dp, barHeight / 2).toPx()),
                end = Offset(size.width, max((markerHeight / 2).dp, barHeight / 2).toPx()),
                strokeWidth = barHeight.toPx()
            )

            drawSliderList(
                colorOrBrush = color.trackColor(
                    enabled = enabled,
                    active = true
                ),
                start = Offset(0f, max((markerHeight / 2).dp, barHeight / 2).toPx()),
                end = Offset(offsetX.value, max((markerHeight / 2).dp, barHeight / 2).toPx()),
                strokeWidth = barHeight.toPx()
            )

            if (showTick) {
                for (i in xLabels.indices) {
                    val start = Offset(
                        ((size.width / (xLabels.size - 1)) * i),
                        max((markerHeight / 2).dp, barHeight / 2).toPx()
                    )

                    val end = Offset(
                        ((size.width / (xLabels.size - 1)) * i),
                        max((markerHeight / 2).dp, barHeight / 2).toPx()
                    )

                    val strokeWidth = tickSize.toPx()

                    drawSliderList(
                        colorOrBrush = color.tickColor(
                            enabled = enabled,
                            active = value >= i
                        ),
                        start = start,
                        end = end,
                        strokeWidth = strokeWidth
                    )
                }
            }

            if (showText) {
                for (i in xLabels.indices) {
                    try {
                        val textLayoutResult = textMeasurerList[i].measure(
                            xLabels[i],
                            textStyle.textStyle(
                                enabled = enabled,
                                active = value == i
                            )
                        )

                        if (textHeight < textLayoutResult.size.height) textHeight =
                            textLayoutResult.size.height

                        drawText(
                            textLayoutResult = textLayoutResult,
                            topLeft = Offset(
                                ((size.width / (xLabels.size - 1)) * i) - (textLayoutResult.size.width / 2),
                                ((max(
                                    markerHeight,
                                    barHeight.value.toInt()
                                ) + textTopMargin.value).toInt().dpToPixel(context))
                            )
                        )
                    } catch (_: IllegalArgumentException) {
                    }
                }
            }
        }

        Box(modifier = Modifier
            .offset {
                IntOffset(
                    offsetX.value.toInt(),
                    if (barHeight.value > markerHeight) {
                        ((barHeight.value / 2) - (markerHeight / 2)).dp
                            .toPx()
                            .toInt()
                    } else 0
                )
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { _, dragAmount ->
                        if(dragEnable){
                            scope.launch {
                                offsetX.snapTo(offsetX.value + dragAmount.x)
                            }
                        }

                    },
                    onDragEnd = {
                        if(dragEnable){
                            scope.launch {
                                offsetX.animateTo(
                                    calculateNewOffset(
                                        offsetX.value,
                                        xLabelSize,
                                        endOffset
                                    )
                                )
                            }
                        }

                    }
                )
            }
            .onGloballyPositioned {
                markerWidth = it.size.width
                    .toFloat()
                    .pixelToDp(context)
                    .toInt()
                markerHeight = it.size.height
                    .toFloat()
                    .pixelToDp(context)
                    .toInt()
            }
        ) {
            markerContent()
        }
    }
}

fun calculateNewOffset(
    offsetX: Float,
    size: Int,
    endOffset: Float,
): Float {
    val cnt = size * 2
    val interval = endOffset / cnt
    for (i in 0 until cnt) {
        val start = (interval * i)
        val end = (interval * (i + 1))

        if (offsetX in start..end) {
            return if (i % 2 == 0) start else end
        }
        if (i == cnt - 1 && offsetX > end) return end
    }

    return 0f
}

fun DrawScope.drawSliderList(
    colorOrBrush: ColorOrBrush,
    start: Offset,
    end: Offset,
    strokeWidth: Float,
) {
    when (colorOrBrush) {
        is ColorOrBrush.SingleColor -> {
            drawLine(
                color = colorOrBrush.color,
                start = start,
                end = end,
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round
            )
        }

        is ColorOrBrush.GradientBrush -> {
            drawLine(
                brush = colorOrBrush.brush,
                start = start,
                end = end,
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round
            )
        }
    }
}

@Composable
fun DefaultMarker() {
    Canvas(modifier = Modifier.size(30.dp), onDraw = {
        val size = 30.dp.toPx()
        drawCircle(
            color = Color.Red,
            radius = size / 2f
        )
    })
}


fun Int.dpToPixel(context: Context): Float = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics
)

fun Float.pixelToDp(context: Context) = this / (context.resources.displayMetrics.densityDpi / 160f)


fun vibrator(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        (context.getSystemService(ComponentActivity.VIBRATOR_MANAGER_SERVICE) as VibratorManager).run {
            defaultVibrator.vibrate(
                VibrationEffect.createOneShot(10, 70)
            )
        }
    } else {
        val vibrator =
            context.getSystemService(ComponentActivity.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(10)
    }
}
