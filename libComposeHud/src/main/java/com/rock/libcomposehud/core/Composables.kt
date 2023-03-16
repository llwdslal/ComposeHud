package com.rock.libcomposehud.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalView
import java.util.*

@Composable
fun rememberHudLayout(){
    val composeView = LocalView.current
    if (HudLayoutManager.needNewLayout(composeView)){
        val parentContext = rememberCompositionContext()
        val layoutId = remember{ UUID.randomUUID()}
        val coroutineScope = rememberCoroutineScope()
        remember(parentContext,layoutId) {
            HudLayoutManager.newLayout(composeView,layoutId,parentContext,coroutineScope)
        }
    }
}
