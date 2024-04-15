package com.manuellugodev.hotelmanagment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.manuellugodev.hotelmanagment.navigation.Navigation
import com.manuellugodev.hotelmanagment.navigation.Screen
import com.manuellugodev.hotelmanagment.ui.theme.HotelManagmentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HotelManagmentTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                Scaffold(
                    topBar = {
                        if (currentRoute != Screen.WelcomeScreen.route && currentRoute != Screen.LoginScreen.route) {
                            TopBar()
                        }
                    },
                    bottomBar = {
                        if (currentRoute != Screen.WelcomeScreen.route && currentRoute != Screen.LoginScreen.route) {
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
fun TopBar() {
    TopAppBar(
        title = {
            Text(stringResource(id = R.string.app_name), textAlign = TextAlign.Center)
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        )
    )
}

@Composable
fun BottomBar(navController: NavHostController) {

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
    ) {
        NavigationBarItem(
            selected = true,
            onClick = { navController.navigate(Screen.ReservationScreen.route) },
            icon = { Icon(Icons.Default.Home, "Home") },
            label = { Text("Home") })
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Screen.MyReservationsScreen.route) },
            icon = { Icon(Icons.Default.Bed, "Reservations") },
            label = { Text("Reservations") })
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Screen.MyProfileScreen.route) },
            icon = { Icon(Icons.Default.Person, "Profile") },
            label = { Text("Profile") })

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














