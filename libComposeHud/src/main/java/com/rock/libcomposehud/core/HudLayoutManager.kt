package com.rock.libcomposehud.core

import android.view.Gravity
import android.view.View
import androidx.annotation.GravityInt
import androidx.annotation.IntRange
import androidx.compose.runtime.*
import androidx.compose.ui.unit.IntOffset
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.findViewTreeLifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID


internal object HudLayoutManager {

    private val layouts = mutableListOf<LayoutWrapper>()
    private var currentLayout: HudLayout? = null

    fun show(
        isFocusable: Boolean = true,
        @GravityInt gravity: Int = Gravity.CENTER,
        offset: IntOffset = IntOffset.Zero,
        content: @Composable (State<Boolean>) -> Unit
    ) {
        currentLayout?.let {
            if (it.isShowing.value) {
                it.dismiss()
            }
            it.setContent{
                content(it.isShowing)
            }
            it.resetLayoutParams()
            it.setIsFocusable(isFocusable)
            it.setLayoutGravity(gravity)
            it.setLayoutOffset(offset)
            it.show()
        }
    }

    fun toast(
        @GravityInt gravity: Int = Gravity.CENTER,
        offset: IntOffset = IntOffset.Zero,
        @IntRange(0, 1) duration: Int = 0,
        content: @Composable (State<Boolean>) -> Unit
    ) {
        show(false,gravity,offset,content)
        (currentLayout as LayoutWrapper?)?.let {
            it.coroutineScope.launch {
                val delay = if (duration == 0) 500L else 1_000L
                delay(delay)
                dismiss()
            }
        }
    }

    fun dismiss() {
        currentLayout?.dismiss()
    }

    fun needNewLayout(view: View): Boolean {
        val token = view.applicationWindowToken
        return layouts.find { it.composeViewWindowToken == token } == null
    }

    fun newLayout(
        composeView: View,
        layoutID: UUID,
        parentContext: CompositionContext,
        coroutineScope: CoroutineScope
    ): LayoutWrapper {

        val layout = HudLayoutImpl(composeView, layoutID)
        val wrapper = LayoutWrapper(layout, parentContext, coroutineScope)
        composeView.findViewTreeLifecycleOwner()!!.lifecycle.addObserver(object :
            LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event == Lifecycle.Event.ON_RESUME) {
                    currentLayout = wrapper
                } else if (event == Lifecycle.Event.ON_DESTROY) {
                    layouts.remove(wrapper)
                    if (currentLayout == wrapper) {
                        currentLayout = null
                    }
                }
            }
        })

        layouts.add(wrapper)
        return wrapper
    }
}






