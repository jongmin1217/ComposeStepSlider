package com.bellminp.composestepslider

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.bellminp.composestepslider.ui.theme.ComposeStepSliderTheme
import com.bellminp.stepslider.ColorOrBrush
import com.bellminp.stepslider.StepSlider
import com.bellminp.stepslider.StepSliderColor
import com.bellminp.stepslider.StepSliderTextStyle

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
        var value by rememberSaveable { mutableIntStateOf(2) }
        var detailValue by remember { mutableFloatStateOf(0f) }
        var xLabels by remember { mutableStateOf(List(5) { "$it" }) }
        var textSize by remember { mutableStateOf(13.dp) }
        var tickSize by remember { mutableStateOf(7.dp) }
        var barHeight by remember { mutableStateOf(8.dp) }
        var textTopMargin by remember { mutableStateOf(3.dp) }
        var activeTrackColor by remember { mutableStateOf(Color.Red) }
        var inactiveTrackColor by remember { mutableStateOf(Color.LightGray) }
        var activeTextColor by remember { mutableStateOf(Color.Red) }
        var inactiveTextColor by remember { mutableStateOf(Color.Black) }
        var activeTickColor by remember { mutableStateOf(Color.Red) }
        var inactiveTickColor by remember { mutableStateOf(Color.White) }
        var disabledActiveTrackColor by remember { mutableStateOf(Color.Gray) }
        var disabledInactiveTrackColor by remember { mutableStateOf(Color.LightGray) }
        var disabledActiveTextColor by remember { mutableStateOf(Color.Gray) }
        var disabledInactiveTextColor by remember { mutableStateOf(Color.LightGray) }
        var disabledActiveTickColor by remember { mutableStateOf(Color.Black) }
        var disabledInactiveTickColor by remember { mutableStateOf(Color.White) }
        var markerColor by remember { mutableStateOf(Color.Red) }
        var disableMarkerColor by remember { mutableStateOf(Color.Gray) }
        var isVibrate by remember { mutableStateOf(true) }
        var showTick by remember { mutableStateOf(true) }
        var showText by remember { mutableStateOf(true) }
        var enable by remember { mutableStateOf(true) }


        val numberList = listOf(
            SettingType.LABEL to xLabels,
            SettingType.TEXT_SIZE to textSize,
            SettingType.TICK_SIZE to tickSize,
            SettingType.BAR_HEIGHT to barHeight,
            SettingType.TEXT_TOP_MARGIN to textTopMargin
        )

        val colorList = listOf(
            SettingType.ACTIVE_TRACK_COLOR to activeTrackColor,
            SettingType.INACTIVE_TRACK_COLOR to inactiveTrackColor,
            SettingType.ACTIVE_TEXT_COLOR to activeTextColor,
            SettingType.INACTIVE_TEXT_COLOR to inactiveTextColor,
            SettingType.ACTIVE_TICK_COLOR to activeTickColor,
            SettingType.INACTIVE_TICK_COLOR to inactiveTickColor,
            SettingType.DISABLE_ACTIVE_TRACK_COLOR to disabledActiveTrackColor,
            SettingType.DISABLE_INACTIVE_TRACK_COLOR to disabledInactiveTrackColor,
            SettingType.DISABLE_ACTIVE_TEXT_COLOR to disabledActiveTextColor,
            SettingType.DISABLE_INACTIVE_TEXT_COLOR to disabledInactiveTextColor,
            SettingType.DISABLE_ACTIVE_TICK_COLOR to disabledActiveTickColor,
            SettingType.DISABLE_INACTIVE_TICK_COLOR to disabledInactiveTickColor,
            SettingType.MARKER_COLOR to markerColor,
            SettingType.DISABLE_MARKER_COLOR to disableMarkerColor
        )

        val switchList = listOf(
            SettingType.TEXT to showText,
            SettingType.TICK to showTick,
            SettingType.VIBRATOR to isVibrate,
            SettingType.ENABLE to enable
        )

        LaunchedEffect(value) {
            Log.d("qweqwe", "$value")
        }
        ColorOrBrush.GradientBrush(Brush.sweepGradient(listOf(Color.White, Color.Red)))
        StepSlider(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(top = 40.dp),
            xLabels = xLabels,
            color = StepSliderColor(
                activeTrackColor = ColorOrBrush.GradientBrush(
                    Brush.horizontalGradient(
                        listOf(
                            Color.White,
                            Color(0xffFFB5BA),
                            Color(0xffFF0015)
                        )
                    )
                ),
                inactiveTrackColor = ColorOrBrush.SingleColor(inactiveTrackColor),
                activeTickColor = ColorOrBrush.SingleColor(activeTickColor),
                inactiveTickColor = ColorOrBrush.SingleColor(inactiveTickColor),
                disabledActiveTrackColor = ColorOrBrush.SingleColor(disabledActiveTrackColor),
                disabledInactiveTrackColor = ColorOrBrush.SingleColor(disabledInactiveTrackColor),
                disabledActiveTickColor = ColorOrBrush.SingleColor(disabledActiveTickColor),
                disabledInactiveTickColor = ColorOrBrush.SingleColor(disabledInactiveTickColor),
            ),
            textStyle = StepSliderTextStyle(
                activeTextColor = ColorOrBrush.SingleColor(activeTextColor),
                inactiveTextColor = ColorOrBrush.SingleColor(inactiveTextColor),
                disabledActiveTextColor = ColorOrBrush.SingleColor(disabledActiveTextColor),
                disabledInactiveTextColor = ColorOrBrush.SingleColor(disabledInactiveTextColor),
                fontSize = textSize.textSp
            ),
            enabled = enable,
            tickSize = tickSize,
            barHeight = barHeight,
            textTopMargin = textTopMargin,
            isVibrate = isVibrate,
            showTick = showTick,
            showText = showText,
            value = value,
            markerContent = {
                Marker(if (enable) markerColor else disableMarkerColor)
            },
            onValueChange = {
                value = it
            },
            onDetailValueChange = {
                detailValue = it
            }
        )

        ValueContent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 20.dp),
            value = value,
            detailValue = detailValue
        )

        repeat(numberList.size) { index ->
            SettingNumberContent(
                type = numberList[index].first,
                init = when (val data = numberList[index].second) {
                    is List<*> -> data.size
                    is Dp -> data.value.toInt()
                    else -> 0
                },
                onClick = { size ->
                    when (numberList[index].first) {
                        SettingType.LABEL -> {
                            if (size > 1) {
                                xLabels = List(size) { "$it" }
                            }
                        }

                        SettingType.TEXT_SIZE -> {
                            if (size > 0) {
                                textSize = size.dp
                            }
                        }

                        SettingType.TICK_SIZE -> {
                            if (size > 0) {
                                tickSize = size.dp
                            }
                        }

                        SettingType.BAR_HEIGHT -> {
                            if (size > 0) {
                                barHeight = size.dp
                            }
                        }

                        else -> {
                            if (size > 0) {
                                textTopMargin = size.dp
                            }
                        }
                    }
                }
            )
        }

        repeat(colorList.size) {
            SettingColorContent(
                type = colorList[it].first,
                color = colorList[it].second,
                onChangeColor = { color ->
                    when (colorList[it].first) {
                        SettingType.ACTIVE_TRACK_COLOR -> activeTrackColor = color
                        SettingType.INACTIVE_TRACK_COLOR -> inactiveTrackColor = color
                        SettingType.ACTIVE_TEXT_COLOR -> activeTextColor = color
                        SettingType.INACTIVE_TEXT_COLOR -> inactiveTextColor = color
                        SettingType.ACTIVE_TICK_COLOR -> activeTickColor = color
                        SettingType.INACTIVE_TICK_COLOR -> inactiveTickColor = color
                        SettingType.DISABLE_ACTIVE_TRACK_COLOR -> disabledActiveTrackColor = color
                        SettingType.DISABLE_INACTIVE_TRACK_COLOR -> disabledInactiveTrackColor =
                            color

                        SettingType.DISABLE_ACTIVE_TEXT_COLOR -> disabledActiveTextColor = color
                        SettingType.DISABLE_INACTIVE_TEXT_COLOR -> disabledInactiveTextColor = color
                        SettingType.DISABLE_ACTIVE_TICK_COLOR -> disabledActiveTickColor = color
                        SettingType.DISABLE_INACTIVE_TICK_COLOR -> disabledInactiveTickColor = color
                        SettingType.MARKER_COLOR -> markerColor = color
                        else -> disableMarkerColor = color
                    }
                }
            )
        }

        LazyHorizontalGrid(
            rows = GridCells.Fixed(2),
            modifier = Modifier
                .height(160.dp)
                .padding(horizontal = 20.dp)
                .padding(top = 40.dp)
        ) {
            items(
                items = switchList,
                key = { it.first },
                itemContent = { item: Pair<SettingType, Boolean> ->
                    Row(
                        modifier = Modifier
                            .height(60.dp)
                            .padding(start = 20.dp)
                    ) {
                        Text(
                            text = stringResource(id = item.first.titleResId),
                            style = TextStyle(
                                fontSize = 15.dp.textSp,
                                fontWeight = W700
                            ),
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .width(100.dp)
                        )

                        Switch(
                            checked = item.second,
                            onCheckedChange = {
                                when (item.first) {
                                    SettingType.TEXT -> showText = it
                                    SettingType.TICK -> showTick = it
                                    SettingType.VIBRATOR -> isVibrate = it
                                    else -> enable = it
                                }
                            }
                        )
                    }
                }
            )
        }
    }
}


