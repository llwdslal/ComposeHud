package com.rock.libcomposehud.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import androidx.compose.runtime.RememberObserver
import kotlinx.coroutines.CoroutineScope

internal class LayoutWrapper(
    private val layout: HudLayoutImpl,
    private val parentContext: CompositionContext,
    val coroutineScope: CoroutineScope, // toast 功能使用的协程 scope
) : RememberObserver,HudLayout by layout{

    override fun setContent(parent: CompositionContext?,content: @Composable () -> Unit) {
        layout.setContent(parentContext,content)
    }

    //利用 RememberObserver 实现自动 dispose
    override fun onAbandoned() {
        dispose()
    }

    override fun onForgotten() {
        dispose()
    }

    override fun onRemembered() {}
}