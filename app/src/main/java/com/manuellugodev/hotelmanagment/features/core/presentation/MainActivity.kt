package com.manuellugodev.hotelmanagment.features.core.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.manuellugodev.hotelmanagment.features.core.navigation.Navigation
import com.manuellugodev.hotelmanagment.features.core.navigation.Screen
import com.manuellugodev.hotelmanagment.features.core.presentation.ui.theme.HotelManagmentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HotelManagmentTheme(dynamicColor = false) {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                Scaffold(
                    topBar = {
                        when (currentRoute) {
                            Screen.ReservationScreen.route -> {
                                TopBarAppHotel("Reservation")
                            }

                            Screen.MyProfileScreen.route -> {
                                TopBarAppHotel(title = "My Profile")
                            }

                            Screen.MyReservationsScreen.route -> {
                                TopBarAppHotel(title = "My Reservations")
                            }
                        }
                    },
                    bottomBar = {
                        if (currentRoute != Screen.WelcomeScreen.route && currentRoute != Screen.LoginScreen.route && currentRoute!= Screen.RegisterScreen.route) {
                            BottomBar(navController)
                        }
                    }) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        Navigation(navController)
                    }

                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarAppHotel(title: String) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            scrolledContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        ),
        windowInsets = WindowInsets(0)
    )
}

@Composable
@Preview
fun topBarPreview() {
    TopBarAppHotel(title = "Hotel")
}

@Composable
fun BottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primary,
    ) {
        // Home/Reservations
        NavigationBarItem(
            selected = currentRoute == Screen.ReservationScreen.route,
            onClick = {
                if (currentRoute != Screen.ReservationScreen.route) {
                    navController.navigate(Screen.ReservationScreen.route)
                }
            },
            icon = {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "Home",
                    tint = if (currentRoute == Screen.ReservationScreen.route)
                        MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            label = {
                Text(
                    "Home",
                    color = if (currentRoute == Screen.ReservationScreen.route)
                        MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        )

        // My Reservations
        NavigationBarItem(
            selected = currentRoute == Screen.MyReservationsScreen.route,
            onClick = {
                if (currentRoute != Screen.MyReservationsScreen.route) {
                    navController.navigate(Screen.MyReservationsScreen.route)
                }
            },
            icon = {
                Icon(
                    Icons.Default.Bed,
                    contentDescription = "My Reservations",
                    tint = if (currentRoute == Screen.MyReservationsScreen.route)
                        MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            label = {
                Text(
                    "Reservations",
                    color = if (currentRoute == Screen.MyReservationsScreen.route)
                        MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        )

        // Profile
        NavigationBarItem(
            selected = currentRoute == Screen.MyProfileScreen.route,
            onClick = {
                if (currentRoute != Screen.MyProfileScreen.route) {
                    navController.navigate(Screen.MyProfileScreen.route)
                }
            },
            icon = {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = if (currentRoute == Screen.MyProfileScreen.route)
                        MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            label = {
                Text(
                    "Profile",
                    color = if (currentRoute == Screen.MyProfileScreen.route)
                        MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        )
    }
}

@Composable
fun Screen(innerPadding: PaddingValues) {
    val state = rememberScrollState()
    Column(
        Modifier
            .padding(innerPadding)
            .scrollable(state, Orientation.Vertical)
    ) {

    }
}
