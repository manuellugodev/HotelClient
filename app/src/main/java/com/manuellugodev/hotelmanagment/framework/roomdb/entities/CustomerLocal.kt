package com.manuellugodev.hotelmanagment.framework.roomdb.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.manuellugodev.hotelmanagment.features.core.domain.model.Customer

@Entity
data class CustomerLocal(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    val email: String,
    val phone: String

)

fun Customer.toCustomerLocal():CustomerLocal{
    return CustomerLocal(id, firstName, lastName, email, phone)
}

fun CustomerLocal.toCustomerDomain(): Customer {
    return Customer(id, firstName, lastName, email, phone)
}
class CustomerLocalConverter() {

    @TypeConverter
    fun fromJson(json:String):CustomerLocal{
        return Gson().fromJson(json,CustomerLocal::class.java)
    }

    @TypeConverter
    fun toJson(customerLocal: CustomerLocal):String{
        return Gson().toJson(customerLocal)
    }
}