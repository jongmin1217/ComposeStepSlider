package com.bellminp.stepslider

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.setValue


class EditableOffset(private val initialOffset: Animatable<Float, AnimationVector1D>) {
    var offset by mutableStateOf(initialOffset)

    companion object {
        val Saver: Saver<EditableOffset, *> = listSaver(
            save = { listOf(it.initialOffset.value) },
            restore = {
                EditableOffset(Animatable(it[0]))
            }
        )
    }
}