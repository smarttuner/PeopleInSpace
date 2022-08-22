package com.surrus.peopleinspace

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import com.surrus.common.repository.PeopleInSpaceRepository
import com.surrus.common.repository.PeopleInSpaceRepositoryInterface
import com.surrus.peopleinspace.issposition.ISSPositionScreen
import com.surrus.peopleinspace.issposition.ISSPositionViewModel
import com.surrus.peopleinspace.persondetails.PersonDetailsScreen
import com.surrus.peopleinspace.persondetails.PersonDetailsViewModel
import com.surrus.peopleinspace.personlist.PersonListScreen
import com.surrus.peopleinspace.personlist.PersonListViewModel
import com.surrus.peopleinspace.ui.PeopleInSpaceTheme
import net.smarttuner.kaffeeverde.lifecycle.ui.viewModel
import net.smarttuner.kaffeeverde.navigation.compose.NavHost
import net.smarttuner.kaffeeverde.navigation.compose.composable
import net.smarttuner.kaffeeverde.navigation.compose.currentBackStackEntryAsState
import net.smarttuner.kaffeeverde.navigation.compose.rememberNavController


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PeopleInSpaceApp(peopleInSpaceRepository: PeopleInSpaceRepositoryInterface) {
    PeopleInSpaceTheme {
        MainLayout(peopleInSpaceRepository)
    }
}

sealed class Screen(val title: String) {
    object PersonList : Screen("PersonList")
    object PersonDetails : Screen("PersonDetails")
    object ISSPositionScreen : Screen("ISSPosition")
}

data class BottomNavigationitem(
    val route: String,
    val icon: ImageVector,
    val iconContentDescription: String
)

val bottomNavigationItems = listOf(
    BottomNavigationitem(
        Screen.PersonList.title,
        Icons.Default.Person,
        "People"
    ),
    BottomNavigationitem(
        Screen.ISSPositionScreen.title,
        Icons.Filled.LocationOn,
        "ISS Position"
    )
)

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainLayout(peopleInSpaceRepository: PeopleInSpaceRepositoryInterface) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                bottomNavigationItems.forEach { bottomNavigationitem ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                bottomNavigationitem.icon,
                                contentDescription = bottomNavigationitem.iconContentDescription
                            )
                        },
                        selected = currentRoute == bottomNavigationitem.route,
                        onClick = {
                            navController.navigate(bottomNavigationitem.route) {
                                popUpTo(navController.graph.id)
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->

        NavHost(navController, startDestination = Screen.PersonList.title) {
            composable(
                route = Screen.PersonList.title,
            ) {
                val viewModel = viewModel(
                    PersonListViewModel::class,
                    listOf(peopleInSpaceRepository)
                ) { PersonListViewModel(peopleInSpaceRepository) }

                PersonListScreen(
                    paddingValues = paddingValues,
                    personSelected = {
                        navController.navigate(Screen.PersonDetails.title + "/${it.name}")
                    },
                    viewModel
                )
            }
            composable(
                route = Screen.PersonDetails.title + "/{person}",
            ) { backStackEntry ->
                val personName = backStackEntry.arguments?.get("person") as String
                val viewModel = viewModel(
                    PersonDetailsViewModel::class,
                    listOf(personName,peopleInSpaceRepository)
                ) { PersonDetailsViewModel(personName,peopleInSpaceRepository) }

                PersonDetailsScreen(
                    viewModel,
                    popBack = { navController.popBackStack() }
                )
            }
            composable(Screen.ISSPositionScreen.title) {
                val viewModel = viewModel(
                    ISSPositionViewModel::class,
                    listOf(peopleInSpaceRepository)
                ) { ISSPositionViewModel(peopleInSpaceRepository) }

                ISSPositionScreen(viewModel)
            }
        }
    }
}


