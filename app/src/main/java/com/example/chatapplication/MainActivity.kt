package com.example.chatapplication

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.chatapplication.ui.theme.ChatApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.chatapplication.presentation.navigation.NavigationGraph
import com.example.chatapplication.presentation.viewmodel.RecipeViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  private val uriState = MutableStateFlow(value = "")//chat screen

  private val viewModel: RecipeViewModel by viewModels()

  private val imagePicker = registerForActivityResult<PickVisualMediaRequest, Uri?>(
    ActivityResultContracts.PickVisualMedia()
  ) { uri: Uri? ->
    uri?.let {
      uriState.update { uri.toString() }
    }
  }

  @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
  @RequiresApi(Build.VERSION_CODES.O)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    installSplashScreen()

    setContent {
      val navController = rememberNavController()

      ChatApplicationTheme(darkTheme = false) {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background,
        ) {
          NavigationGraph(Modifier, navController,  "chat", imagePicker, uriState, viewModel )
        }
      }
    }
  }
}