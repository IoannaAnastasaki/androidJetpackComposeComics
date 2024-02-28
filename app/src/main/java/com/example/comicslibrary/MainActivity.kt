package com.example.comicslibrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.comicslibrary.ui.theme.ComicsLibraryTheme
import com.example.comicslibrary.view.CharactersBottomNav
import com.example.comicslibrary.view.CollectionScreen
import com.example.comicslibrary.view.LibraryScreen
import com.example.comicslibrary.viewmodel.LibraryApiViewModel
import dagger.hilt.android.AndroidEntryPoint

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

@AndroidEntryPoint //gia na kanoume inject dependencies se mia activity
/*gia na kanw inject a dependency, kanw annotate tis variables poy tha kanw
//inject me @Inject
//Me auto to annotation dhmioyrghtai aytomata enas dependency container,
ginetai managed apo thn MainActivity.

 */
class MainActivity : ComponentActivity() {

    private val lvm by viewModels<LibraryApiViewModel>()
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
                    CharacterScaffold(navController = navController, lvm)
                }
            }
        }
    }
}
//Scaffold: Xrhsimopoieitai gia to ui, na mporoume na exoume mazi bootomAppBar, klp
//https://developer.android.com/jetpack/compose/components/scaffold
@Composable
fun CharacterScaffold(navController: NavHostController, lvm: LibraryApiViewModel) {
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
                LibraryScreen(navController, lvm, paddingValues)
            }
            composable(Destination.Collection.route) {
                CollectionScreen()
            }
            composable(Destination.CharacterDetail.route) { navBackStackEntry ->

            }
        }
   }
}








