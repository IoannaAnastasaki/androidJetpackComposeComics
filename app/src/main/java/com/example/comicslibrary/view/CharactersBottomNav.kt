package com.example.comicslibrary.view

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.comicslibrary.Destination
import com.example.comicslibrary.R

@Composable
fun CharactersBottomNav(navController: NavHostController) {
    BottomNavigation(elevation = 5.dp) {
        //auto tha epistrepsei to top entry apo to backstack meta apo
        //allagh sto navigation: tap back h otan kanoyme add another view sto back stack
        //etsi kseroyme se aytes tis 2 grammes poia einai i othoni mas
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry.value?.destination

        val iconLibrary = painterResource(id = R.drawable.ic_library)
        val iconCollection = painterResource(id = R.drawable.ic_collections)

        BottomNavigationItem(
            selected = currentDestination?.route == Destination.Library.route,
            onClick = { navController.navigate(Destination.Library.route) {
                //mono gia thn arxh, mas paei ekei prin ksekinhsei to navigation sthn efarmogh
                //opote to popUp einai mono gia to prwto BottomNavigationItem
                popUpTo(Destination.Library.route)
                println("ioanna = ${currentDestination?.route} = ${Destination.Library.route}")
                /*
                * otan perihgoumaste sthn efarmogh, dhmiourgountai polles othones. Dld px
                * an perihgoumai kai pataw to bottom bar toy Library, tha vgalei polles
                * library othones. Gia na to apofygw ayto, xrhsimopoiw to launchingSingleTop,
                * poy m exei panta mia, to empodizei dld apo to na ftiaxnei diarkws nees othones
                * */
                launchSingleTop = true
            } },
            icon = { Icon(painter = iconLibrary, contentDescription = null) },
            label = { Text( text = Destination.Library.route) }
        )

        BottomNavigationItem(
            selected = currentDestination?.route == Destination.Collection.route,
            onClick = { navController.navigate(Destination.Collection.route) {
                launchSingleTop = true
            } },
            icon = { Icon(painter = iconCollection, contentDescription = null) },
            label = { Text( text = Destination.Collection.route) }
        )

    }
}