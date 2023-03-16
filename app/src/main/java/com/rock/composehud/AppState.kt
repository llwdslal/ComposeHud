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

    class TransActivityAction(val clazz: Class<out Activity>):AppAction()
}

@Composable
fun rememberAppState(context: Context,coroutineScope: CoroutineScope) = remember {
    AppState(context)
}

class AppState(
    private val context:Context,
){
    fun dispatchAction(action: AppAction){
        when (action) {
            is AppAction.ToastMessage -> {
                HUD.toastMessage(action.message, Toast.LENGTH_LONG)
            }
            is AppAction.ShowLoading ->{
                HUD.showLoading()
            }
            is AppAction.Dismiss -> {
                HUD.dismiss()
            }
            is AppAction.TransActivityAction ->{
                context.startActivity(Intent(context,action.clazz))
            }
        }
    }
}