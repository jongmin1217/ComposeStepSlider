package com.bellminp.stepslider


import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import android.util.TypedValue
import androidx.activity.ComponentActivity
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import java.lang.IndexOutOfBoundsException
import kotlin.math.floor
import kotlin.math.log
import kotlin.math.round

@Composable
fun StepSlider(
    modifier: Modifier = Modifier,
    value: Int,
    xLabels: List<String> = List(5){"$it"},
    trackColorActive: Color = Color.Red,
    trackColor: Color = Color.LightGray,
    textColorActive: Color = Color.Red,
    textColor: Color = Color.Black,
    pointerColor: Color = Color.White,
    textSize: Dp = 13.dp,
    barHeight: Dp = 8.dp,
    textTopMargin : Dp = 5.dp,
    markerContent: (@Composable () -> Unit) = { DefaultMarker() },
    textTypefacePath: String? = null,
    isVibrate: Boolean = true,
    showPointer: Boolean = true,
    showText: Boolean = true,
    onValueChange: ((Int) -> Unit),
    onDetailValueChange: ((Float) -> Unit)? = null
) {


    val configuration = LocalConfiguration.current
    val context = LocalContext.current
    val density = LocalDensity.current

    val scope = rememberCoroutineScope()
    var markerSize by remember { mutableStateOf(Size(0f, 0f)) }
    var screenWidth by remember { mutableFloatStateOf(configuration.screenWidthDp.dpToPixel(context)) }
    var endOffset by remember {
        mutableFloatStateOf(
            screenWidth - markerSize.width.toInt().dpToPixel(context)
        )
    }

    var currentStep by rememberSaveable { mutableIntStateOf(value) }
    val offsetX = rememberSaveable(saver = EditableOffset.Saver) {
        EditableOffset(Animatable(endOffset / (xLabels.size - 1) * currentStep))
    }

    var halfWidth by remember { mutableIntStateOf(0) }
    var halfHeight by remember { mutableIntStateOf(0) }


    @Composable
    fun textPaint(textColor: Int) = remember(density) {
        Paint().apply {
            color = textColor
            textAlign = Paint.Align.CENTER
            this.textSize = density.run { textSize.toPx() }
            isAntiAlias = true
            textTypefacePath?.let {
                Typeface.createFromAsset(context.resources.assets, it).run {
                    typeface = Typeface.create(this, Typeface.NORMAL)
                }
            }
        }
    }

    val slideTextPaintBasic = textPaint(textColor.toArgb())
    val slideTextPaintSelect = textPaint(textColorActive.toArgb())

    fun calculateNewOffset(offsetX: Float): Float {
        val cnt = (xLabels.size - 1) * 2
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

    LaunchedEffect(markerSize) {
        halfWidth = (markerSize.width.toInt() / 2)
        halfHeight = (markerSize.height.toInt() / 2)
    }


    LaunchedEffect(
        screenWidth,
        endOffset
    ) {
        offsetX.offset.snapTo(endOffset / (xLabels.size - 1) * currentStep)
    }

    LaunchedEffect(offsetX.offset.value) {
        val interval = endOffset / (xLabels.size - 1)
        val detailValue = offsetX.offset.value / interval
        onDetailValueChange?.invoke(detailValue)

        if (currentStep != round(detailValue).toInt()) {
            currentStep = round(detailValue).toInt()
            if (isVibrate) vibrator(context)
            onValueChange(currentStep)
        }
    }

    Box(
        modifier = modifier.then(
            Modifier
                .height((markerSize.height + (if (showText) textSize.value + textTopMargin.value else 0f) + 3).dp)
                .pointerInput(Unit) {
                    detectTapGestures {
                        scope.launch {
                            offsetX.offset.animateTo(
                                calculateNewOffset(it.x - halfWidth.dpToPixel(context))
                            )
                        }
                    }
                }
                .onGloballyPositioned {
                    screenWidth = it.size.width.toFloat()
                    endOffset = it.size.width.toFloat() - markerSize.width
                        .toInt()
                        .dpToPixel(context)
                    offsetX.offset.updateBounds(0f, endOffset)
                }
        )
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = halfWidth.dp)
                .align(Alignment.TopCenter)
        ) {

            drawLine(
                color = trackColor,
                start = Offset(0f, halfHeight.dp.toPx()),
                end = Offset(size.width, halfHeight.dp.toPx()),
                strokeWidth = barHeight.toPx(),
                cap = StrokeCap.Round
            )

            drawLine(
                color = trackColorActive,
                start = Offset(0f, halfHeight.dp.toPx()),
                end = Offset(offsetX.offset.value, halfHeight.dp.toPx()),
                strokeWidth = barHeight.toPx(),
                cap = StrokeCap.Round
            )

            if (showPointer || showText) {
                for (i in xLabels.indices) {
                    if (showText) {
                        try {
                            xLabels[i]
                        } catch (e: IndexOutOfBoundsException) {
                            String()
                        }.run {
                            drawContext.canvas.nativeCanvas.drawText(
                                this,
                                ((size.width / (xLabels.size - 1)) * i),
                                (markerSize.height + textSize.value + textTopMargin.value).toInt().dpToPixel(context),
                                if (i == currentStep) slideTextPaintSelect else slideTextPaintBasic
                            )
                        }
                    }

                    if (showPointer) {
                        drawLine(
                            color = pointerColor,
                            start = Offset(
                                ((size.width / (xLabels.size - 1)) * i),
                                halfHeight.dp.toPx()
                            ),
                            end = Offset(
                                ((size.width / (xLabels.size - 1)) * i),
                                halfHeight.dp.toPx()
                            ),
                            strokeWidth = (barHeight - 1.dp).toPx(),
                            cap = StrokeCap.Round
                        )
                    }
                }
            }
        }

        Box(modifier = Modifier
            .offset { IntOffset(offsetX.offset.value.toInt(), 0) }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { _, dragAmount ->
                        scope.launch {
                            offsetX.offset.snapTo(offsetX.offset.value + dragAmount.x)
                        }
                    },
                    onDragEnd = {
                        scope.launch {
                            offsetX.offset.animateTo(calculateNewOffset(offsetX.offset.value))
                        }
                    }
                )
            }
            .onGloballyPositioned {
                markerSize = Size(
                    it.size.width
                        .toFloat()
                        .pixelToDp(context),
                    it.size.height
                        .toFloat()
                        .pixelToDp(context)
                )
            }
        ) {
            markerContent()
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