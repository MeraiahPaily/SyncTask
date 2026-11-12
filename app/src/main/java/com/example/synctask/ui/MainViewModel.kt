package com.example.synctask.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.synctask.data.Task
import com.example.synctask.data.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")

    private val _tasks = repository.allTasks
    val allTasks = combine(_tasks, _searchQuery) { tasks, query ->
        if (query.isBlank()) {
            tasks
        } else {
            tasks.filter { it.title.contains(query, ignoreCase = true) }
        }
    }.asLiveData()

    init {
        repository.startRealtimeSync()
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }

    fun searchTasks(query: String) {
        _searchQuery.value = query
    }

    fun onLogout() {
        viewModelScope.launch {
            repository.clearLocalDataOnLogout()
        }
    }
}