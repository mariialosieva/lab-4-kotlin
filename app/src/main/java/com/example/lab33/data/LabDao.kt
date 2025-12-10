package com.example.lab33.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface LabDao {
    @Query("SELECT * FROM Subject")
    fun getSubjects(): Flow<List<Subject>>

    @Query("SELECT * FROM LabWork WHERE subjectId = :subjectId")
    fun getLabsForSubject(subjectId: Long): Flow<List<LabWork>>

    @Query("SELECT * FROM LabWork WHERE statusDone = 1")
    fun getDoneLabs(): Flow<List<LabWork>>

    @Query("SELECT * FROM Subject WHERE id = :id")
    suspend fun getSubjectById(id: Long): Subject?

    @Insert
    suspend fun insertSubjects(subjects: List<Subject>): List<Long>

    @Insert
    suspend fun insertLabs(labs: List<LabWork>)

    @Update
    suspend fun updateLab(lab: LabWork)
    
    @Query("UPDATE LabWork SET statusDone = :isDone WHERE id = :id")
    suspend fun updateLabStatus(id: Long, isDone: Boolean)

    @Query("SELECT COUNT(*) FROM Subject")
    suspend fun getSubjectCount(): Int

    @Query("SELECT * FROM Subject")
    suspend fun getSubjectsList(): List<Subject>
}
