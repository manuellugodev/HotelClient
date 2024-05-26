package com.manuellugodev.hotelmanagment

import com.manuellugodev.hotelmanagment.framework.roomdb.HotelDatabase
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

abstract class HotelManagmentTest {

    @Inject
    lateinit var db: HotelDatabase

    @get:Rule
    val hiltRule=HiltAndroidRule(this)

    @Before
    open fun setUp(){
        hiltRule.inject()
        db.clearAllTables()
    }

    @After
    open fun tearDown(){
        db.close()
    }
}