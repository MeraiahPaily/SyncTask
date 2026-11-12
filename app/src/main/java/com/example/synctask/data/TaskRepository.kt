package com.example.synctask.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

class TaskRepository(
    private val taskDao: TaskDao,
    private val categoryDao: CategoryDao,
    private val projectDao: ProjectDao,
    private val firestore: FirebaseFirestore
) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    val allTasks: Flow<List<Task>>
        get() {
            val userId = auth.currentUser?.uid
            return if (userId != null) {
                taskDao.getAllTasks(userId)
            } else {
                emptyFlow()
            }
        }

    val allCategories: Flow<List<Category>> = categoryDao.getAllCategories()
    val allProjects: Flow<List<Project>> = projectDao.getAllProjects()

    suspend fun insertTask(task: Task) {
        val userId = auth.currentUser?.uid ?: return
        val taskWithUser = task.copy(userId = userId)
        taskDao.insertTask(taskWithUser)
        firestore.collection("users").document(userId).collection("tasks").document(taskWithUser.id).set(taskWithUser)
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
        if (task.userId.isNotBlank()) {
            firestore.collection("users").document(task.userId).collection("tasks").document(task.id).set(task)
        }
    }

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
        if (task.userId.isNotBlank()) {
            firestore.collection("users").document(task.userId).collection("tasks").document(task.id).delete()
        }
    }

    fun startRealtimeSync() {
        val userId = auth.currentUser?.uid ?: return
        firestore.collection("users").document(userId).collection("tasks")
            .addSnapshotListener { snapshot, _ ->
                snapshot?.let { querySnapshot ->
                    val tasks = querySnapshot.toObjects<Task>().map { it.copy(userId = userId) }
                    CoroutineScope(Dispatchers.IO).launch {
                        taskDao.insertAll(tasks)
                    }
                }
            }
    }

    suspend fun clearLocalDataOnLogout() {
        taskDao.clearAllTasks()
    }
}