@Composable
fun Marker(color: Color) {
    Image(
        painter = painterResource(id = R.drawable.img_icon_dog),
        contentDescription = "",
        modifier = Modifier.size(60.dp)
    )
}

@Composable
fun ValueContent(
    modifier: Modifier = Modifier,
    value: Int,
    detailValue: Float,
) {
    Row(
        modifier = modifier.padding(top = 10.dp)
    ) {
        repeat(2) {
            Box(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${stringResource(id = if (it == 0) R.string.value else R.string.detail_value)} : ${if (it == 0) value else detailValue}",
                    modifier = Modifier.align(Alignment.Center),
                    style = TextStyle(
                        fontSize = 15.dp.textSp,
                        fontWeight = W700
                    )
                )
            }
        }
    }
}

@Composable
fun SettingNumberContent(
    modifier: Modifier = Modifier,
    type: SettingType,
    init: Int,
    onClick: (Int) -> Unit,
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

        Row(
            modifier = Modifier
                .padding(top = 20.dp)
                .align(Alignment.CenterHorizontally)
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
                text = init.toString() + if (type != SettingType.LABEL) "dp" else "",
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
}


@Composable
fun SettingColorContent(
    modifier: Modifier = Modifier,
    type: SettingType,
    color: Color,
    onChangeColor: (Color) -> Unit,
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
                fontWeight = W700
            )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .horizontalScroll(rememberScrollState())
        ) {
            val colorList = listOf(
                Color.Black,
                Color.DarkGray,
                Color.Gray,
                Color.LightGray,
                Color.White,
                Color.Red,
                Color.Green,
                Color.Blue,
                Color.Yellow,
                Color.Cyan,
                Color.Magenta
            )

            repeat(colorList.size) {
                val isSelect = colorList[it] == color

                Box(
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .border(
                            width = 3.dp,
                            color = if (isSelect) {
                                if (colorList[it] == Color.Red) Color.Black
                                else Color.Red
                            } else Color.Transparent
                        )
                        .clickable {
                            onChangeColor(colorList[it])
                        }
                ) {
                    Spacer(
                        modifier = Modifier
                            .size(40.dp)
                            .background(colorList[it])
                    )
                }
            }
        }
    }
}


