package com.rock.composehud

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

sealed class AppAction{
    class MessageAction(val message:String):AppAction()
}

@Composable
fun rememberAppState(coroutineScope: CoroutineScope) = remember {
    AppState(coroutineScope)
}

class AppState(
  private  val coroutineScope: CoroutineScope,
){
    fun dispatchAction(action: AppAction){
        when (action) {
            is AppAction.MessageAction -> {
                coroutineScope.launch {
                    //todo show Hud
                }
            }
        }
    }
}