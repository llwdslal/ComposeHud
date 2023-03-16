package com.rock.libcomposehud.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import androidx.compose.runtime.RememberObserver
import kotlinx.coroutines.CoroutineScope

internal class LayoutWrapper(
    private val layout: HudLayoutImpl,
    private val parentContext: CompositionContext,
    val coroutineScope: CoroutineScope,
) : RememberObserver,HudLayout by layout{

    override fun setContent(content: @Composable () -> Unit, parent: CompositionContext?) {
        layout.setContent(content,parentContext)
    }

    override fun onAbandoned() {
        dispose()
    }

    override fun onForgotten() {
        dispose()
    }

    override fun onRemembered() {}
}