enum class SettingType(@StringRes val titleResId: Int) {
    LABEL(R.string.x_labels),
    TICK_SIZE(R.string.tick_Size),
    TEXT_SIZE(R.string.text_Size),
    BAR_HEIGHT(R.string.bar_height),
    TEXT_TOP_MARGIN(R.string.text_top_margin),
    ACTIVE_TRACK_COLOR(R.string.active_track_color),
    INACTIVE_TRACK_COLOR(R.string.inactive_track_color),
    ACTIVE_TEXT_COLOR(R.string.active_text_color),
    INACTIVE_TEXT_COLOR(R.string.inactive_text_color),
    ACTIVE_TICK_COLOR(R.string.active_tick_color),
    INACTIVE_TICK_COLOR(R.string.inactive_tick_color),
    DISABLE_ACTIVE_TRACK_COLOR(R.string.disable_active_track_color),
    DISABLE_INACTIVE_TRACK_COLOR(R.string.disable_inactive_track_color),
    DISABLE_ACTIVE_TEXT_COLOR(R.string.disable_active_text_color),
    DISABLE_INACTIVE_TEXT_COLOR(R.string.disable_inactive_text_color),
    DISABLE_ACTIVE_TICK_COLOR(R.string.disable_active_tick_color),
    DISABLE_INACTIVE_TICK_COLOR(R.string.disable_inactive_tick_color),
    MARKER_COLOR(R.string.marker_color),
    DISABLE_MARKER_COLOR(R.string.disable_marker_color),
    VIBRATOR(R.string.is_vibrate),
    TICK(R.string.show_tick),
    TEXT(R.string.show_text),
    ENABLE(R.string.enable)
}


val Dp.textSp: TextUnit
    @Composable get() = with(LocalDensity.current) {
        this@textSp.toSp()
    }
