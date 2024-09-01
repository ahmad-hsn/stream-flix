package com.streamflix.ui.screens.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.medialibrary.network.Resource
import com.streamflix.R
import com.streamflix.viewmodel.MainViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder

@Composable
fun HomeScreen(
    modifier: Modifier,
    navController: NavController
) {
    val viewModel: MainViewModel = hiltViewModel()
    val mediaDataList = viewModel.mediaDataState.collectAsState()
    var showProgress by remember {
        mutableStateOf(true)
    }

    if(showProgress) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    when (mediaDataList.value) {
        is Resource.Loading -> {
            showProgress = true
            Log.d("inside", "loading")
        }

        is Resource.Failure -> {
            showProgress = false
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                searchField(viewModel = viewModel)
                Spacer(modifier = Modifier.height(100.dp))
                Image(painter = painterResource(id = R.drawable.ic_bug), contentDescription = "", modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp), contentScale = ContentScale.Crop)
                Text(text = "Search Something New!", color = Color.White, modifier = Modifier.padding(vertical = 20.dp))
            }
        }

        is Resource.Success -> {
            showProgress = false
            val result = (mediaDataList.value as Resource.Success).value.categorizeResults(
                (mediaDataList.value as Resource.Success).value.results
            )
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())

            ) {
                searchField(viewModel = viewModel)
                MediaCarousels(modifier, categorizedResults = result, onItemClick = { mediaItem ->
                    val encodedJson = URLEncoder.encode(Json.encodeToString(mediaItem), "UTF-8")
                    navController.navigate("MovieDetailScreen/$encodedJson")
                })
            }
        }
    }
}

@Composable
fun searchField(viewModel: MainViewModel) {
    var text by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = text,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                keyboardController?.hide()
                viewModel.getDataFromModule(text)
            }
        ),
        onValueChange = {
            text = it
        },
        label = { Text("Search Here...") },
        modifier = Modifier.fillMaxWidth()
    )
}