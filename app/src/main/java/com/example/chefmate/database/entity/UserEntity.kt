package com.example.chefmate.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.chefmate.model.AnotherUserData

@Entity(tableName = "Users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val userId: Int? = null,
    val fullName: String,
    val phone: String,
    val email: String,
    val password: String,
    val image: String,
    val createdAt: String
)

fun UserEntity.toUserView(): AnotherUserData {
    return AnotherUserData(
        userId = this.userId ?: 0,
        fullName = this.fullName,
        image = this.image
    )
}