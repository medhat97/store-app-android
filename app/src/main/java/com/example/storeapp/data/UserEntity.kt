package com.example.storeapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.storeapp.model.StoreUsers

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: String = "",
    val userName: String = "",
    val userPassword: String = "",
    val userPrivilage: String = "",
    val userAccount: String = ""
)

fun UserEntity.toStoreUser(): StoreUsers {
    return StoreUsers(
        id = id,
        userName = userName,
        userPassword = userPassword,
        userPrivilage = userPrivilage,
        userAccount = userAccount
    )
}

fun StoreUsers.toUserEntity(): UserEntity {
    return UserEntity(
        id = id,
        userName = userName,
        userPassword = userPassword,
        userPrivilage = userPrivilage,
        userAccount = userAccount
    )
}
