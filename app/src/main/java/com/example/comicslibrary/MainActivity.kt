package com.example.comicslibrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.comicslibrary.ui.theme.ComicsLibraryTheme
import com.example.comicslibrary.view.CharactersBottomNav
import com.example.comicslibrary.view.CollectionScreen
import com.example.comicslibrary.view.LibraryScreen

//https://medium.com/@summitkumar/unlocking-the-power-of-sealed-classes-in-kotlin-design-patterns-and-better-code-organization-5627d73a4903
//polu kalo arthro gia sealed classes, san enums alla me perissoteres dunatothtes. Enums
//uparxoun epishs kai sth Kotlin
sealed class Destination(val route: String) {
    object Library: Destination("Library")
    object Collection: Destination("collection")
    object CharacterDetail: Destination("character/{characterId}") {
        /*
        * Auto sth kotlin legetai single-line function. Den exei {} kai return.
        * Auto meta to = einai to return value.
        * */
        fun createRoute(characterId: Int?) = "character/$characterId"
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComicsLibraryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    CharacterScaffold(navController = navController)
                }
            }
        }
    }
}
//Scaffold: Xrhsimopoieitai gia to ui, na mporoume na exoume mazi bootomAppBar, klp
//https://developer.android.com/jetpack/compose/components/scaffold
@Composable
fun CharacterScaffold(navController: NavHostController) {
    //tha kseroume pou eimaste sto scaffold, wste to swsto link
    //na ginei highlight otan patisoume se mia sygekrimenh othoni
    val scaffoldState = rememberScaffoldState()

  androidx.compose.material.Scaffold(
      scaffoldState = scaffoldState,
      bottomBar = { CharactersBottomNav(navController = navController) }
  ) {
      paddingValues ->
       //me ton NavHost kanoume navigate sthn efamorgh
        NavHost(navController = navController, startDestination = Destination.Library.route) {
            composable(Destination.Library.route) {
                LibraryScreen()
            }
            composable(Destination.Collection.route) {
                CollectionScreen()
            }
            composable(Destination.CharacterDetail.route) { navBackStackEntry ->

            }
        }
   }
}








