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
import com.example.chatapplication.presentation.ui.BakingScreen
import com.example.chatapplication.presentation.ui.ChatScreen
import com.example.chatapplication.ui.theme.ChatApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatapplication.presentation.ui.recipe_list_screen.RecipeListScreen
import kotlinx.coroutines.launch


data class NavigationItem(
  val title: String,
  val selectedIcon: ImageVector,
  val unselectedIcon: ImageVector,
  val badgeCount: Int? = null,
  val route: String
)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  @OptIn(ExperimentalMaterial3Api::class)
  @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
  @RequiresApi(Build.VERSION_CODES.O)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      ChatApplicationTheme {
        // A surface container using the 'background' color from the theme
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background,
        ) {
          val items = listOf(
            NavigationItem(
              title = "Chat Screen",
              selectedIcon = Icons.Outlined.Person,
              unselectedIcon = Icons.Outlined.Person,
              route = "chatScreen"
            ),
            NavigationItem(
              title = "Baking Screen",
              selectedIcon = Icons.Filled.Favorite,
              unselectedIcon = Icons.Outlined.Favorite,
              //badgeCount = 45,
              route = "bakingScreen"
            ),
            NavigationItem(
              title = "Recipes",
              selectedIcon = Icons.Filled.Info,
              unselectedIcon = Icons.Outlined.Info,
              route = "recipeScreen"
            ),
          )

          Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
          ) {
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            var selectedItemIndex by rememberSaveable {
              mutableStateOf(0)
            }
            val navController = rememberNavController()
            ModalNavigationDrawer(
              drawerContent = {
                ModalDrawerSheet {
                  Spacer(modifier = Modifier.height(16.dp))
                  items.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                      label = {
                        Text(text = item.title)
                      },
                      selected = index == selectedItemIndex,
                      onClick = {
                        navController.navigate(item.route)
                        selectedItemIndex = index
                        scope.launch {
                          drawerState.close()
                        }
                      },
                      icon = {
                        Icon(
                          imageVector = if (index == selectedItemIndex) {
                            item.selectedIcon
                          } else item.unselectedIcon,
                          contentDescription = item.title
                        )
                      },
                      badge = {
                        item.badgeCount?.let {
                          Text(text = item.badgeCount.toString())
                        }
                      },
                      modifier = Modifier
                        .padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                  }
                }
              },
              drawerState = drawerState
            ) {
              Scaffold(
                topBar = {
                  TopAppBar(
                    title = {
                      Text(text = "Chat App")
                    },
                    navigationIcon = {
                      IconButton(onClick = {
                        scope.launch {
                          drawerState.open()
                        }
                      }) {
                        Icon(
                          imageVector = Icons.Default.Menu,
                          contentDescription = "Menu"
                        )
                      }
                    }
                  )
                }
              ) { paddingValues ->
                // NavHost to handle navigation
                NavHost(navController = navController, startDestination = "chatScreen", modifier = Modifier.padding(paddingValues)) {
                  composable("chatScreen") { ChatScreen(navController) }
                  composable("bakingScreen") { BakingScreen() }
                  composable("recipeScreen") { RecipeListScreen() }

                }
              }
            }
          }
        }
      }
    }
  }

}

