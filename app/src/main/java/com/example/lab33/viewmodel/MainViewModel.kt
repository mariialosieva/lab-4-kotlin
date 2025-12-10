package com.example.lab33.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab33.data.AppDatabase
import com.example.lab33.data.LabRepository
import com.example.lab33.data.LabWork
import com.example.lab33.data.Subject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: LabRepository

    init {
        val database = AppDatabase.getDatabase(application)
        val dao = database.labDao()
        repository = LabRepository(dao)
        
        viewModelScope.launch {
            repository.initDataIfNeeded()
        }
    }

    val subjects: Flow<List<Subject>> = repository.subjects
    val doneLabs: Flow<List<LabWork>> = repository.doneLabs

    fun getLabsForSubject(subjectId: Long): Flow<List<LabWork>> {
        return repository.getLabsForSubject(subjectId)
    }
    
    suspend fun getSubjectById(id: Long): Subject? {
        return repository.getSubjectById(id)
    }

    fun updateLabStatus(labId: Long, isDone: Boolean) {
        viewModelScope.launch {
            repository.updateLabStatus(labId, isDone)
        }
    }
}
