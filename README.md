# StepSlider

StepSlider is a customizable slider component for Jetpack Compose in Android, designed to allow users to select values within a predefined range with discrete steps.  
  
    
### Features
- Allows selection of values within a predefined range with discrete steps.
- Customizable appearance including track color, tick color, text color, and more.
- Support for enabling/disabling the slider.
- Option for vibrating feedback when sliding (Android device dependent).
- Easy-to-use API with customizable callbacks for value changes.


### Installation
To use StepSlider in your Android project, follow these steps:

1. Add the JitPack repository to your root build.gradle at the end of repositories:

```kotlin
repositories {
    maven("https://jitpack.io")
}
```
2. Add the dependency in your app-level build.gradle:
```kotlin
dependencies {
    implementation("com.github.jongmin1217:ComposeStepSlider:<latest_version>")
}
```


### Usage
Add the StepSlider composable to your UI layout and customize its appearance and behavior as needed:

```kotlin
@Composable
fun MyScreen() {
    var sliderValue by remember { mutableStateOf(0) }

    StepSlider(
        modifier = Modifier.padding(16.dp),
        value = sliderValue,
        onValueChange = { newValue ->
            sliderValue = newValue
        }
    )
}
```
For advanced customization, you can modify the 'color', 'textStyle', and other properties of the StepSlider.

```kotlin
StepSlider(
    modifier = Modifier.padding(16.dp),
    value = sliderValue,
    color = StepSliderColor(
        activeTrackColor = ColorOrBrush.SingleColor(Color.Red), // using color
        inactiveTrackColor = ColorOrBrush.GradientBrush(Brush.horizontalGradient(listOf(Color.White,Color.Red))), // using brush
        // Add more color customization here
    ),
    textStyle = StepSliderTextStyle(
        activeTextColor = ColorOrBrush.SingleColor(Color.Red), // using color
        inactiveTextColor = ColorOrBrush.GradientBrush(Brush.horizontalGradient(listOf(Color.White,Color.Red))), // using brush
        // Add more text style customization here
    ),
    // Add more customization properties here
    onValueChange = { newValue ->
        sliderValue = newValue
    }
)
```
## Documentation
### StepSlider
|Parameter|Data type|Description|default|
|:---|:---|:---|:---|
|xLabels| List<String> |This is a text list displayed at the bottom of the slider. The number of steps is created according to the number of lists.|List(5){"$it"}|
|color|StepSliderColor|Color settings for slider|StepSliderColor()|
|textStyle|StepSliderTextStyle|Set the style of the bottom text of the slider|StepSliderTextStyle()|
|enable|Boolean|enable of slider|true|
|tickSize|Dp|Diameter of slider tick|7.dp|
|barHeight|Dp|height of slider|8.dp|
|textTopMargin|Dp|Distance between slider and text|5.dp|
|isVibrate|Boolean|Whether to vibrate when the value is changed|true|
|showTick|Boolean|Whether the tick is exposed or not|true|
|showText|Boolean|Whether to expose text at the bottom of the slider|true|
|markerContent|@Composable () -> Unit|marker ui|DefaultMarker()|
|value|Int|Slider value||
|onValueChange|((Int) -> Unit)|Changed slider value callback||
|onDetailValueChange|((Float) -> Unit)?|A callback that returns a value according to the movement of the marker even if the step does not change.|null|

#### StepSliderColor
|Parameter|Data type|Description|default|
|:---|:---|:---|:---|
|activeTrackColor|ColorOrBrush|Color of activated slider bar|ColorOrBrush.SingleColor(Color.Red)|
|inactiveTrackColor|ColorOrBrush|Color of inactivated slider bar|ColorOrBrush.SingleColor(Color.LightGray)|
|activeTickColor|ColorOrBrush|Color of activated ticks|ColorOrBrush.SingleColor(Color.White)|
|inactiveTickColor|ColorOrBrush|Color of inactivated ticks|ColorOrBrush.SingleColor(Color.White)|
|disabledActiveTrackColor|ColorOrBrush|Color of active slider bar when disabled|ColorOrBrush.SingleColor(Color.Gray)|
|disabledInactiveTrackColor|ColorOrBrush|Color of inactive slider bar when disabled|ColorOrBrush.SingleColor(Color.LightGray)|
|disabledActiveTickColor|ColorOrBrush|Color of active tick when disabled|ColorOrBrush.SingleColor(Color.White)|
|disabledInactiveTickColor|ColorOrBrush|Color of inactive tick when disabled|ColorOrBrush.SingleColor(Color.White)|

### StepSliderColor
|Parameter|Data type|Description|default|
|:---|:---|:---|:---|
|activeTextColor|ColorOrBrush|Color of currently selected text|ColorOrBrush.SingleColor(Color.Red)|
|inactiveTextColor|ColorOrBrush|Color of text that is not currently selected|ColorOrBrush.SingleColor(Color.Gray)|
|disabledActiveTextColor|ColorOrBrush|Color of the currently selected text when the slider is inactive|ColorOrBrush.SingleColor(Color.Gray)|
|disabledInactiveTextColor|ColorOrBrush|Color of unselected text when slider is inactive|ColorOrBrush.SingleColor(Color.LightGray)|
|other||The remaining parameters are the same as TextStyle||

### ColorOrBrush
```kotlin
sealed class ColorOrBrush {
    data class SingleColor(val color: Color) : ColorOrBrush()
    data class GradientBrush(val brush: Brush) : ColorOrBrush()
}
```
#### using
```kotlin
ColorOrBrush.SingleColor(Color.Red) // using color
ColorOrBrush.GradientBrush(Brush.horizontalGradient(listOf(Color.White,Color.Red))) // using brush
```


# License
Compose SwipeBox is distributed under the terms of the Apache License (Version 2.0). See the [license](https://github.com/jongmin1217/ComposeStepSlider/blob/main/LICENSE) for more information.
