package com.example.storeapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.storeapp.model.StoreRecord
import com.example.storeapp.model.StoresList
import java.util.Date

@Entity(tableName = "stores")
data class StoreListEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val location: String,
    val manager: String
)

fun StoreListEntity.toStoreList(): StoresList {
    return StoresList(
        id = id,
        name = name ,
        location = location,
        manager = manager
    )
}


fun StoresList.toStoreListEntity(): StoreListEntity {
    return StoreListEntity(
        id = id,
        name = name ,
        location = location,
        manager = manager
    )
}