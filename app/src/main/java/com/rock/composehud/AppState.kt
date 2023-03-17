package com.rock.composehud

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.rock.libcomposehud.HUD
import kotlinx.coroutines.CoroutineScope

sealed class AppAction{
    class ToastMessage(val message:String):AppAction()
    object ShowLoading:AppAction()
    object Dismiss:AppAction()

}

@Composable
fun rememberAppState() = remember {
    AppState()
}

class AppState(){
    fun dispatchAction(action: AppAction){
        when (action) {
            is AppAction.ToastMessage -> {
                HUD.toastMessage(action.message, Toast.LENGTH_LONG)
            }
            is AppAction.ShowLoading ->{
                HUD.showLoading(modal = false)
            }
            is AppAction.Dismiss -> {
                HUD.dismiss()
            }
        }
    }
}
