package com.rock.libcomposehud

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

private const val TAG = "HudComposables"
@Composable
internal fun LoadingHud(visible: Boolean ) { //想实现显示/隐藏动画，没成功 T_T
    val bgColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)

    val infiniteTransition = rememberInfiniteTransition()

    val rotate by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(300),
            repeatMode = RepeatMode.Restart
        )
    )

    Surface(
        color = bgColor,
        shape = RoundedCornerShape(4.dp),
    ) {
        Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            Image(
                painter = painterResource(id = R.drawable.loading),
                contentDescription = "loading",
                modifier = Modifier.graphicsLayer {
                    rotationZ = rotate
                }
            )
        }
    }
}

@Composable
internal fun ToastHud(msg:String){
    val bgColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)

    Surface(
        color = bgColor,
        shape = RoundedCornerShape(4.dp)
    ) {
        Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            Text(text = msg, modifier = Modifier.align(Alignment.Center), textAlign = TextAlign.Center)
        }
    }
}

