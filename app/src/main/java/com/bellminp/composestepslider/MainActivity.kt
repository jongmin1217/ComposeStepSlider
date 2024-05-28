package com.bellminp.composestepslider

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
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
            .verticalScroll(rememberScrollState())
    ) {
        var value by remember { mutableIntStateOf(0) }
        var detailValue by remember { mutableFloatStateOf(0f) }
        var xLabels by remember { mutableStateOf(List(5){"$it"}) }
        var trackColorActive by remember { mutableStateOf(Color.Red) }
        var trackColor by remember { mutableStateOf(Color.Gray) }
        var textColorActive by remember { mutableStateOf(Color.Red) }
        var textColor by remember { mutableStateOf(Color.Black) }
        var pointerColor by remember { mutableStateOf(Color.White) }
        var textSize by remember { mutableStateOf(13.dp) }
        var barHeight by remember { mutableStateOf(5.dp) }
        var textTopMargin by remember { mutableStateOf(3.dp) }
        var isVibrate by remember { mutableStateOf(true) }
        var showPointer by remember { mutableStateOf(true) }
        var showText by remember { mutableStateOf(true) }
        var markerColor by remember { mutableStateOf(Color.Red) }

        LaunchedEffect(key1 = value) {
            Log.d("qweqwe",value.toString())
        }

        StepSlider(
            modifier = Modifier
                .padding(top = 40.dp)
                .padding(horizontal = 20.dp),
            xLabels = xLabels,
            trackColorActive = trackColorActive,
            trackColor = trackColor,
            textColorActive = textColorActive,
            textColor = textColor,
            pointerColor = pointerColor,
            textSize = textSize,
            barHeight = barHeight,
            textTopMargin = textTopMargin,
            isVibrate = isVibrate,
            showPointer = showPointer,
            showText = showText,
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
fun <T : Any> SettingNumberContent(
    modifier: Modifier = Modifier,
    type : NumberType,
    init : T,
    onNumberChange : (T) -> Unit
){

}


@Composable
fun SettingColorContent(
    modifier: Modifier = Modifier,
    type : ColorType,
    color: Color,
    onChangeColor : (Color) -> Unit
){

}


@Composable
fun SettingCheckContent(
    modifier: Modifier = Modifier,
    type : CheckType,
    value : Boolean,
    onValueChange : (Boolean) -> Unit
){

}

enum class NumberType(@StringRes val titleResId : Int){
    LABEL(R.string.x_labels),
    TEXT_SIZE(R.string.text_Size),
    BAR_HEIGHT(R.string.bar_height),
    TEXT_TOP_MARGIN(R.string.text_top_margin)
}


enum class ColorType(@StringRes val titleResId : Int){
    TRACK_COLOR_ACTIVE(R.string.track_color_active),
    TRACK_COLOR(R.string.track_color),
    TEXT_COLOR_ACTIVE(R.string.text_color_active),
    TEXT_COLOR(R.string.text_color),
    POINTER_COLOR(R.string.pointer_color)
}


enum class CheckType(@StringRes val titleResId : Int){
    VIBRATOR(R.string.is_vibrate),
    POINTER(R.string.show_pointer),
    TEXT(R.string.show_text)
}
