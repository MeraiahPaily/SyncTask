package com.example.synctask.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val userId: String = "",
    val title: String = "",
    val description: String = "",
    val isCompleted: Boolean = false,
    val categoryId: String? = null,
    val projectId: String? = null,
    val dueDate: Long? = null,
    val reminderTime: Long? = null
)