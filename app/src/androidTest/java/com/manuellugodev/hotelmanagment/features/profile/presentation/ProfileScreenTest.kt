package com.manuellugodev.hotelmanagment.features.profile.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.manuellugodev.hotelmanagment.HotelManagmentTest
import com.manuellugodev.hotelmanagment.features.profile.domain.Profile
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
class ProfileScreenTest:HotelManagmentTest(){


    @get:Rule
    val ruleCompose= createComposeRule()

    @Test
    fun assertProfileIsDisplayedWithNameEmailAndPhone()= runBlocking<Unit> {

        val profile =Profile(username = "manuellugo", firstName = "Manuel", lastName = "Lugo","manuel@gmail.com","123456789")
        val state=ProfileState(showProfile = profile)

        startProfileScreen(state)
        ruleCompose.onNodeWithText(profile.firstName).assertIsDisplayed()
        ruleCompose.onNodeWithText(profile.email).assertIsDisplayed()
        ruleCompose.onNodeWithText(profile.phone).assertIsDisplayed()

    }


    private fun startProfileScreen(state: ProfileState=ProfileState(),event:(ProfileEvent)->Unit = {}){
        ruleCompose.setContent {
            ProfileScreen(state = state,event)
        }
    }
}