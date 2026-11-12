package com.example.synctask.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.synctask.data.Task
import com.example.synctask.data.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    fun saveTask(task: Task) {
        viewModelScope.launch {
            repository.insertTask(task)
        }
    }
}