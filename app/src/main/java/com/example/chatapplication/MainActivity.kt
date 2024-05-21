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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.chatapplication.data.remote.model.Recipe
import com.example.chatapplication.presentation.navigation.NavigationGraph

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  private val uriState = MutableStateFlow(value = "")//chat screen
  private val imagePicker = registerForActivityResult<PickVisualMediaRequest, Uri?>(// chatscreen
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

      ChatApplicationTheme {
        // A surface container using the 'background' color from the theme
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background,
        ) {
          NavigationGraph(modifier = Modifier, navController = navController, startDestination = "chat", imagePicker, uriState , recipe = Recipe())

        }
      }
    }
  }
}





























//          val items = listOf(
//            NavigationItem(
//              title = "Chat Screen",
//              selectedIcon = Icons.Outlined.Person,
//              unselectedIcon = Icons.Outlined.Person,
//              route = "chatScreen"
//            ),
//            NavigationItem(
//              title = "Baking Screen",
//              selectedIcon = Icons.Filled.Favorite,
//              unselectedIcon = Icons.Outlined.Favorite,
//              //badgeCount = 45,
//              route = "bakingScreen"
//            ),
//            NavigationItem(
//              title = "Recipes",
//              selectedIcon = Icons.Filled.Info,
//              unselectedIcon = Icons.Outlined.Info,
//              route = "recipeScreen"
//            ),
//          )
//
//          Surface(
//            modifier = Modifier.fillMaxSize(),
//            color = MaterialTheme.colorScheme.background
//          ) {
//            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//            val scope = rememberCoroutineScope()
//            var selectedItemIndex by rememberSaveable {
//              mutableStateOf(0)
//            }
//            val navController = rememberNavController()
//            ModalNavigationDrawer(
//              drawerContent = {
//                ModalDrawerSheet {
//                  Spacer(modifier = Modifier.height(16.dp))
//                  items.forEachIndexed { index, item ->
//                    NavigationDrawerItem(
//                      label = {
//                        Text(text = item.title)
//                      },
//                      selected = index == selectedItemIndex,
//                      onClick = {
//                        navController.navigate(item.route)
//                        selectedItemIndex = index
//                        scope.launch {
//                          drawerState.close()
//                        }
//                      },
//                      icon = {
//                        Icon(
//                          imageVector = if (index == selectedItemIndex) {
//                            item.selectedIcon
//                          } else item.unselectedIcon,
//                          contentDescription = item.title
//                        )
//                      },
//                      badge = {
//                        item.badgeCount?.let {
//                          Text(text = item.badgeCount.toString())
//                        }
//                      },
//                      modifier = Modifier
//                        .padding(NavigationDrawerItemDefaults.ItemPadding)
//                    )
//                  }
//                }
//              },
//              drawerState = drawerState
//            ) {
//              Scaffold(
//                topBar = {
//                  TopAppBar(
//                    title = {
//                      Text(text = "Chat App")
//                    },
//                    navigationIcon = {
//                      IconButton(onClick = {
//                        scope.launch {
//                          drawerState.open()
//                        }
//                      }) {
//                        Icon(
//                          imageVector = Icons.Default.Menu,
//                          contentDescription = "Menu"
//                        )
//                      }
//                    }
//                  )
//                }
//              ) { paddingValues ->
//                // NavHost to handle navigation
//                NavHost(navController = navController, startDestination = "chatScreen", modifier = Modifier.padding(paddingValues)) {
//                  composable("chatScreen") { ChatScreen(navController) }
//                  composable("bakingScreen") { BakingScreen() }
//                  composable("recipeScreen") { RecipeScreen()}
//
//                }
//              }
//          }
      //        }
//       }
//      }
//    }
//  }
//
//}
//
