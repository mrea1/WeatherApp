package com.mrea.weatherapp.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.getValue
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.Box
import androidx.ui.foundation.ContentGravity
import androidx.ui.foundation.Text
import androidx.ui.layout.Column
import androidx.ui.layout.fillMaxSize
import androidx.ui.layout.padding
import androidx.ui.layout.preferredSize
import androidx.ui.livedata.observeAsState
import androidx.ui.material.CircularProgressIndicator
import androidx.ui.material.TopAppBar
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import androidx.ui.viewmodel.viewModel
import com.mrea.weatherapp.MainUiState
import com.mrea.weatherapp.MainViewModel

var JETPACK_COMPOSE_ENABLED = false

fun AppCompatActivity.setComposeContent() {
    setContent {
        NextAppWeatherTheme {
            Content()
        }
    }
}

@Composable
fun Content() {
    val viewModel: MainViewModel = viewModel()
    val state by viewModel.uiStateLiveData.observeAsState(initial = MainUiState(isLoading = true))

    TopAppBar(title = { Text(text = "Current Weather") }, actions = {
        if (state.searchBox.isVisible) {
//            TextField(
//                value = ""
//            )
        } else {
//            IconButton(onClick = { state.showSearchBox = true }) {
//                Icon(imageResource(android.R.drawable.ic_menu_search))
//            }
        }
    })

    if (state.isLoading) {
        Loading()
    } else {
        Box(modifier = Modifier.fillMaxSize(), gravity = ContentGravity.Center) {
            Column {
                Text(text = state.currentLocation)
            }
        }
    }
}

@Composable
fun Loading() = Box(modifier = Modifier.fillMaxSize(), gravity = ContentGravity.Center) {
    CircularProgressIndicator(Modifier.preferredSize(50.dp).padding(4.dp))
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NextAppWeatherTheme {
        Content()
    }
}

