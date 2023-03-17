package com.rock.libcomposehud.core

import android.content.Context
import android.graphics.PixelFormat
import android.os.IBinder
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.compose.ui.R
import android.view.WindowManager
import androidx.annotation.GravityInt
import androidx.compose.runtime.*
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.unit.IntOffset
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.lifecycle.ViewTreeViewModelStoreOwner
import androidx.savedstate.findViewTreeSavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import java.util.UUID

private const val TAG = "HudLayout"


interface HudLayout {
    val composeViewWindowToken: IBinder
    val isShowing: State<Boolean>

    fun setContent(parent: CompositionContext? = null, content: @Composable () -> Unit)

    fun show()
    fun dismiss()
    fun dispose()

    fun resetLayoutParams()
    fun setIsFocusable(isFocusable: Boolean)
    fun setLayoutGravity(@GravityInt gravity: Int)
    fun setLayoutOffset(offset: IntOffset)
}


internal class HudLayoutImpl(
    composeView: View,
    layoutId: UUID,
) : AbstractComposeView(composeView.context), HudLayout {

    override val composeViewWindowToken: IBinder = composeView.applicationWindowToken

    init {
        ViewTreeLifecycleOwner.set(this, ViewTreeLifecycleOwner.get(composeView))
        ViewTreeViewModelStoreOwner.set(this, ViewTreeViewModelStoreOwner.get(composeView))
        setViewTreeSavedStateRegistryOwner(composeView.findViewTreeSavedStateRegistryOwner())
        setTag(R.id.compose_view_saveable_id_tag, "HudLayout:$layoutId")
    }


    private var _showing = mutableStateOf(false)

    override val isShowing: State<Boolean>
        get() = _showing

    private val windowManager =
        composeView.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    private var windowParams = createLayoutParams()

    private var content: @Composable () -> Unit by mutableStateOf({})

    override var shouldCreateCompositionOnAttachedToWindow: Boolean = false
        private set

    @Composable
    override fun Content() {
        content()
    }

    override fun setContent(parent: CompositionContext?, content: @Composable () -> Unit) {

        parent?.let {
            setParentCompositionContext(it)
        }

        shouldCreateCompositionOnAttachedToWindow = true

        this.content = content

        if (isAttachedToWindow) {
            createComposition()
        }
    }

    override fun show() {
        if (_showing.value) {
            dismiss()
        }
        _showing.value = true
        windowManager.addView(this, windowParams)

    }

    override fun dismiss() {
        if (!_showing.value) return
        _showing.value = false
        disposeComposition()
        windowManager.removeViewImmediate(this)
    }

    override fun dispose() {
        disposeComposition()
        ViewTreeLifecycleOwner.set(this, null)
        ViewTreeViewModelStoreOwner.set(this, null)
        setViewTreeSavedStateRegistryOwner(null)
        if (_showing.value) {
            windowManager.removeViewImmediate(this)
        }
        Log.e(TAG, "dispose: $composeViewWindowToken")
    }

    override fun resetLayoutParams() {
        windowParams = createLayoutParams()
    }

    private fun createLayoutParams(): WindowManager.LayoutParams {
        return WindowManager.LayoutParams().apply {
            gravity = Gravity.CENTER

            type = WindowManager.LayoutParams.TYPE_APPLICATION_SUB_PANEL
            token = composeViewWindowToken

            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            format = PixelFormat.TRANSLUCENT
        }
    }

    override fun setIsFocusable(isFocusable: Boolean) {
        windowParams.flags = if (!isFocusable) {
            windowParams.flags or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        } else {
            windowParams.flags and (WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE.inv())
        }
    }

    override fun setLayoutGravity(@GravityInt gravity: Int) {
        windowParams.gravity = gravity
    }

    override fun setLayoutOffset(offset: IntOffset) {
        windowParams.x = offset.x
        windowParams.y = offset.y
    }

}





