package com.rock.composehud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rock.composehud.ui.theme.ComposeHudTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeHudTheme {
                App()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(){
    val appState = rememberAppState(coroutineScope = rememberCoroutineScope())

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Title")},
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Cyan)
            )
        },
        bottomBar = {
            BottomAppBar {
                NavigationBarItem(selected = true, onClick = {  }, icon = {Icon(Icons.Default.Home,"")})
                NavigationBarItem(selected = true, onClick = {  }, icon = {Icon(Icons.Default.Face,"")})
                NavigationBarItem(selected = true, onClick = {  }, icon = {Icon(Icons.Default.Favorite,"")})
                NavigationBarItem(selected = true, onClick = {  }, icon = {Icon(Icons.Default.Email,"")})
            }
        },
        floatingActionButton = {
            Column() {
                Button(onClick = {
                    appState.dispatchAction(AppAction.MessageAction("Show Message"))
                }) {
                    Text(text = "Show Message")
                }
                Button(onClick = {  }) {
                    Text(text = "Show Message2")
                }
            }
        }

    ) {
        LazyColumn(
            modifier = Modifier.padding(it),
            contentPadding = PaddingValues(4.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
           items(20){ index ->
               ListItem(
                   modifier = Modifier.fillMaxWidth(),
                   headlineText = { Text(text = "Item $index")},
                   colors = ListItemDefaults.colors(
                       containerColor = Color.LightGray
                   )
               )
           }
        }
    }
}

@Preview
@Composable
fun AppPreview(){
    App()
}

