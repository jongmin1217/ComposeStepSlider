package com.bellminp.composestepslider

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.bellminp.composestepslider.ui.theme.ComposeStepSliderTheme
import com.bellminp.stepslider.StepSlider

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeStepSliderTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SampleScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun SampleScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        var value by remember { mutableIntStateOf(0) }
        var detailValue by remember { mutableFloatStateOf(0f) }
        var xLabels by remember { mutableStateOf(List(5) { "$it" }) }
        var trackColorActive by remember { mutableStateOf(Color.Red) }
        var trackColor by remember { mutableStateOf(Color.Gray) }
        var textColorActive by remember { mutableStateOf(Color.Red) }
        var textColor by remember { mutableStateOf(Color.Black) }
        var pointerColor by remember { mutableStateOf(Color.White) }
        var textSize by remember { mutableStateOf(20.dp) }
        var barHeight by remember { mutableStateOf(5.dp) }
        var textTopMargin by remember { mutableStateOf(3.dp) }
        var isVibrate by remember { mutableStateOf(true) }
        var showPointer by remember { mutableStateOf(true) }
        var showText by remember { mutableStateOf(true) }
        var markerColor by remember { mutableStateOf(Color.Red) }
        var textWeight by remember { mutableStateOf(W500) }

        val dpTypeList = listOf(
            DpType.TEXT_SIZE to textSize,
            DpType.BAR_HEIGHT to barHeight,
            DpType.TEXT_TOP_MARGIN to textTopMargin
        )

        StepSlider(
            modifier = Modifier
                .padding(vertical = 20.dp, horizontal = 20.dp),
            xLabels = xLabels,
            activeTrackColor = trackColorActive,
            inactiveTrackColor = trackColor,
            activeTextColor = textColorActive,
            inactiveTextColor = textColor,
            pointerColor = pointerColor,
            textSize = textSize,
            barHeight = barHeight,
            textTopMargin = textTopMargin,
            isVibrate = isVibrate,
            showPointer = showPointer,
            showText = showText,
            textFontWeight = textWeight,
            value = value,
            markerContent = {
                Marker(markerColor)
            },
            onValueChange = {
                value = it
            },
            onDetailValueChange = {
                detailValue = it
            }
        )

        SettingListContent(
            type = ListType.LABEL,
            init = xLabels,
            onNumberChange = {
                xLabels = it
            }
        )
        SettingDpContent(
            type = DpType.TEXT_SIZE,
            init = textSize,
            onNumberChange = {
                textSize = it
            }
        )
        SettingDpContent(
            type = DpType.BAR_HEIGHT,
            init = barHeight,
            onNumberChange = {
                barHeight = it
            }
        )
        SettingDpContent(
            type = DpType.TEXT_TOP_MARGIN,
            init = textTopMargin,
            onNumberChange = {
                textTopMargin = it
            }
        )
    }
}


@Composable
fun Marker(color: Color) {
    Canvas(modifier = Modifier.size(30.dp), onDraw = {
        val size = 30.dp.toPx()
        drawCircle(
            color = color,
            radius = size / 2f
        )
    })
}

@Composable
fun SettingListContent(
    modifier: Modifier = Modifier,
    type: ListType,
    init: List<String>,
    onNumberChange: (List<String>) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = stringResource(id = type.titleResId),
            modifier = Modifier
                .padding(top = 40.dp)
                .align(Alignment.CenterHorizontally),
            style = TextStyle(
                fontSize = 20.dp.textSp,
                fontWeight = FontWeight.W700
            )
        )

        SettingNumberContent(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            init = init.size,
            onClick = { size ->
                onNumberChange(List(size) { "$it" })
            }
        )
    }
}


@Composable
fun SettingDpContent(
    modifier: Modifier = Modifier,
    type: DpType,
    init: Dp,
    onNumberChange: (Dp) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = stringResource(id = type.titleResId),
            modifier = Modifier
                .padding(top = 30.dp)
                .align(Alignment.CenterHorizontally),
            style = TextStyle(
                fontSize = 20.dp.textSp,
                fontWeight = FontWeight.W700
            )
        )

        SettingNumberContent(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            init = init.value.toInt(),
            onClick = { size ->
                onNumberChange(size.dp)
            }
        )
    }
}

@Composable
fun SettingNumberContent(
    modifier: Modifier = Modifier,
    init: Int,
    onClick: (Int) -> Unit,
) {
    Row(
        modifier = modifier.padding(top = 20.dp)
    ) {
        IconButton(
            modifier = Modifier
                .size(40.dp)
                .border(
                    width = 1.dp,
                    color = Color.DarkGray,
                    shape = CircleShape
                )
                .align(Alignment.CenterVertically),
            onClick = {
                onClick(init.minus(1))
            }
        ) {
            Icon(imageVector = Icons.Rounded.KeyboardArrowDown, contentDescription = "down")
        }

        Text(
            text = init.toString(),
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .align(Alignment.CenterVertically)
        )

        IconButton(
            modifier = Modifier
                .size(40.dp)
                .border(
                    width = 1.dp,
                    color = Color.DarkGray,
                    shape = CircleShape
                )
                .align(Alignment.CenterVertically),
            onClick = {
                onClick(init.plus(1))
            }
        ) {
            Icon(imageVector = Icons.Rounded.KeyboardArrowUp, contentDescription = "up")
        }
    }

}


@Composable
fun SettingColorContent(
    modifier: Modifier = Modifier,
    type: ColorType,
    color: Color,
    onChangeColor: (Color) -> Unit,
) {

}


@Composable
fun SettingCheckContent(
    modifier: Modifier = Modifier,
    type: CheckType,
    value: Boolean,
    onValueChange: (Boolean) -> Unit,
) {


}

enum class ListType(@StringRes val titleResId: Int) {
    LABEL(R.string.x_labels)
}


enum class DpType(@StringRes val titleResId: Int) {
    TEXT_SIZE(R.string.text_Size),
    BAR_HEIGHT(R.string.bar_height),
    TEXT_TOP_MARGIN(R.string.text_top_margin)
}


enum class ColorType(@StringRes val titleResId: Int) {
    TRACK_COLOR_ACTIVE(R.string.track_color_active),
    TRACK_COLOR(R.string.track_color),
    TEXT_COLOR_ACTIVE(R.string.text_color_active),
    TEXT_COLOR(R.string.text_color),
    POINTER_COLOR(R.string.pointer_color)
}


enum class CheckType(@StringRes val titleResId: Int) {
    VIBRATOR(R.string.is_vibrate),
    POINTER(R.string.show_pointer),
    TEXT(R.string.show_text)
}


val Dp.textSp: TextUnit
    @Composable get() = with(LocalDensity.current) {
        this@textSp.toSp()
    }
