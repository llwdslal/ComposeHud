package com.rock.libcomposehud

import android.view.Gravity
import android.widget.Toast
import androidx.annotation.GravityInt
import androidx.annotation.IntRange
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import com.rock.libcomposehud.core.HudLayoutManager


object HUD {

    fun showLoading() {
        popup {
            LoadingHud()
        }
    }

    fun toastMessage(
        message: String,
        @IntRange(0, 1)duration: Int = Toast.LENGTH_SHORT,

    ) {
        toast(duration = duration) {
            ToastHud(msg = message)
        }
    }

    fun popup(
        isFocusable: Boolean = true,
        @GravityInt gravity: Int = Gravity.CENTER,
        offset: IntOffset = IntOffset.Zero,
        content: @Composable () -> Unit
    ) {
        HudLayoutManager.show(isFocusable, gravity, offset, content)
    }

    fun toast(
        @GravityInt gravity: Int = Gravity.CENTER,
        offset: IntOffset = IntOffset.Zero,
        @IntRange(0, 1) duration: Int = Toast.LENGTH_SHORT,
        content: @Composable () -> Unit,

    ) {
        HudLayoutManager.toast(gravity,offset,duration,content)
    }

    fun dismiss(){
        HudLayoutManager.dismiss()
    }
}









