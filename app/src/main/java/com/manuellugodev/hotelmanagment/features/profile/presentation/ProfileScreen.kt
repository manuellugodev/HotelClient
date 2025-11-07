package com.manuellugodev.hotelmanagment.features.profile.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.manuellugodev.hotelmanagment.features.core.composables.ErrorSnackbar
import com.manuellugodev.hotelmanagment.features.core.navigation.Screen
import com.manuellugodev.hotelmanagment.features.core.navigation.navigateAndCleanBackStack
import com.manuellugodev.hotelmanagment.features.profile.domain.Profile

@Composable
fun ProfileScreenRoot(navController: NavController, viewModel: ProfileViewModel) {
    val state by viewModel.stateProfile.collectAsState()

    LaunchedEffect(key1 = state.isLogOut) {
        if (state.isLogOut) {
            navController.navigateAndCleanBackStack(Screen.WelcomeScreen.route)
        }
    }

    ProfileScreen(state, viewModel::onEvent)

    LaunchedEffect(key1 = state.getDataProfile) {
        if (state.getDataProfile) {
            viewModel.onEvent(ProfileEvent.LoadProfile)
        }
    }
}

@Composable
fun ProfileScreen(state: ProfileState, onEvent: (ProfileEvent) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.showProfile != null) {
                // Profile Header Section
                ProfileHeader(state.showProfile)

                Spacer(modifier = Modifier.height(24.dp))

                // User Information Section
                UserInformationSection(state.showProfile)

                Spacer(modifier = Modifier.height(32.dp))

                // Actions Section
                ProfileActionsSection(onEvent)
            }
        }

        // Error Snackbar
        if (state.showError.isNotEmpty()) {
            ErrorSnackbar(
                modifier = Modifier.align(Alignment.BottomCenter),
                errorMessage = state.showError
            ) {
                onEvent(ProfileEvent.DismissError)
            }
        }
    }
}

@Composable
private fun ProfileHeader(profile: Profile) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Profile Avatar
        Card(
            modifier = Modifier.size(120.dp),
            shape = CircleShape,
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile Avatar",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(60.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // User Name
        Text(
            text = "${profile.firstName} ${profile.lastName}",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Username
        Text(
            text = "@${profile.username}",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun UserInformationSection(profile: Profile) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Personal Information",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Email Info Card
        UserInfoCard(
            icon = Icons.Default.Email,
            label = "Email",
            value = profile.email,
            iconTint = MaterialTheme.colorScheme.primary
        )

        // Phone Info Card
        UserInfoCard(
            icon = Icons.Default.Phone,
            label = "Phone",
            value = profile.phone,
            iconTint = MaterialTheme.colorScheme.primary
        )

        // Username Info Card
        UserInfoCard(
            icon = Icons.Default.Person,
            label = "Username",
            value = profile.username,
            iconTint = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun UserInfoCard(
    icon: ImageVector,
    label: String,
    value: String,
    iconTint: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = iconTint,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
private fun ProfileActionsSection(onEvent: (ProfileEvent) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Account Actions",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Logout Button
        Button(
            onClick = { onEvent(ProfileEvent.LogOutSession) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ExitToApp,
                contentDescription = "Logout",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Log Out",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
@Preview
fun ProfileScreenPreview() {
    MaterialTheme {
        ProfileScreen(
            state = ProfileState(
                showProfile = Profile(
                    username = "manuellugo",
                    firstName = "Manuel",
                    lastName = "Lugo",
                    email = "manuellugo2000ml@gmail.com",
                    phone = "78627815631",
                    guestId = 1
                ),
                showError = "",
                isLogOut = false,
                getDataProfile = false
            ),
            onEvent = {}
        )
    }
}

@Composable
@Preview
fun ProfileHeaderPreview() {
    MaterialTheme {
        ProfileHeader(
            Profile(
                username = "manuellugo",
                firstName = "Manuel",
                lastName = "Lugo",
                email = "manuellugo2000ml@gmail.com",
                phone = "78627815631",
                guestId = 1
            )
        )
    }
}
